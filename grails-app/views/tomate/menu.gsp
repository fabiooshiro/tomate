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
	function openFileChooser(){
		parent.editor.showFileChooser();
	}
	</script>
	<r:require module="tomate-menu" />
	<r:layoutResources />
</head>
<body>
	<div><a href="javascript: refreshMenu()">Refresh</a> <a href="javascript: openFileChooser()">Open File</a></div>
	<div>Click 2 run </div>
	<div id="suitePlace"></div>
	<r:layoutResources />
</body>