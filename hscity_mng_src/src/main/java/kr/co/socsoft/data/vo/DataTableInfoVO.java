package kr.co.socsoft.data.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.web.multipart.MultipartFile;

public class DataTableInfoVO {
    private String tableId;
    private String tableNm;
    private String description;
    private String dataGubunNm;
    private String dataTypeScd;
    private String dataTypeScdNm;
    private String dataConnScd;
    private String dataConnScdNm;
    private String openapiUrl;
	private String openapiTimeScd;
    private String openapiTimeScdNm;
    private String dataFilePath;
    private String mngStdYmdScd;
    private String mngStdYmdScdNm;
    private String insertStdYmdYn;
    private String splitTableScd;
    private String dataUpdPerScd;
    private String dataUpdPerScdNm;
    private String oriDataOwner;
    private String oriDataMngNm;
    private String oriDataMngTel;
    private String oriDataMngEmail;
    private String lastUploadDt;
    private String rmk;
    private String useYn;
    private String createId;
    private String createDt;
    private String updateId;
    private String updateDt;
    private String insertStdYmd;
    private String searchType;
    private String searchText;
    private String status;
    
    
    public String getOpenapiTimeScdNm() {
		return openapiTimeScdNm;
	}

	public void setOpenapiTimeScdNm(String openapiTimeScdNm) {
		this.openapiTimeScdNm = openapiTimeScdNm;
	}
    
    
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDataTypeScdNm() {
		return dataTypeScdNm;
	}

	public void setDataTypeScdNm(String dataTypeScdNm) {
		this.dataTypeScdNm = dataTypeScdNm;
	}

	public String getDataConnScdNm() {
		return dataConnScdNm;
	}

	public void setDataConnScdNm(String dataConnScdNm) {
		this.dataConnScdNm = dataConnScdNm;
	}

	public String getMngStdYmdScdNm() {
		return mngStdYmdScdNm;
	}

	public void setMngStdYmdScdNm(String mngStdYmdScdNm) {
		this.mngStdYmdScdNm = mngStdYmdScdNm;
	}

	public String getDataUpdPerScdNm() {
		return dataUpdPerScdNm;
	}

	public void setDataUpdPerScdNm(String dataUpdPerScdNm) {
		this.dataUpdPerScdNm = dataUpdPerScdNm;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	private MultipartFile file;

    public String getInsertStdYmd() {
        return insertStdYmd;
    }

    public void setInsertStdYmd(String insertStdYmd) {
        this.insertStdYmd = insertStdYmd;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableNm() {
        return tableNm;
    }

    public void setTableNm(String tableNm) {
        this.tableNm = tableNm;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDataGubunNm() {
        return dataGubunNm;
    }

    public void setDataGubunNm(String dataGubunNm) {
        this.dataGubunNm = dataGubunNm;
    }

    public String getDataTypeScd() {
        return dataTypeScd;
    }

    public void setDataTypeScd(String dataTypeScd) {
        this.dataTypeScd = dataTypeScd;
    }

    public String getDataConnScd() {
        return dataConnScd;
    }

    public void setDataConnScd(String dataConnScd) {
        this.dataConnScd = dataConnScd;
    }

    public String getOpenapiUrl() {
        return openapiUrl;
    }

    public void setOpenapiUrl(String openapiUrl) {
        this.openapiUrl = openapiUrl;
    }

    public String getOpenapiTimeScd() {
        return openapiTimeScd;
    }

    public void setOpenapiTimeScd(String openapiTimeScd) {
        this.openapiTimeScd = openapiTimeScd;
    }

    public String getDataFilePath() {
        return dataFilePath;
    }

    public void setDataFilePath(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

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

    public String getDataUpdPerScd() {
        return dataUpdPerScd;
    }

    public void setDataUpdPerScd(String dataUpdPerScd) {
        this.dataUpdPerScd = dataUpdPerScd;
    }

    public String getOriDataOwner() {
        return oriDataOwner;
    }

    public void setOriDataOwner(String oriDataOwner) {
        this.oriDataOwner = oriDataOwner;
    }

    public String getOriDataMngNm() {
        return oriDataMngNm;
    }

    public void setOriDataMngNm(String oriDataMngNm) {
        this.oriDataMngNm = oriDataMngNm;
    }

    public String getOriDataMngTel() {
        return oriDataMngTel;
    }

    public void setOriDataMngTel(String oriDataMngTel) {
        this.oriDataMngTel = oriDataMngTel;
    }

    public String getOriDataMngEmail() {
        return oriDataMngEmail;
    }

    public void setOriDataMngEmail(String oriDataMngEmail) {
        this.oriDataMngEmail = oriDataMngEmail;
    }

    public String getLastUploadDt() {
        return lastUploadDt;
    }

    public void setLastUploadDt(String lastUploadDt) {
        this.lastUploadDt = lastUploadDt;
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
