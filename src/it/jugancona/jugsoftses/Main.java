/**
 * 
 */
package it.jugancona.jugsoftses;

import it.jugancona.jugsoftses.communication.Client;
import it.jugancona.jugsoftses.communication.Server;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Main.java generated 03/mag/2011
 *
 * (type in general information)
 */

/**
 * @author Gabriele Cardosi
 * 
 */
public class Main extends Activity {

	private static final String TAG = "Main";
	protected static final int PICK_REQUEST = 1337;
	private int orientation = 0;
	private Button openPickerButton;
	private Button startServerButton;
	private Uri contact = null;
	private Context context = this;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.app.Activity#onConfigurationChanged(android.content.res.Configuration
	 * )
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		Log.i(TAG, "onConfigurationChanged");
		if (orientation != newConfig.orientation) {
			orientation = newConfig.orientation;
			String orientationDescription = "";
			switch (orientation) {
			case Configuration.ORIENTATION_LANDSCAPE:
				orientationDescription = "ORIENTATION_LANDSCAPE";
				break;
			case Configuration.ORIENTATION_PORTRAIT:
				orientationDescription = "ORIENTATION_PORTRAIT";
				break;
			case Configuration.ORIENTATION_SQUARE:
				orientationDescription = "ORIENTATION_PORTRAIT";
				break;
			case Configuration.ORIENTATION_UNDEFINED:
				orientationDescription = "ORIENTATION_UNDEFINED";
				break;
			default:
				orientationDescription = "BO????";
				break;
			}
			Log.i(TAG, "Current orientation: " + orientationDescription);
			String res = Client.sendMessage(orientationDescription);
			Log.i(TAG, "Server response: " + res);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		openPickerButton = (Button) findViewById(R.id.BtnOpenPicker);
		openPickerButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent i = new Intent(Intent.ACTION_PICK, Uri
						.parse("content://contacts/people"));
				startActivityForResult(i, PICK_REQUEST);
			}
		});
		startServerButton = (Button) findViewById(R.id.BtnStartServer);
		startServerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(context, ServerView.class);
				context.startActivity(i);
			}
			
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PICK_REQUEST) {
			if (resultCode == RESULT_OK) {
				contact = data.getData();
			}
		}
	}
	

}
