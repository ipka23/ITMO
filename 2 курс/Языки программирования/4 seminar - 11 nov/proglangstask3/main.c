#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#include <string.h>

#include <sys/ipc.h>

#include <sys/shm.h>

void* threadFunc(void* thread_data)
{
    int shared = shmget(100, 1024, 0666 | IPC_CREAT);
    if (shared == -1)
    {
        perror("shmget");
        exit(1);
    }

    char* shmaddr = shmat(shared, NULL, 0);
    if (shmaddr == (char*) -1)

    {
        perror("shmat");
        exit(1);
    }
    for (;;)
    {
        char* message;
        scanf("%s", &message);
        if (message == "") continue;
        strcpy(shmaddr, message);
    }

    //завершаем поток
    pthread_exit(0);
}

int main()
{
    //какие то данные для потока (для примера)
    void* thread_data = NULL;

    //создаем идентификатор потока
    pthread_t thread;


    //создаем поток по идентификатору thread и функции потока threadFunc
    //и передаем потоку указатель на данные thread_data
    pthread_create(&thread, NULL, threadFunc, thread_data);

    //ждем завершения потока
    pthread_join(thread, NULL);

    return 0;
}
