package kr.co.socsoft.manage.board.service.impl;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.socsoft.manage.board.vo.BoardVO;

import java.util.List;


@Mapper("manageBoardMapper")
public interface ManageBoardMapper {
    /* 공지사항 시작 */
    List<BoardVO> selectNoticeBoardList(BoardVO boardVO);

    int selectNoticeBoardListToCnt(BoardVO boardVO);

    BoardVO selectNoticeBoard(BoardVO boardVO);

    int updateNoticeBoard(BoardVO boardVO);

    int insertNoticeBoard(BoardVO boardVO);

    int deleteNoticeBoard(BoardVO boardVO);
    /* 공지사항 끝 */

    /* FAQ 시작 */
    List<BoardVO> selectFaqBoardList(BoardVO boardVO);

    int selectFaqBoardListToCnt(BoardVO boardVO);

    BoardVO selectFaqBoard(BoardVO boardVO);

    int updateFaqBoard(BoardVO boardVO);

    int insertFaqBoard(BoardVO boardVO);

    int deleteFaqBoard(BoardVO boardVO);
    /* FAQ 끝 */

    /* QA 시작 */
    List<BoardVO> selectQaBoardList(BoardVO boardVO);

    int selectQaBoardListToCnt(BoardVO boardVO);

    BoardVO selectQaBoard(BoardVO boardVO);

    int updateQaBoard(BoardVO boardVO);

    int updateQaAnswerBoard(BoardVO boardVO);

    int insertQaBoard(BoardVO boardVO);

    int insertQaAnswerBoard(BoardVO boardVO);

    int deleteQaBoard(BoardVO boardVO);
    /* QA 끝 */

    /* 데이터도움말 시작 */
    List<BoardVO> selectDataManegementBoardList(BoardVO boardVO);

    int selectDataManegementBoardListToCnt(BoardVO boardVO);

    BoardVO selectDataManegementBoard(BoardVO boardVO);

    int updateDataManegementBoard(BoardVO boardVO);

    int insertDataManegementBoard(BoardVO boardVO);

    int deleteDataManegementBoard(BoardVO boardVO);


    /* 데이터도움말 끝*/
    
    /* 생각의 탄생 시작 */
    List<BoardVO> selectThinkBoardList(BoardVO boardVO);

    int selectThinkBoardListToCnt(BoardVO boardVO);

    BoardVO selectThinkBoard(BoardVO boardVO);

    int updateThinkBoard(BoardVO boardVO);

    int updateThinkAnswerBoard(BoardVO boardVO);

    int insertThinkBoard(BoardVO boardVO);

    int insertThinkAnswerBoard(BoardVO boardVO);

    int deleteThinkBoard(BoardVO boardVO);
    /* 생각의 탄생 끝 */
    
    List<BoardVO> selectleehomeenBoardList(BoardVO boardVO);

    int selectleehomeenBoardListToCnt(BoardVO boardVO);

    BoardVO selectleehomeenBoard(BoardVO boardVO);

    int updateleehomeenBoard(BoardVO boardVO);

    int insertleehomeenBoard(BoardVO boardVO);

    int deleteleehomeenBoard(BoardVO boardVO);

    
    
}
