@echo off
echo ========================================
echo   Testing Backend Login
echo ========================================
echo.

set /p username="Enter username:riya "
set /p password="Enter password:riya1234 "

echo.
echo Testing login for user: riya
echo.

curl -X POST http://localhost:8083/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"riya\",\"password\":\"riya1234\"}" ^
  -v

echo.
echo.
echo ========================================
echo If you see "200 OK" above, login works!
echo If you see "401", credentials are wrong
echo If you see "Connection refused", backend is not running
echo ========================================
echo.
pause
