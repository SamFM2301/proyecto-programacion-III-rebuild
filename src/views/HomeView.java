package views;

import java.awt.*;
import java.net.URL;

import javax.swing.*;

import controllers.HomeController;
import controllers.SideMenuController;
import utils.AppColors;

public class HomeView extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HomeController controller;
    private JPanel currentView;
    private JPanel mainPanel;

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

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1280, 720));
        setResizable(true);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(AppColors.PANEL);

        SideMenuController sideMenuController = new SideMenuController(this);
        mainPanel.add(sideMenuController.getView(), BorderLayout.WEST);

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // ESTE METODO CAMBIA LA VISTA DEL CENTRO
    public void setCurrentView(JPanel view) {
        if (currentView != null) {
            mainPanel.remove(currentView);
        }
        
        currentView = view;
        mainPanel.add(currentView, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}