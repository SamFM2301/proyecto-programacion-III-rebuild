package views;

import java.awt.*;

import javax.swing.*;

import controllers.NewDateController;
import utils.AppColors;

public class NewDateView extends JPanel{

	private NewDateController controller;
	
	public NewDateView(NewDateController controller) {
		this.controller = controller;
		
		setLayout(new FlowLayout());
        setPreferredSize(new Dimension(250, 0));
        setMinimumSize(new Dimension(250, 0));
        setMaximumSize(new Dimension(250, Integer.MAX_VALUE));
        setBackground(AppColors.PANEL);

        JLabel label = new JLabel("HOLA MUNDO!");
        add(label);
        
        setVisible(true);
	}
	
}
