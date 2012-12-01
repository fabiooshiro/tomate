function getDescriptions(){

	var descriptions = [];
	var currentDescribe = {};
	function describe(desc, descFunction){
		console.log(desc);
		currentDescribe = {name: desc, its: []};
		descriptions.push(currentDescribe);
		descFunction();
	}

	function it(desc, itFunction){
		console.log("    " + desc);
		var spec = {
			name: desc,
			fullName: currentDescribe.name + ' ' + desc
		};
		currentDescribe.its.push(spec);
	}

	var testCode = $('#codeEditor').val();

	eval(testCode);

	return descriptions;
}
