import math
import matplotlib.pyplot as plt


def det2(M):
    return M[0][0] * M[1][1] - M[0][1] * M[1][0]


# определитель матрицы 3x3 (правило треугольников)
def det3(M):
    return (M[0][0] * M[1][1] * M[2][2] +
            M[0][1] * M[1][2] * M[2][0] +
            M[0][2] * M[1][0] * M[2][1] -
            M[0][2] * M[1][1] * M[2][0] -
            M[0][1] * M[1][0] * M[2][2] -
            M[0][0] * M[1][2] * M[2][1])


# определитель матрицы 4x4 (разложение по первой строке)
def det4(M):
    det = 0
    for j in range(4):
        # минор (убираем 0-ю строку и j-й столбец)
        minor = []
        for i in range(1, 4):
            row = []
            for k in range(4):
                if k != j:
                    row.append(M[i][k])
            minor.append(row)
        sign = 1 if j % 2 == 0 else -1
        det += sign * M[0][j] * det3(minor)
    return det
# ============================================================
# решение слау (метод гаусса)
# ============================================================
def solveSle(A, b):
    """
    Решение СЛАУ методом Крамера
    Поддерживаются размерности 2, 3 и 4
    """
    n = len(A)

    # выбираем функцию определителя по размеру матрицы
    if n == 2:
        det = det2
    elif n == 3:
        det = det3
    elif n == 4:
        det = det4

    # главный определитель
    detMain = det(A)

    # решение методом Крамера
    x = []
    for col in range(n):
        # создаём копию матрицы A и заменяем col-й столбец на b
        Ai = [row[:] for row in A]
        for i in range(n):
            Ai[i][col] = b[i]

        # вычисляем определитель
        detAi = det(Ai)

        # находим неизвестную
        xi = detAi / detMain
        x.append(xi)

    return x


# ============================================================
# вспомогательные функции
# ============================================================
def sumList(arr):
    return sum(arr)


def sumOfProducts(a, b):
    return sum(ai * bi for ai, bi in zip(a, b))


def sumOfSquares(arr):
    return sum(x * x for x in arr)


def mean(arr):
    return sum(arr) / len(arr)


def pearsonCorrelation(x, y):
    n = len(x)
    sx, sy = sumList(x), sumList(y)
    sxy = sumOfProducts(x, y)
    sx2, sy2 = sumOfSquares(x), sumOfSquares(y)
    num = n * sxy - sx * sy
    den = math.sqrt((n * sx2 - sx * sx) * (n * sy2 - sy * sy))
    return num / den if den else 0


def determinationCoefficient(yTrue, yPred):
    ym = mean(yTrue)
    ssRes = sum((yt - yp) ** 2 for yt, yp in zip(yTrue, yPred))
    ssTot = sum((yt - ym) ** 2 for yt in yTrue)
    return 1 - ssRes / ssTot if ssTot else 0


# ============================================================
# функции аппроксимации
# ============================================================
def linearApprox(x, y):
    n = len(x)
    sx, sy = sumList(x), sumList(y)
    sxy = sumOfProducts(x, y)
    sx2 = sumOfSquares(x)
    A = [[n, sx], [sx, sx2]]
    b = [sy, sxy]
    a, bCoef = solveSle([row[:] for row in A], b[:])
    yPred = [a + bCoef * xi for xi in x]
    S = sum((yp - yi) ** 2 for yp, yi in zip(yPred, y))
    return a, bCoef, yPred, S


def poly2Approx(x, y):
    n = len(x)
    sx = sumList(x)
    sx2 = sumOfSquares(x)
    sx3 = sum(xi ** 3 for xi in x)
    sx4 = sum(xi ** 4 for xi in x)
    sy = sumList(y)
    sxy = sumOfProducts(x, y)
    sx2y = sum(xi * xi * yi for xi, yi in zip(x, y))
    A = [[n, sx, sx2], [sx, sx2, sx3], [sx2, sx3, sx4]]
    b = [sy, sxy, sx2y]
    a, bCoef, c = solveSle([row[:] for row in A], b[:])
    yPred = [a + bCoef * xi + c * xi * xi for xi in x]
    S = sum((yp - yi) ** 2 for yp, yi in zip(yPred, y))
    return a, bCoef, c, yPred, S


def poly3Approx(x, y):
    n = len(x)
    sx = sumList(x)
    sx2 = sumOfSquares(x)
    sx3 = sum(xi ** 3 for xi in x)
    sx4 = sum(xi ** 4 for xi in x)
    sx5 = sum(xi ** 5 for xi in x)
    sx6 = sum(xi ** 6 for xi in x)
    sy = sumList(y)
    sxy = sumOfProducts(x, y)
    sx2y = sum(xi * xi * yi for xi, yi in zip(x, y))
    sx3y = sum(xi ** 3 * yi for xi, yi in zip(x, y))
    A = [[n, sx, sx2, sx3],
         [sx, sx2, sx3, sx4],
         [sx2, sx3, sx4, sx5],
         [sx3, sx4, sx5, sx6]]
    b = [sy, sxy, sx2y, sx3y]
    a, bCoef, c, d = solveSle([row[:] for row in A], b[:])
    yPred = [a + bCoef * xi + c * xi * xi + d * xi ** 3 for xi in x]
    S = sum((yp - yi) ** 2 for yp, yi in zip(yPred, y))
    return a, bCoef, c, d, yPred, S


def exponentialApprox(x, y):
    lnY = [math.log(yi) for yi in y]
    lnA, b, _, _ = linearApprox(x, lnY)
    a = math.exp(lnA)
    yPred = [a * math.exp(b * xi) for xi in x]
    S = sum((yp - yi) ** 2 for yp, yi in zip(yPred, y))
    return a, b, yPred, S


def logarithmicApprox(x, y):
    lnX = [math.log(xi) for xi in x]
    a, b, _, _ = linearApprox(lnX, y)
    yPred = [a + b * math.log(xi) for xi in x]
    S = sum((yp - yi) ** 2 for yp, yi in zip(yPred, y))
    return a, b, yPred, S


def powerApprox(x, y):
    lnX = [math.log(xi) for xi in x]
    lnY = [math.log(yi) for yi in y]
    lnA, b, _, _ = linearApprox(lnX, lnY)
    a = math.exp(lnA)
    yPred = [a * (xi ** b) for xi in x]
    S = sum((yp - yi) ** 2 for yp, yi in zip(yPred, y))
    return a, b, yPred, S


# ============================================================
# ввод данных
# ============================================================
def loadData(filename):
    try:
        with open(filename) as f:
            points = [list(map(float, line.split())) for line in f if line.strip()]
        x, y = zip(*points)
        return list(x), list(y)
    except Exception as e:
        print(f"ошибка чтения: {e}")
        return None, None


def inputData():
    n = int(input("точек (8-12): "))
    n = max(8, min(12, n))
    x, y = [], []
    for i in range(n):
        x.append(float(input(f"x[{i}] = ")))
        y.append(float(input(f"y[{i}] = ")))
    return x, y


# ============================================================
# вывод в файл
# ============================================================
def saveToFile(filename, x, y, results, bestName, bestS, rPearson):
    try:
        with open(filename, 'w', encoding='utf-8') as f:
            f.write("=" * 60 + "\n")
            f.write("мнк - аппроксимация\n")
            f.write(f"точек: {len(x)}\n")
            f.write(f"x: {[round(xi, 3) for xi in x]}\n")
            f.write(f"y: {[round(yi, 3) for yi in y]}\n")
            f.write("\n" + "-" * 60 + "\n")

            for name, (coeffs, yPred, S, r2, formula) in results.items():
                rmse = math.sqrt(S / len(x))
                f.write(f"\n{name}:\n")
                f.write(f"  {formula}\n")
                f.write(f"  S = {S:.3f}, ско = {rmse:.3f}, R² = {r2:.3f}\n")
                f.write(f"  коэффициент детерминации: {r2:.3f}\n")

                # таблица xi, yi, φ(xi), εi
                f.write(f"  {'input1.txt':>2} {'x':>8} {'y':>8} {'φ(x)':>8} {'ε':>8}\n")
                for i in range(len(x)):
                    eps = yPred[i] - y[i]
                    f.write(f"  {i:2d}  {x[i]:8.3f}  {y[i]:8.3f}  {yPred[i]:8.3f}  {eps:8.3f}\n")

            if bestName:
                f.write(f"\nлучшая: {bestName} (S = {bestS:.3f})\n")

            if rPearson is not None:
                f.write(f"\nкоэф. корреляции пирсона: {rPearson:.3f}\n")

            f.write("-" * 60 + "\n")

        print(f"\nрезультаты сохранены в файл: {filename}")
    except Exception as e:
        print(f"ошибка сохранения: {e}")


# ============================================================
# основная программа
# ============================================================
def drawPlots(x, y, results):
    showPlot = input("\nпоказать графики? (y/n): ")
    if showPlot.lower() == 'y':
        plt.figure(figsize=(12, 8))
        plt.scatter(x, y, color='black', label='исходные данные', zorder=5, s=50)

        xMin, xMax = min(x), max(x)
        margin = (xMax - xMin) * 0.1
        xs = [xMin - margin + i * (xMax - xMin + 2 * margin) / 300 for i in range(301)]

        for name, (coeffs, _, _, _, _) in results.items():
            typ = coeffs[0]
            if typ == 'lin':
                _, a, b = coeffs
                ys = [a + b * xi for xi in xs]
            elif typ == 'p2':
                _, a, b, c = coeffs
                ys = [a + b * xi + c * xi * xi for xi in xs]
            elif typ == 'p3':
                _, a, b, c, d = coeffs
                ys = [a + b * xi + c * xi * xi + d * xi ** 3 for xi in xs]
            elif typ == 'exp':
                _, a, b = coeffs
                ys = [a * math.exp(b * xi) for xi in xs]
            elif typ == 'log':
                _, a, b = coeffs
                ys = [a + b * math.log(xi) if xi > 0 else None for xi in xs]
            elif typ == 'pow':
                _, a, b = coeffs
                ys = [a * (xi ** b) if xi > 0 else None for xi in xs]
            else:
                continue

            plt.plot(xs, ys, label=name, linewidth=2)

        plt.xlabel('x', fontsize=12)
        plt.ylabel('y', fontsize=12)
        plt.title('аппроксимация мнк', fontsize=14)
        plt.legend(loc='best')
        plt.grid(True, alpha=0.3)
        plt.axhline(y=0, color='k', linewidth=0.5)
        plt.axvline(x=0, color='k', linewidth=0.5)
        plt.show()


def main():
    print("мнк - аппроксимация")
    choice = input("1 - файл, 2 - консоль: ")

    if choice == '1':
        while True:
            x, y = loadData(input("имя файла: "))
            if not x:
                continue
            else:
                break
    else:
        x, y = inputData()

    print(f"\nточек: {len(x)}")
    print("x:", [round(xi, 3) for xi in x])
    print("y:", [round(yi, 3) for yi in y])

    results = {}

    # линейная
    aLin, bLin, yLin, S_lin = linearApprox(x, y)
    r2Lin = determinationCoefficient(y, yLin)
    results['линейная'] = (('lin', aLin, bLin), yLin, S_lin, r2Lin,
                           f"y = {aLin:.3f} + {bLin:.3f}*x")

    # квадратичная
    aP2, bP2, cP2, yP2, S_p2 = poly2Approx(x, y)
    r2P2 = determinationCoefficient(y, yP2)
    results['квадратичная'] = (('p2', aP2, bP2, cP2), yP2, S_p2, r2P2,
                               f"y = {aP2:.3f} + {bP2:.3f}*x + {cP2:.3f}*x²")

    # кубическая
    aP3, bP3, cP3, dP3, yP3, S_p3 = poly3Approx(x, y)
    r2P3 = determinationCoefficient(y, yP3)
    results['кубическая'] = (('p3', aP3, bP3, cP3, dP3), yP3, S_p3, r2P3,
                             f"y = {aP3:.3f} + {bP3:.3f}*x + {cP3:.3f}*x² + {dP3:.3f}*x³")

    # экспоненциальная
    if all(yi > 0 for yi in y):
        aExp, bExp, yExp, S_exp = exponentialApprox(x, y)
        r2Exp = determinationCoefficient(y, yExp)
        results['экспоненциальная'] = (('exp', aExp, bExp), yExp, S_exp, r2Exp,
                                       f"y = {aExp:.3f} * exp({bExp:.3f}*x)")

    # логарифмическая
    if all(xi > 0 for xi in x):
        aLog, bLog, yLog, S_log = logarithmicApprox(x, y)
        r2Log = determinationCoefficient(y, yLog)
        results['логарифмическая'] = (('log', aLog, bLog), yLog, S_log, r2Log,
                                      f"y = {aLog:.3f} + {bLog:.3f}*ln(x)")

    # степенная
    if all(xi > 0 for xi in x) and all(yi > 0 for yi in y):
        aPow, bPow, yPow, S_pow = powerApprox(x, y)
        r2Pow = determinationCoefficient(y, yPow)
        results['степенная'] = (('pow', aPow, bPow), yPow, S_pow, r2Pow,
                                f"y = {aPow:.3f} * x^{bPow:.3f}")

    # вывод результатов в консоль
    print("\n" + "-" * 60)
    bestName = None
    bestS = float('inf')
    rPearson = None

    for name, (coeffs, yPred, S, r2, formula) in results.items():
        delta = math.sqrt(S / len(x))
        print(f"\n{name}:")
        print(f"  {formula}")
        print(f"  S = {S:.3f}, ско = {delta:.3f}, R² = {r2:.3f}")
        print(f"  коэффициент детерминации: {r2:.3f}")

        if S < bestS:
            bestS = S
            bestName = name

    if bestName:
        print(f"\nлучшая: {bestName} (S = {bestS:.3f})")

    # корреляция пирсона для линейной модели
    if 'линейная' in results:
        rPearson = pearsonCorrelation(x, y)
        print(f"\nкоэф. корреляции пирсона: {rPearson:.3f}")
    print("-" * 60)

    # сохранение в файл (по желанию пользователя)
    saveChoice = input("\nсохранить результаты в файл? (y/n): ")
    if saveChoice.lower() == 'y':
        filename = input("имя файла для сохранения: ")
        saveToFile(filename, x, y, results, bestName, bestS, rPearson)

    # построение графиков
    drawPlots(x, y, results)


if __name__ == "__main__":
    main()