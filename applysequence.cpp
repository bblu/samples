//C05:Gromit.h
//The techno-dog, Has member functions
//with various numbers of arguments.

#include <iostream>

class Gromit {
	int arf;
	int totalBarks;
public:
	Gromit(int arf = 1):arf(arf+1),totalBarks(0){}
	void speak(int){
		for(int i=0;i<arf;i++){
			std::cout << "arf!";
			++totalBarks;
		}
		std::cout << std::endl;
	}

	char eat(float) const {
		std::cout<<arf << "chomp!" << std::endl;
		return 'z';
	}
	
	int sleep(char,double) const {
		std::cout << arf<< "zzz..." << std::endl;
		return 0;
	}

	void sit() const {
		std::cout << arf << " sitting..." << std::endl;
	}
};

//:C05:ApplyGromit.cpp
// test ApplySequence.h
#include <cstddef>
#include <iostream>
#include <vector>
#include "applysequence.h"

using namespace std;

int main(){
	vector<Gromit*> dogs;
	for(size_t i = 0; i < 5; i++)
		dogs.push_back(new Gromit(i));

	apply(dogs, &Gromit::sit);
	apply(dogs, &Gromit::eat,1.23f);
}







