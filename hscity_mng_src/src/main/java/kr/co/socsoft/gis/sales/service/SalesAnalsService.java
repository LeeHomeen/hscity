package kr.co.socsoft.gis.sales.service;

import java.awt.image.BufferedImage;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.pop.vo.PopAnalsGisVO;
import kr.co.socsoft.gis.pop.vo.PopAnalsVO;
import kr.co.socsoft.gis.sales.vo.SalesAnalsVO;


/**
 * 매출분석 서비스 클래스
 * @author SOC SOFT
 *
 */
public interface SalesAnalsService {
	/**
	 * 기간별 분석 (국가기초지역)
	 * @param SalesAnalsVO
	 * @return List
	 */
	List<?> selectYearSalesList(SalesAnalsVO salesVO);	
	List<?> selectYearSales_map(PopAnalsGisVO popVO);
	
	BufferedImage createSalesImg(PopAnalsGisVO popVO);
	
	/**
	 * 성 연령별 분석 (국가기초지역)
	 * @param SalesAnalsVO
	 * @return List
	 */
	List<?> selectGASalesList(SalesAnalsVO salesVO);
	List<?> selectGASales_map(PopAnalsGisVO popVO);	
	
	/**
	 * 시간대별 분석 (국가기초지역)
	 * @param SalesAnalsVO
	 * @return List
	 */
	List<?> selectTimeSalesList(SalesAnalsVO salesVO);	
	List<?> selectTimeSales_map(PopAnalsGisVO popVO);	
	
	/**
	 * 업종별 분석 (국가기초지역)
	 * @param SalesAnalsVO
	 * @return List
	 */
	List<?> selectCtgSalesList(SalesAnalsVO salesVO);	
	List<?> selectCtgSales_map(PopAnalsGisVO popVO);
	
	/**
	 * 외국인 분석 (국가기초지역)
	 * @param SalesAnalsVO
	 * @return List
	 */
	List<?> selectFgnSalesList(SalesAnalsVO salesVO);	
	List<?> selectFgnSales_map(PopAnalsGisVO popVO);
	
	/**
	 * 범례정보
	 */
	List<?> selectSaleYearLgdInfo(SalesAnalsVO salesVO); //기간별
	List<?> selectSaleGALgdInfo(SalesAnalsVO salesVO);   //성연령별
	List<?> selectSaleTimeLgdInfo(SalesAnalsVO salesVO); //시간대별
	List<?> selectSaleCtgLgdInfo(SalesAnalsVO salesVO);  //업종별
	List<?> selectSaleFngLgdInfo(SalesAnalsVO salesVO);  //외국인별
	
	/**
	 * create Excel info
	 */
	XSSFWorkbook createExcelInfo(ExcelVO excelVo, SalesAnalsVO salesVo);
}
