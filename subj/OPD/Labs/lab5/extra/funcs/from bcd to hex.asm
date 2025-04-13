
res:    word 0x0018

START:
    ld res
    push
    ld #0x0
    push
    call $bcd_to_hex
    pop
    pop
    st res
    hlt



bcd_to_hex:
    ld &2           ; a -> AC
    sub #0xA
    st &2            ; a - 10 -> a
    ld &1           ; счетчик десятков
    inc
    st &1
    ld &2           ; a -> AC
    cmp #0x000A     ; если a < 10, то завершаем подсчет десятков
    blt done
    jump bcd_to_hex

done:
    ld &1
    asl
    asl
    asl
    asl
    or &2
    st &2
    ret