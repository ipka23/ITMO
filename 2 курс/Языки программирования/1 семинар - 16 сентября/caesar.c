#include <stdio.h>
#include <stdlib.h>
#define ENG 26
void encrypt(char* text, int key){
    for (int i = 0; text[i] != '\0'; i++) {
        char c = text[i];
        if (c >= 'A' && c <= 'Z') {
            c = c + (key % ENG);
        }
        if (c >= 'a' && c <= 'z') {
            c = c + (key % ENG);
        }
        text[i] = c;
    }
}
void decrypt(char* text, int key){
    for (int i = 0; text[i] != '\0'; i++) {
        char c = text[i];
        if (c >= 'A' && c <= 'Z') {
            c = c - (key % ENG);
        }
        if (c >= 'a' && c <= 'z') {
            c = c - (key % ENG);
        }
        text[i] = c;
    }
}

int main(int argc, char* argv[]) {
    char* text = argv[1];
    int key = atoi(argv[2]);
    printf("Исходный текст: %s\n", text);
    printf("Ключ: %d\n", key);
    encrypt(text, key);
    printf("Зашифрованный текст: %s\n", text);
    decrypt(text, key);
    printf("Расшифрованный текст: %s\n", text);
    return 0;
}