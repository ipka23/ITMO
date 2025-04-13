jump hex_to_bcd
res:            word 0x0012
count_of_tens:  word 0x0

; преобразование hex в bcd
hex_to_bcd:
    ld res
    cmp #0x000A
    blt done
    sub #0x000A
    st res
    ld count_of_tens
    inc
    st count_of_tens
    jump hex_to_bcd

done:
    ld count_of_tens
    asl
    asl
    asl
    asl
    or res
    st res
    hlt