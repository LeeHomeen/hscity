package kr.co.socsoft.gis.traffic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.co.socsoft.gis.comm.service.CommService;
import kr.co.socsoft.gis.traffic.service.DupAnalsDao;
import kr.co.socsoft.gis.traffic.service.DupAnalsService;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;


/**
 * 버스라인 중복도 서비스  SERVICE
 * @author SOC SOFT
 *
 */
@Service("dupAnalsService")
public class DupAnalsServiceImpl implements DupAnalsService{

	@Resource(name = "commService")
	private CommService commService;
	
	@Resource(name = "dupAnalsDao")
	private DupAnalsDao dao;	
	
	/**
	 * 버스라인 중복도분석 결과 리스트
	 * @param TrafficAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectBusDupList(TrafficAnalsVO dupVO){		
		return dao.selectBusDupList(dupVO);
	}	
}
