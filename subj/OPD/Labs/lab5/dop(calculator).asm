;Калькулятор.
;Умножение знаковых чисел.
;Ввод пары чисел с ВУ-9 (цифровая клавиатура), разделитель чисел - операция умножения.
;По нажатию "=" вывод результата на ВУ-7 (семисегментный индикатор).

; a - first symbol
; b - second symbol
; 0xA - "-"
; 0xD - "*"
; 0xF - "="

;signed: word 0x1000
;unsigned: word 0x0000
;mask_for_sign_definiton: word 0x7fff
;sign:                    word ?

; TODO реализовать умножение на 0 и добавить проверку на 0 в каждом умножении
jump START
minus: word 0xA ; "-"
a:  word ?
sign: word 0x0
START:
; сброс разрядов индикатора
    ld #0x002B
    out 0x14
    ld #0x001B
    out 0x14
    ld #0x000B
    out 0x14
    ;clc
sign_input_1:
    in 0x1C           ; ввод с цифровой клавиатуры
    cmp minus         ; символ == "-"?
    ;clc                ; т.к. при cmp если символ не равен "-" то при AC - m => С=1
    bne skip_in_a       ; если не "-", то знак не меняем
    ;cmc               ; меняем флаг C который указвает на знак цифры
    ld sign
    not
    st sign


a_input:
    in 0x1C           ; ввод с цифровой клавиатуры
skip_in_a:
    cmp #0x0          ; начало проверки на a_input в {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
    beq break_a_input
    cmp #0x1
    beq break_a_input
    cmp #0x2
    beq break_a_input
    cmp #0x3
    beq break_a_input
    cmp #0x4
    beq break_a_input
    cmp #0x5
    beq break_a_input
    cmp #0x6
    beq break_a_input
    cmp #0x7
    beq break_a_input
    cmp #0x8
    beq break_a_input
    cmp #0x9
    beq break_a_input ; конец проверки на a_input в {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
    jump a_input
break_a_input:
    st a ; сохраняем первый символ - a


jump multiply_input
multiplication:   word 0xD ; "*"
multiply_input:
    in 0x1C    ; ввод с клавиатуры
    cmp multiplication
    beq sign_input_2
    jump multiply_input



b: word ?
sign_input_2:
    in 0x1C           ; ввод с цифровой клавиатуры
    cmp minus         ; символ == "-"?
    bne skip_in_b     ; если не "-", то проверяем цифра ли это
    ;cmc               ; меняем флаг C который указвает на знак цифры
    ld sign
    not
    st sign

b_input:
    in 0x1C           ; ввод с цифровой клавиатуры
skip_in_b:
    cmp #0x0          ; начало проверки на b_input в {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
    beq break_b_input
    cmp #0x1
    beq break_b_input
    cmp #0x2
    beq break_b_input
    cmp #0x3
    beq break_b_input
    cmp #0x4
    beq break_b_input
    cmp #0x5
    beq break_b_input
    cmp #0x6
    beq break_b_input
    cmp #0x7
    beq break_b_input
    cmp #0x8
    beq break_b_input
    cmp #0x9
    beq break_b_input ; конец проверки на b_input в {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
    jump b_input
break_b_input:
    st b ; сохраняем второй символ - b



jump equate_input
equate: word 0xF ; "="
equate_input:
    in 0x1C
    cmp equate
    beq multiply
    jump equate_input

res: word ?
multiply:
    ld a
    push
    ld b
    push
    call $func
    pop
    pop
    st res ; результат умножения записываем в res






FINISH:
    ld sign
    rol
    ld res
    bcs negative_num ; я не знаю почему bcc а не bcs но оно работает, а нет не работает
    out 0x14
    hlt
negative_num:
    out 0x14   ; вывод res на позицию №0
    ld #0x001A ;
    out 0x14   ; вывод "-" на позицию №1 на 7-сегментном индикаторе
    hlt


org 0x200
func:
    ld &2 ; a -> AC
    cmp #0x1
    beq multiply_X1
    ld &1 ; b -> AC
    cmp #0x1
    beq multiply_X1


; a = &2 - первый множитель, b = &1 - второй множитель
multiply_X1:
    ld &1 ; в AC записываем b
    cmp #0x1
    beq return_a
    st &2 ; если b != 1, то записываем его в &2
    ret   ; и делаем ret (return b)
return_a:
    ret



multiply_X2:
multiply_X3:


to_decimal: