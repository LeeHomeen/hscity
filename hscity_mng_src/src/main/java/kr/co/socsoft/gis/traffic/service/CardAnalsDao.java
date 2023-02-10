package kr.co.socsoft.gis.traffic.service;

import java.util.List;

import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 교통카드 현황분석 서비스  DAO
 * @author SOC SOFT
 *
 */
@Mapper("cardAnalsDao")
public interface CardAnalsDao {
	/**
	 * 교통카드 현황분석 결과 리스트
	 * @param BlindAnalsVO
	 * @return List
	 */
	List<?> selectCardList(TrafficAnalsVO cardVO);		
}