package controllers.newdateflow;

import java.util.function.Consumer;

import views.newdateflow.SelectServiceView;

public class SelectServiceController {
    private SelectServiceView view;
    private Consumer<Integer> onServiceSelected;

    public SelectServiceController(Consumer<Integer> onServiceSelected) {
        this.onServiceSelected = onServiceSelected;
        this.view = new SelectServiceView(this);
    }

    public SelectServiceView getView() {
        return view;
    }

    public void onServiceSelected(int serviceId) {
        if (onServiceSelected != null) {
            onServiceSelected.accept(serviceId);
        }
    }
}