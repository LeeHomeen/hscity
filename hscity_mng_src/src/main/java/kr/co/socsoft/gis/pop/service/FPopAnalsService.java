package kr.co.socsoft.gis.pop.service;

import java.awt.image.BufferedImage;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.pop.vo.PopAnalsGisVO;
import kr.co.socsoft.gis.pop.vo.PopAnalsVO;


/**
 * 유동인구분석 서비스 클래스
 * @author SOC SOFT
 *
 */
public interface FPopAnalsService {
	
	/**
	 * 기간별 분석 (밀도맵, 격자, 벌집)
	 * @param PopAnalsVO
	 * @return List
	 */
	List<?> selectYearFPopList(PopAnalsVO popVO);
	List<?> selectYearFPop_heat(PopAnalsGisVO popVO);	
	List<?> selectYearFPop_grid(PopAnalsGisVO popVO);
	List<?> selectYearFPop_beehive(PopAnalsGisVO popVO);
	
	/**
	 * 성 연령별 분석 (밀도맵, 격자, 벌집)
	 * @param PopAnalsVO
	 * @return List
	 */
	List<?> selectGAFPopList(PopAnalsVO popVO);
	List<?> selectGAFPop_heat(PopAnalsGisVO popVO);
	List<?> selectGAFPop_grid(PopAnalsGisVO popVO);
	List<?> selectGAFPop_beehive(PopAnalsGisVO popVO);
	
	/**
	 * 시간대별 분석 (밀도맵, 격자, 벌집)
	 * @param PopAnalsVO
	 * @return List
	 */
	List<?> selectTimeFPopList(PopAnalsVO popVO);
	List<?> selectTimeFPop_heat(PopAnalsGisVO popVO);
	List<?> selectTimeFPop_grid(PopAnalsGisVO popVO);
	List<?> selectTimeFPop_beehive(PopAnalsGisVO popVO);
	
	/**
	 * 이미지 생성
	 * @param popVO
	 * @return
	 */
	BufferedImage createFPopImg(PopAnalsGisVO popVO);
	
	/**
	 * 범례를 위한 min, max값 가져오기
	 */
	List<?> selectFPopYearLgdInfo(PopAnalsVO popVO); //기간별 분석 범례정보 min max
	List<?> selectFPopGALgdInfo(PopAnalsVO popVO);   //성연령별 분석 범례정보 min max
	List<?> selectFPopTimeLgdInfo(PopAnalsVO popVO); //시간대별 분석 범례정보 min max
	
	/**
	 * create Excel info
	 */
	XSSFWorkbook createExcelInfo(ExcelVO excelVo, PopAnalsVO popVo);
	
}
