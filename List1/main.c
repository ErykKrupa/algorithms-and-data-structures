#include <stdio.h>
#include <malloc.h>
#include <stdlib.h>
#include <time.h>

int counter;

struct node {
    int number;
    struct node *pointer;
};

struct list {
    struct node *first_node;
};

int is_empty(struct list *list) {
    return list->first_node == NULL;
}

void insert(struct list *list, int element) {
    struct node *node = malloc(sizeof(*node));
    node->number = element;
    node->pointer = NULL;
    if (is_empty(list) == 1) {
        list->first_node = node;
    } else {
        struct node *tmp_pointer = list->first_node;
        while (tmp_pointer->pointer != NULL) {
            tmp_pointer = tmp_pointer->pointer;
        }
        tmp_pointer->pointer = node;
    }
}

void delete(struct list *list, int element) {
    if (is_empty(list) == 1) {
        return;
    }
    struct node *tmp_pointer = list->first_node;
    counter++;
    if (list->first_node->number == element) {
        struct node *tmp = list->first_node;
        list->first_node = list->first_node->pointer;
        free(tmp);
        return;
    }
    while (1) {
        if (tmp_pointer->pointer == NULL) {
            return;
        }
        counter++;
        if (tmp_pointer->pointer->number == element) {
            struct node *tmp = tmp_pointer->pointer;
            tmp_pointer->pointer = tmp_pointer->pointer->pointer;
            free(tmp);
            return;
        }
        tmp_pointer = tmp_pointer->pointer;
    }
}

int findMTF(struct list *list, int element) {
    if (is_empty(list) == 1) {
        return 0;
    }
    counter++;
    if (list->first_node->number == element) {
        return 1;
    }
    struct node *tmp_pointer = list->first_node;
    while (1) {
        if (tmp_pointer->pointer == NULL) {
            return 0;
        }
        counter++;
        if (tmp_pointer->pointer->number == element) {
            struct node *tmp = tmp_pointer->pointer;
            tmp_pointer->pointer = tmp_pointer->pointer->pointer;
            tmp->pointer = list->first_node;
            list->first_node = tmp;
            return 1;
        }
        tmp_pointer = tmp_pointer->pointer;
    }
}

int findTRANS(struct list *list, int element) {
    if (is_empty(list) == 1) {
        return 0;
    }
    counter++;
    if (list->first_node->number == element) {
        return 1;
    }
    if (list->first_node->pointer == NULL) {
        return 0;
    }
    counter++;
    if (list->first_node->pointer->number == element) {
        struct node *tmp = list->first_node;
        list->first_node = list->first_node->pointer;
        tmp->pointer = tmp->pointer->pointer;
        list->first_node->pointer = tmp;
        return 1;
    }
    struct node *tmp_pointer = list->first_node;
    while (1) {
        if (tmp_pointer->pointer->pointer == NULL) {
            return 0;
        }
        counter++;
        if (tmp_pointer->pointer->pointer->number == element) {
            struct node *tmp = tmp_pointer->pointer;
            tmp_pointer->pointer = tmp_pointer->pointer->pointer;
            tmp->pointer = tmp->pointer->pointer;
            tmp_pointer->pointer->pointer = tmp;
            return 1;
        }
        tmp_pointer = tmp_pointer->pointer;
    }
}

void print_list(struct list *list) {
    struct node *pointer = list->first_node;
    while (pointer != NULL) {
        printf("%d ", pointer->number);
        pointer = pointer->pointer;
    }
}

void fill_list(struct list *list) {
    int *array;
    array = malloc(100 * sizeof(*array));
    for (int i = 0; i < 100; i++) {
        array[i] = i + 1;
    }
    for (int i = 0; i < 500; i++) {
        int random1 = rand() % 100;
        int random2 = rand() % 100;
        int tmp = *(array + random1);
        *(array + random1) = *(array + random2);
        *(array + random2) = tmp;
    }
    for (int i = 0; i < 100; i++) {
        insert(list, array[i]);
    }
    free(array);

}

int main() {
    struct list list;
    list.first_node = NULL;
    srand(time(NULL));

    counter = 0;
    fill_list(&list);
    while (!is_empty(&list)) {
        int max_element = 0;
        for (int i = 1; i <= 100; i++) {
            if (findMTF(&list, i) && i > max_element) {
                max_element = i;
            }
        }
        delete(&list, max_element);
    }
    printf("Amount of comparisons for findMTF: %d\n", counter);

    counter = 0;
    fill_list(&list);
    while (!is_empty(&list)) {
        int max_element = 0;
        for (int i = 1; i <= 100; i++) {
            if (findTRANS(&list, i) && i > max_element) {
                max_element = i;
            }
        }
        delete(&list, max_element);
    }
    printf("Amount of comparisons for findTRANS: %d\n", counter);
    return 0;
}