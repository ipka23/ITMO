; 1. Массив имеет следующие характеристики: - адрес начала массива в памяти БЭВМ - 0x6d2
; - количество элементов исходного массива - 16
; - каждый элемент является знаковым числом с разрядностью 11 бит
; - элементы хранятся в массиве по границам слов, нет необходимости в плотной упаковке
; 2. Для элементов массива необходимо вычислить 32-х битное значение функции:
; F(M_i) = 9 * M_i + 649
; -32-битный результат необходимо поместить в другой массив по адресу 0x400
; -Результатом является массив 32-х разрядных чисел равным количеству элементов исходного массива.

LEN: WORD 16
ANSWER_P: WORD 0x400
CURR: WORD ?
TEMP: WORD ?
NUM_H: WORD ?
CONST: WORD 649
BIT11: WORD 0x0400
MASK_P: WORD 0x07FF
MASK_N: WORD 0xF800
ARRAY_P: WORD 0x6D2

START: CLA
  LD (ARRAY_P)+
  ST $CURR
  AND $BIT11
  BEQ POSITIVE
  JUMP NEGATIVE

POSITIVE: LD $CURR
  AND $MASK_P
  ST $CURR
  LD #0x0
  ST $NUM_H
  JUMP ST_L

NEGATIVE: LD $CURR
  OR $MASK_N
  ST $CURR
  LD #0xFF
  ST $NUM_H
  JUMP ST_L

ST_L: CALL FUNC
  LD $TEMP ;начало младший
  ST (ANSWER_P)+
  LD $NUM_H ;потом старший
  ST (ANSWER_P)+
  LOOP $LEN
  JUMP START
  HLT

FUNC: LD $CURR
  ASL
  ASL
  ASL
  ST $TEMP
  LD $CURR
  ADD $TEMP
  ADD $CONST
  ST $TEMP
  CLA
  ADC $NUM_H
  ST $NUM_H
  RET

ORG 0x6D2
WORD 0x0C18 ; ответ: -1000*9 + 649 = 0xFFFFDF61
WORD 0xF801 ; 1111 1000 0000 0001 = мусор_000_0000_0001 = 1   ответ: 0x0292 = 0b0000_0010_1001_0010 = 658
WORD 0xFFFE ; 1111 1111 1111 1110 =  ответ: 0x0277 = 631
WORD 0xFFFF ;0x0280
WORD 0xF80A ;0x02E3
WORD 0xF3E8 ;0x25B1
WORD 0xFFFF
WORD 0xFFFF
WORD 0xFFFF
WORD 0xFFFF
WORD 0xFFFF
WORD 0xFFFF
WORD 0xFFFF
WORD 0xFFFF
WORD 0xFFFF
WORD 0xFFFF