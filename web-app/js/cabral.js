

/**
 * Cabral the Navigator
 * @constructor
 */
var Cabral = function(){

	var _config = typeof(config) == 'undefined' ? {contextPath: ''} : config;

	var win = 'appFrame';
	var self = this;
	function onPageChange(){
		getWin().confirm = function(){return true};
	}

	/**
	   @param {string} frameName - The frame or iframe name for cabral manipulation
	 */
	this.setFrameName = function(anFrameName){
		win = anFrameName;
	};

	function getWin(){
		if(window.parent.window.opener){
			return window.parent.window.opener;
		}else{
			return window[win];
		}
	}

	function callCallBack(callback, args){
		var tid = setInterval( function () {
		    if ( getWin().document.readyState !== 'complete' ) return;
		    clearInterval( tid );

		    if(typeof(getWin()['$']) != 'undefined'){
				callback(getWin().$, args);	
			}else{
				console.log('No jQuery found...');
				callback(getWin(), args);
			}
		}, 100 );
	}

	/**
	 * ref http://blog.stchur.com/2010/01/15/programmatically-clicking-a-link-in-javascript/
	 */
	function actuateLink(link){
		if(!link){
			throw 'Undefined link';
		}
		var allowDefaultAction = true;
	      
	   	if (link.click){
			link.click();
			return;
		}else if (getWin().document.createEvent){
			var e = getWin().document.createEvent('MouseEvents');
			e.initEvent(
				'click'     // event type
				,true      // can bubble?
				,true      // cancelable?
			);
			allowDefaultAction = link.dispatchEvent(e);
		}
		if (allowDefaultAction){
			var f = getWin().document.createElement('form');
			f.action = link.href;
			getWin().document.body.appendChild(f);
			f.submit();
		}
	}

	/**
	 * Intercepted by TomateGrailsPlugin.groovy
	 */
	function defFillFile(el, fileName){
		var name = el.getAttribute('name')
		var parent = el.parentNode;
		var input = getWin().document.createElement('input');
		input.setAttribute('name', 'tomate_file_' + name);
		input.setAttribute('value', fileName);
		input.setAttribute('type', 'hidden');
		parent.appendChild(input);
	}

	var regexComparator = function(uri){
		return uri.exec(getWin().location.href);
	};

	var endsWithComparator = function(uri){
		var local = getWin().location.href.substr(uri.length * -1);
		return local == uri;
	};

	var waitForUrl = function(uri, comparator, callback){
		var loadListener = function(){
			var t;
			if(t = comparator(uri)){
				onPageChange();
				callCallBack(callback, t);
			}else{
				setTimeout(loadListener, 250);
			}
		}
		setTimeout(loadListener, 250);
	};

	/**
	 * @param {string} uri - the page to go. eg. '/book/create'
	 * @param {function} callback - callback function
	 * @example 
	 * cabral.navigateTo('/book/create', function($){
	 *     // $ is a jQuery if present
	 *     // or is a window
	 * });
	 */
	this.navigateTo = function(uri, callback){
		if(endsWithComparator(uri)){
			callCallBack(callback, true);
		}else{
			getWin().location.href = _config.contextPath + uri;
			waitForUrl(uri, endsWithComparator, callback);	
		}
	};

	/**
	 * alias to navigateTo
	 */
	this.go = this.navigateTo

	/**
	 * Wait for a page to load.
	 * @param {string or regex} uri - the URI to wait for
	 * @param {function} callback - will be called when page load
	 * @example
	 * &lt;form action="/book/save" />
	 *     &lt;input type="submit" id="create" />
	 * &lt;/form>
	 *
	 * $('#create').click();
	 * cabral.waitFor('/book/save', function($){
	 *     // ...
	 * });
	 */
	this.waitFor = function(uri, callback){
		waitForUrl(uri, (typeof(uri) == 'string' ? endsWithComparator : regexComparator), callback);
	};

	/**
     * Simulate a click &lt;a href="somePage.html">click here&lt;/a>
	 * @param {string} linkText - eg. click here
	 * @param {function} callback - Fired on page load
	 * @example
	 * cabral.clickLink('click here', function($){
     *     // evaluate your page
	 *     var message = $('.message').text();
	 * });
	 */
	this.clickLink = function(){
		if(arguments.length == 2){
			var links = getWin().document.getElementsByTagName('a');
			for(var i = 0; i < links.length; i++){
				if(links[i].innerHTML == arguments[0]){
					actuateLink(links[i]);
					self.waitFor(links[i].getAttribute('href'), arguments[1]);
					return;
				}
			}
			console.log("Link not found with text '" + arguments[0] + "'.");
			throw new Error("Link not found with text '" + arguments[0] + "'.");
		}else if(arguments.length == 1){
			actuateLink(arguments[0]);
		}
	};

	/**
	 * Pretend to fill a input type file.<br>
	 * The javascript can't change a value of an input with type "file".<br>
	 * So Cabral creates a hidden field to send your file name value.<br>
	 * This value will be intercepted by TomateGrailsPlugin.groovy 
	 * with a modified version of request.getFile('yourfile.txt') 
	 * that get that file from &lt;yourapp>/test/resources/yourfile.txt
	 * @param {element} el - input file tag
	 * @param {string} fileName - name of your file
	 * @example
	 * &lt;input type="file" id="myfile" />
	 *
	 * var el = $('#myfile')[0];
	 * cabral.fillFile(el, 'yourfile.txt') ;
	 */
	this.fillFile = function(el, fileName){
		defFillFile(el, fileName);
	}
}

var browser = new Cabral();
var cabral = browser; // para manter a compatibilidade, deprecated
