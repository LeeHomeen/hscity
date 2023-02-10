package kr.co.socsoft.gis.traffic.service;

import java.util.List;

import kr.co.socsoft.gis.traffic.vo.TrafficAnalsGisVO;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 저상버스 도입요구분석 서비스  DAO
 * @author SOC SOFT
 *
 */
@Mapper("busAnalsDao")
public interface BusAnalsDao {
	/**
	 * 저장버스도입 요구분석 결과 리스트
	 * @param BlindAnalsVO
	 * @return List
	 */
	List<?> selectBusList(TrafficAnalsVO busVO);
	
	/**
	 * 노인격자 화면 처리
	 * @param TrafficAnalsGisVO
	 * @return List
	 */
	List<?> selectBusAnals(TrafficAnalsGisVO blindVO);	
	
	/**
	 * 범례를 위한 min, max값 가져오기
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectBusLgdInfo(TrafficAnalsVO blindVO);
}