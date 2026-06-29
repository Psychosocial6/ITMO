s = input()
nums = []
for i in s:
    nums.append(int(i))
s1 = (nums[0] + nums[2] + nums[4] + nums[6]) % 2
s2 = (nums[1] + nums[2] + nums[5] + nums[6]) % 2
s3 = (nums[3] + nums[4] + nums[5] + nums[6]) % 2
syndromes = ["100", "010", "110", "001", "101", "011", "111"]
bits = ["r1", "r2", "i1", "r3", "i2", "i3", "i4"]
syndrome = str(s1) + str(s2) + str(s3)
if syndrome == "000":
    print("Нет ошибок")
    exit()
else:
    ind = syndromes.index(syndrome)
    if s[ind] == "0":
        correct = "1"
    else:
        correct = "0"
    print("Ошибка в бите " + bits[ind] + ", корректное сообщение: " + s[:ind] + correct + s[ind + 1:])
