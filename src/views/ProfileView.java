package views;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.io.File;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import models.User;
import utils.AppColors;
import utils.AppFonts;
import utils.Session;

public class ProfileView extends JPanel {

    private static final long serialVersionUID = 1L;

    private Image profileImage = null;
    private JLabel avatarLabel;

    private views.SideMenuView sideMenuView;

    public ProfileView(views.SideMenuView sideMenuView) {
        this.sideMenuView = sideMenuView;

        setLayout(new BorderLayout());
        setBackground(AppColors.PANEL);
        setBorder(new EmptyBorder(40, 50, 40, 50));

        add(buildHeader(), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
    }


    private JPanel buildHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, 0, 30, 0));

        JLabel title = new JLabel("Mi Perfil");
        title.setFont(AppFonts.title());
        title.setForeground(AppColors.TEXT_LIGHT);

        JPanel separator = new JPanel();
        separator.setBackground(AppColors.BORDER_WHITE_40);
        separator.setPreferredSize(new Dimension(0, 1));

        panel.add(title, BorderLayout.NORTH);
        panel.add(separator, BorderLayout.SOUTH);
        return panel;
    }


    private JPanel buildContent() {
        JPanel panel = new JPanel(new BorderLayout(40, 0));
        panel.setOpaque(false);
        panel.add(buildLeftColumn(), BorderLayout.WEST);
        panel.add(buildRightColumn(), BorderLayout.CENTER);
        return panel;
    }


    private JPanel buildLeftColumn() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(160, 0));

        avatarLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth();
                int h = getHeight();

                if (profileImage != null) {
                    g2.setClip(new Ellipse2D.Float(0, 0, w, h));
                    g2.drawImage(profileImage, 0, 0, w, h, null);
                } else {
                    g2.setColor(AppColors.YELLOW);
                    g2.fillOval(0, 0, w, h);
                    String initials = getInitials();
                    g2.setColor(AppColors.TEXT_DARK);
                    g2.setFont(AppFonts.bold(36));
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(initials,
                        (w - fm.stringWidth(initials)) / 2,
                        (h - fm.getHeight()) / 2 + fm.getAscent()
                    );
                }
               
                g2.setClip(null);
                g2.setColor(AppColors.BORDER_WHITE_40);
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(1, 1, w - 2, h - 2);
                g2.dispose();
            }
        };

        avatarLabel.setPreferredSize(new Dimension(120, 120));
        avatarLabel.setMinimumSize(new Dimension(120, 120));
        avatarLabel.setMaximumSize(new Dimension(120, 120));
        avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnChangePhoto = new JButton("Cambiar foto de perfil");
        btnChangePhoto.setFont(AppFonts.regular(11));
        btnChangePhoto.setForeground(AppColors.YELLOW);
        btnChangePhoto.setBackground(null);
        btnChangePhoto.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppColors.YELLOW, 1, true),
            new EmptyBorder(5, 10, 5, 10)
        ));
        btnChangePhoto.setContentAreaFilled(false);
        btnChangePhoto.setFocusPainted(false);
        btnChangePhoto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnChangePhoto.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnChangePhoto.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                btnChangePhoto.setForeground(AppColors.YELLOW_HOVER);
                btnChangePhoto.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(AppColors.YELLOW_HOVER, 1, true),
                    new EmptyBorder(5, 10, 5, 10)
                ));
            }
            @Override public void mouseExited(MouseEvent e) {
                btnChangePhoto.setForeground(AppColors.YELLOW);
                btnChangePhoto.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(AppColors.YELLOW, 1, true),
                    new EmptyBorder(5, 10, 5, 10)
                ));
            }
        });

        btnChangePhoto.addActionListener(e -> selectProfileImage());

        panel.add(Box.createVerticalStrut(10));
        panel.add(avatarLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnChangePhoto);
        return panel;
    }


    private JPanel buildRightColumn() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        User user = Session.getCurrentUser();


        JLabel lblName = new JLabel(getFullName(user));
        lblName.setFont(AppFonts.bold(26));
        lblName.setForeground(AppColors.TEXT_LIGHT);
        lblName.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lblName);
        panel.add(Box.createVerticalStrut(25));
        panel.add(buildSeparator());
        panel.add(Box.createVerticalStrut(20));

        panel.add(buildInfoRow(
            "Correo electrónico",
            user != null && user.getEmail() != null ? user.getEmail() : "—"
        ));
        panel.add(Box.createVerticalStrut(18));

        panel.add(buildInfoRow(
            "Género",
            user != null && user.getGender() != null ? user.getGender() : "—"
        ));
        panel.add(Box.createVerticalStrut(18));

        panel.add(buildInfoRow(
            "Fecha de nacimiento",
            user != null && user.getBirthDate() != null ? user.getBirthDate() : "—"
        ));

        panel.add(Box.createVerticalStrut(20));
        panel.add(buildSeparator());

        return panel;
    }


    private JPanel buildInfoRow(String label, String value) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(AppFonts.bold(12));
        lblLabel.setForeground(AppColors.WHITE_ALPHA_150);
        lblLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(AppFonts.regular(14));
        lblValue.setForeground(AppColors.TEXT_LIGHT);
        lblValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        row.add(lblLabel);
        row.add(Box.createVerticalStrut(3));
        row.add(lblValue);
        return row;
    }


    private JPanel buildSeparator() {
        JPanel sep = new JPanel();
        sep.setBackground(AppColors.BORDER_WHITE_40);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);
        return sep;
    }


    private void selectProfileImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar foto de perfil");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(
            new FileNameExtensionFilter("Imágenes (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif")
        );

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            profileImage = new ImageIcon(file.getAbsolutePath())
                .getImage()
                .getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            avatarLabel.repaint();

            Image sideImage = new ImageIcon(file.getAbsolutePath())
                .getImage()
                .getScaledInstance(42, 42, Image.SCALE_SMOOTH);
            sideMenuView.updateProfileImage(sideImage);
        }
    }


    private String getFullName(User user) {
        if (user == null) return "Usuario";
        String first = user.getFirstName() != null ? user.getFirstName() : "";
        String last  = user.getLastName()  != null ? user.getLastName()  : "";
        String full  = (first + " " + last).trim();
        return full.isEmpty() ? "Usuario" : full;
    }

    private String getInitials() {
        User user = Session.getCurrentUser();
        if (user == null) return "U";
        String first = user.getFirstName();
        String last  = user.getLastName();
        String i = "";
        if (first != null && !first.isEmpty()) i += first.charAt(0);
        if (last  != null && !last.isEmpty())  i += last.charAt(0);
        return i.isEmpty() ? "U" : i.toUpperCase();
    }
}
