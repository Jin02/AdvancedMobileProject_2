package dku.mse.TestApp.System;

import java.util.Random;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class Item{

	private Random random;
	
	private String[] items = {"Speed", "bomb"};
	//�������, �� �ӵ� ����, ���� �ӵ� ����, ���� �ڵ� �ݴ��, õ�������, �ν��� ������ ����
	
	private int storage;
	
	public Item(){
		storage = 0;
		random = new Random();
	}
	
	public int getItem(){
		return storage;
	}
	
	public void setItem(){
		storage = random.nextInt(2)+1;
	}
	
	public void setItem(int item){
		storage = item;
	}
	
	public void clear(){
		storage = 0;
	}
}
