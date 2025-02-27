@echo off
setlocal enabledelayedexpansion

set "project_path=C:\Users\ilyai\OneDrive\Рабочий стол\ITMO\subj\PROG\Labs\Lab5.0\Lab5Main\lab5"
set "output_file=%project_path%\all_code.txt"

if not exist "%project_path%" (
    echo Ошибка: путь %project_path% не найден!
    pause
    exit /b
)

echo. > "%output_file%"

for /f "delims=" %%f in ('dir /s /b "%project_path%\*.java"') do (
    echo Добавляю файл: %%f
    type "%%f" >> "%output_file%"
    echo. >> "%output_file%"
)

echo Код сохранен в %output_file%
pause