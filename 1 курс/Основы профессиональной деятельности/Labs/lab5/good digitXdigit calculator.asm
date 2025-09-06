;Калькулятор.
;Умножение знаковых чисел.
;Ввод пары чисел с ВУ-9 (цифровая клавиатура), разделитель чисел - операция умножения.
;По нажатию "=" вывод результата на ВУ-7 (семисегментный индикатор).
;
a:                word 0x0 ; первый множитель
b:                word 0x0 ; второй множитель
res:              word 0x0 ; резульат умножения

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
    push
    ld b
    push
    ld #0x0             ; локальная переменная для накопления результата умножения
    push
    call $multiply_func
    pop
    pop
    pop
    st res

; вывод результата
FINISH:
    ld res
    push
    ld #0x0         ; локальная переменная счетчика десятков
    push
    call $hex_to_bcd
    pop
    pop
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

; вывод цифры с минусом
negative_res_one_digit:
    ld #0x001A
    out 0x14         ; выводим "-" на позицию 1
    ld res
    out 0x14         ; выводим цифру на позицию 0
    jump START

zero_out:
    ld #0x001B  ;
    out 0x14    ; сброс 1 разряда индикатора
    ld res
    out 0x14
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



org 0x200
multiply_func:
    ld &3               ; ld a
    add &1              ; add temp
    st &1               ; st temp
    loop &2             ; b - 1
    jump multiply_func
    ld &1
    st &3               ; результат умножения сохранить в 7FF
    ret



org 0x250
; первод числа из 16 СС в 2-10 СС
hex_to_bcd:
    ld &2             ; ldres
    cmp #0x000A
    blt done          ; если res < 10 то завершаем подсчёт десятков
    sub #0x000A
    st &2
    ld &1
    inc
    st &1  ; иначе вычитаем из него 10 и увеличиваем счетчик десятков на 1
    jump hex_to_bcd       ; переходим к следующей итерации

done:
    ld &1              ; количество десятков числа E[0x0000, 0x0009]
    asl
    asl
    asl
    asl               ; сдвигаем количество десятков из 1 тетрады во 2
    or &2             ; объединяем с количеством единиц
    st &2
    ret