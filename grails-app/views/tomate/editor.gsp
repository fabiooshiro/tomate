<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8"/>
	<title>Editor</title>
	<style type="text/css" media="screen">
	    #codeEditor { 
	        position: absolute;
	        top: 0;
	        right: 0;
	        bottom: 0;
	        left: 0;
	        width: 100%;
	    }
	</style>
	<script type="text/javascript">
	var config = {
		contextPath: "${request.contextPath}"
	};	
	</script>
	<script src="http://d1n0x3qji82z53.cloudfront.net/src-min-noconflict/ace.js" type="text/javascript" charset="utf-8"></script>
	<r:require module="tomate-editor" />
	<r:layoutResources />
</head>
<body>
	<div id="codeEditor" name="codeEditor"></div>
	<div id="filesPlace"></div>

	<div style="display: none" id="sampleCode">
	// isso eh um teste
	describe("How to", function() {
		
		it("create a book", function() {
			var done = false;
			var jQuery, matches;
			runs(function(){

				// go to book create, so dont forget the language param
				cabral.navigateTo('/book/create?lang=pt_BR', function($){

					//make sure that you are in the correct location
					expect($('title').text()).toBe("Criar Book");

					// Fill fields
					$('#name').val("my book");

					// submit the form
					$('#create').click();

					// wait for next page...
					cabral.waitFor(/\/book\/show\/(.*)/g, function($, m){

						// keep this things for final evaluation
						jQuery = $;
						matches = m;
						done = true;
					});
				});
			});

			// timeout to exec navigation
			waitsFor(function() {
		      	return done;
		    }, "The Value should be incremented", 3000);

			// finally execute the end verification
		    runs(function(){
		    	expect(
		    		jQuery('.message').text()
		    	).toBe('Book ' + matches[1] + ' criado');
		    });
		});

		it("list books", function(){
			var done = false;
			var jQuery;
			runs(function(){
				cabral.navigateTo('/book/list', function($){
					jQuery = $;
					done = true;
				});
			});

			waitsFor(function() {
		      	return done;
		    }, "The Value should be incremented", 3000);

		    // finally execute the end verification
		    runs(function(){
		    	expect(jQuery('title').text()).toBe("Book Listagem");
		    });
		});

		it("delete a book", function(){
			var done = false;
			var jQuery, matches;
			runs(function(){
				cabral.navigateTo('/book/list', function($){
					var aLs = $('a').filter(function(){
						return $(this).text() == 'my book';
					});

					console.log("clicando no link");
					cabral.clickLink(aLs[0]);

					cabral.waitFor(/\/book\/show\/(.*)/g, function($, m){
						$('.delete').click();
						matches = m;
						cabral.waitFor('/book/list', function($, m){
							jQuery = $;
							done = true;
						});
					});
				});
			});

			waitsFor(function() {
		      	return done;
		    }, "The Value should be incremented", 3000);

		    // finally execute the end verification
		    runs(function(){
		    	expect(jQuery('title').text()).toBe("Book Listagem");
		    	expect(jQuery('.message').text()).toBe("Book " + matches[1] + " removido");
		    });
		});
	});
	</script>
	<r:layoutResources />
</body>