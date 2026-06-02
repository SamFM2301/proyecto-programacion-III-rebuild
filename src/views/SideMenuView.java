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

public class SideMenuView extends JPanel {

    private static final long serialVersionUID = 1L;
    private SideMenuController controller;
    private String activeItem = "mis_citas";

    private Image profileImage = null;
    private JLabel avatarLabel;

    public SideMenuView(SideMenuController controller) {
        this.controller = controller;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 0));
        setMinimumSize(new Dimension(250, 0));
        setMaximumSize(new Dimension(250, Integer.MAX_VALUE));
        setBackground(AppColors.BACKGROUND);

        add(logoSection());
        add(principalSection());
        add(accountSection());
        add(Box.createVerticalGlue());
        add(buildProfileSection());
    }

    private JPanel logoSection() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBackground(AppColors.BACKGROUND);
        panel.setMaximumSize(new Dimension(250, 100));
        panel.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, AppColors.BORDER_WHITE_40),
            new EmptyBorder(20, 25, 20, 25)
        ));

        URL logoURL = getClass().getClassLoader().getResource("assets/img/logo_horizontal.png");
        Image img = new ImageIcon(logoURL).getImage().getScaledInstance(200, 56, Image.SCALE_SMOOTH);
        panel.add(new JLabel(new ImageIcon(img)), BorderLayout.WEST);
        return panel;
    }

    private JPanel principalSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBackground(AppColors.BACKGROUND);
        panel.setBorder(new EmptyBorder(20, 0, 20, 0));

        JLabel lbl = new JLabel("PRINCIPAL");
        lbl.setForeground(AppColors.WHITE_ALPHA_100);
        lbl.setFont(AppFonts.regular(11));
        lbl.setBorder(new EmptyBorder(0, 25, 10, 25));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lbl);

        panel.add(createMenuItem("assets/icons/calendar-event.png", "Mis citas",  "mis_citas",  activeItem.equals("mis_citas")));
        panel.add(createMenuItem("assets/icons/calendar-plus.png",  "Nueva cita", "nueva_cita", activeItem.equals("nueva_cita")));
        return panel;
    }

    private JPanel accountSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBackground(AppColors.BACKGROUND);
        panel.setBorder(new CompoundBorder(
            new MatteBorder(1, 0, 0, 0, AppColors.BORDER_WHITE_40),
            new EmptyBorder(20, 0, 20, 0)
        ));

        JLabel lbl = new JLabel("CUENTA");
        lbl.setForeground(AppColors.WHITE_ALPHA_100);
        lbl.setFont(AppFonts.regular(11));
        lbl.setBorder(new EmptyBorder(0, 25, 10, 25));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lbl);

        panel.add(createMenuItem("assets/icons/user-circle.png", "Mi Perfil", "perfil", activeItem.equals("perfil")));
        return panel;
    }

    private JPanel buildProfileSection() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBackground(AppColors.BACKGROUND);
        panel.setMaximumSize(new Dimension(250, 70));
        panel.setBorder(new CompoundBorder(
            new MatteBorder(1, 0, 0, 0, AppColors.BORDER_WHITE_40),
            new EmptyBorder(12, 10, 12, 10)
        ));

        avatarLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth();
                int h = getHeight();

                if (profileImage != null) {
                    g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, w, h));
                    g2.drawImage(profileImage, 0, 0, w, h, null);
                } else {
                    g2.setColor(AppColors.YELLOW);
                    g2.fillOval(0, 0, w, h);

                    String initials = getInitials();
                    g2.setColor(AppColors.TEXT_DARK);
                    g2.setFont(new Font("SansSerif", Font.BOLD, 14));
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(initials,
                        (w - fm.stringWidth(initials)) / 2,
                        (h - fm.getHeight()) / 2 + fm.getAscent()
                    );
                }
                g2.dispose();
            }
        };
        avatarLabel.setPreferredSize(new Dimension(42, 42));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(AppColors.BACKGROUND);
        info.setBorder(new EmptyBorder(0, 10, 0, 0));

        JLabel nameLabel = new JLabel(getFullName());
        nameLabel.setForeground(AppColors.TEXT_LIGHT);
        nameLabel.setFont(AppFonts.bold(13));

        JLabel roleLabel = new JLabel("");
        roleLabel.setForeground(AppColors.WHITE_ALPHA_150);
        roleLabel.setFont(AppFonts.regular(11));
        
        try {
        	roleLabel.setText(Session.getCurrentUser().getRole());
        } catch (Exception e) {
        	System.out.println(e);
        }
        
        info.add(nameLabel);
        info.add(roleLabel);

        URL logoutURL = getClass().getClassLoader().getResource("assets/icons/logout.png");
        Image logoutImg = new ImageIcon(logoutURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JButton btnLogout = new JButton(new ImageIcon(logoutImg));
        btnLogout.setBackground(null);
        btnLogout.setBorderPainted(false);
        btnLogout.setContentAreaFilled(false);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setToolTipText("Cerrar sesión");
        btnLogout.addActionListener(e -> controller.logOut());

        panel.add(avatarLabel, BorderLayout.WEST);
        panel.add(info, BorderLayout.CENTER);
        panel.add(btnLogout, BorderLayout.EAST);

        return panel;
    }

    public void updateProfileImage(Image image) {
        this.profileImage = image;
        if (avatarLabel != null) avatarLabel.repaint();
    }

    private JPanel createMenuItem(String iconPath, String text, String section, boolean isActive) {
        JPanel item = new JPanel(new BorderLayout());
        item.setAlignmentX(Component.LEFT_ALIGNMENT);
        item.setMaximumSize(new Dimension(250, 44));
        item.setPreferredSize(new Dimension(250, 44));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (isActive) {
            item.setOpaque(true);
            item.setBackground(AppColors.PANEL2);
            item.setBorder(new CompoundBorder(
                new MatteBorder(0, 3, 0, 0, AppColors.YELLOW),
                new EmptyBorder(12, 22, 12, 15)
            ));
        } else {
            item.setOpaque(true);
            item.setBackground(AppColors.BACKGROUND);
            item.setBorder(new EmptyBorder(12, 25, 12, 15));
        }

        item.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e)  { controller.onMenuItemClick(section); }
            @Override public void mouseEntered(MouseEvent e)  { if (!activeItem.equals(section)) { item.setBackground(AppColors.HOVER_BG); item.repaint(); } }
            @Override public void mouseExited(MouseEvent e)   { if (!activeItem.equals(section)) { item.setBackground(AppColors.BACKGROUND); item.repaint(); } }
        });

        URL iconURL = getClass().getClassLoader().getResource(iconPath);
        if (iconURL != null) {
            Image img = new ImageIcon(iconURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            item.add(new JLabel(new ImageIcon(img)), BorderLayout.WEST);
        }

        JLabel label = new JLabel(text);
        label.setForeground(AppColors.TEXT_LIGHT);
        label.setFont(AppFonts.regular(14));
        label.setBorder(new EmptyBorder(0, 12, 0, 0));
        item.add(label, BorderLayout.CENTER);

        return item;
    }

    public void setActiveItem(String section) {
        this.activeItem = section;
        removeAll();
        add(logoSection());
        add(principalSection());
        add(accountSection());
        add(Box.createVerticalGlue());
        add(buildProfileSection());
        revalidate();
        repaint();
    }

    private String getFullName() {
        if (Session.getCurrentUser() == null) return "Usuario";
        String first = Session.getCurrentUser().getFirstName();
        String last  = Session.getCurrentUser().getLastName();
        if (first == null) first = "";
        if (last  == null) last  = "";
        String full = (first + " " + last).trim();
        return full.isEmpty() ? "Usuario" : full;
    }

    private String getInitials() {
        if (Session.getCurrentUser() == null) return "U";
        String first = Session.getCurrentUser().getFirstName();
        String last  = Session.getCurrentUser().getLastName();
        String i = "";
        if (first != null && !first.isEmpty()) i += first.charAt(0);
        if (last  != null && !last.isEmpty())  i += last.charAt(0);
        return i.isEmpty() ? "U" : i.toUpperCase();
    }
}
