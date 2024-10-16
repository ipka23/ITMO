import re
def count_regex(test):
    l = re.findall(r"X-\\", test)
    return len(l)

# test1 = "X-\\X-\\X-\\X-\\X-\\"
# test2 = "abcX-\\defX-\\ghiX-\\jkl"
# test3 = "X-\\\\X-\\X-\\\\X-\\"
# test4 = "X-\\\\X_\\-\\X\\\\\\\\X-\\"
# test5 = "12345!@#X-/xyz$%^&*()_+|X-/"

print(count_regex(open('test1').readline()))
