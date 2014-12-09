package com.yarin.android.Examples_08_09;

import java.lang.reflect.Method;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.IBluetooth;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class ClientSocketActivity  extends Activity
{
	private static final String TAG = ClientSocketActivity.class.getSimpleName();
	private static final int REQUEST_DISCOVERY = 0x1;;
	private Handler _handler = new Handler();
	private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();
	
	private BluetoothSocket socket = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
		WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		setContentView(R.layout.client_socket);
		if (!_bluetooth.isEnabled()) {
			finish();
			return;
		}
		Intent intent = new Intent(this, DiscoveryActivity.class);
		
		/* Prompted to select a server to connect */
		Toast.makeText(this, "select device to connect", Toast.LENGTH_SHORT).show();
		
		/* Select device for list */
		startActivityForResult(intent, REQUEST_DISCOVERY);
	}
	
	/* after select, connect to device */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode != REQUEST_DISCOVERY) {
			return;
		}
		if (resultCode != RESULT_OK) {
			return;
		}
		final BluetoothDevice device = data.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		new Thread() {
			public void run() {
				connect(device);
			};
		}.start();
	}
	
	
	private IBluetooth getIBluetooth() {
		IBluetooth ibt = null;

		try {

		    Class c2 = Class.forName("android.os.ServiceManager");

		    Method m2 = c2.getDeclaredMethod("getService",String.class);
		    IBinder b = (IBinder) m2.invoke(null, "bluetooth");

		    Class c3 = Class.forName("android.bluetooth.IBluetooth");

		    Class[] s2 = c3.getDeclaredClasses();

		    Class c = s2[0];
		    Method m = c.getDeclaredMethod("asInterface",IBinder.class);
		    m.setAccessible(true);
		    ibt = (IBluetooth) m.invoke(null, b);


		} catch (Exception e) {
		    Log.e("flowlab", "Erroraco!!! " + e.getMessage());
		}

		return ibt;
		}
	
	protected void connect(BluetoothDevice device) {

		IBluetooth ib = getIBluetooth();
		try {
			byte[] pinBytes = new String("0000").getBytes();
			ib.setPin(device.getAddress(), pinBytes);
			ib.createBond(device.getAddress());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	/*	try {
			byte[] pinBytes = new String("0000").getBytes();

			Log.d(TAG, "Try to set the PIN");
			Method m = device.getClass().getMethod("setPin", byte[].class);
			m.invoke(device, pinBytes);
			Log.d("pairDevice()", "Start Pairing...");
			m = device.getClass().getMethod("createBond", (Class[]) null);
			m.invoke(device, (Object[]) null);
			Log.d("pairDevice()", "Pairing finished.");
		} catch (Exception e) {
			Log.e("pairDevice()", e.getMessage());
		}*/
		  
		      
		  
		  
	/*	String ACTION_PAIRING_REQUEST = "android.bluetooth.device.action.PAIRING_REQUEST";
        Intent intent = new Intent(ACTION_PAIRING_REQUEST);
        String EXTRA_DEVICE = "android.bluetooth.device.extra.DEVICE";
        intent.putExtra(EXTRA_DEVICE, device);
        String EXTRA_PAIRING_VARIANT = "android.bluetooth.device.extra.PAIRING_VARIANT";
        int PAIRING_VARIANT_PIN = 0000;
        intent.putExtra(EXTRA_PAIRING_VARIANT, PAIRING_VARIANT_PIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/
		
		
		/*
		//BluetoothSocket socket = null;
		try {
			//Create a Socket connection: need the server's UUID number of registered
			socket = device.createRfcommSocketToServiceRecord(UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666"));
			
			socket.connect();
			Log.d("EF-BTBee", ">>Client connectted");
			
			InputStream inputStream = socket.getInputStream();														
			OutputStream outputStream = socket.getOutputStream();
			//outputStream.write(new byte[] { (byte) 0xa0, 0, 7, 16, 0, 4, 0 });
String string="TN Yadav";

//byte[] b = string.getBytes();
byte[] b = string.getBytes(Charset.forName("UTF-8"));


			
			outputStream.write(b);
//			new Thread() {
//				public void run() {
//						while(true)
//						{	
//						try {
//							Log.d("EF-BTBee", ">>Send data thread!");
//							OutputStream outputStream = socket.getOutputStream();
//							outputStream.write(new byte[] { (byte) 0xa2, 0, 7, 16, 0, 4, 0 });
//						} catch (IOException e) {
//							Log.e("EF-BTBee", "", e);
//						}
//						}
//				};
//			}.start();
			
		} catch (IOException e) {
			Log.e("EF-BTBee", "", e);
		} finally {
			if (socket != null) {
				try {
					Log.d("EF-BTBee", ">>Client Close");
					//socket.close();	
					//finish();
					return ;
				} catch (IOException e) {
					Log.e("EF-BTBee", "", e);
				}
			}
		}
	*/}
}

