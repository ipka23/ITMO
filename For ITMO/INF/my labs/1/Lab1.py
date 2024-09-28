# z = (1 + 5**0.5) / 2
# i = '543210 123456'
# A = '101010.000001'
# print(z**5 + z**3 + z**1  + z**-6)

def f(n):
    if n == 0:
        return 1
    return f(n-1)*n
print()

def fib(n):
    if n < 2:
        return 1
    else:
        return fib(n-1) + fib(n-2)

#
# dec_num = int(input())
# fib_numbers = [fib(n) for n in range(1, 30) if fib(n) < dec_num]
# fib_len = len(str(dec_num))
# fib_num = ''
# ind = 0
# while fib_numbers != []:
#     fib_num += str(int((dec_num - fib_numbers[-1]) >= 0))
#     if dec_num - fib_numbers[-1] >= 0:
#         dec_num -= fib_numbers[-1]
#     pass
#     fib_numbers.remove(fib_numbers[-1])
# print(fib_num)
