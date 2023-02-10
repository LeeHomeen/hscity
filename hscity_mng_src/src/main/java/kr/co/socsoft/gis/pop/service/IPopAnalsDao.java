package kr.co.socsoft.gis.pop.service;

import java.util.List;

import kr.co.socsoft.gis.pop.vo.PopAnalsVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 유입인구분석 서비스  DAO
 * @author SOC SOFT
 *
 */
@Mapper("iPopAnalsDao")
public interface IPopAnalsDao {	
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
}