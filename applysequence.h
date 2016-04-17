//:C05:ApplySequence.h
// Apply a function to an stl sequence container.

//const. 0 arguments, any type of return value:
template<class Seq, class T, class R>
void apply(Seq& sq,R(T::*f)() const) {
	typename Seq::iterator it = sq.begin();
	while(it != sq.end())
		((*it++)->*f)();
}

//Non-const, 1 arguments, any type of return value:
template<class Seq, class T, class R>
void apply(Seq& sq,R(T::*f)()) {
	typename Seq::iterator it = sq.begin();
	while(it != sq.end())
		((*it++)->*f)();
}


template<class Seq, class T, class R, class A>
void apply(Seq &sq,R(T::*f)(A) const,A a) {
	typename Seq::iterator it = sq.begin();
	while(it != sq.end())
		((*it++)->*f)(a);
}

