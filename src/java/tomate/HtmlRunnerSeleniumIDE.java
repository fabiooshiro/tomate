package tomate;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;

public class HtmlRunnerSeleniumIDE {
	
	WebDriver webDriver;
	
	public HtmlRunnerSeleniumIDE(WebDriver webDriver) {
		super();
		this.webDriver = webDriver;
	}

	private int getLineNumber(String content, int cursorPoint){
		int line = 0;
		if(cursorPoint>0){
			for(int i=0;i<cursorPoint;i++){
				if(content.charAt(i)=='\n') line++;
			}
		}
		return line;
	}
	
	public void runHtml(File file){
		int cursorPoint = 0;
		String content = "";
		String lastCommand = "";
		int trN = 0;
		try {
			content = FileUtils.readFileToString(file);
			Pattern pat = Pattern.compile("<tr>\\s*<td>(.*?)</td>\\s*<td>(.*?)</td>\\s*<td>(.*?)</td>\\s*</tr>", Pattern.DOTALL);
			Matcher mat = pat.matcher(content);
			while(mat.find()){
				trN ++;
				cursorPoint = mat.start(1);
				if(!mat.group(3).equals("")){
					lastCommand = mat.group(1) + "(" + mat.group(2) + "," + mat.group(3) + ")";
				}else if(!mat.group(2).equals("")){
					lastCommand = mat.group(1) + "(" + mat.group(2) + ")";
				}else{
					lastCommand = mat.group(1);
				}
				invokeSeleniumMethod(mat.group(1), mat.group(2), mat.group(3));
				
				if(webDriver.getTitle().equals("Grails Runtime Exception")){
					throw new RuntimeException("Erro depois de executar a linha " + getLineNumber(content, cursorPoint) + " instrucao: " + lastCommand);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Throwable e) {
			throw new RuntimeException("Erro " + lastCommand + " na linha " + getLineNumber(content, cursorPoint) + ", tr " + trN + ", arquivo " + file.getAbsolutePath(), e);
		}
	}
	
	private void invokeSeleniumMethod(String methodName, String arg1, String arg2) {
		
	}
	
}
