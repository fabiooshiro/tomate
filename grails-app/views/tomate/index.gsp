<!DOCTYPE html>
<html>
<head>
	<title>Welcome</title>
	<script type="text/javascript">
		function openTomate(){
			window.open('${createLink(controller: "tomate", action: "ide")}', 'page','toolbar=no,location=no,status=no,menubar=yes,scrollbars=no,resizable=no,width=700,height=500');  
		}
	</script>
	<r:require module="tomate-bootstrap" />
	<r:layoutResources />

	<style type="text/css">

      /* Sticky footer styles
      -------------------------------------------------- */

      html,
      body {
        height: 100%;
        /* The html and body elements cannot have any padding or margin. */
      }

      /* Wrapper for page content to push down footer */
      #wrap {
        min-height: 100%;
        height: auto !important;
        height: 100%;
        /* Negative indent footer by it's height */
        margin: 0 auto -60px;
      }

      #push,
      #footer {
        height: 60px;
      }
      #footer {
        background-color: #f5f5f5;
      }

      @media (max-width: 767px) {
        #footer {
          margin-left: -20px;
          margin-right: -20px;
          padding-left: 20px;
          padding-right: 20px;
        }
      }
      .container {
        width: auto;
        max-width: 680px;
      }
      .container .credit {
        margin: 20px 0;
      }

    </style>
</head>
<body>
	<div id="wrap">

      <!-- Begin page content -->
      <div class="container">
        <div class="page-header">
          <h1>TOMATE is soooo sessy!!</h1> 
          <g:each in="{0..10}">
            <img src="${resource(dir: 'images', file: 'kingjuliansmall.jpg')}"/>
          </g:each>
          
        </div>
        <p class="lead">
        	<a href="javascript: openTomate()">Start tomate here (IDE mode)</a>
        	<h1>Runner Mode:</h1>
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
        </p>
      </div>

      <p class="lead">
      	
      </p>
    </div>

    <div id="footer">
      <div class="container">
        <p class="muted credit"></p>
      </div>
    </div>


</body>
</html>