ORG 0x228

word_beginning: WORD 0x552

encoded_string: WORD ?
stop_symbol:    WORD 0x000D
current_symbol: WORD 0x552
mask:           WORD 0x00FF


start:          CLA

symbol_1:       IN 3
                AND #0x40
                BEQ symbol_1
                LD (current_symbol)+
                ST encoded_string
                SWAB
                AND mask
                CMP stop_symbol
                BEQ end_program
                OUT 2

symbol_2:       IN 3
                AND #0x40
                BEQ symbol_2
                LD encoded_string
                AND mask
                CMP stop_symbol
                BEQ end_program
                OUT 2
                JUMP start

end_program:    LD word_beginning
                ST current_symbol
                HLT