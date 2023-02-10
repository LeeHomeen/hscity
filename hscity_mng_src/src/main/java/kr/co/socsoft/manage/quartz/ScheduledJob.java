package kr.co.socsoft.manage.quartz;

import kr.co.socsoft.data.vo.DataUploadScheduleVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduledJob extends QuartzJobBean {

    private static final Logger logger = (Logger) LogManager.getLogger(ScheduledJob.class);

    private ScheduledBean anotherBean;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startDate = format.format(new Date());
            logger.info("ScheduledJob run start date => {}", startDate);

            String tableId = (String) context.getJobDetail().getJobDataMap().get("tableId");
            String createId = (String) context.getJobDetail().getJobDataMap().get("createId");
            String updateId = (String) context.getJobDetail().getJobDataMap().get("updateId");

            logger.debug("table_id => {} user_id => {} update_id = {}", tableId, createId, updateId);

            DataUploadScheduleVO dataUploadVO = new DataUploadScheduleVO();
            dataUploadVO.setTableId(tableId);
            dataUploadVO.setCreateId(createId);
            dataUploadVO.setUpdateId(updateId);

            anotherBean.run(dataUploadVO);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void setScheduledBean(ScheduledBean anotherBean) {
        this.anotherBean = anotherBean;
    }
}