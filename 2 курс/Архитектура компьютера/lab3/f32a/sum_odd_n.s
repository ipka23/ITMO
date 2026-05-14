    .data
.org             0x100
input_addr:      .word  0x80
output_addr:     .word  0x84
divisor_addr:    .word  0x90
n_odd:           .word  0

    .text
_start:
    @p input_addr a! @       \ n:[]
    dup dup a!               \ n:n:[]
    sum_odd_n

    @p output_addr a! !      \  1: mem[output_addr]:res:[] == 0x84:res:[] 2: mem[0x84] <- dataStack.pop() ==> :[]

    halt

divide:
    lit 31 >r
divide_do:
    +/
    next divide_do
    ;

multiply:
    lit 31 >r
multiply_do:
    +*
    next multiply_do
    drop drop a
    ;

check_overflow:
    dup                      \res:res:[]
    lit 0x80000000           \ 0x80000000:res:res:[]
    and
    \ x:res:[]; if x < 0 then is_overflow else return
    if not_overflow          \ x:res:[]

is_overflow:
    lit 0
    ;

not_overflow:
    lit 1
    ;

sum_odd_n:
    if is_zero
    inv
    -if is_negative

    @p divisor_addr b!       \ B <- 0x90 \ :[]
    lit 2 !b                 \ :[]
    \ :[]
    lit 0 dup                \ инициализируем ячейки для хранения частного и остатка нулями
    \ 0:0:[]
    divide

    \ quotient:remainder:[]
    +
    \ n_odd:[]

    dup a!                   \ n_odd:[]
    lit 0                    \ кладем 0 на стек для хранения частичных результатов умножений \ 0:n_odd:[]
    multiply                 \ res:[]

    check_overflow           \ 0:res:[] or 1:res:[]
    if overflow              \ res:[]

sum_odd_n_finish:
    ;

is_negative:
    lit -1
    ;

is_zero:
    lit -1
    ;

overflow:
    drop                     \ :[]
    lit 0xCCCCCCCC
    ;