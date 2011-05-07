/**
 * 
 */
package it.jugancona.jugsoftses.communication;


import java.io.*;
import java.net.*;
import java.util.Enumeration;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Server extends Thread {
	private static final String TAG = "Server";
	final static int RECEIVED = 1;
	
	private Handler mHandler;
	
	

	/**
	 * @param mHandler the mHandler to set
	 */
	public void setmHandler(Handler mHandler) {
		this.mHandler = mHandler;
	}

	public void run() {
		try {
			ServerSocket ss = new ServerSocket(12345);
			String localIp = getLocalIpAddress(); 
			String res = "S: Server started: "
				+ localIp + ":"
				+ ss.getLocalPort();
			Log.i(TAG, res);
			sendReceived(res);
			while (true) {
				Socket s = ss.accept();
				Log.i(TAG, "S: Client connected" + s.toString());
				PrintWriter out = new PrintWriter(s.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						s.getInputStream()));
				res = in.readLine();
				out.println("PONG" + res);
				out.close();
				in.close();
				sendReceived(res);
			}
		} catch (IOException e) {
			String res = "FAIL: " + e.getMessage();
			Log.e(TAG, e.getMessage(), e);
			sendReceived(res);
		}
	}
	
	private void sendReceived(String received) {
		Message msg = mHandler.obtainMessage();
		Bundle b = new Bundle();
		b.putString("received", received);
		msg.setData(b);
		msg.what = RECEIVED;
		mHandler.sendMessage(msg);
	}
	
	private String getLocalIpAddress() {
	    try {
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	            NetworkInterface intf = en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                InetAddress inetAddress = enumIpAddr.nextElement();
	                if (!inetAddress.isLoopbackAddress()) {
	                    return inetAddress.getHostAddress().toString();
	                }
	            }
	        }
	    } catch (SocketException ex) {
	        Log.e(TAG, ex.toString());
	    }
	    return null;
	}

}
