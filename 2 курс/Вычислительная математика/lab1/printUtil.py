class PrintUtil:
    def printSoulutionX(self, k, d1, d2):
        print(f"x_{k} = ({round(d1, 3)}, {round(d2, 3)})")

    def printX(self, k, x):
        s = f"Вектор неизвестных: x_{k} = ("
        for i in range(len(x)):
            if i == len(x) - 1:
                s += f"{round(x[i], 4)})"
                break
            s += f"{round(x[i], 4)}, "

        print(f"{s:60}", end=" | ")

    def printMatrix(self, matrix: list):
        print("=" * 387)
        for i in range(len(matrix)):
            for j in range(len(matrix) + 1):
                if not isinstance(matrix[i][j], float):
                    print(f"{matrix[i][j]:6}", end=" ")
                else:
                    print(f"{matrix[i][j]:6.4f}", end=" ")
            print()
        print("=" * 387)
