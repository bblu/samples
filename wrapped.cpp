//C01:Wrapped.cpp
//safe, atomic pointers
//《c++ 编程思想》
#include <iostream>
#include <cstddef>

using namespace std;

template<class T, int sz=1> class PWrap{
	T* ptr;
public:
	class RangeError{};
	PWrap(){
		ptr = new T[sz];
		cout << "PWrap constructor" << endl;
	}
	~PWrap(){
		delete[] ptr;
		cout << "PWrap destructor" << endl;
	}
	T& operator[](int i)throw(RangeError){
		if(i >= 0 && i < sz) return ptr[i];
		throw RangeError();
	}
};

class Cat {
public:
	Cat(){ cout << "cat()" << endl;}
	~Cat(){ cout << "~cat()" << endl;}
	void g(){}
};

class Dog{
public:
	void* operator new[](size_t){
		cout << "Allocating a dog" << endl;
		throw 47;
	}
	void operator delete[](void* p) {
		cout << "Deallocationg a dog" << endl;
		::operator delete[](p);
	}
};

class UseResources{
	PWrap<Cat, 3> cats;
	PWrap<Dog> dog;
public:
	UseResources() { cout << "UseResources()" << endl;}
	~UseResources(){ cout << "~UseResources()"<< endl;}
	void f() { cats[1].g();}
};

int main(){
try{
	UseResources ur;
}catch(int){
	cout << "inside handler" << endl;
}catch(...){
	cout << "insice catch(...)" << endl;
}
}


	
		
		
