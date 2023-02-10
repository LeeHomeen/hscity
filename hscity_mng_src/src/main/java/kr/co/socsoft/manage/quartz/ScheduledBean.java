package kr.co.socsoft.manage.quartz;

import kr.co.socsoft.data.vo.DataUploadScheduleVO;
import kr.co.socsoft.manage.data.BigDataProcessingService;
import kr.co.socsoft.manage.data.vo.TextDataUploadVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("scheduledBean")
public class ScheduledBean {

    private static final Logger logger = (Logger) LogManager.getLogger(ScheduledBean.class);

    @Resource(name = "bigDataProcessingService")
    private BigDataProcessingService bigDataProcessingService;

    public void run(DataUploadScheduleVO dataUploadVO) throws Exception {
        TextDataUploadVO textDataUploadVO = new TextDataUploadVO();

        String tableId = dataUploadVO.getTableId();
        String createId = dataUploadVO.getCreateId();
        String updateId = dataUploadVO.getUpdateId();

        textDataUploadVO.setTableId(tableId);

        if(updateId.isEmpty()) {
            textDataUploadVO.setUserId(createId);
        } else {
            textDataUploadVO.setUserId(updateId);
        }

        bigDataProcessingService.runTextdataUploadProcedure(textDataUploadVO);
    }
}