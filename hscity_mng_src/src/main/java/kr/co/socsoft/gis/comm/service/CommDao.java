package kr.co.socsoft.gis.comm.service;

import java.util.HashMap;
import java.util.List;

import kr.co.socsoft.gis.comm.vo.CommVO;
import kr.co.socsoft.gis.comm.vo.LogVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 공통 서비스 DAO
 * @author SOC SOFT
 *
 */
@Mapper("commDao")
public interface CommDao {
	/**
	 * 읍/면/동 기준정보 조회
	 * @param CommVO
	 * @return
	 */
	List<?> selectStdDong(CommVO commVo);
	
	/**
	 * 읍/면/동 조회 
	 * @param CommVO
	 * @return List
	 */	 
	List<?> selectDongList(CommVO commVo);    //행정동
	List<?> selectCstDongList(CommVO commVo); //법정동(다중)
	
	/**
	 * 조회 읍면동 awk 가져오기	 
	 */
	List<?> selectDongWkt(CommVO commVo);   //행정동
	List<?> selectCstDongWkt(CommVO commVo); //법정동
	
	/**
	 * 분석영역 저장정보 조회
	 */
	List<?> selectBookmarkList(CommVO commVO);
	/**
	 * 분석영역 저장
	 */
	int insertBookmark(CommVO commVO);
	
	/**
	 * 분석영역 수정
	 */
	int updateBookmark(CommVO commVO);
	
	/**
	 * 분석영역 삭제
	 */
	int deleteBookmark(CommVO commVO);
	
	/**
	 * 업종분류 대분류
	 */
	List<?> selectLclasList(CommVO commVO);
	
	/**
	 * 업종분류 중분류
	 */
	List<?> selectMclasList(CommVO commVO);
	
	/**
	 * 업종분류 소분류
	 */
	List<?> selectSclasList(CommVO commVO);
	
	/**
	 * 테이블 정보 가져오기 
	 */
	List<?> selectTableInfo(CommVO commVO);
	List<?> selectTblPeriod(CommVO commVO); //테이블 기간조회

	/**
     * 기준년월 최근 데이터 가져오기 
     */
	List<?> selectRecentPeriod(CommVO commVO);
	
	/**
	 * excel download log save
	 */
	int insertExlLog(LogVO logVo);
	
	/**
	 * 버스 노선타입 정보 가져오기	  
	 */
	List<?> selectBusType(CommVO commVO);
	
	/**
	 * 버스 노선 정보	  
	 */
	List<?> selectBusLine(CommVO commVO);
}