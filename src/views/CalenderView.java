package views;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import components.RoundedButton;
import controllers.CalenderController;
import models.Appointment;
import models.AppointmentDetail;
import repository.AppointmentRepository;
import utils.AppColors;
import utils.AppFonts;

public class CalenderView extends JPanel {
    private CalenderController controller;
    private HomeView homeView;
    private int userId;

    private JLabel lblDate;
    private JPanel calendarPanel;
    private Map<LocalDate, List<Appointment>> appointmentMap;
    private AppointmentRepository repo;

    private Month actualMonth = LocalDate.now().getMonth();
    private int actualYear = LocalDate.now().getYear();

    public CalenderView(CalenderController controller, int userId, HomeView homeView) {
        this.controller = controller;
        this.userId = userId;
        this.homeView = homeView;
        this.repo = new AppointmentRepository();

        setLayout(new BorderLayout());
        setBackground(AppColors.BACKGROUND);

        loadAppointmentMap();

        add(createHeaderPanel(), BorderLayout.NORTH);

        calendarPanel = createCalendarPanel();
        calendarPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        add(calendarPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void loadAppointmentMap() {
        List<Appointment> allAppointments = repo.getAppointmentsByUserId(userId);
        appointmentMap = allAppointments.stream()
                .collect(Collectors.groupingBy(Appointment::getAppointmentDate));
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setMinimumSize(new Dimension(1030, 70));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.PANEL2));
        panel.setBackground(AppColors.PANEL);

        JPanel dateSelection = new JPanel(new FlowLayout());
        dateSelection.setLayout(new BorderLayout());
        dateSelection.setPreferredSize(new Dimension(300, 70));
        dateSelection.setBackground(AppColors.PANEL);

        RoundedButton btnPreviosDate = new RoundedButton("", 12);
        btnPreviosDate.setBackground(AppColors.BACKGROUND);
        btnPreviosDate.setPreferredSize(new Dimension(36, 36));

        JPanel prevWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 17));
        prevWrapper.setBackground(AppColors.PANEL);
        prevWrapper.add(btnPreviosDate);

        RoundedButton btnNextDate = new RoundedButton("", 12);
        btnNextDate.setBackground(AppColors.BACKGROUND);
        btnNextDate.setPreferredSize(new Dimension(36, 36));

        JPanel nextWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 17));
        nextWrapper.setBackground(AppColors.PANEL);
        nextWrapper.add(btnNextDate);

        btnPreviosDate.addActionListener(e -> controller.previousMonth());
        btnNextDate.addActionListener(e -> controller.nextMonth());

        URL prevIconURL = getClass().getClassLoader().getResource("assets/icons/arrow-left.png");
        if (prevIconURL != null) {
            Image img = new ImageIcon(prevIconURL).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            btnPreviosDate.setIcon(new ImageIcon(img));
        }

        URL nextIconURL = getClass().getClassLoader().getResource("assets/icons/arrow-right.png");
        if (nextIconURL != null) {
            Image img = new ImageIcon(nextIconURL).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            btnNextDate.setIcon(new ImageIcon(img));
        }

        lblDate = new JLabel();
        lblDate.setFont(AppFonts.bold(18));
        lblDate.setForeground(AppColors.TEXT_LIGHT);
        lblDate.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));

        String nameMonth = actualMonth.getDisplayName(TextStyle.FULL, Locale.of("es", "MX"));
        nameMonth = nameMonth.substring(0, 1).toUpperCase() + nameMonth.substring(1);
        lblDate.setText(nameMonth + " " + actualYear);

        dateSelection.add(prevWrapper, BorderLayout.WEST);
        dateSelection.add(lblDate, BorderLayout.CENTER);
        dateSelection.add(nextWrapper, BorderLayout.EAST);

        RoundedButton btnNewAppointment = new RoundedButton("Nueva cita", 8);
        btnNewAppointment.setBackground(AppColors.YELLOW);
        btnNewAppointment.setForeground(AppColors.TEXT_DARK);
        btnNewAppointment.setFont(AppFonts.button());

        URL plusIconURL = getClass().getClassLoader().getResource("assets/icons/plus-black.png");
        if (plusIconURL != null) {
            Image img = new ImageIcon(plusIconURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            btnNewAppointment.setIcon(new ImageIcon(img));
        }

        btnNewAppointment.addActionListener(e -> {
            if (homeView != null) {
                homeView.navigateToSection("nueva_cita");
            }
        });

        JPanel btnWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 17));
        btnWrapper.setBackground(AppColors.PANEL);
        btnWrapper.add(btnNewAppointment);

        panel.add(dateSelection, BorderLayout.WEST);
        panel.add(btnWrapper, BorderLayout.EAST);

        return panel;
    }

    private JPanel createDayCell(int dayNumber, boolean isToday, List<Appointment> dayAppointments) {
        JPanel cell = new JPanel(new BorderLayout());
        cell.setBackground(dayNumber > 0 ? AppColors.BACKGROUND : AppColors.PANEL);
        cell.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, AppColors.PANEL2));

        if (dayNumber > 0) {
            JLabel lblDay = new JLabel(String.valueOf(dayNumber));
            lblDay.setForeground(isToday ? AppColors.YELLOW : AppColors.TEXT_LIGHT);
            lblDay.setFont(AppFonts.regular(12));
            lblDay.setBorder(BorderFactory.createEmptyBorder(6, 8, 0, 0));
            cell.add(lblDay, BorderLayout.NORTH);

            if (isToday) {
                cell.setBorder(BorderFactory.createLineBorder(AppColors.YELLOW, 2));
            }

            if (!dayAppointments.isEmpty()) {
                int count = dayAppointments.size();
                String text = "● " + count + (count == 1 ? " cita" : " citas");
                JLabel pill = new JLabel(text);
                pill.setFont(AppFonts.bold(10));
                pill.setForeground(AppColors.YELLOW);
                pill.setBorder(new EmptyBorder(0, 8, 4, 0));
                cell.add(pill, BorderLayout.SOUTH);
            }

            LocalDate cellDate = LocalDate.of(actualYear, actualMonth, dayNumber);
            cell.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            cell.addMouseListener(new MouseAdapter() {
                private final Color originalBg = AppColors.BACKGROUND;
                private final Color hoverBg = new Color(31, 36, 56);

                @Override
                public void mouseEntered(MouseEvent e) {
                    cell.setBackground(hoverBg);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    cell.setBackground(originalBg);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    showDayPopup(dayNumber, cell);
                }
            });
        }

        return cell;
    }

    private JPanel createCalendarPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AppColors.PANEL);

        String[] days = {"DOMINGO", "LUNES", "MARTES", "MI\u00c9RCOLES", "JUEVES", "VIERNES", "S\u00c1BADO"};

        JPanel headersPanel = new JPanel(new GridLayout(1, 7));
        headersPanel.setBackground(AppColors.BACKGROUND);
        headersPanel.setPreferredSize(new Dimension(0, 50));

        for (String day : days) {
            JLabel lbl = new JLabel(day, SwingConstants.CENTER);
            lbl.setForeground(AppColors.YELLOW);
            lbl.setFont(AppFonts.bold(15));
            lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, AppColors.PANEL2));
            headersPanel.add(lbl);
        }

        JPanel gridPanel = new JPanel(new GridLayout(6, 7));
        gridPanel.setBackground(AppColors.PANEL);

        LocalDate firstDay = LocalDate.of(actualYear, actualMonth, 1);
        int startCol = firstDay.getDayOfWeek().getValue() % 7;
        int totalDays = actualMonth.length(Year.isLeap(actualYear));
        LocalDate today = LocalDate.now();

        int dayNumber = 1;

        for (int i = 0; i < 42; i++) {
            if (i < startCol || dayNumber > totalDays) {
                gridPanel.add(createDayCell(0, false, Collections.emptyList()));
            } else {
                boolean isToday = (
                    dayNumber == today.getDayOfMonth()
                    && actualMonth == today.getMonth()
                    && actualYear == today.getYear()
                );

                LocalDate cellDate = LocalDate.of(actualYear, actualMonth, dayNumber);
                List<Appointment> dayAppointments = appointmentMap.getOrDefault(cellDate, Collections.emptyList());
                gridPanel.add(createDayCell(dayNumber, isToday, dayAppointments));
                dayNumber++;
            }
        }

        panel.add(headersPanel, BorderLayout.NORTH);
        panel.add(gridPanel, BorderLayout.CENTER);

        return panel;
    }

    private void showDayPopup(int dayNumber, JPanel sourceCell) {
        Container parent = getParent();
        while (parent != null && !(parent instanceof JLayeredPane)) {
            parent = parent.getParent();
        }

        if (parent == null) return;

        JLayeredPane layeredPane = (JLayeredPane) parent;
        int centerX = (layeredPane.getWidth() - 400) / 2;
        int centerY = (layeredPane.getHeight() - 600) / 2;

        JPanel popup = new JPanel(new BorderLayout());
        popup.setBounds(centerX, centerY, 400, 600);
        popup.setBackground(AppColors.PANEL);
        popup.setBorder(BorderFactory.createLineBorder(AppColors.YELLOW, 1));
        popup.setName("popup");

        String monthName = actualMonth.getDisplayName(TextStyle.FULL, Locale.of("es", "MX"));
        monthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1);
        String dateLabel = dayNumber + " de " + monthName + " " + actualYear;

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(400, 48));
        headerPanel.setBackground(AppColors.PANEL2);

        JButton closeBtn = new JButton("X");
        closeBtn.setFocusPainted(false);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setBackground(new Color(0, 0, 0, 0));
        closeBtn.setForeground(AppColors.TEXT_LIGHT);
        closeBtn.setFont(AppFonts.bold(14));
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> {
            layeredPane.remove(popup);
            layeredPane.revalidate();
            layeredPane.repaint();
        });

        JLabel dateLbl = new JLabel(dateLabel, SwingConstants.CENTER);
        dateLbl.setFont(AppFonts.bold(16));
        dateLbl.setForeground(AppColors.TEXT_LIGHT);

        headerPanel.add(closeBtn, BorderLayout.WEST);
        headerPanel.add(dateLbl, BorderLayout.CENTER);

        popup.add(headerPanel, BorderLayout.NORTH);

        LocalDate clickedDate = LocalDate.of(actualYear, actualMonth, dayNumber);
        List<AppointmentDetail> details = repo.getDetailedAppointmentsByUserAndDate(userId, clickedDate);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(18, 21, 33));

        if (details.isEmpty()) {
            JLabel emptyLabel = new JLabel("No tienes citas para este dia");
            emptyLabel.setFont(AppFonts.regular(13));
            emptyLabel.setForeground(AppColors.TEXT_SECONDARY);
            emptyLabel.setAlignmentX(CENTER_ALIGNMENT);
            emptyLabel.setBorder(new EmptyBorder(20, 0, 0, 0));
            contentPanel.add(emptyLabel);
        } else {
            for (AppointmentDetail detail : details) {
                JPanel card = createAppointmentCard(detail);
                contentPanel.add(card);
            }
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        popup.add(scrollPane, BorderLayout.CENTER);

        layeredPane.add(popup, JLayeredPane.POPUP_LAYER);
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    private JPanel createAppointmentCard(AppointmentDetail detail) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        card.setMinimumSize(new Dimension(400, 200));
        card.setPreferredSize(new Dimension(400, 200));
        card.setBackground(new Color(18, 21, 33));
        card.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(12, 12, 12, 12),
            BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.PANEL2)
        ));

        String timeRange = detail.getStartTime().toString().substring(0, 5) + " - " + detail.getEndTime().toString().substring(0, 5);
        card.add(createFieldLabel("Hora: ", timeRange));
        card.add(createFieldLabel("Servicio: ", detail.getServiceName()));
        card.add(createFieldLabel("Precio: ", "$" + String.format("%.2f", detail.getServicePrice())));
        card.add(createFieldLabel("Empleado: ", detail.getEmployeeFullName()));

        String statusCap = detail.getStatus().substring(0, 1).toUpperCase() + detail.getStatus().substring(1);
        card.add(createFieldLabel("Estado: ", statusCap));

        if (detail.getNotes() != null && !detail.getNotes().isEmpty()) {
            card.add(createFieldLabel("Notas: ", detail.getNotes()));
        }

        return card;
    }

    private JPanel createFieldLabel(String fieldName, String value) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
        row.setBackground(new Color(0, 0, 0, 0));

        JLabel nameLabel = new JLabel(fieldName);
        nameLabel.setFont(AppFonts.bold(12));
        nameLabel.setForeground(AppColors.YELLOW);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(AppFonts.regular(12));
        valueLabel.setForeground(AppColors.TEXT_LIGHT);

        row.add(nameLabel);
        row.add(valueLabel);

        return row;
    }

    public void refreshCalendar() {
        loadAppointmentMap();

        actualMonth = controller.getActualMonth();
        actualYear = controller.getActualYear();

        String nameMonth = actualMonth.getDisplayName(TextStyle.FULL, Locale.of("es", "MX"));
        nameMonth = nameMonth.substring(0, 1).toUpperCase() + nameMonth.substring(1);
        lblDate.setText(nameMonth + " " + actualYear);

        remove(calendarPanel);
        calendarPanel = createCalendarPanel();
        calendarPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        add(calendarPanel);
        revalidate();
        repaint();
    }

    public Month getActualMonth() {
        return actualMonth;
    }

    public void setActualMonth(Month actualMonth) {
        this.actualMonth = actualMonth;
    }

    public int getActualYear() {
        return actualYear;
    }

    public void setActualYear(int actualYear) {
        this.actualYear = actualYear;
    }
}