package controllers.newdateflow;

import java.time.LocalTime;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import views.newdateflow.SelectDateTimeView;

public class SelectDateTimeController {
    private SelectDateTimeView view;
    private TriConsumer<Integer, Integer, Integer> onDateSelected;
    private Consumer<LocalTime> onTimeSelected;

    public SelectDateTimeController(TriConsumer<Integer, Integer, Integer> onDateSelected, Consumer<LocalTime> onTimeSelected) {
        this.onDateSelected = onDateSelected;
        this.onTimeSelected = onTimeSelected;
        this.view = new SelectDateTimeView(this);
    }

    public SelectDateTimeView getView() {
        return view;
    }

    public void onDateSelected(int day, int month, int year) {
        if (onDateSelected != null) {
            onDateSelected.accept(day, month, year);
        }
    }

    public void onTimeSelected(LocalTime time) {
        if (onTimeSelected != null) {
            onTimeSelected.accept(time);
        }
    }

    public void setBlockedTimes(Set<String> times) {
        view.setBlockedTimes(times);
    }
}