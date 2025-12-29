package util;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Global Logger utility for error logging to log.txt
 * Captures all errors with timestamp, file, method, and stack trace
 */
public class Logger {
    private static final String LOG_FILE = "log.txt";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Object lock = new Object(); // For thread-safe logging

    /**
     * Log an error/exception to log.txt
     * @param exception The exception to log
     * @param className The class name where error occurred
     * @param methodName The method name where error occurred
     */
    public static void logError(Exception exception, String className, String methodName) {
        logError(exception, className, methodName, null);
    }

    /**
     * Log an error/exception with custom message
     * @param exception The exception to log
     * @param className The class name where error occurred
     * @param methodName The method name where error occurred
     * @param customMessage Additional custom message
     */
    public static void logError(Exception exception, String className, String methodName, String customMessage) {
        synchronized (lock) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
                LocalDateTime now = LocalDateTime.now();
                String timestamp = now.format(DATE_FORMAT);
                
                writer.println("=".repeat(80));
                writer.println("ERROR LOG - " + timestamp);
                writer.println("=".repeat(80));
                writer.println("File/Class: " + className);
                writer.println("Method: " + methodName);
                
                if (customMessage != null && !customMessage.isEmpty()) {
                    writer.println("Custom Message: " + customMessage);
                }
                
                writer.println("Exception Type: " + exception.getClass().getName());
                writer.println("Error Message: " + exception.getMessage());
                
                // Log database-specific information if it's a SQLException
                if (exception instanceof java.sql.SQLException) {
                    java.sql.SQLException sqlEx = (java.sql.SQLException) exception;
                    writer.println("SQL State: " + sqlEx.getSQLState());
                    writer.println("Error Code: " + sqlEx.getErrorCode());
                }
                
                writer.println("\nStack Trace:");
                exception.printStackTrace(writer);
                
                // Print next exception if it's a SQLException with chained exceptions
                if (exception instanceof java.sql.SQLException) {
                    java.sql.SQLException sqlEx = (java.sql.SQLException) exception;
                    Throwable nextEx = sqlEx.getNextException();
                    if (nextEx != null) {
                        writer.println("\nNext Exception:");
                        nextEx.printStackTrace(writer);
                    }
                }
                
                writer.println("=".repeat(80));
                writer.println(); // Empty line for readability
                writer.flush();
                
            } catch (IOException e) {
                // If we can't write to log file, print to console
                System.err.println("CRITICAL: Cannot write to log file: " + e.getMessage());
                System.err.println("Original error was:");
                exception.printStackTrace();
            }
        }
    }

    /**
     * Log a general error message (not an exception)
     * @param message Error message
     * @param className The class name where error occurred
     * @param methodName The method name where error occurred
     */
    public static void logError(String message, String className, String methodName) {
        synchronized (lock) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
                LocalDateTime now = LocalDateTime.now();
                String timestamp = now.format(DATE_FORMAT);
                
                writer.println("=".repeat(80));
                writer.println("ERROR LOG - " + timestamp);
                writer.println("=".repeat(80));
                writer.println("File/Class: " + className);
                writer.println("Method: " + methodName);
                writer.println("Error Message: " + message);
                writer.println("=".repeat(80));
                writer.println();
                writer.flush();
                
            } catch (IOException e) {
                System.err.println("CRITICAL: Cannot write to log file: " + e.getMessage());
                System.err.println("Original error message: " + message);
            }
        }
    }

    /**
     * Log database-specific error
     * @param exception SQLException or other database-related exception
     * @param className The class name where error occurred
     * @param methodName The method name where error occurred
     * @param query Optional SQL query that caused the error
     */
    public static void logDatabaseError(Exception exception, String className, String methodName, String query) {
        synchronized (lock) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
                LocalDateTime now = LocalDateTime.now();
                String timestamp = now.format(DATE_FORMAT);
                
                writer.println("=".repeat(80));
                writer.println("DATABASE ERROR LOG - " + timestamp);
                writer.println("=".repeat(80));
                writer.println("File/Class: " + className);
                writer.println("Method: " + methodName);
                
                if (query != null && !query.isEmpty()) {
                    writer.println("SQL Query: " + query);
                }
                
                writer.println("Exception Type: " + exception.getClass().getName());
                writer.println("Error Message: " + exception.getMessage());
                
                if (exception instanceof java.sql.SQLException) {
                    java.sql.SQLException sqlEx = (java.sql.SQLException) exception;
                    writer.println("SQL State: " + sqlEx.getSQLState());
                    writer.println("Error Code: " + sqlEx.getErrorCode());
                    
                    // Log connection details if available
                    if (sqlEx.getMessage() != null && sqlEx.getMessage().contains("Connection")) {
                        writer.println("Connection Error Details: Check database server status, credentials, and network connectivity");
                    }
                }
                
                writer.println("\nStack Trace:");
                exception.printStackTrace(writer);
                
                // Print chained exceptions for SQLException
                if (exception instanceof java.sql.SQLException) {
                    java.sql.SQLException sqlEx = (java.sql.SQLException) exception;
                    Throwable nextEx = sqlEx.getNextException();
                    if (nextEx != null) {
                        writer.println("\nNext Exception:");
                        nextEx.printStackTrace(writer);
                    }
                }
                
                writer.println("=".repeat(80));
                writer.println();
                writer.flush();
                
            } catch (IOException e) {
                System.err.println("CRITICAL: Cannot write to log file: " + e.getMessage());
                System.err.println("Original database error was:");
                exception.printStackTrace();
            }
        }
    }

    /**
     * Log an info message (non-error)
     * @param message Info message
     * @param className The class name
     * @param methodName The method name
     */
    public static void logInfo(String message, String className, String methodName) {
        synchronized (lock) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
                LocalDateTime now = LocalDateTime.now();
                String timestamp = now.format(DATE_FORMAT);
                
                writer.println("[" + timestamp + "] INFO - " + className + "." + methodName + ": " + message);
                writer.flush();
                
            } catch (IOException e) {
                System.err.println("Cannot write to log file: " + e.getMessage());
            }
        }
    }

    /**
     * Get the current class and method name from stack trace
     * Helper method for automatic detection
     */
    private static String[] getCallerInfo() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        // [0] is getStackTrace, [1] is getCallerInfo, [2] is the caller, [3] is the actual method that called log
        if (stackTrace.length > 3) {
            StackTraceElement caller = stackTrace[3];
            return new String[]{caller.getClassName(), caller.getMethodName()};
        }
        return new String[]{"Unknown", "Unknown"};
    }

    /**
     * Convenience method that automatically detects calling class and method
     * Usage: Logger.log(exception) - automatically detects where it was called from
     */
    public static void log(Exception exception) {
        String[] callerInfo = getCallerInfo();
        logError(exception, callerInfo[0], callerInfo[1]);
    }

    /**
     * Convenience method for database errors with automatic detection
     */
    public static void logDatabase(Exception exception, String query) {
        String[] callerInfo = getCallerInfo();
        logDatabaseError(exception, callerInfo[0], callerInfo[1], query);
    }
}

