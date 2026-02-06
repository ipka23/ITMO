from configparser import ParsingError
from os import error


class MatrixUtil:

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

            # for line in matrix:
            #     if len(line) != n + 1:
            #         print("Введите корректную матрицу")
            # continue

            else:
                self.printMatrix(matrix)
                print()
                self.gauss_zeldel(matrix, n)
                break

    def gauss_zeldel(self, a, n):
        max_iter = 15
        for k in range(max_iter):
            for i in range(n):
                for j in range(n):
                    summ = 0
                    for j2 in range(n):
                        if j2 != j:
                            summ += a[i][j2]
                    if a[i][j] >= summ: # условие преобладания диагональных элементов
                        for j2 in range(n):
                            tmp = a[i][j2]
                            a[i][j2] = a[j][j2]
                            a[j][j2] = tmp
        self.printMatrix(a)


    def getMatrixFromFile(self, filename):
        pass

    def inputMatrix(self):
        matrix = list()
        while True:
            line_input = input()
            if line_input == "":
                break
            else:
                line = list(map(int, line_input.strip().split(" ")))
                matrix.append(line)
        return matrix


    def printMatrix(self, matrix: list):
        for i in range(len(matrix)):
            for j in range(len(matrix)):
                print(matrix[i][j], end=" ")
            print()