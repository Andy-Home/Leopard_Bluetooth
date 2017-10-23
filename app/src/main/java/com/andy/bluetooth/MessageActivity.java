package com.andy.bluetooth;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.andy.leopard_bluetooth.LeopardManager;
import com.andy.leopard_bluetooth.socket.message.StringMessage;

/**
 * 消息发送窗口
 * <p>
 * Created by andy on 17-10-23.
 */

public class MessageActivity extends Activity {
    private ListView mListView;
    private EditText mMessgeInput;
    private Button mSend;
    private LeopardManager mLeopardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initData();
        initView();
    }

    private void initData() {
        mLeopardManager = LeopardManager.getInstance();
    }

    private void initView() {
        mListView = findViewById(R.id.msgList);
        mMessgeInput = findViewById(R.id.input);
        mSend = findViewById(R.id.send);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringMessage msg = new StringMessage(mMessgeInput.getText().toString());
                mLeopardManager.sendMessage(msg);
            }
        });
    }


}
