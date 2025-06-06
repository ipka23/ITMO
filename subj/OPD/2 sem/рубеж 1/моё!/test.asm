; применить функцию на каждый элемент массива и переместить в другой массив f(M_i) = 13 * M_i + 1210 = 0x000D * M_i + 0x04BA
old_arr_pointer: word 0x6c1
new_arr_pointer: word 0x400

len: word 0x4

start:
    ld (old_arr_pointer)+
    push
    call $main_func
    pop
    st (new_arr_pointer)+
    loop len
    jump start

finish:
     hlt

d: word 0xD
const: word 0x04BA
main_func:
    ld &1 ; ld M_i
    push
    ld d
    push
    call $multiply_func
    pop
    pop
    add const
    st &1 ; st 7ff
    ret


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


org 0x400
new_arr:
  word 4 dup (?)

org 0x6c1
arr:
    word 0x0
    word 0x1
    word 0x2
    word 0x3