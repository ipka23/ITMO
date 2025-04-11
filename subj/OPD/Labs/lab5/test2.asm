; тест минус
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
    ; цикл ввода
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
    jump FINISH

handle_minus_a:
    ld sign
    cmp #0x1
    beq set_zero
    ld #0x1
    jump save_sign
set_zero:
    ld #0x0
save_sign:
    st sign
    jump input_a


FINISH:
    ld sign
    cmp #0x1
    beq negative_a

    ld a
    out 0x14         ; выводим цифру на позицию 0
    hlt

negative_a:
    ld #0x001A
    out 0x14         ; выводим "-" на позицию 1
    ld a
    out 0x14         ; выводим цифру на позицию 0
    hlt