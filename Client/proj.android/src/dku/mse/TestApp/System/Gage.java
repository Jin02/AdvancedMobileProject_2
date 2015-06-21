package dku.mse.TestApp.System;
public class Gage{
	private int gage;
	private boolean flag;//�ν��� ������� �ƴ���(���:true)
	Timer gTimer;//timer for gage
	
	public Gage(){
		gage = 0;
		flag = false;
		gTimer = new Timer();
	}
	
	public int getGuage(){
		return gage;
	}
	
	public void increaseGage(){
		gage+=5;
	}
	
	public void decreaseGage(){
		gage-=15;
	}

	public boolean getFlag(){
		return flag;
	}
	public void setFlagTrue(){//�ν��� ���
		flag = true;
		gTimer.resetCount();
	}
	public void setFlagFalse(){
		flag = false;
	}
	
	public int gageControl(){
		gTimer.increaseCount();
		if(flag==false && gage<100)
		{
			if(gTimer.getCount()==30)
			{
				increaseGage();
				gTimer.resetCount();
			}
		}
		else if(getFlag()==true && getGuage()>=15)
		{
			if(gTimer.getCount()==10)
			{
				gTimer.resetCount();
				decreaseGage();
			}
		}
		return gage;
	}
}
