// #include <iostream>
// #include <string>
// #include <vector>
// using namespace std;
// void solve() {
//   vector<string> l;
//   string n;
//   while (cin >> n) {
//     l.push_back(n);
//   }
//   for (size_t _ = 0; _ < l.size() - 1; _++) {
//     for (size_t i = 0; i < l.size() - 1; i++) {
//       if (l[i] + l[i + 1] < l[i + 1] + l[i]) {
//         string tmp = l[i];
//         l[i] = l[i + 1];
//         l[i + 1] = tmp;
//       }
//     }
//   }
//   for (size_t i = 0; i < l.size(); i++) {
//     cout << l[i];
//   }
// }
// int main() {
//   solve();
// }