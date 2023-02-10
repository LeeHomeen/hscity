package kr.co.socsoft.manage.data.impl;

import kr.co.socsoft.manage.data.BigDataProcessingService;
import kr.co.socsoft.manage.data.vo.TextDataUploadVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("bigDataProcessingService")
public class BigDataProcessingServiceImpl implements BigDataProcessingService {

    @Resource(name="bigDataProcessingMapper")
    private BigDataProcessingMapper bigDataProcessingMapper;

    @Override
    public String runTextdataUploadProcedure(TextDataUploadVO textDataUploadVO) {
        return bigDataProcessingMapper.runTextdataUploadProcedure(textDataUploadVO);
    }
}
