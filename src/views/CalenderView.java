package views;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controllers.CalenderController;
import utils.AppColors;

public class CalenderView extends JPanel{
	private CalenderController controller;
	
	public CalenderView(CalenderController controller) {
		this.controller = controller;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 0));
        setMinimumSize(new Dimension(250, 0));
        setMaximumSize(new Dimension(250, Integer.MAX_VALUE));
        setBackground(AppColors.BACKGROUND);
        
        add(createHeaderPanel());
        
        setVisible(true);
	}
	
	private JPanel createHeaderPanel() {
		JPanel panel = new JPanel();
		
		JLabel label = new JLabel("Hola mundo!");
		
		panel.add(label);
		
		return panel;
	}
}
