rm build/libs/server.jar
gradle build

scp -P 2222  web-app/conf/httpd.conf s467204@se.ifmo.ru:~/httpd-root/conf/
scp -P 2222 build/libs/server.jar s467204@se.ifmo.ru:~/httpd-root/fcgi-bin/
scp -rP 2222 web-app/index.html web-app/index.js s467204@se.ifmo.ru:~/httpd-root