/**
 * 
 */
package it.jugancona.jugsoftses.communication;


import java.io.*;
import java.net.*;

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
			Log.i(TAG,
					"S: Server started: "
							+ ss.getInetAddress().getHostAddress() + ":"
							+ ss.getLocalPort());
			String res = "FAIL";
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
			Log.e(TAG, e.getMessage(), e);
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

}
