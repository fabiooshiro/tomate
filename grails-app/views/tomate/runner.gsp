<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8"/>
	<title>Tomate Runner</title>
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

	<g:if test="${fileName}">
		<script type="text/javascript" src="${request.contextPath}/js/tests/${fileName}?t=${new Date().getTime()}"></script>
		<iframe src="about:blank" name="appFrame" width="100%" height="968"></iframe>
	</g:if>
	<script type="text/javascript">
		<g:if test="${fileName == null }">
			eval(parent.editor.codeEditor.getValue());
		</g:if>

  		var jasmineEnv = jasmine.getEnv();
  		jasmineEnv.updateInterval = 250;
  		var htmlReporter = new jasmine.HtmlReporter();
  		jasmineEnv.addReporter(htmlReporter);

  		// aqui entra o filtro do teste
  		jasmineEnv.specFilter = function(spec) {
  			var res = htmlReporter.specFilter(spec);
  			if(res){
  				console.log("Running " + spec.description);
        	}
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