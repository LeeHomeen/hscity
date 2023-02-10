package kr.co.socsoft.gis.spatial.service;

import java.util.List;

import kr.co.socsoft.gis.spatial.vo.SpatialVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * GIS 공간분석 DAO
 * @author SOC SOFT
 *
 */
@Mapper("spatialDao")
public interface SpatialDao {	
	/**
	 * 반경검색시 반경정보 가져오기 
	 * @param SpatialVO
	 * @return List
	 */	 
	List<?> selectRadiusPolygon(SpatialVO spatialVo);
	
	/**
	 * 행정동 정보 가져오기 
	 * @param SpatialVO
	 * @return List
	 */
	List<?> selectDongList(SpatialVO spatialVo);
}