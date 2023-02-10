package kr.co.socsoft.gis.comm.web;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.socsoft.gis.comm.service.CommService;
import kr.co.socsoft.gis.comm.vo.CommVO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 공통 관련 클래스
 * @author SocSoft
 *
 */
@Controller
public class CommController{
	
	@SuppressWarnings("unused")
	private static final Logger logger = (Logger) LogManager.getLogger(CommController.class);

	@Resource(name = "commService")
	private CommService commService;
	
	/**
     * 동 기준정보를 가져온다.
     */
    @RequestMapping(value = "/gis/comm/stdList.do")
    public String selectStdDong(CommVO commVo, Model model) throws Exception {
        model.addAttribute("result", commService.selectStdDong(commVo));
        return "jsonView";
    }
    
    /**
     * 업종분류_대분류
     */
    @RequestMapping(value = "/gis/comm/lClasList.do")
    public String selectLclasList(CommVO commVo, Model model) throws Exception {
        model.addAttribute("result", commService.selectLclasList(commVo));
        return "jsonView";
    }
    
    /**
     * 업종분류_중분류
     */
    @RequestMapping(value = "/gis/comm/mClasList.do")
    public String selectMclasList(CommVO commVo, Model model) throws Exception {
        model.addAttribute("result", commService.selectMclasList(commVo));
        return "jsonView";
    }
    
    /**
     * 업종분류_소분류
     */
    @RequestMapping(value = "/gis/comm/sClasList.do")
    public String selectSclasList(CommVO commVo, Model model) throws Exception {
        model.addAttribute("result", commService.selectSclasList(commVo));
        return "jsonView";
    }
	
	/**
     * 반경 폴리곤 정보를 가져온다.
     */
    @RequestMapping(value = "/gis/comm/dongList.do")
    public String selectDongList(CommVO commVo, Model model) throws Exception {
        model.addAttribute("result", commService.selectDongList(commVo));
        return "jsonView";
    }
    
    /**
     * 반경 폴리곤 정보를 가져온다.(법정동) 사용안함
     */
    @RequestMapping(value = "/gis/comm/cstDongList.do")
    public String selectCstDongList(CommVO commVo, Model model) throws Exception {
        model.addAttribute("result", commService.selectCstDongList(commVo));
        return "jsonView";
    }
        
    
    /**
     * 선택된 행정동 bounds를 가져온다. 
     */
    @RequestMapping(value = "/gis/comm/dongWkt.do")
    public String selectDongWkt(CommVO commVo, Model model) throws Exception {    	
        model.addAttribute("result", commService.selectDongWkt(commVo));
        return "jsonView";
    }
    
    /**
     * 선택된 법정동 bounds를 가져온다. 
     */
    @RequestMapping(value = "/gis/comm/cstDongWkt.do")
    public String selectCstDongWkt(CommVO commVo, Model model) throws Exception {    	
        model.addAttribute("result", commService.selectCstDongWkt(commVo));
        return "jsonView";
    }
    
    /**
     * 사용자 지정영역정보 가져오기
     */
    @RequestMapping(value = "/gis/comm/bmkList.do")
    public String selectBookmarkList(CommVO commVo, Model model) throws Exception {
    	commVo.setUserid("test");
        model.addAttribute("result", commService.selectBookmarkList(commVo));
        return "jsonView";
    }
    
    /**
     * 버스타입 정보 가져오기 
     */
    @RequestMapping(value = "/gis/comm/busType.do")
    public String selectBusType(CommVO commVo, Model model) throws Exception {
        model.addAttribute("result", commService.selectBusType(commVo));
        return "jsonView";
    }
    
    /**
     * 버스라인 정보 가져오기 
     */
    @RequestMapping(value = "/gis/comm/busLine.do")
    public String selectBusLine(CommVO commVo, Model model) throws Exception {
        model.addAttribute("result", commService.selectBusLine(commVo));
        return "jsonView";
    }
    
    /**
     * 사용자 지정영역정보 저장
     */
    @SuppressWarnings("finally")
	@RequestMapping(value = "/gis/comm/addBmk.do")
    public String insertBookmark(CommVO commVo, Model model) throws Exception {
    	commVo.setUserid("test");
    	String result = "err";
    	try{
    		commService.insertBookmark(commVo);
    		result = "ok";
    	}catch(Exception e){
    		result = "err";
    	}finally{
    		model.addAttribute("result", result);
            return "jsonView";
    	}        
    }
    
    /**
     * 사용자 지정영역정보 삭제
     */
    @SuppressWarnings("finally")
	@RequestMapping(value = "/gis/comm/delBmk.do")
    public String deleteBookmark(CommVO commVo, Model model) throws Exception {
    	commVo.setUserid("test");
    	String result = "err";
    	try{
    		commService.deleteBookmark(commVo);
    		result = "ok";
    	}catch(Exception e){
    		result = "err";
    	}finally{
    		model.addAttribute("result", result);
            return "jsonView";
    	}        
    } 
    
    /**
     * 테이블 정보 가져오기 
     * (테이블 기간정보) 
     */
    @RequestMapping(value = "/gis/comm/tblPeriod.do")
    public String selectTableInfo(CommVO commVo, Model model) throws Exception {
        model.addAttribute("result", commService.selectTblPeriod(commVo));
        return "jsonView";
    }
    
    /**
     * 기준년월 최근 데이터 가져오기 
     */
    @RequestMapping(value = "/gis/comm/recentPeriod.do")
    public String selectPeriodInfo(CommVO commVO, Model model) throws Exception {
        model.addAttribute("result", commService.selectRecentPeriod(commVO));
        return "jsonView";
    }
    
    /**
     * proxy
     */
    @RequestMapping(value = "/gis/comm/proxy.do")
    public void proxy(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String url = null;
		url = request.getParameter("url");
		URL imageURL = new URL(url);		
		BufferedImage img = ImageIO.read(imageURL);
	   	OutputStream ostream = response.getOutputStream();
		ImageIO.write(img, "png", ostream);
		ostream.flush();
		ostream.close();		
		/*
		 * String imageString = null;
	   	ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] imageBytes = bos.toByteArray();
		BASE64Encoder encoder = new BASE64Encoder();  
        	imageString = encoder.encode(imageBytes); 
        	imageString = imageString.replaceAll("\r\n", "");
        	imageString = imageString.replaceAll("\r", "");
        	imageString = imageString.replaceAll("\n", "");
		
		//byte[] callbackBytes = (callback + "(\"data:image/png;base64," + imageString + "\")").getBytes();
        byte[] callbackBytes = ("(\"data:image/png;base64," + imageString + "\")").getBytes();
		
		OutputStream ostream = response.getOutputStream(); 
		response.setContentType("application/octet-stream; charset=utf-8");
		response.addHeader("Content-Disposition", "inline;filename=\"image.png\"");
		ostream.write(callbackBytes);
		ostream.flush();
		ostream.close();
		*/
    }
}