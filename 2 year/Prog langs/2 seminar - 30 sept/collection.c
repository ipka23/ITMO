#include <stdlib.h>
#include "main.h"
#include <string.h>
#include <stdint.h>


void bookConstructor(Book* px, char* title, char* author, char* annot, char* isbn, char* date) {
    if (px == NULL) return;
    px->title = title;
    px->author = author;
    px->annot = annot;
    px->isbn = isbn;
    px->date = date;
}

void bookDestructor(void* px) {
    Book* x = (Book*) px;
    free(x->title);
    free(x->author);
    free(x->annot);
    free(x->isbn);
    free(x->date);
    free(x);
}

Book* newBook(char* title, char* author, char* annot, char* isbn, char* date) {
    Book* px = (Book*) malloc(sizeof(Book));
    bookConstructor(px, title, author, annot, isbn, date);
    return px;
}

bool compareBooks(void* ptra, void* ptrb) {
    Book* a = (Book*) ptra;
    Book* b = (Book*) ptrb;
    return strcmp(a->isbn, b->isbn) == 0;
}

arrayList* newList(comparerFunc comparer, destructorFunc dtor) {
    arrayList* list = (arrayList*) malloc(sizeof(arrayList));
    if (list == NULL) return NULL;

    list->items = malloc(sizeof(void*) * 8); // (Book*)
    if (list->items == NULL) {
        free(list);
        return NULL;
    }

    list->size = 0;
    list->capacity = 8;
    list->comparer = comparer;
    list->dtor = dtor;
    return list;
}

bool addToList(arrayList* collection, void* item) {
    if (collection == NULL || item == NULL) return false;

    if (collection->size >= collection->capacity) {
        size_t newCapacity = collection->capacity * 2;

        void** newItems = realloc(collection->items, sizeof(void*) * newCapacity);

        if (newItems == NULL) return false;

        collection->items = newItems;
        collection->capacity = newCapacity;
    }

    collection->items[collection->size] = item;
    collection->size++;
    return true;
}


bool removeFromList(arrayList* collection, void* item) {
    if (collection == NULL || item == NULL || collection->comparer == NULL) return false;

    comparerFunc compare = collection->comparer;
    for (size_t i = 0; i < collection->size; i++) {
        void* current = collection->items[i];
        if (compare(current, item)) {
            for (size_t j = i; j < collection->size - 1; j++) {
                collection->dtor(current);
                collection->items[j] = collection->items[j+1];
            }
            collection->size--;
            return true;
        }
    }
    return false;
}



// void* listTraverse(arrayList* collection, ptrTraverseFunc func) {
//     for (size_t i = 0; i < collection->size; i++) {
//         void* item = collection->items[i];
//         bool flag = func(item, );
//         if (flag) return item;
//     }
//     return NULL;
// }


void destroyList(arrayList* collection) {
    if (collection == NULL || collection->dtor == NULL) return;
    for (size_t i = 0; i < collection->size; i++) {
        collection->dtor(collection->items[i]);
    }
    free(collection->items);
    free(collection);
}