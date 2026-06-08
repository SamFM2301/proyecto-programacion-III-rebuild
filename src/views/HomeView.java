package views;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.*;

import controllers.HomeController;
import controllers.SideMenuController;
import controllers.CalenderController;
import controllers.NewDateController;
import utils.AppColors;

public class HomeView extends JFrame {
    private static final long serialVersionUID = 1L;

    private HomeController controller;
    private JPanel currentView;
    private JLayeredPane mainPanel;
    private JPanel contentWrapper;
    private SideMenuController sideMenuController;

    public HomeView(HomeController controller) {
        this.controller = controller;

        initFrame();
        initComponents();
    }

    private void initFrame() {
        setTitle("Ventana Principal");

        URL iconURL = getClass().getClassLoader().getResource("assets/img/logo_icono.png");
        Image icon = Toolkit.getDefaultToolkit().getImage(iconURL);
        setIconImage(icon);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(
                    HomeView.this,
                    "¿Estás seguro de que deseas salir?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                );
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        setMinimumSize(new Dimension(1280, 720));
        setResizable(true);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        mainPanel = new JLayeredPane();
        mainPanel.setBackground(AppColors.PANEL);

        contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setBackground(AppColors.PANEL);
        contentWrapper.setName("contentWrapper");
        contentWrapper.setBounds(0, 0, 1280, 720);

        sideMenuController = new SideMenuController(this);
        contentWrapper.add(sideMenuController.getView(), BorderLayout.WEST);
        currentView = new CalenderController(this).getView();
        contentWrapper.add(currentView, BorderLayout.CENTER);

        mainPanel.add(contentWrapper, Integer.valueOf(0));
        mainPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                contentWrapper.setBounds(0, 0, mainPanel.getWidth(), mainPanel.getHeight());
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    public void setCurrentView(JPanel view) {
        contentWrapper.remove(currentView);
        currentView = view;
        contentWrapper.add(currentView, BorderLayout.CENTER);
        contentWrapper.revalidate();
        contentWrapper.repaint();
    }

    public void navigateToSection(String section) {
        JPanel newView = null;
        String viewSection = section;

        switch (section) {
            case "mis_citas":
                newView = new CalenderController(this).getView();
                break;
            case "nueva_cita":
                newView = new NewDateController(this).getView();
                break;
            case "perfil":
                newView = new ProfileView(null);
                break;
        }

        if (newView != null) {
            setCurrentView(newView);
            sideMenuController.getView().setActiveItem(viewSection);
        }
    }

    public void setSideMenuController(SideMenuController controller) {
        this.sideMenuController = controller;
    }

    public JLayeredPane getMainPanel() {
        return mainPanel;
    }
}