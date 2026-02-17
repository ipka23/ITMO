from printUtil import PrintUtil


class MatrixUtil:
    def __init__(self, printUtil: PrintUtil):
        self.printUtil = printUtil

    def getMatrixFromFile(self, filename):
        matrix = list()
        n = 0
        with open(filename) as f:
            while True:
                for line in f:
                    if line.strip() == "":
                        break
                    else:
                        lineNums = list(map(int, line.strip().split()))
                        if len(lineNums) == 1:
                            n = lineNums[0]
                        else:
                            matrix.append(lineNums)
                break
        print("Изначальная матрица:")
        self.printUtil.printMatrix(matrix)
        print()
        self.zeroApproximation(matrix, n)

    def getMatrixFromInput(self):
        n = 0
        while True:
            try:
                n = int(input("Введите размерность матрицы(от 1 до 20): "))
                if n < 1 or n > 20:
                    print("Размерность матрицы должна быть от 1 до 20!")
                    continue
                else:
                    break
            except ValueError:
                print("Размерность должна быть числом!")
                continue

        while True:
            try:
                print("Для подтверждения матрицы нажмите enter 2 раза")
                print("Введите матрицу\n")
                matrix = self.inputMatrix()
                if len(matrix) != n:
                    print("Введите корректную матрицу")
                    continue

                else:
                    print("Изначальная матрица:")
                    self.printUtil.printMatrix(matrix)
                    print()
                    self.zeroApproximation(matrix, n)
                    break
            except ValueError:
                print("Введите корректную матрицу")

    def inputMatrix(self):
        matrix = list()
        while True:
            line_input = input()
            if line_input == "":
                break
            else:
                line = list(map(int, line_input.strip().split()))
                matrix.append(line)
        return matrix

    def diagElemDominate(self, matrix, lineIndex):
        s = 0  # сумма всех коэф. кроме главного
        matrixLine = matrix[lineIndex]
        for i in range(len(matrixLine) - 1):
            if i != lineIndex:
                s += abs(matrixLine[i])
        if abs(matrixLine[lineIndex]) < s:
            return False
        return True

    def zeroApproximation(self, matrix, n):
        for i in range(n):
            for j in range(i + 1, n):
                if not self.diagElemDominate(matrix, i):  # условие преобладания диагональных элементов
                    matrix[i], matrix[j] = matrix[j], matrix[i]
                    if self.diagElemDominate(matrix, i):
                        break
        if all(self.diagElemDominate(matrix, i) for i in range(n)):
            print("Преобладание диагональных элементов достигнуто")
        else:
            print(
                "Решение может не сходиться т.к. в матрице не выполняется условие преобладания диагональных элементов")

        self.printUtil.printMatrix(matrix)
        c = []  # список для коэф. при главных элементах
        divideZeroFlag = False
        for i in range(n):
            c.append(matrix[i][i])
        for i in range(n):
            for j in range(n):
                if i != j:
                    if c[i] != 0:
                        matrix[i][j] = -1 * matrix[i][j] / c[i]
                    else:
                        divideZeroFlag  = True
                        matrix[i][-1] = matrix[i][-1]
                else:
                    matrix[i][j] = 0
        d = [0] * n  # список свободных членов равных решению x_0 (нулевому приближению)
        for i in range(n):
            if c[i] != 0:
                matrix[i][-1] = matrix[i][-1] / c[i]
            else:
                divideZeroFlag  = True
                matrix[i][-1] = matrix[i][-1]
            d[i] = matrix[i][-1]

        X = []  # решения системы x_0, ..., x_k; где k = номер итерации
        X.append(d)

        self.printUtil.printX(0, X[0])
        # self.printUtil.printMatrix(matrix)

        k = 1
        i = 0
        if not divideZeroFlag :
            while not self.absoluteDeviation(X):
                i += 1
                x = self.kApproximationSimpleIteration(matrix, n, X)
                X.append(x)
                self.printUtil.printX(k, X[-1])
                k += 1
                if i > 10: break
        else:
            self.printUtil.printX(0, d)
        print(f"Итого: {i + 1} итераций")

    def absoluteDeviation(self, X):
        epsilon = 0.01  # абсолютное отклонение
        maxDeviation = -1
        l = len(X)
        solutionLen = len(X[0])
        if l == 1:
            print(f"Вектор погрешности: {max(X[0]):10.4f}")
            return False
        for i in range(solutionLen):
            e = abs(X[l - 1][i] - X[l - 2][i])
            if e >= maxDeviation:
                maxDeviation = e
        print(f"Вектор погрешности: {maxDeviation:10.4f}")
        return maxDeviation <= epsilon

    def kApproximationSimpleIteration(self, matrix, n, X):
        x = []  # k-тое приближение решения
        for i in range(n):  # x_0 = matrix[0][1] * X[-1][1] + matrix[0][2] * X[-1][2] + X[-1][0]
            x_k = 0
            for j in range(n):
                x_k += matrix[i][j] * X[-1][j]
            x_k += + X[0][i]
            x.append(x_k)
        return x

    def kApproximationGaussZeldel(self, matrix, n, X):
        x = []  # k-тое приближение решения
        # self.printUtil.printMatrix(matrix)
        for i in range(n):  # x_0 = matrix[0][1] * X[-1][1] + matrix[0][2] * X[-1][2] + X[-1][0]
            x_k = 0
            for j in range(n):
                if j < len(x):
                    x_k += matrix[i][j] * x[j]
                else:
                    x_k += matrix[i][j] * X[-1][j]
            x_k += + X[0][i]  # прибавляем свободный член
            x.append(x_k)
        return x
