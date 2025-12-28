@echo off
REM Manual database setup - uses password directly
REM For MariaDB with password: root

echo ========================================
echo Patient Management System
echo Manual Database Setup
echo ========================================
echo.

echo Using password: root
echo.

echo Step 1: Creating database...
mysql -u root -proot < sql\01_create_database.sql

if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Failed to create database!
    echo.
    echo Troubleshooting:
    echo 1. Check if MariaDB is running: net start MariaDB
    echo 2. Verify password is correct: mysql -u root -proot -e "SHOW DATABASES;"
    echo 3. Try resetting MariaDB password if needed
    pause
    exit /b 1
)

echo Success!
echo.

echo Step 2: Creating tables...
mysql -u root -proot patient_management < sql\02_create_tables.sql

if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Failed to create tables!
    pause
    exit /b 1
)

echo Success!
echo.

echo Step 3: Inserting sample data...
mysql -u root -proot patient_management < sql\03_insert_sample_data.sql

if %ERRORLEVEL% EQU 0 (
    echo Success!
    echo.
    echo ========================================
    echo Database Setup Complete!
    echo ========================================
    echo.
    echo Database: patient_management
    echo Tables: 5 (users, patients, follow_ups, panchakarma, todo_items)
    echo Sample Data: Loaded
    echo.
    echo Login credentials:
    echo   Username: admin
    echo   Password: password123
    echo.
) else (
    echo Warning: Sample data insertion had issues.
    echo.
)

echo Next steps:
echo 1. Run compile.bat
echo 2. Run run.bat
echo.
pause

