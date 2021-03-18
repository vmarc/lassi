function loopInit (leds) {
    var length = leds.length + 3;
    i = 0;
    myLoop();

    function myLoop() {
        if(i < leds.length) {
            setTimeout(function () {
                leds[i].writeSync(1);
                i++;
                myLoop()
            }, 400)
        }
        if (i > 2 && i < length) {
            setTimeout(function () {
                leds[(i - 3)].writeSync(0);
                if (i >= leds.length) {
                    i++;
                    myLoop()
                }
            }, 300)
        }
    }
}

module.exports.loopInit = loopInit;

