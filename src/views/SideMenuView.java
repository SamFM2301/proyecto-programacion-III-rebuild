package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import controllers.SideMenuController;
import utils.AppColors;

public class SideMenuView extends JPanel{
	
	/**
	 *  Este es el menu lateral de la ventana principal
	 */
	private static final long serialVersionUID = 1L;
	private SideMenuController controller;
	private String activeItem = "mis_citas";
	
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
        add(profileSection());
    }
	
	private JPanel logoSection() {
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.setAlignmentX(Component.LEFT_ALIGNMENT);
	    panel.setBackground(AppColors.BACKGROUND);
	    panel.setMaximumSize(new Dimension(250, 100));
	    panel.setBorder(new CompoundBorder(
	        new MatteBorder(0, 0, 1, 0, new Color(255, 255, 255, 40)),
	        new EmptyBorder(20, 25, 20, 25)
	    ));

	    URL logoURL = getClass().getClassLoader().getResource("assets/img/logo_horizontal.png");
	    Image img = new ImageIcon(logoURL).getImage().getScaledInstance(200, 56, Image.SCALE_SMOOTH);
	    JLabel label = new JLabel(new ImageIcon(img));

	    panel.add(label, BorderLayout.WEST);
	    return panel;
	}
	
	private JPanel principalSection() {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setAlignmentX(Component.LEFT_ALIGNMENT);
	    panel.setBackground(AppColors.BACKGROUND);
	    panel.setBorder(new EmptyBorder(20, 0, 20, 0));

	    JLabel sectionLabel = new JLabel("PRINCIPAL");
	    sectionLabel.setForeground(new Color(255, 255, 255, 100));
	    sectionLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
	    sectionLabel.setBorder(new EmptyBorder(0, 25, 10, 25));
	    sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
	    panel.add(sectionLabel);

	    panel.add(createMenuItem("assets/icons/calendar-event.png", "Mis citas", "mis_citas", activeItem.equals("mis_citas")));
	    panel.add(createMenuItem("assets/icons/calendar-plus.png", "Nueva cita", "nueva_cita", activeItem.equals("nueva_cita")));

	    return panel;
	}
	
	private JPanel accountSection() {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setAlignmentX(Component.LEFT_ALIGNMENT);
	    panel.setBackground(AppColors.BACKGROUND);
	    panel.setBorder(new CompoundBorder(
	        new MatteBorder(1, 0, 0, 0, new Color(255, 255, 255, 40)),
	        new EmptyBorder(20, 0, 20, 0)
	    ));

	    JLabel sectionLabel = new JLabel("CUENTA");
	    sectionLabel.setForeground(new Color(255, 255, 255, 100));
	    sectionLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
	    sectionLabel.setBorder(new EmptyBorder(0, 25, 10, 25));
	    sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
	    panel.add(sectionLabel);

	    panel.add(createMenuItem("assets/icons/user-circle.png", "Mi Perfil", "perfil", activeItem.equals("perfil")));
	    panel.add(createMenuItem("assets/icons/settings.png", "Configuracion", "configuracion", activeItem.equals("configuracion")));

	    return panel;
	}
	
	private JPanel profileSection() {
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.setAlignmentX(Component.LEFT_ALIGNMENT);
	    panel.setBackground(AppColors.BACKGROUND);
	    panel.setMaximumSize(new Dimension(250, 70));
	    panel.setBorder(new CompoundBorder(
	        new MatteBorder(1, 0, 0, 0, new Color(255, 255, 255, 40)),
	        new EmptyBorder(12, 25, 12, 25)
	    ));

	    JLabel avatar = new JLabel();
	    avatar.setPreferredSize(new Dimension(40, 40));
	    // URL avatarURL = getClass().getClassLoader().getResource("assets/img/avatar.png");
	    // Image avatarImg = new ImageIcon(avatarURL).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	    // avatar.setIcon(new ImageIcon(avatarImg));

	    JPanel info = new JPanel();
	    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
	    info.setBackground(AppColors.BACKGROUND);
	    info.setBorder(new EmptyBorder(0, 10, 0, 0));

	    JLabel name = new JLabel("Esteban Samuel");
	    name.setForeground(Color.WHITE);
	    name.setFont(new Font("SansSerif", Font.BOLD, 13));

	    JLabel role = new JLabel("Administrador");
	    role.setForeground(new Color(255, 255, 255, 150));
	    role.setFont(new Font("SansSerif", Font.PLAIN, 11));

	    info.add(name);
	    info.add(role);

	    URL logoutURL = getClass().getClassLoader().getResource("assets/icons/logout.png");
	    Image logoutImg = new ImageIcon(logoutURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

	    JButton btnLogout = new JButton(new ImageIcon(logoutImg));
	    btnLogout.setBackground(null);
	    btnLogout.setBorderPainted(false);
	    btnLogout.setContentAreaFilled(false);
	    btnLogout.setFocusPainted(false);
	    btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));

	    btnLogout.addActionListener(e -> controller.logOut());
	    
	    panel.add(avatar, BorderLayout.WEST);
	    panel.add(info, BorderLayout.CENTER);
	    panel.add(btnLogout, BorderLayout.EAST);

	    return panel;
	}
	
	private JPanel createMenuItem(String iconPath, String text, String section, boolean isActive) {
	    JPanel item = new JPanel(new BorderLayout());
	    item.setAlignmentX(Component.LEFT_ALIGNMENT);
	    item.setMaximumSize(new Dimension(250, 44));
	    item.setPreferredSize(new Dimension(250, 44));
	    item.setCursor(new Cursor(Cursor.HAND_CURSOR));

	    if (isActive) {
	        item.setOpaque(true);
	        item.setBackground(AppColors.PANEL);
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
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            controller.onMenuItemClick(section);
	        }

	        @Override
	        public void mouseEntered(MouseEvent e) {
	            if (!activeItem.equals(section)) {
	                item.setBackground(new Color(40, 43, 52));
	                item.repaint();
	            }
	        }

	        @Override
	        public void mouseExited(MouseEvent e) {
	            if (!activeItem.equals(section)) {
	                item.setBackground(AppColors.BACKGROUND);
	                item.repaint();
	            }
	        }
	    });

	    URL iconURL = getClass().getClassLoader().getResource(iconPath);
	    if (iconURL != null) {
	        Image img = new ImageIcon(iconURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	        item.add(new JLabel(new ImageIcon(img)), BorderLayout.WEST);
	    }

	    JLabel label = new JLabel(text);
	    label.setForeground(Color.WHITE);
	    label.setFont(new Font("SansSerif", Font.PLAIN, 14));
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
	    add(profileSection());
	    revalidate();
	    repaint();
	}
}
