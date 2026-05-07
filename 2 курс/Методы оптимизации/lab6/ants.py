import math
import random

nodes = ["A", "B", "C", "D", "E", "F", "G"]
n = len(nodes)
INF = math.inf

graph = [
    [INF, 6,   8,   INF, 12,  INF, INF],
    [6,   INF, INF, 9,   INF, INF, 31 ],
    [8,   INF, INF, 8,   INF, 8,   INF],
    [INF, 9,   8,   INF, INF, 9,   17 ],
    [12,  INF, INF, INF, INF, INF, INF],
    [INF, INF, 8,   9,   INF, INF, 5  ],
    [INF, 31,  INF, 17,  INF, 5,   INF]
]

ph = []
for i in range(n):
    row = []
    for j in range(n):
        row.append(1.0)
    ph.append(row)

cool = 0.5
q = 100
numAnts = 20
numIterations = 100

def chooseNext(cur, visited):
    paths = []
    weights = []
    total = 0

    for v in range(n):
        if graph[cur][v] != INF and v not in visited:
            w = ph[cur][v] / (graph[cur][v] * graph[cur][v])
            paths.append(v)
            weights.append(w)
            total += w

    if total == 0:
        return None

    r = random.random()
    curSum = 0

    i = 0
    while i < len(paths):
        curSum += weights[i] / total
        if r <= curSum:
            return paths[i]
        i += 1

    return paths[len(paths) - 1]

def buildPath():
    path = []
    visited = set()

    cur = 0
    path.append(cur)
    visited.add(cur)

    while cur != 6:
        nextPath = chooseNext(cur, visited)
        if nextPath is None:
            return None
        path.append(nextPath)
        visited.add(nextPath)
        cur = nextPath

    return path

def pathLength(path):
    total = 0
    i = 0
    while i < len(path) - 1:
        total += graph[path[i]][path[i + 1]]
        i += 1
    return total

bestPath = None
bestLength = INF

it = 0
while it < numIterations:
    allPaths = []

    a = 0
    while a < numAnts:
        path = buildPath()
        if path is not None:
            length = pathLength(path)
            allPaths.append([path, length])

            if length < bestLength:
                bestLength = length
                bestPath = path
        a += 1

    i = 0
    while i < n:
        j = 0
        while j < n:
            if graph[i][j] != INF:
                ph[i][j] = ph[i][j] * (1 - cool)
            j += 1
        i += 1

    k = 0
    while k < len(allPaths):
        path = allPaths[k][0]
        length = allPaths[k][1]

        add = q / length

        i = 0
        while i < len(path) - 1:
            u = path[i]
            v = path[i + 1]
            ph[u][v] += add
            ph[v][u] += add
            i += 1

        k += 1

    it += 1

res = []
i = 0
while i < len(bestPath):
    res.append(nodes[bestPath[i]])
    i += 1

print("Лучший путь:", res)
print("Длина:", bestLength)