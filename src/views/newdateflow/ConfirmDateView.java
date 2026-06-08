package views.newdateflow;

import java.awt.*;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import utils.AppColors;
import utils.AppFonts;

public class ConfirmDateView extends JPanel {

    private Object controller;

    private JLabel lblDateValue;
    private JLabel lblTimeValue;
    private JLabel lblEmployeeValue;
    private JLabel lblServiceValue;
    private JLabel lblServicePrice;
    private JLabel lblTotalValue;

    public ConfirmDateView(Object controller) {
        this.controller = controller;

        setLayout(new BorderLayout());
        setBackground(AppColors.BACKGROUND);

        add(createContentPanel(), BorderLayout.CENTER);
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(AppColors.BACKGROUND);
        panel.setBorder(new EmptyBorder(20, 28, 20, 28));

        panel.add(createDetailsCard());
        panel.add(Box.createVerticalStrut(14));
        panel.add(createServicesCard());
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createDetailsCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(AppColors.BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppColors.PANEL2, 1),
            BorderFactory.createEmptyBorder(22, 22, 22, 22)
        ));
        card.setMaximumSize(new Dimension(600, Integer.MAX_VALUE));

        JLabel lblTitle = new JLabel("Detalles de la cita");
        lblTitle.setFont(AppFonts.bold(11));
        lblTitle.setForeground(AppColors.TEXT_MUTED);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(lblTitle);
        card.add(Box.createVerticalStrut(14));

        lblDateValue = new JLabel("-");
        card.add(createDetailRow(createCalendarIcon(), "Fecha", lblDateValue));
        card.add(Box.createVerticalStrut(10));

        lblTimeValue = new JLabel("-");
        card.add(createDetailRow(createClockIcon(), "Hora", lblTimeValue));
        card.add(Box.createVerticalStrut(10));

        lblEmployeeValue = new JLabel("-");
        card.add(createDetailRow(createUserIcon(), "Barbero", lblEmployeeValue));

        return card;
    }

    private JPanel createServicesCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(AppColors.BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppColors.PANEL2, 1),
            BorderFactory.createEmptyBorder(22, 22, 22, 22)
        ));
        card.setMaximumSize(new Dimension(600, Integer.MAX_VALUE));

        JLabel lblTitle = new JLabel("Servicios");
        lblTitle.setFont(AppFonts.bold(11));
        lblTitle.setForeground(AppColors.TEXT_MUTED);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(lblTitle);
        card.add(Box.createVerticalStrut(14));

        lblServiceValue = new JLabel("-");
        lblServicePrice = new JLabel("-");
        card.add(createServiceRow(lblServiceValue, lblServicePrice));

        card.add(Box.createVerticalStrut(12));

        JPanel separator = new JPanel();
        separator.setBackground(AppColors.PANEL2);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(separator);

        card.add(Box.createVerticalStrut(12));

        JPanel totalRow = new JPanel(new BorderLayout());
        totalRow.setOpaque(false);
        totalRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTotalLabel = new JLabel("Total");
        lblTotalLabel.setFont(AppFonts.regular(14));
        lblTotalLabel.setForeground(AppColors.TEXT_SECONDARY);

        lblTotalValue = new JLabel("-");
        lblTotalValue.setFont(AppFonts.bold(18));
        lblTotalValue.setForeground(AppColors.TEXT_LIGHT);
        lblTotalValue.setHorizontalAlignment(SwingConstants.RIGHT);

        totalRow.add(lblTotalLabel, BorderLayout.WEST);
        totalRow.add(lblTotalValue, BorderLayout.EAST);

        card.add(totalRow);

        return card;
    }

    private JPanel createDetailRow(Icon icon, String label, JLabel value) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);

        JLabel lblKey = new JLabel(label);
        lblKey.setFont(AppFonts.regular(12));
        lblKey.setForeground(AppColors.TEXT_MUTED);
        lblKey.setPreferredSize(new Dimension(80, 20));

        value.setFont(AppFonts.regular(13));
        value.setForeground(AppColors.TEXT_LIGHT);

        row.add(iconLabel, BorderLayout.WEST);
        row.add(lblKey, BorderLayout.CENTER);
        row.add(value, BorderLayout.EAST);

        return row;
    }

    private JPanel createServiceRow(JLabel name, JLabel price) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        name.setFont(AppFonts.regular(13));
        name.setForeground(AppColors.TEXT_LIGHT);

        price.setFont(AppFonts.bold(13));
        price.setForeground(AppColors.YELLOW);
        price.setHorizontalAlignment(SwingConstants.RIGHT);

        row.add(name, BorderLayout.WEST);
        row.add(price, BorderLayout.EAST);

        return row;
    }

    private Icon createCalendarIcon() {
        URL iconURL = getClass().getClassLoader().getResource("assets/icons/calendar.png");
        if (iconURL != null) {
            Image img = new ImageIcon(iconURL).getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
        return new ImageIcon();
    }

    private Icon createClockIcon() {
        URL iconURL = getClass().getClassLoader().getResource("assets/icons/clock.png");
        if (iconURL != null) {
            Image img = new ImageIcon(iconURL).getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
        return new ImageIcon();
    }

    private Icon createUserIcon() {
        URL iconURL = getClass().getClassLoader().getResource("assets/icons/user.png");
        if (iconURL != null) {
            Image img = new ImageIcon(iconURL).getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
        return new ImageIcon();
    }

    public void updateServiceInfo(String name, double price) {
        lblServiceValue.setText(name);
        lblServicePrice.setText(String.format("$%.0f MXN", price));
        lblTotalValue.setText(String.format("$%.0f MXN", price));
    }

    public void updateEmployeeInfo(String name) {
        lblEmployeeValue.setText(name);
    }

    public void updateDateTimeInfo(String date, String time) {
        lblDateValue.setText(date);
        lblTimeValue.setText(time);
    }
}