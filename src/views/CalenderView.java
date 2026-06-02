package views;

import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.Locale;

import javax.swing.*;

import components.RoundedButton;
import controllers.CalenderController;
import utils.AppColors;
import utils.AppFonts;

public class CalenderView extends JPanel{
	private CalenderController controller;
	
	private JLabel lblDate;
	private JPanel calendarPanel;
	
	private Month actualMonth = LocalDate.now().getMonth();
    private int actualYear = LocalDate.now().getYear();
	
	public CalenderView(CalenderController controller) {
		this.controller = controller;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 0));
        setMinimumSize(new Dimension(250, 0));
        setMaximumSize(new Dimension(250, Integer.MAX_VALUE));
        setBackground(AppColors.BACKGROUND);
        
        add(createHeaderPanel());
        
        calendarPanel = createCalendarPanel();
        calendarPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        add(calendarPanel);
        
        setVisible(true);
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
		
		JPanel btnWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 17));
		btnWrapper.setBackground(AppColors.PANEL);
		btnWrapper.add(btnNewAppointment);
		
		panel.add(dateSelection, BorderLayout.WEST);
		panel.add(btnWrapper, BorderLayout.EAST);
		
		return panel;
	}
	
	private JPanel createDayCell(int dayNumber, boolean isToday) {
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
	            gridPanel.add(createDayCell(0, false));
	        } else {
	            boolean isToday = (
            		dayNumber == today.getDayOfMonth() 
            		&& actualMonth == today.getMonth() 
            		&& actualYear == today.getYear()
        		);
	            
	            gridPanel.add(createDayCell(dayNumber, isToday));
	            dayNumber++;
	        }
	    }

	    panel.add(headersPanel, BorderLayout.NORTH);
	    panel.add(gridPanel, BorderLayout.CENTER);

	    return panel;
	}
	
	public void refreshCalendar() {
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