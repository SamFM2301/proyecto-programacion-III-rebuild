package views;

import java.awt.*;

import javax.swing.*;

import controllers.NewDateController;
import utils.AppColors;

public class NewDateView extends JPanel{

	private NewDateController controller;
	
	public NewDateView(NewDateController controller) {
		this.controller = controller;
		
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(1030, 70));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        setBackground(AppColors.PANEL);

        add(new HeaderDateView(), BorderLayout.NORTH);
        add(new SideDateView(), BorderLayout.EAST);
        
        setVisible(true);
	}
	
}
