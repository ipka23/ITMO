rm build/libs/server.jar
gradle build

scp -P 2222  webapp/conf/httpd.conf helios:~/httpd-root/conf/
scp -P 2222 build/libs/server.jar helios:~/httpd-root/fcgi-bin/
scp -rP 2222 webapp/index.html webapp/script.js webapp/libs webapp/intl helios:~/httpd-root