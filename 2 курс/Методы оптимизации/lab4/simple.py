def f(x1, x2):
    return 2 * x1 ** 2 + 3 * x1 * x2 + 5 * x2 ** 2 - 16 * x1 - 12 * x2 + 32


H11, H12, H21, H22 = 4, 3, 3, 10
x1, x2 = -3.0, 2.0
eps = 10 ** -4
n = 0

g = [4 * x1 + 3 * x2 - 16, 3 * x1 + 10 * x2 - 12]
norm_g = (g[0] ** 2 + g[1] ** 2) ** 0.5

while norm_g > eps:
    n += 1

    g_g = g[0] * g[0] + g[1] * g[1]

    Hg = [H11 * g[0] + H12 * g[1],
          H21 * g[0] + H22 * g[1]]

    g_Hg = g[0] * Hg[0] + g[1] * Hg[1]

    alpha = g_g / g_Hg

    x1, x2 = x1 - alpha * g[0], x2 - alpha * g[1]
    g = [4 * x1 + 3 * x2 - 16, 3 * x1 + 10 * x2 - 12]

    norm_g = (g[0] ** 2 + g[1] ** 2) ** 0.5
    print(f"Итерация {n}: x = [{x1, x2}], f(x) = {f(x1, x2)}")
