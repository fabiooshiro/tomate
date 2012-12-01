package tomate

class TomateController {

    def index() { }

    def runner() { }

    def editor() { }

    def menu() { }

    private getTestsDir(){
    	def dir = new File('web-app/js/tests')
    	if(!dir.exists()) dir.mkdirs()
    	return dir
    }

    def writeFile() {
    	def file = new File(getTestsDir(), params.filename);
    	file.write(params.data)
    	render('ok')
    }

    def listFiles(){
    	def ls = []
    	getTestsDir().eachFile{
    		ls.add(it.name)
    	}
    	ls.sort()
    	render(contentType: 'text/json'){
    		ls
    	}
    }

    def readFile(){
    	def file = new File(getTestsDir(), params.filename);
    	render(file.getText())
    }
}
