// #include <algorithm>
// #include <iostream>
// #include <numeric>
// #include <string>
// #include <vector>
//
// using namespace std;
//
// int main() {
//   string s;
//   cin >> s;
//
//   vector<long long> weight(26);
//   for (int i = 0; i < 26; ++i) {
//     cin >> weight[i];
//   }
//
//   vector<int> freq(26, 0);
//   for (char c : s) {
//     freq[c - 'a']++;
//   }
//
//   vector<int> order(26);
//   iota(order.begin(), order.end(), 0);
//   sort(order.begin(), order.end(), [&](int a, int b) {
//     if (weight[a] != weight[b])
//       return weight[a] > weight[b];
//     return a < b;
//   });
//
//   int n = (int)s.size();
//   string ans(n, '\0');
//   int left = 0, right = n - 1;
//
//   while (true) {
//     vector<int> candidates;
//     for (int id : order) {
//       if (freq[id] >= 2) {
//         candidates.push_back(id);
//       }
//     }
//
//     if (candidates.empty() || left > right) {
//       break;
//     }
//
//     for (int id : candidates) {
//       if (left > right)
//         break;
//
//       ans[left++] = char('a' + id);
//       ans[right--] = char('a' + id);
//       freq[id] -= 2;
//     }
//   }
//
//   string middle;
//   for (int id : order) {
//     while (freq[id] > 0) {
//       middle.push_back(char('a' + id));
//       --freq[id];
//     }
//   }
//
//   int idx = 0;
//   for (int i = 0; i < n; ++i) {
//     if (ans[i] == '\0') {
//       ans[i] = middle[idx++];
//     }
//   }
//
//   cout << ans << '\n';
//   return 0;
// }