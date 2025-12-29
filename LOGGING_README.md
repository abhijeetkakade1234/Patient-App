# Error Logging System

## Overview
The application now has comprehensive error logging that automatically captures all errors to `log.txt` file in the root directory.

## Features

### ✅ Automatic Error Capture
- **Global Exception Handler**: Catches all uncaught exceptions across the entire application
- **Swing EDT Exception Handler**: Catches exceptions in the Swing Event Dispatch Thread
- **Database Error Logging**: Special handling for SQL/database errors with connection details
- **Thread-Safe**: All logging operations are thread-safe

### 📝 What Gets Logged

Each error log entry includes:
- **Timestamp**: Exact date and time of the error
- **File/Class Name**: Where the error occurred
- **Method Name**: Which method had the error
- **Exception Type**: The class of exception
- **Error Message**: The exception message
- **Full Stack Trace**: Complete stack trace for debugging
- **Custom Messages**: Additional context when provided

### 🗄️ Database-Specific Logging

For database errors, additional information is logged:
- **SQL State**: SQL error state code
- **Error Code**: Database-specific error code
- **SQL Query**: The query that caused the error (when available)
- **Connection Details**: Connection URL and user information
- **Chained Exceptions**: Multiple related exceptions if present

## Log File Location

```
log.txt
```

Located in the root directory of the application.

## Usage Examples

### Automatic Logging (Recommended)
The system automatically logs all uncaught exceptions. You don't need to do anything!

### Manual Logging

If you want to manually log exceptions in your code:

```java
import util.Logger;

// Simple logging (automatically detects class and method)
try {
    // your code
} catch (Exception e) {
    Logger.log(e);  // Auto-detects calling class and method
}

// With custom message
try {
    // your code
} catch (Exception e) {
    Logger.logError(e, "MyClass", "myMethod", "Custom error context");
}

// Database errors with query
try {
    // SQL operation
} catch (SQLException e) {
    Logger.logDatabase(e, "SELECT * FROM patients WHERE id = ?");
}
```

## Example Log Entry

```
================================================================================
ERROR LOG - 2024-01-15 14:30:25
================================================================================
File/Class: util.DBUtil
Method: getConnection
Custom Message: Connection URL: jdbc:mariadb://localhost:3306/patient_management, User: root
Exception Type: java.sql.SQLException
Error Message: Access denied for user 'root'@'localhost'
SQL State: 28000
Error Code: 1045

Stack Trace:
java.sql.SQLException: Access denied for user 'root'@'localhost'
    at org.mariadb.jdbc.internal.util.exceptions.ExceptionMapper.get(ExceptionMapper.java:232)
    at org.mariadb.jdbc.internal.util.exceptions.ExceptionMapper.getException(ExceptionMapper.java:185)
    ...
================================================================================
```

## Database Error Handling

All database operations in `DBUtil` are automatically logged:
- Connection failures
- Query execution errors
- Connection close errors
- Statement/ResultSet close errors

## User-Friendly Error Messages

When errors occur, users see friendly dialog boxes:
- General errors show: "An error occurred. Please check log.txt for details."
- Database errors show specific database-related messages

## Benefits

1. **Full Error History**: All errors are recorded with complete context
2. **Easy Debugging**: Stack traces and file locations help identify issues quickly
3. **Database Problem Tracking**: Special handling makes database issues easy to identify
4. **No Lost Errors**: Uncaught exceptions are captured automatically
5. **Production Ready**: Helps diagnose issues in production environments

## Important Notes

- Log file is appended to (not overwritten) - keeps history
- Log file grows over time - consider periodic cleanup for long-running applications
- All logging operations are thread-safe - safe for multi-threaded applications
- If log file cannot be written, errors are printed to console as fallback

