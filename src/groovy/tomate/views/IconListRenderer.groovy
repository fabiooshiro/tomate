package tomate.views;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;


class IconListRenderer extends DefaultListCellRenderer{
	
	def breakPoints
	
	public IconListRenderer(breakPoints){
		this.breakPoints = breakPoints
	}
	
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		// Get the renderer component from parent class
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		if(breakPoints.contains("${index}")){
			// Get icon to use for the list item value
			java.net.URL imgURL = getClass().getResource("iconPause.png");
			Icon icon = new ImageIcon(imgURL,  "Pause");
			
			// Set icon to display for value
			label.setIcon(icon);
		}
		
		return label;
	}
}