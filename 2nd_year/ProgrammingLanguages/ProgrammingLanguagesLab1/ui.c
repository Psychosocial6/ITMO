#define _CRT_SECURE_NO_WARNINGS
#include "ui.h"
#include <stdio.h>
#include <string.h>

static void clearInputBuffer() {
    int c;
    while ((c = getchar()) != '\n' && c != EOF);
}

void printMenu() {
    printf("Available options: \n");
    printf("1. Add new purchase\n");
    printf("2. Remove purchase by its index\n");
    printf("3. Print purchases in a specified range of dates\n");
    printf("4. Print all purchases\n");
    printf("5. Exit\n");
    printf("Enter your choice: ");
}

void printShoppingList(ShoppingList* shoppingList) {
    Purchase* current = shoppingList->first;
    int counter = 1;

    printf("List size: %d \n", shoppingList->size);
    while (current != NULL) {
        printf("%d). Name: %s, Comment: %s, Credits: %lf, Date: %s \n", counter, current->name, current->comment, current->credits, current->date);
        counter++;
        current = current->next;
    }
}

void printShoppingListInDateRange(ShoppingList* shoppingList, char* startDate, char* endDate) {
    if (shoppingList == NULL || shoppingList->first == NULL) {
        printf("Error. List is not initialized.");
        return;
    }

    Purchase* current = shoppingList->first;
    int counter = 0;
    while (current != NULL) {
        if (strcmp(current->date, startDate) >= 0 && strcmp(current->date, endDate) <= 0) {
            if (counter == 0) {
                printf("Purchases in this range: \n");
            }
            counter++;
            printf("%d). Name: %s, Comment: %s, Credits: %lf, Date: %s \n", counter, current->name, current->comment, current->credits, current->date);
        }
        current = current->next;
    }
    if (counter == 0) {
        printf("No purchases in this range. \n");
    }
}

void startUI(ShoppingList* shoppingList) {
    int choice;
    char nameBuffer[100];
    char commentBuffer[200];
    char dateBuffer[12];
    double credits;
    int index_to_delete;
    char startDate[12], endDate[12];

    while (1) {
        printMenu();
        if (scanf("%d", &choice) != 1) {
            printf("Invalid input. Please enter a number.\n");
            clearInputBuffer();
            continue;
        }
        clearInputBuffer();

        switch (choice) {
        case 1:
            printf("Enter purchase name: ");
            fgets(nameBuffer, sizeof(nameBuffer), stdin);
            nameBuffer[strcspn(nameBuffer, "\n")] = '\0';

            printf("Enter a comment: ");
            fgets(commentBuffer, sizeof(commentBuffer), stdin);
            commentBuffer[strcspn(commentBuffer, "\n")] = '\0';

            printf("Enter credits spent: ");
            scanf("%lf", &credits);
            clearInputBuffer();

            printf("Enter date (format YYYY-MM-DD): ");
            fgets(dateBuffer, sizeof(dateBuffer), stdin);
            dateBuffer[strcspn(dateBuffer, "\n")] = '\0';

            addPurchase(shoppingList, nameBuffer, commentBuffer, credits, dateBuffer);
            printf("Purchase added!\n");
            break;

        case 2:
            if (shoppingList->size == 0) {
                printf("The list is empty. Nothing to remove.\n");
                break;
            }
            printf("Enter the index of the purchase to remove: ");
            scanf("%d", &index_to_delete);
            clearInputBuffer();
            deleteByIndex(shoppingList, index_to_delete);
            break;

        case 3:
            printf("Enter start date (YYYY-MM-DD): ");
            fgets(startDate, sizeof(startDate), stdin);
            startDate[strcspn(startDate, "\n")] = '\0';

            printf("Enter end date (YYYY-MM-DD): ");
            fgets(endDate, sizeof(endDate), stdin);
            endDate[strcspn(endDate, "\n")] = '\0';

            printShoppingListInDateRange(shoppingList, startDate, endDate);
            break;

        case 4:
            printShoppingList(shoppingList);
            break;

        case 5:
            printf("Exiting program. Freeing memory...\n");
            return;

        default:
            printf("Invalid choice. Please enter a number from 1 to 5.\n");
            break;
        }
    }
}