package tomate.interceptors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class WebDriverInterceptor {
    
    boolean driverChanged
    boolean paused
    long delay = 0
    
    private beforeClosures = []
    
    def beforeCall(Closure cls){
        beforeClosures.add(cls)
    }
    
    def changeDriver(){
        if(!driverChanged){
            driverChanged = true
            def oldGet = WebDriver.metaClass.getMetaMethod("get", [String] as Class[])
            WebDriver.metaClass.get = { String p ->
                beforeClosures.each{ Closure cls -> cls('get', [p]) }
                oldGet.invoke(delegate, p)
            }
            def oldSetSelected = WebDriver.metaClass.getMetaMethod("setSelected")
            WebDriver.metaClass.setSelected = {
                oldSetSelected.invoke(delegate)
            }
            def oldFind = WebDriver.metaClass.getMetaMethod("findElement", [By] as Class[])
            WebDriver.metaClass.findElement = { By p ->
                if(delay>0){
                    println('Waiting ' + delay + "ms to execute find element " + p)
                    sleep(delay)
                }
                while(paused){
                    sleep(1000)
                }
                oldFind.invoke(delegate, p)
            }
            def oldSubmit = WebElement.metaClass.getMetaMethod("submit")
            WebElement.metaClass.submit = {
                oldSubmit.invoke(delegate)
            }
            def oldClick = WebElement.metaClass.getMetaMethod('click')
            WebElement.metaClass.click = {
                oldClick.invoke(delegate)
            }
            def oldSendKeys = WebElement.metaClass.getMetaMethod('sendKeys')
            WebElement.metaClass.sendKeys = {String cs ->
                delegate.sendKeys(cs as CharSequence[])
            }
        }
    }
    
}
