import re

def count_regex(test):
    l = re.findall(r"X-\\", test)
    return print(len(l), l)


for i in range(1,6):
    count_regex(open(f'test{i}').readline())