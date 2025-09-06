; 16bit применить функцию на каждый элемент массива и переместить в другой массив f(M_i) = 13 * M_i + 1210 = 0x000D * M_i + 0x04BA
old_arr_pointer: word 0x6c1
new_arr_pointer: word 0x400
d: word 0xD
const: word 0x04BA
len: word 0x4
smask: word 0x0800
mask_12bit: word 0x0fff
sl_mask: word 0xf000
sh_mask: word 0xffff

l: word 0x0
h: word 0x0


tmp: word ?
start:
    cla
check_sign:
    ld (old_arr_pointer)+
    st tmp
    and smask
    bzc negative

main:
    ld tmp
    st l
    and mask_12bit
    st h

    ld l
    push
    call $multiply_func
    pop
    add const
    st (new_arr_pointer)+
    cla ; 0000 в старший бит
    st (new_arr_pointer)+
    loop len
    jump start



negative:
    ld tmp
    st l
    or sh_mask ; ffff в старший бит
    st h

    ld l
    push
    call $multiply_func
    pop
    add const
    st (new_arr_pointer)+
    ld h ; 0000 в старший бит
    st (new_arr_pointer)+
    loop len
    jump start


finish:
     hlt




multiply_func:
    ld &1
    asl
    add &1
    asl
    asl
    add &1
    st &1   ; st 7ff
    ret


org 0x400
new_arr:
    word ?, ?
    word ?, ?
    word ?, ?


org 0x6c1
arr:
    word 0x0801
    word 0x0802
    word 0x0803
