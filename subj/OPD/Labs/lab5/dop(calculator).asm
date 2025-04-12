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

a:                word 0x0 ; первый множитель
b:                word 0x0 ; второй множитель
res:              word 0x0 ; резульат умножения

sign:             word 0x0 ; знак результата
minus:            word 0xA ; код символа "-"
equate:           word 0xF ; код символа "="
multiplication:   word 0xD ; код символа "*"

count_of_tens:  word 0x0   ; количество десятков в числе
START:
    cla
    ; сброс разрядов индикатора
    ;ld #0x002B
    ;out 0x14
    ;ld #0x001B
    ;out 0x14
    ;ld #0x000B
    ;out 0x14

    ; очистка значений
    ld sign
    cla
    st sign
    ld count_of_tens
    cla
    st count_of_tens



; цикл ввода a
input_a:
    in 0x1C
    in 0x1C
    cmp minus
    beq handle_minus_a
    cmp #0x1
    blt input_a   ; если in < 0 то не цифра
    cmp #0xB
    bge input_a   ; если in >= B то не цифра и не минус
    st a          ; сохраняем первый символ - a
    jump multiply_input

; изменение знака
handle_minus_a:
    ld sign
    cmp #0x1
    beq set_zero_a
    ld #0x1         ; установка "-"
    jump save_sign_a
set_zero_a:         ; установка "+"
    ld #0x0
save_sign_a:        ; сохранение знака переход к вводу a
    st sign
    jump input_a


; цикл ввода "*"
multiply_input:
    in 0x1C    ; ввод с клавиатуры
    cmp multiplication
    bne multiply_input
    beq input_b




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
    st b          ; сохраняем первый символ - a
    jump equate_input

; изменение знака
handle_minus_b:
    ld sign
    cmp #0x1
    beq set_zero_b
    ld #0x1           ; установка "-"
    jump save_sign_b

set_zero_b:           ; установка "+"
    ld #0x0
save_sign_b:          ; сохранение знака переход к вводу b
    st sign
    jump input_b



; цикл ввода "="
equate_input:
    in 0x1C
    in 0x1C
    cmp equate
    beq multiply
    bne equate_input


; умножение
multiply:
    ld a
    push
    ld b
    push
    call $func
    pop
    pop
    st res ; результат умножения записываем в res





; вывод результата
FINISH:
    ld res
hex_to_bcd:
    cmp #0x000A
    blt done
    sub #0x000A
    st res
    ld count_of_tens
    inc
    st count_of_tens
    jump FINISH

done:
    ld count_of_tens
    asl
    asl
    asl
    asl
    or res
    st res

    and #0x00f0               ; маска для сравнения 2 тетрады
    cmp #0x0010
    bge two_digits            ; если во второй тетраде число >= 1, значит двузначное число
    blt one_digit             ; если во второй тетраде число < 1, значит цифра


; вывод цифры
one_digit:
    ld #0x002B  ;
    out 0x14    ; сброс 2 разряда индикатора


    ld sign
    cmp #0x1
    beq negative_res_one_digit  ; если "-" то переходим к выводу цифры с минусом

    ld #0x001B  ;
    out 0x14    ; сброс 1 разряда индикатора
    ld res
    out 0x14                    ; иначе выводим цифру на позицию 0 и завершаем программу
    jump START

; вывод двузначного числа
two_digits:
    ld sign
    cmp #0x1
    beq negative_res_two_digits  ; если "-" то переходим к выводу числа с минусом


    ld #0x002B  ;
    out 0x14    ; сброс 2 разряда индикатора

    ld res
    asr
    asr
    asr
    asr
    or #0x0010
    out 0x14
    ld res
    and #0x000f
    out 0x14
    jump START



; вывод цифры с минусом
negative_res_one_digit:
    ld #0x001A
    out 0x14         ; выводим "-" на позицию 1
    ld res
    out 0x14         ; выводим цифру на позицию 0
    jump START

; вывод двузначного числа с минусом
negative_res_two_digits:
    ld #0x002A
    out 0x14         ; выводим "-" на позицию 2
    ld res
    asr
    asr
    asr
    asr
    or #0x0010
    out 0x14
    ld res
    and #0x000f
    out 0x14
    jump START



; функция умножения
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
    ld &2
    asl
    st &2
    ret