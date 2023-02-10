package kr.co.socsoft.gis.spatial.service.impl;

import java.util.List;

import javax.annotation.Resource;

import kr.co.socsoft.gis.spatial.service.SpatialDao;
import kr.co.socsoft.gis.spatial.service.SpatialService;
import kr.co.socsoft.gis.spatial.vo.SpatialVO;

import org.springframework.stereotype.Service;

/**
 * GIS 공간분석 구현 SERVICE
 * @author Administrator
 *
 */
@Service("spatialService")
public class SpatialServiceImpl implements SpatialService{
	
	@Resource(name = "spatialDao")
	private SpatialDao dao;
	
	/**
	 * 반경검색시 반경정보(폴리곤) 가져오기 
	 */
	@Override
	public List<?> selectRadiusPolygon(SpatialVO spatialVo){
		return dao.selectRadiusPolygon(spatialVo);
	}		
	
	/**
	 * 행정동 정보 가져오기 
	 * @param SpatialVO
	 * @return
	 */
	@Override
	public List<?> selectDongList(SpatialVO spatialVo){
		return dao.selectDongList(spatialVo);
	}
}
