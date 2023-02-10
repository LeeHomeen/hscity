package kr.co.socsoft.data.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.co.socsoft.common.vo.DataUpldLogVO;
import kr.co.socsoft.common.vo.DataVO;
import kr.co.socsoft.common.vo.DataLinkVO;
import kr.co.socsoft.data.service.DataDao;
import kr.co.socsoft.data.service.DataService;
import kr.co.socsoft.data.vo.DataTableInfoVO;


@Service("dataService")
public class DataServiceImpl implements DataService{
	
	@Resource(name = "dataDao")
	private DataDao dataDao;
	
	
	@Override
	public List<DataLinkVO> getGyeonggiDataLinkTableList() throws Exception {
		return dataDao.getGyeonggiDataLinkTableList();
	}
	
	@Override
	public List<DataLinkVO> getGyeonggiOpenapiList(Map<String, Object> params) throws Exception {
		return dataDao.getGyeonggiOpenapiList(params);
	}
	
	@Override
	public List<DataLinkVO> getGyeonggiOpenapiExcel(Map<String, Object> params) throws Exception {
		return dataDao.getGyeonggiOpenapiExcel(params);
	}
	
	
	@Override
	public List<DataLinkVO> getDataStatusList(Map<String, Object> params) throws Exception {
		return dataDao.getDataStatusList(params);
	}
	
	
	@Override
	public List<DataTableInfoVO> getDataStatusExcel(Map<String, Object> params) throws Exception {
		return dataDao.getDataStatusExcel(params);
	}
	
	@Override
	public List<DataVO> getDataTableColumnList(Map<String, Object> params) throws Exception {
		return dataDao.getDataTableColumnList(params);
	}
	
	@Override
	public List<DataLinkVO> getRightsMinwonLinkList(Map<String, Object> params) throws Exception {
		return dataDao.getRightsMinwonLinkList(params);
	}
	
	
	@Override
	public List<DataLinkVO> getRightsMinwonLinkExcel(Map<String, Object> params) throws Exception {
		return dataDao.getRightsMinwonLinkExcel(params);
	}
	
	@Override
	public List<?> getOpenapiTableList() {
		return dataDao.getOpenapiTableList();
	}
	
	@Override
	public List<?> getRightsMinwonTableList() {
		return dataDao.getRightsMinwonTableList();
	}
	
	@Override
	public int openapiConfSave(DataLinkVO dataLinkVo) throws Exception {
		return dataDao.openapiConfSave(dataLinkVo);
	}
	
	@Override
	public int gyeonggiOpenapiKeySave(DataLinkVO dataLinkVo) throws Exception {
		return dataDao.gyeonggiOpenapiKeySave(dataLinkVo);
	}
	
	@Override
	public DataVO getDataTableInfo(String tableId) throws Exception {
		return dataDao.getDataTableInfo(tableId);
	}
	
	@Override
	public List<DataVO> getDataUploadUser(String tableId) throws Exception {
		return dataDao.getDataUploadUser(tableId);
	}
	
	@Override
	public List<?> getDataTableColumnInfo(String tableId) throws Exception {
		return dataDao.getDataTableColumnInfo(tableId);
	}
	
	@Override
	public DataVO getSpotColumnInfo(String tableId) throws Exception {
		return dataDao.getSpotColumnInfo(tableId);
	}
	
	@Override
	public int insertGyeonggiData(DataVO dataVo) throws Exception {
		return dataDao.insertGyeonggiData(dataVo);
	}
	
	@Override
	public int saveDataCsv(DataVO dataVo) throws Exception {
		return dataDao.saveDataCsv(dataVo);
	}
	
	@Override
	public int insretCsvToTable(DataVO dataVo) throws Exception {
		return dataDao.insretCsvToTable(dataVo);
	}
	
	@Override
	public int saveGyeonggiData(List<String> saveGyeonggyDataList) throws Exception {
		return dataDao.saveGyeonggiData(saveGyeonggyDataList);
	}
	
	@Override
	public int deleteLinkData(DataVO dataVo) throws Exception {
		return dataDao.deleteLinkData(dataVo);
	}
	
	@Override
	public int insertDataUpldLog(DataUpldLogVO dataUpldLogVo) throws Exception {
		return dataDao.insertDataUpldLog(dataUpldLogVo);
	}

	@Override
	public List<DataVO> getExcelUploadTables() throws Exception {
		return dataDao.getExcelUploadTables();
	}

	@Override
	public List<DataVO> getColumnsInTables(String string) throws Exception {
		return dataDao.getColumnsInTables(string);
	}
	
	@Override
	public int saveDataTableColumn(List<DataVO> dataVoList) throws Exception {
		return dataDao.saveDataTableColumn(dataVoList);
	}
	
	@Override
	public int deleteDataTableColumn(List<DataVO> dataVoList) throws Exception {
		return dataDao.deleteDataTableColumn(dataVoList);
	}
	
	@Override
	public int saveDataTableInfo(DataVO dataVo) throws Exception {
		return dataDao.saveDataTableInfo(dataVo);
	}
	
	@Override
	public int saveDataUploadUser(List<DataVO> dataVoList) throws Exception {
		return dataDao.saveDataUploadUser(dataVoList);
	}
	
	@Override
	public int deleteDataUploadUser(DataVO dataVo) throws Exception {
		return dataDao.deleteDataUploadUser(dataVo);
	}
	
	/* (non-Javadoc)
	 * @see kr.co.socsoft.data.service.DataService#getSystemLinkList(java.util.Map)
	 * 내부시스템 연계
	 */
	@Override
	public List<DataLinkVO> getSystemLinkList(Map<String, Object> params) throws Exception {
		return dataDao.getSystemLinkList(params);
	}
	
	@Override
	public List<DataLinkVO> getSystemLinkExcel(Map<String, Object> params) throws Exception {
		return dataDao.getSystemLinkExcel(params);
	}
	
	
	/* (non-Javadoc)
	 * @see kr.co.socsoft.data.service.DataService#excuteSystemLinkManualUpload(kr.co.socsoft.common.vo.DataVO)
	 * 내부시스템 연계 수동업로드
	 */
	@Override 
	public String excuteSystemLinkManualUpload(DataVO dataVo) throws Exception {
		return dataDao.excuteSystemLinkManualUpload(dataVo);
	}

	@Override
	public DataVO getConfirmTableExistYn(Map<String, Object> params) throws Exception {
		return dataDao.getConfirmTableExistYn(params);
	}
	
	@Override 
	public String getGyeonggiOpenapiKey() throws Exception {
		return dataDao.getGyeonggiOpenapiKey();
	}
}
