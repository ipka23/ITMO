opening_tags = []
closing_tags = []
s = ""
schedule_yaml = open('schedule.yml', mode="r", encoding='utf-8')
schedule_xml = open('schedule.xml', mode='a', encoding='utf-8')
lines = [line.rstrip() for line in schedule_yaml]
for i in range(len(lines)):
    line = lines[i]
    if ":" in line:
        line_split = line.split(":", maxsplit=1)
        if line_split[1] == '' and line_split[0][0] != " ":
            line = f'<{line_split[0].rstrip().replace(" ", "*")}>'
            opening_tags.append(f'<{line_split[0].replace("-", "").strip()}>')
        if line_split[1] == '' and line_split[0][0] == " ":
            line = f'{line_split[0].rstrip().replace(" ", "*")}>'
            opening_tags.append(f'<{line_split[0].replace("-", "").strip()}>')
        if line_split[1] == '' and line_split[0].strip()[0] == "-":
            line = f'{line_split[0].rstrip().replace("-", "", 1).replace(" ", "*")}>'
            opening_tags.append(f'<{line_split[0].replace("-", "").strip().strip()}>')
        elif line_split[1] != '' and line_split[0].strip()[0] == "-":
            line = f'{line_split[0].rstrip().replace("-", "").replace(" ", "*")}>{line_split[1].strip()}</{line_split[0].replace("-", "").strip()}>'
            opening_tags.append(f'<{line_split[0].replace("-", "").strip()}>')
            closing_tags.append(f'</{line_split[0].replace("-", "").strip()}>')
        elif line_split[1] != '' and line_split[1][-1] == "\"":
            line = f'{line_split[0].rstrip().replace(" ", "*")}>{line_split[1][2:-1]}</{line_split[0].strip()}>'
            opening_tags.append(f'<{line_split[0].strip()}>')
            closing_tags.append(f'</{line_split[0].strip()}>')
        elif line_split[1] != '':
            line = f'{line_split[0].rstrip().replace(" ", "*")}>{line_split[1].strip()}</{line_split[0].strip()}>'
            opening_tags.append(f'<{line_split[0].strip()}>')
            closing_tags.append(f'</{line_split[0].strip()}>')
        for j in range(len(line)):
            if j < len(line) - 1 and line[j] == "*" and line[j+1] != "*": ################### j < len(line) - 1
                line = line[:j+1].replace("*", " ") + line[j:].replace("*", "<")

        space_count = len(line) - len(line.lstrip())
        d = {space_count: line}
        print(line, space_count)
for tag in opening_tags:
    if "</" + tag[1:] in closing_tags:
        opening_tags.remove(tag)
    else:
        close_tag = "</" + tag[1:]

        for k in range(len(lines)):
            l = d.get(k)
        #     print(f'k = {k}, d = {d}, l = {l}')
        # print(d.get(0))

    break