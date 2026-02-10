# from printUtil import PrintUtil
#
#
# def f(matrix, n):
#     for i in range(n):
#         for k in range(n - 1):
#             if not diagElemDominate(matrix, i):  # Условие преобладания диагональных элементов
#                 matrix[k], matrix[k + 1] = matrix[k + 1], matrix[k]
#             if i == n: print("Решение может не сходиться т.к. в матрице не выполняется условие преобладания диагональных элементов")
#
#
# def diagElemDominate(matrix, lineIndex):
#     s = 0  # сумма всех коэф. кроме главного
#     matrixLine = matrix[lineIndex]
#     for i in range(len(matrixLine) - 1):
#         if i != lineIndex:
#             s += abs(matrixLine[i])
#     if abs(matrixLine[lineIndex]) < s:
#         return False
#     return True
#
# m = [
#     [1, 5, 1, 7],
#     [4, 1, 1, 6],
#     [1, 1, 6, 8]
# ]
# printUtil = PrintUtil()
# printUtil.printMatrix(m)
# f(m, 3)
# printUtil.printMatrix(m)
s = [1, 2, 3, 4, 5]
l = 4
print(len(s))
