#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

int main() {
  int n, k;
  cin >> n >> k;

  vector<int> prices(n);
  for (int i = 0; i < n; i++) {
    cin >> prices[i];
  }

  sort(prices.begin(), prices.end(), greater<int>());

  int s = 0;

  for (int i = 0; i < n; i++) {
    if ((i + 1) % k == 0) {
      continue;
    }
    s += prices[i];
  }

  cout << s << endl;

  return 0;
}