#include <stdio.h>
#include <vector>

int main()
{
	std::vector<bool> vb;
	std::vector<int> vi;
	std::vector<char> vc;
	std::vector<short> vs;
		
	printf("vector of bool size=%lu\n",sizeof(vb));
	printf("vector of char size=%lu\n",sizeof(vc));
	printf("vector of short size=%lu\n",sizeof(vs));
	printf("vector of int size=%lu\n",sizeof(vi));
	for(int i=0;i<81*8;i++)
	{
		vb.push_back(i%2);
		if(!(i%20))
			printf("vector of bool[%d] size=%lu, so=%lu\n",i,vb.size(),sizeof(vb));
	
	}





}
