package kr.co.socsoft.manage.data;

import kr.co.socsoft.manage.data.vo.TextDataUploadVO;

public interface BigDataProcessingService {
    String runTextdataUploadProcedure(TextDataUploadVO textDataUploadVO);
}
