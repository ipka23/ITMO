org 0x010
x:   word 0x0032 ; 50
min: word 0xfffc ; -52
max: word 0x0032 ;  50

START:
    ld x
    push
    call $check_value
    pop
    st x
    hlt

check_value:
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