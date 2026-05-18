package controllers;

import javax.swing.JOptionPane;

import views.HomeView;
import views.SideMenuView;

public class SideMenuController {
    private SideMenuView view;
    private HomeView homeView;

    public SideMenuController(HomeView homeView) {
        this.homeView = homeView;
        this.view = new SideMenuView(this);
    }

    public SideMenuView getView() {
        return view;
    }

    public void onMenuItemClick(String section) {
        view.setActiveItem(section);

        switch (section) {
            case "mis_citas":
                // homeView.setCurrentView(new MisCitasView());
                break;
            case "nueva_cita":
                // homeView.setCurrentView(new NuevaCitaView());
                break;
            case "perfil":
                // homeView.setCurrentView(new PerfilView());
                break;
            case "configuracion":
                // homeView.setCurrentView(new ConfiguracionView());
                break;
        }
    }
    
    public void logOut() {
    	int result = JOptionPane.showConfirmDialog(
		    homeView,
		    "¿Estás seguro de que deseas cerrar sesión?",
		    "Confirmar",
		    JOptionPane.YES_NO_OPTION
		);

		if (result == JOptionPane.YES_OPTION) {
		    homeView.dispose();
		    
		    new LoginController();
		}
    }
}
