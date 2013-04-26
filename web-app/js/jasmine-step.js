/**
 * Baby steps
 */
function step(description, fn, timeout){
	var done = fn.length != 1; // has done arg in fn?
	runs(function(){
		fn(function(){
			done = true;
		});
	});
	waitsFor(function(){ return done; }, 'step "' + description + '"', timeout?timeout:5000);
}
