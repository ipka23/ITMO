from matrixUtil import MatrixUtil
from printUtil import PrintUtil

printUtil = PrintUtil()
matrixUtil = MatrixUtil(printUtil)
if __name__ == "__main__":
    while True:
        try:
            print("=" * 387)
            print("Выберите откуда считать матрицу:\n1 - ввод в консоли\n2 - файл")
            print("Введите \"exit\" для выхода\n")
            userInput = input().strip()
            if userInput == "1":
                matrixUtil.getMatrixFromInput()
            elif userInput == "2":
                print("Введите название файла: ")
                fileName = input()
                matrixUtil.getMatrixFromFile(fileName)
            elif userInput == "exit":
                break
            else:
                print("Введите корректный вариант")
        except FileNotFoundError:
            print("Введите корректное имя файла!")
