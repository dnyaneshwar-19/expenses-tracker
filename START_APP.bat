@echo off
echo ========================================
echo   Expenses Tracker - Quick Start
echo ========================================
echo.
echo This will start both backend and frontend
echo.
echo Prerequisites:
echo - MySQL running with 'expenses_tracker' database
echo - Java 17+ installed
echo - Node.js 16+ installed
echo.
pause

echo.
echo Starting Backend (Spring Boot)...
echo.
start "Expenses Tracker Backend" cmd /k "cd expenses_tracker_backend && mvnw.cmd spring-boot:run"

echo Waiting 10 seconds for backend to start...
timeout /t 10 /nobreak

echo.
echo Starting Frontend (React)...
echo.
start "Expenses Tracker Frontend" cmd /k "cd expenses_tracker_frontend && npm start"

echo.
echo ========================================
echo   Application Starting!
echo ========================================
echo.
echo Backend: http://localhost:8083
echo Frontend: http://localhost:3000
echo.
echo The frontend will open automatically in your browser.
echo.
echo To stop: Close both terminal windows
echo.
pause
