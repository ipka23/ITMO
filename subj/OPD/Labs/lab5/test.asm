; ввод символа с ву-9 и его сохранение в память
a: word ?
start:
  in 0x1C    ; ввод с клавиатуры
  cmp #0x1
  beq break
  jump start
break:
  st a
  hlt

; сравнение по прямой адресации
minus:  word 0xA ; "-"

sign_input:
  in 0x1C           ; ввод с клавиатуры
  cmp minus         ; символ == "-"?
  bne first_symbol ; если не "-", то знак не меняем
  cmc               ; меняем флаг C который указвает на знак цифры
break_sign:


;a_input in [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
a: word ?
a_input:
    in 0x1C           ; ввод с клавиатуры
    cmp #0x0          ; начало проверки на a_input in [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
    beq break_a
    cmp #0x1
    beq break_a
    cmp #0x2
    beq break_a
    cmp #0x3
    beq break_a
    cmp #0x4
    beq break_a
    cmp #0x5
    beq break_a
    cmp #0x6
    beq break_a
    cmp #0x7
    beq break_a
    cmp #0x8
    beq break_a
    cmp #0x9
    beq break_a
    jump a_input
break_a:
    st a
    hlt



; ввод "*"


multiply:   word 0xD ; "*"
multiply_input:
    in 0x1C    ; ввод с клавиатуры
    cmp multiply
    beq break
    jump multiply_input
break:
    hl

; вывод "-" на позицию №1 на 7-сегментном индикаторе
ld #0x001A
out 0x14
hlt
; сброс разряда
ld #0x007F
out 0x14
ld #0x001B
out 0x14
ld #0x000B
out 0x14
hlt










