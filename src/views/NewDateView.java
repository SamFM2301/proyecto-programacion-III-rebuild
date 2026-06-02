package views;

import java.awt.*;

import javax.swing.*;

import controllers.NewDateController;
import utils.AppColors;
import views.newdateflow.ConfirmDateView;
import views.newdateflow.HeaderDateView;
import views.newdateflow.ResultView;
import views.newdateflow.SelectDateTimeView;
import views.newdateflow.SelectEmplyeeView;
import views.newdateflow.SelectServiceView;
import views.newdateflow.SideInfoDateView;

public class NewDateView extends JPanel {

    private NewDateController controller;

    private HeaderDateView headerView;
    private SideInfoDateView sideInfoView;
    private JPanel centerPanel;

    private SelectServiceView selectServiceView;
    private SelectEmplyeeView selectEmployeeView;
    private SelectDateTimeView selectDateTimeView;
    private ConfirmDateView confirmDateView;

    private ResultView resultView;

    private JPanel currentView;
    private boolean showingResult;

    public NewDateView(NewDateController controller) {
        this.controller = controller;

        setLayout(new BorderLayout());
        setBackground(AppColors.PANEL);

        headerView = new HeaderDateView();
        sideInfoView = new SideInfoDateView();
        resultView = new ResultView();

        selectServiceView = controller.getSelectServiceController().getView();
        selectEmployeeView = controller.getSelectEmployeeController().getView();
        selectDateTimeView = controller.getSelectDateTimeController().getView();
        confirmDateView = controller.getConfirmDateController().getView();

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(AppColors.BACKGROUND);

        add(headerView, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(sideInfoView, BorderLayout.EAST);

        setCurrentStep(0);

        setupButtonActions();

        setVisible(true);
    }

    private void setupButtonActions() {
        sideInfoView.getBtnAction().addActionListener(e -> {
            if (showingResult) {
                controller.onResultAction();
                return;
            }

            int step = controller.getCurrentStep();
            if (step == 3) {
                controller.confirmAppointment();
            } else {
                controller.nextStep();
            }
        });

        sideInfoView.getBtnBack().addActionListener(e -> {
            controller.previousStep();
        });

        resultView.getBtnAction().addActionListener(e -> controller.onResultAction());
    }

    public void setCurrentStep(int step) {
        if (currentView != null) {
            centerPanel.remove(currentView);
        }

        showingResult = false;
        headerView.setStep(step);
        sideInfoView.setVisible(true);

        switch (step) {
            case 0:
                currentView = selectServiceView;
                sideInfoView.setActionButtonText("Continuar");
                sideInfoView.setBackButtonVisible(false);
                sideInfoView.setActionButtonEnabled(controller.canProceedFromService());
                break;
            case 1:
                currentView = selectEmployeeView;
                sideInfoView.setActionButtonText("Continuar");
                sideInfoView.setBackButtonVisible(true);
                sideInfoView.setActionButtonEnabled(controller.canProceedFromEmployee());
                break;
            case 2:
                currentView = selectDateTimeView;
                sideInfoView.setActionButtonText("Continuar");
                sideInfoView.setBackButtonVisible(true);
                sideInfoView.setActionButtonEnabled(controller.canProceedFromDateTime());
                break;
            case 3:
                currentView = confirmDateView;
                sideInfoView.setActionButtonText("Agendar cita");
                sideInfoView.setBackButtonVisible(true);
                sideInfoView.setActionButtonEnabled(true);
                updateConfirmView();
                break;
            default:
                currentView = selectServiceView;
                sideInfoView.setActionButtonText("Continuar");
                sideInfoView.setBackButtonVisible(false);
                sideInfoView.setActionButtonEnabled(controller.canProceedFromService());
                break;
        }

        centerPanel.add(currentView, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public void showResultView(boolean success, String service, String employee, String date, String price) {
        if (currentView != null) {
            centerPanel.remove(currentView);
        }

        showingResult = true;
        headerView.setStep(-1);
        sideInfoView.setVisible(false);

        if (success) {
            resultView.showSuccess(service, employee, date, price);
        } else {
            resultView.showError(null);
        }

        currentView = resultView;
        centerPanel.add(currentView, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void updateConfirmView() {
        confirmDateView.updateServiceInfo(
            controller.getSelectedServiceName(),
            controller.getSelectedServicePrice()
        );
        confirmDateView.updateEmployeeInfo(controller.getSelectedEmployeeName());
        confirmDateView.updateDateTimeInfo(
            controller.getSelectedDateString(),
            controller.getSelectedTimeString()
        );
    }

    public void updateServiceSelection(String name, double price) {
        sideInfoView.updateServiceSelection(name, price);
    }

    public void updateEmployeeSelection(String name) {
        sideInfoView.updateEmployeeSelection(name);
    }

    public void updateDateTimeSelection(String date, String time) {
        sideInfoView.updateDateTimeSelection(date, time);
    }

    public void setSideActionButtonEnabled(boolean enabled) {
        sideInfoView.setActionButtonEnabled(enabled);
    }

    public HeaderDateView getHeaderView() {
        return headerView;
    }

    public SideInfoDateView getSideInfoView() {
        return sideInfoView;
    }

    public SelectServiceView getSelectServiceView() {
        return selectServiceView;
    }

    public SelectEmplyeeView getSelectEmployeeView() {
        return selectEmployeeView;
    }

    public SelectDateTimeView getSelectDateTimeView() {
        return selectDateTimeView;
    }

    public ConfirmDateView getConfirmDateView() {
        return confirmDateView;
    }

    public NewDateController getController() {
        return controller;
    }

    public boolean isShowingResult() {
        return showingResult;
    }

    public ResultView getResultView() {
        return resultView;
    }
}