<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8"/>
	<title>Menu</title>
	<script type="text/javascript">
	var config = {
		contextPath: "${request.contextPath}"
	};	
	function refreshMenu(){
		parent.editor.refreshMenu();
	}
	</script>
	<r:require module="tomate-menu" />
	<r:layoutResources />
</head>
<body>
	<div>Click 2 run <a href="javascript: refreshMenu()">Refresh Menu</a></div>
	<div id="suitePlace"></div>
	<r:layoutResources />
</body>