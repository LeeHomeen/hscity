package kr.co.socsoft.manage.file.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class BbsAttFileMngVO {
    private String attFileMngSeq;
    private String createId;
    private String createDt;
    private String updateId;
    private String updateDt;

    public String getAttFileMngSeq() {
        return attFileMngSeq;
    }

    public void setAttFileMngSeq(String attFileMngSeq) {
        this.attFileMngSeq = attFileMngSeq;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
