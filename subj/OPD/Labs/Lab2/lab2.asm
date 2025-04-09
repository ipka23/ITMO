org 0x190
start:
    cla
    ld a
    or d
    add d
    st res
finish:
    hlt
res: word ?
a: word 0xE19A
b: word 0xE19A
d: word 0xE199
