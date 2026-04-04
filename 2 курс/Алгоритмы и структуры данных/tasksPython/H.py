n, k = map(int, input().split())
prices = list(map(int, input().split()))

prices.sort(reverse=True)
print(prices)

s = 0

for i in range(len(prices)):
    if (i + 1) % k == 0:
        continue
    s += prices[i]

print(s)