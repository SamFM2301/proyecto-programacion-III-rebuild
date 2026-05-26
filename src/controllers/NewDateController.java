package controllers;

import views.NewDateView;

public class NewDateController {
	private NewDateView view;
	
	public NewDateController() {
		this.view = new NewDateView(this);
	}

	public NewDateView getView() {
		return view;
	}

	public void setView(NewDateView view) {
		this.view = view;
	}
	
	
}
