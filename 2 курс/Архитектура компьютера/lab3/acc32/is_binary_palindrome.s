    .data
.org             140
input_addr:      .word  0x80
output_addr:     .word  0x84
i:               .word  16                 ; счетчик итераций
n:               .word  0                  ; переменная для хранения числа из input
mask_31_bit:     .word  0x80000000
mask_0_bit:      .word  0x00000001
is_palindrome:   .word  1                  ; переменная для накопления логического значения результата сравнений
shift_31:        .word  0x1F               ; переменная для хранения количества битов для сдвига, инициализированная 31
shift_counter:   .word  0                  ; cчетчик сдвигов, инициализированный 0
const_1:         .word  1
tmp:             .word  0

    .text

_start:
    load         input_addr
    load_acc
    store        n

while:
    load         n
    shiftl       shift_counter               ; сдвигаем аккумулятор влево на число равное номеру текущей итерации
    and          mask_31_bit
    shiftr       shift_31
    and          mask_0_bit                  ; обнуляем все биты кроме 0 чтобы не было ошибки из-за знакового сдвига
    store        tmp

    load         n
    shiftr       shift_counter               ; сдвигаем аккумулятор вправо на число равное номеру текущей итерации
    and          mask_0_bit                  ; обнуляем все биты кроме 0 чтобы не было ошибки из-за знакового сдвига

    xor          tmp                         ; сравниваем соответствующие биты левой и правой части числа
    not

    and          is_palindrome               ; all(is_palindrome)
    store        is_palindrome
    beqz         end                         ; если хоть один бит не совпал то завершаем программу

    load         shift_counter
    add          const_1
    store        shift_counter

    load         i
    sub          const_1
    store        i
    beqz         end
    jmp          while

end:
    load         is_palindrome
    store_ind    output_addr
    halt