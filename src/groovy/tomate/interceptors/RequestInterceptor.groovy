package tomate.interceptors

import org.springframework.web.multipart.MultipartRequest
import javax.servlet.http.HttpServletRequest
import org.springframework.mock.web.MockMultipartFile

class RequestInterceptor{

    /**
     * getFile interceptor
     * @param File resourcesDir a directory to store test files
     */
	def modifyGetFile(resourcesDir){

        def originalMethod = MultipartRequest.metaClass.getMetaMethod("getFile", [String] as Class[])

        HttpServletRequest.metaClass.getFile = { String fileName ->
            def fileNameValue = delegate.getParameter("tomate_file_${fileName}")
            if(fileNameValue){
                def file = new File(resourcesDir, fileNameValue)
                println "requisitando ${file.getCanonicalPath()}"
                return new MockMultipartFile(fileNameValue, fileNameValue, null, file.newInputStream())
            }else{
                if(delegate instanceof MultipartRequest)
                return originalMethod.invoke(delegate, fileName)
            }
        }
	}

}
