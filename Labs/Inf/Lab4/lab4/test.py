s = ""
schedule_yaml = open('schedule.yml', mode="r", encoding='utf-8')
schedule_xml = open('schedule.xml', mode='a', encoding='utf-8')
lines = [line for line in schedule_yaml]
for i in range(len(lines)):
    line = lines[i]
    print(line)