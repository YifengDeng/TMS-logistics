package com.yydscm.Entity;

public class T_unusual_log_file {
    private Long uuid;
    private Long unusual_uuid;
    private Long file_uuid;
    private String file_path;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getUnusual_uuid() {
        return unusual_uuid;
    }

    public void setUnusual_uuid(Long unusual_uuid) {
        this.unusual_uuid = unusual_uuid;
    }

    public Long getFile_uuid() {
        return file_uuid;
    }

    public void setFile_uuid(Long file_uuid) {
        this.file_uuid = file_uuid;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
