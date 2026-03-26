

def check(x, coords):
    cows = 1
    last_cow = coords[0]
    for c in coords:
        if c - last_cow >= x:
            cows += 1
            last_cow = c
    return cows >= x
def solve():
    n, k = input().split()

    coords = list(map(int, input().split()))

    l = 0
    r = coords[-1] - coords[0] + 1
    while r - l > 1:
        m = (l + r) // 2
        if check(m, coords):
            l = m
        else:
            r = m
    return l

if __name__ == "__main__":
    print(solve())