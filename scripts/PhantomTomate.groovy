import java.lang.StringBuffer
import java.util.concurrent.*

includeTargets << grailsScript("_GrailsRun")

target('default': "Runs project and tomate") {
	depends(checkVersion, configureProxy, packageApp, parseArguments)
	def maxPhantomInstances = 1
	def serverUrl
	if (!argsMap.tomateUrl){
		if (argsMap.https) {
	        runAppHttps()
	    }
	    else {
	        runApp()
	    }
	    startPluginScanner()
	    serverUrl = "http://${serverHost ?: 'localhost'}:${serverPort}$serverContextPath"
	}else{
		serverUrl = argsMap.tomateUrl
	}
	println "Starting phantom ${tomatePluginDir}"
    def testRunner = { jsTestFile ->
    	println "Tomate running ${jsTestFile} ..."

	    def sout = new StringBuffer(), serr = new StringBuffer()
		def proc
		try{
			if(config.tomate.auth){
				println "Using tomate.auth config ${config.tomate.auth}"
				def authParams = []
				config.tomate.auth.each{ k, v ->
					authParams.add(k)
					authParams.add(v)
				}
				def shCommand = 'phantomjs ' + tomatePluginDir + '/phantomscript.js ' + serverUrl + ' ' + jsTestFile + ' ' + (authParams.join(' '))
				proc = shCommand.execute()
			}else{
				proc = ('phantomjs ' + tomatePluginDir + '/phantomscript.js ' + serverUrl + ' ' + jsTestFile).execute()
			}
		}catch(Throwable e){
			println "Fail: make sure that phantomjs is installed. Error: ${e.message}."
			e.printStackTrace()
		}
		proc.consumeProcessOutput(sout, serr)
		proc.waitFor()
		println "out> $sout err> $serr"
		if (proc.exitValue()) {
			println "Tomate '${jsTestFile}' tests fail..."
			return 1
		}else{
			println "Tomate ${jsTestFile} passed."
			return 0	
		}
	}

	def ls = []
	def dir = new File('web-app/js/tests').eachFile{
    	ls.add(it.name)
    }
    ls.sort()

	def pool = Executors.newFixedThreadPool(maxPhantomInstances)
    def future = { c -> pool.submit(c as Callable) }
    def futures = []
    def exitVal = 0

    def startTime = System.currentTimeMillis()
    ls.each{ jsTestFileName ->
		futures.add(
			future{
				testRunner(jsTestFileName)
			}
		)
    }

    futures.each{
    	try {
    		println "waiting..."
    		def exitCode = it.get(5, TimeUnit.MINUTES)
	    	if(exitCode != 0){
				exitVal = 1
	    	}
    	}catch(TimeoutException e){
    		println "TLE 5m!"
    		it.cancel(true)
    		exitVal = 1
    	}
    }

    println "Tomate done in ${System.currentTimeMillis() - startTime}ms."
    if (!argsMap.tomateUrl){
		println "stopping server..."
    	stopServer()
	}
	return exitVal
}
