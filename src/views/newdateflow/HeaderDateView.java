package views.newdateflow;

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
    private JLabel lblPageTitle;
	private int currentStep;

	public HeaderDateView() {
		this.currentStep = 0;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(AppColors.PANEL);
        setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, AppColors.PANEL2),
            new EmptyBorder(20, 28, 16, 28)
        ));

        stepPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        stepPanel.setOpaque(false);
        stepPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblPageTitle = new JLabel();
        lblPageTitle.setFont(AppFonts.bold(22));
        lblPageTitle.setForeground(AppColors.TEXT_LIGHT);
        lblPageTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblPageTitle.setBorder(new EmptyBorder(6, 0, 0, 0));

        add(stepPanel);
        add(lblPageTitle);

        setStep(0);
	}

	public void setStep(int step) {
        this.currentStep = step;
        if (step >= 0 && step < PAGE_TITLES.length) {
        	lblPageTitle.setText(PAGE_TITLES[step]);
        	stepPanel.setVisible(true);
        	updateStepPanel();
        } else {
        	lblPageTitle.setText("");
        	stepPanel.setVisible(false);
        }
    }

	private void updateStepPanel() {
		stepPanel.removeAll();

        for (int i = 0; i < STEP_NAMES.length; i++) {
            if (i > 0) {
                JLabel arrow = new JLabel("\u203A");
                arrow.setFont(AppFonts.bold(14));
                arrow.setForeground(AppColors.BUTTON_DISABLED_TEXT);
                stepPanel.add(arrow);
            }

            JLabel lblStep = new JLabel(STEP_NAMES[i]);
            lblStep.setFont(AppFonts.regular(11));

            if (i == currentStep) {
            	lblStep.setForeground(AppColors.YELLOW);
            } else if (i < currentStep) {
            	lblStep.setForeground(AppColors.TEXT_MUTED);
            } else {
            	lblStep.setForeground(AppColors.BUTTON_DISABLED_TEXT);
            }

            stepPanel.add(lblStep);
        }

        stepPanel.revalidate();
        stepPanel.repaint();
    }
}