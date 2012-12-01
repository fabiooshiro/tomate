var cabral = new function(){

	function getWin(){
		return window.parent.window.opener;
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
				callback(getWin().$, t);
			}else{
				console.log("uri " + uri + " nao casa com " + getWin().location.href);
				setTimeout(loadListener, 250);
			}
		}
		setTimeout(loadListener, 250);
	};

	this.navigateTo = function(uri, callback){
		getWin().location.href = config.contextPath + uri;
		waitForUrl(uri, endsWithComparator, callback);
	};

	this.waitFor = function(uri, callback){
		waitForUrl(uri, (typeof(uri) == 'string' ? endsWithComparator : regexComparator), callback);
	};
};

