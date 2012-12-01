//tomate-editor.js
$(function(){
	//var code = $('#testCodeHolder').text();
	//$('#codeEditor').val(code);
	parent.jQeditor = $;
});

function refreshMenu(){
	var menuPlace = parent.menu.$('#suitePlace');
	menuPlace.html('');

	function createSpecItem(specIt){
		var res = $('<div>');
		var link = $('<a style="padding-left: 10px">')
			.text(specIt.name)
			.attr("href", config.contextPath + '/tomate/runner?spec=' + specIt.fullName)
			.attr("target", "runner")
			;
		res.append(link);
		return res;
	}

	function createDescriptionItem(description){
		var res = $('<div>');
		var name = $("<a>").text(description.name)
			.attr("href", config.contextPath + '/tomate/runner?spec=' + description.name)
			.attr("target", "runner");
		res.append(name);
		for(var i = 0; i < description.its.length; i++){
			res.append(createSpecItem(description.its[i]));
		}
		return res;
	}
	
	var descriptions = getDescriptions();
	for(var i = 0; i < descriptions.length; i++){
		menuPlace.append(createDescriptionItem(descriptions[i]));	
	}
	
}
