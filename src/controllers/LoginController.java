package controllers;

import javax.swing.*;

import models.User;
import repository.LoginRepository;
import utils.Session;
import views.LoginView;

public class LoginController {
    private LoginView view;
    private LoginRepository repository;

    public LoginController() {
        this.view = new LoginView(this);
        this.repository = new LoginRepository();
    }

    public void onLogin() {
        if (!isValidateFields()) return;

        User user = repository.login(view.getEmail(), view.getPassword());

        if (user == null) {
            view.setErrorPassword("Correo o contraseña incorrectos");
            return;
        }
        Session.setCurrentUser(user);

        new HomeController();
        view.dispose();
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

        if (emailOk && passwordOk)
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
