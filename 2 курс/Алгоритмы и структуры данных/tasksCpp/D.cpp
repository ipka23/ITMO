#include <bits/stdc++.h>
using namespace std;
using int64 = long long;

int main() {
  int64 a, b, c, d, k;
  cin >> a >> b >> c >> d >> k;

  int64 x = a;
  vector<int64> seen(d + 1, -1);
  int64 day = 0;

  while (day < k) {
    if (seen[x] != -1) {
      int64 prevDay = seen[x];
      int64 cycleLen = day - prevDay;
      if (cycleLen > 0) {
        int64 remainingDays = k - day;
        int64 skipDays = remainingDays % cycleLen;
        for (int64 i = 0; i < skipDays; ++i) {
          int64 nextX = x * b;
          if (nextX < c) {
            cout << 0 << '\n';
            return 0;
          }
          nextX -= c;
          x = min(nextX, d);
        }
        day = k;
        break;
      }
    }

    seen[x] = day;

    int64 nextX = x * b;
    if (nextX < c) {
      cout << 0 << '\n';
      return 0;
    }

    nextX -= c;
    x = min(nextX, d);
    day++;
  }

  cout << x << '\n';
}