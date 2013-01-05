# Tomate

Tomate is a <a href="http://grails.org">Grails</a> plugin for javascript testing.
Using <a href="http://pivotal.github.com/jasmine/">jasmine.js</a> and ace.js

## Target

1. Export results to CI
2. Smooth learning curve (<a href="http://jquery.com/">jQuery</a> and <a href="http://pivotal.github.com/jasmine/">Jasmine</a>)
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

Open <a href="http://localhost:8080/how-to-tomate/tomate/welcome">http://localhost:8080/how-to-tomate/tomate/welcome</a>

## Continuous Integration - CI

There is an example using phantomjs in how-to-tomate
```sh
phantomjs phantomscript.js
```
or 
```sh
grails phantom-tomate
```
