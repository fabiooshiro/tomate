package tomate

class TomateController {

    def welcome(){
        redirect(view: 'index')
    }

    def index() {
        def ls = getFileList()
        log.debug(ls)
        [fileList: ls]
    }

    def runner() {
        def fileName = params.fileName
        if(!fileName && params.id) fileName = params.id + '.js'
        
        [fileName: fileName]
    }

    def ide(){

    }

    /**
     * Ace window
     */
    def editor() {

    }

    def menu() { }

    private getTestsDir(){
    	def dir = new File('web-app/js/tests')
    	if(!dir.exists()) dir.mkdirs()
    	return dir
    }

    private getFileList(){
        def ls = []
        getTestsDir().eachFile{
            ls.add(it.name)
        }
        ls.sort()
        return ls
    }

    def writeFile() {
    	def file = new File(getTestsDir(), params.filename);
    	file.write(params.data)
    	render('ok')
    }

    def listFiles(){
    	def ls = getFileList()
    	render(contentType: 'text/json'){
    		ls
    	}
    }

    def readFile(){
    	def file = new File(getTestsDir(), params.filename);
    	render(file.getText())
    }
}
