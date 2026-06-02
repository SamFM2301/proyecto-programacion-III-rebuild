package views.newdateflow;

import java.awt.*;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import utils.AppColors;
import utils.AppFonts;

public class SideInfoDateView extends JPanel {

    private JPanel serviceSection;
    private JPanel employeeSection;
    private JPanel dateTimeSection;
    private JButton btnAction;
    private JButton btnBack;
    private JLabel lblTotal;

    private String selectedServiceName;
    private double selectedServicePrice;
    private String selectedEmployeeName;
    private String selectedDate;
    private String selectedTime;

    private boolean hasService;
    private boolean hasEmployee;
    private boolean hasDateTime;

    public SideInfoDateView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(240, 0));
        setMinimumSize(new Dimension(240, 0));
        setMaximumSize(new Dimension(240, Integer.MAX_VALUE));
        setBackground(AppColors.BACKGROUND);
        setBorder(new CompoundBorder(
            new MatteBorder(0, 1, 0, 0, AppColors.PANEL2),
            new EmptyBorder(20, 18, 20, 18)
        ));

        JPanel headerInfo = createHeaderInfo();
        headerInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel selectionContainer = createSelectionContainer();
        selectionContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel bottomPanel = createBottomPanel();
        bottomPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(headerInfo);
        add(Box.createVerticalStrut(20));
        add(selectionContainer);
        add(Box.createVerticalGlue());
        add(bottomPanel);

        setVisible(true);
    }

    private JPanel createHeaderInfo() {
        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setOpaque(false);
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerPanel.setMaximumSize(new Dimension(240, 60));

        JPanel iconWrapper = new JPanel(new GridBagLayout());
        iconWrapper.setBackground(AppColors.PANEL2);
        iconWrapper.setPreferredSize(new Dimension(44, 44));
        iconWrapper.setMaximumSize(new Dimension(44, 44));
        iconWrapper.setBorder(BorderFactory.createLineBorder(AppColors.BUTTON_DARK, 1));

        ImageIcon houseIcon = loadIcon("assets/icons/home.png", 36);
        JLabel lblHouseIcon = new JLabel(houseIcon);
        lblHouseIcon.setAlignmentX(Component.LEFT_ALIGNMENT);
        iconWrapper.add(lblHouseIcon);

        headerPanel.add(iconWrapper, BorderLayout.WEST);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.setMaximumSize(new Dimension(180, 60));

        JLabel lblTitle = new JLabel("Barber\u00eda");
        lblTitle.setForeground(AppColors.TEXT_LIGHT);
        lblTitle.setFont(AppFonts.bold(13));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel starsRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        starsRow.setOpaque(false);
        starsRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        ImageIcon starScaledIcon = loadIcon("assets/icons/star.png", 13);
        for (int i = 0; i < 5; i++) {
            JLabel starLabel = new JLabel(starScaledIcon);
            starLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            starsRow.add(starLabel);
        }

        JLabel lblReviews = new JLabel("(0)");
        lblReviews.setForeground(AppColors.FIELDS);
        lblReviews.setFont(AppFonts.small());
        lblReviews.setAlignmentX(Component.LEFT_ALIGNMENT);
        starsRow.add(lblReviews);

        JLabel lblDirection = new JLabel("Direcci\u00f3n...");
        lblDirection.setForeground(AppColors.FIELDS);
        lblDirection.setFont(AppFonts.small());
        lblDirection.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(lblTitle);
        infoPanel.add(Box.createVerticalStrut(2));
        infoPanel.add(starsRow);
        infoPanel.add(Box.createVerticalStrut(2));
        infoPanel.add(lblDirection);

        headerPanel.add(infoPanel, BorderLayout.CENTER);
        return headerPanel;
    }

    private JPanel createSelectionContainer() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        container.setAlignmentX(Component.LEFT_ALIGNMENT);
        container.setMaximumSize(new Dimension(240, Integer.MAX_VALUE));

        serviceSection = createEmptySelectionSection("Servicio", "assets/icons/scissors.png");
        employeeSection = createEmptySelectionSection("Barbero", "assets/icons/user.png");
        dateTimeSection = createEmptySelectionSection("Fecha y hora", "assets/icons/calendar.png");

        container.add(serviceSection);
        container.add(Box.createVerticalStrut(8));
        container.add(employeeSection);
        container.add(Box.createVerticalStrut(8));
        container.add(dateTimeSection);

        return container;
    }

    private JPanel createEmptySelectionSection(String title, String iconPath) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setOpaque(false);
        section.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.setMaximumSize(new Dimension(240, 60));

        JPanel header = new JPanel(new BorderLayout(8, 0));
        header.setOpaque(false);
        header.setMaximumSize(new Dimension(240, 24));

        JLabel iconLabel = new JLabel();
        ImageIcon icon = loadIcon(iconPath, 16);
        if (icon != null) {
            iconLabel.setIcon(icon);
        }
        iconLabel.setForeground(AppColors.FIELDS);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(AppFonts.small());
        titleLabel.setForeground(AppColors.FIELDS);

        header.add(iconLabel, BorderLayout.WEST);
        header.add(titleLabel, BorderLayout.CENTER);

        JLabel valueLabel = new JLabel("-");
        valueLabel.setFont(AppFonts.regular(12));
        valueLabel.setForeground(AppColors.WHITE_ALPHA_100);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        section.add(header);
        section.add(Box.createVerticalStrut(2));
        section.add(valueLabel);

        return section;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(240, 160));

        JPanel separator = new JPanel();
        separator.setBackground(AppColors.PANEL2);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel totalRow = new JPanel(new BorderLayout());
        totalRow.setOpaque(false);
        totalRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        totalRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

        JLabel lblTotalLabel = new JLabel("Total");
        lblTotalLabel.setFont(AppFonts.regular(13));
        lblTotalLabel.setForeground(AppColors.TEXT_MUTED);

        lblTotal = new JLabel("$0");
        lblTotal.setFont(AppFonts.bold(14));
        lblTotal.setForeground(AppColors.TEXT_SECONDARY);
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);

        totalRow.add(lblTotalLabel, BorderLayout.WEST);
        totalRow.add(lblTotal, BorderLayout.EAST);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonRow.setOpaque(false);
        buttonRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        btnBack = new JButton();
        btnBack.setFont(AppFonts.bold(13));
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.setPreferredSize(new Dimension(42, 42));
        btnBack.setMinimumSize(new Dimension(42, 42));
        btnBack.setBackground(AppColors.FIELDS);
        btnBack.setForeground(AppColors.TEXT_LIGHT);
        btnBack.setVisible(false);

        ImageIcon backIcon = loadIcon("assets/icons/arrow-left-white.png", 18);
        if (backIcon != null) {
            btnBack.setIcon(backIcon);
        }

        btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnBack.setBackground(AppColors.FIELDS_HOVER);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnBack.setBackground(AppColors.FIELDS);
            }
        });

        btnAction = new JButton("Continuar");
        btnAction.setFont(AppFonts.bold(13));
        btnAction.setFocusPainted(false);
        btnAction.setBorderPainted(false);
        btnAction.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAction.setEnabled(false);
        setButtonDisabledStyle(btnAction);
        btnAction.setPreferredSize(new Dimension(158, 42));
        btnAction.setMinimumSize(new Dimension(158, 42));

        btnAction.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btnAction.isEnabled()) {
                    btnAction.setBackground(AppColors.YELLOW_HOVER);
                }
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (btnAction.isEnabled()) {
                    btnAction.setBackground(AppColors.YELLOW);
                }
            }
        });

        buttonRow.add(btnBack);
        buttonRow.add(btnAction);

        panel.add(separator);
        panel.add(Box.createVerticalStrut(12));
        panel.add(totalRow);
        panel.add(Box.createVerticalStrut(12));
        panel.add(buttonRow);

        return panel;
    }

    private void setButtonDisabledStyle(JButton btn) {
        btn.setBackground(AppColors.BUTTON_DARK);
        btn.setForeground(AppColors.BUTTON_DISABLED_TEXT);
    }

    private void setButtonEnabledStyle(JButton btn) {
        btn.setBackground(AppColors.YELLOW);
        btn.setForeground(AppColors.TEXT_DARK);
    }

    private ImageIcon loadIcon(String path, int size) {
        URL url = getClass().getClassLoader().getResource(path);
        if (url == null) {
            return null;
        }
        Image img = new ImageIcon(url).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public void updateServiceSelection(String name, double price) {
        selectedServiceName = name;
        selectedServicePrice = price;
        hasService = true;
        updateSelectionSection(serviceSection, "Servicio", name, true);
        updateTotal();
    }

    public void updateEmployeeSelection(String name) {
        selectedEmployeeName = name;
        hasEmployee = true;
        updateSelectionSection(employeeSection, "Barbero", name, true);
    }

    public void updateDateTimeSelection(String date, String time) {
        selectedDate = date;
        selectedTime = time;
        hasDateTime = true;

        JPanel section = dateTimeSection;
        Component[] components = section.getComponents();

        if (components.length >= 3) {
            JPanel header = (JPanel) components[0];
            JLabel valueLabel = (JLabel) components[2];

            valueLabel.setText(date + " " + time);
            valueLabel.setForeground(AppColors.TEXT_LIGHT);

            JLabel titleLabel = (JLabel) header.getComponent(1);
            titleLabel.setForeground(AppColors.YELLOW);
        }
    }

    private void updateSelectionSection(JPanel section, String title, String value, boolean completed) {
        Component[] components = section.getComponents();

        if (components.length >= 3) {
            JPanel header = (JPanel) components[0];
            JLabel valueLabel = (JLabel) components[2];

            valueLabel.setText(value);

            if (completed) {
                valueLabel.setForeground(AppColors.TEXT_LIGHT);
                JLabel titleLabel = (JLabel) header.getComponent(1);
                titleLabel.setForeground(AppColors.YELLOW);
            } else {
                valueLabel.setForeground(AppColors.WHITE_ALPHA_100);
            }
        }
    }

    private void updateTotal() {
        if (selectedServicePrice > 0) {
            lblTotal.setText(String.format("$%.0f MXN", selectedServicePrice));
            lblTotal.setForeground(AppColors.YELLOW);
            lblTotal.setFont(AppFonts.bold(16));
        } else {
            lblTotal.setText("$0");
            lblTotal.setForeground(AppColors.TEXT_SECONDARY);
        }
    }

    public void setActionButtonEnabled(boolean enabled) {
        btnAction.setEnabled(enabled);
        if (enabled) {
            setButtonEnabledStyle(btnAction);
        } else {
            setButtonDisabledStyle(btnAction);
        }
    }

    public void setActionButtonText(String text) {
        btnAction.setText(text);
    }

    public void setBackButtonVisible(boolean visible) {
        btnBack.setVisible(visible);
        revalidate();
        repaint();
    }

    public JButton getBtnAction() {
        return btnAction;
    }

    public JButton getBtnBack() {
        return btnBack;
    }

    public void reset() {
        selectedServiceName = null;
        selectedServicePrice = 0;
        selectedEmployeeName = null;
        selectedDate = null;
        selectedTime = null;
        hasService = false;
        hasEmployee = false;
        hasDateTime = false;

        serviceSection = createEmptySelectionSection("Servicio", "assets/icons/scissors.png");
        employeeSection = createEmptySelectionSection("Barbero", "assets/icons/user.png");
        dateTimeSection = createEmptySelectionSection("Fecha y hora", "assets/icons/calendar.png");

        removeAll();
        JPanel headerInfo = createHeaderInfo();
        headerInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel selectionContainer = createSelectionContainer();
        selectionContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel bottomPanel = createBottomPanel();
        bottomPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(headerInfo);
        add(Box.createVerticalStrut(20));
        add(selectionContainer);
        add(Box.createVerticalGlue());
        add(bottomPanel);

        setActionButtonEnabled(false);
        setBackButtonVisible(false);
        lblTotal.setText("$0");
        lblTotal.setForeground(AppColors.TEXT_SECONDARY);

        revalidate();
        repaint();
    }
}