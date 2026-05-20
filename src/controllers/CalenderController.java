package controllers;

import views.CalenderView;

public class CalenderController {
	private CalenderView view;
	
	public CalenderController() {
		this.view = new CalenderView(this);
	}

	public CalenderView getView() {
		return view;
	}

	public void setView(CalenderView view) {
		this.view = view;
	}
}
