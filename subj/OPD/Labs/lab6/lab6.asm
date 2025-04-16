ORG 0x0
v0: word $default, 0x180
v1: word $int1,    0x180
v2: word $int2,    0x180
v3: word $default, 0x180
v4: word $default, 0x180
v5: word $default, 0x180
v6: word $default, 0x180
v7: word $default, 0x180

org 0x00f
default: iret

org 0x01b
x:   word 0x0    ; x E[-52, 50]
min: word 0xfffc ; -52
max: word 0x0032 ;  50

START:
    cla
    ld #9 ;
    out 7 ; устанавливаем вектор int1 в MR(#7) ВУ-3 <=> 1001 -> MR(#7)
    ld #0xA ;
    out 5 ; устанавливаем вектор int2 в MR(#5) ВУ-2 <=> 1010 -> MR(#5)

main:
    di                  ; по умолчанию прерывания и так запрещены, это нужно для реентерабельности
    ld x
    add #2
    push
    call $check_value
    pop
    st x
    ei
    jump main



int1:           ; вычисление f(x) = -5x-5
    ld x
    hlt         ; для отладки
    asl
    asl
    add x
    neg
    sub #5
    out 6
    hlt         ; для отладки
    iret

int2:           ; изменение знака x по нажатию кнопки готовности SR(#5) ВУ-2
    ld x
    hlt         ; для отладки
    neg
    st x
    hlt         ; для отладки
    iret




check_value: ; функция проверки ОДЗ
    ld &1
    cmp $min
    blt reset_x
    dec             ; x + 1 т.к. bge
    cmp $max
    bge reset_x
    inc
    ret
reset_x:
    ld $min
    st &1
    ret