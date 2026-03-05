import numpy as np


def f(x):
    return 2 * np.sin(x) - 1.5 * np.cos(2 * x) + np.sin(3 * x) - 0.5 * np.cos(4 * x)


eps = 10 ** -8
tau = (np.sqrt(5) - 1) / 2
a = -2
b = 2
n = 1
while abs(b - a) > eps:
    n += 1
    x1 = a + (b - a) * (1 - tau)
    x2 = a + (b - a) * tau
    f1 = f(x1)
    f2 = f(x2)
    if (f1 > f2):
        b = x1
    else:
        a = x2
x_max = (a + b) / 2
nt = np.log(b - a / eps) / np.log(1 / tau)
print(f"{x_max}, {f(x_max)}, {n}, {nt}")
