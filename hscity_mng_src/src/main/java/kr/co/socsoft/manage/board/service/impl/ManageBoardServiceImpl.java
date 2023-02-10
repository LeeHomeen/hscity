package kr.co.socsoft.manage.board.service.impl;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.socsoft.manage.board.service.ManageBoardService;
import kr.co.socsoft.manage.board.vo.BoardVO;
import kr.co.socsoft.manage.file.service.FileService;
import kr.co.socsoft.manage.file.vo.BbsAttFileMngVO;
import kr.co.socsoft.manage.file.vo.BbsAttFileVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service("manageBoardService")
public class ManageBoardServiceImpl implements ManageBoardService {

    @Resource(name = "manageBoardMapper")
    private ManageBoardMapper mapper;

    @Resource(name = "fileService")
    private FileService fileService;

    @Resource(name = "boardFileIdGnrService")
    private EgovIdGnrService fileIdGnrService;

    @Override
    public List<BoardVO> selectNoticeBoardList(BoardVO boardVO) {
        return mapper.selectNoticeBoardList(boardVO);
    }

    @Override
    public int selectNoticeBoardListToCnt(BoardVO boardVO) {
        return mapper.selectNoticeBoardListToCnt(boardVO);
    }

    @Override
    public BoardVO selectNoticeBoard(BoardVO boardVO) {
        return mapper.selectNoticeBoard(boardVO);
    }

    @Override
    public int updateNoticeBoard(BoardVO boardVO) throws Exception {
        String attFileMngSeq = fileKeyInsert(boardVO);
        BbsAttFileVO fileVO = new BbsAttFileVO();
        fileVO.setAttFileMngSeq(attFileMngSeq);

        insertFile(boardVO, fileVO); // 파일 추가
        deleteFile(boardVO, fileVO); // 파일 삭제
        return mapper.updateNoticeBoard(boardVO);
    }

    @Override
    public int insertNoticeBoard(BoardVO boardVO) throws FdlException, IOException {
        String attFileMngSeq = fileIdGnrService.getNextStringId();
        BbsAttFileMngVO bbsAttFileMngVO = new BbsAttFileMngVO();
        bbsAttFileMngVO.setAttFileMngSeq(attFileMngSeq);
        int cnt = fileService.insertBbsAttFileMng(bbsAttFileMngVO);

        BbsAttFileVO fileVO = new BbsAttFileVO();
        fileVO.setAttFileMngSeq(attFileMngSeq);

        insertFile(boardVO, fileVO);

        boardVO.setAttFileMngSeq(attFileMngSeq);
        return mapper.insertNoticeBoard(boardVO);
    }

    @Override
    public int deleteNoticeBoard(BoardVO boardVO) {
        return mapper.deleteNoticeBoard(boardVO);
    }

    @Override
    public List<BoardVO> selectFaqBoardList(BoardVO boardVO) {
        return mapper.selectFaqBoardList(boardVO);
    }

    @Override
    public int selectFaqBoardListToCnt(BoardVO boardVO) {
        return mapper.selectFaqBoardListToCnt(boardVO);
    }

    @Override
    public BoardVO selectFaqBoard(BoardVO boardVO) {
        return mapper.selectFaqBoard(boardVO);
    }

    @Override
    public int insertFaqBoard(BoardVO boardVO) throws Exception {
        String attFileMngSeq = fileIdGnrService.getNextStringId();
        BbsAttFileMngVO bbsAttFileMngVO = new BbsAttFileMngVO();
        bbsAttFileMngVO.setAttFileMngSeq(attFileMngSeq);
        int cnt = fileService.insertBbsAttFileMng(bbsAttFileMngVO);

        BbsAttFileVO fileVO = new BbsAttFileVO();
        fileVO.setAttFileMngSeq(attFileMngSeq);

        insertFile(boardVO, fileVO);

        boardVO.setAttFileMngSeq(attFileMngSeq);
        return mapper.insertFaqBoard(boardVO);
    }

    @Override
    public int updateFaqBoard(BoardVO boardVO) throws Exception {
        String attFileMngSeq = fileKeyInsert(boardVO);
        BbsAttFileVO fileVO = new BbsAttFileVO();
        fileVO.setAttFileMngSeq(attFileMngSeq);

        insertFile(boardVO, fileVO);
        deleteFile(boardVO, fileVO);
        return mapper.updateFaqBoard(boardVO);
    }

    @Override
    public int deleteFaqBoard(BoardVO boardVO) {
        return mapper.deleteFaqBoard(boardVO);
    }

    @Override
    public int deleteQaBoard(BoardVO boardVO) {
        return mapper.deleteQaBoard(boardVO);
    }

    @Override
    public BoardVO selectQaBoard(BoardVO boardVO) {
        return mapper.selectQaBoard(boardVO);
    }

    @Override
    public int updateQaBoard(BoardVO boardVO) throws Exception {
        String attFileMngSeq = fileKeyInsert(boardVO);
        BbsAttFileVO fileVO = new BbsAttFileVO();
        fileVO.setAttFileMngSeq(attFileMngSeq);

        insertFile(boardVO, fileVO);
        deleteFile(boardVO, fileVO);
        return mapper.updateQaBoard(boardVO);
    }

    @Override
    public int updateQaAnswerBoard(BoardVO boardVO) throws Exception {
        String attFileMngSeq = fileKeyInsert(boardVO);
        BbsAttFileVO fileVO = new BbsAttFileVO();
        fileVO.setAttFileMngSeq(attFileMngSeq);

        insertFile(boardVO, fileVO);
        deleteFile(boardVO, fileVO);
        return mapper.updateQaAnswerBoard(boardVO);
    }

    @Override
    public int insertQaBoard(BoardVO boardVO) throws Exception {
        return mapper.insertQaBoard(boardVO);
    }

    @Override
    public int insertQaAnswerBoard(BoardVO boardVO) throws Exception {
        boardVO.setUpperQnaSeq(boardVO.getSeq());

        String attFileMngSeq = fileIdGnrService.getNextStringId();
        BbsAttFileMngVO bbsAttFileMngVO = new BbsAttFileMngVO();
        bbsAttFileMngVO.setAttFileMngSeq(attFileMngSeq);
        int cnt = fileService.insertBbsAttFileMng(bbsAttFileMngVO);

        BbsAttFileVO fileVO = new BbsAttFileVO();
        fileVO.setAttFileMngSeq(attFileMngSeq);

        insertFile(boardVO, fileVO);

        boardVO.setAttFileMngSeq(attFileMngSeq);
        return mapper.insertQaAnswerBoard(boardVO);
    }

    @Override
    public int selectQaBoardListToCnt(BoardVO boardVO) {
        return mapper.selectQaBoardListToCnt(boardVO);
    }

    @Override
    public List<BoardVO> selectQaBoardList(BoardVO boardVO) {
        return mapper.selectQaBoardList(boardVO);
    }

    @Override
    public int deleteDataManegementBoard(BoardVO boardVO) {
        return mapper.deleteDataManegementBoard(boardVO);
    }

    @Override
    public BoardVO selectDataManegementBoard(BoardVO boardVO) {
        return mapper.selectDataManegementBoard(boardVO);
    }

    @Override
    public int updateDataManegementBoard(BoardVO boardVO) throws Exception {
        String attFileMngSeq = fileKeyInsert(boardVO);
        BbsAttFileVO fileVO = new BbsAttFileVO();
        fileVO.setAttFileMngSeq(attFileMngSeq);

        insertFile(boardVO, fileVO); // 파일 추가
        deleteFile(boardVO, fileVO); // 파일 삭제
        return mapper.updateDataManegementBoard(boardVO);
    }

    private String fileKeyInsert(BoardVO boardVO) throws FdlException {
        String attFileMngSeq = boardVO.getAttFileMngSeq();
        if(StringUtils.isEmpty(attFileMngSeq)) {
            attFileMngSeq = fileIdGnrService.getNextStringId();
            BbsAttFileMngVO bbsAttFileMngVO = new BbsAttFileMngVO();
            bbsAttFileMngVO.setAttFileMngSeq(attFileMngSeq);
            int cnt = fileService.insertBbsAttFileMng(bbsAttFileMngVO);
            boardVO.setAttFileMngSeq(attFileMngSeq);
        }

        return attFileMngSeq;
    }

    @Override
    public int insertDataManegementBoard(BoardVO boardVO) throws Exception {
        String attFileMngSeq = fileIdGnrService.getNextStringId();
        BbsAttFileMngVO bbsAttFileMngVO = new BbsAttFileMngVO();
        bbsAttFileMngVO.setAttFileMngSeq(attFileMngSeq);
        int cnt = fileService.insertBbsAttFileMng(bbsAttFileMngVO);

        BbsAttFileVO fileVO = new BbsAttFileVO();
        fileVO.setAttFileMngSeq(attFileMngSeq);

        insertFile(boardVO, fileVO);

        boardVO.setAttFileMngSeq(attFileMngSeq);
        return mapper.insertDataManegementBoard(boardVO);
    }

    @Override
    public int selectDataManegementBoardListToCnt(BoardVO boardVO) {
        return mapper.selectDataManegementBoardListToCnt(boardVO);
    }

    @Override
    public List<BoardVO> selectDataManegementBoardList(BoardVO boardVO) {
        return mapper.selectDataManegementBoardList(boardVO);
    }

    private boolean insertFile(BoardVO boardVO, BbsAttFileVO fileVO) throws IOException {
        try {
            List<MultipartFile> files = boardVO.getFiles();
            if(files == null) {
                return false;
            }

            for (MultipartFile file : boardVO.getFiles()) {
                if (file != null && file.getSize() > 0) {
                    fileVO.setFileData(file.getBytes());
                    fileVO.setFileNm(file.getOriginalFilename());
                    fileVO.setFileSize(new BigDecimal(file.getSize()));
                    fileService.insertBbsAttFile(fileVO);
                }
            }
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    private boolean deleteFile(BoardVO boardVO, BbsAttFileVO fileVO) {
        try {
            BigDecimal[] fileDelList = boardVO.getFileDelList();
            if (fileDelList != null && fileDelList.length > 0) {
                for (BigDecimal fileSeq : fileDelList) {
                    fileVO.setAttFileMngSeq(boardVO.getAttFileMngSeq());
                    fileVO.setFileSeq(fileSeq);
                    fileService.deleteFiles(fileVO);
                }
            }
            return true;
        } catch (Exception e) {
            throw e;
        }
    }
    
    /* 생각의 탄생 시작 */
    @Override
    public int deleteThinkBoard(BoardVO boardVO) {
        return mapper.deleteThinkBoard(boardVO);
    }

    @Override
    public BoardVO selectThinkBoard(BoardVO boardVO) {
        return mapper.selectThinkBoard(boardVO);
    }

    @Override
    public int updateThinkBoard(BoardVO boardVO) throws Exception {
        String attFileMngSeq = fileKeyInsert(boardVO);
        BbsAttFileVO fileVO = new BbsAttFileVO();
        fileVO.setAttFileMngSeq(attFileMngSeq);

        insertFile(boardVO, fileVO);
        deleteFile(boardVO, fileVO);
        return mapper.updateThinkBoard(boardVO);
    }

    @Override
    public int updateThinkAnswerBoard(BoardVO boardVO) throws Exception {
        String attFileMngSeq = fileKeyInsert(boardVO);
        BbsAttFileVO fileVO = new BbsAttFileVO();
        fileVO.setAttFileMngSeq(attFileMngSeq);

        insertFile(boardVO, fileVO);
        deleteFile(boardVO, fileVO);
        return mapper.updateThinkAnswerBoard(boardVO);
    }

    @Override
    public int insertThinkBoard(BoardVO boardVO) throws Exception {
        return mapper.insertThinkBoard(boardVO);
    }

    @Override
    public int insertThinkAnswerBoard(BoardVO boardVO) throws Exception {
        boardVO.setUpperThinkSeq(boardVO.getSeq());

        String attFileMngSeq = fileIdGnrService.getNextStringId();
        BbsAttFileMngVO bbsAttFileMngVO = new BbsAttFileMngVO();
        bbsAttFileMngVO.setAttFileMngSeq(attFileMngSeq);
        int cnt = fileService.insertBbsAttFileMng(bbsAttFileMngVO);

        BbsAttFileVO fileVO = new BbsAttFileVO();
        fileVO.setAttFileMngSeq(attFileMngSeq);

        insertFile(boardVO, fileVO);

        boardVO.setAttFileMngSeq(attFileMngSeq);
        return mapper.insertThinkAnswerBoard(boardVO);
    }

    @Override
    public int selectThinkBoardListToCnt(BoardVO boardVO) {
        return mapper.selectThinkBoardListToCnt(boardVO);
    }

    @Override
    public List<BoardVO> selectThinkBoardList(BoardVO boardVO) {
        return mapper.selectThinkBoardList(boardVO);
    }
    /* 생각의 탄생 끝 */
    @Override
    public int deleteleehomeenBoard(BoardVO boardVO) {
        return mapper.deleteleehomeenBoard(boardVO);
    }

    @Override
    public BoardVO selectleehomeenBoard(BoardVO boardVO) {
        return mapper.selectleehomeenBoard(boardVO);
    }

    @Override
    public int updateleehomeenBoard(BoardVO boardVO) throws Exception {
        String attFileMngSeq = fileKeyInsert(boardVO);
        BbsAttFileVO fileVO = new BbsAttFileVO();
        fileVO.setAttFileMngSeq(attFileMngSeq);

        insertFile(boardVO, fileVO); // 파일 추가
        deleteFile(boardVO, fileVO); // 파일 삭제
        return mapper.updateleehomeenBoard(boardVO);
    }


    @Override
    public int insertleehomeenBoard(BoardVO boardVO) throws Exception {
        String attFileMngSeq = fileIdGnrService.getNextStringId();
        BbsAttFileMngVO bbsAttFileMngVO = new BbsAttFileMngVO();
        bbsAttFileMngVO.setAttFileMngSeq(attFileMngSeq);
        int cnt = fileService.insertBbsAttFileMng(bbsAttFileMngVO);

        BbsAttFileVO fileVO = new BbsAttFileVO();
        fileVO.setAttFileMngSeq(attFileMngSeq);

        insertFile(boardVO, fileVO);

        boardVO.setAttFileMngSeq(attFileMngSeq);
        return mapper.insertleehomeenBoard(boardVO);
    }

    @Override
    public int selectleehomeenBoardListToCnt(BoardVO boardVO) {
        return mapper.selectleehomeenBoardListToCnt(boardVO);
    }

    @Override
    public List<BoardVO> selectleehomeenBoardList(BoardVO boardVO) {
        return mapper.selectleehomeenBoardList(boardVO);
    }

}
