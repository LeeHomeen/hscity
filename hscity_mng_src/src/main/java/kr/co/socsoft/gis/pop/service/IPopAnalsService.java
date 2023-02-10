package kr.co.socsoft.gis.pop.service;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.pop.vo.PopAnalsVO;


/**
 * 유입인구분석 서비스 클래스
 * @author SOC SOFT
 *
 */
public interface IPopAnalsService {
	/**
	 * 시도별 분석
	 * @param PopAnalsVO
	 * @return List
	 */
	List<?> selectSiIPopList(PopAnalsVO popVO);
	List<?> selectSiGeomList(PopAnalsVO popVO);
	
	/**
	 * 시군구별 분석
	 * @param PopAnalsVO
	 * @return List
	 */
	List<?> selectSigunIPopList(PopAnalsVO popVO);
	List<?> selectSigunGeomList(PopAnalsVO popVO);
	
	/**
	 * 성연령별 분석
	 * @param PopAnalsVO
	 * @return List
	 */
	List<?> selectGAIPopList(PopAnalsVO popVO);
	List<?> selectGAGeomList(PopAnalsVO popVO);
	
	/**
	 * create Excel info
	 */
	XSSFWorkbook createExcelInfo(ExcelVO excelVo, PopAnalsVO popVo);
	
}
