package controllers;

import java.time.LocalDate;
import java.time.Month;

import utils.Session;
import views.CalenderView;
import views.HomeView;

public class CalenderController {
	private CalenderView view;
	private HomeView homeView;

	private Month actualMonth = LocalDate.now().getMonth();
	private int actualYear = LocalDate.now().getYear();

	public CalenderController() {
		this(null);
	}

	public CalenderController(HomeView homeView) {
		this.homeView = homeView;
		int userId = Session.getCurrentUser() != null ? Session.getCurrentUser().getId() : 0;
		this.view = new CalenderView(this, userId, homeView);
	}

	public void nextMonth() {
	    if (actualMonth == Month.DECEMBER) {
	        actualMonth = Month.JANUARY;
	        actualYear++;
	    } else {
	        actualMonth = actualMonth.plus(1);
	    }
	    view.refreshCalendar();
	}

	public void previousMonth() {
	    if (actualMonth == Month.JANUARY) {
	        actualMonth = Month.DECEMBER;
	        actualYear--;
	    } else {
	        actualMonth = actualMonth.minus(1);
	    }
	    view.refreshCalendar();
	}

	public Month getActualMonth() {
	    return actualMonth;
	}

	public int getActualYear() {
	    return actualYear;
	}

	public CalenderView getView() {
		return view;
	}

	public void setView(CalenderView view) {
		this.view = view;
	}

	public void navigateToNewDate() {
		if (homeView != null) {
			homeView.navigateToSection("nueva_cita");
		}
	}
}