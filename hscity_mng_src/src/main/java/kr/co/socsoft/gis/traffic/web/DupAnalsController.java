package kr.co.socsoft.gis.traffic.web;

import javax.annotation.Resource;

import kr.co.socsoft.gis.comm.service.CommService;
import kr.co.socsoft.gis.traffic.service.DupAnalsService;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 버스라인 중복도 분석 controller 
 * @author SocSoft
 *
 */
@Controller
public class DupAnalsController{
	
	@SuppressWarnings("unused")
	private static final Logger logger = (Logger) LogManager.getLogger(DupAnalsController.class);

	@Resource(name = "commService")
	private CommService commService;
	
	@Resource(name = "dupAnalsService")
	private DupAnalsService dupAnalsService;	
	
	
	/**
	 * 버스라인 중복도 분석_ 결과 LIST
	 */
	 @RequestMapping(value = "/gis/traffic/dupList.do")
	 public String selectBusDupList(TrafficAnalsVO facVO, Model model) throws Exception {
		 model.addAttribute("result", dupAnalsService.selectBusDupList(facVO));
	     return "jsonView";
	 }
}