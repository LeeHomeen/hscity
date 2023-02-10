package kr.co.socsoft.gis.sales.service;

import java.util.List;

import kr.co.socsoft.gis.pop.vo.PopAnalsGisVO;
import kr.co.socsoft.gis.sales.vo.SalesAnalsVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 매출분석 서비스  DAO
 * @author SOC SOFT
 *
 */
@Mapper("salesAnalsDao")
public interface SalesAnalsDao {	
	/**
	 * 기간별 분석 (국가기초구역)
	 * @param SalesAnalsVO
	 * @return List
	 */
	List<?> selectYearSalesList_area(SalesAnalsVO salesVO);
	List<?> selectYearSalesList_user(SalesAnalsVO salesVO);
	List<?> selectYearSales_map(PopAnalsGisVO popVO);
	
	/**
	 * 성 연령별 분석 (국가기초구역)
	 * @param SalesAnalsVO
	 * @return List
	 */
	List<?> selectGASalesList_area(SalesAnalsVO salesVO);
	List<?> selectGASalesList_user(SalesAnalsVO salesVO);
	List<?> selectGASales_map(PopAnalsGisVO popVO);
	
	/**
	 * 시간대별 분석 (국가기초지역)
	 * @param SalesAnalsVO
	 * @return List
	 */
	List<?> selectTimeSalesList_area(SalesAnalsVO salesVO);	
	List<?> selectTimeSalesList_user(SalesAnalsVO salesVO);
	List<?> selectTimeSales_map(PopAnalsGisVO popVO);
	
	/**
	 * 업종별 분석 (국가기초지역)
	 * @param SalesAnalsVO
	 * @return List
	 */
	List<?> selectCtgSalesList_area(SalesAnalsVO salesVO);	
	List<?> selectCtgSalesList_user(SalesAnalsVO salesVO);
	List<?> selectCtgSales_map(PopAnalsGisVO popVO);	
	
	/**
	 * 외국인 분석 (국가기초지역)
	 * @param SalesAnalsVO
	 * @return List
	 */
	List<?> selectFgnSalesList_area(SalesAnalsVO salesVO);	
	List<?> selectFgnSalesList_user(SalesAnalsVO salesVO);
	List<?> selectFgnSales_map(PopAnalsGisVO popVO);
	
	/**
	 * 범례정보
	 */
	List<?> selectSaleYearLgdInfo(SalesAnalsVO salesVO); //기간별
	List<?> selectSaleGALgdInfo(SalesAnalsVO salesVO);   //성연령별
	List<?> selectSaleTimeLgdInfo(SalesAnalsVO salesVO); //시간대별
	List<?> selectSaleCtgLgdInfo(SalesAnalsVO salesVO);  //업종별
	List<?> selectSaleFngLgdInfo(SalesAnalsVO salesVO);  //외국인별
}