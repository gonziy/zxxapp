package com.zxxapp.mall.maintenance.model;

import java.io.File;

public class PictureDataModel {
    private String remoteFile;
    private File localFile;
    private String title;

    public String getRemoteFile() {
        return remoteFile;
    }

    public void setRemoteFile(String remoteFile) {
        this.remoteFile = remoteFile;
    }

    public File getLocalFile() {
        return localFile;
    }

    public void setLocalFile(File localFile) {
        this.localFile = localFile;
    }

    public boolean isLocalFile() {
        return localFile != null;
    }

    public boolean isRemoteFile() {
        return remoteFile != null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
