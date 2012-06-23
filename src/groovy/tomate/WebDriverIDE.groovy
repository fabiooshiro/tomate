package tomate;

import groovy.lang.Singleton;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tomate.interceptors.WebDriverInterceptor;
import tomate.views.*;

public class WebDriverIDE{
	private long delay = 0
	private boolean paused = false
	private boolean driverChanged = false
	private WebDriverInterceptor webDriverInterceptor = new WebDriverInterceptor()
    private UiWebDriverIDE uiWebDriverIDE
    private executionClosureList = []
    
	def exec(cmd){
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(cmd);
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		def rs = ""
		String line;
		while ((line = br.readLine()) != null) {
		  rs += line + '\n'
		}
		System.out.println(rs);
		return process.waitFor()
	}
	
	public WebDriverIDE() {
		webDriverInterceptor.changeDriver()
        uiWebDriverIDE = new UiWebDriverIDE(this)
	}
    
	def executeIndex(index){
        def obj = executionClosureList.get(index)
        uiWebDriverIDE?.beginExecutionClosure(index)
        System.out.println("${index}) executando: ${obj.name}");
        obj.clos()
        uiWebDriverIDE?.endExecutionClosure(index)
    }
    
    def replaceClosure(int index, Closure closure){
        ExecutionClos execClos = executionClosureList.get(index)
        closure.delegate = execClos.clos.delegate
        execClos.clos = closure
    }
    
	def info(msg, clos){
		if(uiWebDriverIDE){
			def ex = new ExecutionClos()
			ex.clos = clos
			ex.name = executionClosureList.size() + ') ' + msg
            executionClosureList.add(ex)
			uiWebDriverIDE.addExecutionClosure(ex)
		}else{
			clos
		}
	}
	
	def waitClose(){
		while(uiWebDriverIDE?.isVisible()){
			sleep(1000)
		}
	}
    
    def shutDown(){
        uiWebDriverIDE.shutDown()
    }
    
}
