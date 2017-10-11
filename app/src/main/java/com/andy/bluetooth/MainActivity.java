package com.andy.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.andy.leopard_bluetooth.LeopardManager;
import com.andy.leopard_bluetooth.subscribe.BluetoothOper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends Activity {
    private final String TAG = getClass().getSimpleName();
    private RecyclerView mRecyclerView;
    private DeviceAdapter mAdapter;
    private List<BluetoothDevice> deviceData = new ArrayList<>();
    private LeopardManager mLeopardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new DeviceAdapter(deviceData);
        initData();
        initView();
    }

    private void initData() {
        mLeopardManager = LeopardManager.getInstance();
        mLeopardManager.init(this, UUID.randomUUID());
        mLeopardManager.open();
        mLeopardManager.setDeviceUpdateListener(new BluetoothOper.DeviceUpdateListener() {
            @Override
            public void update(List<BluetoothDevice> data) {
                deviceData.clear();
                deviceData.addAll(data);
                mAdapter.notifyDataSetChanged();
                Log.d(TAG, "数据更新：" + data.size());
            }
        });
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLeopardManager.close();
    }
}
