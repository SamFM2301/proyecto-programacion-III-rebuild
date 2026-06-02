package controllers.newdateflow;

import views.newdateflow.ConfirmDateView;

public class ConfirmDateController {
    private ConfirmDateView view;

    public ConfirmDateController() {
        this.view = new ConfirmDateView(this);
    }

    public ConfirmDateView getView() {
        return view;
    }

    public void onConfirm() {
        // Logic to confirm the appointment
    }

    public void onBack() {
        // Logic to go back to previous step
    }
}