package controllers;

import java.time.LocalDate;
import java.time.Month;

import views.CalenderView;

public class CalenderController {
	private CalenderView view;
	
	private Month actualMonth = LocalDate.now().getMonth();
	private int actualYear = LocalDate.now().getYear();
	
	public CalenderController() {
		this.view = new CalenderView(this);
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
}
