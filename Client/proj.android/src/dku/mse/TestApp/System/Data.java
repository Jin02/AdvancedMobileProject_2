package dku.mse.TestApp.System;

public class Data {
		
	int[] item;
	int gage;
	
	public Data(){
		item = new int[3];
		
	}
	
	private static Data single = new Data();
	private static Data getIns()
	{
		return single;
	}

}
