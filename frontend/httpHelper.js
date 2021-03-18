const url = 'http://localhost:8080/';
const fetch = require('node-fetch');
const blinkHelper = require('./blinkHelper');
const main = require('../../index');

function recordScene(led, button, ledsFunction, ledRecord) {

    console.log('recordfromhhhtp');

    (fetch(url + '/api/recordSceneSingleFrame/' + button))
        .then(res => res.text())
        .then(function (res) {
            if (res == 'true'){
                led.writeSync(0);
                blinkHelper.blinkConfirm(led);
            }
            else {
                led.writeSync(0);
                blinkHelper.blinkStart(ledRecord);
                getButtons(ledsFunction, 'Record');
            }
        })
}

function recordSceneMultiple(led, button, ledRecord) {

    console.log('recordfromhhhtp');

    (fetch(url + '/api/recordSceneMultipleFrames/' + button))
        .then(res => res.text())
        .then(function (res) {
            if (res == 'true'){
                console.log('rec started');
                led.writeSync(1);
            }
            else {
                blinkHelper.blinkStart(ledRecord);
            }
        })
}

function stopRecording(led, ledsFunction, ledRecord) {

    console.log('stoprecording');

    (fetch(url + '/api/stopRecording'))
        .then(res => res.text())
        .then(function (res) {
            if (res == 'true'){
                console.log('true recording');
                led.writeSync(0);
                blinkHelper.blinkConfirm(led);
            }
            else {
                console.log('false recording');
                led.writeSync(0);
                blinkHelper.blinkFastStart(ledRecord);
                getButtons(ledsFunction, 'Record');
            }
        })
}


function playScene(button, led) {
    (fetch(url + '/api/playscene/' + button))
        .then(res => res.text())
        .then(function (res) {
            if (res == 'true'){
                blinkHelper.blinkEndLeds();
                led.writeSync(1);
                main.setPlayingLed(null);
            }
            else {
                console.log(res);
                console.log('error');
            }
        })
}

function getButtons(leds, mode){
    fetch(url + 'api/getbuttons')
        .then(function(response){
            return response.json();
        })
        .then(function(json){
            blinkHelper.blinkSpecificLedsStart(json, leds, mode);
            main.setButtonsInUse(json);
        });
}

function stop(){
    fetch(url + 'api/stop')
        .then(res => res.text())
    console.log('sending stop request')
}

function getPages(){
    fetch(url + 'api/getPages')
        .then(function(response){
            return response.json();
        })
        .then(function(json){
            main.setPages(json);
        });
}

function pause(bool, playingLed){
    fetch(url + 'api/pause/' + bool)
        .then(res => res.text())

    if (bool){
        blinkHelper.blinkEnd(playingLed);
        blinkHelper.blinkStartSlow(playingLed);
    }
    else {
        blinkHelper.blinkEnd(playingLed);
        blinkHelper.blinkStart(playingLed);
    }
}

module.exports.recordScene = recordScene;
module.exports.recordSceneMultiple = recordSceneMultiple;
module.exports.stopRecording = stopRecording;
module.exports.playScene = playScene;
module.exports.getButtons = getButtons;
module.exports.stop = stop;
module.exports.pause = pause;
module.exports.getPages = getPages;


