package view.components;

import view.util.ButtonStyleUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * Modern date picker dialog with integrated year/month selection
 */
public class DatePickerDialog extends JDialog {
    private LocalDate selectedDate;
    private YearMonth currentYearMonth;
    private JLabel monthYearLabel;
    private JPanel calendarPanel;
    private JButton[] dayButtons;
    private boolean confirmed = false;
    private boolean showingYearMonth = false;
    
    private static final String[] DAY_NAMES = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private static final String[] MONTH_NAMES = {"January", "February", "March", "April", "May", "June",
                                                  "July", "August", "September", "October", "November", "December"};
    private static final Color HEADER_COLOR = new Color(0, 102, 204);
    private static final Color SELECTED_COLOR = new Color(0, 150, 136);
    private static final Color TODAY_COLOR = new Color(255, 193, 7);

    public DatePickerDialog(Frame parent, String title) {
        this(parent, title, LocalDate.now());
    }

    public DatePickerDialog(Frame parent, String title, LocalDate initialDate) {
        super(parent, title, true);
        this.selectedDate = initialDate;
        this.currentYearMonth = YearMonth.from(initialDate);
        initializeUI();
    }

    private void initializeUI() {
        setSize(420, 480);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);
        
        // Header with navigation
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Calendar grid or year/month selector
        calendarPanel = createCalendarPanel();
        mainPanel.add(calendarPanel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        updateCalendar();
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(HEADER_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        
        JButton prevButton = createNavButton("◄");
        prevButton.addActionListener(e -> {
            if (showingYearMonth) {
                currentYearMonth = currentYearMonth.minusYears(1);
            } else {
                currentYearMonth = currentYearMonth.minusMonths(1);
            }
            updateCalendar();
        });
        
        JButton nextButton = createNavButton("►");
        nextButton.addActionListener(e -> {
            if (showingYearMonth) {
                currentYearMonth = currentYearMonth.plusYears(1);
            } else {
                currentYearMonth = currentYearMonth.plusMonths(1);
            }
            updateCalendar();
        });
        
        monthYearLabel = new JLabel("", SwingConstants.CENTER);
        monthYearLabel.setFont(new Font("Arial", Font.BOLD, 18));
        monthYearLabel.setForeground(Color.WHITE);
        monthYearLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        monthYearLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showingYearMonth = !showingYearMonth;
                updateCalendar();
            }
        });
        
        panel.add(prevButton, BorderLayout.WEST);
        panel.add(monthYearLabel, BorderLayout.CENTER);
        panel.add(nextButton, BorderLayout.EAST);
        
        return panel;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 28));
        button.setForeground(Color.WHITE);
        button.setBackground(HEADER_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setRolloverEnabled(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(70, 45));
        button.setMinimumSize(new Dimension(70, 45));
        return button;
    }

    private JPanel createCalendarPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }

    private void showCalendarView() {
        calendarPanel.removeAll();
        JPanel gridPanel = new JPanel(new GridLayout(7, 7, 4, 4));
        gridPanel.setBackground(Color.WHITE);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        
        // Day name headers
        for (String dayName : DAY_NAMES) {
            JLabel label = new JLabel(dayName, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setForeground(new Color(80, 80, 80));
            label.setOpaque(true);
            label.setBackground(new Color(240, 240, 240));
            label.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
            label.setPreferredSize(new Dimension(50, 35));
            gridPanel.add(label);
        }
        
        // Day buttons
        dayButtons = new JButton[42];
        for (int i = 0; i < 42; i++) {
            dayButtons[i] = createDayButton();
            gridPanel.add(dayButtons[i]);
        }
        
        calendarPanel.add(gridPanel, BorderLayout.CENTER);
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private void showYearMonthView() {
        calendarPanel.removeAll();
        JPanel mainView = new JPanel(new BorderLayout(10, 10));
        mainView.setBackground(Color.WHITE);
        mainView.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Year selector
        JPanel yearPanel = new JPanel(new BorderLayout(5, 5));
        yearPanel.setBackground(Color.WHITE);
        yearPanel.setBorder(BorderFactory.createTitledBorder("Select Year"));
        
        JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(
            currentYearMonth.getYear(), 1900, 2100, 1));
        yearSpinner.setFont(new Font("Arial", Font.BOLD, 18));
        yearSpinner.setPreferredSize(new Dimension(0, 40));
        yearPanel.add(yearSpinner, BorderLayout.CENTER);
        
        // Month grid
        JPanel monthGrid = new JPanel(new GridLayout(4, 3, 8, 8));
        monthGrid.setBackground(Color.WHITE);
        monthGrid.setBorder(BorderFactory.createTitledBorder("Select Month"));
        
        int currentMonth = currentYearMonth.getMonthValue() - 1;
        
        for (int i = 0; i < 12; i++) {
            JButton monthBtn = new JButton(MONTH_NAMES[i]);
            monthBtn.setFont(new Font("Arial", Font.PLAIN, 13));
            monthBtn.setPreferredSize(new Dimension(100, 40));
            
            if (i == currentMonth) {
                monthBtn.setBackground(HEADER_COLOR);
                monthBtn.setForeground(Color.WHITE);
            } else {
                monthBtn.setBackground(Color.WHITE);
                monthBtn.setForeground(Color.BLACK);
                monthBtn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            }
            
            monthBtn.setOpaque(true);
            monthBtn.setContentAreaFilled(true);
            monthBtn.setFocusPainted(false);
            monthBtn.setBorderPainted(true);
            monthBtn.setRolloverEnabled(false);
            
            final int monthIndex = i;
            monthBtn.addActionListener(e -> {
                int selectedYear = (Integer) yearSpinner.getValue();
                currentYearMonth = YearMonth.of(selectedYear, monthIndex + 1);
                showingYearMonth = false;
                updateCalendar();
            });
            
            monthGrid.add(monthBtn);
        }
        
        mainView.add(yearPanel, BorderLayout.NORTH);
        mainView.add(new JScrollPane(monthGrid), BorderLayout.CENTER);
        
        calendarPanel.add(mainView, BorderLayout.CENTER);
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private JButton createDayButton() {
        JButton button = new JButton();
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setRolloverEnabled(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(50, 45));
        button.setMinimumSize(new Dimension(50, 45));
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        return button;
    }

    private void updateButtonColor(JButton button) {
        if (button.getText().isEmpty()) {
            button.setBackground(Color.WHITE);
            button.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240), 1));
            return;
        }
        
        try {
            int day = Integer.parseInt(button.getText());
            LocalDate buttonDate = currentYearMonth.atDay(day);
            
            if (buttonDate.equals(selectedDate)) {
                button.setBackground(SELECTED_COLOR);
                button.setForeground(Color.WHITE);
                button.setBorder(BorderFactory.createLineBorder(SELECTED_COLOR, 2));
            } else if (buttonDate.equals(LocalDate.now())) {
                button.setBackground(TODAY_COLOR);
                button.setForeground(Color.BLACK);
                button.setBorder(BorderFactory.createLineBorder(TODAY_COLOR, 2));
            } else {
                button.setBackground(Color.WHITE);
                button.setForeground(Color.BLACK);
                button.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
            }
        } catch (NumberFormatException e) {
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
        }
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        
        JButton todayButton = ButtonStyleUtil.createButton("Today", new Color(100, 150, 200));
        todayButton.setPreferredSize(new Dimension(90, 38));
        todayButton.addActionListener(e -> {
            selectedDate = LocalDate.now();
            currentYearMonth = YearMonth.from(selectedDate);
            showingYearMonth = false;
            updateCalendar();
        });
        
        JButton cancelButton = ButtonStyleUtil.createSecondaryButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(90, 38));
        cancelButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });
        
        JButton okButton = ButtonStyleUtil.createPrimaryButton("OK");
        okButton.setPreferredSize(new Dimension(90, 38));
        okButton.addActionListener(e -> {
            confirmed = true;
            dispose();
        });
        
        panel.add(todayButton);
        panel.add(cancelButton);
        panel.add(okButton);
        
        return panel;
    }

    private void updateCalendar() {
        if (showingYearMonth) {
            monthYearLabel.setText(String.valueOf(currentYearMonth.getYear()));
            showYearMonthView();
        } else {
            monthYearLabel.setText(currentYearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
            showCalendarView();
            
            LocalDate firstOfMonth = currentYearMonth.atDay(1);
            int dayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;
            int daysInMonth = currentYearMonth.lengthOfMonth();
            
            for (int i = 0; i < 42; i++) {
                JButton button = dayButtons[i];
                int dayNumber = i - dayOfWeek + 1;
                
                if (dayNumber > 0 && dayNumber <= daysInMonth) {
                    button.setText(String.valueOf(dayNumber));
                    button.setEnabled(true);
                    
                    final int day = dayNumber;
                    // Remove old listeners
                    for (ActionListener al : button.getActionListeners()) {
                        button.removeActionListener(al);
                    }
                    button.addActionListener(e -> {
                        selectedDate = currentYearMonth.atDay(day);
                        updateCalendar();
                    });
                    
                    updateButtonColor(button);
            } else {
                button.setText("");
                button.setEnabled(false);
                button.setBackground(new Color(250, 250, 250));
                button.setForeground(Color.GRAY);
                button.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240), 1));
            }
            }
        }
    }

    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    /**
     * Show date picker dialog and return selected date
     * @param parent Parent frame
     * @param title Dialog title
     * @return Selected date or null if cancelled
     */
    public static LocalDate showDialog(Frame parent, String title) {
        return showDialog(parent, title, LocalDate.now());
    }

    /**
     * Show date picker dialog with initial date
     * @param parent Parent frame
     * @param title Dialog title
     * @param initialDate Initial date to display
     * @return Selected date or null if cancelled
     */
    public static LocalDate showDialog(Frame parent, String title, LocalDate initialDate) {
        DatePickerDialog dialog = new DatePickerDialog(parent, title, initialDate);
        dialog.setVisible(true);
        return dialog.isConfirmed() ? dialog.getSelectedDate() : null;
    }
}
