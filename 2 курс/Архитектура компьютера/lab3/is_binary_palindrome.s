    .data

input_addr:      .word  0x80
output_addr:     .word  0x84
first_part_mask: .word  0xFFFF0000
second_part_mask: .word  0x0000FFFF
shift_count:     .word  0x10               ; переменная для хранения количества битов для сдвига, инициализированная 16
result:          .word  0xCCCCCCCC
tmp:             .word  0                  ; дополнительная ячейка

    .text

_start:
    load         input_addr
    load_acc
    and          first_part_mask
    shiftr       shift_count
    store        tmp

    load         input_addr
    and          second_part_mask
    sub          tmp
    beqz         is_palindrome

not_palindrome:
    load_imm     -1
    store_ind    output_addr
    halt

is_palindrome:
    load_imm     1
    store_ind    output_addr
    halt