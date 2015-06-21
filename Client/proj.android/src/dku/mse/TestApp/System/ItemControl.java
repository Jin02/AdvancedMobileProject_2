package dku.mse.TestApp.System;

import android.util.Log;

public class ItemControl {
	Item item1;
	Item item2;
	int[] out;
	
	public ItemControl(){
		item1 = new Item();
		item2 = new Item();
		out = new int[3];
	}
	
	public int[] obtainItem()
	{
		if(item1.getItem() == 0){
			item1.setItem();
		}
		else if(item2.getItem() == 0){
			item2.setItem();
		}
		
		out[1] = item1.getItem();
		out[2] = item2.getItem();
		
		return out;
	}
	
	public int[] useItem(int i)
	{
		if(i == 1)
		{
			out[0] = item1.getItem();
			item1.clear();
		}
		if(i == 2)
		{
			out[0] = item2.getItem();
			item2.clear();
		}
		
		out[1] = item1.getItem();
		out[2] = item2.getItem();
		
		return out;
	}
}
