x:  word 0x1

main:
	ld x
	push
	call $func
	pop
	st x
	hlt

func:
    ld &1
    neg
    st &1
    ret
