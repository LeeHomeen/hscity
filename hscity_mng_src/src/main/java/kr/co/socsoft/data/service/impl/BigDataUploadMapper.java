package kr.co.socsoft.data.service.impl;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.socsoft.data.vo.DataTableInfoVO;
import kr.co.socsoft.data.vo.DataUploadScheduleVO;
import kr.co.socsoft.data.vo.DbUpldLogVO;
import kr.co.socsoft.data.vo.TextdataUploadManualVO;

import java.util.List;
import java.util.Map;

@Mapper("bigDataUploadMapper")
public interface BigDataUploadMapper {
    List<DataUploadScheduleVO> selectDataUploadScheduleList(DataUploadScheduleVO dataUploadScheduleVO);

    int selectDataUploadScheduleListToCnt(DataUploadScheduleVO dataUploadScheduleVO);

    List<Map<String,Object>> selectDataUploadScheduleExcelList(DataUploadScheduleVO searchVO);

    List<DbUpldLogVO> selectDbUpldLogList(DbUpldLogVO dbUpldLogVO);

    int selectDbUpldLogListToCnt(DbUpldLogVO dbUpldLogVO);

    DataUploadScheduleVO selectDataUploadSchedule(DataUploadScheduleVO searchVO);

    int updateDataUploadSchedule(DataUploadScheduleVO dataUploadScheduleVO);

    List<DataTableInfoVO> selectDataTableInfoList(DataTableInfoVO dataTableInfoVO);

    List<String> selectExcelUploadDataColumnList(Map<String, Object> params);

    String selectTextdataUploadManual(TextdataUploadManualVO manualVO);

    List<Map<String,Object>> selectDbUpldLogExcelList(DbUpldLogVO searchVO);
}
