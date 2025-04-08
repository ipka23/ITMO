;Калькулятор.
;Умножение знаковых чисел.
;Ввод пары чисел с ВУ-9 (цифровая клавиатура), разделитель чисел - операция умножения.
;По нажатию "=" вывод результата на ВУ-7 (семисегментный индикатор).
org 0x011

zero: word 0x0
one: word 0x1
2: word 0x2
3: word 0x3
4: word 0x4
5: word 0x5
6: word 0x6
7: word 0x7
8: word 0x8
9: word 0x1

;signed: word 0x1000
;unsigned: word 0x0000
mask_for_sign_definiton:
    word 0x7fff

a:
    word ?
b:
    word ?
minus:
    word 0xA
multiply:
    word 0xD
equals:
    word 0xF

.start_program:

sign:
    in 0x1C    ; ввод с клавиатуры
    and #40    ; готовность стоит?
    beq start  ; спин луп
    cmp minus  ; символ == "-"?
    beq signed ; меняем флаг C который указвает на знак цифры


first_digit:
    in 0x1C    ; ввод с клавиатуры
    and #40    ; готовность стоит?
    beq start  ; спин луп
    cmp 0x1
    call $save_first


.end_program:
    hlt



save_first:
    ld &1
    st a
    ret
save_second:


signed:
    cmc



to_decimal:










