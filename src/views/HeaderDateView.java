package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import utils.AppColors;
import utils.AppFonts;

public class HeaderDateView extends JPanel{
    private static final String[] STEP_NAMES = {"Servicios", "Barbero", "Fecha y hora", "Confirmar"};
	private static final String[] PAGE_TITLES = {
        "Seleccionar servicios",
        "Seleccionar barbero",
        "Seleccionar fecha y hora",
        "Confirmar cita"
	};
	
	private JPanel stepPanel;
    private JLabel pageTitleLabel;
	private int currentStep;
	
	public HeaderDateView() {
		this.currentStep = 0;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(AppColors.PANEL);
        setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, new Color(30, 35, 53)),
            new EmptyBorder(20, 28, 16, 28)
        ));

        stepPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        stepPanel.setOpaque(false);
        stepPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        pageTitleLabel = new JLabel();
        pageTitleLabel.setFont(AppFonts.bold(22));
        pageTitleLabel.setForeground(AppColors.TEXT_LIGHT);
        pageTitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        pageTitleLabel.setBorder(new EmptyBorder(6, 0, 0, 0));

        add(stepPanel);
        add(pageTitleLabel);

        setStep(0);
	}
	
	public void setStep(int step) {
        this.currentStep = step;
        updateStepPanel();
        if (step >= 0 && step < PAGE_TITLES.length) {
            pageTitleLabel.setText(PAGE_TITLES[step]);
        }
    }
	
	private void updateStepPanel() {
		stepPanel.removeAll();

        for (int i = 0; i < STEP_NAMES.length; i++) {
            if (i > 0) {
                JLabel arrow = new JLabel("\u203A");
                arrow.setFont(AppFonts.bold(14));
                arrow.setForeground(new Color(75, 83, 114));
                stepPanel.add(arrow);
            }

            JLabel stepLabel = new JLabel(STEP_NAMES[i]);
            stepLabel.setFont(AppFonts.regular(11));

            if (i == currentStep) {
                stepLabel.setForeground(AppColors.YELLOW);
            } else if (i < currentStep) {
                stepLabel.setForeground(new Color(107, 116, 153));
            } else {
                stepLabel.setForeground(new Color(75, 83, 114));
            }

            stepPanel.add(stepLabel);
        }

        stepPanel.revalidate();
        stepPanel.repaint();
    }
}
