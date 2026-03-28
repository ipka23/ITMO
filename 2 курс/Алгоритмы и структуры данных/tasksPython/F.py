l = []
while True:
    n = input().strip()
    if n == "":
        break
    l.append(n)

for i in range(len(l) - 1):
    if l[i] + l[i + 1] < l[i + 1] + l[i]:
        l[i], l[i + 1] = l[i + 1], l[i]
print(l)