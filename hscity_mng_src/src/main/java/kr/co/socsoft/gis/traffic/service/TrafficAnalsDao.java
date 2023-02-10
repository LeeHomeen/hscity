package kr.co.socsoft.gis.traffic.service;

import java.util.List;

import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 교통 분석 공통 서비스  DAO
 * @author SOC SOFT
 *
 */
@Mapper("trafficAnalsDao")
public interface TrafficAnalsDao {
	/**
	 * 정류장 정보
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectStaList(TrafficAnalsVO trafficVO);
	
	/**
	 * 버스라인정보
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectBusLine(TrafficAnalsVO trafficVO);
}