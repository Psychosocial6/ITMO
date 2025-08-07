def yaml_to_csv(yaml_file, csv_file):
    with open(yaml_file, "r", encoding="utf8") as f:
        data = f.readlines()
    csv_data = {}
    for string in data:
        key, value = string.strip().split(":", 1)
        value.strip()
        if value != "":
            if key not in csv_data:
                csv_data[key] = [value]
            else:
                csv_data[key].append(value)

    with open(csv_file, "w", encoding="utf8") as f:
        for key in csv_data:
            string = key + ";"
            for value in csv_data[key]:
                value = value[1:]
                if value != "":
                    if value[0] == "[" and value[-1] == "]":
                        value = value[1:-1]
                        s = value.split(", ")
                        for i in s:
                            string += i + ";"
                    else:
                        string += value + ";"
            string = string[:-1] + "\n"
            f.write(string)


yaml_to_csv("Schedule.yml", "Schedule.csv")
