/****************************************************************************
Copyright (c) 2010-2011 cocos2d-x.org

http://www.cocos2d-x.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
****************************************************************************/
package dku.mse.TestApp;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxGLSurfaceView;

import dku.mse.TestApp.System.Accelerometer;
import dku.mse.TestApp.System.SystemData;
import dku.mse.TestApp.System.HBEBT;
import dku.mse.TestApp.System.HBEBTListener;
import dku.mse.TestApp.System.ItemControl;
import dku.mse.TestApp.System.SmartMessage;
import dku.mse.TestApp.System.SpeedControl;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView.OnEditorActionListener;

public class Test extends Cocos2dxActivity implements HBEBTListener
{	
	int left;
	int right;
	int infrared;
	
    private HBEBT mBluetooth;
    private CountDownTimer timer1;
	
	private ItemControl item_cont;
	private SpeedControl speed_cont;
	private Accelerometer acl;
	
	private byte[] mBuff = new byte[100];
	private int mBuffLen = 0;
		
	SystemData sData;

    private native void UIRecive(int speed, int boost);
    private native void BotConnect();
    private native void Item(int itemId, int leftRight);	
    private native void Arrive(int time);
	
	protected void onCreate(Bundle savedInstanceState)
    {
		infrared= 0;
		left = 0;
		right = 0;
		
		super.onCreate(savedInstanceState);
//		NetTesting t = new NetTesting();
		sData = SystemData.getIns();
		mBluetooth = new HBEBT(this);
		mBluetooth.setListener(this);
		item_cont = new ItemControl();
		speed_cont = new SpeedControl();
		acl = new Accelerometer(this);
		timer1 = new CountDownTimer(1000,100)
		{
			public void onTick(long millisUntilFinished)
			{

				sData.x =acl.returnValue();
				sData.gage = speed_cont.speedControl((int)sData.x);
				storeMaxSpeed();

				if(infrared== 8)
				{
					sData.sendFlag = false;
					byte cmd[];
					cmd = SmartMessage.CMD_FORWARD.clone();
					cmd[5] = (byte)0;
					cmd[6] = getCheckSum(cmd);
					mBluetooth.sendData(cmd);
					sData.init();					
					Arrive(sData.time);
					Log.e("TEST", "arrive system");
				}
				infrared= 0;			
							
				if(sData.bluetooth)
				{
					mBluetooth.conntect();
					sData.bluetooth = false;
				}
				if(sData.sendFlag)
				{
					send();
					sData.time++;
					sData.item_count++;
					UIRecive(sData.maxSpeed, sData.gage);
				}
				if(sData.item_count == 30)
				{
					sData.item_count = 0;
					sData.item = item_cont.obtainItem();
					Item(sData.item[1], sData.item[2]);
				}
				if(sData.booster)
				{
					if(speed_cont.gage.getFlag()==false)
					{
						speed_cont.useBooster();
					}
					sData.booster = false;
				}
				if(sData.leftitem)
				{
					sData.item = item_cont.useItem(1);
					if(sData.item[0] == 1)
					{
						speed_cont.useSpeedItem();
					}
					sData.leftitem = false;
					
				}
				if(sData.rightitem)
				{
					sData.item = item_cont.useItem(2);
					if(sData.item[0] == 1)
					{
						speed_cont.useSpeedItem();
					}
					sData.rightitem = false;
					
				}
				if(sData.brake)
				{
					if(speed_cont.getBrake()==true)
					{
						speed_cont.stopBrake();
					}
					else
					{
						speed_cont.setBrake();
					}
					sData.brake = false;
				}				
			}
			public void onFinish(){
				timer1.start();
				}
		};
		timer1.start();
	}

    public Cocos2dxGLSurfaceView onCreateView()
    {
    	Cocos2dxGLSurfaceView glSurfaceView = new Cocos2dxGLSurfaceView(this);
    	// Test should create stencil buffer
    	glSurfaceView.setEGLConfigChooser(5, 6, 5, 0, 16, 8);
    	
    	return glSurfaceView;
    }

    static {
        System.loadLibrary("cocos2dcpp");
    }
    
    public static void LeftItem()
    {
    	SystemData sData = SystemData.getIns();
    	sData.leftitem = true;
    }
    
    public static void RightItem()
    {
    	SystemData sData = SystemData.getIns();
    	sData.rightitem = true;
    }
    
    public static void Boost()
    {
    	SystemData sData = SystemData.getIns();
    	sData.booster = true;
    }
    
    public static void Break()
    {
    	SystemData sData = SystemData.getIns();
    	sData.brake = true;
    }
    
    public static void ConnectBot()
    {
    	SystemData sData = SystemData.getIns();
    	sData.bluetooth = true;
    }
    
    public static void Start()
    {
    	SystemData sData = SystemData.getIns();
    	sData.sendFlag = true;    	
    }

	@Override
	public void onConnected() {
		// TODO Auto-generated method stub
		BotConnect();
		Log.d("TEST", "CONNECTED");
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
					//infrared
					StringBuffer str = new StringBuffer();
					for(int i=4;i<12;i++)
					{
						str.append(send[i]);
						if(send[i]==0)
						{
							infrared++;
						}
					}
					mBuffLen = 0;
				}
			}
		}
	}
	
	public void storeMaxSpeed()
	{
		left = speed_cont.getLeftSpeed();
		right = speed_cont.getRightSpeed();
		if(left>right)
		{
			sData.maxSpeed = left;
		}
		else
		{
			sData.maxSpeed = right;
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
	
	public void send()
	{
		left = speed_cont.getLeftSpeed();
		right = speed_cont.getRightSpeed();
		
		if(acl.returnValue()>2&&acl.returnValue()<=4)
		{
			left = 2;
			sData.mSpeed = left<<4;
			sData.mSpeed = sData.mSpeed + right;	
			sendCarPacket(SmartMessage.TOP_CENTER, sData.mSpeed);
		}
		else if(acl.returnValue()>4&& acl.returnValue()<=6)
		{
			left = 1;
			sData.mSpeed = left<<4;
			sData.mSpeed = sData.mSpeed + right;
			sendCarPacket(SmartMessage.TOP_CENTER, sData.mSpeed);
		}
		else if(acl.returnValue()>6&&acl.returnValue()<=8)
		{
			left = 0;
			sData.mSpeed = left<<4;
			sData.mSpeed = sData.mSpeed + right;
			sendCarPacket(SmartMessage.TOP_CENTER, sData.mSpeed);
		}
		else if(acl.returnValue()>8)
		{
			left = 2;
			sData.mSpeed = left<<4;
			sData.mSpeed = sData.mSpeed + right;
			sendCarPacket(SmartMessage.TOP_LEFT, sData.mSpeed);
		}
		else if(acl.returnValue()<-2&&acl.returnValue()>=-4)
		{
			right = 2;
			sData.mSpeed = left<<4;
			sData.mSpeed = sData.mSpeed + right;
			sendCarPacket(SmartMessage.TOP_CENTER, sData.mSpeed);
		}
		else if(acl.returnValue()<-4&&acl.returnValue()>=-6)
		{
			right = 1;
			sData.mSpeed = left<<4;
			sData.mSpeed = sData.mSpeed + right;
			sendCarPacket(SmartMessage.TOP_CENTER, sData.mSpeed);
		}
		else if(acl.returnValue()<-6&&acl.returnValue()>=-8)
		{
			right = 0;
			sData.mSpeed = left<<4;
			sData.mSpeed = sData.mSpeed + right;
			sendCarPacket(SmartMessage.TOP_CENTER, sData.mSpeed);
		}
		else if(acl.returnValue()<-8)
		{
			right = 2;
			sData.mSpeed = left<<4;
			sData.mSpeed = sData.mSpeed + right;
			sendCarPacket(SmartMessage.TOP_RIGHT, sData.mSpeed);
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
			sData.mSpeed = left<<4;
			sData.mSpeed = sData.mSpeed + right;
			sendCarPacket(SmartMessage.TOP_CENTER, sData.mSpeed);
		}
	}
}
