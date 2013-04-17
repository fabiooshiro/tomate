(function(){

	var el;
	
	/**
	 * creates an hidden field to store xml contents
	 */
	jasmine.JUnitXmlReporter.prototype.writeFile = function(filename, text) {
		if(el){
			el.value = text;
		}
	};

	jasmine.JUnitXmlReporter.prototype.setOutput = function(outputEl){
		el = outputEl;
	};
	
})();