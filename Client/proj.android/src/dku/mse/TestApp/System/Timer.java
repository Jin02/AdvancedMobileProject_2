package dku.mse.TestApp.System;

public class Timer {

	private int count;
	
	public Timer(){
		count = 0;
	}
	
	public void increaseCount(){
		count++;
	}
	
	public int getCount(){
		return count;
	}
	
	public void resetCount(){
		count = 0;
	}
}
