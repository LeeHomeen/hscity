package kr.co.socsoft.data.service.impl;

import egovframework.com.cmm.EgovUserDetailsHelper;
import kr.co.socsoft.common.vo.LoginVO;
import kr.co.socsoft.data.service.BigDataUploadService;
import kr.co.socsoft.data.vo.DataTableInfoVO;
import kr.co.socsoft.data.vo.DataUploadScheduleVO;
import kr.co.socsoft.data.vo.DbUpldLogVO;
import kr.co.socsoft.data.vo.TextdataUploadManualVO;
import kr.co.socsoft.manage.quartz.QuartzService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("bigDataUploadService")
public class BigDataUploadServiceImpl implements BigDataUploadService {
    @Resource(name = "socQuartzService")
    private QuartzService quartzService;

    @Resource(name="bigDataUploadMapper")
    private BigDataUploadMapper bigDataUploadMapper;

    @Override
    public List<DataUploadScheduleVO> selectDataUploadScheduleList(DataUploadScheduleVO dataUploadScheduleVO) {
        return bigDataUploadMapper.selectDataUploadScheduleList(dataUploadScheduleVO);
    }

    @Override
    public int selectDataUploadScheduleListToCnt(DataUploadScheduleVO dataUploadScheduleVO) {
        return bigDataUploadMapper.selectDataUploadScheduleListToCnt(dataUploadScheduleVO);
    }

    @Override
    public List<DbUpldLogVO> selectDbUpldLogList(DbUpldLogVO dbUpldLogVO) {
        return bigDataUploadMapper.selectDbUpldLogList(dbUpldLogVO);
    }

    @Override
    public int selectDbUpldLogListToCnt(DbUpldLogVO dbUpldLogVO) {
        return bigDataUploadMapper.selectDbUpldLogListToCnt(dbUpldLogVO);
    }

    @Override
    public DataUploadScheduleVO selectDataUploadSchedule(DataUploadScheduleVO searchVO) {
        return bigDataUploadMapper.selectDataUploadSchedule(searchVO);
    }

    @Override
    public int updateDataUploadSchedule(DataUploadScheduleVO dataUploadScheduleVO) {
        LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
        dataUploadScheduleVO.setCreateId(loginVO.getUserId());
        dataUploadScheduleVO.setUpdateId(loginVO.getUserId());

        int cnt = bigDataUploadMapper.updateDataUploadSchedule(dataUploadScheduleVO);

        if(dataUploadScheduleVO.getUseYn().equals("y")) {
            quartzService.registerSchedule(dataUploadScheduleVO);
        }

        return cnt;
    }

    @Override
    public List<DataTableInfoVO> selectDataTableInfoList(DataTableInfoVO dataTableInfoVO) {
        return bigDataUploadMapper.selectDataTableInfoList(dataTableInfoVO);
    }

    @Override
    public List<String> selectExcelUploadDataColumnList(Map<String, Object> params) {
        return bigDataUploadMapper.selectExcelUploadDataColumnList(params);
    }

    @Override
    public String selectTextdataUploadManual(TextdataUploadManualVO manualVO) {
        return bigDataUploadMapper.selectTextdataUploadManual(manualVO);
    }

    @Override
    public List<Map<String, Object>> selectDataUploadScheduleExcelList(DataUploadScheduleVO searchVO) {
        return bigDataUploadMapper.selectDataUploadScheduleExcelList(searchVO);
    }

    @Override
    public List<Map<String, Object>> selectDbUpldLogExcelList(DbUpldLogVO searchVO) {
        return bigDataUploadMapper.selectDbUpldLogExcelList(searchVO);
    }
}
