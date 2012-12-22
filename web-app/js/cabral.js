var cabral = new function(){

	function onPageChange(){
		getWin().confirm = function(){return true};
	}

	function getWin(){
		if(window.parent.window.opener){
			return window.parent.window.opener;	
		}else{
			return window.appFrame;
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

	this.navigateTo = function(uri, callback){
		if(endsWithComparator(uri)){
			callCallBack(callback, true);
			
		}else{
			getWin().location.href = config.contextPath + uri;
			waitForUrl(uri, endsWithComparator, callback);	
		}
	};

	this.waitFor = function(uri, callback){
		waitForUrl(uri, (typeof(uri) == 'string' ? endsWithComparator : regexComparator), callback);
	};

	this.clickLink = actuateLink;
};

