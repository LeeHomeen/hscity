package kr.co.socsoft.gis.traffic.service;

import java.util.List;

import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 환승편의시설 분석 서비스  DAO
 * @author SOC SOFT
 *
 */
@Mapper("facAnalsDao")
public interface FacAnalsDao {
	/**
	 * 환승편의시설 분석 결과 리스트
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectFacList(TrafficAnalsVO facVO);	
}