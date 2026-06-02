package views.newdateflow;

import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import utils.AppColors;
import utils.AppFonts;

public class SelectDateTimeView extends JPanel {

    private Object controller;

    private int selectedDay;
    private int selectedMonth;
    private int selectedYear;
    private LocalTime selectedTime;

    private List<LocalDate> allDates;
    private int dateWindowStart;
    private static final int VISIBLE_DATES = 7;

    private JPanel dateStripPanel;
    private JPanel timeGridPanel;
    private JButton btnPrevDates;
    private JButton btnNextDates;

    private Set<String> blockedTimes;

    public SelectDateTimeView(Object controller) {
        this.controller = controller;
        this.selectedDay = 0;
        this.selectedMonth = 0;
        this.selectedYear = 0;
        this.selectedTime = null;
        this.dateWindowStart = 0;
        this.allDates = generateDateList();
        this.blockedTimes = new HashSet<>();

        setLayout(new BorderLayout());
        setBackground(AppColors.BACKGROUND);

        add(createContentPanel(), BorderLayout.CENTER);
    }

    private List<LocalDate> generateDateList() {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 60; i++) {
            dates.add(today.plusDays(i));
        }
        return dates;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(AppColors.BACKGROUND);
        panel.setBorder(new EmptyBorder(20, 28, 20, 28));

        panel.add(createDateSection());
        panel.add(Box.createVerticalStrut(24));
        panel.add(createTimeSection());
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createDateSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setOpaque(false);

        JLabel lblSection = new JLabel("SELECCIONA UNA FECHA");
        lblSection.setFont(AppFonts.bold(11));
        lblSection.setForeground(AppColors.TEXT_MUTED);
        lblSection.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel dateStripContainer = new JPanel(new BorderLayout(6, 0));
        dateStripContainer.setOpaque(false);
        dateStripContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        dateStripContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        btnPrevDates = new JButton();
        btnPrevDates.setFocusPainted(false);
        btnPrevDates.setBorderPainted(false);
        btnPrevDates.setContentAreaFilled(false);
        btnPrevDates.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPrevDates.setPreferredSize(new Dimension(28, 28));
        btnPrevDates.setBackground(AppColors.DATE_NAV_BG);
        btnPrevDates.setOpaque(true);

        ImageIcon leftArrow = loadIcon("assets/icons/arrow-left-white.png", 14);
        if (leftArrow != null) btnPrevDates.setIcon(leftArrow);

        btnPrevDates.addActionListener(e -> scrollDates(-1));

        btnNextDates = new JButton();
        btnNextDates.setFocusPainted(false);
        btnNextDates.setBorderPainted(false);
        btnNextDates.setContentAreaFilled(false);
        btnNextDates.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNextDates.setPreferredSize(new Dimension(28, 28));
        btnNextDates.setBackground(AppColors.DATE_NAV_BG);
        btnNextDates.setOpaque(true);

        ImageIcon rightArrow = loadIcon("assets/icons/arrow-right-white.png", 14);
        if (rightArrow != null) btnNextDates.setIcon(rightArrow);

        btnNextDates.addActionListener(e -> scrollDates(1));

        dateStripPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        dateStripPanel.setOpaque(false);

        JPanel leftWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 28));
        leftWrapper.setOpaque(false);
        leftWrapper.add(btnPrevDates);

        JPanel rightWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 28));
        rightWrapper.setOpaque(false);
        rightWrapper.add(btnNextDates);

        dateStripContainer.add(leftWrapper, BorderLayout.WEST);
        dateStripContainer.add(dateStripPanel, BorderLayout.CENTER);
        dateStripContainer.add(rightWrapper, BorderLayout.EAST);

        section.add(lblSection);
        section.add(Box.createVerticalStrut(10));
        section.add(dateStripContainer);

        refreshDateStrip();

        return section;
    }

    private void refreshDateStrip() {
        dateStripPanel.removeAll();

        LocalDate today = LocalDate.now();
        int end = Math.min(dateWindowStart + VISIBLE_DATES, allDates.size());

        for (int i = dateWindowStart; i < end; i++) {
            LocalDate date = allDates.get(i);
            boolean isPast = date.isBefore(today);
            boolean isSelected = date.getDayOfMonth() == selectedDay
                && date.getMonthValue() == selectedMonth
                && date.getYear() == selectedYear;

            JPanel chip = createDateChip(date, isPast, isSelected);
            dateStripPanel.add(chip);
        }

        btnPrevDates.setEnabled(dateWindowStart > 0);
        btnNextDates.setEnabled(end < allDates.size());

        dateStripPanel.revalidate();
        dateStripPanel.repaint();
    }

    private JPanel createDateChip(LocalDate date, boolean isPast, boolean isSelected) {
        JPanel chip = new JPanel();
        chip.setLayout(new BoxLayout(chip, BoxLayout.Y_AXIS));
        chip.setCursor(isPast ? Cursor.getDefaultCursor() : new Cursor(Cursor.HAND_CURSOR));
        chip.setPreferredSize(new Dimension(54, 68));
        chip.setMinimumSize(new Dimension(54, 68));
        chip.setMaximumSize(new Dimension(54, 68));

        if (isSelected) {
            chip.setBackground(AppColors.YELLOW);
            chip.setBorder(BorderFactory.createLineBorder(AppColors.YELLOW, 1));
        } else if (isPast) {
            chip.setBackground(AppColors.PANEL);
            chip.setBorder(BorderFactory.createLineBorder(AppColors.DATE_CHIP_DISABLED_BORDER, 1));
        } else {
            chip.setBackground(AppColors.BACKGROUND);
            chip.setBorder(BorderFactory.createLineBorder(AppColors.PANEL2, 1));
        }

        String dayName = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.of("es", "MX"))
            .substring(0, 3).toUpperCase();
        String dayNum = String.valueOf(date.getDayOfMonth());
        String monthName = date.getMonth().getDisplayName(TextStyle.SHORT, Locale.of("es", "MX"))
            .substring(0, 3).toLowerCase();

        JLabel lblDayName = new JLabel(dayName, SwingConstants.CENTER);
        lblDayName.setFont(AppFonts.bold(10));
        lblDayName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblDayNum = new JLabel(dayNum, SwingConstants.CENTER);
        lblDayNum.setFont(AppFonts.bold(15));
        lblDayNum.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblMonth = new JLabel(monthName, SwingConstants.CENTER);
        lblMonth.setFont(AppFonts.regular(10));
        lblMonth.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (isSelected) {
            lblDayName.setForeground(AppColors.TEXT_DARK);
            lblDayNum.setForeground(AppColors.TEXT_DARK);
            lblMonth.setForeground(AppColors.TEXT_DARK);
        } else if (isPast) {
            lblDayName.setForeground(AppColors.DATE_CHIP_DISABLED_TEXT);
            lblDayNum.setForeground(AppColors.DATE_CHIP_DISABLED_TEXT);
            lblMonth.setForeground(AppColors.DATE_CHIP_DISABLED_TEXT);
        } else {
            lblDayName.setForeground(AppColors.TEXT_MUTED);
            lblDayNum.setForeground(AppColors.TEXT_LIGHT);
            lblMonth.setForeground(AppColors.TEXT_MUTED);
        }

        chip.add(Box.createVerticalStrut(6));
        chip.add(lblDayName);
        chip.add(Box.createVerticalStrut(1));
        chip.add(lblDayNum);
        chip.add(Box.createVerticalStrut(1));
        chip.add(lblMonth);

        if (!isPast) {
            final int d = date.getDayOfMonth();
            final int m = date.getMonthValue();
            final int y = date.getYear();
            chip.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    selectDate(d, m, y);
                }
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (!(d == selectedDay && m == selectedMonth && y == selectedYear)) {
                        chip.setBorder(BorderFactory.createLineBorder(AppColors.BORDER_HOVER, 1));
                    }
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (!(d == selectedDay && m == selectedMonth && y == selectedYear)) {
                        chip.setBorder(BorderFactory.createLineBorder(AppColors.PANEL2, 1));
                    }
                }
            });
        }

        return chip;
    }

    private void scrollDates(int direction) {
        int newStart = dateWindowStart + direction;
        if (newStart >= 0 && newStart + VISIBLE_DATES <= allDates.size()) {
            dateWindowStart = newStart;
            refreshDateStrip();
        }
    }

    private JPanel createTimeSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setOpaque(false);

        JLabel lblSection = new JLabel("ESCOGE UNA HORA");
        lblSection.setFont(AppFonts.bold(11));
        lblSection.setForeground(AppColors.TEXT_MUTED);
        lblSection.setAlignmentX(Component.LEFT_ALIGNMENT);

        timeGridPanel = new JPanel(new GridLayout(0, 3, 8, 8));
        timeGridPanel.setOpaque(false);
        timeGridPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        timeGridPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 320));

        String[] times = {
            "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
            "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
            "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
            "18:00", "18:30", "19:00", "19:30", "20:00", "20:30"
        };

        for (String time : times) {
            JPanel slot = createTimeSlot(time);
            timeGridPanel.add(slot);
        }

        section.add(lblSection);
        section.add(Box.createVerticalStrut(10));
        section.add(timeGridPanel);

        return section;
    }

    private JPanel createTimeSlot(String timeStr) {
        JPanel slot = new JPanel(new GridBagLayout());
        slot.setPreferredSize(new Dimension(0, 40));

        boolean isSelected = selectedTime != null && selectedTime.equals(LocalTime.parse(timeStr));
        boolean isBlocked = blockedTimes.contains(timeStr);

        if (isBlocked) {
            slot.setCursor(Cursor.getDefaultCursor());
            slot.setBackground(AppColors.PANEL);
            slot.setBorder(BorderFactory.createLineBorder(AppColors.DATE_CHIP_DISABLED_BORDER, 1));
        } else {
            slot.setCursor(new Cursor(Cursor.HAND_CURSOR));
            slot.setBackground(isSelected ? AppColors.TIME_SELECTED_BG : AppColors.BACKGROUND);
            slot.setBorder(isSelected
                ? BorderFactory.createLineBorder(AppColors.YELLOW, 1)
                : BorderFactory.createLineBorder(AppColors.PANEL2, 1));
        }

        JLabel lblTime = new JLabel(timeStr);
        if (isBlocked) {
            lblTime.setFont(AppFonts.regular(13));
            lblTime.setForeground(AppColors.DATE_CHIP_DISABLED_TEXT);
        } else {
            lblTime.setFont(isSelected ? AppFonts.bold(13) : AppFonts.regular(13));
            lblTime.setForeground(isSelected ? AppColors.YELLOW : AppColors.TEXT_SECONDARY);
        }

        slot.add(lblTime);

        if (!isBlocked) {
            slot.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    selectTime(timeStr);
                }
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (!(selectedTime != null && selectedTime.equals(LocalTime.parse(timeStr)))) {
                        slot.setBorder(BorderFactory.createLineBorder(AppColors.BORDER_HOVER, 1));
                        lblTime.setForeground(AppColors.TEXT_LIGHT);
                    }
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (!(selectedTime != null && selectedTime.equals(LocalTime.parse(timeStr)))) {
                        slot.setBorder(BorderFactory.createLineBorder(AppColors.PANEL2, 1));
                        lblTime.setForeground(AppColors.TEXT_SECONDARY);
                    }
                }
            });
        }

        return slot;
    }

    private void selectDate(int day, int month, int year) {
        selectedDay = day;
        selectedMonth = month;
        selectedYear = year;
        selectedTime = null;
        blockedTimes.clear();

        refreshDateStrip();
        refreshTimeSlots();

        if (controller instanceof controllers.newdateflow.SelectDateTimeController) {
            ((controllers.newdateflow.SelectDateTimeController) controller).onDateSelected(day, month, year);
        }
    }

    private void selectTime(String timeStr) {
        selectedTime = LocalTime.parse(timeStr);
        refreshTimeSlots();

        if (controller instanceof controllers.newdateflow.SelectDateTimeController) {
            ((controllers.newdateflow.SelectDateTimeController) controller).onTimeSelected(selectedTime);
        }
    }

    private void refreshTimeSlots() {
        timeGridPanel.removeAll();

        String[] times = {
            "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
            "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
            "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
            "18:00", "18:30", "19:00", "19:30", "20:00", "20:30"
        };

        for (String time : times) {
            JPanel slot = createTimeSlot(time);
            timeGridPanel.add(slot);
        }

        timeGridPanel.revalidate();
        timeGridPanel.repaint();
    }

    private ImageIcon loadIcon(String path, int size) {
        URL iconUrl = getClass().getClassLoader().getResource(path);
        if (iconUrl == null) return null;
        Image img = new ImageIcon(iconUrl).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public void setBlockedTimes(Set<String> times) {
        this.blockedTimes = times != null ? times : new HashSet<>();
        if (selectedTime != null && blockedTimes.contains(String.format("%02d:%02d", selectedTime.getHour(), selectedTime.getMinute()))) {
            selectedTime = null;
        }
        refreshTimeSlots();
    }

    public int getSelectedDay() {
        return selectedDay;
    }

    public int getSelectedMonth() {
        return selectedMonth;
    }

    public int getSelectedYear() {
        return selectedYear;
    }

    public LocalTime getSelectedTime() {
        return selectedTime;
    }
}