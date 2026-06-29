import matplotlib.pyplot as plt

data = {}
with open("ExtraTask2.csv", "r") as f:
    l = f.readlines()
    l.pop(0)
    for i in l:
        values = i.strip().split(";")
        if values[2] not in data:
            data[values[2]] = [[float(values[4])], [float(values[7])], [float(values[6])], [float(values[5])]] #open close low high
        else:
            data[values[2]][0].append(float(values[4]))
            data[values[2]][1].append(float(values[7]))
            data[values[2]][2].append(float(values[6]))
            data[values[2]][3].append(float(values[5]))
x = []
for i in data:
    for j in data[i]:
        x.append(j)
plt.boxplot(x)
plt.xticks([1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16], ["13.09.18 OPEN","13.09.18 CLOSE","13.09.18 MIN","13.09.18 MAX",
                                                      "15.10.18 OPEN","15.10.18 CLOSE","15.10.18 MIN","15.10.18 MAX",
                                                      "13.11.18 OPEN","13.11.18 CLOSE","13.11.18 MIN","13.11.18 MAX",
                                                      "13.12.18 OPEN","13.12.18 CLOSE","13.12.18 MIN","13.12.18 MAX"],rotation=30)

plt.grid()
plt.show()