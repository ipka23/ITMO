import time
import required_task
import additional_task_1
import additional_task_2
import additional_task_3

start_time = time.perf_counter()
for i in range(1000):
    required_task.req_task()
end_time = time.perf_counter()
execution_time = end_time - start_time
print(f"Время выполнения required_task: {round(execution_time, 10)} секунд")



start_time = time.perf_counter()
for i in range(1000):
    additional_task_1.add_1()
end_time = time.perf_counter()
execution_time = end_time - start_time
print(f"Время выполнения additional_task_1: {round(execution_time, 10)} секунд")


start_time = time.perf_counter()
for i in range(1000):
    additional_task_2.add_2()
end_time = time.perf_counter()
execution_time = end_time - start_time
print(f"Время выполнения additional_task_2: {round(execution_time, 10)} секунд")


start_time = time.perf_counter()
for i in range(1000):
    additional_task_3.add_3()
end_time = time.perf_counter()
execution_time = end_time - start_time
print(f"Время выполнения additional_task_3: {round(execution_time, 10)} секунд")