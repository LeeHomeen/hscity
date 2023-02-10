package kr.co.socsoft.manage.file.service;

import kr.co.socsoft.manage.file.vo.BbsAttFileMngVO;
import kr.co.socsoft.manage.file.vo.BbsAttFileVO;

public interface FileService {
    int insertBbsAttFileMng(BbsAttFileMngVO bbsAttFileMngVO);

    int insertBbsAttFile(BbsAttFileVO fileVO);

    BbsAttFileVO selectBbsAttFile(BbsAttFileVO bbsAttFileVO);

    int deleteFiles(BbsAttFileVO fileVO);
}
