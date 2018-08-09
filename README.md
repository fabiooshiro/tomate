# Tomate

Tomate is a <a href="http://grails.org">Grails</a> plugin for javascript testing.
Using <a href="https://jasmine.github.io/">jasmine.js</a> and ace.js

## Target

1. Export results to CI
2. Smooth learning curve (<a href="http://jquery.com/">jQuery</a> and <a href="https://jasmine.github.io/">Jasmine</a>)
3. Code and Run (not code compile wait Zzzz.. and run)
4. Browser powered debug tools. eg. firebug, chrome
5. Some page navigation (<a href="https://github.com/fabiooshiro/cabral">Cabral</a>)

Sample project here: '<a href="https://github.com/fabiooshiro/how-to-tomate">how-to-tomate</a>'.

## QuickStart

```sh
git clone git://github.com/fabiooshiro/tomate.git
git clone git://github.com/fabiooshiro/how-to-tomate.git
cd how-to-tomate
grails run-app

```

Open <a href="http://localhost:8080/how-to-tomate/tomate">http://localhost:8080/how-to-tomate/tomate</a>

And start coding your test!

New files will be filled with a sample code like:

```javascript
describe("Some feature", function() { // just jasmine
    it("should do something", function() { // just jasmine
        var done = false;
        var jQuery, matches;
        runs(function(){
            cabral.navigateTo('/book/create', function($){ // cabral navigation
                $('#title').val("my book title"); // just jquery
                $('#create').click(); // just jquery
                cabral.waitFor(/\/book\/show\/(.*)/g, function($, m){
                    jQuery = $; // keep last jQuery from show.gsp
                    matches = m; // if you need the id ...
                    done = true;
                });
            });
        });
        
        // jasmine timeout to exec navigation
        waitsFor(function() {
            return done;
        }, 'navigation time out', 30000);

        runs(function(){
            expect(
                jQuery('.message')[0].innerHTML
            ).toMatch(/Book .* created/g);
        });
    });
});
```

## Download and Install

This plugin isn't published at grails plugins site yet.

Now clone this project 
```sh
cd workspace
git clone git://github.com/fabiooshiro/tomate.git
```
and add this line in your file /yourgrailsproject/grails-app/conf/BuildConfig.groovy
```groovy
grails.plugin.location.tomate = "../tomate" // inline plugin
```

## Continuous Integration - CI

There is a grails script using phantomjs
```sh
grails phantom-tomate
```

### Spring Security Integration

You can configure an url to post user and pass
```groovy
// Tomate auth configuration
tomate.auth.uri2post='/j_spring_security_check'
tomate.auth.j_username = 'tester'
tomate.auth.j_password = 'tester'
```

Before every test file the tomate will post to '/j_spring_security_check' the username and password.
If you have your own auth code you can modify the params to be posted.

```groovy
// Tomate auth configuration
tomate.auth.uri2post='/user/chkpass'
tomate.auth.myUsername = 'tester'
tomate.auth.myPassword = 'tester'
```



