package kr.co.socsoft.data.service;

import kr.co.socsoft.data.vo.DataTableInfoVO;
import kr.co.socsoft.data.vo.DataUploadScheduleVO;
import kr.co.socsoft.data.vo.DbUpldLogVO;
import kr.co.socsoft.data.vo.TextdataUploadManualVO;

import java.util.List;
import java.util.Map;

public interface BigDataUploadService {
    List<DataUploadScheduleVO> selectDataUploadScheduleList(DataUploadScheduleVO dataUploadScheduleVO);

    int selectDataUploadScheduleListToCnt(DataUploadScheduleVO dataUploadScheduleVO);

    List<DbUpldLogVO> selectDbUpldLogList(DbUpldLogVO dbUpldLogVO);

    int selectDbUpldLogListToCnt(DbUpldLogVO dbUpldLogVO);

    DataUploadScheduleVO selectDataUploadSchedule(DataUploadScheduleVO searchVO);

    int updateDataUploadSchedule(DataUploadScheduleVO dataUploadScheduleVO);

    List<DataTableInfoVO> selectDataTableInfoList(DataTableInfoVO dataTableInfoVO);

    List<String> selectExcelUploadDataColumnList(Map<String, Object> params);

    String selectTextdataUploadManual(TextdataUploadManualVO manualVO);

    List<Map<String,Object>> selectDataUploadScheduleExcelList(DataUploadScheduleVO searchVO);

    List<Map<String,Object>> selectDbUpldLogExcelList(DbUpldLogVO searchVO);
}
