def check(d, coords, k):
    last_cow = coords[0]
    cows = 1
    # coords = coords[1:]
    for x in coords:
        if x - last_cow >= d:
            cows += 1
            last_cow = x
            if cows >= k:
                return True
    return False

def solve():
    n, k = map(int, input().split())
    l = 0
    coords = list(map(int, input().split()))
    r = coords[n - 1] - coords[0] + 1
    while r - 1 > l:
        mid = (l + r) // 2
        if check(mid, coords, k):
            l = mid
        else:
            r = mid
    return l


if __name__ == "__main__":
    print(solve())
