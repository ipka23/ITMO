#include <iostream>

void inputArray(int arr[], int n) {
    for (int i = 0; i < n; ++i) {
        int x;
        std::cin >> x;
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
    std::cout << start + 1 << " " << end + 1;
}

void inputFlowers() {
    int n;
    while (true) {
        std::cin >> n;
        if (n > 0 || n < 200000)
            break;
    }
    int* flowers = new
    int [n];
    inputArray(flowers, n);
    sortFlowers(flowers, n);
    delete[]
    flowers;
}

int main() {
    inputFlowers();
    return 0;
}