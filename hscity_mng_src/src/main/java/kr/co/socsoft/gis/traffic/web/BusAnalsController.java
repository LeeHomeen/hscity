package kr.co.socsoft.gis.traffic.web;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import kr.co.socsoft.common.vo.LoginVO;
import kr.co.socsoft.gis.comm.service.CommService;
import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.pop.vo.PopAnalsVO;
import kr.co.socsoft.gis.traffic.service.BusAnalsService;
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
 * 저상버스도입요구분석 controller 
 * @author SocSoft
 *
 */
@Controller
public class BusAnalsController{
	
	@SuppressWarnings("unused")
	private static final Logger logger = (Logger) LogManager.getLogger(BusAnalsController.class);

	@Resource(name = "commService")
	private CommService commService;
	
	@Resource(name = "busAnalsService")
	private BusAnalsService busAnalsService;	
	
	
	/**
	 * 저상버스 분석_ 결과 LIST
	 */
	 @RequestMapping(value = "/gis/traffic/busList.do")
	 public String selectBusList(TrafficAnalsVO busVO, Model model) throws Exception {
		 model.addAttribute("result", busAnalsService.selectBusList(busVO));
	     return "jsonView";
	 }
	 
	 /**
	  * 노인격자 분석 화면 결과표시
	  */    
	@RequestMapping(value = "/gis/traffic/busAnals.do")
    public void selectBusAnals(TrafficAnalsGisVO trafficVO, HttpServletResponse response) throws Exception {
    	BufferedImage image = null;    	
		image = busAnalsService.createBusImg(trafficVO);
    	
    	response.setContentType("image/png");
    	ServletOutputStream sos = response.getOutputStream();
    	ImageIO.write(image, "png", sos);
    	sos.flush();
    	sos.close();
    }
	
	/**
	  * 노인격자 범례 정보
	  */
	 @RequestMapping(value = "/gis/traffic/busLegend.do")
	 public String selectBlindLgdInfo(TrafficAnalsVO busVO, Model model) throws Exception {		 
		 model.addAttribute("result", busAnalsService.selectBusLgdInfo(busVO));
	     return "jsonView";
	 }
	 
	 /**
     * 엑셀 내보내기
     */
	@RequestMapping(value = "/gis/traffic/busExcel.do")
    public void exportBusExcel(ExcelVO excelVo, TrafficAnalsVO busVo, HttpServletResponse resp) throws Exception {	
		java.io.ByteArrayOutputStream outByteStream  = null;
		ServletOutputStream outStream = null;
		//사용자 id 가져오기
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		excelVo.setUid(loginVO.getUserId());
		try {		
			XSSFWorkbook xlsxWb = busAnalsService.createExcelInfo(excelVo, busVo);
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