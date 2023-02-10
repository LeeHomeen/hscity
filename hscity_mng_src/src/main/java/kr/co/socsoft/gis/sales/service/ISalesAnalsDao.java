package kr.co.socsoft.gis.sales.service;

import java.util.List;

import kr.co.socsoft.gis.sales.vo.SalesAnalsVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 유입매출분석 서비스  DAO
 * @author SOC SOFT
 *
 */
@Mapper("iSalesAnalsDao")
public interface ISalesAnalsDao {	
	/**
	 * 시도별 분석
	 * @param SalesAnalsVO
	 * @return List
	 */
	List<?> selectSiSalesList(SalesAnalsVO salesVO);
	
	List<?> selectSiSalesArea_geom(SalesAnalsVO salesVO);	
	List<?> selectSiSalesUser_geom(SalesAnalsVO salesVO);
	
	/**
	 * 시군구별 분석
	 * @param SalesAnalsVO
	 * @return List
	 */
	List<?> selectSigunSalesList(SalesAnalsVO salesVO);
	
	List<?> selectSigunSalesArea_geom(SalesAnalsVO salesVO);
	List<?> selectSigunSalesUser_geom(SalesAnalsVO salesVO);
	
	/**
	 * 인접 분석
	 * @param SalesAnalsVO
	 * @return List
	 */
	List<?> selectAdjSalesList(SalesAnalsVO salesVO);
	
	List<?> selectAdjSalesArea_geom(SalesAnalsVO salesVO);	
	List<?> selectAdjSalesUser_geom(SalesAnalsVO salesVO);
}