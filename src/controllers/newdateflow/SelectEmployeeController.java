package controllers.newdateflow;

import java.util.function.Consumer;

import views.newdateflow.SelectEmplyeeView;

public class SelectEmployeeController {
    private SelectEmplyeeView view;
    private Consumer<Integer> onEmployeeSelected;

    public SelectEmployeeController(Consumer<Integer> onEmployeeSelected) {
        this.onEmployeeSelected = onEmployeeSelected;
        this.view = new SelectEmplyeeView(this);
    }

    public SelectEmplyeeView getView() {
        return view;
    }

    public void onEmployeeSelected(int employeeId) {
        if (onEmployeeSelected != null) {
            onEmployeeSelected.accept(employeeId);
        }
    }
}