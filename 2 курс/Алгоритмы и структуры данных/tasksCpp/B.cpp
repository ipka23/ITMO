#include <cctype>
#include <iostream>
#include <stack>
#include <string>
#include <vector>

using namespace std;

int main() {
  string s;
  cin >> s;
  int n = s.size();
  if (n % 2 != 0) {
    cout << "Impossible\n";
    return 0;
  }

  vector<int> animalNum(n, 0);
  vector<int> ans(n, 0);
  stack<int> st;
  int animalCounter = 0;

  for (int i = 0; i < n; ++i) {
    char ch = s[i];
    if (islower(ch)) {
      ++animalCounter;
      animalNum[i] = animalCounter;
    }

    if (!st.empty()) {
      int topIdx = st.top();
      char topCh = s[topIdx];
      if (toupper(ch) == toupper(topCh) && islower(ch) != islower(topCh)) {
        if (islower(ch)) {
          ans[topIdx] = animalNum[i];
        } else {
          ans[i] = animalNum[topIdx];
        }
        st.pop();
        continue;
      }
    }
    st.push(i);
  }

  if (!st.empty()) {
    cout << "Impossible\n";
  } else {
    cout << "Possible\n";
    bool first = true;
    for (int i = 0; i < n; ++i) {
      if (isupper(s[i])) {
        if (!first)
          cout << ' ';
        cout << ans[i];
        first = false;
      }
    }
    cout << '\n';
  }

  return 0;
}