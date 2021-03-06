package com.andy.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.andy.leopard_bluetooth.BluetoothClient;
import com.andy.leopard_bluetooth.LeopardManager;
import com.andy.leopard_bluetooth.subscribe.Bluetooth;

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
        mLeopardManager.setDeviceUpdateListener(new BluetoothClient.DeviceUpdateListener() {
            @Override
            public void update(List<BluetoothDevice> data) {
                deviceData.clear();
                deviceData.addAll(data);
                mAdapter.notifyDataSetChanged();
                Log.d(TAG, "数据更新：" + data.size());
            }
        });

        mAdapter.setOnItemClickListener(new DeviceAdapter.ItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                BluetoothDevice device = deviceData.get(position);
                if (device.getBondState() == BluetoothDevice.BOND_NONE) {
                    mLeopardManager.bond(device, new Bluetooth.BondListener() {
                        @Override
                        public void bonded(BluetoothDevice device) {
                            Toast.makeText(MainActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                            connect(device);
                        }

                        @Override
                        public void bonding(BluetoothDevice device) {
                            Toast.makeText(MainActivity.this, "正在绑定", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void none(BluetoothDevice device) {
                            Toast.makeText(MainActivity.this, "绑定失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void connect(BluetoothDevice device) {
        mLeopardManager.connect(device, new BluetoothClient.ConnectListener() {
            @Override
            public void success(String result) {
                Toast.makeText(MainActivity.this, "蓝牙通讯建立连接", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(String msg) {
                Toast.makeText(MainActivity.this, "蓝牙通讯建立断开", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLeopardManager.close();
    }
}
