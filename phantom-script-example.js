
var page = require('webpage').create();

// configure your test script
var fileName = 'bruno.js';

// the name of your app
var aplication = 'how-to-tomate';

console.log("Starting test");
window.setInterval(function(){
    var duration = page.evaluate(function () {
        return $('.duration').text();
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
            console.log("All tests passed.");
            phantom.exit();
        }else{
            var txtError = page.evaluate(function(){
                return $('#details').text();
            });
            console.log("Fail...");
            console.log(txtError);
            phantom.exit(1);
        }
    }
}, 500);

page.open('http://localhost:8080/' + aplication + '/tomate/runner?fileName=' + fileName, function () {
    console.log("running some navigation ...");
});
