def sum_odd_n(n):
    """Calculate the sum of odd numbers from 1 to n"""
    if n <= 0:
        return -1
    total = 0
    for i in range(1, n + 1):
        if i % 2 != 0:
            print(i)
            total += i
    print("---")
    return total


def sum_n(n):
    """Calculate the sum of odd numbers from 1 to n"""
    if n <= 0:
        return -1
    total = 0
    for i in range(1, n + 1):
        total += i
    return total


# assert sum_odd_n(5) == 9
# assert sum_odd_n(10) == 25
# assert sum_odd_n(90000) == 2025000000


# print(sum_odd_n(5))
c = 0
n = 5
for i in range(1, n + 1):
    if i % 2 != 0:
        c += 1
print(c)
print("---")
print(n // 2 + n % 2)
