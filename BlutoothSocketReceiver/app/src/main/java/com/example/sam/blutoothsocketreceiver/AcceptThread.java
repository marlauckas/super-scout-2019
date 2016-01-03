package com.example.sam.blutoothsocketreceiver;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by sam on 1/3/16.
 */
public class AcceptThread extends  Thread{
    String text;
    Activity context;
    String byteSize;
    String data;
    TextView changing;
    BluetoothSocket socket;

        public AcceptThread(Activity context, BluetoothSocket socket) {
            this.socket = socket;
            this.context = context;
        }
        public void run() {
            // If a connection was accepted
            if (socket != null) {
                //socket opened with connection
                // Do work to manage the connection (in a separate thread)
                try {
                    PrintWriter out;
                    out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter file = null;
                        /*try {
                            File dir = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/MassStringText");
                            //can delete when doing the actual thing
                            file = new PrintWriter(new FileOutputStream(new File(dir, "Send-Data.txt")));
                        } catch (IOException IOE) {
                            Log.e("File error", "Failed to open File");
                            return;
                        }*/
                    try {
                        text = "";
                        //get the bytesize from the first line of the data
                        byteSize = reader.readLine();
                    } catch (IOException e) {
                        text("Failed to read data");
                        System.out.println("Failed to read Data");
                    }
                    int size = Integer.parseInt(byteSize);
                    if (socket != null) {
                        data = "";
                        while (true) {
                            text = reader.readLine();
                            //If all the data is sent then break
                            if (text.equals("\0")) {
                                break;
                            }
                            //append data to the variable "data"
                            data = data.concat(text + "\n");
                            System.out.println(data);
                            //text(Integer.toString(size));
                        }
                        //data = data.concat("asdf");
                        //if the actual byte size is different from the byte size received..
                        if (size != data.length()) {
                            //send error message to scout.
                            //0 = no error, 1 = ERROR!
                            out.println("1");
                            out.flush();
                            text("Error Message Sent " + "\n" + "Byte size: " + Integer.toString(size) + "\n"
                                    + "Length size: " + Integer.toString(data.length()));
                            Log.e("Error", "Error message sent");
                            //I the byte size of actual is equal to the byte size received
                        } else {
                            //can delete when doing actual thing
                            //file.println(text);
                            System.out.println(text);
                            out.println("0");
                            out.flush();
                            context.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "Data trasnfer success", Toast.LENGTH_SHORT).show();
                                }
                            });

                            Firebase myFirebaseRef = new Firebase("https://popping-torch-4659.firebaseio.com");
                            myFirebaseRef.child("Mass String Data").child("Byte Size").setValue(Integer.toString(size));
                            System.out.println("Firebase");

                        }
                        System.out.println("end");
                        text("end");
                        return;
                    }
                    //file.close();
                    // socket.close();
                } catch (IOException e) {
                    System.out.println("Failed to handle data");
                    text("Failed to handle data");
                    Log.getStackTraceString(e);
                    return;
                }
            }
        }

    public void text(final String change_text) {
        changing = (TextView)context.findViewById(R.id.text);
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                changing.setText(change_text);
            }
        });
    }

}


