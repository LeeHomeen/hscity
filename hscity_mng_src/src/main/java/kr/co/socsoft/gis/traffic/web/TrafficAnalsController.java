package kr.co.socsoft.gis.traffic.web;

import javax.annotation.Resource;

import kr.co.socsoft.gis.comm.service.CommService;
import kr.co.socsoft.gis.traffic.service.TrafficAnalsService;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 교통분석 공통 controller 
 * @author SocSoft
 *
 */
@Controller
public class TrafficAnalsController{
	
	@SuppressWarnings("unused")
	private static final Logger logger = (Logger) LogManager.getLogger(TrafficAnalsController.class);

	@Resource(name = "commService")
	private CommService commService;
	
	@Resource(name = "trafficAnalsService")
	private TrafficAnalsService trafficAnalsService;	
	
	/**
	 * 정류장 정보 제공
	 */
	 @RequestMapping(value = "/gis/traffic/staList.do")
	 public String selectStaList(TrafficAnalsVO trafficVO, Model model) throws Exception {
		 model.addAttribute("result", trafficAnalsService.selectStaList(trafficVO));
	     return "jsonView";
	 }
	 
	 /**
	 * 버스노선 정보 제공
	 */
	 @RequestMapping(value = "/gis/traffic/lineList.do")
	 public String selectBusLineList(TrafficAnalsVO trafficVO, Model model) throws Exception {
		 model.addAttribute("result", trafficAnalsService.selectBusLine(trafficVO));
	     return "jsonView";
	 }
}