gradle build
cd /Users/ipka23/Documents/wildfly-37.0.1.Final/bin
./jboss-cli.sh --connect --controller=localhost:25232
deploy "/Users/ipka23/Desktop/ITMO/2 курс/Веб-программирование/lab3/lab3/build/libs/lab3.war" --force