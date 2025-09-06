org 0x6C1
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF
word 0xFFFF

org 0x10
compare: word 0x0800
mask_plus: word 0x0FFF
mask_minus: word 0xF000
index: word 0x6C1
len: word 0x19
ans_index: word 0x400
curr: word ?
tmp: word ?
st_num: word ?
const: word 0x4BA

org 0x30
start: cla
  ld (index)+
   st $curr
  and $compare
  beq plus
  jump minus

plus: ld $curr
  and $mask_plus
  st $curr
  ld #0x00
  st $st_num
  jump cnt_st

minus: ld $curr
  or $mask_minus
  st $curr
  ld #0xFF
  st $st_num
  jump cnt_st

cnt_st: call func
  ld $st_num
  st (ans_index)+
  ld $tmp
  st (ans_index)+
  loop $len
  jump start
  hlt

func: ld $curr
  asl
  asl
  asl
  st $tmp
  ld $curr
  asl
  asl
  add $tmp
  add $curr
  add $const
  st $tmp
  cla
  adc $st_num
  st $st_num
  ret
