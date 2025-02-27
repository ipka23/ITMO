javac -d bin -sourcepath src -cp libs/gson-2.10.1.jar src/Main.java
echo -e "Main-Class: Main\nClass-Path: libs/gson-2.10.1.jar" > MANIFEST.mf
jar cfm lab5.jar MANIFEST.mf -C bin .