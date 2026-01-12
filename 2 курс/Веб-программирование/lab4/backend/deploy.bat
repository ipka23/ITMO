gradle build
cd C:\wildfly-37.0.1.Final\bin
./jboss-cli.bat --connect --controller=localhost:25232
deploy "C:\Users\ipka23\ITMO\2 курс\Веб-программирование\lab4\lab4\build\libs\lab4.war" --force
