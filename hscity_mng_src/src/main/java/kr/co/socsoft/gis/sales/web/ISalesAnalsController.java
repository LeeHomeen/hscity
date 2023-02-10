package kr.co.socsoft.gis.sales.web;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import kr.co.socsoft.common.vo.LoginVO;
import kr.co.socsoft.gis.comm.service.CommService;
import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.sales.service.ISalesAnalsService;
import kr.co.socsoft.gis.sales.vo.SalesAnalsVO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.EgovUserDetailsHelper;


/**
 * 유입매출분석 controller 
 * @author SocSoft
 *
 */
@Controller
public class ISalesAnalsController{
	
	@SuppressWarnings("unused")
	private static final Logger logger = (Logger) LogManager.getLogger(ISalesAnalsController.class);

	@Resource(name = "commService")
	private CommService commService;
	
	@Resource(name = "iSalesAnalsService")
	private ISalesAnalsService iSalesAnalsService;
	
	/**
	 * 시도별_유입분석
	 */
	 @RequestMapping(value = "/gis/sale/siSaleList.do")
	 public String selectSiSaleList(SalesAnalsVO salesVO, Model model) throws Exception {
		 model.addAttribute("cnt", iSalesAnalsService.selectSiSalesList(salesVO));
		 model.addAttribute("geom", iSalesAnalsService.selectSiGeomList(salesVO));
	     return "jsonView";
	 }
	 

	/**
	 * 시군구별_유입분석
	 */
	 @RequestMapping(value = "/gis/sale/sigunSaleList.do")
	 public String selectSigunSaleList(SalesAnalsVO salesVO, Model model) throws Exception {
		 model.addAttribute("cnt", iSalesAnalsService.selectSigunSalesList(salesVO));
		 model.addAttribute("geom", iSalesAnalsService.selectSigunGeomList(salesVO));
		 return "jsonView";
	 }
	 
	 /**
	 * 화성시 인접_유입분석
	 */
	 @RequestMapping(value = "/gis/sale/adjSaleList.do")
	 public String selectAdjSaleList(SalesAnalsVO salesVO, Model model) throws Exception {
		 model.addAttribute("cnt", iSalesAnalsService.selectAdjSalesList(salesVO));
		 model.addAttribute("geom", iSalesAnalsService.selectAdjGeomList(salesVO));
		 return "jsonView";
	 }	
	 
	 
	 /**
     * 엑셀 내보내기
     */
	@RequestMapping(value = "/gis/sale/iSaleExcel.do")
    public void exportISaleExcel(ExcelVO excelVo, SalesAnalsVO salesVo, HttpServletResponse resp) throws Exception {
		java.io.ByteArrayOutputStream outByteStream  = null;
		ServletOutputStream outStream = null;
		//사용자 id 가져오기
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		excelVo.setUid(loginVO.getUserId());
		try {						
			XSSFWorkbook xlsxWb = iSalesAnalsService.createExcelInfo(excelVo, salesVo);
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