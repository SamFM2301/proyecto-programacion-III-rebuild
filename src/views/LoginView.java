package views;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.net.URL;

import components.RoundedButton;
import components.RoundedPasswordField;
import components.RoundedTextField;
import controllers.LoginController;
import utils.AppColors;
import utils.AppFonts;

public class LoginView extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LoginController controller;
	
	private RoundedTextField txtEmail;
	private RoundedPasswordField txtPassword;
	
	private JLabel lblErrorEmail;
	private JLabel lblErrorPassword;
	
	public LoginView(LoginController controller) {
		this.controller = controller;
		initFrame();
		initComponents();
	}
	
	private void initFrame() {
		setTitle("Inicio de sesion");
        
        URL iconURL = getClass().getClassLoader().getResource("assets/img/logo_icono.png");
        Image icon = Toolkit.getDefaultToolkit().getImage(iconURL);
        setIconImage(icon);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(840, 480));
        setResizable(true);
        setLocationRelativeTo(null);
	}
	
	private void initComponents() {
		JPanel mainPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.setBackground(AppColors.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(24, 24, 24, 24));
        
        mainPanel.add(createLeftPanel("assets/img/welcome.png"));
        mainPanel.add(createLoginPanel());
        
        add(mainPanel);
        setVisible(true);
	}
	
	private JPanel createLeftPanel(String imagePath) {
		URL imgURL = getClass().getClassLoader().getResource(imagePath);
	    Image image = new ImageIcon(imgURL).getImage();

        JPanel panel = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panel.setPreferredSize(new Dimension(390, 420));
        return panel;
    }
	
	private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(AppColors.PANEL);
        loginPanel.setBorder(new EmptyBorder(35, 55, 35, 55));
        loginPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        
        // TITLE
        JLabel lblTitle = new JLabel("Inicio de Sesión");
        lblTitle.setFont(AppFonts.title());
        lblTitle.setForeground(AppColors.TEXT_LIGHT);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, lblTitle.getPreferredSize().height));
        
        JLabel lblEmail = new JLabel("Correo: *");
        txtEmail = new RoundedTextField(8);
        lblErrorEmail = new JLabel();
        
        JLabel lblPassword = new JLabel("Contraseña: *");
        txtPassword = new RoundedPasswordField(8);
        lblErrorPassword = new JLabel();
        
        // LOGIN BUTTON
        JButton btnLoginIn = createButton(
            "Iniciar Sesión", 
            AppColors.YELLOW, 
            AppColors.TEXT_DARK,
            15
        );
        
        // HOVER DEL LOGIN BUTTON
        btnLoginIn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                changeBackground(btnLoginIn);
            }
            
            public void mouseExited(MouseEvent e) {
                resetBackground(btnLoginIn);
            }
        });
        
        // REGISTER BUTTON
        JButton btnRegister = createButton(
            "¿No tienes una cuenta? Regístrate aquí", 
            AppColors.FIELDS, 
            AppColors.TEXT_LIGHT,
            11
        );
        
        // HOVER DEL REGISTER BUTTON
        btnRegister.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                changeBackground(btnRegister);
            }
            
            public void mouseExited(MouseEvent e) {
                resetBackground(btnRegister);
            }
        });
        
        
        btnLoginIn.addActionListener(e -> controller.onLogin());
        btnRegister.addActionListener(e -> controller.onRegister());
        
        // ADD COMPONENTS
        loginPanel.add(lblTitle);
        loginPanel.add(Box.createVerticalStrut(10));
        
        loginPanel.add(createPanelField(txtEmail, lblEmail, lblErrorEmail));
        loginPanel.add(Box.createVerticalStrut(15));
        
        loginPanel.add(createPanelField(txtPassword, lblPassword, lblErrorPassword));
        loginPanel.add(Box.createVerticalStrut(30));
        
        loginPanel.add(btnLoginIn);
        loginPanel.add(Box.createVerticalStrut(10));
        loginPanel.add(btnRegister);
        
        return loginPanel;
    }
    
    private void changeBackground(JComponent c) {
        Color color = c.getBackground();
        
        if (color.equals(AppColors.YELLOW))
            c.setBackground(AppColors.YELLOW_HOVER);
        else 
            c.setBackground(AppColors.FIELDS_HOVER);
    }
    
    private void resetBackground(JComponent c) {
        Color color = c.getBackground();
        
        if (color.equals(AppColors.YELLOW_HOVER))
            c.setBackground(AppColors.YELLOW);
        else 
            c.setBackground(AppColors.FIELDS);
    }
    
    private JPanel createPanelField(JComponent field, JLabel label, JLabel error) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        
        label.setFont(AppFonts.bold(12));
        label.setForeground(AppColors.TEXT_LIGHT);
        label.setAlignmentX(LEFT_ALIGNMENT);
        
        field.setBackground(AppColors.FIELDS);
        field.setForeground(AppColors.TEXT_LIGHT);
        field.setBorder(new EmptyBorder(5, 6, 5, 6));
        field.setFont(AppFonts.bold(12));
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setMinimumSize(new Dimension(0, 35));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        
        error.setFont(AppFonts.small());
        error.setForeground(Color.RED);
        error.setText(" ");
        error.setAlignmentX(LEFT_ALIGNMENT);
        
        panel.add(Box.createVerticalStrut(2));
        panel.add(label);
        panel.add(Box.createVerticalStrut(2));
        panel.add(field);
        panel.add(Box.createVerticalStrut(2));
        panel.add(error);
        
        return panel;
    }
    
    private JButton createButton(String text, Color background, Color textColor, int textSize) {
        RoundedButton button = new RoundedButton(text, 8);
        
        button.setFont(AppFonts.bold(textSize));
        button.setBackground(background);
        button.setForeground(textColor);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        
        return button;
    }
    
    public void resetErrorMsg() {
        lblErrorEmail.setText(" ");
        lblErrorPassword.setText(" ");
    }
    
    public void resetFields() {
        txtEmail.setText("");
        txtPassword.setText("");
    }

    public String getEmail() { 
    	return txtEmail.getText().trim(); 
    }
    
    public String getPassword() { 
    	return new String(txtPassword.getPassword()); 
    }

    public void setErrorEmail(String msg) { 
    	lblErrorEmail.setText(msg); 
    }
    
    public void setErrorPassword(String msg) { 
    	lblErrorPassword.setText(msg); 
    }
    
    
}
