org 0x010
x:   word 0x001f    ; x E[-33, 30]
min: word 0xffdf ; -33
max: word 0x001e ;  30

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
    dec             ; т.к. bge
    cmp $max
    bge reset_x
    inc
    ret
reset_x:
    ld $min
    st &1
    ret