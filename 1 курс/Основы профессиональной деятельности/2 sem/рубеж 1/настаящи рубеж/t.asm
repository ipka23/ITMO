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
extend_sign_low: word 0xc000 ; or
extend_sign_high: word 0xffff
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
  add const
  st (new_p)+
  cla
  st (new_p)+
  loop arr_len
  jump check_sign
  jump finish


minus:
  ld extend_sign_high       ;;;;;;;;
  st h                      ;;;;;;;;

  ld l
  or extend_sign_low
  push
  call $multiply
  pop
  add const
  st (new_p)+
  ld h                 ;;;;;;;;
  adc extend_sign_high ;;;;;;;;
  st (new_p)+
  loop arr_len
  jump check_sign

finish:
  hlt

t: word 0x0
cnt: word 0xf
multiply:
  ;ld &1
  ;add t
  ;st t
  ;loop cnt
  ;jump multiply
  ;st &1
  ;ret
  ld &1
  asl
  asl
  asl
  sub &1
  asl
  add &1
  st &1
  cla   ;;;;;;;;
  adc h ;;;;;;;;
  st h  ;;;;;;;;
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
  word 0xfc18 ; -1000 ответ: -1000*15 + 4594 = -10406 = 0xffffd75a , в памяти d75a ffff
  word 0xffff ; -1 ответ: -1*15 + 4594 = 4579 = 0x11e3
  word 0x0001 ; 1 ответ: 1*15 + 4594 = 4609 = 0x1201
  word 0xfffe ; -2 ответ: -2*15 + 4594 = 4564 = 0x11d4
  word 0x1388 ; 5000 ответ: 5000*15 + 4594 = 79594 = 0x136EA
  word 0xec78 ; -5000 ответ: -5000*15 + 4594 = -70406 = 0xfffeecfa
  word 0x1
  word 0x1
  word 0x1
  word 0x1
  word 0x1
  word 0x1
  word 0x1
  word 0x1
  word 0x1
  word 0x1
  word 0x1
  word 0x1
  word 0x1
  word 0x1
  word 0x1