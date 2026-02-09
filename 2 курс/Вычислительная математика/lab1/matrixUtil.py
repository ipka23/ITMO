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
                print("Изначальная матрица:")
                self.printMatrix(matrix)
                print()
                # self.gauss_zeldel(matrix, n)
                self.gauss_zeldel(matrix, n)
                break

    def gauss_zeldel(self, a, n):
        max_iter = 1
        precision = 0.05
        for k in range(max_iter):
            for i in range(n):
                for j in range(n):
                    # print(f"a_ij:{a[i][j]}")
                    summ = 0
                    for j2 in range(n):
                        # print(f"a_ij2:{a[i][j2]}")
                        if j2 != j:
                            summ += a[i][j2]
                    if a[i][j] >= summ: # условие преобладания диагональных элементов
                        for j2 in range(n):
                            tmp = a[i][j2]
                            a[i][j2] = a[j][j2]
                            a[j][j2] = tmp
            matrix = a
            print("Преобразованная матрица")
            self.printMatrix(matrix)
            x = [0]*n
            for i in range(n):
                c = matrix[i][i]
                b = matrix[i][-1]
                x[i] = b
                # print("c: " + str(c))
                s = 0
                for j in range(n):
                    if i != j:
                        matrix[i][j] = -1 * matrix[i][j] / c
                    else:
                        matrix[i][j] = 1
                matrix[i][-1] /= c
                matrix[i][i] = 0
            for i in range(n):
                for j in range(n):
                    x[i] = x[i] * matrix[i][j]
            print("Итоговая матрица")
            self.printMatrix(matrix)


    def getMatrixFromFile(self, filename):
        pass

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


    def printMatrix(self, matrix: list):
        print("=========start============")
        for i in range(len(matrix)):
            for j in range(len(matrix) + 1):
                print(round(matrix[i][j]), end=" ")
            print()
        print("=========finish===========")

    # def zeldel(self, matrix, n, e):
    #     abs_max = e + 1
    #
    #     answer = []
    #     answer_new = []
    #
    #     for i in range(0, n):
    #         for j in range(0, n):
    #             sum = 0
    #             for jj in range(0, n):
    #                 if jj != j:
    #                     sum += matrix[i][jj]
    #             if matrix[i][j] >= sum:
    #                 for jj in range(0, n):
    #                     tmp = matrix[i][jj]
    #                     matrix[i][jj] = matrix[j][jj]
    #                     matrix[j][jj] = tmp
    #
    #     for i in range(0, n):
    #         c = matrix[i][i]
    #         for j in range(0, n):
    #             matrix[i][j] = -1 * matrix[i][j] / c
    #         matrix[i][-1] /= c
    #         matrix[i][i] = 0
    #
    #     c_norm = 0
    #     for i in range(0, n):
    #         sum = 0
    #         for j in range(0, n):
    #             sum += abs(matrix[i][j])
    #         if sum > c_norm:
    #             c_norm = sum
    #
    #     if c_norm < 1:
    #         for i in range(0, n):
    #             answer[i] = matrix[i][-1]
    #             answer_new[i] = matrix[i][-1]
    #
    #     # while abs_max > e:
    #     #     for i in range(0, n):
    #     #         sum = 0
    #     #         for j in range(0, n):
    #     #             sum += matrix[i][j] * answer_new[j]
    #     #         answer_new[i] = sum + matrix[i][-1]
    #     #
    #     #     abs_max = 0
    #     #     for i in range(0, n):
    #     #         if abs(answer[i] - answer_new[i]) > abs_max:
    #     #             abs_max = abs(answer[i] - answer_new[i])
    #     #         answer[i] = answer_new[i]
    #
    #     for i in range(0, n):
    #         print(answer[i])