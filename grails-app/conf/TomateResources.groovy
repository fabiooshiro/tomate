modules = {

	'junit-reporter'{
		resource url: 'js/jasmine.junit_reporter.js'
	}

	'waiting-4u-pero'{
		dependsOn 'junit-reporter'
		resource url: 'js/tomate-junit-adapter.js'
	}

	'jasmine-html'{
		resource url: 'js/jasmine.js', disposition: 'head'
		resource url: 'js/jasmine-html.js', disposition: 'head'
		resource url: 'js/jasmine-step.js', disposition: 'head'
		resource url: 'css/jasmine.css', disposition: 'head'
	}

	'testcode-descriptor-reader'{
		dependsOn 'jquery'
		resource url: 'js/testcode-descriptor-reader.js', disposition: 'head'
	}

	tomate{
		dependsOn 'jasmine-html, testcode-descriptor-reader, waiting-4u-pero'
		resource url: 'js/cabral.js', disposition: 'head'
	}

	'tomate-menu'{
		dependsOn 'jquery'
		resource url: 'js/tomate-menu.js'
	}

	'tomate-editor'{
		dependsOn 'testcode-descriptor-reader'
		resource url: 'js/tomate-editor.js'	
	}

	"tomate-bootstrap"{
		//resource url: 'js/bootstrap.min.js'
		//resource url: 'css/bootstrap.min.css'
		//resource url: 'css/bootstrap-responsive.min.css'
	}

}
