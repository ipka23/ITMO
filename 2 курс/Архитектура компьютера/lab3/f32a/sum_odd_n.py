def sum_odd_n(n):
    """Calculate the sum of odd numbers from 1 to n"""
    if n <= 0:
        return -1
    total = 0
    for i in range(1, n + 1):
        if i % 2 != 0:
            # print(i)
            total += i
    # print("---")
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



# c = 0
n = 100000
#
# for i in range(1, n + 1):
#     if i % 2 != 0:
#         c += 1
# print(c)
# print("---")
# for n in range(1, n + 1):
#     print(f"n = {n} | s1 = {(n // 2 + n % 2)**2} | s2 = {sum_odd_n(n)}")
# print(5//2)'
print(sum_odd_n(100000))
print(hex(2**31))
print(hex(-1794967296))
print(hex(2**31 - 1794967296))