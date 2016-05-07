#include <stdlib.h>
#include <stdio.h>
#include <string.h>

char *GetMemory()
{
    char p[]="hello world";
    return p;
}

void Test0()
{ 
    char *str=NULL;
    str = GetMemory();
    printf(str);
}

void GetMemory(char **p, int num){
    *p = (char*)malloc(num);
}

void Test1()
{
    char *str = NULL;
    GetMemory(&str,16);
    strcpy(str, "hello");
    printf(str);
}

void Test2()
{
    char *str= (char*)malloc(16);
    strcpy(str, "hello");
    free(str);
    if(str != NULL){
        strcpy(str, "world");
        printf(str);
    }
}

void GetMemory(char *p){
    p = (char*)malloc(16);
}

void Test3()
{
    char *str = NULL;
    GetMemory(str);
    strcpy(str,"hello world");
    printf(str);
}

int main()
{
    typedef void(*TestFun)();
    TestFun tf[]={Test0,Test1,Test2,Test3};
    for(int i=0;i< 4;i++)
    {
        printf("\nTest%i: ",i);
        try{ tf[i]();
        }catch(...)NULL;
    }
    char p = getchar();
    return 0;
}
// output
// Test0: �		//the memory is free when use
// Test1: hello		//memory leak
// Test2: world		//can not confirm what is in the memory
// Segmentation fault 	//program crush!

