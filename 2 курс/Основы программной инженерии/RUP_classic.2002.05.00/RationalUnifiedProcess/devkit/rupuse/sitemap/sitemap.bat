copy *.gif ..\..\..\sitemap\.
copy sitemap.htm ..\..\..\sitemap\.
copy sitemap.css ..\..\..\sitemap\.
copy buttons.htm ..\..\..\sitemap\.
..\..\..\..\common\java\jre\bin\java -classpath . buildTree
..\..\..\..\common\java\jre\bin\java  -classpath ..\..\..\applet ruptools.SiteMap -r overviewdef.txt
..\..\..\..\common\java\jre\bin\java  -classpath ..\..\..\applet ruptools.SiteMap -r workflowdef.txt
..\..\..\..\common\java\jre\bin\java  -classpath ..\..\..\applet ruptools.SiteMap -r toolmentorsdef.txt
..\..\..\..\common\java\jre\bin\java  -classpath ..\..\..\applet ruptools.SiteMap -r artifactdef.txt
..\..\..\..\common\java\jre\bin\java  -classpath ..\..\..\applet ruptools.SiteMap -r workerdef.txt