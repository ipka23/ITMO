rm -rf "svnRepo"
rm -rf "workingCopy"
svnadmin create "svnRepo"

REPO_URL="file://$(pwd)/svnRepo"

svn mkdir "${REPO_URL}/trunk" "${REPO_URL}/branches" -m "init"

TRUNK="${REPO_URL}/trunk"
ALT_RED="${REPO_URL}/branches/altRed"
ALT_BLUE="${REPO_URL}/branches/altBlue"
COMMITS="../../commits"

svn checkout "$TRUNK" "workingCopy"
cd "workingCopy" || exit 1

unzip -o "${COMMITS}/commit0.zip" -d "src"
svn add *
svn commit -m "r0" --username="Red"

unzip -o "${COMMITS}/commit1.zip" -d "src"
svn add *
svn commit -m "r1" --username="Red"

unzip -o "${COMMITS}/commit2.zip" -d "src"
svn add *
svn commit -m "r2" --username="Red"

unzip -o "${COMMITS}/commit3.zip" -d "src"
svn add *
svn commit -m "r3" --username="Red"

svn copy "$TRUNK" "$ALT_RED" -m "creating altRed branch"
svn switch "$ALT_RED"

unzip -o "${COMMITS}/commit4.zip" -d "src"
svn add *
svn commit -m "r4" --username="Red"

svn copy "$TRUNK" "$ALT_BLUE" -m "creating altBlue branch"
svn switch "$ALT_BLUE"

unzip -o "${COMMITS}/commit5.zip" -d "src"
svn add *
svn commit -m "r5" --username="Blue"

svn switch "$TRUNK"
unzip -o "${COMMITS}/commit6.zip" -d "src"
svn add *
svn commit -m "r6" --username="Red"

svn switch "$ALT_RED"
unzip -o "${COMMITS}/commit7.zip" -d "src"
svn add *
svn commit -m "r7" --username="Red"

svn switch "$TRUNK"
unzip -o "${COMMITS}/commit8.zip" -d "src"
svn add *
svn commit -m "r8" --username="Red"

svn switch "$ALT_RED"
unzip -o "${COMMITS}/commit9.zip" -d "src"
svn add *
svn commit -m "r9" --username="Red"

svn switch "$ALT_BLUE"
unzip -o "${COMMITS}/commit10.zip" -d "src"
svn add *
svn commit -m "r10" --username="Blue"

svn switch "$ALT_RED"
unzip -o "${COMMITS}/commit11.zip" -d "src"
svn add *
svn commit -m "r11" --username="Red"
svn merge "$ALT_BLUE"

svn switch "$TRUNK"
unzip -o "${COMMITS}/commit12.zip" -d "src"
svn add *
svn commit -m "r12" --username="Red"
svn merge "$ALT_RED"

unzip -o "${COMMITS}/commit13.zip" -d "src"
svn add *
svn commit -m "r13" --username="Red"

unzip -o "${COMMITS}/commit14.zip" -d "src"
svn add *
svn commit -m "r14" --username="Red"