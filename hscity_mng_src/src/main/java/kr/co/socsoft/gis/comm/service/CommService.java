package kr.co.socsoft.gis.comm.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.socsoft.gis.comm.vo.CommVO;
import kr.co.socsoft.gis.comm.vo.LogVO;


/**
 * 공통 서비스 클래스
 * @author SOC SOFT
 *
 */
public interface CommService {
	/**
	 * 읍/면/동 기준정보 조회
	 * @return
	 */
	List<?> selectStdDong(CommVO commVo);
	/**
	 * 읍/면/동 조회
	 * @return
	 */
	List<?> selectDongList(CommVO commVo);	  //행정동
	List<?> selectCstDongList(CommVO commVo); //법정동 (다중)
	/**
	 * 조회 읍면동 awk 가져오기	 
	 */
	List<?> selectDongWkt(CommVO commVo);    //행정동
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
	
	///////////////////////////////////////////////////////////////////////////
	///////////////// 공통 util /////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * 동적 테이블 생성 쿼리 생성
	 */	
	String createDynamicTable(String tbl, int start, int end);
	
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
	 * 구간 가져오기
	 */
	int getImgGrade(long max, int min, long val);
	/**
	 * 외곽선 정보 가져오기	 
	 */
	Color getSrokeColor();
	/**
	 * COLOR 가져오기
	 */
	Color getImgColor(String chk, int grade, int alpha);
	/**
	 * 여러동 쿼리 용이한 동코드로 변환
	 */
	String changeCommaInfo(String dongcd);
	
	/**
	 * string to image
	 */
	BufferedImage getStringImg(String img);
	
	/**
	 * export excel
	 */
	XSSFWorkbook createExcel(List<String[]> colNames, List<String[]> colIds, String title, List<List<?>> list, 
			BufferedImage mapImg, BufferedImage chartImg);
	
	/**
	 * excel download log save
	 */
	boolean insertExlLog(LogVO logVo);
	
	/**
	 * 버스 노선타입 정보 가져오기	  
	 */
	List<?> selectBusType(CommVO commVO);
	
	/**
	 * 버스 노선 정보	  
	 */
	List<?> selectBusLine(CommVO commVO);
}
