/**
 * 
 */
package it.jugancona.jugsoftses;

import it.jugancona.jugsoftses.R;
import it.jugancona.jugsoftses.communication.Server;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;

public class ServerView extends Activity {
	private static final String TAG = "ServerView";
	private EditText et;
	
	// Define the Handler that receives messages from the thread and update the
	// progress
	final Handler handler = new Handler() {
		final static int RECEIVED = 1;

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case RECEIVED:
				String received = msg.getData().getString("received");
				et.setText(received);
				break;
			}
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.server);
		et = (EditText) findViewById(R.id.ServerEditText);
		Log.i(TAG, "onCreate");
		et.setText("Setting up Server...");
		Server server = new Server();
		server.setmHandler(handler);
		et.setText("Starting up Server...");
		server.start();
		et.setText("S: waiting...");
	}
}
