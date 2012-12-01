<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8"/>
	<title>introduction.js</title>
	<script type="text/javascript">
	var config = {
		contextPath: "${request.contextPath}"
	};	
	</script>
	<r:require module="jquery" />
	<r:require module="tomate" />
	<r:layoutResources />
</head>
<body>
	<r:layoutResources />
	
	<script type="text/javascript">
		eval(parent.editor.codeEditor.getValue());

  		var jasmineEnv = jasmine.getEnv();
  		jasmineEnv.updateInterval = 250;
  		var htmlReporter = new jasmine.HtmlReporter();
  		jasmineEnv.addReporter(htmlReporter);

  		// aqui entra o filtro do teste
  		jasmineEnv.specFilter = function(spec) {
  			//console.log("spec");
  			//console.log(spec);
  			var res = htmlReporter.specFilter(spec);
  			//console.log("res");
  			console.log(res);
    		return res;
  		};

  		var currentWindowOnload = window.onload;
		window.onload = function() {
			if (currentWindowOnload) {
				currentWindowOnload();
		    }

		    //document.querySelector('.version').innerHTML = jasmineEnv.versionString();
		    execJasmine();
		};

		function execJasmine() {
			jasmineEnv.execute();
		}
	</script>
</body>