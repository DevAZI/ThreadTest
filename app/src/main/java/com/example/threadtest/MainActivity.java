package com.example.threadtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        int id = android.os.Process.myPid();
        int id2 = android.os.Process.myTid();
        int id3 = android.os.Process.myUid();
        Log.i(TAG,"onCreate" + "PID=" + id + "TID" + id2 + "UID" + id3);

        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG, "handleMessage");
                String time = msg.getData().getString("time");
                TextView textView = findViewById(R.id.textView);
                textView.setText(time);
            }
        };


        TextView textView = findViewById(R.id.textView);
        Button updateBtn = findViewById(R.id.button);
        Button start = findViewById(R.id.button2);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ComplexThread complexThread = new ComplexThread();
                complexThread.start();
            }
        });
    }
    class ComplexThread extends Thread{
        @Override
        public void run() {
            int id = android.os.Process.myPid();
            int id2 = android.os.Process.myTid();
            int id3 = android.os.Process.myUid();
            Log.i(TAG,"onCreate" + "PID=" + id + "TID" + id2 + "UID" + id3);


            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:MM:SS");
            try {
                Thread.sleep(1000);

            } catch (Exception e) {
                e.printStackTrace();
            }


            String time = sdf.format(cal.getTime());
            Message message = new Message();

            Bundle bundle = new Bundle();
            bundle.putString("time",time);

            message.setData(bundle);

            handler.sendMessage(message);
            //TextView textView = findViewById(R.id.textView);
            //textView.setText(handler.toString());
        }
    }
}