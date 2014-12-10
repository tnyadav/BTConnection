package com.bt.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.IBluetooth;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class BTReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {/*
		// TODO Auto-generated method stub

		if (intent.getAction().equals(
				"android.bluetooth.device.action.PAIRING_REQUEST")) {
			BluetoothDevice rbd = (BluetoothDevice) intent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

			IBluetooth ib = getIBluetooth();
			try {
				byte[] pinBytes = new String("12345").getBytes();
				ib.setPin(rbd.getAddress(), pinBytes);
				rbd.getClass()
						.getMethod("setPairingConfirmation", boolean.class)
						.invoke(rbd, true);
			} catch (RemoteException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException e) { // TODO Auto-generated catch
													// block
				e.printStackTrace();
			}

		}

	*/
		
		 String action = intent.getAction();
		 if (action.equals(BluetoothDevice.ACTION_PAIRING_REQUEST)) {
		 // convert broadcast intent into activity intent (same action string)
		 BluetoothDevice device =
		 intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		 
		 try {
			 device.getClass().getMethod("setPairingConfirmation", boolean.class).invoke(device, true);
			 device.getClass().getMethod("cancelPairingUserInput", boolean.class).invoke(device);
		 } catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 }
		 /*
		 
		 int type = intent.getIntExtra(BluetoothDevice.EXTRA_PAIRING_VARIANT,
		 BluetoothDevice.ERROR);
		 Intent pairingIntent = new Intent();
		 pairingIntent.setClass(context, BluetoothPairingDialog.class);
		 pairingIntent.putExtra(BluetoothDevice.EXTRA_DEVICE, device);
		 pairingIntent.putExtra(BluetoothDevice.EXTRA_PAIRING_VARIANT, type);
		 if (type == BluetoothDevice.PAIRING_VARIANT_PASSKEY_CONFIRMATION ||
		 type == BluetoothDevice.PAIRING_VARIANT_DISPLAY_PASSKEY ||
		 type == BluetoothDevice.PAIRING_VARIANT_DISPLAY_PIN) {
		 int pairingKey = intent.getIntExtra(BluetoothDevice.EXTRA_PAIRING_KEY,
		 BluetoothDevice.ERROR);
		 pairingIntent.putExtra(BluetoothDevice.EXTRA_PAIRING_KEY, pairingKey);
		 }
		 
		 int pairingKey = intent.getIntExtra(BluetoothDevice.EXTRA_PAIRING_KEY,
				 BluetoothDevice.ERROR);
	//	 pairingKey=12345;
			IBluetooth ib = getIBluetooth();
			try {
				ib.setPin(device.getAddress(),intToBytes(pairingKey));
			
				device .getClass()
					.getMethod("setPairingConfirmation", boolean.class)
					.invoke(device, true);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 Log.e("t", ""+pairingKey);
		 pairingIntent.setAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
		 pairingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 PowerManager powerManager =
		 (PowerManager)context.getSystemService(Context.POWER_SERVICE);
		 String deviceAddress = device != null ? device.getAddress() : null;
		 if (powerManager.isScreenOn() &&
		 LocalBluetoothPreferences.shouldShowDialogInForeground(context, deviceAddress)) {
		 // Since the screen is on and the BT-related activity is in the foreground,
		 // just open the dialog
		 context.startActivity(pairingIntent);
		 } else {
		 // Put up a notification that leads to the dialog
		 Resources res = context.getResources();
		 Notification.Builder builder = new Notification.Builder(context)
		 .setSmallIcon(android.R.drawable.stat_sys_data_bluetooth)
		 .setTicker(res.getString(R.string.bluetooth_notif_ticker));
		 PendingIntent pending = PendingIntent.getActivity(context, 0,
		 pairingIntent, PendingIntent.FLAG_ONE_SHOT);
		 String name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
		 if (TextUtils.isEmpty(name)) {
		 name = device != null ? device.getAliasName() :
		 context.getString(android.R.string.unknownName);
		 }
		 builder.setContentTitle(res.getString(R.string.bluetooth_notif_title))
		 .setContentText(res.getString(R.string.bluetooth_notif_message, name))
		 .setContentIntent(pending)
		 .setAutoCancel(true)
		 .setDefaults(Notification.DEFAULT_SOUND)
		 .setColor(res.getColor(
		 com.android.internal.R.color.system_notification_accent_color));
		 NotificationManager manager = (NotificationManager)
		 context.getSystemService(Context.NOTIFICATION_SERVICE);
		 manager.notify(NOTIFICATION_ID, builder.getNotification());
		 }
		 } else if (action.equals(BluetoothDevice.ACTION_PAIRING_CANCEL)) {
		 // Remove the notification
		 NotificationManager manager = (NotificationManager) context
		 .getSystemService(Context.NOTIFICATION_SERVICE);
		 manager.cancel(NOTIFICATION_ID);
		 }
		 }
		 else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
			 int bondState = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE,
			 BluetoothDevice.ERROR);
			 int oldState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE,
			 BluetoothDevice.ERROR);
			 if((oldState == BluetoothDevice.BOND_BONDING) &&
			 (bondState == BluetoothDevice.BOND_NONE)) {
			 // Remove the notification
			 }
			 }*/
	}

	private IBluetooth getIBluetooth() {
		IBluetooth ibt = null;

		try {

			Class c2 = Class.forName("android.os.ServiceManager");

			Method m2 = c2.getDeclaredMethod("getService", String.class);
			IBinder b = (IBinder) m2.invoke(null, "bluetooth");

			Class c3 = Class.forName("android.bluetooth.IBluetooth");

			Class[] s2 = c3.getDeclaredClasses();

			Class c = s2[0];
			Method m = c.getDeclaredMethod("asInterface", IBinder.class);
			m.setAccessible(true);
			ibt = (IBluetooth) m.invoke(null, b);

		} catch (Exception e) {
			Log.e("flowlab", "Erroraco!!! " + e.getMessage());
		}

		return ibt;
	}
	public byte[] intToBytes( final int i ) {
	    ByteBuffer bb = ByteBuffer.allocate(4); 
	    bb.putInt(i); 
	    return bb.array();
	}
}
