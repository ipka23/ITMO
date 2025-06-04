; Задание №1. Разработать программу для работы с элементами массива M, в которой:
; 1. Массив имеет следующие характеристики:
; - адрес начала массива в памяти БЭВМ - 0x6ca;
; - число измерений исходного массива - 1;
; - количество элементов исходного массива - 21;
; - каждый элемент является знаковым числом с разрядностью 14 бит;
; - нумерация элементов начинается с 4;
; - элементы хранятся в массиве по границам слов, нет необходимости в плотной упаковке;
; 2. Для элементов массива необходимо вычислить 32-х битное значение функции:
; - формула функции F(Mi) = 15 * Mi + 4594;
; - 32-битный результат необходимо поместить в другой массив по адресу 0x400
; - Результатом является массив 32-х разрядных чисел равным количеству элементов исходного массива.
; Примечание: все числа представлены в десятичной системе счисления, если явно не указано иное.

pointer: word 0x6ca
new_p: word 0x400
extend_sign_low: word 0xe000 ; or
extend_sign_high: word 0xffff ; or
smask: word 0x2000 ; and
l: word 0x0
h: word 0x0
const: word 0x11f2 ; 4594
arr_len: word 0x0015 ; 21

start:

check_sign:
  ld (pointer)+
  st l
  and smask
  bzc minus

plus:
  ld l
  push
  call $multiply
  pop
  st l


  ld l
  add const
  st (new_p)+
  cla
  st (new_p)+
  loop arr_len
  jump check_sign
  jump finish


minus:
  ld h
  or extend_sign_high
  st h

  ld l
  or extend_sign_low
  push
  call $multiply
  pop
  st l


  ld l
  add const
  st (new_p)+
  ld h
  st (new_p)+
  loop arr_len
  jump check_sign

finish:
  hlt

t: word 0x0
cnt: word 0xf
multiply:
  ld &1
  add t
  st t

  ld h
  adc 0
  st h

  loop cnt
  jump multiply
  st &1
  ld #0xf
  st cnt
  ret


org 0x400
new_arr:
  word 0, 0
  word 0, 0
  word 0, 0
  word 0, 0
  word 0, 0
  word 0, 0
  word 0, 0
  word 0, 0
  word 0, 0
  word 0, 0
  word 0, 0
  word 0, 0
  word 0, 0
  word 0, 0
  word 0, 0
  word 0, 0
  word 0, 0
  word 0, 0
  word 0, 0
  word 0, 0
  word 0, 0

org 0x6ca
arr:
  word 0x1, 0x1
  word 0x1, 0x1
  word 0x1, 0x1
  word 0x1, 0x1
  word 0x1, 0x1
  word 0x1, 0x1
  word 0x1, 0x1
  word 0x1, 0x1
  word 0x1, 0x1
  word 0x1, 0x1
  word 0x1, 0x1
  word 0x1, 0x1
  word 0x1, 0x1
  word 0x1, 0x1
  word 0x1, 0x1
  word 0x1, 0x1
  word 0x1, 0x1
  word 0x1, 0x1
  word 0x1, 0x1
  word 0x1, 0x1
  word 0x1, 0x1
