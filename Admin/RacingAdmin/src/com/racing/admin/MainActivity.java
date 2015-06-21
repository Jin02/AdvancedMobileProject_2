package com.racing.admin;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity{
	private ImageView start;
	private TextView text;
	private ImageView disconnect;
	private ImageView connect;
	private ImageView result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		 text = (TextView)findViewById(R.id.textView);
		
		connect = (ImageView)findViewById(R.id.button_connect);
		connect.setOnClickListener(new Button.OnClickListener(){
			
			public void onClick(View v){
				text.setText("Connect");
				new Thread (new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							checkReady();
							Log.d("CHECK", "checkReady");
						} catch (IOException e) {
							// TODO Auto-generated block
							e.printStackTrace();
						}
					}
				}).start();

			}
		});
		
		disconnect = (ImageView)findViewById(R.id.button_disconnect);
		disconnect.setEnabled(false);
		disconnect.setFocusable(false);

		
		disconnect.setOnClickListener(new Button.OnClickListener(){
			
			public void onClick(View v){
				text.setText("Disconnect");
					new Thread (new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							gameReset();
							Log.d("RESET", "gameReset");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
				
			}
			
		});
			
		
		start = (ImageView)findViewById(R.id.button_start);
		start.setEnabled(false);
		start.setFocusable(false);

		start.setOnClickListener(new Button.OnClickListener(){
			
			public void onClick(View v){
				text.setText("Start");
				start.setImageResource(R.drawable.button_start_p);
				new Thread (new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							gameStart();
							Log.d("START", "gameStart");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}
			
		});
		
		result = (ImageView)findViewById(R.id.button_result);
		result.setEnabled(false);
		result.setFocusable(false);

		result.setOnClickListener(new Button.OnClickListener(){
			
			public void onClick(View v){
				text.setText("Result");
				new Thread (new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							getResult();
							Log.d("RESULT", "result");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}
			
		});
	}


	private void checkReady () throws IOException{
		URL url = new URL("http://dkumse02.appspot.com/admin/check_all_ready");
		HttpURLConnection httpUrl = (HttpURLConnection)url.openConnection();
		httpUrl.setDefaultUseCaches(false);
		httpUrl.setDoInput(true);
	//	httpUrl.setDoOutput(true);
		httpUrl.setRequestMethod("GET");
		httpUrl.setRequestProperty("content-type", "application/x-www-form-urlencoded");
		System.setProperty("http.keepAlive", "false");
		BufferedReader bf = new BufferedReader(new InputStreamReader(httpUrl.getInputStream(), "UTF-8"));
		StringBuilder buff = new StringBuilder();
		String line;
		while((line = bf.readLine())!=null) {
			buff.append(line);
			if(line.equals("all ready")){
				runOnUiThread(new Runnable() {
                  public void run() {
                	  text.setText("Press Start");
                	  start.setEnabled(true);
                	  start.setFocusable(true);
              		start.setImageResource(R.drawable.button_start_on);
                  }
              });				
			} else if (line.equals("fail")) {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
	                	  text.setText("Error");
					}
				});
			}
		}
	}
	
	private void gameStart () throws IOException{
		URL url = new URL("http://dkumse02.appspot.com/admin/start");
		HttpURLConnection httpUrl = (HttpURLConnection)url.openConnection();
		httpUrl.setDefaultUseCaches(false);
		httpUrl.setDoInput(true);
		httpUrl.setRequestMethod("GET");
		httpUrl.setRequestProperty("content-type", "application/x-www-form-urlencoded");

		BufferedReader bf = new BufferedReader(new InputStreamReader(httpUrl.getInputStream(), "UTF-8"));
		StringBuilder buff = new StringBuilder();
		String line;
		while((line = bf.readLine())!=null) {
			buff.append(line);
			if(line.equals("start")){
				runOnUiThread(new Runnable() {
                  public void run() {
                	  text.setText("Game Started!");
                	  start.setImageResource(R.drawable.button_start_on);
              		disconnect.setEnabled(true);
            		disconnect.setFocusable(true);
            		result.setEnabled(true);
            		result.setFocusable(true);

                  }
              });				
			} else if (line.equals("fail")) {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
	                	  text.setText("Game cannot be started :(");
					}
				});
			}
		}
	}
	private void gameReset () throws IOException{
		URL url = new URL("http://dkumse02.appspot.com/admin/reset");
		HttpURLConnection httpUrl = (HttpURLConnection)url.openConnection();
		httpUrl.setDefaultUseCaches(false);
		httpUrl.setDoInput(true);
	//	httpUrl.setDoOutput(true);
		httpUrl.setRequestMethod("GET");
		httpUrl.setRequestProperty("content-type", "application/x-www-form-urlencoded");

		BufferedReader bf = new BufferedReader(new InputStreamReader(httpUrl.getInputStream(), "UTF-8"));
		StringBuilder buff = new StringBuilder();
		String line;
		while((line = bf.readLine())!=null) {
			buff.append(line);
			if(line.equals("clear success")){
				runOnUiThread(new Runnable() {
                  public void run() {
                	  text.setText("Clear Success!");
              		start.setEnabled(false);
            		start.setFocusable(false);
            		disconnect.setEnabled(false);
            		disconnect.setFocusable(false);
            		start.setImageResource(R.drawable.button_start);

                  }
              });				
			} else{
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
	                	  text.setText("Reset Fail :(");
					}
				});
			}
		}
	}
	
	private void getResult () throws IOException{
		URL url = new URL("http://dkumse02.appspot.com/admin/getwin");
		HttpURLConnection httpUrl = (HttpURLConnection)url.openConnection();
		httpUrl.setDefaultUseCaches(false);
		httpUrl.setDoInput(true);
	//	httpUrl.setDoOutput(true);
		httpUrl.setRequestMethod("GET");
		httpUrl.setRequestProperty("content-type", "application/x-www-form-urlencoded");

		BufferedReader bf = new BufferedReader(new InputStreamReader(httpUrl.getInputStream(), "UTF-8"));
		StringBuilder buff = new StringBuilder();
		String line;
		while((line = bf.readLine())!=null) {
			buff.append(line);
			if(line.equals("draw")){
				runOnUiThread(new Runnable() {
                  public void run() {
                	  text.setText("draw");
                  }
              });				
			} else{
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
	                	  text.setText("Win!");
					}
				});
			}
		}
	}
}
		

