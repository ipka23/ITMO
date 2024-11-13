path = "schedule.yml"
file = open(path, "r", encoding="utf-8").readlines()
yaml_lines = [line.strip().strip('-') for line in file]
i = 0
s = ""
def yaml_list():
    global s
    global i
    d = {}
    while i < len(yaml_lines):
        i += 1
        if i == len(yaml_lines) - 1:
            return s
        if yaml_lines[i].split(":", 1)[1] != "" and len(yaml_lines[i].split(":", 1)) == 2:          # если в строке два значения, то есть и ключ и значение, тогда просто
            key, value = yaml_lines[i].split(": ", 1)
            s += f'<{key}>{value}</{key}>\n'
        elif len(yaml_lines[i].split(":", 1)) == 2 and yaml_lines[i].split(":", 1)[1].strip() == "": # если в строке до ":" только одно значение, значит это начало списка
            s += "<" + yaml_lines[i].split(":", 1)[0] + ">\n"                                                   # записываем в строку открывающий тег списка
            s += yaml_list()
            s += "</" + yaml_lines[i].split(":", 1)[0] + ">\n"                                                  # записываем в строку закрывающий тег списка
            # print(yaml_lines[i].split(":", 1), "yes")
        elif len(yaml_lines[i].split(":", 1)) == 2 and yaml_lines[i].split(":", 1)[1].strip() == "" \
        and yaml_lines[i-1] == "" :                                                                                  # если в строке до ":" только одно значение и предыдущая строка пустая (значит текущая является подсписком списка, в данном случае например создастся список значений дисциплины, который является подсписком списка дисциплин
            key, value = yaml_lines[i].split(": ", 1)
            d[key] = value
            s += f"<{key} name=\"{value}\">\n"
            s += yaml_list()
            s += f"</{key}>\n"


yaml_list()