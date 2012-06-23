package tomate;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tomate.interceptors.*;

public class FunctionalTests {

    WebDriverIDE webDriverIDE
    
    @Before
    void setUp(){
        webDriverIDE = new WebDriverIDE()
    }
    
    @After
    void tearDown(){
        webDriverIDE.shutDown()
    }
    
    @Test
    public void testWebDriverIDE() {
        def closureExecuted = false
        webDriverIDE.info('execute some closure'){
            closureExecuted = true
        }
        webDriverIDE.executeIndex(0)
        assert closureExecuted
    }
    
    @Test
    public void testChangeDriver() {
        WebDriverInterceptor webDriverInterceptor = new WebDriverInterceptor()
        webDriverInterceptor.changeDriver()
        
        FakeWebDriver fakeWebDriver = new FakeWebDriver()
        def msgReceived
        webDriverInterceptor.beforeCall{ methodName, args ->
            msgReceived = "${methodName}: ${args}".toString()
        }
        fakeWebDriver.get("some string")
        assert 'get: [some string]' == msgReceived
    }
    
    @Test
    public void testReplaceClosure() {
        def closureExecuted = 0
        webDriverIDE.info('execute some closure'){
            closureExecuted = 1
        }
        
        webDriverIDE.replaceClosure(0){
            closureExecuted = 2
        }
        
        webDriverIDE.executeIndex(0)
        assert 2 == closureExecuted
    }
    
    @Test
    public void testWaitClose(){
        webDriverIDE.info('run code'){
            println "ola mundo"
        }
        webDriverIDE.waitClose()
    }
}

