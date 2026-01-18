#define _CRT_SECURE_NO_WARNINGS
#include "collection.h"
#include "ui.h"
#include <stdio.h>

int main() {
    ShoppingList shoppingList;
    createShoppingList(&shoppingList);

    startUI(&shoppingList);

    clearShoppingList(&shoppingList);

    return 0;
}