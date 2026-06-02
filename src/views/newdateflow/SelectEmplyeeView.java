package views.newdateflow;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import utils.AppColors;
import utils.AppFonts;

public class SelectEmplyeeView extends JPanel {

    private Object controller;

    private JPanel employeesContainer;
    private Map<Integer, JPanel> employeeCards;
    private Integer selectedEmployeeId;

    public SelectEmplyeeView(Object controller) {
        this.controller = controller;
        this.employeeCards = new HashMap<>();
        this.selectedEmployeeId = null;

        setLayout(new BorderLayout());
        setBackground(AppColors.BACKGROUND);

        add(createContentPanel(), BorderLayout.CENTER);
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(AppColors.BACKGROUND);
        panel.setBorder(new EmptyBorder(20, 28, 20, 28));

        JPanel wrapper = new JPanel(new GridLayout(0, 2, 12, 12));
        wrapper.setOpaque(true);
        wrapper.setBackground(AppColors.BACKGROUND);

        employeesContainer = wrapper;

        JScrollPane scrollPane = new JScrollPane(employeesContainer);
        scrollPane.setBackground(AppColors.BACKGROUND);
        scrollPane.getViewport().setBackground(AppColors.BACKGROUND);
        scrollPane.setBorder(null);
        scrollPane.setViewportBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(scrollPane);

        return panel;
    }

    public void addEmployee(int id, String name, String specialty, Image avatarImage) {
        JPanel card = createEmployeeCard(id, name, specialty, avatarImage);
        employeesContainer.add(card);
        employeeCards.put(id, card);
        employeesContainer.revalidate();
        employeesContainer.repaint();
    }

    private JPanel createEmployeeCard(int id, String name, String specialty, Image avatarImage) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(AppColors.BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppColors.PANEL2, 1),
            BorderFactory.createEmptyBorder(18, 16, 18, 16)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon checkIcon = loadIcon("assets/icons/check-black.png", 10);

        JPanel avatarPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth();
                int h = getHeight();

                g2.setColor(AppColors.BUTTON_DARK);
                g2.fillOval(0, 0, 52, 52);

                String initial = name != null && !name.isEmpty()
                    ? name.substring(0, 1).toUpperCase() : "?";
                g2.setColor(AppColors.TEXT_SECONDARY);
                g2.setFont(AppFonts.bold(20));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(initial,
                    (52 - fm.stringWidth(initial)) / 2,
                    (52 - fm.getHeight()) / 2 + fm.getAscent()
                );

                if (selectedEmployeeId != null && selectedEmployeeId.equals(id)) {
                    g2.setColor(AppColors.YELLOW);
                    g2.fillOval(34, 34, 18, 18);

                    if (checkIcon != null) {
                        int ix = 34 + (18 - checkIcon.getIconWidth()) / 2;
                        int iy = 34 + (18 - checkIcon.getIconHeight()) / 2;
                        checkIcon.paintIcon(this, g, ix, iy);
                    }
                }

                g2.dispose();
            }
        };
        avatarPanel.setPreferredSize(new Dimension(52, 52));
        avatarPanel.setMaximumSize(new Dimension(52, 52));
        avatarPanel.setOpaque(false);
        avatarPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblName = new JLabel(name, SwingConstants.CENTER);
        lblName.setFont(AppFonts.bold(14));
        lblName.setForeground(AppColors.TEXT_LIGHT);
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblRole = new JLabel(specialty != null ? specialty : "Barbero", SwingConstants.CENTER);
        lblRole.setFont(AppFonts.small());
        lblRole.setForeground(AppColors.TEXT_MUTED);
        lblRole.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(avatarPanel);
        card.add(Box.createVerticalStrut(10));
        card.add(lblName);
        card.add(Box.createVerticalStrut(2));
        card.add(lblRole);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                selectEmployee(id);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (selectedEmployeeId == null || !selectedEmployeeId.equals(id)) {
                    card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(AppColors.BORDER_HOVER, 1),
                        BorderFactory.createEmptyBorder(18, 16, 18, 16)
                    ));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (selectedEmployeeId == null || !selectedEmployeeId.equals(id)) {
                    card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(AppColors.PANEL2, 1),
                        BorderFactory.createEmptyBorder(18, 16, 18, 16)
                    ));
                }
            }
        });

        return card;
    }

    private void selectEmployee(int id) {
        if (selectedEmployeeId != null) {
            JPanel oldCard = employeeCards.get(selectedEmployeeId);
            if (oldCard != null) {
                oldCard.setBackground(AppColors.BACKGROUND);
                oldCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(AppColors.PANEL2, 1),
                    BorderFactory.createEmptyBorder(18, 16, 18, 16)
                ));
            }
        }

        selectedEmployeeId = id;

        JPanel card = employeeCards.get(id);
        if (card != null) {
            card.setBackground(AppColors.SELECTED_BG);
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppColors.YELLOW, 1),
                BorderFactory.createEmptyBorder(18, 16, 18, 16)
            ));
        }

        for (JPanel c : employeeCards.values()) {
            c.repaint();
        }

        if (controller instanceof controllers.newdateflow.SelectEmployeeController) {
            ((controllers.newdateflow.SelectEmployeeController) controller).onEmployeeSelected(id);
        }
    }

    public Integer getSelectedEmployeeId() {
        return selectedEmployeeId;
    }

    public void clearEmployees() {
        employeesContainer.removeAll();
        employeeCards.clear();
        selectedEmployeeId = null;
    }

    public void setSelectedEmployeeId(int id, String name) {
        selectEmployee(id);
    }

    private ImageIcon loadIcon(String path, int size) {
        java.net.URL iconUrl = getClass().getClassLoader().getResource(path);
        if (iconUrl == null) return null;
        Image img = new ImageIcon(iconUrl).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}