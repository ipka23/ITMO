ORG 0x2A6

START:          CLA
                ST R
                LD Z
                INC
                PUSH
                CALL $FUNC
                POP
                SUB R
                ST R
                LD Y
                DEC
                PUSH
                CALL $FUNC
                POP
                ADD R
                ST R
                LD X
                PUSH
                CALL $FUNC
                POP
                ADD R
                ST R
END_PROGRAM:    HLT

Z: WORD 0x0E74
Y: WORD 0xFEC8
X: WORD 0x0001
R: WORD ?

ORG 0x688
FUNC:   LD &1
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
A: WORD 0x0E3A
B: WORD 0x00A1
