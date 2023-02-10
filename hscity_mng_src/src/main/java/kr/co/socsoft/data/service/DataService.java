package kr.co.socsoft.data.service;

import java.util.List;
import java.util.Map;

import kr.co.socsoft.common.vo.DataUpldLogVO;
import kr.co.socsoft.common.vo.DataVO;
import kr.co.socsoft.common.vo.DataLinkVO;
import kr.co.socsoft.data.vo.DataTableInfoVO;

public interface DataService {
	
	List<DataLinkVO> getGyeonggiDataLinkTableList() throws Exception;
	
	List<DataLinkVO> getGyeonggiOpenapiList(Map<String, Object> params) throws Exception;
	
	List<DataLinkVO> getGyeonggiOpenapiExcel(Map<String, Object> params) throws Exception;
	
	List<DataLinkVO> getDataStatusList(Map<String, Object> params) throws Exception;

	List<DataTableInfoVO> getDataStatusExcel(Map<String, Object> params) throws Exception;
	
	List<DataVO> getDataTableColumnList(Map<String, Object> params) throws Exception;
	
	List<DataLinkVO> getRightsMinwonLinkList(Map<String, Object> params) throws Exception;
	
	List<DataLinkVO> getRightsMinwonLinkExcel(Map<String, Object> params) throws Exception;
	
	List<?> getOpenapiTableList() throws Exception;
	
	List<?> getRightsMinwonTableList() throws Exception;
	
	int openapiConfSave(DataLinkVO dataLinkVo) throws Exception;
	
	int gyeonggiOpenapiKeySave(DataLinkVO dataLinkVo) throws Exception;
	
	DataVO getDataTableInfo(String tableId) throws Exception;
	
	List<DataVO> getDataUploadUser(String tableId) throws Exception;
	
	List<?> getDataTableColumnInfo(String tableId) throws Exception;
	
	DataVO getSpotColumnInfo(String tableId) throws Exception;
	
	int insertGyeonggiData(DataVO dataVo) throws Exception;
	
	int saveDataCsv(DataVO dataVo) throws Exception;
	
	int insretCsvToTable(DataVO dataVo) throws Exception;
	
	int saveGyeonggiData(List<String> saveGyeonggyDataList) throws Exception;
	
	int deleteLinkData(DataVO dataVo) throws Exception;
	
	int insertDataUpldLog(DataUpldLogVO dataUpldLogVo) throws Exception;

	List<DataVO> getExcelUploadTables() throws Exception;

	List<DataVO> getColumnsInTables(String string) throws Exception;
	
	int saveDataTableColumn(List<DataVO> dataVoList) throws Exception;
	
	int deleteDataTableColumn(List<DataVO> dataVoList) throws Exception;
	
	int saveDataTableInfo(DataVO dataVo) throws Exception;
	
	int saveDataUploadUser(List<DataVO> dataVoList) throws Exception;
	
	int deleteDataUploadUser(DataVO dataVo) throws Exception;

	//내부시스템 연계
	List<DataLinkVO> getSystemLinkList(Map<String, Object> params) throws Exception;
	
	List<DataLinkVO> getSystemLinkExcel(Map<String, Object> params) throws Exception;
	
	//내부시스템 연계 수동업로드
	String excuteSystemLinkManualUpload(DataVO dataVo) throws Exception;

	DataVO getConfirmTableExistYn(Map<String, Object> params) throws Exception;
	
	String getGyeonggiOpenapiKey() throws Exception;

}
