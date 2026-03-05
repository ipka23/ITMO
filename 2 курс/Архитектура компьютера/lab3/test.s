    .data
.org 0x100
input_addr:      .word 0x80
output_addr:     .word 0x84
i:               .word  16
left_mask:       .word  0x80000000
right_mask:      .word  0x00000001
l:               .word  0
r:               .word  0                  ; дополнительная ячейка
n:               .word  0
const_1:         .word  1
is_palindrome:   .word  1
shift_31:        .word  0x1F               ; переменная для хранения количества битов для сдвига, инициализированная 31
counter:         .word  0
tmp:             .word  0


    .text

_start:
    load         input_addr ; load_addr
    load_acc

    store        n
    load_imm    0
    store counter
    load const_1
    store is_palindrome
    load_imm 16
    store i
    load         i

while:
    beqz         end
    load         n
    shiftl       counter
    and          left_mask
    shiftr       shift_31
    store        tmp

    load         n
    shiftr       counter
    and          right_mask

    xor          tmp
    and          is_palindrome
    store        is_palindrome
    beqz         end                         ; если хоть один бит не совпал то завершаем программу

    load         counter
    add          const_1
    store        counter

    load         i
    sub          const_1
    store        i

    jmp          while

end:
    load          is_palindrome
    store_ind     output_addr
    halt
