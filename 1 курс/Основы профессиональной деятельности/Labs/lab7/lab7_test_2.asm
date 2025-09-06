org 0x0ff
; ячейки для хранения результата теста 1 - успешно 0 - ошибка
check1: word 0x0
check2: word 0x0
check3: word 0x0
; ячейка для хранения результата всех тестов 1 - все были выполненны успешно 0 - не все были выполненны успешно
check_all: word 0x1
; переменные для проверки SHL
t1: word 0xffff ; -1 => shl(ffff) = fffe
t2: word 0x0001 ; shl(0001) = 0002
t3: word 0x0 ; shl(0) = 0

; значения которые должны получаться
res1: word 0xfffe
res2: word 0x0002
res3: word 0x0

org 0x109
START:
    call test1
    call test2
    call test3
    ld check1
    and check2
    and check3
    st check_all
END_PROG:
    hlt

test1:
    ld t1
    word 0x0F10 ; SHL(AC) -> AC
    cmp res1
    beq success1
    cla
    st $check1
    ret

success1:
    ld #0x1
    st $check1
    ret


test2:
    ld t2
    word 0x0F10 ; SHL(AC) -> AC
    cmp res2
    beq success2
    cla
    st $check2
    ret

success2:
    ld #0x1
    st $check2
    ret

test3:
    ld t3
    word 0x0F10 ; SHL(AC) -> AC
    cmp res3
    beq success3
    cla
    st $check3
    ret

success3:
    ld #0x1
    st $check3
    ret