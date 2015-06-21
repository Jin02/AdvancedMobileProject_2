package dku.mse.TestApp.System;

import android.content.Context;
import android.os.CountDownTimer;

public class SystemManager implements HBEBTListener
{
	private static SystemManager sm = new SystemManager();
	
	Context mContext;
	HBEBT mBluetooth;
    CountDownTimer timer1;
	
	ItemControl item_cont;
	SpeedControl speed_cont;
	Accelerometer acl;
	
	int[] item;
	int gage;
	
	int mSpeed = 0;
	
	byte[] mBuff = new byte[100];
	int mBuffLen = 0;
	
	boolean sendFlag;
	
	public void init(Context context)
	{
		
		mContext = context;
		mBluetooth = new HBEBT(context);
		mBluetooth.setListener(this);
		sendFlag = false;
		item = new int[3];
		item_cont = new ItemControl();
		speed_cont = new SpeedControl();
		acl = new Accelerometer(context);
		timer1 = new CountDownTimer(1000,100){
			public void onTick(long millisUntilFinished){
			if(sendFlag)
				{
					int left = speed_cont.getLeftSpeed();
					int right = speed_cont.getRightSpeed();
					
					if(acl.returnValue()>1&&acl.returnValue()<=3)
					{
						left = 2;
						mSpeed = left<<4;
						mSpeed = mSpeed + right;	
						sendCarPacket(SmartMessage.TOP_CENTER, mSpeed);
					}
					else if(acl.returnValue()>3&& acl.returnValue()<=5)
					{
						left = 1;
						mSpeed = left<<4;
						mSpeed = mSpeed + right;
						sendCarPacket(SmartMessage.TOP_CENTER, mSpeed);
					}
					else if(acl.returnValue()>5&&acl.returnValue()<=8)
					{
						left = 0;
						mSpeed = left<<4;
						mSpeed = mSpeed + right;
						sendCarPacket(SmartMessage.TOP_CENTER, mSpeed);
					}
					else if(acl.returnValue()>8)
					{
						left = 1;
						mSpeed = left<<4;
						mSpeed = mSpeed + right;
						sendCarPacket(SmartMessage.TOP_LEFT, mSpeed);
					}
					else if(acl.returnValue()<-1&&acl.returnValue()>=-3)
					{
						right = 2;
						mSpeed = left<<4;
						mSpeed = mSpeed + right;
						sendCarPacket(SmartMessage.TOP_CENTER, mSpeed);
					}
					else if(acl.returnValue()<-3&&acl.returnValue()>=-5)
					{
						right = 1;
						mSpeed = left<<4;
						mSpeed = mSpeed + right;
						sendCarPacket(SmartMessage.TOP_CENTER, mSpeed);
					}
					else if(acl.returnValue()<-5&&acl.returnValue()>=-8)
					{
						right = 0;
						mSpeed = left<<4;
						mSpeed = mSpeed + right;
						sendCarPacket(SmartMessage.TOP_CENTER, mSpeed);
					}
					else if(acl.returnValue()<-8)
					{
						right = 1;
						mSpeed = left<<4;
						mSpeed = mSpeed + right;
						sendCarPacket(SmartMessage.TOP_RIGHT, mSpeed);
					}
					else
					{
						if(left>right)
						{
							right = left;
						}
						else if(right>left)
						{
							left = right;
						}
						mSpeed = left<<4;
						mSpeed = mSpeed + right;
						sendCarPacket(SmartMessage.TOP_CENTER, mSpeed);
					}
				}
			}
			public void onFinish(){
				timer1.start();
				}
		};
		timer1.start();
	}
	
	public static SystemManager getIns()
	{
		if(sm==null){sm = new SystemManager();}
		return sm;
	}

	@Override
	public void onConnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		mBluetooth.disconnect();
	}

	@Override
	public void onConnecting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionFailed() {
		// TODO Auto-generated method stub
		mBluetooth.disconnect();
	}

	@Override
	public void onConnectionLost() {
		// TODO Auto-generated method stub
		mBluetooth.disconnect();
	}

	@Override
	public void onReceive(byte[] buffer) {

		for(int n=0;n<buffer.length;n++){
			byte buff = buffer[n];
			
			if(mBuffLen==0 && buff!=0x76){
				return;
			} else if(mBuffLen==1 && buff!=0x00){
				mBuffLen = 0;
				return;
			} else if(mBuffLen>1 && buff==0x00 && mBuff[mBuffLen-1]==0x76){
				mBuffLen = 2;
				mBuff[0] = 0x76;
				mBuff[1] = 0x00;
			} else {
				mBuff[mBuffLen++] = buff;
				
				if(mBuffLen==22 && mBuff[2]==0x33){
					byte[] send = new byte[mBuffLen];
					
					for(int i=0;i<mBuffLen;i++){
						send[i] = mBuff[i];
					}
					mBuffLen = 0;
				} else if(mBuffLen==17 && mBuff[2]==0x3C){
					byte[] send = new byte[mBuffLen];
					
					for(int i=0;i<mBuffLen;i++){
						send[i] = mBuff[i];
					}
					//ultra�묒뾽
					StringBuffer str = new StringBuffer();
					for(int i=4;i<12;i++)
					{
						str.append(send[i]);
					}
					mBuffLen = 0;
				}
			}
		}
	}
	
	public void sendCarPacket(int array, int speed){
		byte[] cmd;
		
		switch(array){
		case SmartMessage.TOP_CENTER:
			cmd = SmartMessage.CMD_FORWARD.clone();
			cmd[5] = (byte)speed;
			cmd[6] = getCheckSum(cmd);
			mBluetooth.sendData(cmd);
			break;
		case SmartMessage.TOP_LEFT:
			cmd = SmartMessage.CMD_FORWARD_LEFT.clone();
			cmd[5] = (byte)speed; 
			cmd[6] = getCheckSum(cmd);
			mBluetooth.sendData(cmd);
			break;
		case SmartMessage.TOP_RIGHT:
			cmd = SmartMessage.CMD_FORWARD_RIGHT.clone();
			cmd[5] = (byte)speed; 
			cmd[6] = getCheckSum(cmd);
			mBluetooth.sendData(cmd);
			break;	
		}
	}
	
	public byte getCheckSum(byte[] buff){
		int ret = (buff[2]&0xFF)+(buff[4]&0xFF)+(buff[5]&0xFF);

		return (byte)ret;
	}
}
