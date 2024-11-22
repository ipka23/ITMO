import yaml
import csv

with open('schedule.yml', 'r', encoding='utf-8') as yaml_file:
    yaml_dict = yaml.safe_load(yaml_file)

def to_csv(yaml_dict):
    rows = []
    for week_data in yaml_dict.get('schedule', {}).get('weeks', []):
        week = week_data.get('week', '')

        for day_data in week_data.get('days', []):
            day = day_data.get('day', '')

            for discipline_data in day_data.get('disciplines', []):
                discipline = discipline_data.get('discipline', '')
                teacher = discipline_data.get('teacher', '')
                building = discipline_data.get('building', '')
                classroom = discipline_data.get('classroom', '')

                for lesson_data in discipline_data.get('lessons', []):
                    lesson = lesson_data.get('lesson', '')
                    time = lesson_data.get('time', '')

                    rows.append([week, day, discipline, teacher, building, classroom, lesson, time])
    return rows


rows = to_csv(yaml_dict)

with open('schedule.csv', 'w', newline='', encoding='utf-8') as file:
    writer = csv.writer(file)

    writer.writerow(['Week', 'Day', 'Discipline', 'Teacher', 'Building', 'Classroom', 'Lesson', 'Time'])

    writer.writerows(rows)