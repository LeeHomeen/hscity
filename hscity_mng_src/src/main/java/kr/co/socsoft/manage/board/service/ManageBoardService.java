package kr.co.socsoft.manage.board.service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.co.socsoft.manage.board.vo.BoardVO;

import java.io.IOException;
import java.util.List;

public interface ManageBoardService {
    /* 공지사항 시작 */
    List<BoardVO> selectNoticeBoardList(BoardVO boardVO);

    int selectNoticeBoardListToCnt(BoardVO boardVO);

    BoardVO selectNoticeBoard(BoardVO boardVO);

    int updateNoticeBoard(BoardVO boardVO) throws Exception;

    int insertNoticeBoard(BoardVO boardVO) throws Exception;

    int deleteNoticeBoard(BoardVO boardVO);
    /* 공지사항 끝 */

    /* FAQ 시작 */
    List<BoardVO> selectFaqBoardList(BoardVO boardVO);

    int selectFaqBoardListToCnt(BoardVO boardVO);

    BoardVO selectFaqBoard(BoardVO boardVO);

    int updateFaqBoard(BoardVO boardVO) throws Exception;

    int insertFaqBoard(BoardVO boardVO) throws Exception;

    int deleteFaqBoard(BoardVO boardVO);
    /* FAQ 끝 */

    /* QA 시작 */
    List<BoardVO> selectQaBoardList(BoardVO boardVO);

    int selectQaBoardListToCnt(BoardVO boardVO);

    BoardVO selectQaBoard(BoardVO boardVO);

    int updateQaBoard(BoardVO boardVO) throws Exception;

    int updateQaAnswerBoard(BoardVO boardVO) throws Exception;

    int insertQaBoard(BoardVO boardVO) throws Exception;

    int insertQaAnswerBoard(BoardVO boardVO) throws Exception;

    int deleteQaBoard(BoardVO boardVO);
    /* QA 끝 */

    /* 데이터도움말 시작 */
    List<BoardVO> selectDataManegementBoardList(BoardVO boardVO);

    int selectDataManegementBoardListToCnt(BoardVO boardVO);

    BoardVO selectDataManegementBoard(BoardVO boardVO);

    int updateDataManegementBoard(BoardVO boardVO) throws Exception;

    int insertDataManegementBoard(BoardVO boardVO) throws Exception;

    int deleteDataManegementBoard(BoardVO boardVO);
    /* 데이터도움말 끝 */
    
    /* 생각의 탄생 시작 */
    List<BoardVO> selectThinkBoardList(BoardVO boardVO);

    int selectThinkBoardListToCnt(BoardVO boardVO);

    BoardVO selectThinkBoard(BoardVO boardVO);

    int updateThinkBoard(BoardVO boardVO) throws Exception;

    int updateThinkAnswerBoard(BoardVO boardVO) throws Exception;

    int insertThinkBoard(BoardVO boardVO) throws Exception;

    int insertThinkAnswerBoard(BoardVO boardVO) throws Exception;

    int deleteThinkBoard(BoardVO boardVO);
    /* 생각의 탄생 끝 */
    
    List<String> selectHominNew() throws Exception;
}
