ORG 0x2A6

X: WORD 0x0001
Y: WORD 0xFECB
Z: WORD 0x0E74
A: WORD 0x0E3A
B: WORD 0x00A1
R: WORD ?


START:          CLA
                ST R
                LD Z
                INC
                PUSH
                CALL $FOO
                POP
                SUB R
                ST R
                LD Y
                DEC
                CALL $FOO
                POP
                ADD R
                ST R
                LD X
                PUSH
                CALL $FOO
                POP
                ADD R
                ST R
END_PROGRAM:    HLT



FOO:    LD &1
        BMI LOAD_A
        BEQ LOAD_A
        CMP A
        BGE LOAD_A
        ASL
        ADD &1
        ADD B
        JUMP RES
LOAD_A: LD A
RES:    ST &1
        RET