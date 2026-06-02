package views.newdateflow;

import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import utils.AppColors;
import utils.AppFonts;

public class SelectServiceView extends JPanel {

    private Object controller;

    private JPanel servicesContainer;
    private Map<Integer, JPanel> serviceCards;
    private Map<Integer, JPanel> circleButtons;
    private Integer selectedServiceId;

    public SelectServiceView(Object controller) {
        this.controller = controller;
        this.serviceCards = new HashMap<>();
        this.circleButtons = new HashMap<>();
        this.selectedServiceId = null;

        setLayout(new BorderLayout());
        setBackground(AppColors.BACKGROUND);

        add(createContentPanel(), BorderLayout.CENTER);
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(AppColors.BACKGROUND);
        panel.setBorder(new EmptyBorder(20, 28, 20, 28));

        servicesContainer = new JPanel();
        servicesContainer.setLayout(new BoxLayout(servicesContainer, BoxLayout.Y_AXIS));
        servicesContainer.setOpaque(true);
        servicesContainer.setBackground(AppColors.BACKGROUND);

        JScrollPane scrollPane = new JScrollPane(servicesContainer);
        scrollPane.setBackground(AppColors.BACKGROUND);
        scrollPane.getViewport().setBackground(AppColors.BACKGROUND);
        scrollPane.setBorder(null);
        scrollPane.setViewportBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(scrollPane);

        return panel;
    }

    public void addService(int id, String name, String description, double price, String duration) {
        JPanel card = createServiceCard(id, name, description, price, duration);
        servicesContainer.add(card);
        servicesContainer.add(Box.createVerticalStrut(10));
        serviceCards.put(id, card);
        servicesContainer.revalidate();
        servicesContainer.repaint();
    }

    private JPanel createServiceCard(int id, String name, String description, double price, String duration) {
        JPanel card = new JPanel(new BorderLayout(12, 0));
        card.setBackground(AppColors.BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppColors.PANEL2, 1),
            BorderFactory.createEmptyBorder(16, 18, 16, 18)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel lblName = new JLabel(name);
        lblName.setFont(AppFonts.bold(15));
        lblName.setForeground(AppColors.TEXT_LIGHT);
        lblName.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDuration = new JLabel(duration);
        lblDuration.setFont(AppFonts.small());
        lblDuration.setForeground(AppColors.TEXT_MUTED);
        lblDuration.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDescription = new JLabel(description != null ? description : "");
        lblDescription.setFont(AppFonts.small());
        lblDescription.setForeground(AppColors.TEXT_SECONDARY);
        lblDescription.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblPrice = new JLabel(String.format("$%.0f MXN", price));
        lblPrice.setFont(AppFonts.bold(14));
        lblPrice.setForeground(AppColors.YELLOW);
        lblPrice.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(lblName);
        infoPanel.add(Box.createVerticalStrut(2));
        infoPanel.add(lblDuration);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(lblDescription);
        infoPanel.add(Box.createVerticalStrut(6));
        infoPanel.add(lblPrice);

        ImageIcon plusIcon = loadIcon("assets/icons/plus-white.png", 14);
        ImageIcon checkIcon = loadIcon("assets/icons/check-black.png", 14);

        JPanel btnCircle = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean isSelected = (selectedServiceId != null && selectedServiceId.equals(id));
                int w = getWidth();
                int h = getHeight();
                int size = Math.min(w, h);
                int x = (w - size) / 2;
                int y = (h - size) / 2;

                if (isSelected) {
                    g2.setColor(AppColors.YELLOW);
                    g2.fillOval(x, y, size, size);

                    if (checkIcon != null) {
                        int ix = x + (size - checkIcon.getIconWidth()) / 2;
                        int iy = y + (size - checkIcon.getIconHeight()) / 2;
                        checkIcon.paintIcon(this, g, ix, iy);
                    }
                } else {
                    g2.setColor(AppColors.BORDER_HOVER);
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawOval(x, y, size, size);

                    if (plusIcon != null) {
                        int ix = x + (size - plusIcon.getIconWidth()) / 2;
                        int iy = y + (size - plusIcon.getIconHeight()) / 2;
                        plusIcon.paintIcon(this, g, ix, iy);
                    }
                }

                g2.dispose();
            }
        };
        btnCircle.setPreferredSize(new Dimension(28, 28));
        btnCircle.setMinimumSize(new Dimension(28, 28));
        btnCircle.setMaximumSize(new Dimension(28, 28));
        btnCircle.setOpaque(false);
        btnCircle.setAlignmentY(Component.TOP_ALIGNMENT);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 2));
        rightPanel.setOpaque(false);
        rightPanel.add(btnCircle);

        card.add(infoPanel, BorderLayout.CENTER);
        card.add(rightPanel, BorderLayout.EAST);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                selectService(id, name, price);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (selectedServiceId == null || !selectedServiceId.equals(id)) {
                    card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(AppColors.BORDER_HOVER, 1),
                        BorderFactory.createEmptyBorder(16, 18, 16, 18)
                    ));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (selectedServiceId == null || !selectedServiceId.equals(id)) {
                    card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(AppColors.PANEL2, 1),
                        BorderFactory.createEmptyBorder(16, 18, 16, 18)
                    ));
                }
            }
        });

        circleButtons.put(id, btnCircle);

        return card;
    }

    private void selectService(int id, String name, double price) {
        if (selectedServiceId != null) {
            JPanel oldCard = serviceCards.get(selectedServiceId);
            if (oldCard != null) {
                oldCard.setBackground(AppColors.BACKGROUND);
                oldCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(AppColors.PANEL2, 1),
                    BorderFactory.createEmptyBorder(16, 18, 16, 18)
                ));
            }
            JPanel oldBtn = circleButtons.get(selectedServiceId);
            if (oldBtn != null) oldBtn.repaint();
        }

        selectedServiceId = id;

        JPanel card = serviceCards.get(id);
        if (card != null) {
            card.setBackground(AppColors.SELECTED_BG);
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppColors.YELLOW, 1),
                BorderFactory.createEmptyBorder(16, 18, 16, 18)
            ));
        }

        JPanel btnCircle = circleButtons.get(id);
        if (btnCircle != null) btnCircle.repaint();

        if (controller instanceof controllers.newdateflow.SelectServiceController) {
            ((controllers.newdateflow.SelectServiceController) controller).onServiceSelected(id);
        }
    }

    public Integer getSelectedServiceId() {
        return selectedServiceId;
    }

    public void clearServices() {
        servicesContainer.removeAll();
        serviceCards.clear();
        circleButtons.clear();
        selectedServiceId = null;
    }

    public void setSelectedServiceId(int id, String name, double price) {
        selectService(id, name, price);
    }

    private ImageIcon loadIcon(String path, int size) {
        URL iconUrl = getClass().getClassLoader().getResource(path);
        if (iconUrl == null) return null;
        Image img = new ImageIcon(iconUrl).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}