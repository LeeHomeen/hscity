package kr.co.socsoft.manage.file.service.impl;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.socsoft.manage.file.vo.BbsAttFileMngVO;
import kr.co.socsoft.manage.file.vo.BbsAttFileVO;

@Mapper("fileMapper")
public interface FileMapper {
    int insertBbsAttFileMng(BbsAttFileMngVO bbsAttFileMngVO);

    int insertBbsAttFile(BbsAttFileVO fileVO);

    BbsAttFileVO selectBbsAttFile(BbsAttFileVO bbsAttFileVO);

    int deleteFiles(BbsAttFileVO fileVO);
}
