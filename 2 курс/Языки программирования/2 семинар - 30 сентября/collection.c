#include <stdlib.h>
#include "main.h"
#include <stdbool.h>
#include <string.h>


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

int compareBooks(void* pa, void* pb) {
    Book* a = (Book*) pa;
    Book* b = (Book*) pb;
    return strcmp(a->isbn, b->isbn);
}

arrayList* newList(int (*comparer)(void*, void*), void (*dtor)(void*)) {
    arrayList* list = malloc(sizeof(arrayList));
    list->items = (malloc(sizeof(void) * 8));
    list->size = 8;
    list->comparer = compareBooks;
    list->dtor = dtor;


}
