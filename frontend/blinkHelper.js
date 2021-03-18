var blinkInProcess = false;
var leds;
var blinkInterval;
var arrayBlinkInterval = [];

//Blinking single led
function blinkStart(led) {
    if (!blinkInProcess) {
        blinkInProcess = true;
        blinkInterval = setInterval(function blinking() {
            if (led.readSync() === 0) {
                led.writeSync(1);
            } else {
                led.writeSync(0);
            }
        },300);
        arrayBlinkInterval.push(blinkInterval);
    }
}

//Blinking single led slow
function blinkStartSlow(led) {
    if (!blinkInProcess) {
        blinkInProcess = true;
        blinkInterval = setInterval(function blinking() {
            if (led.readSync() === 0) {
                led.writeSync(1);
            } else {
                led.writeSync(0);
            }
        },900);
        arrayBlinkInterval.push(blinkInterval);
    }
}

//Blinking single led fast
function blinkFastStart(led) {
    if (!blinkInProcess) {
        blinkInProcess = true;
        blinkInterval = setInterval(function blinking() {
            if (led.readSync() === 0) {
                led.writeSync(1);
            } else {
                led.writeSync(0);
            }
        },100);
        arrayBlinkInterval.push(blinkInterval);
    }
}

//Blinking end single led
function blinkEnd(led) {
    clearInterval(blinkInterval);
    blinkInProcess = false;
    led.writeSync(0);
}

//Blinking multiple leds
function blinkStartLeds(ledsInput) {
    leds = ledsInput;
    for (let i = 0; i < leds.length; i++) {
        blinkInProcess = false;
        blinkStart(leds[i]);
    }
}

//Blinking end multiple leds
function blinkEndLeds() {
    try {
        if (arrayBlinkInterval.length > 0) {
            for (let i = 0; i < arrayBlinkInterval.length; i++) {
                clearInterval(arrayBlinkInterval[i]);
            }
            for (let i = 0; i < leds.length; i++) {
                leds[i].writeSync(0);
            }
        }
        blinkInProcess = false;
    } catch(e) {
        console.log('error');
    }
}

//Stop function stop existing blinking leds also on callback
function stopLeds(ledsInput, ledStop){
    leds = [ ...ledsInput ];
    blinkEndLeds();
    setTimeout(function () {
        blinkEndLeds();
        if (ledStop.readSync() != 1){

        }
        blinkConfirm(ledStop);
    },1000);
}

//Blinking multiple leds depending on mode
function blinkSpecificLedsStart(input, ledsInput, mode) {
    leds = [ ...ledsInput ];
    var ledsSelected = [];

    if (mode === 'Play1'){
        for (let i = 0; i < (input.length - 18); i++) {
            if (!input[i]) {
                ledsSelected.push(leds[i]);
            }
        }
    }
    if (mode === 'Play2'){
        for (let i = 9; i < (input.length - 9); i++) {
            if (!input[i]) {
                ledsSelected.push(leds[i - 9]);
            }
        }
    }
    if (mode === 'Play3'){
        for (let i = 18; i < input.length; i++) {
            if (!input[i]) {
                ledsSelected.push(leds[i -18]);
            }
        }
    }
    if (mode === 'Record1'){
        for (let i = 0; i < input.length - 18; i++) {
            if (input[i]) {
                ledsSelected.push(leds[i]);
            }
        }
    }
    if (mode === 'Record2'){
        for (let i = 9; i < (input.length - 9); i++) {
            if (input[i]) {
                ledsSelected.push(leds[i - 9]);
            }
        }
    }
    if (mode === 'Record3'){
        for (let i = 18; i < input.length; i++) {
            if (input[i]) {
                ledsSelected.push(leds[i - 18]);
            }
        }
    }
    blinkStartLeds(ledsSelected);
}

//Confirmation blink
function blinkConfirm(led) {
    if (!blinkInProcess) {
        blinkInProcess = true;
        blinkInterval = setInterval(function blinking() {
            if (led.readSync() === 0) {
                led.writeSync(1);
            } else {
                led.writeSync(0);
            }
        },50);
        arrayBlinkInterval.push(blinkInterval);
    }
    setTimeout(function () {
        clearInterval(blinkInterval);
        blinkInProcess = false;
        led.writeSync(0);
    },1000);
}

function blinkPage(led, page) {
    var i = 0;
    function pageBlink() {
        if (i < (page*2)) {
            setTimeout(function () {
                if (led.readSync() === 0) {
                    led.writeSync(1);
                } else {
                    led.writeSync(0);
                }
                i++;
                pageBlink();
            }, 150)

        }
    }
    blinkInterval = setInterval(function blinking() {
        i = 0;
        pageBlink();
    },1500);
    arrayBlinkInterval.push(blinkInterval);

}


module.exports.blinkStart = blinkStart;
module.exports.blinkFastStart = blinkFastStart;
module.exports.blinkEnd = blinkEnd;
module.exports.blinkStartLeds = blinkStartLeds;
module.exports.blinkEndLeds = blinkEndLeds;
module.exports.blinkSpecificLedsStart = blinkSpecificLedsStart;
module.exports.blinkConfirm = blinkConfirm;
module.exports.stopLeds = stopLeds;
module.exports.blinkStartSlow = blinkStartSlow;
module.exports.blinkPage = blinkPage;

