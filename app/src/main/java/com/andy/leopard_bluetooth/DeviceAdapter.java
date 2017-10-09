package com.andy.leopard_bluetooth;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceHolder> {

    private List<BluetoothDevice> deviceList = null;

    public DeviceAdapter(List<BluetoothDevice> deviceList) {
        this.deviceList = deviceList;
    }

    @Override
    public DeviceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DeviceHolder deviceHolder = new DeviceHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent));
        return deviceHolder;
    }

    @Override
    public void onBindViewHolder(DeviceHolder holder, int position) {
        if (position != -1) {
            holder.deviceName.setText(deviceList.get(position).getName());
        }

    }

    @Override
    public int getItemCount() {
        return deviceList == null ? -1 : deviceList.size();
    }

    class DeviceHolder extends RecyclerView.ViewHolder {
        private TextView deviceName;
        private Button connect;

        public DeviceHolder(View itemView) {
            super(itemView);
            deviceName = itemView.findViewById(R.id.device_name);
            connect = itemView.findViewById(R.id.connect);
        }
    }
}
