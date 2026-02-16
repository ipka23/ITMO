#include <iostream>
#include <vector>

void inputArray(std::vector<int>& arr, int n) {
  for (int i = 0; i < n; ++i) {
    int x;
    std::cin >> x;
    arr[i] = x;
  }
}

void sortFlowers(std::vector<int>& arr, int n) {
  int i = 1;
  int start = 0;
  int count = 1;
  int maxCount = 0;
  int minStart = 200000;
  int minEnd = 200000;
  while (i < n - 1) {
    if (arr[i - 1] == arr[i] && arr[i] == arr[i + 1]) {
      count++;
      if (maxCount < count)
        maxCount = count;
      count = 1;
      start = i;
      i++;
    } else {
      count++;
      if (maxCount < count) {
        maxCount = count;
      }
      i++;
    }

    if (maxCount < count) {
      maxCount = count;
      minStart = start;
      minEnd = minStart + maxCount;
    } else if (maxCount == count) {
      minStart = start;
      minEnd = minStart + count;
    }
  }
  std::cout << minStart + 1 << " " << minEnd + 1 << std::endl;
}

void inputFlowers() {
  int n;
  while (true) {
    std::cin >> n;
    if (n > 0 && n < 200000) {
      break;
    }
  }
  std::vector<int> flowers(n);
  inputArray(flowers, n);
  sortFlowers(flowers, n);
}

int main() {
  inputFlowers();
  return 0;
}