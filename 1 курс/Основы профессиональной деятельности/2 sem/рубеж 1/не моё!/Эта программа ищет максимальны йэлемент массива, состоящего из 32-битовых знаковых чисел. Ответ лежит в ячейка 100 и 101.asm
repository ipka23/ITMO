    ORG 0x100
maxHead: WORD 0x8000
maxTail: WORD 0x0000
max: WORD 0x8000
currentNumberHead: WORD 0x0000
currentNumberTail: WORD 0x0000
firstElementAddress: WORD 0x500
currentElementAddress: WORD 0x0000          ; Переменная, равная firstElementAddress (чтобы сделать программу реентерабельной)
arrayLength: WORD 0x0005                    ; Длина массива 32-разрядных чисел (то есть если занято в массиве 6 ячеек, то длина = 3)
elementsLeft: WORD 0x0000                   ; Переменная, равная elementsLeft (чтобы сделать программу реентерабельной)

    ORG 0x110
Start: CLA
    CALL Reset
    MainLoop: LD (currentElementAddress)+   ; Считать начало и конец 32-разрядного числа.
        ST currentNumberTail
        LD (currentElementAddress)+
        ST currentNumberHead

        CMP maxHead
        BEQ CompareTails                    ; Если currentNumberHead = maxHead, то надо сравнить хвостики
        BGE UpdateMaximum                   ; Если currentNumberHead > maxHead, то текущее число однозначно больше максимального
        JUMP Next

        CompareTails: LD currentNumberTail
            CMP maxTail
            BHIS UpdateMaximum              ; Если currentNumberTail выше, чем maxTail, то текущее число больше максимального
            JUMP Next

        UpdateMaximum: LD currentNumberHead
            ST maxHead
            LD currentNumberTail
            ST maxTail
        Next: LOOP elementsLeft
        JUMP MainLoop
HLT

    ORG 0x0F0
Reset: LD max                               ; Сброс всех элементов (чтобы сделать программу реентерабельной)
    ST maxHead
    CLA
    ST maxTail
    ST currentNumberHead
    ST currentNumberTail
    LD firstElementAddress
    ST currentElementAddress
    LD arrayLength
    ST elementsLeft
    RET

    ORG 0x500
WORD 0x1000 ; Конец 32-разрядного числа
WORD 0x2141 ; Начало 32-разрядного числа
WORD 0xFFFF
WORD 0x9241
WORD 0x5555
WORD 0x2121
WORD 0x5556
WORD 0x2121
WORD 0xFFFF
WORD 0x9000
