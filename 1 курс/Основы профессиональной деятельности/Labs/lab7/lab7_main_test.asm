org 0x0ff

; ячейки для хранения результата теста 1 - успешно 0 - ошибка
check1: word 0xffff
check2: word 0xffff
check3: word 0xffff

; ячейка для хранения результата всех тестов 1 - все были выполненны успешно 0 - не все были выполненны успешно
check_all: word 0xffff

; переменные для проверки SHL
t1: word 0xffff ; shl(ffff) = fffe
t2: word 0x0001 ; shl(0001) = 0002
t3: word 0x0 ; shl(0) = 0

; значения для проверки
res1: word 0xfffe
res2: word 0x0002
res3: word 0x0

org 0x109
START:
    ld t1
    push
    call $test1
    pop
    st check1

    ld t2
    push
    call $test2
    pop
    st check2

    ld t3
    push
    call $test3
    pop
    st check3

; проверяем все тесты
    ld check1
    push

    ld check2
    push

    ld check3
    push

    call $total_check
    pop
    pop
    pop
    st check_all

end_program:
    hlt


test1:
    ld &1 ; t1
    word 0x0F10 ; SHL(AC) -> AC
    cmp res1
    beq success
    cla
    st &1
    ret

test2:
    ld &1 ; t2
    word 0x0F10 ; SHL(AC) -> AC
    cmp res2
    beq success
    cla
    st &1
    ret

test3:
    ld &1 ; t3
    word 0x0F10 ; SHL(AC) -> AC
    cmp res3
    beq success
    cla
    st &1
    ret

success:
    ld #0x1
    st &1
    ret


total_check:
    ld &1   ; check1
    and &2  ; check2
    and &3  ; check3
    cmp #0x0001
    bne failure
    st &3
    ret

failure:
    st &3
    ret