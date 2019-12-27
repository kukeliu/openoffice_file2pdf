@echo openoffice
cd /d %~dp0
title %~dp0[install]
@echo ************************** start *************************
@ start soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
@echo **************************sucess**************************
pause