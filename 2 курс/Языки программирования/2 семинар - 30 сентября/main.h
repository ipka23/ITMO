#ifndef INC_2____30__MAIN_H
#define INC_2____30__MAIN_H

typedef int (*intFunc2)(void* a, void* b);
typedef void (*voidFunc1)(void* item);
typedef void* (*ptrFunc2)(void* a, void* b);

typedef struct {
    void** items;
    int size;
    int capacity;
    intFunc2 (*comparer) (void* a, void* b);
    voidFunc1 (*dtor) (void* item);
} arrayList;

arrayList* newList(intFunc2 comparer, voidFunc1 dtor);
void addToList(arrayList* collection, void* item);
void removeFromList(arrayList* collection, void* item);
void* listTraverse(arrayList* collection, ptrFunc2* test);
void destroyList(arrayList* collection);


typedef struct  {
    char* title;
    char* author;
    char* annot;
    char* isbn;
    char* date;
} Book;

Book* newBook(char*, char*, char*, char*, char*);
void bookConstructor(Book* px, char* name, char* author, char* annot, char* isbn, char* date);
void destructor(void* item);
// int compare(void*, void*);
#endif //INC_2____30__MAIN_H