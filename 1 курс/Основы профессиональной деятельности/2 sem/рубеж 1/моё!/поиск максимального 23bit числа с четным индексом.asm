; Задание №1.
; Разработать программу для работы с элементами массива М, в которой:
; - каждый элемент является знаковым числом с разрядностью 23 бит;
; - элементы хранятся в массиве по границам слов, нет необходимости в плотной упаковке;
; 2. Для элементов массива необходимо вычислить одно значение по правилам:
; - агрегировать необходимо только для элементы массива с четными i-индексами;
; - из выбранных элементов необходимо вычислить максимальное значени записать результат в память поадресу 0x400.
; - Результатом является одно 32-х разрядное число!
; Примечание: все числа представлены в десятичной системе счисления, если явно не указано иное.


pointer: word 0x100
result_addr: word 0x200
arr_len: word 3
mask: word 0x007f            ; маска для обнуления битов с 23 по 31   0000 0000 0111 1111
sign_bit: word 0x0040        ; маска для проверки знака (22 бит)
extend_sign: word 0xff80     ; расширение знака

tmp_cur_low: word 0x0
tmp_cur_high: word 0x0

tmp_res_low: word 0x0
tmp_res_high: word 0x0
start:
    ld (pointer)+     ; загружаем младшее слово
    st tmp_cur_low
    ld (pointer)      ; загружаем старшее слово
    st tmp_cur_high
    ld pointer
    dec
    st pointer

    ld (result_addr)+
    st tmp_res_low          ; сохраняем младшее слово res
    ld (result_addr)
    st tmp_res_high         ; сохраняем старшее слово res
    ld result_addr
    dec
    st result_addr

check_sign:
    ld tmp_cur_high
    and sign_bit
    bzc negative        ; [0x0040, 0x0080] - отриц.


main:
    ld tmp_cur_high
    and mask
    cmp tmp_res_high
    blt skip            ; скипаем
                        ; иначе сохраняем всё в result_addr
    ld tmp_cur_low
    st (result_addr)+
    ld tmp_cur_high
    st (result_addr)

    ld result_addr
    dec
    st result_addr
    loop arr_len
    jump check_sign
    jump finish

negative:
    ld tmp_cur_high
    or extend_sign
    cmp tmp_res_high
    blt skip            ; скипаем
                        ; иначе сохраняем всё в result_addr

    ld tmp_cur_low
    st (result_addr)+
    ld tmp_cur_high
    st (result_addr)

    ld result_addr
    dec
    st result_addr
    loop arr_len
    jump check_sign
skip:
    loop arr_len
    jump check_sign


finish:
    hlt

org 0x200
result: word 0xffc0, 0x0000 ; 0x50200041

org 0x100
arr:                ; little-endian
word 0x0001, 0x0000 ; 0040 1100 ; i = 1
word 0x0002, 0x0002 ; 0070 5010 ; i = 2
word 0x0003, 0x0003 ; 0011 0018 ; i = 3
