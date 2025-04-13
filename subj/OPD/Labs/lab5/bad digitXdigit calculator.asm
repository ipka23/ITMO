;Калькулятор.
;Умножение знаковых чисел.
;Ввод пары чисел с ВУ-9 (цифровая клавиатура), разделитель чисел - операция умножения.
;По нажатию "=" вывод результата на ВУ-7 (семисегментный индикатор).

a:                word 0x0 ; первый множитель
b:                word 0x0 ; второй множитель
res:              word 0x0 ; резульат умножения

t:                word 0x0 ; количество десятков в числе

sign:             word 0x0 ; знак результата
minus:            word 0xA ; код символа "-"
equate:           word 0xF ; код символа "="
multiplication:   word 0xD ; код символа "*"


START:
    cla
    ; очистка значений
    ld sign
    cla
    st sign
    ld t
    cla
    st t



; цикл ввода a
input_a:
    in 0x1C
    cmp minus
    beq handle_minus_a
    cmp #0x0
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
    bne input_a



; цикл ввода b
input_b:
    in 0x1C
    cmp minus
    beq handle_minus_b ;
    cmp #0x0
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
    cmp equate
    bne input_b


; умножение
multiply:
    ld a
    push   ; кладем а на стек => a = &2
    ld b
    push   ; кладем b на стек => b = &1
    call $func
    pop
    pop    ; res -> AC
    st res ; результат умножения записываем в res





; вывод результата
FINISH:
    ld res

; первод числа из 16 СС в 2-10 СС
hex_to_bcd:
    cmp #0x000A
    blt done          ; если res < 10 то завершаем подсчёт десятков
    sub #0x000A
    st res
    ld t
    inc
    st t  ; иначе вычитаем из него 10 и увеличиваем счетчик десятков на 1
    jump FINISH       ; переходим к следующей итерации

done:
    ld t              ; t E[0x0000, 0x0009]
    asl
    asl
    asl
    asl               ; сдвигаем количество десятков из 1 тетрады во 2
    or res            ; объединяем с количеством единиц
    st res

    and #0x00f0               ; маска для сравнения 2 тетрады
    cmp #0x0010
    bge two_digits            ; если во второй тетраде число >= 1, значит двузначное число
    blt one_digit             ; если во второй тетраде число < 1, значит цифра


; вывод цифры
one_digit:
    ld #0x002B
    out 0x14    ; сброс 2 разряда индикатора
    ld res
    cmp #0x0
    beq zero_out

    ld sign
    cmp #0x1
    beq negative_res_one_digit  ; если "-" то переходим к выводу цифры с минусом

    ld #0x001B
    out 0x14    ; сброс 1 разряда индикатора
    ld res
    out 0x14                    ; иначе выводим цифру на позицию 0 и завершаем программу
    jump START

; вывод двузначного числа
two_digits:
    ld sign
    cmp #0x1
    beq negative_res_two_digits  ; если "-" то переходим к выводу числа с минусом


    ld #0x002B
    out 0x14    ; сброс 2 разряда индикатора

    ld res
    asr
    asr
    asr
    asr             ; сдвигаем на 4 бита вправо => кол-во десятков t в первой тетраде => AC = 0x000t
    or #0x0010      ; объединяем t и 0x0010 для вывода на 1 позицию индикатора => AC = 0x001t
    out 0x14
    ld res
    and #0x000f     ; оставляем только 1 тетраду res (кол-во единиц) и выводим на 0 позицию индикатора
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

zero_out:
    ld #0x001B  ;
    out 0x14    ; сброс 1 разряда индикатора
    ld #0x0
    out 0x14
    hlt






; функция умножения
org 0x200
func:
    ld &2            ; a -> AC
    cmp #0x0
    beq multiply_X0
    cmp #0x1
    beq multiply_X1
    cmp #0x2
    beq multiply_X2
    cmp #0x3
    beq multiply_X3
    cmp #0x4
    beq multiply_X4
    cmp #0x5
    beq multiply_X5
    cmp #0x6
    beq multiply_X6
    cmp #0x7
    beq multiply_X7
    cmp #0x8
    beq multiply_X8
    cmp #0x9
    beq multiply_X9




; a = &2 - первый множитель, b = &1 - второй множитель
multiply_X0:
    ld #0x0
    st &2
    ret

multiply_X1:
    ld &1   ; в AC записываем b
    cmp #0x1
    beq return_aX1
    st &2   ; если b != 1, то записываем его в &2
    ret     ; и делаем ret (return b)
return_aX1:
    ret



multiply_X2:
    ld &1   ; в AC записываем b
    cmp #0x2
    beq return_aX2
    asl
    st &2   ; если b != 2, то умножаем его на 2, записываем в 7FF = &2
    ret     ; и делаем ret (return b)
return_aX2: ; иначе умножаем a на 2, записываем в 7FF = &2 и ret
    ld &2
    asl
    st &2
    ret


multiply_X3:
    ld &1 ; в AC записываем b
    cmp #0x3
    beq return_aX3
    asl
    add &1
    st &2 ; если b != 3, то умножаем его на 3, записываем в 7FF = &2
    ret   ; и делаем ret (return b)
return_aX3:
    ld &2
    asl
    add &2
    st &2
    ret


multiply_X4:
    ld &1 ; в AC записываем b
    cmp #0x4
    beq return_aX4
    asl
    asl
    st &2 ; если b != 4, то умножаем его на 4, записываем в 7FF = &2
    ret   ; и делаем ret (return b)
return_aX4:
    ld &2
    asl
    asl
    st &2
    ret


multiply_X5:
    ld &1 ; в AC записываем b
    cmp #0x5
    beq return_aX5
    asl
    asl
    add &1
    st &2 ; если b != 5, то умножаем его на 5, записываем в 7FF = &2
    ret   ; и делаем ret (return b)
return_aX5:
    ld &2
    asl
    asl
    add &2
    st &2
    ret


multiply_X6:
    ld &1 ; в AC записываем b
    cmp #0x6
    beq return_aX6
    asl
    add &1
    asl
    st &2 ; если b != 6, то умножаем его на 6, записываем в 7FF = &2
    ret   ; и делаем ret (return b)
return_aX6:
    ld &2
    asl
    add &2
    asl
    st &2
    ret


multiply_X7:
    ld &1 ; в AC записываем b
    cmp #0x7
    beq return_aX7
    asl
    add &1
    asl
    add &1
    st &2 ; если b != 7, то умножаем его на 7, записываем в 7FF = &2
    ret   ; и делаем ret (return b)
return_aX7:
    ld &2
    asl
    add &2
    asl
    add &2
    st &2
    ret


multiply_X8:
    ld &1 ; в AC записываем b
    cmp #0x8
    beq return_aX8
    asl
    asl
    asl
    st &2 ; если b != 8, то умножаем его на 8, записываем в 7FF = &2
    ret   ; и делаем ret (return b)
return_aX8:
    ld &2
    asl
    asl
    asl
    st &2
    ret

multiply_X9:
    ld &1 ; в AC записываем b
    cmp #0x8
    beq return_aX9
    asl
    asl
    asl
    add &1
    st &2 ; если b != 9, то умножаем его на 9, записываем в 7FF = &2
    ret   ; и делаем ret (return b)
return_aX9:
    ld &2
    asl
    asl
    asl
    add &2
    st &2
    ret