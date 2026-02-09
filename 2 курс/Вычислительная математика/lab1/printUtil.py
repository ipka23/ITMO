class PrintUtil:
    def printSoulutionX(self, k, d1, d2):
        print(f"x_{k} = ({round(d1, 3)}, {round(d2, 3)})")

    def printMatrix(self, matrix: list):
        print("=========start============")
        for i in range(len(matrix)):
            for j in range(len(matrix) + 1):
                print(round(matrix[i][j], 2), end=" ")
            print()
        print("=========finish===========")