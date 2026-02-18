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
  int start = 0;
  int count = 0;
  int maxCount = 0;
  int minStart = 200000;
  int minEnd = 200000;
  int repeat = 0;
  for (int i = 0; i < arr.size() - 1; ++i) {
    if (arr[i] == arr[i + 1]) {
      if (repeat < 2) {
        repeat++;
        count++;
      } else {
        start = i;
        repeat++;
        count = 0;
      }
    } else {
      repeat = 1;
      count++;
    }

    if (i == arr.size() - 2) {
      count++;
    }
    if (maxCount < count) {
      maxCount = count;
    }
  }
  int end = start + maxCount;
  std::cout << start + 1 << " " << end + 1 << std::endl;
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