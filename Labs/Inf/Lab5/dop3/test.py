file = open('data.csv').readlines()
lines = [line.replace(';', ',') for line in file]
new_file = open('data2.csv', 'w')
s = ""
for i in range(len(lines)):
    s += lines[i]
new_file.write(s)

