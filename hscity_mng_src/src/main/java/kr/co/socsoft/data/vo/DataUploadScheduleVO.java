package kr.co.socsoft.data.vo;

import egovframework.com.cmm.DefaultVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DataUploadScheduleVO extends DefaultVO {
    private String tableId;
    private String tableNm;
    private String description;
    private String upldFileNm;
    private String upldFileEncoding;
    private String insertStdym;
    private String splitTableYm;
    private String shceduleDay;
    private String scheduleHour;
    private String scheduleMinute;
    private String scheduleState;
    private String rmk;
    private String useYn;
    private String createId;
    private String createDt;
    private String updateId;
    private String updateDt;
    private String regist;

    private String mngStdYmdScd; /* 관리기준년월일_코드 */
    private String insertStdYmdYn; /* 기준년월일입력여부 */
    private String splitTableScd; /* DB테이블분리기준_코드 */

    public String getMngStdYmdScd() {
        return mngStdYmdScd;
    }

    public void setMngStdYmdScd(String mngStdYmdScd) {
        this.mngStdYmdScd = mngStdYmdScd;
    }

    public String getInsertStdYmdYn() {
        return insertStdYmdYn;
    }

    public void setInsertStdYmdYn(String insertStdYmdYn) {
        this.insertStdYmdYn = insertStdYmdYn;
    }

    public String getSplitTableScd() {
        return splitTableScd;
    }

    public void setSplitTableScd(String splitTableScd) {
        this.splitTableScd = splitTableScd;
    }

    public String getRegist() {
        return regist;
    }

    public void setRegist(String regist) {
        this.regist = regist;
    }

    public String getTableNm() {
        return tableNm;
    }

    public void setTableNm(String tableNm) {
        this.tableNm = tableNm;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpldFileNm() {
        return upldFileNm;
    }

    public void setUpldFileNm(String upldFileNm) {
        this.upldFileNm = upldFileNm;
    }

    public String getUpldFileEncoding() {
        return upldFileEncoding;
    }

    public void setUpldFileEncoding(String upldFileEncoding) {
        this.upldFileEncoding = upldFileEncoding;
    }

    public String getInsertStdym() {
        return insertStdym;
    }

    public void setInsertStdym(String insertStdym) {
        this.insertStdym = insertStdym;
    }

    public String getSplitTableYm() {
        return splitTableYm;
    }

    public void setSplitTableYm(String splitTableYm) {
        this.splitTableYm = splitTableYm;
    }

    public String getShceduleDay() {
        return shceduleDay;
    }

    public void setShceduleDay(String shceduleDay) {
        this.shceduleDay = shceduleDay;
    }

    public String getScheduleHour() {
        return scheduleHour;
    }

    public void setScheduleHour(String scheduleHour) {
        this.scheduleHour = scheduleHour;
    }

    public String getScheduleMinute() {
        return scheduleMinute;
    }

    public void setScheduleMinute(String scheduleMinute) {
        this.scheduleMinute = scheduleMinute;
    }

    public String getScheduleState() {
        return scheduleState;
    }

    public void setScheduleState(String scheduleState) {
        this.scheduleState = scheduleState;
    }

    public String getRmk() {
        return rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
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
