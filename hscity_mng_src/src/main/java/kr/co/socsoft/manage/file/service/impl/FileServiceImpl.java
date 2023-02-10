package kr.co.socsoft.manage.file.service.impl;

import egovframework.com.cmm.EgovUserDetailsHelper;
import kr.co.socsoft.common.vo.LoginVO;
import kr.co.socsoft.manage.file.service.FileService;
import kr.co.socsoft.manage.file.vo.BbsAttFileMngVO;
import kr.co.socsoft.manage.file.vo.BbsAttFileVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("fileService")
public class FileServiceImpl implements FileService {

    @Resource(name="fileMapper")
    private FileMapper mapper;

    @Override
    public int insertBbsAttFileMng(BbsAttFileMngVO bbsAttFileMngVO) {
        LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
        bbsAttFileMngVO.setCreateId(loginVO.getUserId());
        return mapper.insertBbsAttFileMng(bbsAttFileMngVO);
    }

    @Override
    public int insertBbsAttFile(BbsAttFileVO fileVO) {
        LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
        fileVO.setCreateId(loginVO.getUserId());
        return mapper.insertBbsAttFile(fileVO);
    }

    @Override
    public BbsAttFileVO selectBbsAttFile(BbsAttFileVO bbsAttFileVO) {
        return mapper.selectBbsAttFile(bbsAttFileVO);
    }

    @Override
    public int deleteFiles(BbsAttFileVO fileVO) {
        return mapper.deleteFiles(fileVO);
    }
}
