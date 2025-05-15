;поиск максимального нечетного эл. массива
max: word 0x8000 ; -32768
pointer: word 0x012
arr:
    word 0x0003
    word 0x0999
    word 0x9992
    word 0xff22

len: word 0x4

START:
    ld (pointer)+
    asr
    bcc skip
    rol
    cmp max
    blt skip
    st max
skip:
    loop len
    jump START

finish:
     hlt