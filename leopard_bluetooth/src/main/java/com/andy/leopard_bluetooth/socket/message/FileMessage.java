package com.andy.leopard_bluetooth.socket.message;

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
    public String display() {
        if (mFile != null) {
            return "id:" + id + " ---- 文件名：" + mFile.getName() + " 路径：" + mFile.getAbsolutePath();
        }
        return "空文件";
    }
}
