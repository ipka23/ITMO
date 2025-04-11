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
sign:   word 0x0     ; 0 = положительное, 1 = отрицательное
minus:  word 0xA     ; код символа "-"
a:      word 0x0
START:
    cla
    ; сброс разрядов индикатора
    ld #0x002B
    out 0x14
    ld #0x001B
    out 0x14
    ld #0x000B
    out 0x14
; цикл ввода a
input_a:
    in 0x1C
    in 0x1C
    cmp minus
    beq handle_minus_a ;
    cmp #0x1
    blt input_a   ; если in < 0 то не цифра
    cmp #0xB
    bge input_a   ; если in >= B то не цифра и не минус


    st a ; сохраняем первый символ - a
    jump multiply_input

handle_minus_a:
    ld sign
    cmp #0x1
    beq set_zero_a
    ld #0x1
    jump save_sign_a
set_zero_a:
    ld #0x0
save_sign_a:
    st sign
    jump input_a


multiplication:   word 0xD ; "*"
multiply_input:
    in 0x1C    ; ввод с клавиатуры
    cmp multiplication
    beq input_b
    jump multiply_input



b:      word 0x0
; цикл ввода b
input_b:
    in 0x1C
    in 0x1C
    cmp minus
    beq handle_minus_b ;
    cmp #0x1
    blt input_b   ; если in < 0 то не цифра
    cmp #0xB
    bge input_b   ; если in >= B то не цифра и не минус


    st b ; сохраняем первый символ - a
    jump equate_input

handle_minus_b:
    ld sign
    cmp #0x1
    beq set_zero_b
    ld #0x1
    jump save_sign_b
set_zero_b:
    ld #0x0
save_sign_b:
    st sign
    jump input_b



jump equate_input
equate: word 0xF ; "="
equate_input:
    in 0x1C
    in 0x1C
    cmp equate
    beq multiply
    jump equate_input

res:    word 0x0
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
    cmp #0x1
    beq negative_res

    ld res
    out 0x14         ; выводим цифру на позицию 0
    hlt

negative_res:
    ld #0x001A
    out 0x14         ; выводим "-" на позицию 1
    ld res
    out 0x14         ; выводим цифру на позицию 0
    hlt




org 0x200
func:
    ld &2 ; a -> AC
    cmp #0x1
    beq multiply_X1
    cmp #0x2
    beq multiply_X2
    ld &1 ; b -> AC
    cmp #0x1
    beq multiply_X1
    cmp #0x2
    beq multiply_X2


; a = &2 - первый множитель, b = &1 - второй множитель
multiply_X1:
    ld &1 ; в AC записываем b
    cmp #0x1
    beq return_aX1
    st &2 ; если b != 1, то записываем его в &2
    ret   ; и делаем ret (return b)
return_aX1:
    ret



multiply_X2:
    ld &1 ; в AC записываем b
    cmp #0x2
    beq return_aX2
    asl   ;
    st &2 ; если b != 2, то умножаем его на 2 и записываем в 7FF = &2
    ret   ; и делаем ret (return b)
return_aX2:
    asl
    ret


multiply_X3:
    ld &1 ; в AC записываем b
    cmp #0x3
    beq return_aX3
    asl   ;
    add &1
    st &2 ; если b != 3, то умножаем его на 3 и записываем в 7FF = &2
    ret   ; и делаем ret (return b)
return_aX3:
    asl
    add &2
    ret



to_decimal: