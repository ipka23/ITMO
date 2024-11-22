import time
import subprocess

start_time = time.perf_counter()
for i in range(100):
    subprocess.run(["/usr/local/bin/python3.12", "required_task.py"])
end_time = time.perf_counter()
execution_time = end_time - start_time
print(f"Время выполнения required_task: {round(execution_time, 3)} секунд")



start_time = time.perf_counter()
for i in range(100):
    subprocess.run(["/usr/local/bin/python3.12", "additional_task_1.py"])
end_time = time.perf_counter()
execution_time = end_time - start_time
print(f"Время выполнения additional_task_1: {round(execution_time, 3)} секунд")


start_time = time.perf_counter()
for i in range(100):
    subprocess.run(["/usr/local/bin/python3.12", "additonal_task_2.py"])
end_time = time.perf_counter()
execution_time = end_time - start_time
print(f"Время выполнения additonal_task_2: {round(execution_time, 3)} секунд")


start_time = time.perf_counter()
for i in range(100):
    subprocess.run(["/usr/local/bin/python3.12", "additional_task_3.py"])
end_time = time.perf_counter()
execution_time = end_time - start_time
print(f"Время выполнения additional_task_3: {round(execution_time, 3)} секунд")