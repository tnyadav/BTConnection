package com.bt.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
	public void onReceive(Context context, Intent intent) {
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

}
