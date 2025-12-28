@echo off
REM Database setup script for Patient Management System
REM Windows batch file - Fixed version

echo ========================================
echo Patient Management System
echo Database Setup - Fixed Version
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
echo 1. Create the patient_management database (if not exists)
echo 2. Create all required tables
echo 3. Insert sample data (optional)
echo.
echo NOTE: You'll be prompted for your MariaDB root password ONCE
echo.
pause

echo.
echo ========================================
echo Connecting to MariaDB...
echo ========================================
echo.
echo Please enter your MariaDB root password when prompted.
echo Password: root
echo.

REM Use mysql with -p flag and let user enter password once
mysql -u root -p -e "SOURCE sql/01_create_database.sql; USE patient_management; SOURCE sql/02_create_tables.sql;"

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ========================================
    echo ERROR: Database setup failed!
    echo ========================================
    echo.
    echo Possible issues:
    echo 1. Incorrect password
    echo 2. MariaDB service not running
    echo 3. Permission issues
    echo.
    echo Try these commands:
    echo   net start MariaDB
    echo   mysql -u root -proot -e "SHOW DATABASES;"
    echo.
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
    mysql -u root -proot patient_management < sql\03_insert_sample_data.sql
    
    if %ERRORLEVEL% EQU 0 (
        echo.
        echo Sample data inserted successfully!
        echo.
        echo Login credentials:
        echo   Username: admin
        echo   Password: password123
        echo.
    ) else (
        echo.
        echo Warning: Failed to insert sample data.
        echo You can insert it manually later.
        echo.
    )
)

echo.
echo ========================================
echo Setup Complete!
echo ========================================
echo.
echo Next steps:
echo 1. Run compile.bat to compile the project
echo 2. Run run.bat to start the application
echo.
pause

