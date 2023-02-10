package kr.co.socsoft.gis.spatial.web;

import javax.annotation.Resource;

import kr.co.socsoft.gis.spatial.service.SpatialService;
import kr.co.socsoft.gis.spatial.vo.SpatialVO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * GIS 공간분석 및 공통 관련 클래스
 * @author SocSoft
 *
 */
@Controller
public class SpatialController{
	
	@SuppressWarnings("unused")
	private static final Logger logger = (Logger) LogManager.getLogger(SpatialController.class);

	@Resource(name = "spatialService")
	private SpatialService spatialService;
	
	/**
     * 반경 폴리곤 정보를 가져온다.
     */
    @RequestMapping(value = "/gis/raiusPolygon.do")
    public String selectRadiusPolygon(SpatialVO spatialVo, Model model) throws Exception {
        model.addAttribute("result", spatialService.selectRadiusPolygon(spatialVo));
        return "jsonView";
    }
}