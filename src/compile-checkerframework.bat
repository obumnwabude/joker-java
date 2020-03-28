echo
rmdir /s /q ..\build\classes
java -jar "C:\Program Files\checker-framework-3.2.0\checker\dist\checker.jar" -processor nullness,optional -d ../build/classes joker/app/*.java joker/board/*.java joker/board/situe/*.java joker/playing/*.java joker/shellgame/*.java joker/util/*.java
pause