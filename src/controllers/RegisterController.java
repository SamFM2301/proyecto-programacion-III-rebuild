package controllers;

import views.RegisterView;

public class RegisterController {

	private RegisterView view;
	
	public RegisterController() {
		this.view = new RegisterView(this);
	}
	
	public void onRegister() {	
		if (isValidateFields()) {
	    	System.out.println("Se registro el usuario");
	    	view.resetFields();
	    	view.resetErrorMsg();
	    	
	    	new LoginController();
	    	view.dispose();
	    }
	}
	
	public void onLogin() {
		new LoginController();
		view.dispose();
	}
	
	private boolean isValidateFields() {
		boolean nameOk = isValidName();
		boolean lastNameOk = isValidLastName();
	    boolean emailOk = isValidEmail();
	    boolean dateOk = isValidDate();
	    boolean genderOk = isValidGender();
	    boolean passwordOk = isValidPassword();
	    boolean confirmPasswordOk = isValidConfirmPassword();
	    
	    if(nameOk && lastNameOk && emailOk && dateOk && genderOk && passwordOk && confirmPasswordOk)
	    	return true;
	    
	    return false;
	}
	
	private boolean isValidName() {
	    String name = view.getName();

	    if (name.isEmpty()) {
	        view.setErrorName("El nombre es requerido");
	        return false;
	    }

	    if (name.length() < 2) {
	        view.setErrorName("Nombre muy corto");
	        return false;
	    }

	    view.setErrorName(" ");
	    return true;
	}
	
	private boolean isValidLastName() {
	    String lastName = view.getLastName();

	    if (lastName.isEmpty()) {
	        view.setErrorLastName("El apellido es requerido");
	        return false;
	    }

	    if (lastName.length() < 2) {
	        view.setErrorLastName("Apellido muy corto");
	        return false;
	    }

	    view.setErrorLastName(" ");
	    return true;
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
	
	private boolean isValidDate() {
	    if (view.getDayIndex() == 0 || view.getMonthIndex() == 0 || !view.isYearSelected()) {
	        view.setErrorDate("Ingrese una fecha valida");
	        return false;
	    }

	    view.setErrorDate(" ");
	    return true;
	}
	
	private boolean isValidGender() {
		
		if (view.getGender() == null) {
			view.setErrorGender("Eliga una opcion");
			return false;
		}
		
		view.setErrorGender("");
		return true;
	}
	
	private boolean isValidPassword() {
		String password = view.getPassword();
		
		if (password.isEmpty()) {
	        view.setErrorPassword("La contraseña es requerida");
	        return false;
	    }
		
		if (password.length() < 8) {
			view.setErrorPassword("La contraseña debe tener al menos 8 caracteres");
			return false;
		}
		
		view.setErrorPassword(" ");
		return true;
	}
	
	private boolean isValidConfirmPassword() {
		String password = view.getPassword();
		String confirmPassword = view.getConfirmPassword();
		
		if (confirmPassword.isEmpty()) {
			view.setErrorConfirmPassword("Confirme la contraseña");
			return false;
		}
		
		if (!password.equals(confirmPassword)) {
			view.setErrorConfirmPassword("Las contraseñas no coinciden");
			return false;
		}
		
		view.setErrorConfirmPassword(" ");
		return true;
	}
}
