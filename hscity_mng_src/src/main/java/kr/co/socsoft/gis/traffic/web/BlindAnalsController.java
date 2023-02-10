package kr.co.socsoft.gis.traffic.web;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import kr.co.socsoft.common.vo.LoginVO;
import kr.co.socsoft.gis.comm.service.CommService;
import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.traffic.service.BlindAnalsService;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsGisVO;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.EgovUserDetailsHelper;


/**
 * 사각지대분석 controller 
 * @author SocSoft
 *
 */
@Controller
public class BlindAnalsController{
	
	@SuppressWarnings("unused")
	private static final Logger logger = (Logger) LogManager.getLogger(BlindAnalsController.class);

	@Resource(name = "commService")
	private CommService commService;
	
	@Resource(name = "blindAnalsService")
	private BlindAnalsService blindAnalsService;	
	
	
	/**
	 * 사각지대 분석_ 결과 LIST
	 */
	 @RequestMapping(value = "/gis/traffic/blindList.do")
	 public String selectBlindList(TrafficAnalsVO blindVO, Model model) throws Exception {
		 model.addAttribute("result", blindAnalsService.selectBlindList(blindVO));
	     return "jsonView";
	 }
	 
	 /**
	  * 사각지대 분석 화면 결과표시
	  */    
	@RequestMapping(value = "/gis/traffic/blindAnals.do")
    public void selectBlindAnals(TrafficAnalsGisVO trafficVO, HttpServletResponse response) throws Exception {
    	BufferedImage image = null;    	
		image = blindAnalsService.createBlindImg(trafficVO);
    	
    	response.setContentType("image/png");
    	ServletOutputStream sos = response.getOutputStream();
    	ImageIO.write(image, "png", sos);
    	sos.flush();
    	sos.close();
    }
	
	/**
	  * 사각지대 범례 정보
	  */
	 @RequestMapping(value = "/gis/traffic/blindLegend.do")
	 public String selectBlindLgdInfo(TrafficAnalsVO blindVO, Model model) throws Exception {		 
		 model.addAttribute("result", blindAnalsService.selectBlindLgdInfo(blindVO));
	     return "jsonView";
	 }
	 
	 /**
     * 엑셀 내보내기
     */
	@RequestMapping(value = "/gis/traffic/blindExcel.do")
    public void exportBlindExcel(ExcelVO excelVo, TrafficAnalsVO blindVo, HttpServletResponse resp) throws Exception {	
		java.io.ByteArrayOutputStream outByteStream  = null;
		ServletOutputStream outStream = null;
		//사용자 id 가져오기
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		excelVo.setUid(loginVO.getUserId());
		try {		
			XSSFWorkbook xlsxWb = blindAnalsService.createExcelInfo(excelVo, blindVo);
			String fileName = excelVo.getFnm() + ".xlsx";
			fileName = new String(fileName.getBytes("euc-kr"), "8859_1");
			resp.setContentType("application/vnd.ms-excel;charset=utf-8");
			resp.setHeader("Content-Disposition", "attachment; fileName=\"" + fileName + "\";");
			
			xlsxWb.write(resp.getOutputStream());
			outByteStream = new java.io.ByteArrayOutputStream();
			xlsxWb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();
			outStream = resp.getOutputStream();
			outStream.write(outArray);
			outStream.flush();
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {	        	
        	outByteStream.close();
        	outStream.close();
			resp.getOutputStream().close();
        }
    }
}