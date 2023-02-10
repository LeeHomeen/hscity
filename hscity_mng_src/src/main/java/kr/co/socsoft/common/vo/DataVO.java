package kr.co.socsoft.common.vo;

/**
 * DATA관리 vo
 * editor G1
 *
 */
public class  DataVO{
	private String sql;
	private String key;
	private String tableId;
	private String tableNm;
	private String uploadUserId;
	private String uploadUserName;
	private String uploadUserDeptName;
	private String dataGugunNm;
	private String description;
	private String dataTypeScd;
	private String dataTypeScdNm;
	private String dataConnScd;
	private String dataConnScdNm;
	private String openapiUrl;
	private String openapiKey;
	private String dataFilePath;
	private String mngStdYmdScd;
	private String mngStdYmdScdNm;
	private String dataUpdPerScd;
	private String dataUpdPerScdNm;
	private String splitTableScd;
	private String oriDataOwner;
	private String oriDataMngNm;
	private String oriDataMngTel;
	private String oriDataMngEmail;
	private String insertStdYmdYn;
	private String gisType;
	private String useYn;
	private String columnId;
	private String columnAliasId;
	private String columnSeq;
	private String columnNm;
	private String stdYm;
	private String stdColumn;
	private String stdFormUseYn;
	private int stdFormCount;
	private String columnType;
	private String crud;
	private String spot;
	private String spotX;
	private String spotY;
	private String saveFileNm;
	private String encodingType;
	private String dataGubunNm;
	private String openapiTimeScd;
	private String rmk;
	private String dbUpldUserId;
	private String confirmTableYn;
	private String confirmTableId;
	
	private String createId;
	private String updateId;
	
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
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
	public String getUploadUserId() {
		return uploadUserId;
	}
	public void setUploadUserId(String uploadUserId) {
		this.uploadUserId = uploadUserId;
	}
	public String getUploadUserName() {
		return uploadUserName;
	}
	public void setUploadUserName(String uploadUserName) {
		this.uploadUserName = uploadUserName;
	}
	public String getUploadUserDeptName() {
		return uploadUserDeptName;
	}
	public void setUploadUserDeptName(String uploadUserDeptName) {
		this.uploadUserDeptName = uploadUserDeptName;
	}
	public String getDataGugunNm() {
		return dataGugunNm;
	}
	public void setDataGugunNm(String dataGugunNm) {
		this.dataGugunNm = dataGugunNm;
	}
	public String getDataTypeScd() {
		return dataTypeScd;
	}
	public void setDataTypeScd(String dataTypeScd) {
		this.dataTypeScd = dataTypeScd;
	}
	public String getDataTypeScdNm() {
		return dataTypeScdNm;
	}
	public void setDataTypeScdNm(String dataTypeScdNm) {
		this.dataTypeScdNm = dataTypeScdNm;
	}
	public String getDataConnScd() {
		return dataConnScd;
	}
	public void setDataConnScd(String dataConnScd) {
		this.dataConnScd = dataConnScd;
	}
	public String getDataConnScdNm() {
		return dataConnScdNm;
	}
	public void setDataConnScdNm(String dataConnScdNm) {
		this.dataConnScdNm = dataConnScdNm;
	}
	public String getOpenapiUrl() {
		return openapiUrl;
	}
	public void setOpenapiUrl(String openapiUrl) {
		this.openapiUrl = openapiUrl;
	}
	public String getOpenapiKey() {
		return openapiKey;
	}
	public void setOpenapiKey(String openapiKey) {
		this.openapiKey = openapiKey;
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
	public String getMngStdYmdScdNm() {
		return mngStdYmdScdNm;
	}
	public void setMngStdYmdScdNm(String mngStdYmdScdNm) {
		this.mngStdYmdScdNm = mngStdYmdScdNm;
	}
	public String getDataUpdPerScd() {
		return dataUpdPerScd;
	}
	public void setDataUpdPerScd(String dataUpdPerScd) {
		this.dataUpdPerScd = dataUpdPerScd;
	}
	public String getDataUpdPerScdNm() {
		return dataUpdPerScdNm;
	}
	public void setDataUpdPerScdNm(String dataUpdPerScdNm) {
		this.dataUpdPerScdNm = dataUpdPerScdNm;
	}
	public String getSplitTableScd() {
		return splitTableScd;
	}
	public void setSplitTableScd(String splitTableScd) {
		this.splitTableScd = splitTableScd;
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
	public String getInsertStdYmdYn() {
		return insertStdYmdYn;
	}
	public void setInsertStdYmdYn(String insertStdYmdYn) {
		this.insertStdYmdYn = insertStdYmdYn;
	}
	public String getGisType() {
		return gisType;
	}
	public void setGisType(String gisType) {
		this.gisType = gisType;
	}
	public String getColumnId() {
		return columnId;
	}
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	public String getColumnAliasId() {
		return columnAliasId;
	}
	public void setColumnAliasId(String columnAliasId) {
		this.columnAliasId = columnAliasId;
	}
	public String getColumnSeq() {
		return columnSeq;
	}
	public void setColumnSeq(String columnSeq) {
		this.columnSeq = columnSeq;
	}
	public String getColumnNm() {
		return columnNm;
	}
	public void setColumnNm(String columnNm) {
		this.columnNm = columnNm;
	}
	public String getStdYm() {
		return stdYm;
	}
	public void setStdYm(String stdYm) {
		this.stdYm = stdYm;
	}
	public String getStdColumn() {
		return stdColumn;
	}
	public void setStdColumn(String stdColumn) {
		this.stdColumn = stdColumn;
	}
	public String getStdFormUseYn() {
		return stdFormUseYn;
	}
	public void setStdFormUseYn(String stdFormUseYn) {
		this.stdFormUseYn = stdFormUseYn;
	}
	public int getStdFormCount() {
		return stdFormCount;
	}
	public void setStdFormCount(int stdFormCount) {
		this.stdFormCount = stdFormCount;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getCrud() {
		return crud;
	}
	public void setCrud(String crud) {
		this.crud = crud;
	}
	public String getSpot() {
		return spot;
	}
	public void setSpot(String spot) {
		this.spot = spot;
	}
	public String getSpotX() {
		return spotX;
	}
	public void setSpotX(String spotX) {
		this.spotX = spotX;
	}
	public String getSpotY() {
		return spotY;
	}
	public void setSpotY(String spotY) {
		this.spotY = spotY;
	}
	public String getSaveFileNm() {
		return saveFileNm;
	}
	public void setSaveFileNm(String saveFileNm) {
		this.saveFileNm = saveFileNm;
	}
	public String getEncodingType() {
		return encodingType;
	}
	public void setEncodingType(String encodingType) {
		this.encodingType = encodingType;
	}
	public String getUpdateId() {
		return updateId;
	}
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
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
	public String getOpenapiTimeScd() {
		return openapiTimeScd;
	}
	public void setOpenapiTimeScd(String openapiTimeScd) {
		this.openapiTimeScd = openapiTimeScd;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getRmk() {
		return rmk;
	}
	public void setRmk(String rmk) {
		this.rmk = rmk;
	}
	public String getDbUpldUserId() {
		return dbUpldUserId;
	}
	public void setDbUpldUserId(String dbUpldUserId) {
		this.dbUpldUserId = dbUpldUserId;
	}
	public String getConfirmTableYn() {
		return confirmTableYn;
	}
	public void setConfirmTableYn(String confirmTableYn) {
		this.confirmTableYn = confirmTableYn;
	}
	public String getConfirmTableId() {
		return confirmTableId;
	}
	public void setConfirmTableId(String confirmTableId) {
		this.confirmTableId = confirmTableId;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	
}