#ifndef COLLECTION_H
#define COLLECTION_H

#include "domain.h"

void createShoppingList(ShoppingList* shoppingList);

void addPurchase(ShoppingList* shoppingList, char* name, char* comment, double credits, char* date);

void deleteByIndex(ShoppingList* shoppingList, int index);

void clearShoppingList(ShoppingList* shoppingList);

#endif