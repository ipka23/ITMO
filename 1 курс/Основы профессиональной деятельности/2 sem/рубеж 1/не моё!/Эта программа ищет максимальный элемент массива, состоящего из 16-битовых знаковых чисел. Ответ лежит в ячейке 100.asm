    ORG 0x100
max: WORD 0x0000
firstElementAddress: WORD 0x500
currentElementAddress: WORD 0x0000          ; Переменная, равная firstElementAddress (чтобы сделать программу реентерабельной)
arrayLength: WORD 0x0005
elementsLeft: WORD 0x0000                   ; Переменная, равная elementsLeft (чтобы сделать программу реентерабельной)

    ORG 0x110
Start: CLA
    ST max                                  ; Сброс всех элементов (чтобы сделать программу реентерабельной)
    LD firstElementAddress
    ST currentElementAddress
    LD arrayLength
    ST elementsLeft
    MainLoop: LD (currentElementAddress)+   ; Загружен элемент массива
        CMP max                             ; Сравниваем с максимумом
        BLT Next                            ; Если элеменет < максимума, то переход на следующую итерацию цикла
        ST max                              ; Если оказалось, что элемент >= максимуму, то записать его в максимум
        Next: LOOP elementsLeft
        JUMP MainLoop
HLT


    ORG 0x500
WORD 0x1000
WORD 0x2141
WORD 0xFFFF
WORD 0x9241
WORD 0x5555
