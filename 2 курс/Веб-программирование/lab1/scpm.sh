rm build/libs/server.jar
gradle build

scp -P 2222  webapp/conf/httpd.conf s467204@helios.cs.ifmo.ru:~/httpd-root/conf/
scp -P 2222 build/libs/server.jar s467204@helios.cs.ifmo.ru:~/httpd-root/fcgi-bin/
scp -rP 2222 webapp/index.html webapp/script.js s467204@helios.cs.ifmo.ru:~/httpd-root