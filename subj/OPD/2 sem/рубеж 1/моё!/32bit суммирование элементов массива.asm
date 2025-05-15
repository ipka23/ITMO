pointer: word 0x100
result_addr: word 0x030
arr_len: word 3

start:
    ld (pointer)+       ; загружаем младшее слово
    add (result_addr)   ; прибавляем младшее слово результата
    st (result_addr)+   ; сохраняем сумму младших слов

    ld (pointer)+       ; загружаем старшее слово
    adc (result_addr)   ; прибавляем старшее слово результата + С
    st (result_addr)    ; сохраняем сумму старших слов

    ld result_addr      ; сбрасываем указатель результата
    dec
    st result_addr
    loop arr_len
    jump start

finish:
    hlt

org 0x030
result_sum: word 0x0000, 0x0000 ; org 0x030, org 0x031 ; 2000 0001 + 0005 2000 + 3000 4000 = 5005 6001 = 1_342_529_537(10) => word 0x6001, 0x5005

org 0x100
arr:                ; little-endian
word 0x0001, 0x2000 ; 2000 0001 ; i = 1
word 0x2000, 0x0005 ; 0005 2000 ; i = 2
word 0x4000, 0x3000 ; 3000 4000 ; i = 3