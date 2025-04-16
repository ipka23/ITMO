x:  word 0x1

main:
	ld x
	push
	call $func
	pop
	st x
	hlt

iter: word 0x5
tmp:  word 0x0
func:
    ld &1
    add tmp
    st tmp
    loop iter
    jump func
    ld tmp
    neg
    sub #0x5
    st &1
    ret
