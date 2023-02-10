package kr.co.socsoft.manage.quartz;

import kr.co.socsoft.data.vo.DataUploadScheduleVO;

public interface QuartzService {
    boolean registerSchedule(DataUploadScheduleVO vo);
}
