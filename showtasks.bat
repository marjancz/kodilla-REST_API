call runcrud.bat
SLEEP 15
if "%ERRORLEVEL%" == "0" goto startFirefox
echo.
echo runcrud.bat has errors - breaking work
goto fail

:startFirefox
set getTasks="http://localhost:8080/crud/v1/task/getTasks"
Start "" "%ProgramFiles%\Mozilla Firefox\firefox.exe" %getTasks%
set SleepTime=10
Timeout /T %SleepTime% /NoBreak>NUL
Taskkill /IM "firefox.exe" /F
goto stoptomcat

:stoptomcat
call %CATALINA_HOME%\bin\shutdown.bat
goto end

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.