yml = '''
days:
  - day: Понедельник
    disciplines:
      - discipline: Основы профессиональной деятельности
        teacher: Остапенко Ольга Денисовна
        building: Кронверкский пр., д.49, лит.А
        classroom: "2308"
        lessons:
          - lesson: Лабораторная
            time: "8:20-9:50"
          - lesson: Лабораторная
            time: "9:50-11:20"
      - discipline: Дискретная математика
        teacher: Поляков Владимир Иванович
        building: Кронверкский пр., д.49, лит.А
        classroom: "2403"
        lessons:
          - lesson: Лекция
            time: "11:40-13:10"
          - lesson: Практика
            time: "13:30-15:00"
  - day: Четверг
'''

def yaml(a: str) -> dict:
    # Split the input, first by each line
    lines = a.split("\n")
    # Create an empty dict
    d = {}
    # Split the input, by colon
    for line in lines:
        if line != "": # Looking for extra \n's which result in empty lines
            key = f"{line.split(':')[0]}"
            val = f"{line.split(':')[1]}"
        else:
            continue
        # Clean up whitespace around the value
        val = val.lstrip()
        val = val.rstrip()

        # If value is detected as integer, typecast it from string to int
        if val.isnumeric(): val = int(val)

        # Produce a dictionary
        d[key] = val

    return d

res = yaml("name: John\nage: 30\nheight: 180")
print(res)