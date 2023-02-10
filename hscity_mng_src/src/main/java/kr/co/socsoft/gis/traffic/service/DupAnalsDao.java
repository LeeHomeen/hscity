package kr.co.socsoft.gis.traffic.service;

import java.util.List;

import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 버스라인 중복도 분석 서비스  DAO
 * @author SOC SOFT
 *
 */
@Mapper("dupAnalsDao")
public interface DupAnalsDao {
	/**
	 * 버스라인 중복도 조회
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectBusDupList(TrafficAnalsVO dupVO);			
}