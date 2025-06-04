from time import perf_counter

import yaml
import dict2xml
import time



def add_1():
    start_time = time.perf_counter()
    perf_counter()
    yaml_file = input()
    yaml_dict = yaml.safe_load(yaml_file)
    result = dict2xml.dict2xml(yaml_dict)
    end_time = time.perf_counter()
    execution_time = end_time - start_time
    print(result)

add_1()