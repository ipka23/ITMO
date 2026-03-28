// #include <iostream>
// #include <vector>
// using namespace std;
//
// bool check(int d, vector<int>& coords, int k) {
//   int lastCow = coords[0];
//   int cowsCount = 1;
//   for (int x : coords) {
//     if (x - lastCow >= d) {
//       cowsCount++;
//       lastCow = x;
//       if (cowsCount >= k) {
//         return true;
//       }
//     }
//   }
//   return false;
// }
//
// int solve() {
//   int n, k;
//   cin >> n >> k;
//   vector<int> coords(n);
//
//   for (int i = 0; i < n; i++) {
//     cin >> coords[i];
//   }
//
//   int l = 0;
//   int r = coords[n - 1];
//
//   while (r - 1 > l) {
//     int mid = (l + r) / 2;
//     if (check(mid, coords, k)) {
//       l = mid;
//     } else {
//       r = mid;
//     }
//   }
//   return l;
// }
//
// int main() {
//   cout << solve();
// }
