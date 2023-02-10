package kr.co.socsoft.gis.sales.service;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.sales.vo.SalesAnalsVO;


/**
 * 유입매출분석 서비스 클래스
 * @author SOC SOFT
 *
 */
public interface ISalesAnalsService {
	/**
	 * 시도별 분석
	 * @param SalesAnalsVO
	 * @return List
	 */
	List<?> selectSiSalesList(SalesAnalsVO salesVO);
	List<?> selectSiGeomList(SalesAnalsVO salesVO);
	
	/**
	 * 시군구별 분석
	 * @param SalesAnalsVO
	 * @return List
	 */
	List<?> selectSigunSalesList(SalesAnalsVO salesVO);
	List<?> selectSigunGeomList(SalesAnalsVO salesVO);
	
	/**
	 * 인접 분석
	 * @param SalesAnalsVO
	 * @return List
	 */
	List<?> selectAdjSalesList(SalesAnalsVO salesVO);	
	List<?> selectAdjGeomList(SalesAnalsVO salesVO);
	
	/**
	 * create Excel info
	 */
	XSSFWorkbook createExcelInfo(ExcelVO excelVo, SalesAnalsVO salesVo);
}
