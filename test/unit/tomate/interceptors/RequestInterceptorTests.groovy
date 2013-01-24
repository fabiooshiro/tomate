package tomate.interceptors

import org.springframework.mock.web.MockHttpServletRequest


class RequestInterceptorTests{

	void testRequestInterceptor(){
		def interceptor = new RequestInterceptor()
		interceptor.modifyGetFile(new File('test/resources'))
		def request = new MockHttpServletRequest()
		request.addParameter('tomate_file_myfile', 'myFile.txt')
		def file = request.getFile('myfile')
		assert file.inputStream.text == 'it works'
	}

}
