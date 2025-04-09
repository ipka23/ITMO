org 0x4b6

current_string_addr:
    word 0x5a5



start:
    cla
s1:
    in 5
    and #0x40
    beq s1
    in 4
    swab    ; симв1 00
    st (current_string_addr)
    swab
    cmp #0x00
    beq finish

s2:
    in 5
    and #0x40
    beq s2
    ld (current_string_addr)
    in 4    ; симв1 симв2
    st (current_string_addr)+
    sxtb
    cmp #0x00 ; 00 симв2 == 0x0000 ?
    beq finish
    jump start


finish:
    hlt

org 0x5a5
word ?