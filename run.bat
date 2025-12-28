@echo off
REM Run script for Patient Management System
REM Windows batch file
REM MariaDB Java Client 3.5.7

echo ========================================
echo Patient Management System - Run
echo ========================================
echo.

REM Check if compiled
if not exist "bin\App.class" (
    echo ERROR: Application not compiled!
    echo Please run compile.bat first
    pause
    exit /b 1
)

REM Check if lib folder has JDBC driver
if not exist "lib\mariadb-java-client-3.5.7.jar" (
    echo ERROR: mariadb-java-client-3.5.7.jar not found in lib\ folder
    echo Please place the JAR file in lib\ folder
    pause
    exit /b 1
)

echo Starting application...
echo Using: mariadb-java-client-3.5.7.jar
echo.

cd bin
java -cp ".;..\lib\mariadb-java-client-3.5.7.jar" App

cd ..
pause

