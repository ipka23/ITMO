rm -rf gitRepo
git init gitRepo
cd gitRepo
git config user.name "Red"
git config user.email red@gmail.com
git branch -m master main

unzip -o ../../commits/commit0.zip -d src
git add .
git commit -m "r0"

unzip -o ../../commits/commit1.zip -d src
git add .
git commit -m "r1"

unzip -o ../../commits/commit2.zip -d src
git add .
git commit -m "r2"

git branch altRed
git checkout altRed

unzip -o ../../commits/commit3.zip -d src
git add .
git commit -m "r3"

unzip -o ../../commits/commit4.zip -d src
git add .
git commit -m "r4"

git checkout -b altBlue
unzip -o ../../commits/commit5.zip -d src
git add .
git commit -m "r5" --author="Blue <blue@gmail.com>"

git checkout main

unzip -o ../../commits/commit6.zip -d src
git add .
git commit -m "r6"

git checkout altRed

unzip -o ../../commits/commit7.zip -d src
git add .
git commit -m "r7"

git checkout main

unzip -o ../../commits/commit8.zip -d src
git add .
git commit -m "r8"

git checkout altRed

unzip -o ../../commits/commit9.zip -d src
git add .
git commit -m "r9"

git checkout altBlue
unzip -o ../../commits/commit10.zip -d src
git add .
git commit -m "r10" --author="Blue <blue@gmail.com>"

git checkout altRed

unzip -o ../../commits/commit11.zip -d src
git add .
git commit -m "r11"
git merge altBlue -m "merge altBlue into altRed"

git checkout main

unzip -o ../../commits/commit12.zip -d src
git add .
git commit -m "r12"
git merge altRed -m "merge altRed into main"

unzip -o ../../commits/commit13.zip -d src
git add .
git commit -m "r13"

unzip -o ../../commits/commit14.zip -d src
git add .
git commit -m "r14"