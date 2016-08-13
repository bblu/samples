package ch2;

public class Counter {
	private long value;
	private Lock lock;
	
	public long getAndIncrement(){
		lock.lock();
		try{
			long temp = value;
			value = temp + 1;
			return temp;
		}finally{
			lock.unlock();
		}
		
	}
}
