package ch2;
//when the thread run with each other it will lock itself!
public class LockOne implements Lock {
	private boolean[] flag = new boolean[2];
	
	@Override
	public void lock() {
		int i = (int)Thread.currentThread().getId();
		int j = 1 - i;
		flag[i] = true;
		while(flag[j]){} //wait
	}

	@Override
	public void unlock() {
		int i = (int)Thread.currentThread().getId();
		flag[i]=false;
	}

}
