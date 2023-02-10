package kr.co.socsoft.data.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.socsoft.common.vo.DataUpldLogVO;
import kr.co.socsoft.common.vo.DataVO;
import kr.co.socsoft.common.vo.DataLinkVO;
import kr.co.socsoft.data.vo.DataTableInfoVO;

@Mapper("dataDao")
public interface DataDao {
	
	List<DataLinkVO> getGyeonggiDataLinkTableList();
	
	List<DataLinkVO> getGyeonggiOpenapiList(Map<String, Object> params);
	
	List<DataLinkVO> getGyeonggiOpenapiExcel(Map<String, Object> params);
	
	List<DataLinkVO> getDataStatusList(Map<String, Object> params);
	
	List<DataTableInfoVO> getDataStatusExcel(Map<String, Object> params);
	
	List<DataVO> getDataTableColumnList(Map<String, Object> params);
	
	List<DataLinkVO> getRightsMinwonLinkList(Map<String, Object> params);
	
	List<DataLinkVO> getRightsMinwonLinkExcel(Map<String, Object> params);
	
	List<?> getOpenapiTableList();
	
	List<?> getRightsMinwonTableList();
	
	int openapiConfSave(DataLinkVO dataLinkVo);
	
	int gyeonggiOpenapiKeySave(DataLinkVO dataLinkVo);
	
	DataVO getDataTableInfo(String tableId);
	
	List<DataVO> getDataUploadUser(String tableId);
	
	List<?> getDataTableColumnInfo(String tableId);
	
	DataVO getSpotColumnInfo(String tableId);
	
	int insertGyeonggiData(DataVO dataVo);
	
	int saveDataCsv(DataVO dataVo);
	
	int insretCsvToTable(DataVO dataVo);
	
	int saveGyeonggiData(List<String> saveGyeonggyDataList);
	
	int deleteLinkData(DataVO dataVo);
	
	int insertDataUpldLog(DataUpldLogVO dataUpldLogVo);

	List<DataVO> getExcelUploadTables();

	List<DataVO> getColumnsInTables(String string);
	
	int saveDataTableColumn(List<DataVO> dataVoList);
	
	int deleteDataTableColumn(List<DataVO> dataVoList);
	
	int saveDataTableInfo(DataVO dataVo);
	
	int saveDataUploadUser(List<DataVO> dataVoList);
	
	int deleteDataUploadUser(DataVO dataVo);
	
	//내부시스템연계
	List<DataLinkVO> getSystemLinkList(Map<String, Object> params);

	List<DataLinkVO> getSystemLinkExcel(Map<String, Object> params);
	//내부시스템연계 수동업로드
	String excuteSystemLinkManualUpload(DataVO dataVo);
	
	DataVO getConfirmTableExistYn(Map<String, Object> params);
	
	String getGyeonggiOpenapiKey();
}