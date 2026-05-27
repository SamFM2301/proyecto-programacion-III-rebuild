package views;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import controllers.SideMenuController;
import utils.AppColors;
import utils.AppFonts;
import utils.Session;

public class SideDateView  extends JPanel {
	
	private JLabel serviceNameLabel;
    private JLabel servicePriceLabel;
    private JLabel serviceSubLabel;
    private JLabel totalLabel;
    private JPanel selectionPanel;
    private JPanel emptyPanel;
    private JPanel actionPanel;
    private JButton actionButton;
    private JLabel dateInfoLabel;
    private JLabel timeInfoLabel;
    private JPanel infoPanel;
	
	public SideDateView() {
		
	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    setPreferredSize(new Dimension(240, 0));
	    setMinimumSize(new Dimension(240, 0));
	    setMaximumSize(new Dimension(240, Integer.MAX_VALUE));
	    setBackground(new Color(18, 21, 33));
	    setBorder(new CompoundBorder(
	        new MatteBorder(0, 1, 0, 0, new Color(30, 35, 53)),
	        new EmptyBorder(20, 18, 20, 18)
	    	));
	}
	    
	    
	    
    private JPanel buildActionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(240, 50));

        actionButton = new JButton("Continuar");
        actionButton.setFont(AppFonts.bold(13));
        actionButton.setFocusPainted(false);
        actionButton.setBorderPainted(false);
        actionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        actionButton.setEnabled(false);
        actionButton.setPreferredSize(new Dimension(200, 42));
        actionButton.setMaximumSize(new Dimension(200, 42));
        setButtonDisabledStyle(actionButton);

        actionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (actionButton.isEnabled()) {
                    actionButton.setBackground(AppColors.YELLOW_HOVER);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (actionButton.isEnabled()) {
                    actionButton.setBackground(AppColors.YELLOW);
                }
            }
        });
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerWrapper.setOpaque(false);
        centerWrapper.add(actionButton);
        panel.add(centerWrapper, BorderLayout.CENTER);
        return panel;
	}
    
    private void setButtonDisabledStyle(JButton btn) {
        btn.setBackground(new Color(42, 45, 66));
        btn.setForeground(new Color(75, 83, 114));
    }

}
