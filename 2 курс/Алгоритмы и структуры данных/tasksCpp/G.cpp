// #include <iostream>
// #include <sstream>
// #include <string>
// #include <vector>
//
// using namespace std;
//
// char getChar(int index) {
//   return 'a' + index;
// }
//
// int main() {
//   string s;
//   getline(cin, s);
//
//   vector<char> l;
//   for (int i = 0; i < s.length(); i++) {
//     l.push_back(s[i]);
//   }
//   string line;
//   getline(cin, line);
//   vector<string> weights;
//   stringstream ss(line);
//   string weight;
//
//   while (ss >> weight) {
//     weights.push_back(weight);
//   }
//
//   for (int _ = 0; _ < l.size() - 1; _++) {
//     for (int i = 0; i < l.size() - 1; i++) {
//       if (l[i] < l[i + 1]) {
//         char tmp = l[i + 1];
//         l[i + 1] = l[i];
//         l[i] = tmp;
//       }
//     }
//   }
//
//   int ans_i = s.length();
//   vector<string> ans(ans_i, "");
//
//   vector<int> freq(26, 0);
//   for (char n : l) {
//     freq[n - 'a']++;
//   }
//
//   vector<string> tmp;
//
//   for (int i = 25; i >= 0; i--) {
//     char ch = getChar(i);
//     int fr = freq[i];
//     if (fr >= 2) {
//       ans[ans_i - 1] = string(1, ch);
//       ans[s.length() - ans_i] = string(1, ch);
//       ans_i--;
//       while (fr - 2 != 0) {
//         tmp.push_back(string(1, ch));
//         fr--;
//       }
//     } else if (fr == 1) {
//       tmp.push_back(string(1, ch));
//     }
//   }
//
//   int n = ans.size();
//   int j = 0;
//   for (int i = 0; i < ans.size(); i++) {
//     if (ans[i] == "") {
//       ans[i] = tmp[j];
//       j++;
//     }
//   }
//
//   for (int i = 0; i < ans.size(); i++) {
//     cout << ans[i];
//   }
//   cout << endl;
//
//   return 0;
// }