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
import kr.co.socsoft.gis.pop.service.PopAnalsService;
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
 * 인구분석 controller 
 * @author SocSoft
 *
 */
@Controller
public class PopAnalsController{
	
	@SuppressWarnings("unused")
	private static final Logger logger = (Logger) LogManager.getLogger(PopAnalsController.class);

	@Resource(name = "commService")
	private CommService commService;
	
	@Resource(name = "popAnalsService")
	private PopAnalsService popAnalsService;	
	
	/**
	 * 인구분석_기간별 분석 결과 LIST
	 */
	 @RequestMapping(value = "/gis/pop/popYearList.do")
	 public String selectYearPopList(PopAnalsVO popVO, Model model) throws Exception {
		 model.addAttribute("result", popAnalsService.selectYearPopList(popVO));
	     return "jsonView";
	 }
	
	/**
     * 인구분석 기간별 분석_격자, 벌집이미지 생성
     */    
	@RequestMapping(value = "/gis/pop/popYearAnals.do")
    public void selectYearPopAnals(PopAnalsGisVO popVO, HttpServletResponse response) throws Exception {
    	BufferedImage image = null;
    	String vtype = popVO.getVTYPE().trim();
    	
    	if(vtype.equals("heat")){
    		List<?> list = popAnalsService.selectYearPop_heat(popVO);
    		HeatMap heat = new HeatMap(popVO);
    		heat.loadData_list(list);
    		heat.drawOuput_list(true);
    		image = heat.heatmapImage;
    	}else{
    		//격자, 벌집 분석
    		popVO.setMENU("year");
    		image = popAnalsService.createPopImg(popVO);
    	}	
    	
    	response.setContentType("image/png");
    	ServletOutputStream sos = response.getOutputStream();
    	ImageIO.write(image, "png", sos);
    	sos.flush();
    	sos.close();
    }
	
	
		
	/**
	 * 인구분석_성연령별 분석 결과 LIST
	 */
	 @RequestMapping(value = "/gis/pop/popGAList.do")
	 public String popGAList(PopAnalsVO popVO, Model model) throws Exception {
		 model.addAttribute("result", popAnalsService.selectGAPopList(popVO));
	     return "jsonView";
	 }
	
	 /**
     * 인구분석 성연령별 분석_격자, 벌집이미지 생성
     */    
	@RequestMapping(value = "/gis/pop/popGAAnals.do")
    public void selectGAPopAnals(PopAnalsGisVO popVO, HttpServletResponse response) throws Exception {
    	BufferedImage image = null;
    	String vtype = popVO.getVTYPE().trim();
    	
    	if(vtype.equals("heat")){
    		List<?> list = popAnalsService.selectGAPop_heat(popVO);
    		HeatMap heat = new HeatMap(popVO);
    		heat.loadData_list(list);
    		heat.drawOuput_list(true);
    		image = heat.heatmapImage;
    	}else{
    		//격자, 벌집 분석
    		popVO.setMENU("age");
    		image = popAnalsService.createPopImg(popVO);
    	}	
    	
    	response.setContentType("image/png");
    	ServletOutputStream sos = response.getOutputStream();
    	ImageIO.write(image, "png", sos);
    	sos.flush();
    	sos.close();
    }
		
	
   
	/**
	 * 인구분석_전입분석 (지역별 전입자수)
	 */
	 @RequestMapping(value = "/gis/pop/inPopList.do")
	 public String selectInPopList(PopAnalsVO popVO, Model model) throws Exception {
		 List<?> cntList = popAnalsService.selectInPopCntList(popVO);
		 model.addAttribute("cnt", cntList);
		 if(cntList == null){
			 model.addAttribute("resn", null);
			 model.addAttribute("geom", null); 
		 }else{
			 model.addAttribute("resn", popAnalsService.selectInPopResnList(popVO));
			 model.addAttribute("geom", popAnalsService.selectInPopGeomList(popVO));	 
		 }
	     return "jsonView";
	 }
	 
	 
	 /**
	 * 인구분석_전출분석 (지역별 전출자수)
	 */
	 @RequestMapping(value = "/gis/pop/outPopList.do")
	 public String selectOutPopList(PopAnalsVO popVO, Model model) throws Exception {
		 List<?> cntList = popAnalsService.selectOutPopCntList(popVO);
		 model.addAttribute("cnt", cntList);
		 if(cntList == null){
			 model.addAttribute("resn", null);
			 model.addAttribute("geom", null);
		 }else{
			 model.addAttribute("resn", popAnalsService.selectOutPopResnList(popVO));
			 model.addAttribute("geom", popAnalsService.selectOutPopGeomList(popVO));	 
		 }
		 
	     return "jsonView";
	 }
	 
	 
	 /**
	  * 범례 정보 (기간별)
	  */
	 @RequestMapping(value = "/gis/pop/popYearLegend.do")
	 public String selectPopYearLgdInfo(PopAnalsVO popVO, Model model) throws Exception {		 
		 model.addAttribute("result", popAnalsService.selectPopYearLgdInfo(popVO));
	     return "jsonView";
	 }
	 
	 /**
	  * 범례 정보 (성/연령별)
	  */
	 @RequestMapping(value = "/gis/pop/popGALegend.do")
	 public String selectPopGALgdInfo(PopAnalsVO popVO, Model model) throws Exception {		 
		 model.addAttribute("result", popAnalsService.selectPopGALgdInfo(popVO));
	     return "jsonView";
	 }
	 
	/**
     * 엑셀 내보내기
     */	
	@RequestMapping(value = "/gis/pop/popExcel.do")
    public void exportPopExcel(ExcelVO excelVo, PopAnalsVO popVo, HttpServletResponse resp) throws Exception {		
		java.io.ByteArrayOutputStream outByteStream  = null;
		ServletOutputStream outStream = null;
		//사용자 id 가져오기
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		excelVo.setUid(loginVO.getUserId());
		try {		
			XSSFWorkbook xlsxWb = popAnalsService.createExcelInfo(excelVo, popVo);
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