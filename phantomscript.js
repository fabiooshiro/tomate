var page = require('webpage').create();
var system = require('system');

if(system.args.length!=3){
    console.log("usage phanthonjs phantomscript.js http://localhost:8080/how-to-tomate bruno.js");
    phantom.exit(1);
}

var appUrl = system.args[1];
var fileName = system.args[2];

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

            //ini pero vaz
            var fs = require('fs');
            var f = fs.open('htmlcode.txt', "w");
            f.write(page.content);
            f.close();
            //end pero vaz

            phantom.exit(1);
        }
    }
}, 500);

page.open(appUrl + '/tomate/runner?fileName=' + fileName, function () {
    //console.log("running some navigation ...");
});

