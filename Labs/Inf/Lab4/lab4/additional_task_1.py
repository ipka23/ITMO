import yaml
import dict2xml
def add_1():
    yaml_file = input()
    yaml_dict = yaml.safe_load(yaml_file)
    result = dict2xml.dict2xml(yaml_dict)
    print(result)

add_1()