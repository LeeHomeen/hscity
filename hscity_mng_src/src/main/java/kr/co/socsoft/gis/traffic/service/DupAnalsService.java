package kr.co.socsoft.gis.traffic.service;

import java.util.List;

import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;


/**
 * 버스라인 중복도 분석 서비스 클래스
 * @author SOC SOFT
 *
 */
public interface DupAnalsService {
	/**
	 * 버스라인 중복도 조회
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectBusDupList(TrafficAnalsVO dupVO);	
}
