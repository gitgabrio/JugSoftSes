/**
 * 
 */
package it.jugancona.jugsoftses.communication;

import java.io.*;
import java.net.*;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Server extends Activity {
	private static final String TAG = "Server";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String res = "FAIL";
		Log.i(TAG, "onCreate");
		try {
			ServerSocket ss = new ServerSocket(12345);
			Log.i(TAG, "S: Server started: " + ss.getInetAddress().getHostAddress() + ":" + ss.getLocalPort());
			Socket s = ss.accept();
			Log.i(TAG, "S: Client connected" + s.toString());
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			res = in.readLine();
			out.println("PONG" + res);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
		}
		TextView tv = new TextView(this);
		tv.setText("S: " + res);
		setContentView(tv);
	}
}
