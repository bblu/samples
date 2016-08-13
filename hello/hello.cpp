#include <stdio.h>
#include <iostream>

//#define PRT(world)  puts(#Hello##world); 
//#define STR(H,W)   #H##W
int main()
{
	
	printf("1.Hello world!\n");
	printf("2.Hello %s!\n","world");
	std::cout << "3.Hello world!"<<std::endl;
	puts("4.Hello world!");
//	PRT(World);
//	std::cout << "5."<< STR(Hello,world)<< std::endl;
	return 0;
}
