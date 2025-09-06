x: word 0x05DC ; 10_10
y: word 0x000A ; 1500_10
res: word 0 ; 1500_10*10_10 = 15000_10 = 3A98_16
org 0x015
start:
    ld x
    push
    ld y
    push
    call $multiply_func
    pop
    pop
    st res
finish:
    hlt

org 0x020
; по сути если a*n, то мы просто складываем a с собой n раз
tmp: word 0 ; накопление результата
multiply_func:
    ld &2   ; ld a
    add tmp
    st tmp
    loop &1 ; loop n
    jump multiply_func
    st &2   ; st 7ff
    ret