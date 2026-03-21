eps = 10 ** -2


def f(x):
    return -1.8 * x ** 3 - 2.94 * x ** 2 + 10.37 * x + 5.38


def halfDivision(a, b):
    k = 1
    print("| № шага |   a   |   b   |   x   | f(a)  | f(b)  | f(x)  | |a - b| |")
    print("|--------|-------|-------|-------|-------|-------|-------|---------|")
    while True:
        x = (a + b) / 2

        print(f"|   {k}    | {a:.3f} | {b:.3f} | {x:.3f} | {f(a):.3f} | {f(b):.3f} | {f(x):.3f} |  {abs(a - b):.3f}  |")
        if abs(a - b) <= eps:
            break
        if f(a) * f(x) > 0:
            # print(f"f({a:.3f}) * f({x:.3f}) > 0")
            a = x
        else:
            # print(f"f({a:.3f}) * f({x:.3f}) < 0")
            b = x
        k += 1


def secants(x0, x1, n_iter=10):
    x_prev = x0
    x_curr = x1
    k = 0
    while True:

        x_next = x_curr - f(x_curr) * (x_curr - x_prev) / (f(x_curr) - f(x_prev))

        print(f"n = {k + 1} | x_{k + 1} = {x_next} | |x_next - x_curr| = {abs(x_next - x_curr)}")
        if abs(f(x_curr)) <= 10 ** -2:
            break
        x_prev, x_curr = x_curr, x_next

        k += 1


halfDivision(1, 3)
# def secants(x_k, x_k1):
#     return x_k1 - f(x_k1) * (x_k1 - x_k) / (f(x_k1) - f(x_k))
# print(secants(-0.467, -0.473))
