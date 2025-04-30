x:  word 0x1

main:
	ld x
	push
	call $func
	pop
	st x
	hlt

iter: word 0x4
tmp:  word 0x0
func:
    ld &1
    add tmp
    st tmp
    loop iter
    jump func
    ld tmp
    neg
    sub #0x8
    st &1
    ret

org 0x666
x:  word 0x4

int1:           ; вычисление f(x) = -4x-8 и вывод результата f(x) на DR(#2) ВУ-1 по нажатию кнопки готовности SR(#3) ВУ-1
    ld x
    hlt         ; для отладки
    asl
    asl
    neg
    sub #8
    st x
    hlt