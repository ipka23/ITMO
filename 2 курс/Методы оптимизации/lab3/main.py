import math

eps = 10 ** -8
a = 0
b = math.pi / 4


def f(x):
    return math.tan(x) - 2 * math.sin(x)


x1 = a
x2 = (a + b) / 2
x3 = b

n = 0
x_min = 10
f_min = f(x_min)
d = abs(x_min - x2)
while d >= eps:
    n += 1
    f1 = f(x1)
    f2 = f(x2)
    f3 = f(x3)

    x_min = 0.5 * ((x2 ** 2 - x3 ** 2) * f1 + (x3 ** 2 - x1 ** 2) * f2 + (x1 ** 2 - x2 ** 2) * f3) / \
            ((x2 - x3) * f1 + (x3 - x1) * f2 + (x1 - x2) * f3)
    f_min = f(x_min)

    d = abs(x_min - x2)

    if x_min < x2:
        if f_min < f2:
            x3 = x2
            x2 = x_min
        else:
            x1 = x_min
    else:
        if f_min < f2:
            x1 = x2
            x2 = x_min
        else:
            x3 = x_min

print(f"x_min = {x_min}")
print(f"f(x_min) = {f_min}")
print(f"n = {n}")
