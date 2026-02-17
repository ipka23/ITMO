from matrixUtil import MatrixUtil
from printUtil import PrintUtil

printUtil = PrintUtil()
matrixUtil = MatrixUtil(printUtil)
if __name__ == "__main__":
    while True:
        print("="*93)
        print("Выберите откуда считать матрицу:\n1 - ввод в консоли\n2 - файл")
        userInput = input()
        if userInput.strip() == "1":
            matrixUtil.getMatrixFromInput()
        elif userInput == "2":
            print("Введите название файла: ")
            fileName = input()
            matrixUtil.getMatrixFromFile(fileName)
        else:
            print("Введите корректный вариант")
