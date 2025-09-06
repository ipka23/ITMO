file = open('data.csv')
lines = [line.replace(';', ',') for line in file.readlines()]
new_file = open('data2.csv', 'w')
s = ""
for i in range(len(lines)):
    s += lines[i]
new_file.write(s)
file.close()
new_file.close()