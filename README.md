# Tomate

Tomate is a grails plugin for javascript testing.
Using jasmine.js and ace.js

## Target

1. Export results to CI
2. Smooth learning curve
3. Code and Run (not code compile wait Zzzz.. and run)
4. Browser powered debug tools. eg. firebug, chrome
5. Some page navigation

Sample project is 'how-to-tomate'.

## QuickStart

```sh
git clone git://github.com/fabiooshiro/tomate.git
git clone git://github.com/fabiooshiro/how-to-tomate.git
cd how-to-tomate
grails run-app
```

Open http://localhost:8080/yourApp/tomate/welcome

## Continuous Integration - CI

There is a example using phantomjs in how-to-tomate
```sh
phantomjs phantomscript.js
```
