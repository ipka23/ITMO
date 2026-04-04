s = input()
freq = [0] * 26
sorted = []
weights = list(map(int, input().split()))

print(weights)
lettersWithWeights = {}


def getChar(index):
    return chr(ord('a') + index)


for i in range(len(s)):
    sorted.append(s[i])
for _ in range(len(sorted) - 1):
    for i in range(len(sorted) - 1):
        if sorted[i] < sorted[i + 1]:
            fills = sorted[i + 1]
            sorted[i + 1] = sorted[i]
            sorted[i] = fills

ans_i = len(s)
ans = [""] * ans_i

for n in sorted:
    freq[ord(n) - 97] += 1

print(freq)
fills = []
candidates = []
for i in range(26):
    char = getChar(i)
    fr = freq[i]
    if fr >= 2:
        candidates.append((weights[i], char, fr))
    else:
        fills.append(char)

candidates.sort(reverse=True)
print(candidates)

n = len(ans)
left = 0
right = n - 1
for weight, char, freq in candidates:
    ans[left] = char
    ans[right] = char
    left += 1
    right -= 1
    for _ in range(freq - 2):
        fills.append(char)

j = 0
for i in range(len(ans)):
    if ans[i] == "":
        ans[i] = fills[j]
        j += 1

print("".join(ans))
