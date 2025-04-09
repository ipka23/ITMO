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




minus: word 0xA ; "-"
_START:
sign_input_1:
    in 0x1C           ; ввод с цифровой клавиатуры
    cmp minus         ; символ == "-"?
    bne a_input       ; если не "-", то знак не меняем
    cmc               ; меняем флаг C который указвает на знак цифры

    jump a_input
a:  word ?
a_input:
    in 0x1C           ; ввод с цифровой клавиатуры
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
multiply:   word 0xD ; "*"
multiply_input:
    in 0x1C    ; ввод с клавиатуры
    cmp multiply
    beq sign_input_2
    jump multiply_input




sign_input_2:
    in 0x1C           ; ввод с цифровой клавиатуры
    cmp minus         ; символ == "-"?
    bne b_input       ; если не "-", то знак не меняем
    cmc               ; меняем флаг C который указвает на знак цифры

jump b_input
b: word ?
b_input:
    in 0x1C           ; ввод с цифровой клавиатуры
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
    in 0x1C    ; ввод с клавиатуры
    cmp equate
    beq sign_input_2
    jump multiply_input










_FINISH:
    hlt


save_second:


signed:
    cmc
    ret


to_decimal:







multiply_X1:
multiply_X2:
multiply_X3:
