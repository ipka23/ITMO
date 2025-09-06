sum1: WORD 0
sum2: WORD 0

thingWeSum: WORD 0

summing:
LD thingWeSum
BLT if_neg

ADD sum1
ST sum1
CLA
ADC sum2
ST sum2
JUMP return

if_neg:
ADD sum1
ST sum1
LD #0xFF
ADC sum2
ST sum2
JUMP return

return:
