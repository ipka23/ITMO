import re

def count_regex(test):
    match = re.findall(r"X-\\", test)
    return print(len(match), match)


for i in range(1,6):
    count_regex(open(f'test{i}').readline())