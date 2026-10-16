@echo off
chcp 65001 >nul
cd /d "%~dp0"

echo [1/3] Dang chuan bi thu muc target\classes...
if not exist "target\classes" mkdir "target\classes"

echo [2/3] Dang bien dich ma nguon Java...
powershell -NoProfile -Command "$srcs = (Get-ChildItem -Path 'src\main\java' -Filter '*.java' -Recurse).FullName; javac -encoding UTF-8 -cp 'lib/*' -d target\classes $srcs"
if %ERRORLEVEL% neq 0 (
    echo [LOI] Bien dich that bai!
    pause
    exit /b %ERRORLEVEL%
)

xcopy /E /I /Y src\main\resources target\classes >nul

echo [3/3] Dang khoi chay chuong trinh...
echo ========================================================
java -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -cp "target\classes;lib\*" com.microservicesdemo.java.Main
echo ========================================================
pause
