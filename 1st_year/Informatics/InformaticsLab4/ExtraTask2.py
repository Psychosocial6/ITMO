import re
#вариант 21
#YAML -> JSON вторник, четверг
def yaml_to_json(yaml_file, json_file):
    with open(yaml_file, "r", encoding="utf8") as f:
        file = f.readlines()
    data = []
    for i in range(len(file)):
        s = file[i]
        key, value = re.split(r':', s, maxsplit=1)[0], re.split(r':', s, maxsplit=1)[1]
        #print(key, value)
        data.append([key, value, key.count(" ")])

    spaces = 0
    json_string = "{"
    for i in data:
        key, value, sp = i[0].strip(), i[1].strip(), i[2]
        if sp < spaces:
            # удаление лишней запятой
            ind = json_string.rfind(",")
            json_string = json_string[:ind] + json_string[ind + 1:]
            # закрытие скобки
            if spaces - sp == 2:
                json_string += "}, "
            else:
                json_string += "} " * ((spaces - sp) // 2 - 1) + "}, "
        spaces = sp
        if re.fullmatch(r'', value):
            json_string += '"' + key + '": ' "{ "
        elif re.fullmatch(r'-?[0-9]+', value) or re.fullmatch(r'\[.*\]', value): #число или массив
            json_string += '"' + key + '": ' + value + ", "
        else:
            json_string+= '"' + key + '": ' + '"' + value + '", '

    if spaces > 0:
        ind = json_string.rfind(",")
        json_string = json_string[:ind] + json_string[ind + 1:]
        json_string += "} " * (spaces // 2)
    else:
        json_string += " "
    json_string += "}"

    with open(json_file, "w", encoding="utf8") as f1:
        f1.write(json_string)
    return

yaml_to_json("Schedule.yml", "Schedule.json")