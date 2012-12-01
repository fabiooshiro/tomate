<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8"/>
	<title>Tomate</title>
	<style type="text/css">
		#editor{
			width: 50%;
			height: 100%;
			position: absolute;
			top: 0;
			right: 0;
		}
		#menu{
			width: 50%;
			height: 50%;
			position: absolute;
			top: 0;
			left: 0;	
		}
		#runner{
			width: 50%;
			height: 50%;
			position: absolute;
			bottom: 0;
			left: 0;
		}
	</style>
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
	<iframe src="${createLink(controller: 'tomate', action: 'editor')}" name="editor" id="editor"></iframe>
	<iframe src="${createLink(controller: 'tomate', action: 'menu')}" name="menu" id="menu" ></iframe>
	<iframe src="${createLink(controller: 'tomate', action: 'runner')}" name="runner" id="runner" ></iframe>

	<r:layoutResources />
</body>
</html>