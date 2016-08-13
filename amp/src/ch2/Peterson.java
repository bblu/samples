//make add of one and two we get three method Peterson Lock by who made it
package ch2;

public class Peterson implements Lock {
	private boolean[] flag = new boolean[2];
	private int victim;
	@Override
	public void lock() {
		int i = (int)Thread.currentThread().getId();
		int j = 1 - i;
		flag[i] = true;
		victim = i;
		while(flag[j] && victim == i){
												//wait
		}
		
	}

	@Override
	public void unlock() {
		int i = (int)Thread.currentThread().getId();
		flag[i]=false;
	}

}
