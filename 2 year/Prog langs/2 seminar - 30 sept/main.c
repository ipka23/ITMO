#include <stdio.h>
#include "main.h"
#include <stdint.h>
#include <string.h>

bool startsWith(const char *pre, const char *str) {
    return strncmp(pre, str, strlen(pre)) == 0;
}

Book*   getBookByISBN(arrayList* collection,  char* isbnCheck) {
    if (isbnCheck == NULL) return NULL;

    for (size_t i = 0; i < collection->size; i++) {
        Book* b = collection->items[i];
        if (b->isbn[0] == isbnCheck[0]) {
            return b;
        }
        if (strcmp(b->isbn, isbnCheck) == 0) {
            return b;
        }
    }
    return NULL;
}

arrayList* searchBooksByKeyWord(arrayList* collection, char* keyWord) {
    arrayList* matchList = newList(compareBooks, bookDestructor);
    for (size_t i = 0; i < collection->size; i++) {
        Book* b = collection->items[i];
        bool match = startsWith(keyWord, b->title) || startsWith(keyWord, b->author) || startsWith(keyWord, b->annot) || startsWith(keyWord, b->isbn) || startsWith(keyWord, b->date);
        if (match) {
            addToList(matchList, b);
        }
    }
    return matchList;
}

void runLibraryCatalog() {
    arrayList* bookCollection = newList(compareBooks, bookDestructor);
    for (;;) {
        setbuf(stdout, 0);
        printf("Enter the number of action and press [Enter]. Then follow instructions.\nMenu:\n1 Add book\n2 Get book by ISBN\n3 Search books\n4 Exit\n> ");
        int8_t input;
        scanf("%d", &input);
        // printf("input: %d\n", input); //
        if (input == 1) {
            char title[20];
            char author[20];
            char annot[20];
            char isbn[20];
            char date[20];

            printf("New book\nTitle: ");
            scanf("%s", title);
            printf("Author: ");
            scanf("%s", author);
            printf("Annotation: ");
            scanf("%s", annot);
            printf("ISBN: ");
            scanf("%s", isbn);
            printf("Publication date: ");
            scanf("%s", date);

            // printf("isbn: %s\n", isbn); //
            Book* b = newBook(strdup(title), strdup(author), strdup(annot), strdup(isbn), strdup(date));
            addToList(bookCollection, b);

        } else if (input == 2) {
            char isbn[13];
            printf("Enter ISBN: ");
            scanf("%s", isbn);
            // printf("Entered isbn: %s\n", &isbn); //

            Book* book = getBookByISBN(bookCollection, isbn);
            if (book != NULL) {
                printf("Book information:\n");
                printf("Title: %s\n", book->title);
                printf("Author: %s\n", book->author);
                printf("Annotation: %s\n", book->annot);
                printf("ISBN: %s\n", book->isbn);
                printf("Publication date: %s\n\n", book->date);
            } else {
                printf("No book found with ISBN: %s\n\n", isbn);
            }
        } else if (input == 3) {
            char request[20];
            printf("Request: ");
            scanf("%s", request);
            printf("Searching...\n");
            arrayList* matches = searchBooksByKeyWord(bookCollection, request);
            printf("Results (%zu) :\n", matches->size);

            if (matches->size == 0) {
                printf("No results for request: %s!", request);
                continue;
            }
            for (size_t i = 0; i < matches->size; i++) {
                printf("#%zu    ", i + 1);
                Book* b = matches->items[i];
                printf("Book information:\n");
                printf("Title: %s\n", b->title);
                printf("Author: %s\n", b->author);
                printf("Annotation: %s\n", b->annot);
                printf("ISBN: %s\n", b->isbn);
                printf("Publication date: %s\n\n", b->date);
            }
        } else if (input == 4) {
            break;
        } else {
            printf("Invalid input!\n\n");
        }
    }
}

int main(void) {
    runLibraryCatalog();
    return 0;
}
