#ifndef INC_2____30__MAIN_H
#define INC_2____30__MAIN_H

typedef bool (*comparerFunc)(void* a, void* b);
typedef void (*destructorFunc)(void* item);
typedef bool (*ptrTraverseFunc)(void* item, void* param);

typedef struct {
    void** items;
    size_t size;
    size_t capacity;
    comparerFunc comparer;
    destructorFunc dtor;
} arrayList;

arrayList* newList(comparerFunc comparer, destructorFunc dtor);
bool addToList(arrayList* collection, void* item);
bool removeFromList(arrayList* collection, void* item); // comparerFunc* ?
// void* listTraverse(arrayList* collection, ptrTraverseFunc func); // ptrTraverseFunc* ?
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
void bookDestructor(void* item);
bool compareBooks(void*, void*);
#endif //INC_2____30__MAIN_H