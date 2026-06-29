#define _CRT_SECURE_NO_WARNINGS
#include "domain.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

Purchase* createPurchase(char* name, char* comment, double credits, char* date) {
    Purchase* newPurchase = malloc(sizeof(Purchase));

    newPurchase->name = malloc(strlen(name) + 1);
    newPurchase->comment = malloc(strlen(comment) + 1);
    newPurchase->date = malloc(strlen(date) + 1);

    strcpy(newPurchase->name, name);
    strcpy(newPurchase->comment, comment);
    strcpy(newPurchase->date, date);
    newPurchase->credits = credits;
    newPurchase->next = NULL;

    return newPurchase;
}