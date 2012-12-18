import java.lang.StringBuffer

includeTargets << grailsScript("_GrailsRun")

target('default': "Runs project and tomate") {
	depends(checkVersion, configureProxy, packageApp, parseArguments)
	runApp()
    println "Starting phantom"
    def sout = new StringBuffer(), serr = new StringBuffer()
	def proc = 'phantomjs phantomscript.js'.execute()
	//def proc = 'ls'.execute()
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
