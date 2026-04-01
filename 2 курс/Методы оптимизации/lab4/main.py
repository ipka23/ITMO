def f(x):
    x1, x2 = x
    return 2 * x1 ** 2 + 3 * x1 * x2 + 5 * x2 ** 2 - 16 * x1 - 12 * x2 + 32


def grad_f(x):
    x1, x2 = x
    return [4 * x1 + 3 * x2 - 16,
            3 * x1 + 10 * x2 - 12]


def scalarMultiply(a, b):
    return a[0] * b[0] + a[1] * b[1]


def matrixVectorMultiply(A, v):
    return [A[0][0] * v[0] + A[0][1] * v[1],
            A[1][0] * v[0] + A[1][1] * v[1]]


H = [[4, 3],
     [3, 10]]

x = [-3.0, 2.0]
eps = 10 ** -4
n = 0

print(f"Итерация {n}: x = {x}, f(x) = {f(x)}")


def vectorNorm(v):
    return (v[0] ** 2 + v[1] ** 2) ** 0.5


while vectorNorm(grad_f(x)) > eps:
    g = grad_f(x)
    g_g = scalarMultiply(g, g)
    Hg = matrixVectorMultiply(H, g)
    g_Hg = scalarMultiply(g, Hg)
    alpha = g_g / g_Hg

    x = [x[0] - alpha * g[0], x[1] - alpha * g[1]]
    n += 1
    print(f"Итерация {n}: x = {x}, f(x) = {f(x)}, grad = {vectorNorm(g):.5f}")
