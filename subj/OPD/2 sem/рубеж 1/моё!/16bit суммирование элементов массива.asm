res: word 0
pointer: word 0x100
len: word 4

start:
    ld res
    add (pointer)+
    st res
    loop len
    jump start
finish:
    hlt
org 0x100
arr:
    word 1
    word 2
    word 3
    word 4