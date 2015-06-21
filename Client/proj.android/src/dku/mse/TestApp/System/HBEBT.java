package dku.mse.TestApp.System;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import java.util.Set;

public class HBEBT implements HBEBTListener {
	public static String EXTRA_DEVICE_ADDRESS = "device_address";

	private HBEBTListener mListener = null;
	private BluetoothAdapter mBluetoothAdapter;
	private SPPService mSPPService;
//	private Activity mActivity;
	private HBEBT mHBEBT;
	private BluetoothAdapter mBtAdapter;
	Context mContext;
	
	/*
	private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	private ArrayAdapter<String> mNewDevicesArrayAdapter;

	private LinearLayout mDeviceListLL;
	private TextView mDeviceListTv1;
	private TextView mDeviceListTv2;
	private ListView mDeviceListLv1;
	private ListView mDeviceListLv2;
	private Button mDeviceListBtn1;

	private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
			if (HBEBT.this.mBtAdapter.isDiscovering()) {
				HBEBT.this.mBtAdapter.cancelDiscovery();
			}

			String info = ((TextView) v).getText().toString();

			if ((info.equals("No devices have been paired"))
					|| (info.equals("No devices found"))
					|| (info.length() < 16)) {
				return;
			}
			String address = info.substring(info.length() - 17);
			Intent intent = new Intent();
			intent.putExtra(HBEBT.EXTRA_DEVICE_ADDRESS, address);
			HBEBT.this.setBluetoothState(-1, "address", address);
		}
	};
*/
//	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
//		public void onReceive(Context context, Intent intent) {
//			String action = intent.getAction();
//
//			if ("android.bluetooth.device.action.FOUND".equals(action)) {
//				BluetoothDevice device = (BluetoothDevice) intent
//						.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
//				if (device.getBondState() != 12)
//					HBEBT.this.mNewDevicesArrayAdapter.add(device.getName()
//							+ "\n" + device.getAddress());
//			} else if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED"
//					.equals(action)) {
////				HBEBT.this.mActivity
////						.setProgressBarIndeterminateVisibility(false);
////				HBEBT.this.setTitle("Select a device to connect");
////				HBEBT.this.mDeviceListBtn1.setVisibility(0);
////				if (HBEBT.this.mNewDevicesArrayAdapter.getCount() == 0)
////					HBEBT.this.mDeviceListTv2.setVisibility(8);
//			}
//		}
//	};

	public HBEBT(Context c) {
		super();
//		this.mActivity = a;
		this.mHBEBT = this;
		mContext = c;
//		this.setBluetoothState(resultCode, id, address)
//		createDeviceListLayout();
		init();
	}

	public void show() {
//		if (this.mActivity == null)
//			return;

		if (!this.mBluetoothAdapter.isEnabled()) {
			Toast.makeText(mContext, "please turn on the Bluetooth",Toast.LENGTH_SHORT).show();
		}
//			Intent enableIntent = new Intent(
//					"android.bluetooth.adapter.action.REQUEST_ENABLE");
//			this.mActivity.startActivity(enableIntent);
//			return;
//		}

//		initArrayAdapter();
//		setContentView(this.mDeviceListLL);
		Log.d("connect", "2");
		setBluetoothState(-1, "connect", null);
		setBluetoothState(-1, "address", null);

//		if (this.mBtAdapter.isDiscovering()) {
//			this.mBtAdapter.cancelDiscovery();
//		}
//		this.mDeviceListTv2.setVisibility(8);
//		this.mNewDevicesArrayAdapter.clear();

//		super.show();
	}

	public void setActivity(Activity a) {
//		this.mActivity = a;
	}

	private void setBluetoothState(int resultCode, String id, String address) {
		if (id == "address") {
			if (resultCode == -1) {
				Log.d("connect", "4");
//				this.mActivity.unregisterReceiver(this.mReceiver);
				BluetoothDevice device = this.mBluetoothAdapter
						.getRemoteDevice("00:19:01:37:B0:A5");
				this.mSPPService.connect(device);
//				this.mHBEBT.hide();
			}
		} else if (id == "connect")
			if (resultCode == -1) {
				Log.d("connect", "3");
				if (this.mSPPService == null)
					this.mSPPService = new SPPService(mContext);
			} else {
				Toast.makeText(this.mContext, "Bluetooth was not enabled.", Toast.LENGTH_SHORT).show();
//						.show();
//				this.mActivity.finish();
			}
	}

	private void init() {
		this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

//		if (this.mBluetoothAdapter == null) {
//			Toast.makeText(this.mActivity, "Bluetooth is not available", 1)
//					.show();
//			this.mActivity.finish();
//			return;
//		}

//		if (!this.mBluetoothAdapter.isEnabled()) {
//			Intent enableIntent = new Intent(
//					"android.bluetooth.adapter.action.REQUEST_ENABLE");
//			this.mActivity.startActivity(enableIntent);
//		} else if (this.mSPPService == null) {
//			this.mSPPService = new SPPService(mContext);
//		}
	}

	public void conntect() {
		Log.d("connect", "1");
		show();
	}

	public void disconnect() {
		if (this.mSPPService != null) {
			this.mSPPService.disconnect();
			this.mSPPService = null;
		}

		if (this.mBtAdapter != null)
			this.mBtAdapter.cancelDiscovery();
	}

	public void onConnected() {
		if (this.mListener != null)
			this.mListener.onConnected();
	}

	public void onDisconnected() {
		if (this.mListener != null)
			this.mListener.onDisconnected();
	}

	public void onConnecting() {
		if (this.mListener != null)
			this.mListener.onConnecting();
	}

	public void onReceive(byte[] buff) {
		if (this.mListener != null)
			this.mListener.onReceive(buff);
	}

	public void onConnectionFailed() {
		if (this.mListener != null)
			this.mListener.onConnectionFailed();
	}

	public void onConnectionLost() {
		if (this.mListener != null)
			this.mListener.onConnectionLost();
	}

	public void sendData(byte[] buff) {
		if (this.mSPPService != null)
			this.mSPPService.sendData(buff);
	}

	public void setListener(HBEBTListener l) {
		this.mListener = l;
	}

//	private void createDeviceListLayout() {
///*
//		this.mDeviceListLL = new LinearLayout(this.mActivity);
//		this.mDeviceListTv1 = new TextView(this.mActivity);
//		this.mDeviceListLv1 = new ListView(this.mActivity);
//		this.mDeviceListTv2 = new TextView(this.mActivity);
//		this.mDeviceListLv2 = new ListView(this.mActivity);
//		this.mDeviceListBtn1 = new Button(this.mActivity);
//
//		ViewGroup.LayoutParams lp1 = new ViewGroup.LayoutParams(-1, -1);
//		ViewGroup.LayoutParams lp2 = new ViewGroup.LayoutParams(-1, -2);
//		ViewGroup.LayoutParams lp3 = new LinearLayout.LayoutParams(-1, -2, 1.0F);
//		ViewGroup.LayoutParams lp4 = new LinearLayout.LayoutParams(-1, -2, 2.0F);
//
//		setTitle("Select a device to connect");
//
//		this.mDeviceListLL.setLayoutParams(lp1);
//		this.mDeviceListLL.setOrientation(1);
//
//		this.mDeviceListTv1.setLayoutParams(lp2);
//		this.mDeviceListTv1.setText("Paired Devices");
//		this.mDeviceListTv1.setVisibility(8);
//		this.mDeviceListTv1.setBackgroundColor(Color.rgb(119, 119, 119));
//		this.mDeviceListTv1.setTextColor(Color.rgb(255, 255, 255));
//		this.mDeviceListTv1.setPadding(5, 0, 0, 0);
//
//		this.mDeviceListLv1.setLayoutParams(lp3);
//		this.mDeviceListLv1.setStackFromBottom(true);
//
//		this.mDeviceListTv2.setLayoutParams(lp2);
//		this.mDeviceListTv2.setText("Other Available Devices");
//		this.mDeviceListTv2.setVisibility(8);
//		this.mDeviceListTv2.setBackgroundColor(Color.rgb(119, 119, 119));
//		this.mDeviceListTv2.setTextColor(Color.rgb(255, 255, 255));
//		this.mDeviceListTv2.setPadding(5, 0, 0, 0);
//
//		this.mDeviceListLv2.setLayoutParams(lp4);
//		this.mDeviceListLv2.setStackFromBottom(true);
//
//		this.mDeviceListBtn1.setLayoutParams(lp2);
//		this.mDeviceListBtn1.setText("Scan for devices");
//
//		this.mDeviceListLL.addView(this.mDeviceListTv1);
//		this.mDeviceListLL.addView(this.mDeviceListLv1);
//		this.mDeviceListLL.addView(this.mDeviceListTv2);
//		this.mDeviceListLL.addView(this.mDeviceListLv2);
//		this.mDeviceListLL.addView(this.mDeviceListBtn1);
//
//		this.mDeviceListBtn1.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				HBEBT.this.doDiscovery();
//				v.setVisibility(8);
//			}
//		});
//		*/
//	}

//	private void initArrayAdapter() {
//		this.mPairedDevicesArrayAdapter = new ArrayAdapter(this.mActivity,
//				17367043);
//		this.mNewDevicesArrayAdapter = new ArrayAdapter(this.mActivity,
//				17367043);
//
////		ListView pairedListView = this.mDeviceListLv1;
////		pairedListView.setAdapter(this.mPairedDevicesArrayAdapter);
////		pairedListView.setOnItemClickListener(this.mDeviceClickListener);
//
////		ListView newDevicesListView = this.mDeviceListLv2;
////		newDevicesListView.setAdapter(this.mNewDevicesArrayAdapter);
////		newDevicesListView.setOnItemClickListener(this.mDeviceClickListener);
//
////		IntentFilter filter = new IntentFilter(
////				"android.bluetooth.device.action.FOUND");
////		this.mActivity.registerReceiver(this.mReceiver, filter);
////
////		filter = new IntentFilter(
////				"android.bluetooth.adapter.action.DISCOVERY_FINISHED");
////		this.mActivity.registerReceiver(this.mReceiver, filter);
//
//		this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
//
//		Set<BluetoothDevice> pairedDevices = this.mBtAdapter.getBondedDevices();
//
//		if (pairedDevices.size() > 0) {
////			this.mDeviceListTv1.setVisibility(0);
//			for (BluetoothDevice device : pairedDevices)
//				this.mPairedDevicesArrayAdapter.add(device.getName() + "\n"
//						+ device.getAddress());
//		} else {
//			String noDevices = "No devices have been paired";
//			this.mPairedDevicesArrayAdapter.add(noDevices);
//		}
//	}

	private void doDiscovery() {
//		this.mActivity.setProgressBarIndeterminateVisibility(true);
//		setTitle("Scanning for devices...");

//		this.mDeviceListTv2.setVisibility(0);

		if (this.mBtAdapter.isDiscovering()) {
			this.mBtAdapter.cancelDiscovery();
		}

		this.mBtAdapter.startDiscovery();
	}
}