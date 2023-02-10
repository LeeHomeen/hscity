package kr.co.socsoft.gis.sales.web;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import kr.co.socsoft.common.vo.LoginVO;
import kr.co.socsoft.gis.comm.service.CommService;
import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.pop.vo.PopAnalsGisVO;
import kr.co.socsoft.gis.sales.service.SalesAnalsService;
import kr.co.socsoft.gis.sales.vo.SalesAnalsVO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.EgovUserDetailsHelper;


/**
 * 매출분석 controller 
 * @author SocSoft
 *
 */
@Controller
public class SalesAnalsController{
	
	@SuppressWarnings("unused")
	private static final Logger logger = (Logger) LogManager.getLogger(SalesAnalsController.class);

	@Resource(name = "commService")
	private CommService commService;
	
	@Resource(name = "salesAnalsService")
	private SalesAnalsService salesAnalsService;	
	
	/**
	 * 매출분석_기간별 분석 결과 LIST
	 */
	 @RequestMapping(value = "/gis/sale/salesYearList.do")
	 public String selectYearSalesList(SalesAnalsVO salesVO, Model model) throws Exception {
		 model.addAttribute("result", salesAnalsService.selectYearSalesList(salesVO));
	     return "jsonView";
	 }
	
	/**
     * 매출분석 기간별 국가기초구역 이미지 생성
     */    
	@RequestMapping(value = "/gis/sale/salesYearAnals.do")
    public void selectYearSalesAnals(PopAnalsGisVO popVO, HttpServletResponse response) throws Exception {
    	BufferedImage image = null;
    	popVO.setMENU("year");
    	image = salesAnalsService.createSalesImg(popVO);    	
    	response.setContentType("image/png");
    	ServletOutputStream sos = response.getOutputStream();
    	ImageIO.write(image, "png", sos);
    	sos.flush();
    	sos.close();
    }
	
	////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 매출분석_시간대별 분석 결과 LIST
	 */
	 @RequestMapping(value = "/gis/sale/salesTimeList.do")
	 public String salesTimeList(SalesAnalsVO salesVO, Model model) throws Exception {
		 model.addAttribute("result", salesAnalsService.selectTimeSalesList(salesVO));
	     return "jsonView";
	 }
	
	/**
     * 매출분석 시간대별 국가기초구역 이미지 생성
     */    
	@RequestMapping(value = "/gis/sale/salesTimeAnals.do")
    public void selectTimeSalesAnals(PopAnalsGisVO popVO, HttpServletResponse response) throws Exception {
    	BufferedImage image = null;    	    	
    	popVO.setMENU("time");
    	image = salesAnalsService.createSalesImg(popVO);    	
    	response.setContentType("image/png");
    	ServletOutputStream sos = response.getOutputStream();
    	ImageIO.write(image, "png", sos);
    	sos.flush();
    	sos.close();
    }
	 
	
	////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 매출분석_성연령별 분석 결과 LIST
	 */
	 @RequestMapping(value = "/gis/sale/salesGAList.do")
	 public String salesGAList(SalesAnalsVO salesVO, Model model) throws Exception {
		 model.addAttribute("result", salesAnalsService.selectGASalesList(salesVO));
	     return "jsonView";
	 }
	
	/**
     * 매출분석 성연령별 국가기초구역 이미지 생성
     */    
	@RequestMapping(value = "/gis/sale/salesGAAnals.do")
    public void selectGASalesAnals(PopAnalsGisVO popVO, HttpServletResponse response) throws Exception {
    	BufferedImage image = null;    	    	
    	popVO.setMENU("age");
    	image = salesAnalsService.createSalesImg(popVO);    	
    	response.setContentType("image/png");
    	ServletOutputStream sos = response.getOutputStream();
    	ImageIO.write(image, "png", sos);
    	sos.flush();
    	sos.close();
    }
	
	////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 매출분석_업종별 분석 결과 LIST
	 */
	 @RequestMapping(value = "/gis/sale/salesCtgList.do")
	 public String salesCtgList(SalesAnalsVO salesVO, Model model) throws Exception {
		 model.addAttribute("result", salesAnalsService.selectCtgSalesList(salesVO));
	     return "jsonView";
	 }
	
	/**
     * 매출분석 업종별 국가기초구역 이미지 생성
     */    
	@RequestMapping(value = "/gis/sale/salesCtgAnals.do")
    public void selectCtgSalesAnals(PopAnalsGisVO popVO, HttpServletResponse response) throws Exception {
    	BufferedImage image = null;    	    	
    	popVO.setMENU("ctg");
    	image = salesAnalsService.createSalesImg(popVO);    	
    	response.setContentType("image/png");
    	ServletOutputStream sos = response.getOutputStream();
    	ImageIO.write(image, "png", sos);
    	sos.flush();
    	sos.close();
    }
	
	////////////////////////////////////////////////////////////////////////////////////
	
////////////////////////////////////////////////////////////////////////////////////
	
	/**
	* 외국인분석_결과 LIST
	*/
	@RequestMapping(value = "/gis/sale/salesFgnList.do")
		public String salesFgnList(SalesAnalsVO salesVO, Model model) throws Exception {
		model.addAttribute("result", salesAnalsService.selectFgnSalesList(salesVO));
		return "jsonView";
	}

	/**
	* 외국인분석 국가기초구역 이미지 생성
	*/    
	@RequestMapping(value = "/gis/sale/salesFgnAnals.do")
	public void selectFgnSalesAnals(PopAnalsGisVO popVO, HttpServletResponse response) throws Exception {
		BufferedImage image = null;    	    	
		popVO.setMENU("fgn");
		image = salesAnalsService.createSalesImg(popVO);    	
		response.setContentType("image/png");
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(image, "png", sos);
		sos.flush();
		sos.close();
	}

	////////////////////////////////////////////////////////////////////////////////////
	
	/**
	  * 범례 정보 (기간별)
	  */
	 @RequestMapping(value = "/gis/sale/saleYearLegend.do")
	 public String selectSaleYearLgdInfo(SalesAnalsVO salesVO, Model model) throws Exception {		 
		 model.addAttribute("result", salesAnalsService.selectSaleYearLgdInfo(salesVO));
	     return "jsonView";
	 }
	 
	 /**
	  * 범례 정보 (성연령별)
	  */
	 @RequestMapping(value = "/gis/sale/saleGALegend.do")
	 public String selectSaleGALgdInfo(SalesAnalsVO salesVO, Model model) throws Exception {		 
		 model.addAttribute("result", salesAnalsService.selectSaleGALgdInfo(salesVO));
	     return "jsonView";
	 }
	 
	 /**
	  * 범례 정보 (시간대)
	  */
	 @RequestMapping(value = "/gis/sale/saleTimeLegend.do")
	 public String selectSaleTimeLgdInfo(SalesAnalsVO salesVO, Model model) throws Exception {		 
		 model.addAttribute("result", salesAnalsService.selectSaleTimeLgdInfo(salesVO));
	     return "jsonView";
	 }
	 
	 /**
	  * 범례 정보 (업종별)
	  */
	 @RequestMapping(value = "/gis/sale/saleCtgLegend.do")
	 public String selectSaleCtgLgdInfo(SalesAnalsVO salesVO, Model model) throws Exception {		 
		 model.addAttribute("result", salesAnalsService.selectSaleCtgLgdInfo(salesVO));
	     return "jsonView";
	 }
	 
	 /**
	  * 범례 정보 (외국인)
	  */
	 @RequestMapping(value = "/gis/sale/saleFngLegend.do")
	 public String selectSaleFngLgdInfo(SalesAnalsVO salesVO, Model model) throws Exception {		 
		 model.addAttribute("result", salesAnalsService.selectSaleFngLgdInfo(salesVO));
	     return "jsonView";
	 }
	 
	 
	 /**
     * 엑셀 내보내기
     */
	@RequestMapping(value = "/gis/sale/saleExcel.do")
    public void exportSaleExcel(ExcelVO excelVo, SalesAnalsVO salesVo, HttpServletResponse resp) throws Exception {
		java.io.ByteArrayOutputStream outByteStream  = null;
		ServletOutputStream outStream = null;
		//사용자 id 가져오기
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		excelVo.setUid(loginVO.getUserId());
		try {			
			XSSFWorkbook xlsxWb = salesAnalsService.createExcelInfo(excelVo, salesVo);
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