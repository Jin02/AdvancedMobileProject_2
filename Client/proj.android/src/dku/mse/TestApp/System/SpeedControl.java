package dku.mse.TestApp.System;

public class SpeedControl{

	private int s[] = {0, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
	private int r_speed;
	private int l_speed;
	private int max; //���� ���¿��� max��11�̰� ���ǵ� �������� ���ų� �ν��͸� �� ��� 2�� ������.
	private int level; //speed level
	private int g;//gage �� ����
	private int spd;//���ǵ� ������ �ð� üũ�� ���
	private boolean spd_item;//���ǵ� ������ ��뿩�� üũ
	private boolean brk;//brake
	
	Timer sTimer;//timer for speed
	public Gage gage;
	
	public SpeedControl(){
		r_speed = 0;
		l_speed = 0;
		level = 0;
		spd = 0;
		g = 0;
		max = 11;
		brk = false;
		spd_item = false;
		
		sTimer = new Timer();
		gage = new Gage();
	}
	
	public int speedControl(int x){
		g = gage.gageControl();
		sTimer.increaseCount();
		
		if(sTimer.getCount()%5==0)
		{
			if(spd_item == true)
			{
				spd++;
				if(spd==6)
				{
					finishSpeedItem();
				}
			}
			if(brk == false)
			{
				speedAccel(x);
			}
			else
			{
				brake();
			}
		}
		
		if(gage.getFlag()==true)
		{			
			if(g<15)
			{
				finishBooster();
			}
		}
		
		return g;
	}
	
	public void speedAccel(int x){
		if(level<max)
		{
			level++;
			turnSpeed(x);
		}
		else if(level>max)
		{ //�ν��ͳ� ���ǵ� �������� �������� �ӵ��� ���ݾ� ���ҵǵ���.
			level--;
			turnSpeed(x);
		}
		else if(level == max)
		{
			turnSpeed(x);
		}
	}
	public void turnSpeed(int x)
	{
		if(x>0)
		{
			if(level-x<0)
			{
				l_speed=s[0];
			}
			else
			{
				l_speed = s[level-x];
			}
			
			
			r_speed = s[level];
		}
		else
		{
			l_speed = s[level];
			
			if(level+x<0)
			{
				r_speed = s[0];
			}
			else
			{
				r_speed = s[level+x];
			}
		}
	}
	
	public void brake(){
		level--;
		r_speed = s[level];
		l_speed = s[level];
		if(level<=0)
		{
			stopBrake();
		}
	}
	
	public void setBrake(){
		brk = true;
	}
	
	public void stopBrake(){
		brk = false;
	}
	public boolean getBrake(){
		return brk;
	}
	
	public void useBooster(){
		if(gage.getGuage()>=15)
		{
			if(level<=13)
			{	
				level+=2;
			}
			if(level>=15)
			{
				level = 15;
			}
			max+=2;
			if(max>15)
			{
				max = 15;
			}
			l_speed =s[level];
			r_speed =s[level];
			gage.setFlagTrue();
		}
	}
	public void useSpeedItem(){
		if(spd_item)
		{
			return;
		}
		level+=2;
		max+=2;
		if(level>15)
		{
			level = 15;
		}
		if(max>15)
		{
			max = 15;
		}
		r_speed =s[level];
		l_speed =s[level];
		spd_item = true;
	}	
	public void finishBooster(){
		max-=2;
		gage.setFlagFalse();
	}
	public void finishSpeedItem(){
		max-=2;
		spd = 0;
		spd_item = false;
	}
	public int getSpeedLevel(){
		return level;
	}
	public int getLeftSpeed(){
		return l_speed;
	}
	public int getRightSpeed(){
		return r_speed;
	}
}