package kr.co.socsoft.manage.quartz.impl;

import kr.co.socsoft.data.vo.DataUploadScheduleVO;
import kr.co.socsoft.manage.quartz.QuartzService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("socQuartzService")
public class QuartzServiceImpl implements QuartzService {

    private static final Logger logger = (Logger) LogManager.getLogger(QuartzServiceImpl.class);

    @Resource(name="scheduler")
    private StdScheduler scheduler;

    @Resource(name="jobDetail")
    private JobDetail jobDetail;

    @Override
    public boolean registerSchedule(DataUploadScheduleVO vo) {
        String shceduleDay = vo.getShceduleDay();
        String hour = vo.getScheduleHour();
        String minute = vo.getScheduleMinute();

        try {
            String from = shceduleDay + " " + hour + ":" + minute;
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd HH:mm");
            Date to = transFormat.parse(from);

            Trigger trigger = TriggerBuilder.newTrigger().startAt(to).build();
            JobDataMap jobDataMap = (JobDataMap) jobDetail.getJobDataMap().clone();
            jobDataMap.put("tableId", vo.getTableId());
            jobDataMap.put("createId", vo.getCreateId());
            jobDataMap.put("updateId", vo.getUpdateId());

            JobDetail job = jobDetail.getJobBuilder().setJobData(jobDataMap).withIdentity(vo.getTableId() + System.currentTimeMillis()).build();

            scheduler.scheduleJob(job, trigger);
        } catch (ParseException e) {
            logger.error("스케쥴 시간 파싱 에러", e);
            return false;
        } catch (SchedulerException e) {
            logger.error("스케쥴 등록 에러", e);
            return false;
        }

        return true;
    }
}
