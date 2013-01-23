var page = require('webpage').create();
var system = require('system');

if(system.args.length < 3){
    console.log("usage phanthonjs phantomscript.js http://localhost:8080/how-to-tomate bruno.js");
    phantom.exit(1);
}

function writeXml(){
    var xml = page.evaluate(function(){
        return $('#xmlOut').val();
    });
    var fileName = page.evaluate(function(){
        return $('#xmlOut').attr('data-filename');
    });
    console.log(fileName);
    console.log(xml);
    var fs = require('fs');
    if(!fs.exists('target/test-reports')){
        fs.makeDirectory('target/test-reports');
    }
    var f = fs.open('target/test-reports/' + fileName, "w");
    f.write(xml);
    f.close();
}


//console.log(system.args);
var appUrl = system.args[1];
var fileName = system.args[2];
var authParams, authUri2post = false;
if(system.args.length > 4){
    var authArgs = [];
    for (var i = 3; (i+1) < system.args.length; i+=2) {
        var name = system.args[i];
        var value = system.args[i+1];
        if(name == 'uri2post'){
            authUri2post = value;
        }else{
            authArgs.push(name + '=' + value);
        }
    };
    authParams = authArgs.join('&');
}

page.onConsoleMessage = function (msg) {
    console.log('User page msg: ' + msg);
};

console.log("Starting test");
window.setInterval(function(){
    var duration = page.evaluate(function () {
        if(typeof($) != 'undefined'){
            return $('.duration').text();
        }else{
            return false;
        }
    });
    if(duration){
        var passed = page.evaluate(function(){
            var ok = true;
            var tests = $('.symbolSummary li');
            for(var i = 0; i < tests.length; i ++){
                if($(tests[i]).attr('class') != 'passed'){
                    ok = false;
                    break;
                }
            }
            return ok;
        });

        // get perovaz report
        writeXml();

        if(passed){
            console.log("All " + fileName + " tests passed.");
            phantom.exit();
        }else{
            console.log(fileName + " fail...");
            var txtError = page.evaluate(function(){
                var errors = [];
                var details = $('.specDetail');
                for(var i = 0; i<details.length; i++){
                    var eTxt = $(details[i]).text();
                    errors.push(eTxt);
                }
                return errors.join('\n');
            });
            console.log(txtError);
            phantom.exit(1);
        }
    }
}, 500);

var tomatePageChecked = false;

function openTomateRunner(){
    page.open(appUrl + '/tomate/runner?fileName=' + fileName, function () {
        console.log('Some page navigation...')
        if(!tomatePageChecked){
            tomatePageChecked = true;
            var title = page.evaluate(function () {
                return document.title;
            });
            console.log('Page title is ' + title);
            if(title != 'Tomate Runner'){
                console.log("Wrong tomate runner page...")
                console.log("Some spring security fail?")
                phantom.exit(1);
            }
        }
    });
}

if(authUri2post){
    var data = authParams;
    page.open(appUrl + authUri2post, 'post', data, function (status) {
        if (status !== 'success') {
            console.log('Unable to post!');
            phantom.exit(1);
        } else {
            openTomateRunner();
        }
    });
}else{
    openTomateRunner();
}
