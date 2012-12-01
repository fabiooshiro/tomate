//tomate-editor.js
var context = {};
var codeEditor;
$(function(){
	//var code = $('#testCodeHolder').text();
	//$('#codeEditor').val(code);
	parent.jQeditor = $;
    var editor = ace.edit("codeEditor");
    editor.setTheme("ace/theme/monokai");
    editor.getSession().setMode("ace/mode/javascript");
    editor.commands.addCommand({
        name: 'writeFile',
        bindKey: {win: 'Ctrl-S',  mac: 'Command-S'},
        exec: function(editor) {
            var data = editor.getValue();
            if(!context.filename){
            	context.filename = prompt('File will be saved in web-app/js/tests');
            }
            $.post(config.contextPath + '/tomate/writeFile', {filename: context.filename, data: data}, function(res){
                console.log(res);
                if(res == 'ok'){
                	refreshMenu();
                }
            }, 'json');
        }
    });
    editor.commands.addCommand({
        name: 'readFile',
        bindKey: {win: 'Ctrl-O',  mac: 'Command-O'},
        exec: function(editor) {
        	showFileChooser();
        }
    });
    editor.commands.addCommand({
        name: 'newFile',
        bindKey: {win: 'Ctrl-N',  mac: 'Command-M'},
        exec: function(editor) {
    		createNewFile();
        }
    });
    
    codeEditor = editor;
    codeEditor.setValue("// press Command-O or Ctrl-O");
});

function createNewFile(){
	context.filename = prompt('File will be saved in web-app/js/tests');
    codeEditor.setValue($('#sampleCode').text());
    $('#codeEditor').show();
    $('#filesPlace').hide();
}

function createNewFileLink(){
	return $('<a>').click(createNewFile)
		.css('display', 'block')
		.text('New File (Command-M)')
		.attr('href', '#newFile');
}

function createFileLink(file){
	return $('<a>').click(function(){
			openFile(file);
		})
		.css('display', 'block')
		.text(file)
		.attr('href', '#openFile' + file);
}

function createCancelFileOpen(){
	return $('<a>').click(function(){
			$('#codeEditor').show();
			$('#filesPlace').hide();
		})
		.text('Cancel')
		.css('display', 'block')
		.attr('href', '#cancelFileOpen');
}

function openFile(filename){
	$.get(config.contextPath + '/tomate/readFile', {filename: filename}, function(data) {
        codeEditor.setValue(data);
        context.filename = filename;
        $('#codeEditor').show();
		$('#filesPlace').hide();
		refreshMenu();
    });
}

function showFileChooser(){
	$('#codeEditor').hide();
	$('#filesPlace').show();
	var filesPlace = $('#filesPlace');
	filesPlace.empty();
	filesPlace.append(createCancelFileOpen());
	filesPlace.append(createNewFileLink());
	$.get(config.contextPath + '/tomate/listFiles', function(data) {
		for(var i = 0; i < data.length; i++){
			console.log(data[i]);
			filesPlace.append(createFileLink(data[i]));	
		}
	});
}

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
	
	var descriptions = getDescriptions(codeEditor.getValue());
	for(var i = 0; i < descriptions.length; i++){
		menuPlace.append(createDescriptionItem(descriptions[i]));	
	}
	
}
