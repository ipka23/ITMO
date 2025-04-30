org 0x0
v0: word $default, 0x180
v1: word $int1,    0x180
v2: word $int2,    0x180
v3: word $default, 0x180
v4: word $default, 0x180
v5: word $default, 0x180
v6: word $default, 0x180
v7: word $default, 0x180

org 0x010
default: iret



org 0x04b
x:   word 0x0    ; x E[-33, 30]
min: word 0xffdf ; -33
max: word 0x001e ;  30

START:
    cla
    ld #9
    out 3    ; устанавливаем вектор int1 в MR(#3) КВУ-1 <=> 1001 -> MR(#3)
    ld #0xA
    out 7    ; устанавливаем вектор int2 в MR(#7) КВУ-3 <=> 1010 -> MR(#7)


main:
    di                 ; по умолчанию прерывания и так запрещены, это нужно для реентерабельности
    ld x
    inc
    push
    call $check_value
    pop
    st x
    hlt
    ei
    jump main


int1:           ; вычисление f(x) = -4x-8 и вывод результата f(x) на DR(#2) ВУ-1 по нажатию кнопки готовности SR(#3) КВУ-1
    ld x
    hlt         ; для отладки
    asl
    asl
    neg
    sub #0x8
    out 2
    hlt         ; для отладки
    iret

int2:           ; запись содержимого DR(#6) КВУ-3 в x по нажатию кнопки готовности SR(#7) КВУ-3
    in 6
    push
    call $check_value
    pop
    st x
    hlt         ; для отладки
    iret




check_value:    ; функция проверки ОДЗ
    ld &1
    cmp $min
    blt reset_x
    dec         ; x + 1 т.к. bge
    cmp $max
    bge reset_x
    inc
    ret
reset_x:        ; запись в x минимального значения x по ОДЗ
    ld $min
    st &1
    ret