package kr.co.socsoft.gis.traffic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.co.socsoft.gis.traffic.service.TrafficAnalsDao;
import kr.co.socsoft.gis.traffic.service.TrafficAnalsService;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;


/**
 * 인구분석 서비스  SERVICE
 * @author SOC SOFT
 *
 */
@Service("trafficAnalsService")
public class TrafficAnalsServiceImpl implements TrafficAnalsService{
	
	@Resource(name = "trafficAnalsDao")
	private TrafficAnalsDao dao;	
	
	/**
	 * 정류장 정보 리스트
	 * @param TrafficAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectStaList(TrafficAnalsVO trafficVO){
		return dao.selectStaList(trafficVO);
	}
	
	/**
	 * 버스라인정보
	 * @param TrafficAnalsVO
	 * @return List
	 */
	public List<?> selectBusLine(TrafficAnalsVO trafficVO){
		return dao.selectBusLine(trafficVO);
	}
}
