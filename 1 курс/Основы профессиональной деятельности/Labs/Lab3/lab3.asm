org 0x3f8

first:
  word 0x410
current:
  word 0x410
m_length:
  word 0x0004
min:
  word 0x7fff
res:
  word ?

start:
  ld (current)+
  asr      ;
  bcs skip ; если нечетное, то переход на новую итерацию цикла
  asl      ;
  cmp min  ;
  bge skip ; если больше либо равно минимальному, то переход на новую итерацию цикла
  st min   ;
skip:
  loop m_length
  jump start


finish:
  ld min
  st res
  ld first
  st current
  hlt
org 0x410
m0:
  word 0x005a ; 90
m1:
  word 0x0057 ; 87
m2:
  word 0xfff4 ; -12
m3:
  word 0x007e ; 126




min: word 0x8000
x: word 0xffff
ld min
cmp x
bge stop
cla
st x
hlt

stop:
ld #0x1
st x
hlt