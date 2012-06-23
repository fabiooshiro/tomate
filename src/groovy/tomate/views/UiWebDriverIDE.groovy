package tomate.views;

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

import tomate.ExecutionClos
import tomate.interceptors.WebDriverInterceptor;
import tomate.WebDriverIDE

public class UiWebDriverIDE{
    private static long delay = 0
    private static boolean paused = false
    private static boolean driverChanged = false
    private static breakPoints = []
    
    private JFrame jFrame
    private DefaultListModel listModel = new DefaultListModel();
    private JList jList
    private th
    private JCheckBox autoPlayNext
    WebDriverIDE webDriverIDE
    
    public UiWebDriverIDE(WebDriverIDE webDriverIDE) {
        this.webDriverIDE = webDriverIDE
        jFrame = new JFrame()
        jList = new JList(listModel)
        jList.setCellRenderer(new IconListRenderer(breakPoints))
        jFrame.setLayout(new BorderLayout())
        def slider = new JSlider(0, 5000, 5000)
        slider.addChangeListener(
            new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider)e.getSource();
                    if (!source.getValueIsAdjusting()) {
                        delay = 5000 - (int)source.getValue();
                    }
                }
            }
        );
        def btnPause = new JButton(paused ? 'Play':'Pause')
        btnPause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                paused = !paused
                btnPause.setText(paused ? 'Play':'Pause')
            }
        })
        jList.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if(keyCode==10){
                    doSelected()
                }else if(keyCode == 66 /* char b */){
                    def idx = jList.getSelectedIndex();
                    if(breakPoints.contains("${idx}")){
                        breakPoints.remove("${idx}")
                    }else{
                        breakPoints.add("${idx}")
                    }
                    jList.repaint()
                }else if(keyCode == 82 /* char r */){
                    def idx = jList.getSelectedIndex();
                    //def restorer = new JDBCDatabaseRestorer();
                    //restorer.restore("test/resource/webdriverIDE-${idx}.sql")
                    //System.out.println("Estado webdriverIDE-${idx}.sql restaurado");
                    JOptionPane.showMessageDialog(jFrame, "Estado webdriverIDE-${idx}.sql restaurado");
                }else{
                    System.out.println('Nada para' + keyCode);
                }
            }
            public void keyPressed(KeyEvent e) {
            }
        })
        jList.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()>1){
                    doSelected()
                }
            }
        })
        jFrame.add(new JScrollPane(jList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER)
        autoPlayNext = new JCheckBox("Auto Play")
        def sPanel = new JPanel(new FlowLayout())
        sPanel.add(btnPause)
        sPanel.add(autoPlayNext)
        jFrame.add(sPanel, BorderLayout.SOUTH)
        jFrame.add(slider, BorderLayout.NORTH)
        jFrame.setSize(280,300)
        jFrame.setVisible(true)
    }
    
    def doSelected(){
        int index = jList.getSelectedIndex();
        try{
            th.interrupt()
        }catch(Throwable e){
            System.out.println('Cancelado...');
        }
        th = Thread.start {
            try{
                def aux = true
                while(aux){
                    if(breakPoints.contains("${index}")){
                        JOptionPane.showMessageDialog(jFrame, "Break point pressione <espaço>")
                        break;
                    }
                    webDriverIDE.executeIndex(index)
                    sleep(500)//aguarda um possivel flush
                    //def rs = exec("mysqldump -u root --opt funds -r ./test/resource/webdriverIDE-${index}.sql");
                    //System.out.println('bkp ' + index + ' ' + (rs == 0 ? 'ok': 'fail'));
                    aux = autoPlayNext.isSelected() && listModel.size() > ++index
                    jList.setSelectedIndex(index)
                }
            }catch(Throwable e){
                jFrame.setTitle('Err ' + jFrame.getTitle())
                JOptionPane.showMessageDialog(jFrame, e.message + '\n pressione <espaço>')
                e.printStackTrace()
            }
        }
    }
    
    static exec(cmd){
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
    
    def addExecutionClosure(ExecutionClos executionClosure){
        listModel.addElement(executionClosure)
    }
    
    def beginExecutionClosure(index){
        def obj = listModel.getElementAt(index)
        jFrame.setTitle("@ " + obj.name)
    }
    
    def endExecutionClosure(index){
        def obj = listModel.getElementAt(index)
        jFrame.setTitle(obj.name)
    }
    
    def isVisible(){
        jFrame.isVisible()
    }
    
    def shutDown(){
        jFrame.setVisible(false)
        jFrame.dispose()
    }
}