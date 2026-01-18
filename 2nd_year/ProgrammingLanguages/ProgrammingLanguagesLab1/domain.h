#ifndef DOMAIN_H
#define DOMAIN_H

typedef struct Purchase {
    char* name;
    char* comment;
    double credits;
    char* date;
    struct Purchase* next;
} Purchase;

typedef struct {
    int size;
    struct Purchase* first;
    struct Purchase* last;
} ShoppingList;

Purchase* createPurchase(char* name, char* comment, double credits, char* date);

#endif