i = (52+89+14+11+20)*4 # =744
for r in range(15):
    if 2**r - r >= i + 1:
        print(r)
        break
print(10 / (i + 10))
