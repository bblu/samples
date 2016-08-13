package ch2;

public class LockTwo implements Lock {
	private int victim;
	@Override
	public void lock() {
		int i = (int)Thread.currentThread().getId();
		i = victim;				//let the other go first
		while(victim == i){
								//wait
		}
	}

	@Override
	public void unlock() {
	}

}
