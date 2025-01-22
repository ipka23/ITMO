javac -d out -sourcepath src src/Main.java
jar -cfm run.jar MANIFEST.mf -C out .