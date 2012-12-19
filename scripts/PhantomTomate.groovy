import java.lang.StringBuffer

includeTargets << grailsScript("_GrailsRun")

target('default': "Runs project and tomate") {
	depends(checkVersion, configureProxy, packageApp, parseArguments)
	runApp()
    println "Starting phantom ${tomatePluginDir}"
    def serverUrl = "http://${serverHost ?: 'localhost'}:${serverPort}$serverContextPath"

    def testRunner = { jsTestFile ->
    	println "Running ${jsTestFile} ..."

	    def sout = new StringBuffer(), serr = new StringBuffer()
		def proc
		try{
			proc = ('phantomjs ' + tomatePluginDir + '/phantomscript.js ' + serverUrl + ' ' + jsTestFile).execute()
		}catch(Throwable e){
			println "Fail: make sure that phantomjs is installed."
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

    def exitVal = 0
    ls.each{
    	def exitCode = testRunner(it)
    	if(exitCode != 0){
			exitVal = 1
    	}
    }
	println "stopping server..."
    stopServer()
	return exitVal
}
