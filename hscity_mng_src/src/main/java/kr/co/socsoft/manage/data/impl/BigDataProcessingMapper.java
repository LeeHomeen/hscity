package kr.co.socsoft.manage.data.impl;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.socsoft.manage.data.vo.TextDataUploadVO;

@Mapper("bigDataProcessingMapper")
public interface BigDataProcessingMapper {
    String runTextdataUploadProcedure(TextDataUploadVO textDataUploadVO);
}
