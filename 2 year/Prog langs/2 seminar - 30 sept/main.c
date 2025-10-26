#include <stdio.h>
#include "main.h"
#include <stdint.h>


// void addBook(arrayList* collection) {
//
// }
//
Book* getBookByISBN(arrayList* collection,  char* isbnCheck) {
    for (size_t i = 0; i < collection->size; i++) {
        Book* b = collection->items[i];
        if (b->isbn == isbnCheck) {
            return b;
        }
    }
    return NULL;
}
//
// void searchBook();

void runLibraryCatalog() {
    arrayList* bookCollection = newList(compareBooks, bookDestructor);
    for (;;) {
        printf("Enter the number of action and press [Enter]. Then follow instructions.\nMenu:\n1 Add book\n2 Get book by ISBN\n3 Search books\n4 Exit\n> ");
        int8_t input;
        scanf("%d", &input);
        if (input == 1) {
            char* title;
            char* author;
            char* annot;
            char* isbn;
            char* date;

            printf("New book\nTitle: ");
            scanf("%s", &title);
            printf("Author: ");
            scanf("%s", &author);
            printf("Annotation: ");
            scanf("%s", &annot);
            printf("ISBN: ");
            scanf("%s", &isbn);
            printf("Publication date: ");
            scanf("%s", &date);

            Book b = {title, author, annot, isbn, date};
            addToList(bookCollection, &b);

        } else if (input == 2) {
            char* isbn;
            printf("Enter ISBN: ");
            scanf("%s", &isbn);
            Book* book = getBookByISBN(bookCollection, isbn);
            if (book != NULL) {
                printf("Book information:\n");
                printf("Title: %s\n", book->title);
                printf("Author: %s\n", book->author);
                printf("Annotation: %s\n", book->annot);
                printf("ISBN: %s\n", book->isbn);
                printf("Publication date: %s\n", book->date);
            } else {
                printf("No book found with ISBN: %s\n", isbn);
            }
        } else if (input == 3) {

        } else if (input == 4) {
            break;
        } else {
            printf("Invalid input!\n");
        }
    }
}

int main(void) {
    runLibraryCatalog();
    return 0;
}
