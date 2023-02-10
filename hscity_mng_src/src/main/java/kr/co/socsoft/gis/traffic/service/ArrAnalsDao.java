package kr.co.socsoft.gis.traffic.service;

import java.util.List;

import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 탄력배치분석 서비스  DAO
 * @author SOC SOFT
 *
 */
@Mapper("arrAnalsDao")
public interface ArrAnalsDao {
	
	/**
	 * 요일별 승차인원
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectArrWeekList(TrafficAnalsVO facVO);	
	
	/**
	 * 청두 미청두
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectArrTimeList(TrafficAnalsVO facVO);	
}