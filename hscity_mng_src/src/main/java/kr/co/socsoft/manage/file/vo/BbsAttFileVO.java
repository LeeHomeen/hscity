package kr.co.socsoft.manage.file.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.Arrays;

public class BbsAttFileVO {
    private BigDecimal fileSeq;
    private String attFileMngSeq;
    private String fileNm;
    private BigDecimal fileSize;
    private byte[] fileData;
    private String createId;
    private String createDt;
    private String updateId;
    private String updateDt;

    public BigDecimal getFileSeq() {
        return fileSeq;
    }

    public void setFileSeq(BigDecimal fileSeq) {
        this.fileSeq = fileSeq;
    }

    public String getAttFileMngSeq() {
        return attFileMngSeq;
    }

    public void setAttFileMngSeq(String attFileMngSeq) {
        this.attFileMngSeq = attFileMngSeq;
    }

    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }

    public BigDecimal getFileSize() {
        return fileSize;
    }

    public void setFileSize(BigDecimal fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
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
