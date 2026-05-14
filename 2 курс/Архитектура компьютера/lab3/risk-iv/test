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
    ; t1 - greet_ptr
    ; t2 - n (оставшиеся символы greet)
    ; t3 - input[i]
    ; t4 - '\n'
    ; t5 - '!'
    ; t6 - input_addr
    ; a0 - текущая позиция в buffer_addr
    ;
    ; a2 - '_'
    ; a3 - buffer_length
    ; a4 - общая длина "Hello, " + имя + '!'
    ; a5 - buffer_start_addr
    ; a6 - 0xCCCCCCCC
    ; a7 - buffer_max_length
    ; s1 - длина имени (m)


    .text
_start:
    lui   t0, %hi(output_addr)
    addi  t0, t0, %lo(output_addr)
    lw    t0, 0(t0)              ; t0 = output_addr

    lui   t1, %hi(prompt)
    addi  t1, t1, %lo(prompt)
    lb    t2, 0(t1)              ; t2 = prompt[0] (длина prompt)
    addi  t1, t1, 1              ; t1 -> первый символ строки prompt

    addi  a4, a4, 7              ; a4 = 7, начальная длина "Hello, "

    ; инициализируем буфер 2 (buf2) по адресу 0x3E8
    lui   sp, %hi(buf2_start_addr)
    addi  sp, sp, %lo(buf2_start_addr)
    lw    sp, 0(sp)              ; sp = 0x3E8

    ; t0 - output_addr
    ; t1 - текущий символ prompt
    ; t2 - оставшаяся длина prompt
    ; t3 - временный регистр
    ; a4 - текущая длина строки "Hello, " + имя

print_prompt:
    beqz  t2, print_greet

    lb    t3, 0(t1)              ; t3 = *prompt
    sb    t3, 0(t0)              ; вывод символа prompt в io[0x84]

    addi  t2, t2, -1             ; длина prompt--
    addi  t1, t1, 1              ; следующий символ prompt

    j     print_prompt

    ; t0 - output_addr
    ; t1 - greet_ptr
    ; t2 - n (длина greet)
    ; t3 - input[i]
    ; t4 - '\n'
    ; t5 - '!'
    ; t6 - input_addr
    ; a0 - buffer_addr
    ;
    ; a2 - '_'
    ; a3 - buffer_length
    ; a4 - длина "Hello, " + имя
    ; a5 - buffer_start_addr
    ; a6 - 0xCCCCCCCC (overflow_value)
    ; a7 - buffer_max_length

print_greet:
    lui   t1, %hi(greet)
    addi  t1, t1, %lo(greet)
    lb    t2, 0(t1)              ; t2 = greet[0] (длина greet)
    addi  t1, t1, 1              ; t1 -> первый символ greet

    lui   t4, %hi(stop_symbol)
    addi  t4, t4, %lo(stop_symbol)
    addi  t4, t4, 1
    lb    t4, 0(t4)              ; t4 = '\n'

    lui   t5, %hi(end_symbol)
    addi  t5, t5, %lo(end_symbol)
    addi  t5, t5, 1
    lb    t5, 0(t5)              ; t5 = '!'

    lui   t6, %hi(input_addr)
    addi  t6, t6, %lo(input_addr)
    lw    t6, 0(t6)              ; t6 = input_addr (0x80)

    lui   a0, %hi(buffer_addr)
    addi  a0, a0, %lo(buffer_addr)
    lw    a0, 0(a0)              ; a0 = buffer_addr

    sb    a4, 0(a0)              ; mem[0] = текущая длина (пока 7)
    addi  a0, a0, 1              ; a0 -> mem[1], начало строки

    lui   a3, %hi(buffer_length)
    addi  a3, a3, %lo(buffer_length)
    lb    a3, 0(a3)              ; a3 = buffer_length

    lui   a5, %hi(buffer_start_addr)
    addi  a5, a5, %lo(buffer_start_addr)
    lw    a5, 0(a5)              ; a5 = buffer_start_addr

    lui   a6, %hi(overflow_value)
    addi  a6, a6, %lo(overflow_value)
    lw    a6, 0(a6)              ; a6 = 0xCCCCCCCC

    lui   a7, %hi(buffer_max_length)
    addi  a7, a7, %lo(buffer_max_length)
    lb    a7, 0(a7)              ; a7 = buffer_max_length

    beq   t4, t3, check_name_length

check_name_length:
    bgt   a4, a7, overflow       ; если длина > max, то overflow

    lb    t3, 0(t6)              ; читаем символ имени из input
    beq   t4, t3, restore_buf2_addr  ; если '\n', то имя закончилось

    sb    t3, 0(sp)              ; пишем символ имени в buf2
    addi  sp, sp, 1              ; следующий байт в buf2
    addi  a4, a4, 1              ; длина строки++
    j     check_name_length

restore_buf2_addr:
    ; восстановить адрес начала buf2 в sp
    lui   sp, %hi(buf2_start_addr)
    addi  sp, sp, %lo(buf2_start_addr)
    lw    sp, 0(sp)              ; sp = buf2_start_addr (0x3E8)

print_hello:
    beqz  t2, print_name

    lb    t3, 0(t1)
    sb    t3, 0(a0)              ; запись greet в буфер mem[0..31]
    sb    t3, 0(t0)              ; вывод greet в io[0x84]

    addi  t2, t2, -1             ; длина greet--
    addi  t1, t1, 1              ; следующий символ greet
    addi  a0, a0, 1              ; следующий байт буфера
    addi  a3, a3, -1             ; buffer_length--
    j     print_hello


print_name:
    mv    s1, a4
    addi  s1, s1, -7             ; s1 = m = (общая длина - 7)

loop_print_name:
    beqz  s1, end_print_name     ; если m == 0, имя закончилось

    lb    t3, 0(sp)              ; читаем символ имени из buf2
    sb    t3, 0(a0)              ; пишем в основной буфер
    sb    t3, 0(t0)              ; выводим в io[0x84]

    addi  sp, sp, 1
    addi  a0, a0, 1
    addi  a3, a3, -1
    addi  s1, s1, -1
    j     loop_print_name

end_print_name:
    sb    t5, 0(t0)              ; вывод '!' в io[0x84]
    sb    t5, 0(a0)              ; запись '!' в буфер
    addi  a4, a4, 1              ; длина строки += 1 (учитываем '!')
    addi  a0, a0, 1              ; следующий байт буфера

    lui   a2, %hi(buf_filler)
    addi  a2, a2, %lo(buf_filler)
    lb    a2, 0(a2)              ; a2 = '_'

loop:
    beqz  a3, end                ; если buffer_length == 0, конец
    sb    a2, 0(a0)              ; заполняем оставшийся буфер '_'
    addi  a0, a0, 1
    addi  a3, a3, -1
    j     loop

end:
    sb    a4, 0(a5)              ; mem[0] = итоговая длина p-строки
    halt

overflow:
    sw    a6, 0(t0)              ; при переполнении выводим 0xCCCCCCCC
    halt