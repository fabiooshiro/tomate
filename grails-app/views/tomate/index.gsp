<!DOCTYPE html>
<html>
<head>
	<title>Welcome</title>
	<script type="text/javascript">
		function openTomate(){
			window.open('${createLink(controller: "tomate", action: "ide")}', 'page','toolbar=no,location=no,status=no,menubar=yes,scrollbars=no,resizable=no,width=700,height=500');  
		}
	</script>
</head>
<body>
	<h1>Tomate</h1>
	<p><a href="javascript: openTomate()">Start tomate here (IDE mode)</a></p>

	<h2>Runner Mode</h2>
	<div>
		<g:if test="${fileList}">
			<ul>
				<g:each in="${fileList}" var="fileName" >
					<li><g:link action="runner" id="${fileName}">${fileName}.js</g:link></li>
				</g:each>
			</ul>
		</g:if>
		<g:else>
			No tests found.
		</g:else>
	</div>

</body>
</html>