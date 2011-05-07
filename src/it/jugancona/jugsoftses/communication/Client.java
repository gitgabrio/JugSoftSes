/**
 * 
 */
package it.jugancona.jugsoftses.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.util.Log;

/**
 * Client.java generated 03/mag/2011
 *
 * (type in general information)
 */

/**
 * @author Gabriele Cardosi
 *
 */
public class Client {
	
	
	
	private static final String TAG = "Client";

	public static String sendMessage(String remoteIp, String toSend) {
		String toReturn = "FAIL";
		try {
			Socket s = new Socket(remoteIp, 12345);
			Log.i(TAG, "C: Connected to server" + s.toString());
        	PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        	BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        	out.println(toSend);
        	toReturn = in.readLine();
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
		}
		return toReturn;
	}

}
