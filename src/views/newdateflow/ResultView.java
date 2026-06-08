package views.newdateflow;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import utils.AppColors;
import utils.AppFonts;

public class ResultView extends JPanel {

    private JLabel lblTitle;
    private JLabel lblMessage;
    private JPanel detailCard;
    private JLabel lblServiceVal;
    private JLabel lblEmployeeVal;
    private JLabel lblDateVal;
    private JLabel lblPriceVal;
    private JButton btnAction;

    private JPanel iconPanel;
    private boolean isSuccess;

    public ResultView() {
        setLayout(new BorderLayout());
        setBackground(AppColors.BACKGROUND);

        btnAction = new JButton();
        btnAction.setFont(AppFonts.bold(13));
        btnAction.setFocusPainted(false);
        btnAction.setBorderPainted(false);
        btnAction.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAction.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAction.setMaximumSize(new Dimension(200, 42));

        btnAction.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (isSuccess) {
                    btnAction.setBackground(AppColors.YELLOW_HOVER);
                } else {
                    btnAction.setBackground(AppColors.BUTTON_DARK);
                }
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (isSuccess) {
                    btnAction.setBackground(AppColors.YELLOW);
                } else {
                    btnAction.setBackground(AppColors.PANEL2);
                }
            }
        });
    }

    public void showSuccess(String service, String employee, String date, String price) {
        removeAll();
        this.isSuccess = true;

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);
        center.setAlignmentX(Component.CENTER_ALIGNMENT);

        iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight();

                g2.setColor(AppColors.SUCCESS_BG);
                g2.fillOval(0, 0, 64, 64);
                g2.setColor(AppColors.SUCCESS_BORDER);
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(0, 0, 64, 64);

                g2.setColor(AppColors.SUCCESS);
                g2.setStroke(new BasicStroke(3));
                int cx = 32, cy = 32;
                int[] xPoints = {cx - 8, cx + 2, cx + 12};
                int[] yPoints = {cy + 3, cy + 9, cy - 7};
                g2.drawPolyline(xPoints, yPoints, 3);
                g2.dispose();
            }
        };
        
        iconPanel.setPreferredSize(new Dimension(64, 64));
        iconPanel.setMaximumSize(new Dimension(64, 64));
        iconPanel.setOpaque(false);
        iconPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTitle = new JLabel("\u00a1Cita agendada!");
        lblTitle.setFont(AppFonts.bold(18));
        lblTitle.setForeground(AppColors.SUCCESS);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblMessage = new JLabel("Tu cita fue registrada con \u00e9xito. Te esperamos el d\u00eda indicado.");
        lblMessage.setFont(AppFonts.regular(13));
        lblMessage.setForeground(AppColors.TEXT_SECONDARY);
        lblMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        detailCard = new JPanel();
        detailCard.setLayout(new BoxLayout(detailCard, BoxLayout.Y_AXIS));
        detailCard.setBackground(AppColors.BACKGROUND);
        detailCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppColors.PANEL2, 1),
            BorderFactory.createEmptyBorder(14, 18, 14, 18)
        ));
        detailCard.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailCard.setMaximumSize(new Dimension(320, 200));

        lblServiceVal = new JLabel("Servicio: " + (service != null ? service : "-"));
        lblEmployeeVal = new JLabel("Barbero: " + (employee != null ? employee : "-"));
        lblDateVal = new JLabel("Fecha: " + (date != null ? date : "-"));
        lblPriceVal = new JLabel("Total: " + (price != null ? price : "-"));

        for (JLabel lbl : new JLabel[]{lblServiceVal, lblEmployeeVal, lblDateVal, lblPriceVal}) {
            lbl.setFont(AppFonts.regular(12));
            lbl.setForeground(AppColors.TEXT_LIGHT);
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        }
        lblPriceVal.setForeground(AppColors.YELLOW);

        detailCard.add(lblServiceVal);
        detailCard.add(Box.createVerticalStrut(8));
        detailCard.add(lblEmployeeVal);
        detailCard.add(Box.createVerticalStrut(8));
        detailCard.add(lblDateVal);
        detailCard.add(Box.createVerticalStrut(8));
        detailCard.add(lblPriceVal);

        btnAction.setText("Ver mis citas");
        btnAction.setBackground(AppColors.YELLOW);
        btnAction.setForeground(AppColors.TEXT_DARK);
        btnAction.setVisible(true);

        center.add(Box.createVerticalGlue());
        center.add(iconPanel);
        center.add(Box.createVerticalStrut(16));
        center.add(lblTitle);
        center.add(Box.createVerticalStrut(8));
        center.add(lblMessage);
        center.add(Box.createVerticalStrut(20));
        center.add(detailCard);
        center.add(Box.createVerticalStrut(20));
        center.add(btnAction);
        center.add(Box.createVerticalGlue());

        add(center, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    public void showError(String message) {
        removeAll();
        this.isSuccess = false;

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);
        center.setAlignmentX(Component.CENTER_ALIGNMENT);

        iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight();

                g2.setColor(AppColors.ERROR_BG);
                g2.fillOval(0, 0, 64, 64);
                g2.setColor(AppColors.ERROR_BORDER);
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(0, 0, 64, 64);

                g2.setColor(AppColors.ERROR);
                g2.setStroke(new BasicStroke(3));
                int cx = 32, cy = 32;
                g2.drawLine(cx - 8, cy - 8, cx + 8, cy + 8);
                g2.drawLine(cx + 8, cy - 8, cx - 8, cy + 8);
                g2.dispose();
            }
        };
        
        iconPanel.setPreferredSize(new Dimension(64, 64));
        iconPanel.setMaximumSize(new Dimension(64, 64));
        iconPanel.setOpaque(false);
        iconPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTitle = new JLabel("No se pudo agendar");
        lblTitle.setFont(AppFonts.bold(18));
        lblTitle.setForeground(AppColors.ERROR);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblMessage = new JLabel(message != null ? message : "Ocurri\u00f3 un error al registrar tu cita. Por favor intenta de nuevo.");
        lblMessage.setFont(AppFonts.regular(13));
        lblMessage.setForeground(AppColors.TEXT_SECONDARY);
        lblMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnAction.setText("Intentar de nuevo");
        btnAction.setBackground(AppColors.PANEL2);
        btnAction.setForeground(AppColors.TEXT_SECONDARY);
        btnAction.setVisible(true);

        center.add(Box.createVerticalGlue());
        center.add(iconPanel);
        center.add(Box.createVerticalStrut(16));
        center.add(lblTitle);
        center.add(Box.createVerticalStrut(8));
        center.add(lblMessage);
        center.add(Box.createVerticalStrut(20));
        center.add(btnAction);
        center.add(Box.createVerticalGlue());

        add(center, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    public JButton getBtnAction() {
        return btnAction;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}