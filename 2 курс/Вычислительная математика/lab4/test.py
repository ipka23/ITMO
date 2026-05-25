import os
import math
import matplotlib.pyplot as plt

def main():
    try:
        xs, ys = read_input_data()
    except Exception as e:
        print(e)
        return
# =====================================================================
# ВСПOMОГАТЕЛЬНЫЕ ФУНКЦИИ ДЛЯ МАТРИЦ (Без внешних библиотек)
# =====================================================================

def solve_linear_system(matrix, vector):
    """Решает СЛАУ методом Гаусса. Применяется для полиномов произвольной степени."""
    n = len(vector)
    # Расширенная матрица
    A = [matrix[i] + [vector[i]] for i in range(n)]

    for i in range(n):
        # Поиск максимального элемента в столбце для устойчивости
        max_row = max(range(i, n), key=lambda r: abs(A[r][i]))
        if abs(A[max_row][i]) < 1e-12:
            return None  # Система вырождена
        A[i], A[max_row] = A[max_row], A[i]

        # Обнуление элементов ниже главной диагонали
        for row in range(i + 1, n):
            factor = A[row][i] / A[i][i]
            for col in range(i, n + 1):
                A[row][col] -= factor * A[i][col]

    # Обратный ход
    x = [0] * n
    for i in range(n - 1, -1, -1):
        x[i] = A[i][n] / A[i][i]
        for row in range(i):
            A[row][n] -= A[row][i] * x[i]
    return x


# =====================================================================
# МАТЕМАТИЧЕСКОЕ ЯДРО МНК (РЕАЛИЗАЦИЯ ВСЕХ ФУНКЦИЙ)
# =====================================================================

def fit_linear(xs, ys):
    """Линейная функция: phi(x) = a * x + b"""
    n = len(xs)
    SX = sum(xs)
    SXX = sum(x ** 2 for x in xs)
    SY = sum(ys)
    SXY = sum(x * y for x, y in zip(xs, ys))

    denom = SXX * n - SX * SX
    if abs(denom) < 1e-12: return None

    a = (SXY * n - SX * SY) / denom
    b = (SXX * SY - SX * SXY) / denom
    return {'a': a, 'b': b}


def fit_polynomial_2(xs, ys):
    """Полиномиальная функция 2-й степени: phi(x) = a2 * x^2 + a1 * x + a0"""
    n = len(xs)
    SX = sum(xs)
    SXX = sum(x ** 2 for x in xs)
    SXXX = sum(x ** 3 for x in xs)
    SXXXX = sum(x ** 4 for x in xs)

    SY = sum(ys)
    SXY = sum(x * y for x, y in zip(xs, ys))
    SXXY = sum((x ** 2) * y for x, y in zip(xs, ys))

    matrix = [
        [SXXXX, SXXX, SXX],
        [SXXX, SXX, SX],
        [SXX, SX, n]
    ]
    vector = [SXXY, SXY, SY]
    res = solve_linear_system(matrix, vector)
    if res is None: return None
    return {'a2': res[0], 'a1': res[1], 'a0': res[2]}


def fit_polynomial_3(xs, ys):
    """Полиномиальная функция 3-й степени: phi(x) = a3 * x^3 + a2 * x^2 + a1 * x + a0"""
    n = len(xs)
    sums_x = [sum(x ** p for x in xs) for p in range(7)]  # от x^0 до x^6

    matrix = [
        [sums_x[6], sums_x[5], sums_x[4], sums_x[3]],
        [sums_x[5], sums_x[4], sums_x[3], sums_x[2]],
        [sums_x[4], sums_x[3], sums_x[2], sums_x[1]],
        [sums_x[3], sums_x[2], sums_x[1], sums_x[0]]
    ]
    vector = [
        sum((x ** 3) * y for x, y in zip(xs, ys)),
        sum((x ** 2) * y for x, y in zip(xs, ys)),
        sum(x * y for x, y in zip(xs, ys)),
        sum(ys)
    ]
    res = solve_linear_system(matrix, vector)
    if res is None: return None
    return {'a3': res[0], 'a2': res[1], 'a1': res[2], 'a0': res[3]}


def fit_exponential(xs, ys):
    """Экспоненциальная функция: phi(x) = a * exp(b * x) -> ln(phi) = ln(a) + b*x"""
    # Валидация: для логарифмирования y должен быть строго положительным
    valid_pts = [(x, y) for x, y in zip(xs, ys) if y > 0]
    if len(valid_pts) < 2: return None

    v_xs, v_ys = zip(*valid_pts)
    ln_ys = [math.log(y) for y in v_ys]

    # Решаем как линейную задачу для (x, ln(y))
    lin_res = fit_linear(list(v_xs), ln_ys)
    if lin_res is None: return None

    return {'a': math.exp(lin_res['b']), 'b': lin_res['a']}


def fit_logarithmic(xs, ys):
    """Логарифмическая функция: phi(x) = a * ln(x) + b"""
    # Валидация: x должен быть строго положительным
    valid_pts = [(x, y) for x, y in zip(xs, ys) if x > 0]
    if len(valid_pts) < 2: return None

    v_xs, v_ys = zip(*valid_pts)
    ln_xs = [math.log(x) for x in v_xs]

    # Решаем как линейную задачу для (ln(x), y)
    lin_res = fit_linear(ln_xs, list(v_ys))
    if lin_res is None: return None

    return {'a': lin_res['a'], 'b': lin_res['b']}


def fit_power(xs, ys):
    """Степенная функция: phi(x) = a * x^b -> ln(phi) = ln(a) + b * ln(x)"""
    # Валидация: и x, и y должны быть строго больше нуля
    valid_pts = [(x, y) for x, y in zip(xs, ys) if x > 0 and y > 0]
    if len(valid_pts) < 2: return None

    v_xs, v_ys = zip(*valid_pts)
    ln_xs = [math.log(x) for x in v_xs]
    ln_ys = [math.log(y) for y in v_ys]

    # Решаем как линейную задачу для (ln(x), ln(y))
    lin_res = fit_linear(ln_xs, ln_ys)
    if lin_res is None: return None

    return {'a': math.exp(lin_res['b']), 'b': lin_res['a']}


# =====================================================================
# ГОТОВАЯ ИНФРАСТРУКТУРА (INPUT/OUTPUT, СТАТИСТИКА, ГРАФИКА)
# =====================================================================

def eval_phi(func_type, coeffs, x):
    """Вычисляет значение аппроксимирующей функции в точке x."""
    try:
        if func_type == "Линейная":
            return coeffs['a'] * x + coeffs['b']
        elif func_type == "Полиномиальная 2-й степени":
            return coeffs['a2'] * (x ** 2) + coeffs['a1'] * x + coeffs['a0']
        elif func_type == "Полиномиальная 3-й степени":
            return coeffs['a3'] * (x ** 3) + coeffs['a2'] * (x ** 2) + coeffs['a1'] * x + coeffs['a0']
        elif func_type == "Экспоненциальная":
            return coeffs['a'] * math.exp(coeffs['b'] * x)
        elif func_type == "Логарифмическая":
            if x <= 0: return float('nan')
            return coeffs['a'] * math.log(x) + coeffs['b']
        elif func_type == "Степенная":
            if x <= 0: return float('nan')
            return coeffs['a'] * (x ** coeffs['b'])
    except (ValueError, TypeError, OverflowError):
        return float('nan')
    return float('nan')


def calculate_pearson_correlation(xs, ys):
    """Вычисляет коэффициент корреляции Пирсона для линейной зависимости."""
    n = len(xs)
    mean_x = sum(xs) / n
    mean_y = sum(ys) / n
    num = sum((xs[i] - mean_x) * (ys[i] - mean_y) for i in range(n))
    den_x = sum((xs[i] - mean_x) ** 2 for i in range(n))
    den_y = sum((ys[i] - mean_y) ** 2 for i in range(n))
    if den_x == 0 or den_y == 0:
        return 0.0
    return num / math.sqrt(den_x * den_y)


def calculate_metrics(xs, ys, func_type, coeffs):
    """Вычисляет СКО, невязки и R^2 (коэффициент детерминации)."""
    n = len(xs)
    y_phi = []
    epsilons = []
    sq_errors = []

    for i in range(n):
        val = eval_phi(func_type, coeffs, xs[i])
        y_phi.append(val)
        if math.isnan(val):
            epsilons.append(float('nan'))
            sq_errors.append(float('nan'))
        else:
            eps = val - ys[i]
            epsilons.append(eps)
            sq_errors.append(eps ** 2)

    valid_sq = [e for e in sq_errors if not math.isnan(e)]
    if not valid_sq:
        return float('inf'), y_phi, epsilons, 0.0

    sigma = math.sqrt(sum(valid_sq) / n)

    mean_y = sum(ys) / n
    ss_res = sum((ys[i] - y_phi[i]) ** 2 for i in range(n) if not math.isnan(y_phi[i]))
    ss_tot = sum((ys[i] - mean_y) ** 2 for i in range(n))
    r_squared = 1 - (ss_res / ss_tot) if ss_tot != 0 else 0.0

    return sigma, y_phi, epsilons, r_squared


def get_determination_message(r2):
    """Возвращает текстовую оценку качества по коэффициенту детерминации R^2."""
    if r2 >= 0.9:
        return "Модель обладает крайне высокой точностью аппроксимации."
    elif r2 >= 0.75:
        return "Модель обладает хорошей точностью аппроксимации."
    elif r2 >= 0.5:
        return "Модель обладает удовлетворительной точностью."
    else:
        return "Точность аппроксимации неудовлетворительна (модель плохо описывает данные)."


def read_input_data():
    """Обеспечивает гибкий ввод данных из файла или консоли с проверками."""
    print("Выберите способ ввода данных:")
    print("1. Консольный ввод")
    print("2. Чтение из файла")
    choice = input("Введите номер (1 или 2): ").strip()

    xs, ys = [], []

    if choice == '2':
        filename = input("Введите путь к файлу: ").strip()
        if not os.path.exists(filename):
            print(f"Ошибка: Файл {filename} не найден. Переключение на консольный ввод.")
            choice = '1'
        else:
            try:
                with open(filename, 'r', encoding='utf-8') as f:
                    for line in f:
                        if not line.strip(): continue
                        parts = line.replace(',', '.').split()
                        if len(parts) >= 2:
                            xs.append(float(parts[0]))
                            ys.append(float(parts[1]))
            except Exception as e:
                print(f"Ошибка при чтении файла: {e}. Переключение на консольный ввод.")
                choice = '1'
                xs, ys = [], []

    if choice == '1':
        print("Вводите пары точек 'X Y' (через пробел).")
        print("Для завершения ввода отправьте пустую строку.")
        idx = 1
        while True:
            line = input(f"Точка {idx}: ").strip()
            if not line: break
            try:
                parts = line.replace(',', '.').split()
                if len(parts) != 2:
                    print("Ошибка: введите ровно два числа через пробел!")
                    continue
                xs.append(float(parts[0]))
                ys.append(float(parts[1]))
                idx += 1
            except ValueError:
                print("Ошибка: некорректный формат чисел!")

    if not (8 <= len(xs) <= 12):
        print(f"\nПредупреждение: Текущий объем выборки ({len(xs)} точек) не входит в диапазон 8-12.")
        if len(xs) < 2:
            raise ValueError("Критическая ошибка: Слишком мало точек для выполнения МНК!")
    return xs, ys


if __name__ == "__main__":
    main()