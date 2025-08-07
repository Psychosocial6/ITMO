import time
import Task1
import ExtraTask1
import ExtraTask2
import ExtraTask3

#1 задание
start_time = time.time()
for i in range(100):
    Task1.yaml_to_json("Schedule.yml", "Schedule.json")
end_time = time.time()
print("Основ. 1 ", (end_time - start_time), "seconds")

#1 доп задание
start_time = time.time()
for i in range(100):
    ExtraTask1.yaml_to_json("Schedule.yml", "Schedule.json")
end_time = time.time()
print("Доп. 1 ", (end_time - start_time), "seconds")

#2 доп задание
start_time = time.time()
for i in range(100):
    ExtraTask2.yaml_to_json("Schedule.yml", "Schedule.json")
end_time = time.time()
print("Доп. 2 ", (end_time - start_time), "seconds")

#3 доп задание
start_time = time.time()
for i in range(100):
    ExtraTask3.yaml_to_json("Schedule.yml", "Schedule.json")
end_time = time.time()
print("Доп. 3 ", (end_time - start_time), "seconds")

