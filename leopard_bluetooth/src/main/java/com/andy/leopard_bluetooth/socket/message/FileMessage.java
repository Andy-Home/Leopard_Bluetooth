package com.andy.leopard_bluetooth.socket.message;

import android.util.Log;

import java.io.File;

/**
 * File类型消息
 * <p>
 * Created by andy on 17-10-20.
 */

public class FileMessage extends Message {

    private File mFile;

    public FileMessage(File file) {
        mFile = file;
    }

    public void setFile(File mFile) {
        this.mFile = mFile;
    }

    public File getFile() {
        return mFile;
    }

    @Override
    void display() {
        if (mFile != null) {
            Log.d(TAG, "id:" + id + " ---- 文件名：" + mFile.getName() + " 路径：" + mFile.getAbsolutePath());
        }
    }
}
