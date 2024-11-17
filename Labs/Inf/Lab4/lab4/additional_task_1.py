import yaml
import dict2xml

yaml_file = open('-schedule-.yml', 'r', encoding="utf-8")
yaml_dict = yaml.safe_load(yaml_file)
print(yaml_dict)
with open('schedule.xml', 'w', encoding="utf-8") as xml_file:
    result = dict2xml.dict2xml(yaml_dict)
    xml_file.write(result)