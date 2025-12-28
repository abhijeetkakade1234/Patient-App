@echo off
REM Compile script for Patient Management System
REM Windows batch file
REM MariaDB Java Client 3.5.7

echo ========================================
echo Patient Management System - Compile
echo ========================================
echo.

REM Check if lib folder has JDBC driver
if not exist "lib\mariadb-java-client-3.5.7.jar" (
    echo ERROR: mariadb-java-client-3.5.7.jar not found in lib\ folder
    echo Please place mariadb-java-client-3.5.7.jar in lib\ folder
    pause
    exit /b 1
)

echo Compiling Java files...
echo.

cd src

javac -d ..\bin -cp "..\lib\*" ^
  Dashboard.java ^
  App.java ^
  model\*.java ^
  util\*.java ^
  dao\*.java ^
  service\*.java ^
  controller\*.java ^
  view\*.java ^
  view\components\*.java ^
  view\util\*.java ^
  view\patient\*.java ^
  view\followup\*.java ^
  view\panchakarma\*.java

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo Compilation successful!
    echo ========================================
    echo.
    echo Compiled files are in: bin\
    echo.
    echo To run the application:
    echo   cd bin
    echo   run.bat
    echo.
) else (
    echo.
    echo ========================================
    echo Compilation failed!
    echo ========================================
    echo.
    echo Please check the error messages above.
    echo.
)

cd ..
pause

