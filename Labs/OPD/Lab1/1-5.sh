#1
mkdir lab0
cd lab0
mkdir -p clamperl4/vileplume clamperl4/poochyena
cd clamperl4
echo "satk=5 sdef=9 spd=8" > stoutland
cd ..
echo -e "Возможности Overland=4\nSurface=2 Jump=2 Power=1 Intelligence=5\nTelekinetic=0" > duosion6
echo -e "weigth=89.1 height=28.0 atk=14\ndef=6" > excadrill7
mkdir graveler2
cd graveler2
echo -e "weigth=209.4 height=67.0 atk=10\ndef=7" > rapidash
echo -e "satk=5 sdef=5 spd=10" > staraptor
echo -e "Развитые способности\nContrary" > servine
cd ..
mkdir taillow0
cd taillow0
echo "Живет Beach Freshwater Ocean" > blastoise
echo -e "Тип диеты\nNullivore" > bronzong
echo "satk=7 sdef=8 spd=5" > beartic
echo -e "Возможности\nOverland=3 Surface=2 Sky=8 Jump=2 Power1=0 Intelligence=4 Telepath=0" > chimecho
echo -e "Способности Growl Absorb Nature Power Fake Out\nFury Swipes Water Sport Bubblebeam Zen Headbutt Uproar Hydro\nPump" > lombre
cd ..
echo -e "Возможности Overland=5 Surface=4 Jump=1 Power=2\nIntelligence=4 Inflatable=0" > trubbish4
cd ..



#2
cd lab0
chmod u+rwx-w clamperl4
chmod g+rwx clamperl4
chmod o+rwx clamperl4
cd clamperl4
chmod u+rw-x stoutland
chmod g+w-r-x stoutland
chmod o+w-r-x stoutland
chmod a= vileplume
chmod u+wx vileplume
chmod g+rwx vileplume
chmod o+rx vileplume
chmod a= poochyena
chmod u+wx poochyena
chmod g+rw poochyena
chmod o+wx poochyena
cd ..
chmod 404 duosion6
chmod a= excadrill7
chmod a+r excadrill7
chmod u+rwx graveler2
chmod g+wx graveler2
chmod o+rwx graveler2
cd graveler2
chmod u+rw-x rapidash
chmod g= rapidash
chmod o+r-wx rapidash
chmod a= staraptor
chmod g+r staraptor
chmod o+r staraptor
chmod 046 servine
cd ..