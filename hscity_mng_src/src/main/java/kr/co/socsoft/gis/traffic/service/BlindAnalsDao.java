package kr.co.socsoft.gis.traffic.service;

import java.util.List;

import kr.co.socsoft.gis.traffic.vo.TrafficAnalsGisVO;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 사각지대 분석 서비스  DAO
 * @author SOC SOFT
 *
 */
@Mapper("blindAnalsDao")
public interface BlindAnalsDao {
	/**
	 * 사각지대 분석 결과 리스트
	 * @param BlindAnalsVO
	 * @return List
	 */
	List<?> selectBlindList(TrafficAnalsVO blindVO);
	
	/**
	 * 사각지대 화면 처리
	 * @param TrafficAnalsGisVO
	 * @return List
	 */
	List<?> selectBlindAnals(TrafficAnalsGisVO trafficVO);
	
	/**
	 * 범례를 위한 min, max값 가져오기
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectBlindLgdInfo(TrafficAnalsVO blindVO);
}