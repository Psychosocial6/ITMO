import json
import yaml

def yaml_to_json(yaml_file, json_file):
    with open(yaml_file, "r", encoding="utf8") as yamlF, open(json_file, "w", encoding="utf8") as jsonF:
        yaml_object = yaml.safe_load(yamlF)
        #print(yaml_object)
        s = json.dumps(yaml_object)
        jsonF.write(s)

yaml_to_json("Schedule.yml", "Schedule.json")