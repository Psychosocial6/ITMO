#из Фибоначчиевой системы счисления в десятичную
def fib(n): #генерация массива из n чисел Фибоначчи
    f1, f2 = 1, 2 #1-е и 2-е числа Фибоначчи
    if n == 1:
        return [f1]
    elif n == 2:
        return [f1, f2]
    else:
        x = [f1, f2]
        for i in range(n - 2):
            f1, f2 = f2, f1 + f2
            x.append(f2)
        return x
    
def fibToDec(s): #перевод из Фибоначчиевой с.с. в десятичную
    result = 0 #результат перевода
    nums = fib(len(s))
    nums.reverse()
    for i in range(len(s)): #перевод
        if int(s[i]):
            result += nums[i]
    return result

s = input()
print(fibToDec(s))
