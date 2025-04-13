ORG 0x223
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
                AND mask
                OUT 2
                CMP stop_symbol
                BEQ end_program

symbol_2:       IN 3
                AND #0x40
                BEQ symbol_2
                LD encoded_string
                SWAB
                AND mask
                OUT 2
                CMP stop_symbol
                BEQ end_program
                JUMP start

end_program:    LD word_beginning
                ST current_symbol
                HLT


ORG 0x552
WORD 0xD1DF ; С, Я
ORG 0x553
WORD 0xCDC5 ; Н, Е
ORG 0x554
WORD 0x0DDC ; СТОП_СИМВ, Ь