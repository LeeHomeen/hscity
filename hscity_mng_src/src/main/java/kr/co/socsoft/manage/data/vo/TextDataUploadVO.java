package kr.co.socsoft.manage.data.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TextDataUploadVO {
    private String tableId;
    private String userId;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
