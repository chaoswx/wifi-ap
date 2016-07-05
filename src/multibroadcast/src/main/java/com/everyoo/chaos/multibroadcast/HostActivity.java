package com.everyoo.chaos.multibroadcast;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by chaos on 2016/3/25.
 */
public class HostActivity extends Activity {

    private TextView txtReceivedMessage;
    private BroadcastUtils broadcastUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        broadcastUtils = new BroadcastUtils(this);
        findViewById(R.id.btn_receive_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("performed receive and send message");
                        broadcastUtils.receiveAndSendMessage();
                    }
                }).start();

            }
        });
    }
}
