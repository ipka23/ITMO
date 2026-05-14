    .data
    .org 0x100

greet:              .byte 7, 'Hello, _______________________'
prompt:             .byte 19, 'What is your name?\n'
stop_symbol:        .byte 1, '\n'
end_symbol:         .byte 1, '!'
buffer_addr:        .word 0
buffer_start_addr:  .word 0
buf_filler:         .word 0x5f
input_addr:         .word 0x80
output_addr:        .word 0x84
buffer_length:      .word 30
buffer_max_length:  .word 29
overflow_value:     .word 0xCCCCCCCC
buf2_start_addr:    .word 0x3E8


    ; t0 - output_addr
    ; t1 - ptr
    ; t2 - n
    ; t3 - tmp
    ; t4 - '\n'
    ; t5 - '!'
    ; t6 - input_addr
    ; a0 - buffer_ptr (buffer_addr)
    ; a1 - m
    ; a2 - '_'
    ; a3 - buffer_length
    ; a4 - n + m
    ; a5 - buffer_start_addr
    ; a6 - 0xCCCCCCCC
    ; a7 - buffer_max_length


    .text
_start:
    lui   t0, %hi(output_addr)
    addi  t0, t0, %lo(output_addr)
    lw    t0, 0(t0)

    lui   t1, %hi(prompt)
    addi  t1, t1, %lo(prompt)
    lb    t2, 0(t1)
    addi  t1, t1, 1

    addi  a4, a4, 7

    lui   sp, %hi(buf2_start_addr)
    addi  sp, sp, %lo(buf2_start_addr)
    lw    sp, 0(sp)

print_prompt:
    beqz  t2, print_greet

    lb    t3, 0(t1)
    sb    t3, 0(t0)

    addi  t2, t2, -1
    addi  t1, t1, 1

    j     print_prompt

print_greet:
    lui   t1, %hi(greet)
    addi  t1, t1, %lo(greet)
    lb    t2, 0(t1)
    addi  t1, t1, 1

    lui   t4, %hi(stop_symbol)
    addi  t4, t4, %lo(stop_symbol)
    addi  t4, t4, 1
    lb    t4, 0(t4)

    lui   t5, %hi(end_symbol)
    addi  t5, t5, %lo(end_symbol)
    addi  t5, t5, 1
    lb    t5, 0(t5)

    lui   t6, %hi(input_addr)
    addi  t6, t6, %lo(input_addr)
    lw    t6, 0(t6)

    lui   a0, %hi(buffer_addr)
    addi  a0, a0, %lo(buffer_addr)
    lw    a0, 0(a0)

    sb    a4, 0(a0)
    addi  a0, a0, 1

    lui   a3, %hi(buffer_length)
    addi  a3, a3, %lo(buffer_length)
    lb    a3, 0(a3)

    lui   a5, %hi(buffer_start_addr)
    addi  a5, a5, %lo(buffer_start_addr)
    lw    a5, 0(a5)

    lui   a6, %hi(overflow_value)
    addi  a6, a6, %lo(overflow_value)
    lw    a6, 0(a6)

    lui   a7, %hi(buffer_max_length)
    addi  a7, a7, %lo(buffer_max_length)
    lb    a7, 0(a7)

    beq   t4, t3, check_name_length

check_name_length:
    bgt   a4, a7, overflow

    lb    t3, 0(t6)
    beq   t4, t3, restore_buf2_addr

    sb    t3, 0(sp)
    addi  sp, sp, 1
    addi  a4, a4, 1
    j     check_name_length

restore_buf2_addr:
    lui   sp, %hi(buf2_start_addr)
    addi  sp, sp, %lo(buf2_start_addr)
    lw    sp, 0(sp)

print_hello:
    beqz  t2, print_name

    lb    t3, 0(t1)
    sb    t3, 0(a0)
    sb    t3, 0(t0)

    addi  t2, t2, -1
    addi  t1, t1, 1
    addi  a0, a0, 1
    addi  a3, a3, -1
    j     print_hello

print_name:
    mv    a1, a4
    addi  a1, a1, -7

loop_print_name:
    beqz  a1, end_print_name

    lb    t3, 0(sp)
    sb    t3, 0(a0)
    sb    t3, 0(t0)

    addi  sp, sp, 1
    addi  a0, a0, 1
    addi  a3, a3, -1
    addi  a1, a1, -1
    j     loop_print_name

end_print_name:
    sb    t5, 0(t0)
    sb    t5, 0(a0)
    addi  a4, a4, 1
    addi  a0, a0, 1

    lui   a2, %hi(buf_filler)
    addi  a2, a2, %lo(buf_filler)
    lb    a2, 0(a2)

loop:
    beqz  a3, end
    sb    a2, 0(a0)
    addi  a0, a0, 1
    addi  a3, a3, -1
    j     loop

end:
    sb    a4, 0(a5)
    halt

overflow:
    sw    a6, 0(t0)
    halt