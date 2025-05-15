ORG 0x10
ArrAddr: WORD 0x300
CurElemAddr: WORD 0
flat_dimension: WORD 7 ; размер
linearIndexMaxBorder: WORD 0 ; макс граница линейного индекса
count: WORD ? ; счётчик
indexI: WORD 0
indexJ: WORD 0
indexJMaxBorder: WORD ? ; фактическая граница для индекса J (т.к храним мл_байт, ст_байт в элементе)
step_i: WORD 3
step_j: WORD 2
step_i_f: WORD ? ; шаг для первого случая
step_j_f: WORD ?
step_j_actual: WORD ? ; шаг для корректного прохода по элементам в строке
indexLinear: WORD 0
sum1: WORD 0
sum2: WORD 0

Start:
CLA
ST sum1
ST sum2
LD step_i
DEC
ST step_i_f
LD step_j
ASL
ST step_j_actual ; фактический шаг это step * 2
SUB #2
ST step_j_f ; но для первого случая step * 2 - 2
LD flat_dimension ; count = 7 = flat_dimension
ST count
ASL
ST indexJMaxBorder; граница у j = 7*2 = 14
LD #0

square: ; расчёт для дальнейшей проверки границ лин.индекса (2*7*7)
ADD flat_dimension ; (=7)
LOOP count ; (count = 7)
JUMP square
ASL
ST linearIndexMaxBorder

calcIndexes_f: ; индексы для первого прохода
LD step_i_f
ST indexI
LD step_j_f
ST indexJ
JUMP calcLinearIndex

; //////////////////////

OlegSheyko:

calcIndexes: ; добавление шага к индексам (смещение)
LD indexJ
CMP indexJMaxBorder ; сначала проверяем, что мы обошли все элементы в строке (j)
BPL inc_i
ADD step_j_actual ; если нет, то добавляем шаг
ST indexJ
JUMP calcLinearIndex

inc_i: ; иначе ставим в позицию для первого случая !!!
LD step_j_f
ST indexJ

LD indexI ; увеличиваем i
ADD step_i
ST indexI

checking: ; проверка что i не вышел за границы
LD indexI
CMP flat_dimension
BMI calcLinearIndex
HLT


; //////////////////////

calcLinearIndex: ; короче тут типа линейный индекс вычисляется как 7*i + j
CLA
ST linearIndex
LD indexI
BEQ add_j

add_i:
LD linearIndex
ADD flat_dimension
ST linearIndex

LD indexI
DEC
ST indexI
BNE add_i

ASL
ST linearIndex

add_j:
LD linearIndex
ADD indexJ
ST linearIndex
CMP linearIndexMaxBorder ; проверка чтобы он не вышел за границы
BMI elemFinder
HLT

; //////////////////////

elemFinder: ; представляем двумерный массив как единую строку и смещаемся по ней
LD ArrAddr
ADD linearIndex
ST CurElemAddr

; //////////////////////

summing:
LD (CurElemAddr)+

checkingMultiplicity:
ROR
BCS return
ROR
BCS return
ROL
ROL

ADD sum1 ; складывание младшего байта
ST sum1

summing2:
LD (CurElemAddr)
ADC #0 ; сложение с битом С, потому что он будет потерян при расширении знака старшего байта
ExtendingSign:
ASL
ASL
ASL
ASL
SXTB
ASR
ASR
ASR
ASR
ADD sum2
return:
LD CurElemAddr ; возвращение указателя к младшему байту
DEC
ST CurElemAddr
JUMP OlegSheyko

; //////////////////////

ORG 0x300
Array:
WORD 0;   i = 0, j = 0
WORD 0;
...
WORD 0;   i = 0, j = 13
WORD 0;

WORD 0;   i = 1, j = 0
WORD 0;
...
WORD 0;   i = 1, j = 13
WORD 0;
