package kr.co.socsoft.gis.pop.web;

import java.awt.image.BufferedImage;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import kr.co.socsoft.common.vo.LoginVO;
import kr.co.socsoft.gis.comm.service.CommService;
import kr.co.socsoft.gis.comm.util.HeatMap;
import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.pop.service.FPopAnalsService;
import kr.co.socsoft.gis.pop.vo.PopAnalsGisVO;
import kr.co.socsoft.gis.pop.vo.PopAnalsVO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.EgovUserDetailsHelper;



/**
 * 유동인구분석 controller 
 * @author SocSoft
 *
 */
@Controller
public class FPopAnalsController{
	
	@SuppressWarnings("unused")
	private static final Logger logger = (Logger) LogManager.getLogger(FPopAnalsController.class);

	@Resource(name = "commService")
	private CommService commService;
	
	@Resource(name = "fPopAnalsService")
	private FPopAnalsService fPopAnalsService;	
	
	/**
	 * 유동인구분석_기간별별 분석 결과 LIST
	 */
	 @RequestMapping(value = "/gis/fpop/fPopYearList.do")
	 public String fPopYearList(PopAnalsVO popVO, Model model) throws Exception {
		 model.addAttribute("result", fPopAnalsService.selectYearFPopList(popVO));
	     return "jsonView";
	 }
	
	 /**
     * 유동인구분석 기간별 분석_격자, 벌집이미지 생성
     */    
	@RequestMapping(value = "/gis/fpop/fPopYearAnals.do")
    public void selectYearFPopAnals(PopAnalsGisVO popVO, HttpServletResponse response) throws Exception {
    	BufferedImage image = null;
    	String vtype = popVO.getVTYPE().trim();
    	
    	if(vtype.equals("heat")){
    		List<?> list = fPopAnalsService.selectYearFPop_heat(popVO);
    		HeatMap heat = new HeatMap(popVO);
    		heat.loadData_list(list);
    		heat.drawOuput_list(true);
    		image = heat.heatmapImage;
    	}else{
    		//격자, 벌집 분석
    		popVO.setMENU("year");
    		image = fPopAnalsService.createFPopImg(popVO);
    	}	
    	
    	response.setContentType("image/png");
    	ServletOutputStream sos = response.getOutputStream();
    	ImageIO.write(image, "png", sos);
    	sos.flush();
    	sos.close();
    }
	
	
	/**
	 * 유동인구분석_성연령별 분석 결과 LIST
	 */
	 @RequestMapping(value = "/gis/fpop/fPopGAList.do")
	 public String fPopGAList(PopAnalsVO popVO, Model model) throws Exception {
		 model.addAttribute("result", fPopAnalsService.selectGAFPopList(popVO));
	     return "jsonView";
	 }
	
	 /**
     * 유동인구분석 성연령별 분석_격자, 벌집이미지 생성
     */    
	@RequestMapping(value = "/gis/fpop/fPopGAAnals.do")
    public void selectGAFPopAnals(PopAnalsGisVO popVO, HttpServletResponse response) throws Exception {
    	BufferedImage image = null;
    	String vtype = popVO.getVTYPE().trim();
    	
    	if(vtype.equals("heat")){
    		List<?> list = fPopAnalsService.selectGAFPop_heat(popVO);
    		HeatMap heat = new HeatMap(popVO);
    		heat.loadData_list(list);
    		heat.drawOuput_list(true);
    		image = heat.heatmapImage;
    	}else{
    		//격자, 벌집 분석
    		popVO.setMENU("age");
    		image = fPopAnalsService.createFPopImg(popVO);
    	}	
    	
    	response.setContentType("image/png");
    	ServletOutputStream sos = response.getOutputStream();
    	ImageIO.write(image, "png", sos);
    	sos.flush();
    	sos.close();
    }
		
	
	/**
	 * 유동인구분석_시간대별 분석 결과 LIST
	 */
	 @RequestMapping(value = "/gis/fpop/fPopTimeList.do")
	 public String fPopTimeList(PopAnalsVO popVO, Model model) throws Exception {
		 model.addAttribute("result", fPopAnalsService.selectTimeFPopList(popVO));
	     return "jsonView";
	 }
	
	 /**
     * 유동인구분석 시간대별 분석_격자, 벌집이미지 생성
     */    
	@RequestMapping(value = "/gis/fpop/fPopTimeAnals.do")
    public void selectTimeFPopAnals(PopAnalsGisVO popVO, HttpServletResponse response) throws Exception {
    	BufferedImage image = null;
    	String vtype = popVO.getVTYPE().trim();
    	
    	if(vtype.equals("heat")){
    		List<?> list = fPopAnalsService.selectTimeFPop_heat(popVO);
    		HeatMap heat = new HeatMap(popVO);
    		heat.loadData_list(list);
    		heat.drawOuput_list(true);
    		image = heat.heatmapImage;
    	}else{
    		//격자, 벌집 분석
    		popVO.setMENU("time");
    		image = fPopAnalsService.createFPopImg(popVO);
    	}	
    	
    	response.setContentType("image/png");
    	ServletOutputStream sos = response.getOutputStream();
    	ImageIO.write(image, "png", sos);
    	sos.flush();
    	sos.close();
    }
	
	/**
	  * 범례 정보 (기간별)
	  */
	 @RequestMapping(value = "/gis/fpop/fPopYearLegend.do")
	 public String selectFPopYearLgdInfo(PopAnalsVO popVO, Model model) throws Exception {		 
		 model.addAttribute("result", fPopAnalsService.selectFPopYearLgdInfo(popVO));
	     return "jsonView";
	 }
	 
	 /**
	  * 범례 정보 (성/연령별)
	  */
	 @RequestMapping(value = "/gis/fpop/fPopGALegend.do")
	 public String selectFPopGALgdInfo(PopAnalsVO popVO, Model model) throws Exception {		 
		 model.addAttribute("result", fPopAnalsService.selectFPopGALgdInfo(popVO));
	     return "jsonView";
	 }
	 
	 /**
	  * 범례 정보 (시간대별)
	  */
	 @RequestMapping(value = "/gis/fpop/fPopTimeLegend.do")
	 public String selectFPopTimeLgdInfo(PopAnalsVO popVO, Model model) throws Exception {		 
		 model.addAttribute("result", fPopAnalsService.selectFPopTimeLgdInfo(popVO));
	     return "jsonView";
	 }
	 
	 
	 
	 /**
     * 엑셀 내보내기
     */   
	@RequestMapping(value = "/gis/fpop/fPopExcel.do")
    public void exportFPopExcel(ExcelVO excelVo, PopAnalsVO popVo, HttpServletResponse resp) throws Exception {
		java.io.ByteArrayOutputStream outByteStream  = null;
		ServletOutputStream outStream = null;
		//사용자 id 가져오기
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		excelVo.setUid(loginVO.getUserId());
		try {								
			XSSFWorkbook xlsxWb = fPopAnalsService.createExcelInfo(excelVo, popVo);
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