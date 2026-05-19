package controllers;

import javax.swing.JOptionPane;

import models.User;
import repository.RegisterRepository;
import views.RegisterView;

public class RegisterController {

	private RegisterView view;
	private RegisterRepository repository;
	
	public RegisterController() {
		this.view = new RegisterView(this);
		this.repository = new RegisterRepository();
	}
	
	public void onRegister() {	
		if (isValidateFields()) {
			if (repository.emailExists(view.getEmail())) {
				view.setErrorEmail("El correo ya está registrado");
				return;
			}
			
			User user = new User();
			user.setFirstName(view.getName());
			user.setLastName(view.getLastName());
			user.setEmail(view.getEmail());
			user.setDate(view.getDate());
			user.setGender(view.getGender());
			user.setPassword(view.getPassword());
			
			if (repository.saveUser(user)) {
				view.resetFields();
		    	view.resetErrorMsg();
		    	
		    	JOptionPane.showMessageDialog(
	                view,
	                "Usuario registrado exitosamente.",
	                "Registro",
	                JOptionPane.INFORMATION_MESSAGE
	            );
		    	
		    	new LoginController();
		    	view.dispose();
			} else {
				view.setErrorEmail("Error al registrar usuario");
			}
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
