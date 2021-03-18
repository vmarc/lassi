const Gpio = require('onoff').Gpio;
const blinkHelper = require('./blinkHelper');
const httpHelper = require('./httpHelper');
const looper = require('./looper');
const shutdown = require('./shutdown');

var recordMode = 0;
var recordDetailMode = 0;
var playMode = 0;
var stopMode = false;
var pauseMode = false;
var buttonsInUse = [];
var recordedLed;
var playingLed;
var debounceTime1 = 500;
var debounceTime2 = 750;
var buttonPages = 1;

//Delay for buttons
var sys1 = true;
var sys2 = true;
var sys3 = true;
var sys4 = true;
var sys5 = true;
var sys6 = true;
var sys7 = true;
var sys8 = true;
var sys9 = true;
var sysPlay = true;
var sysStop = true;
var sysRecord = true;

//Initializing Output
const led1 = new Gpio(10, 'out');
const led2 = new Gpio(24, 'out');
const led3 = new Gpio(23, 'out');
const led4 = new Gpio(22, 'out');
const led5 = new Gpio(27, 'out');
const led6 = new Gpio(18, 'out');
const led7 = new Gpio(17, 'out');
const led8 = new Gpio(15, 'out');
const led9 = new Gpio(14, 'out');
const ledMain = new Gpio(4, 'out');
const ledPlay = new Gpio(11, 'out');
const ledStop = new Gpio(25, 'out');
const ledRecord = new Gpio(9, 'out');
const fan = new Gpio(2, 'out');

//Array leds
var ledsAll = [ledPlay, ledStop, ledRecord, led1, led2, led3, led4, led5, led6, led7, led8, led9];
var ledsFunction = [led1, led2, led3, led4, led5, led6, led7, led8, led9];

//Initializing Input
const button1 = new Gpio(16, 'in', 'both', 'rising');
const button2 = new Gpio(19, 'in', 'both','rising');
const button3 = new Gpio(13, 'in', 'both', 'rising');
const button4 = new Gpio(12, 'in', 'both', 'rising');
const button5 = new Gpio(6, 'in', 'both', 'rising');
const button6 = new Gpio(5, 'in', 'both', 'rising');
const button7= new Gpio(8, 'in', 'both', 'rising');
const button8 = new Gpio(0, 'in', 'both', 'rising');
const button9 = new Gpio(7, 'in', 'both', 'rising');
const buttonPlay = new Gpio(21, 'in', 'both');
const buttonStop = new Gpio(20, 'in', 'both');
const buttonRecord = new Gpio(26, 'in', 'both');

//Main function for white buttons depending on mode
function buttonFunctions(led, x) {
    if (recordDetailMode == 1) {
        blinkHelper.blinkEnd(ledRecord);
        blinkHelper.blinkEndLeds();
        led.writeSync(1);
        if (recordMode == 1){
            httpHelper.recordScene(led, x, ledsFunction, ledRecord);
        }
        else if (recordMode == 2) {
            httpHelper.recordScene(led, (x + 9), ledsFunction, ledRecord);
        }
        else if (recordMode == 3) {
            httpHelper.recordScene(led, (x + 18), ledsFunction, ledRecord);
        }
        ledRecord.writeSync(1);
    }
    if (recordDetailMode == 2) {
        if (recordedLed == null) {
            blinkHelper.blinkEnd(ledRecord);
            blinkHelper.blinkEndLeds();
            if (recordMode == 1){
                httpHelper.recordSceneMultiple(led, x, ledRecord);
            }
            else if (recordMode == 2) {
                httpHelper.recordSceneMultiple(led, (x + 9), ledRecord);
            }
            else if (recordMode == 3) {
                httpHelper.recordSceneMultiple(led, (x + 18), ledRecord);
            }
            ledRecord.writeSync(1);
            recordedLed = led;
        }
        else if (recordedLed == led) {
            httpHelper.stopRecording(led, ledsFunction, ledRecord);
            recordedLed = null;
        }
    }
    if (playMode == 1 && playingLed == null) {
        if (buttonsInUse[x-1] == false) {
            playingLed = led;
            blinkHelper.blinkEnd(ledPlay);
            blinkHelper.blinkEndLeds();
            blinkHelper.blinkStart(playingLed);
            httpHelper.playScene(x, playingLed);
            ledPlay.writeSync(1);
        }
    }
    if (playMode == 2 && playingLed == null) {
        if (buttonsInUse[x+8] == false) {
            playingLed = led;
            blinkHelper.blinkEnd(ledPlay);
            blinkHelper.blinkEndLeds();
            blinkHelper.blinkStart(playingLed);
            httpHelper.playScene((x+9), playingLed);
            ledPlay.writeSync(1);
        }
    }
    if (playMode == 3 && playingLed == null) {
        if (buttonsInUse[x+17] == false) {
            playingLed = led;
            blinkHelper.blinkEnd(ledPlay);
            blinkHelper.blinkEndLeds();
            blinkHelper.blinkStart(playingLed);
            httpHelper.playScene((x+18), playingLed);
            ledPlay.writeSync(1);
        }
    }
    if (playMode != 0 && led == playingLed && !pauseMode){
        console.log('pause true');
        pauseMode = true;
        httpHelper.pause(pauseMode, playingLed);
    }
    else if (playMode != 0 && led == playingLed && pauseMode){
        console.log('pause false');
        pauseMode = false;
        httpHelper.pause(pauseMode, playingLed);
    }
}

//Watch Output
button1.watch((err, value) => {
    if (err) {
        throw err;
    }
    if (sys1 && value === 1) {
        sys1 = false;
        setTimeout(function(){
            sys1 = true;
        }, debounceTime2);
        buttonFunctions(led1, 1);
    }
});

button2.watch((err, value) => {
    if (err) {
        throw err;
    }
    if (sys2 && value === 1) {
        sys2 = false;
        setTimeout(function(){
            sys2 = true;
        }, debounceTime2);
        buttonFunctions(led2, 2);
    }
});

button3.watch((err, value) => {
    if (err) {
        throw err;
    }
    if (sys3 && value === 1) {
        sys3 = false;
        setTimeout(function(){
            sys3 = true;
        }, debounceTime2);
        buttonFunctions(led3, 3);
    }
});

button4.watch((err, value) => {
    if (err) {
        throw err;
    }
        if (sys4 && value === 1) {
            sys4 = false;
            setTimeout(function(){
                sys4 = true;
            }, debounceTime2);
            buttonFunctions(led4, 4);
        }
});

button5.watch((err, value) => {
    if (err) {
        throw err;
    }
    if (sys5 && value === 1) {
        sys5 = false;
        setTimeout(function(){
            sys5 = true;
        }, debounceTime2);
        buttonFunctions(led5, 5);
    }
});

button6.watch((err, value) => {
    if (err) {
        throw err;
    }
    if (sys6 && value === 1) {
        sys6 = false;
        setTimeout(function(){
            sys6 = true;
        }, debounceTime2);
        buttonFunctions(led6, 6);
    }
});

button7.watch((err, value) => {
    if (err) {
        throw err;
    }
    if (sys7 && value === 1) {
        sys7 = false;
        setTimeout(function(){
            sys7 = true;
        }, debounceTime2);
        buttonFunctions(led7, 7);
    }
});

button8.watch((err, value) => {
    if (err) {
        throw err;
    }
    if (sys8 && value === 1) {
        sys8 = false;
        setTimeout(function(){
            sys8 = true;
        }, debounceTime2);
        buttonFunctions(led8, 8);
    }
});

button9.watch((err, value) => {
    if (err) {
        throw err;
    }
    if (sys9 && value === 1) {
        sys9 = false;
        setTimeout(function(){
            sys9 = true;
        }, debounceTime2);
        buttonFunctions(led9, 9);
    }
});

function allModes() {
    if (playMode != 0 || recordMode != 0 || stopMode) {
        return true;
    }
}

//Play
buttonPlay.watch((err, value) => {
    console.log('playmode= '+playMode);
    console.log('buttonpages= '+buttonPages);
    if (err) {
        throw err;
    }
    if (value === 1 && playMode == 0 && sysPlay) {
        sysPlay = false;
        setTimeout(function(){
            sysPlay = true;
        }, debounceTime1);
        httpHelper.getPages();
        blinkHelper.blinkStart(ledPlay);
        httpHelper.getButtons(ledsFunction, 'Play1');
        blinkHelper.blinkPage(ledMain, 1);
        playMode = 1;
    }
    else if (value === 1 && (playMode == 1 && buttonPages > 1) && sysPlay) {
        sysPlay = false;
        setTimeout(function(){
            sysPlay = true;
        }, debounceTime1);
        blinkHelper.blinkEnd(ledPlay);
        blinkHelper.blinkEndLeds();
        blinkHelper.blinkStart(ledPlay);
        httpHelper.getButtons(ledsFunction, 'Play2');
        blinkHelper.blinkPage(ledMain, 2);
        playMode = 2;
    }
    else if (value === 1 && (playMode == 2 && buttonPages > 2) && sysPlay) {
        sysPlay = false;
        setTimeout(function(){
            sysPlay = true;
        }, debounceTime1);
        blinkHelper.blinkEnd(ledPlay);
        blinkHelper.blinkEndLeds();
        blinkHelper.blinkStart(ledPlay);
        httpHelper.getButtons(ledsFunction, 'Play3');
        blinkHelper.blinkPage(ledMain, 3);
        playMode = 3;
    }
    else if (value === 1 && sysPlay) {
        sysPlay = false;
        setTimeout(function(){
            sysPlay = true;
        }, debounceTime1);
        blinkHelper.blinkEnd(ledPlay);
        blinkHelper.blinkEndLeds();
        playMode = 0;
    };
});

//Stop
buttonStop.watch((err, value) => {
    if (err) {
        throw err;
    }
    if (value === 1) {
       shutdownPi(buttonStop);
    }
    if (value === 1 && sysStop && playMode != 0) {
        sysStop = false;
        setTimeout(function(){
            sysStop = true;
        }, debounceTime1);
        httpHelper.stop();
        playMode = 0;
        blinkHelper.stopLeds(ledsAll, ledStop);
    }
});

//Record
buttonRecord.watch((err, value) => {
    if (err) {
        throw err;
    }
    if (value === 1 && recordDetailMode == 0 && sysRecord) {
        sysRecord = false;
        setTimeout(function(){
            sysRecord = true;
        }, debounceTime1);
        httpHelper.getPages();
        recordMode = 1;
        recordDetailMode = 1;
        blinkHelper.blinkStart(ledRecord);
        httpHelper.getButtons(ledsFunction, 'Record1');
        blinkHelper.blinkPage(ledMain, 1);
    }
    else if (value === 1 && recordDetailMode == 1 && recordMode == 1 && sysRecord) {
        sysRecord = false;
        setTimeout(function(){
            sysRecord = true;
        }, debounceTime1);
        blinkHelper.blinkEnd(ledRecord);
        blinkHelper.blinkEndLeds();
        recordMode = 1;
        recordDetailMode = 2;
        blinkHelper.blinkFastStart(ledRecord);
        httpHelper.getButtons(ledsFunction, 'Record1');
        blinkHelper.blinkPage(ledMain, 1);
    }
    else if (value === 1 && recordDetailMode == 2 && recordMode == 1 && sysRecord && buttonPages > 1) {
        sysRecord = false;
        setTimeout(function(){
            sysRecord = true;
        }, debounceTime1);
        blinkHelper.blinkEnd(ledRecord);
        blinkHelper.blinkEndLeds();
        recordMode = 2;
        recordDetailMode = 1;
        blinkHelper.blinkStart(ledRecord);
        httpHelper.getButtons(ledsFunction, 'Record2');
        blinkHelper.blinkPage(ledMain, 2);
    }
    else if (value === 1 && recordDetailMode == 1 && recordMode == 2 && sysRecord && buttonPages > 1) {
        sysRecord = false;
        setTimeout(function(){
            sysRecord = true;
        }, debounceTime1);
        blinkHelper.blinkEnd(ledRecord);
        blinkHelper.blinkEndLeds();
        recordMode = 2;
        recordDetailMode = 2;
        blinkHelper.blinkFastStart(ledRecord);
        httpHelper.getButtons(ledsFunction, 'Record2');
        blinkHelper.blinkPage(ledMain, 2);
    }
    else if (value === 1 && recordDetailMode == 2 && recordMode == 2 && sysRecord && buttonPages > 2) {
        sysRecord = false;
        setTimeout(function(){
            sysRecord = true;
        }, debounceTime1);
        blinkHelper.blinkEnd(ledRecord);
        blinkHelper.blinkEndLeds();
        recordMode = 3;
        recordDetailMode = 1;
        blinkHelper.blinkStart(ledRecord);
        httpHelper.getButtons(ledsFunction, 'Record3');
        blinkHelper.blinkPage(ledMain, 3);
    }
    else if (value === 1 && recordDetailMode == 1 && recordMode == 3 && sysRecord && buttonPages > 2) {
        sysRecord = false;
        setTimeout(function(){
            sysRecord = true;
        }, debounceTime1);
        blinkHelper.blinkEnd(ledRecord);
        blinkHelper.blinkEndLeds();
        recordMode = 3;
        recordDetailMode = 2;
        blinkHelper.blinkFastStart(ledRecord);
        httpHelper.getButtons(ledsFunction, 'Record3');
        blinkHelper.blinkPage(ledMain, 3);
    }
    else if (value === 1 && sysRecord) {
        sysRecord = false;
        setTimeout(function(){
            sysRecord = true;
        }, debounceTime1);
        recordMode = 0;
        recordDetailMode = 0;
        blinkHelper.blinkEnd(ledRecord);
        blinkHelper.blinkEndLeds();
        recordMode = false;
    };
});

//Shutdown the device
function shutdownPi(button){
    ledStop.writeSync(1);
    var i = 0;
    var interval = setInterval(function () {
        if (button.readSync() == 1) {
            i++;
            if (i == 50){
                blinkHelper.blinkConfirm(ledStop);
                blinkHelper.blinkConfirm(ledPlay);
                blinkHelper.blinkConfirm(ledRecord);
                console.log('shutdown now......');
                shutdown.shutdown(function(output){
                    console.log(output);
                });
            }
        }
        else {
            i = 0;
            ledStop.writeSync(0);
            clear();
        }
    },100);

    function clear(){
        clearInterval(interval);
    }
}

//Release all GPIO's
process.on('SIGINT', _ => {
    led1.unexport();
    led2.unexport();
    led3.unexport();
    led4.unexport();
    led5.unexport();
    led6.unexport();
    led7.unexport();
    led8.unexport();
    led9.unexport();
    ledStop.unexport();
    ledPlay.unexport();
    ledRecord.unexport();

    button1.unexport();
    button2.unexport();
    button3.unexport();
    button4.unexport();
    button5.unexport();
    button6.unexport();
    button7.unexport();
    button8.unexport();
    button9.unexport();
    buttonStop.unexport();
    buttonPlay.unexport();
    buttonRecord.unexport();
});

fan.writeSync(0);

function setButtonsInUse(buttons){
    buttonsInUse = buttons;
}

function setPlayingLed(led){
    playingLed = led;
}

function setPages(pages){
    buttonPages = pages;
}

//Startup sequence
looper.loopInit(ledsAll);

module.exports.setButtonsInUse = setButtonsInUse;
module.exports.setPlayingLed = setPlayingLed;
module.exports.setPages = setPages;
