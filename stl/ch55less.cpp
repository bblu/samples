#include <stdio.h>

template <typename T>
bool less(T x,T y)
{
	return x < y;
}



int main()
{

	
bool t = less<int>(1,2);

bool f = less<int>(2,1);

if(t and !f)
	printf("T\n");
else
	printf("F\n");

return 0;

}
