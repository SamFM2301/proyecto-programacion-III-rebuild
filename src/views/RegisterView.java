package views;

import java.awt.*;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import components.RoundedButton;
import components.RoundedPasswordField;
import components.RoundedTextField;
import controllers.LoginController;
import controllers.RegisterController;
import utils.AppColors;
import utils.AppFonts;

public class RegisterView extends JFrame{

	private RegisterController controller;
	
	private RoundedTextField txtName;
	private RoundedTextField txtLastName;
	
	private JComboBox<String> cmbDay;
    private JComboBox<String> cmbMonth;
    private JComboBox<String> cmbYear;
	
	private RoundedTextField txtEmail;
	private RoundedPasswordField txtPassword;
	private RoundedPasswordField txtConfirmPassword;
	
	private JRadioButton rbMan;
    private JRadioButton rbWomen;
    private JRadioButton rbOther;
    private ButtonGroup bgGender;
	
	private JLabel lblErrorName;
	private JLabel lblErrorLastName;
	private JLabel lblErrorEmail;
	private JLabel lblErrorDate;
	private JLabel lblErrorGender;
	private JLabel lblErrorPassword;
	private JLabel lblErrorConfirmPassword;
	
	public RegisterView(RegisterController controller) {
		this.controller = controller;
		initFrame();
		initComponents();
	}
	
	private void initFrame() {
		setTitle("Crear cuenta");
        
        URL iconURL = getClass().getClassLoader().getResource("assets/icons/iniciosesion.png");
        Image icon = Toolkit.getDefaultToolkit().getImage(iconURL);
        setIconImage(icon);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 680));
        setResizable(true);
        setLocationRelativeTo(null);
	}
	
	private void initComponents() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(AppColors.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(24, 24, 24, 24));
        
        mainPanel.add(createLeftPanel("assets/img/welcome.png"), BorderLayout.WEST);
        mainPanel.add(createRegisterPanel(), BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
	}
	
	private JPanel createLeftPanel(String imagePath) {
		URL imgURL = getClass().getClassLoader().getResource(imagePath);
	    Image image = new ImageIcon(imgURL).getImage();

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panel.setPreferredSize(new Dimension(300, 520));
        return panel;
    }
	
	private JPanel createRegisterPanel() {
		JPanel registerPanel = new JPanel();
		registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.Y_AXIS));
		registerPanel.setBackground(AppColors.PANEL);
		registerPanel.setBorder(new EmptyBorder(10, 30, 10, 30));
		registerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        
        // TITLE
        JLabel lblTitle = new JLabel("Registro de usuario");
        lblTitle.setFont(AppFonts.title());
        lblTitle.setForeground(AppColors.TEXT_LIGHT);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, lblTitle.getPreferredSize().height));
		
        txtName = new RoundedTextField(8);
        lblErrorName = new JLabel();

        txtLastName = new RoundedTextField(8);
        lblErrorLastName = new JLabel();
        
        // CORREO
        JLabel lblEmail = new JLabel("Correo: *");
        txtEmail = new RoundedTextField(8);
        lblErrorEmail = new JLabel();
        
        JLabel lblDate = new JLabel("Fecha de nacimiento *");
        lblDate.setFont(AppFonts.bold(12));
        lblDate.setForeground(AppColors.TEXT_LIGHT);
        lblDate.setAlignmentX(LEFT_ALIGNMENT);
        lblErrorDate = new JLabel();
        
        JLabel lblPassword = new JLabel("Contraseña: *");
        txtPassword = new RoundedPasswordField(8);
        lblErrorPassword = new JLabel();
        
        JLabel lblConfirmPassword = new JLabel("Confirmar contraseña: *");
        txtConfirmPassword = new RoundedPasswordField(8);
        lblErrorConfirmPassword = new JLabel();
        
        JButton btnRegister = createButton(
                "Registrarse", 
                AppColors.YELLOW, 
                AppColors.TEXT_DARK,
                15
        );
        
        JButton btnLoginIn = createButton(
                "¿Ya tienes una cuenta? Inicia sesión aquí", 
                AppColors.FIELDS, 
                AppColors.TEXT_LIGHT,
                11
        );
        
        btnRegister.addActionListener(e -> controller.onRegister());
        btnLoginIn.addActionListener(e -> controller.onLogin());
        
        registerPanel.add(Box.createVerticalStrut(10));
        registerPanel.add(lblTitle);
        registerPanel.add(Box.createVerticalStrut(10));
        
        registerPanel.add(createNamePanel());
        
        registerPanel.add(createPanelField(txtEmail, lblEmail, lblErrorEmail));
        
        registerPanel.add(lblDate);
        registerPanel.add(Box.createVerticalStrut(5));
        registerPanel.add(createDatePanel());
        registerPanel.add(Box.createVerticalStrut(2));
        registerPanel.add(lblErrorDate);
        registerPanel.add(Box.createVerticalStrut(5));
        
        registerPanel.add(createGenderPanel());
        registerPanel.add(Box.createVerticalStrut(5));

        registerPanel.add(createPanelField(txtPassword, lblPassword, lblErrorPassword));
    
        registerPanel.add(createPanelField(txtConfirmPassword, lblConfirmPassword, lblErrorConfirmPassword));
        
        registerPanel.add(Box.createVerticalStrut(10));
        registerPanel.add(btnRegister);
        registerPanel.add(Box.createVerticalStrut(10));
		registerPanel.add(btnLoginIn);
        
        return registerPanel;
	}
	
	private JPanel createPanelField(JComponent field, JLabel label, JLabel error) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(LEFT_ALIGNMENT);
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
	
	private JPanel createNamePanel() {
	    JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
	    panel.setOpaque(false);
	    panel.setAlignmentX(LEFT_ALIGNMENT);
	    panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

	    panel.add(createPanelField(txtName, new JLabel("Nombre *"), lblErrorName));
	    panel.add(createPanelField(txtLastName, new JLabel("Apellido *"), lblErrorLastName));

	    return panel;
	}
	
	private JPanel createDatePanel() {
	    JPanel panel = new JPanel(new GridLayout(1, 3, 10, 0));
	    panel.setOpaque(false);
	    panel.setAlignmentX(LEFT_ALIGNMENT);
	    panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

	    // Dias
	    String[] days = new String[32];
	    days[0] = "Día";
	    for (int i = 1; i <= 31; i++) days[i] = String.valueOf(i);
	    cmbDay = new JComboBox<>(days);

	    // Meses
	    String[] months = {
    		"Mes", "Enero", "Febrero", "Marzo", "Abril", "Mayo", 
            "Junio", "Julio", "Agosto", "Septiembre", "Octubre", 
            "Noviembre", "Diciembre"
	    };
	    
	    cmbMonth = new JComboBox<>(months);

	    // Años
	    int currentYear = java.time.Year.now().getValue();
	    String[] years = new String[101];
	    years[0] = "Año";
	    for (int i = 1; i <= 100; i++) years[i] = String.valueOf(currentYear - i + 1);
	    cmbYear = new JComboBox<>(years);

	    // Estilo
	    for (JComboBox<?> cmb : new JComboBox[]{cmbDay, cmbMonth, cmbYear}) {
	        cmb.setBackground(AppColors.FIELDS);
	        cmb.setForeground(AppColors.TEXT_LIGHT);
	        cmb.setFont(AppFonts.bold(12));
	    }
	    
	    lblErrorDate.setFont(AppFonts.small());
        lblErrorDate.setForeground(Color.RED);
        lblErrorDate.setText(" ");
        lblErrorDate.setAlignmentX(LEFT_ALIGNMENT);

	    panel.add(cmbDay);
	    panel.add(cmbMonth);
	    panel.add(cmbYear);

	    return panel;
	}
	
	private JPanel createGenderPanel() {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);
	    panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
	    panel.setAlignmentX(LEFT_ALIGNMENT);

	    JLabel lblGender = new JLabel("Sexo: *");
	    lblGender.setFont(AppFonts.bold(12));
	    lblGender.setForeground(AppColors.TEXT_LIGHT);
	    lblGender.setAlignmentX(LEFT_ALIGNMENT);

	    rbMan = new JRadioButton("Hombre");
	    rbWomen = new JRadioButton("Mujer");
	    rbOther = new JRadioButton("Otro");

	    bgGender = new ButtonGroup();
	    bgGender.add(rbMan);
	    bgGender.add(rbWomen);
	    bgGender.add(rbOther);

	    JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
	    optionsPanel.setOpaque(false);
	    optionsPanel.setAlignmentX(LEFT_ALIGNMENT);
	    
	    lblErrorGender = new JLabel();
	    
	    lblErrorGender.setFont(AppFonts.small());
	    lblErrorGender.setForeground(Color.RED);
	    lblErrorGender.setText(" ");
	    lblErrorGender.setAlignmentX(LEFT_ALIGNMENT);

	    for (JRadioButton rb : new JRadioButton[]{rbMan, rbWomen, rbOther}) {
	        rb.setFont(AppFonts.bold(13));
	        rb.setForeground(AppColors.TEXT_LIGHT);
	        rb.setOpaque(false);
	        optionsPanel.add(rb);
	    }

	    panel.add(lblGender);
	    panel.add(Box.createVerticalStrut(2));
	    panel.add(optionsPanel);
	    panel.add(Box.createVerticalStrut(2));
	    panel.add(lblErrorGender);

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
	
	public String getName() { 
		return txtName.getText().trim();
	}
	
	public String getLastName() { 
		return txtLastName.getText().trim(); 
	}
	
	public String getEmail() { 
		return txtEmail.getText().trim(); 
	}
	
	public int getDayIndex() { 
		return cmbDay.getSelectedIndex(); 
	}
	
	public int getMonthIndex() { 
		return cmbMonth.getSelectedIndex(); 
	}
	
	public boolean isYearSelected() { 
		return !cmbYear.getSelectedItem().equals("Año"); 
	}
	
	public String getDate() {
	    int day = cmbDay.getSelectedIndex();
	    int month = cmbMonth.getSelectedIndex();
	    String year = (String) cmbYear.getSelectedItem();
	    
	    return String.format("%s-%02d-%02d", year, month, day);
	}
	
	public String getGender() {
	    if (rbMan.isSelected()) return "Hombre";
	    if (rbWomen.isSelected()) return "Mujer";
	    if (rbOther.isSelected()) return "Otro";
	    return null;
	}
	
	public String getPassword() {
		return new String(txtPassword.getPassword()); 
	}
	
	public String getConfirmPassword() { 
		return new String(txtConfirmPassword.getPassword()); 
	}

	// Setters de error
	public void setErrorName(String msg) { 
		lblErrorName.setText(msg); 
	}
	
	public void setErrorLastName(String msg) { 
		lblErrorLastName.setText(msg); 
	}
	
	public void setErrorEmail(String msg) { 
		lblErrorEmail.setText(msg); 
	}
	
	public void setErrorDate(String msg) {
		lblErrorDate.setText(msg);
	}
	
	public void setErrorGender(String msg) {
		lblErrorGender.setText(msg);
	}
	
	public void setErrorPassword(String msg) { 
		lblErrorPassword.setText(msg); 
	}

	public void setErrorConfirmPassword(String msg) {
		lblErrorConfirmPassword.setText(msg);
	}
	
	// Reset
	public void resetFields() {
	    txtName.setText("");
	    txtLastName.setText("");
	    txtEmail.setText("");
	    txtPassword.setText("");
	    txtConfirmPassword.setText("");
	    cmbDay.setSelectedIndex(0);
	    cmbMonth.setSelectedIndex(0);
	    cmbYear.setSelectedIndex(0);
	}

	public void resetErrorMsg() {
	    lblErrorName.setText(" ");
	    lblErrorLastName.setText(" ");
	    lblErrorEmail.setText(" ");
	    lblErrorDate.setText(" ");
	    lblErrorPassword.setText(" ");
	    lblErrorConfirmPassword.setText(" ");
	}
}
