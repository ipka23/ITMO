#include <stdio.h>
#include <iso646.h>
#include <stdbool.h>
#define FLOWERS_MAX_SIZE 200000
#define FLOWERS_TYPES_MAX_SIZE 10000000000

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
    int maxCount = -1;
    // while (i < n - 1) {
    //     if (arr[i] != arr[i - 1] or arr[i] != arr[i + 1]) {
    //         start = i;
    //         count += 3;
    //         end = start + count;
    //         i++;
    //     } else {
    //         end += count;
    //         if (count > maxCount) {
    //             maxCount = count;
    //             start = end - maxCount;
    //         }
    //         i++;
    //     }
    // }

    while (i < n - 2) {
        if (arr[i] != arr[i + 1] and arr[i] != arr[i + 2]) {
            start = i;
            count += 3;
            end = start + count;
            i += 2;
        } else if (arr[i] != arr[i + 1] or arr[i] != arr[i + 2]) {
            start = i;
            count += 3;


        }
    }

    // while (i < n - 1) {
    //     if (arr[i] != arr[i - 1]) {
    //         start = i - 1;
    //         count++;
    //         i++;
    //     } else if (arr[i] != arr[i + 1]) {
    //         count++;
    //         i += 2;
    //     } else {
    //         count = i - start;
    //         end = start + count;
    //         i++;
    //     }
    // }
    printf("%d %d", start + 1, end + 1);
}

void inputFlowers() {
    int n;
    while (true) {
        scanf("%d", &n);
        if (n > 0 or n < FLOWERS_MAX_SIZE)
            break;
    }
    int flowers[n];
    inputArray(flowers, n);

    sortFlowers(flowers, n);


}

void test(int exArr[], int exN) {
    int n = 3;
    int a[] = {1, 1, 2, 5, 2, 5, 5, 5, 2, 1, 1, 7};
    int start = 0;
    int cnt = 0;
    if (a[0] != a[1] or a[0] != a[1]) {
        start = 1; // start = i
        cnt += 2; // i++;
        if (a[1] != a[2] or a[1] != a[3]) {
            false;
        }
        // по сути второй цикл
    } else if (a[1] != a[2] or a[1] != a[3]) {

    }

}


void f() {
    for (int i = 0; i < 10; ++i) {
        printf("%d ", i);
        i += 2;

    }
}

int main(void) {
    inputFlowers();
    return 0;
}
