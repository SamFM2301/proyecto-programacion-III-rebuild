package controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import controllers.newdateflow.ConfirmDateController;
import controllers.newdateflow.SelectDateTimeController;
import controllers.newdateflow.SelectEmployeeController;
import controllers.newdateflow.SelectServiceController;
import models.Appointment;
import models.User;
import repository.AppointmentRepository;
import repository.NewDateRepository;
import utils.Session;
import views.NewDateView;

public class NewDateController {

    private NewDateView view;

    private SelectServiceController selectServiceController;
    private SelectEmployeeController selectEmployeeController;
    private SelectDateTimeController selectDateTimeController;
    private ConfirmDateController confirmDateController;

    private NewDateRepository repository;
    private AppointmentRepository appointmentRepository;

    private int currentStep;

    private Integer selectedServiceId;
    private String selectedServiceName;
    private double selectedServicePrice;
    private int selectedServiceDuration;

    private Integer selectedEmployeeId;
    private String selectedEmployeeName;

    private int selectedDay;
    private int selectedMonth;
    private int selectedYear;
    private LocalTime selectedTime;

    public NewDateController() {
        this.repository = new NewDateRepository();
        this.appointmentRepository = new AppointmentRepository();
        this.currentStep = 0;

        this.selectServiceController = new SelectServiceController(serviceId -> onServiceSelected(serviceId));
        this.selectEmployeeController = new SelectEmployeeController(employeeId -> onEmployeeSelected(employeeId));
        this.selectDateTimeController = new SelectDateTimeController(
            (day, month, year) -> onDateSelected(day, month, year),
            time -> onTimeSelected(time)
        );
        this.confirmDateController = new ConfirmDateController();

        this.view = new NewDateView(this);

        loadServices();
    }

    private void loadDataForStep(int step) {
        switch (step) {
            case 0:
                loadServices();
                break;
            case 1:
                loadEmployees();
                break;
            case 2:
                loadBlockedTimes();
                break;
            case 3:
                updateConfirmData();
                break;
        }
    }

    private void loadServices() {
        selectServiceController.getView().clearServices();
        var services = repository.getAllServices();

        for (var service : services) {
            selectServiceController.getView().addService(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getPrice(),
                service.getDuration() + " min"
            );
        }

        if (selectedServiceId != null && selectedServiceName != null) {
            selectServiceController.getView().setSelectedServiceId(
                selectedServiceId, selectedServiceName, selectedServicePrice
            );
        }
    }

    private void loadEmployees() {
        selectEmployeeController.getView().clearEmployees();
        var employees = repository.getAllEmployees();

        for (var employee : employees) {
            selectEmployeeController.getView().addEmployee(
                employee.getId(),
                employee.getFirstName() + " " + employee.getLastName(),
                "Barbero",
                null
            );
        }

        if (selectedEmployeeId != null && selectedEmployeeName != null) {
            selectEmployeeController.getView().setSelectedEmployeeId(
                selectedEmployeeId, selectedEmployeeName
            );
        }
    }

    private void loadBlockedTimes() {
        if (selectedEmployeeId == null || selectedDay == 0) {
            selectDateTimeController.setBlockedTimes(Collections.emptySet());
            return;
        }

        LocalDate date = LocalDate.of(selectedYear, selectedMonth, selectedDay);
        List<Appointment> appointments = appointmentRepository.getAppointmentsByEmployeeAndDate(selectedEmployeeId, date);

        Set<String> computedBlocked = new HashSet<>();
        String[] allTimes = {
            "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
            "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
            "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
            "18:00", "18:30", "19:00", "19:30", "20:00", "20:30"
        };

        for (String timeStr : allTimes) {
            LocalTime slotStart = LocalTime.parse(timeStr);
            LocalTime slotEnd = slotStart.plusMinutes(30);

            for (Appointment apt : appointments) {
                LocalTime aptStart = apt.getStartTime();
                LocalTime aptEnd = apt.getEndTime();
                if (slotStart.isBefore(aptEnd) && slotEnd.isAfter(aptStart)) {
                    computedBlocked.add(timeStr);
                    break;
                }
            }
        }

        if (selectedTime != null && computedBlocked.contains(String.format("%02d:%02d", selectedTime.getHour(), selectedTime.getMinute()))) {
            selectedTime = null;
        }

        selectDateTimeController.setBlockedTimes(computedBlocked);
    }

    public NewDateView getView() {
        return view;
    }

    public SelectServiceController getSelectServiceController() {
        return selectServiceController;
    }

    public SelectEmployeeController getSelectEmployeeController() {
        return selectEmployeeController;
    }

    public SelectDateTimeController getSelectDateTimeController() {
        return selectDateTimeController;
    }

    public ConfirmDateController getConfirmDateController() {
        return confirmDateController;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void nextStep() {
        if (currentStep < 3) {
            currentStep++;
            view.setCurrentStep(currentStep);
            loadDataForStep(currentStep);
        }
    }

    public void previousStep() {
        if (currentStep > 0) {
            currentStep--;
            view.setCurrentStep(currentStep);
        }
    }

    public boolean canProceedFromService() {
        return selectedServiceId != null;
    }

    public boolean canProceedFromEmployee() {
        return selectedEmployeeId != null;
    }

    public boolean canProceedFromDateTime() {
        return selectedDay > 0 && selectedTime != null;
    }

    public void onServiceSelected(int serviceId) {
        var service = repository.getServiceById(serviceId);
        if (service != null) {
            selectedServiceId = serviceId;
            selectedServiceName = service.getName();
            selectedServicePrice = service.getPrice();
            selectedServiceDuration = service.getDuration();

        view.updateServiceSelection(selectedServiceName, selectedServicePrice);
        view.setSideActionButtonEnabled(true);
        }
    }

    private void updateConfirmData() {
        view.updateServiceSelection(selectedServiceName, selectedServicePrice);
        view.updateEmployeeSelection(selectedEmployeeName);
        view.updateDateTimeSelection(getSidePanelDateString(), getSidePanelTimeRangeString());
    }

    public void onEmployeeSelected(int employeeId) {
        var employee = repository.getEmployeeById(employeeId);
        if (employee != null) {
            selectedEmployeeId = employeeId;
            selectedEmployeeName = employee.getFirstName() + " " + employee.getLastName();

            view.updateEmployeeSelection(selectedEmployeeName);
            view.setSideActionButtonEnabled(true);
        }
    }

    public void onDateSelected(int day, int month, int year) {
        this.selectedDay = day;
        this.selectedMonth = month;
        this.selectedYear = year;
        this.selectedTime = null;

        String dateStr = getSidePanelDateString();
        view.updateDateTimeSelection(dateStr, "-");

        view.setSideActionButtonEnabled(false);

        loadBlockedTimes();
    }

    public void onTimeSelected(LocalTime time) {
        this.selectedTime = time;

        String dateStr = getSidePanelDateString();
        String timeStr = getSidePanelTimeRangeString();
        view.updateDateTimeSelection(dateStr, timeStr);

        view.setSideActionButtonEnabled(canProceedFromDateTime());
    }

    public void confirmAppointment() {
        User currentUser = Session.getCurrentUser();
        if (currentUser == null) {
            view.showResultView(false, null, null, null, null);
            return;
        }

        boolean success = repository.saveAppointment(
            currentUser.getId(),
            selectedServiceId,
            selectedEmployeeId,
            selectedYear,
            selectedMonth,
            selectedDay,
            selectedTime,
            selectedServiceDuration
        );

        String priceStr = String.format("$%.0f MXN", selectedServicePrice);
        String dateStr = getSelectedDateString();
        String timeStr = getSelectedTimeString();
        String dateTimeStr = dateStr + ", " + timeStr;

        if (success) {
            view.showResultView(true,
                selectedServiceName,
                selectedEmployeeName,
                dateTimeStr,
                priceStr
            );
        } else {
            view.showResultView(false, null, null, null, null);
        }
    }

    public void onResultAction() {
        resetFlow();
    }

    public void resetFlow() {
        currentStep = 0;
        selectedServiceId = null;
        selectedServiceName = null;
        selectedServicePrice = 0;
        selectedServiceDuration = 0;
        selectedEmployeeId = null;
        selectedEmployeeName = null;
        selectedDay = 0;
        selectedMonth = 0;
        selectedYear = 0;
        selectedTime = null;

        view.setCurrentStep(0);
    }

    public String getSelectedDateString() {
        if (selectedDay == 0) return "-";

        String monthName = java.time.Month.of(selectedMonth)
            .getDisplayName(TextStyle.FULL, Locale.of("es", "MX"));
        monthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1);

        return selectedDay + " " + monthName + " " + selectedYear;
    }

    public String getSelectedTimeString() {
        if (selectedTime == null) return "-";

        int hour = selectedTime.getHour();
        int minute = selectedTime.getMinute();
        return String.format("%d:%02d", hour, minute);
    }

    public String getSelectedServiceName() {
        return selectedServiceName != null ? selectedServiceName : "-";
    }

    public double getSelectedServicePrice() {
        return selectedServicePrice;
    }

    public String getSelectedEmployeeName() {
        return selectedEmployeeName != null ? selectedEmployeeName : "-";
    }

    public String getSidePanelDateString() {
        if (selectedDay == 0) return "-";
        String dayName = java.time.DayOfWeek.from(LocalDate.of(selectedYear, selectedMonth, selectedDay))
            .getDisplayName(TextStyle.FULL, Locale.of("es", "MX"));
        String monthName = java.time.Month.of(selectedMonth)
            .getDisplayName(TextStyle.FULL, Locale.of("es", "MX"));
        return dayName + ", " + selectedDay + " de " + monthName;
    }

    public String getSidePanelTimeRangeString() {
        if (selectedTime == null) return "-";
        int startHour = selectedTime.getHour();
        int startMinute = selectedTime.getMinute();
        LocalTime endTime = selectedTime.plusMinutes(selectedServiceDuration);
        int endHour = endTime.getHour();
        int endMinute = endTime.getMinute();
        String start = String.format("%d:%02d", startHour, startMinute);
        String end = String.format("%d:%02d", endHour, endMinute);
        return start + "\u2013" + end + " (" + selectedServiceDuration + " min)";
    }
}