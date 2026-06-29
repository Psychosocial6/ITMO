#define _CRT_SECURE_NO_WARNINGS
#include "collection.h"
#include <stdio.h>
#include <stdlib.h>

void createShoppingList(ShoppingList* shoppingList) {
    shoppingList->size = 0;
    shoppingList->first = NULL;
    shoppingList->last = NULL;
}

void addPurchase(ShoppingList* shoppingList, char* name, char* comment, double credits, char* date) {
    Purchase* purchase = createPurchase(name, comment, credits, date);

    if (shoppingList->size == 0) {
        shoppingList->size = 1;
        shoppingList->first = purchase;
        shoppingList->last = purchase;
    }
    else {
        shoppingList->size++;
        shoppingList->last->next = purchase;
        shoppingList->last = purchase;
    }
}

void clearShoppingList(ShoppingList* shoppingList) {
    Purchase* current = shoppingList->first;

    while (current != NULL) {
        Purchase* next = current->next;
        free(current->name);
        free(current->comment);
        free(current->date);
        free(current);
        current = next;
    }

    shoppingList->first = NULL;
    shoppingList->last = NULL;
    shoppingList->size = 0;
}

void deleteByIndex(ShoppingList* shoppingList, int index) {
    Purchase* deleted = NULL;

    if (shoppingList->size == 0 || shoppingList == NULL || shoppingList->first == NULL || index < 0 || shoppingList->size < index + 1) {
        printf("Error during deleting element. Index: %d \n", index);
        return;
    }
    if (index == 0) {
        deleted = shoppingList->first;
        shoppingList->first = shoppingList->first->next;
        if (shoppingList->first == NULL) {
            shoppingList->last = NULL;
        }
    }
    else {
        Purchase* previous = shoppingList->first;
        for (int i = 0; i < index - 1; i++) {
            previous = previous->next;
        }
        deleted = previous->next;
        previous->next = deleted->next;
        if (deleted == shoppingList->last) {
            shoppingList->last = previous;
        }
    }
    free(deleted->name);
    free(deleted->comment);
    free(deleted->date);
    free(deleted);

    shoppingList->size--;
}