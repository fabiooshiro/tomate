modules = {

	'jasmine-html'{
		resource url: 'js/jasmine.js', disposition: 'head'
		resource url: 'js/jasmine-html.js', disposition: 'head'
		resource url: 'css/jasmine.css', disposition: 'head'
	}

	'testcode-descriptor-reader'{
		dependsOn 'jquery'
		resource url: 'js/testcode-descriptor-reader.js', disposition: 'head'
	}

	tomate{
		dependsOn 'jasmine-html, testcode-descriptor-reader'
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

	"bootstrap-full"{
		resource url: 'js/bootstrap.min.js'
		resource url: 'css/bootstrap.min.css'
		resource url: 'css/bootstrap-responsive.min.css'
	}

}
