#include <stdbool.h>
#include <stdio.h>

void inputArray(int arr[], int n) {
  for (int i = 0; i < n; ++i) {
    int x;
    scanf("%d", &x);
    arr[i] = x;
  }
}

void sortFlowers(int arr[], int n) {
  int i = 1;
  int start = 0;
  int end = 0;
  int count = 0;
  while (i < n - 2) {
    if ((arr[i] != arr[i + 1] && arr[i] != arr[i + 2]) ||
        (arr[i] != arr[i + 1] || arr[i] != arr[i + 2])) {
      start = i;
      count += 3;
      i += 2;
    } else {
      i += 1;
    }
    end = start + count;
  }
  printf("%d %d\n", start + 1, end + 1);
}

void inputFlowers() {
  int n;
  while (true) {
    scanf("%d", &n);
    if (n > 0 || n < 200000) {
      break;
    }
  }
  int flowers[n];
  inputArray(flowers, n);
  sortFlowers(flowers, n);
}

int main(void) {
  inputFlowers();
  return 0;
}