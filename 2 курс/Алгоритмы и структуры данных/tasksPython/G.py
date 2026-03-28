s = input()
freq = [0] * 26
l = []


def getChar(index):
    return chr(ord('a') + index)
def countWeight():
    pass
for i in range(len(s)):
    l.append(s[i])
for _ in range(len(l) - 1):
    for i in range(len(l) - 1):
        if l[i] < l[i + 1]:
            tmp = l[i + 1]
            l[i + 1] = l[i]
            l[i] = tmp
ans_i = len(s)
ans = [""] * ans_i

for n in l:
    freq[ord(n) - 97] += 1

print(freq)
tmp = []

for i in range(len(freq) - 1, -1, -1):
    char = getChar(i)
    fr = freq[i]
    if fr >= 2:
        ans[ans_i - 1] = char
        ans[-ans_i] = char
        ans_i -= 1
        while fr - 2 != 0:
            tmp.append(char)
            fr -= 1
    elif fr == 1:
        tmp.append(char)
n = len(ans)
j = 0
for i in range(len(ans)):
    if ans[i] == "":
        ans[i] = tmp[j]
        j += 1

# print(ans)
# print(tmp)

print("".join(ans))
