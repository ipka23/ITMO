s = input()
weights = list(map(int, input().split()))  # ← добавить

freq = [0] * 26
for ch in s:
    freq[ord(ch) - 97] += 1

candidates = []
for i in range(26):
    if freq[i] >= 2:
        ch = chr(ord('a') + i)
        candidates.append((weights[i], ch, freq[i]))
candidates.sort(reverse=True)

n = len(s)
ans = [""] * n
left = 0
right = n - 1
fillers = []

for weight, ch, cnt in candidates:
    ans[left] = ch
    ans[right] = ch
    left += 1
    right -= 1
    for _ in range(cnt - 2):
        fillers.append(ch)

for i in range(26):
    if freq[i] == 1:
        fillers.append(chr(ord('a') + i))

idx = 0
for i in range(n):
    if ans[i] == "":
        ans[i] = fillers[idx]
        idx += 1

print("".join(ans))
