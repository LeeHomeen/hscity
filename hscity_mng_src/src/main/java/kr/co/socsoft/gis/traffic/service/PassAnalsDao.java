package kr.co.socsoft.gis.traffic.service;

import java.util.List;

import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 통행패턴 분석 서비스  DAO
 * @author SOC SOFT
 *
 */
@Mapper("passAnalsDao")
public interface PassAnalsDao {
	/**
	 * 통행패턴 분석 결과 리스트
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectPassList(TrafficAnalsVO passVO);
	
	/**
	 * 정류장 정보 조회
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectPassStaList(TrafficAnalsVO passVO);
}