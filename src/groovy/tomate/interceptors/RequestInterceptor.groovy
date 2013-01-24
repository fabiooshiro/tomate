package tomate.interceptors

class RequestInterceptor{

	def modifyGetFile(resourcesDir){
		// getFile interceptor
        def originalMethod = org.springframework.web.multipart.MultipartRequest.metaClass.getMetaMethod("getFile", [String] as Class[])

        javax.servlet.http.HttpServletRequest.metaClass.getFile = { String fileName ->
            def fileNameValue = delegate.getParameter("tomate_file_${fileName}")
            println "fileNameValue = ${fileNameValue} for tomate_file_${fileName}"
            if(fileNameValue){
                def file = new File(resourcesDir, fileNameValue)
                return new org.springframework.mock.web.MockMultipartFile(fileNameValue, fileNameValue, null, file.newInputStream())
            }else{
                if(delegate instanceof org.springframework.web.multipart.MultipartRequest)
                return originalMethod.invoke(delegate, fileName)
            }
        }
	}


}
