def fib(n):
    if n < 2:
        return 1
    else:
        return fib(n-1) + fib(n-2)


dec_num = int(input())
fib_numbers = [fib(n) for n in range(1, 30) if fib(n) <= dec_num]
fib_num = ''
while fib_numbers != []:
    fib_num += str(int((dec_num - fib_numbers[-1]) >= 0))
    if dec_num - fib_numbers[-1] >= 0:
        dec_num -= fib_numbers[-1]
    pass
    fib_numbers.remove(fib_numbers[-1])
print(fib_num)