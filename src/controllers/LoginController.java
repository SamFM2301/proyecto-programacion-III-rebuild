package controllers;

import views.LoginView;
import views.RegisterView;

public class LoginController {
	private LoginView view;
	
	public LoginController() {
		this.view = new LoginView(this);
	}

	public void onLogin() {
	    if (isValidateFields()) {
	    	System.out.println("Se inicio sesion");
	    	view.resetFields();
	    	view.resetErrorMsg();
	    }
	}
	
	public void onRegister() {
		view.resetFields();
		view.resetErrorMsg();
		
		new RegisterController();
		view.dispose();
	}
	
	private boolean isValidateFields() {
	    boolean emailOk = isValidEmail();
	    boolean passwordOk = isValidPassword();
	    
	    if(emailOk && passwordOk)
	    	return true;
	    
	    return false;
	}
	
	private boolean isValidEmail() {
		String email = view.getEmail();
		
		if (email.isEmpty()) {
	        view.setErrorEmail("El correo es requerido");
	        return false;
	    }
		
	    if (!email.contains("@")) {
	        view.setErrorEmail("Correo inválido");
	        return false;
	    }	
	    
	    view.setErrorEmail(" ");
	    return true;
	}
	
	private boolean isValidPassword() {
		String password = view.getPassword();
		
		if (password.isEmpty()) {
	        view.setErrorPassword("La contraseña es requerida");
	        return false;
	    }
		
		view.setErrorPassword(" ");
		return true;
	}
}
