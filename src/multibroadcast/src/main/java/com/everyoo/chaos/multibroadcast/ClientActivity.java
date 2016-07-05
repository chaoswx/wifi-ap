package com.everyoo.chaos.multibroadcast;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by chaos on 2016/3/25.
 */
public class ClientActivity extends Activity{

    private TextView txtReturn;
    private BroadcastUtils broadcastUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        broadcastUtils = new BroadcastUtils(this);
        findViewById(R.id.btn_send_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("receive send am received message");
                        broadcastUtils.sendAndReceiveMessage();
                    }
                }).start();
            }
        });

    }
}
