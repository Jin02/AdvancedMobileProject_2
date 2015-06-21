package dku.mse.TestApp.System;

public class SystemData {
	
	private static SystemData single = new SystemData();
	public static SystemData getIns()
	{
		return single;
	}
	
	public int[] item;
	public boolean bluetooth;
	public boolean sendFlag;
	public boolean booster;
	public boolean brake;
	public boolean leftitem;
	public boolean rightitem;
	public int maxSpeed;
	public int mSpeed;
	public int gage;
	public float x;
	public int time;
	public int item_count;
	
	public void init(){
		item = new int[3];
		bluetooth = false;
		sendFlag = false;
		booster = false;
		brake = false;
		leftitem = false;
		rightitem = false;
		maxSpeed = 0;
		mSpeed = 0;
		gage = 0;
		x = 0;
		time = 0;
		item_count = 0;
		
	}
}
