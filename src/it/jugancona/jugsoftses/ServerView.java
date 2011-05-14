/**
 * 
 */
package it.jugancona.jugsoftses;

import it.jugancona.jugsoftses.communication.Server;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;

public class ServerView extends Activity {
	private static final String TAG = "ServerView";
	private Server server = null;
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
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.server);
		et = (EditText) findViewById(R.id.ServerEditText);
		Log.i(TAG, "onCreate");
		et.setText("onCreate...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		Log.i(TAG, "onResume");
		super.onResume();
		String toSet = "onResume\r\nSetting up Server...";
		et.setText(toSet);
		if (server == null) {
			server = new Server();
			server.setmHandler(handler);
			et.setText("Starting up Server...");
			server.start();
		}
		et.setText("S: waiting...");
	}
	
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		Log.i(TAG, "onPause");
		super.onPause();
		if (server != null) {
			server.setRunnable(false);
			server = null;
			Log.i(TAG, "Is server null ? " + (server == null));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		Log.i(TAG, "onStop");
		super.onStop();
		if (server != null) {
			server.setRunnable(false);
			server = null;
			Log.i(TAG, "Is server null ? " + (server == null));
		}
	}

	
}
