message = input('Исходное сообщение: ')


def xor(x1, x2, x3, x4):
    return str(sum(map(int, x1 + x2 + x3 + x4)) % 2)


def S(s1, s2, s3):
    return int(s3 + s2 + s1, 2)


s1 = xor(message[0], message[2], message[4], message[6])
s2 = xor(message[1], message[2], message[5], message[6])
s3 = xor(message[3], message[4], message[5], message[6])


for i in range(0, 8):
    if S(s1, s2, s3) == 0:
        print('Сообщение передано без ошибок: ', message)
        print('Информационное сообщение:', message[2] + message[4:])
    elif S(s1, s2, s3) == i and message[i-1] == '1':
        message = message[:i-1] + '0' + message[i:]
        print('Ошибка в бите', S(s1, s2, s3))
        print('Исправленное сообщение:', message)
        print('Информационное сообщение:', message[2] + message[4:])
    elif S(s1, s2, s3) == i and message[i-1] == '0':
        message = message[:i-1] + '1' + message[i:]
        print('Ошибка в бите', S(s1, s2, s3))
        print('Исправленное сообщение:', message)
        print('Информационное сообщение:', message[2] + message[4:])