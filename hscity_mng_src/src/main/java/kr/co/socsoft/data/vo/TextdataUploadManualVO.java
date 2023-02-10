package kr.co.socsoft.data.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TextdataUploadManualVO {
    private String tableId;
    private String uploadFileName;
    private String uploadFileEncoding;
    private String userId;
    private String insertStdym;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getUploadFileEncoding() {
        return uploadFileEncoding;
    }

    public void setUploadFileEncoding(String uploadFileEncoding) {
        this.uploadFileEncoding = uploadFileEncoding;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInsertStdym() {
        return insertStdym;
    }

    public void setInsertStdym(String insertStdym) {
        this.insertStdym = insertStdym;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
