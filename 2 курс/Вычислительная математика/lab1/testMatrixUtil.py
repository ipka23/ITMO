from printUtil import PrintUtil


class TestMatrixUtil:
    def __init__(self, printUtil: PrintUtil):
        self.printUtil = printUtil
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
            print("Введите матрицу\n")
            matrix = self.inputMatrix()
            if len(matrix) != n:
                print("Введите корректную матрицу")
                continue

            else:
                print("Изначальная матрица:")
                self.printUtil.printMatrix(matrix)
                print()
                # self.gauss_zeldel(matrix, n)
                # self.gauss_zeldel(matrix)
                self.testGZ(matrix)
                break

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


    def gauss_zeldel(self, matrix):
        for i in range(3):
            s = 0  # сумма всех коэф. в строке кроме текущего
            for j in range(3):
                if i != j:
                    s += abs(matrix[i][j])

    def testGZ(self, matrix: list):
        matrix = self.testGZ1(matrix)
        # matrix = self.testGZ2(matrix)
        return matrix

    def diagElemDominate(self, matrix):
        return abs(matrix[0][0]) >= abs(matrix[0][1]) and abs(matrix[1][1]) >= abs(matrix[1][0])

    def testGZ1(self, matrix):
        for i in range(2):
            if not self.diagElemDominate(matrix):  # Условие преобладания диагональных элементов
                matrix[0], matrix[1] = matrix[1], matrix[0]
                if i == 2: print("Решение может не сходиться т.к. в матрице не выполняется условие преобладания диагональных элементов")
        c1 = matrix[0][0]
        c2 = matrix[1][1]
        matrix[0][1] = -1 * matrix[0][1] / c1
        matrix[1][0] = -1 * matrix[1][0] / c2
        matrix[0][0] = 0
        matrix[1][1] = 0
        x = [] # решения системы x_0, ..., x_k; где k = номер итерации
        d1 = matrix[0][-1] / c1
        d2 = matrix[1][-1] / c2
        self.printUtil.printSoulutionX(0, d1, d2)
        x.append([d1, d2])
        self.printUtil.printMatrix(matrix)
        self.testGZ2(matrix, x)
        return matrix

    def testGZ2(self, matrix, x):
        x1 = matrix[0][1] * x[0][1] + x[0][0]
        x2 = matrix[1][0] * x[0][0] + x[0][1]
        x.append([x1, x2])
        self.printUtil.printSoulutionX(1, x1, x2)


if __name__ == "__main__":
    pu = PrintUtil()
    mu = TestMatrixUtil(pu)
    mu.getMatrixFromInput()

