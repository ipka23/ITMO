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
  int i = 0;
  int start = 0;
  int end = 0;
  int count = 0;
  int maxCount = 0;
  bool newSequence = false;
  while (i < n - 2) {
    if (newSequence) {
      start = i;
      count = 0;
    }
    if (((arr[i] != arr[i + 1] && arr[i] != arr[i + 2]) ||
         (arr[i] != arr[i + 1] || arr[i] != arr[i + 2])) &&
        arr[i + 1] != arr[i + 2]) {
      count += 3;
      i += 3;
      newSequence = false;
    } else if (((arr[i] != arr[i + 1] && arr[i] != arr[i + 2]) ||
                (arr[i] != arr[i + 1] || arr[i] != arr[i + 2])) &&
               arr[i + 1] == arr[i + 2]) {
      i++;
      count++;
      newSequence = false;
    } else {
      i++;
      count += 2;
      newSequence = true;
    }
    if (i >= n - 2) {
      count++;
    }
    if (maxCount < count) {
      maxCount = count;
    }
  }
  end = start + maxCount;
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