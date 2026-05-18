package controllers;

import views.HomeView;

public class HomeController {

	private HomeView view;
	
	public HomeController() {
		this.view = new HomeView(this);
	}
	
}
