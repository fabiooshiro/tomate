import java.lang.StringBuffer

includeTargets << grailsScript("_GrailsRun")

target('default': "Runs project and tomate") {
	depends(checkVersion, configureProxy, packageApp, parseArguments)
	runApp()
    println "Starting phantom"
    def sout = new StringBuffer(), serr = new StringBuffer()
	def proc
	try{
		proc = 'phantomjs phantomscript.js'.execute()
	}catch(Throwable e){
		//TODO: colocar isso no Config
		proc = '/bamboo/home/opt/phantomjs-1.7.0-linux-x86_64/bin/phantomjs phantomscript.js'.execute()
	}
	proc.consumeProcessOutput(sout, serr)
	proc.waitFor()
	println "out> $sout err> $serr"

	println "stopping server..."
    stopServer()
	if (proc.exitValue()) {
		println "Tomate tests fail..."
		return 1
	}
	return 0
}
