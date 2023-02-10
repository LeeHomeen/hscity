package kr.co.socsoft.gis.pop.service;

import java.util.List;

import kr.co.socsoft.gis.pop.vo.PopAnalsGisVO;
import kr.co.socsoft.gis.pop.vo.PopAnalsVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 인구분석 서비스  DAO
 * @author SOC SOFT
 *
 */
@Mapper("popAnalsDao")
public interface PopAnalsDao {	
	/**
	 * 기간별 분석 (밀도맵, 격자, 벌집)
	 * @param PopAnalsVO
	 * @return List
	 */
	List<?> selectYearPopList(PopAnalsVO popVO);
	List<?> selectYearPop_heat(PopAnalsGisVO popVO);	
	List<?> selectYearPop_grid(PopAnalsGisVO popVO);
	List<?> selectYearPop_beehive(PopAnalsGisVO popVO);
	
	/**
	 * 성 연령별 분석 (밀도맵, 격자, 벌집)
	 * @param PopAnalsVO
	 * @return List
	 */
	List<?> selectGAPopList(PopAnalsVO popVO);
	List<?> selectGAPop_heat(PopAnalsGisVO popVO);
	List<?> selectGAPop_grid(PopAnalsGisVO popVO);
	List<?> selectGAPop_beehive(PopAnalsGisVO popVO);
	
	/**
	 * 전입 분석  지역/요인/geom정보
	 * @param PopAnalsVO
	 * @return List
	 */
	List<?> selectInPopCntList(PopAnalsVO popVO);
	List<?> selectInPopResnList(PopAnalsVO popVO);
	List<?> selectInPopGeomList(PopAnalsVO popVO);
	
	/**
	 * 전출 분석 지역/요인/geom
	 * @param PopAnalsVO
	 * @return List
	 */
	List<?> selectOutPopCntList(PopAnalsVO popVO);
	List<?> selectOutPopResnList(PopAnalsVO popVO);
	List<?> selectOutPopGeomList(PopAnalsVO popVO);
	
	/**
	 * 범례를 위한 min, max값 가져오기
	 */
	List<?> selectPopYearLgdInfo(PopAnalsVO popVO); //기간별 분석 범례정보 min max
	List<?> selectPopGALgdInfo(PopAnalsVO popVO);   //성연령별 분석 범례정보 min max
}