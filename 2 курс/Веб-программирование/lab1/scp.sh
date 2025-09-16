rm build/libs/server.jar
gradle build

scp -P 2222  web-app/conf/httpd.conf helios:~/httpd-root/conf/
scp -P 2222 build/libs/server.jar helios:~/httpd-root/fcgi-bin/
scp -rP 2222 web-app/index.html web-app/index.js helios:~/httpd-root