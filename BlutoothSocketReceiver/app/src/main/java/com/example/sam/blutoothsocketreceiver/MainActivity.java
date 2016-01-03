package com.example.sam.blutoothsocketreceiver;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

public class MainActivity extends ActionBarActivity {
    Activity context;
    TextView changing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        accept_loop loop = new accept_loop(context);
        loop.start();
        Firebase.setAndroidContext(this);
        changing = (TextView)findViewById(R.id.text);
    }

    public void Files(){

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_main);
        adapter.add(new SimpleDateFormat("MM-dd-yyyy-H:mm:ss").format(new Date()) + "Sent_Data.txt");
        ListView listView = (ListView)findViewById(R.id.view_sent);
        listView.setAdapter(adapter);
        Intent intent = new Intent(this, Files_Sent.class);
        String fileName = (new SimpleDateFormat("MM-dd-yyyy-H:mm:ss").format(new Date()) + ".txt");
        intent.putExtra("File_Name", fileName);
        startActivity(intent);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


