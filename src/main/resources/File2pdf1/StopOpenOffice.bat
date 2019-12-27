@echo openoffice
cd /d %~dp0
title %~dp0[stop]
@echo **********stop**********
@taskkill /f /im soffice.exe
pause