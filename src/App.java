import javax.swing.*;
import util.Logger;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Main Application Entry Point
 * Patient Management System
 * 
 * Launches the Dashboard with global error logging
 */
public class App {
    public static void main(String[] args) {
        // Set up global exception handler for uncaught exceptions
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Logger.logError(new Exception(e), App.class.getName(), "uncaughtException", 
                    "Uncaught exception in thread: " + t.getName());
                e.printStackTrace();
            }
        });

        // Set up Swing exception handler for EDT (Event Dispatch Thread) exceptions
        Thread.UncaughtExceptionHandler swingExceptionHandler = (thread, throwable) -> {
            Logger.logError(new Exception(throwable), App.class.getName(), "swingExceptionHandler",
                "Uncaught exception in Swing EDT: " + thread.getName());
            throwable.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null,
                "An unexpected error occurred. Please check log.txt for details.\n\nError: " + throwable.getMessage(),
                "Application Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        };

        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            Logger.logError(e, App.class.getName(), "main", 
                "Failed to set look and feel: " + e.getMessage());
            System.err.println("Could not set look and feel: " + e.getMessage());
        }
        
        // Launch Dashboard with exception handling
        try {
            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.currentThread().setUncaughtExceptionHandler(swingExceptionHandler);
                    new view.DashboardView();
                } catch (Exception e) {
                    Logger.logError(e, view.DashboardView.class.getName(), "main", 
                        "Error launching DashboardView");
                    JOptionPane.showMessageDialog(null,
                        "Failed to launch Dashboard. Please check log.txt for details.\n\nError: " + e.getMessage(),
                        "Launch Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            });
        } catch (Exception e) {
            Logger.logError(e, App.class.getName(), "main", 
                "Error in SwingUtilities.invokeLater");
            e.printStackTrace();
        }
    }
}
