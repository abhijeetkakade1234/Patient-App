@echo off
REM Database setup script for Patient Management System
REM Windows batch file

echo ========================================
echo Patient Management System
echo Database Setup
echo ========================================
echo.

REM Check if SQL files exist
if not exist "sql\01_create_database.sql" (
    echo ERROR: SQL files not found!
    echo Please ensure sql\ folder contains the SQL scripts
    pause
    exit /b 1
)

echo This script will:
echo 1. Create the patient_management database
echo 2. Create all required tables
echo 3. Insert sample data (optional)
echo.
echo You will be prompted for your MariaDB root password.
echo.
pause

echo.
echo Step 1: Creating database...
echo.
mysql -u root -p < sql\01_create_database.sql

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Failed to create database!
    echo Please check your MariaDB connection.
    pause
    exit /b 1
)

echo.
echo Step 2: Creating tables...
echo.
mysql -u root -p patient_management < sql\02_create_tables.sql

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Failed to create tables!
    pause
    exit /b 1
)

echo.
echo ========================================
echo Database setup successful!
echo ========================================
echo.
echo Database: patient_management
echo Tables: users, patients, follow_ups, panchakarma, todo_items
echo.

REM Ask about sample data
set /p SAMPLE="Do you want to insert sample data? (Y/N): "
if /i "%SAMPLE%"=="Y" (
    echo.
    echo Inserting sample data...
    mysql -u root -p patient_management < sql\03_insert_sample_data.sql
    echo.
    echo Sample data inserted!
    echo.
    echo Login credentials:
    echo   Username: admin
    echo   Password: password123
    echo.
)

echo.
echo Next steps:
echo 1. Update password in src\util\DBUtil.java
echo 2. Run compile.bat to compile the project
echo 3. Run run.bat to start the application
echo.
pause

