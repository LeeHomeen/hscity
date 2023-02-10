package kr.co.socsoft.gis.spatial.service;

import java.util.List;

import kr.co.socsoft.gis.spatial.vo.SpatialVO;


/**
 * gis공통 및 공간분석 서비스 클래스
 * @author SOC SOFT
 *
 */
public interface SpatialService {
	/**
	 * 구급활동 기준년도 조회
	 * @return
	 */
	List<?> selectRadiusPolygon(SpatialVO spatialVo);
	
	
	/**
	 * 행정동 정보 가져오기 
	 */
	List<?> selectDongList(SpatialVO spatialVo);
}
