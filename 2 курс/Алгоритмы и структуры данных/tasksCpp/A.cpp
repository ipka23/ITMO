// #include <cstdio>
// #include <vector>
//
// void inputArray(std::vector<int>& arr, int n) {
//   for (int i = 0; i < n; i++) {
//     int x;
//     scanf("%d", &x);
//     arr[i] = x;
//   }
// }
//
// void sortFlowers(std::vector<int>& arr, int n) {
//   int start = 0;
//   int bestStart = 0;
//   int bestEnd = 0;
//   int maxLen = 1;
//   int sameCounter = 1;
//   for (int i = 1; i < n; i++) {
//     if (arr[i] == arr[i - 1]) {
//       sameCounter++;
//     } else {
//       sameCounter = 1;
//     }
//     if (sameCounter == 3) {
//       int currLen = i - start;
//       if (currLen > maxLen) {
//         maxLen = currLen;
//         bestStart = start;
//         bestEnd = i - 1;
//       }
//       start = i - 1;
//       sameCounter = 2;
//     }
//     int currLen = i - start + 1;
//     if (currLen > maxLen) {
//       maxLen = currLen;
//       bestStart = start;
//       bestEnd = i;
//     }
//   }
//   int currLen = n - start;
//   if (currLen > maxLen) {
//     bestStart = start;
//     bestEnd = n - 1;
//   }
//   printf("%d %d", bestStart + 1, bestEnd + 1);
// }
//
// void inputFlowers() {
//   int n;
//   scanf("%d", &n);
//   std::vector<int> flowers(n);
//   inputArray(flowers, n);
//   sortFlowers(flowers, n);
// }
//
// int main() {
//   inputFlowers();
//   return 0;
// }