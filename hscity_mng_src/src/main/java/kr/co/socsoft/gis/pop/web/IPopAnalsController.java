package kr.co.socsoft.gis.pop.web;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import kr.co.socsoft.common.vo.LoginVO;
import kr.co.socsoft.gis.comm.service.CommService;
import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.pop.service.IPopAnalsService;
import kr.co.socsoft.gis.pop.vo.PopAnalsVO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.EgovUserDetailsHelper;


/**
 * 유입인구분석 controller 
 * @author SocSoft
 *
 */
@Controller
public class IPopAnalsController{
	
	@SuppressWarnings("unused")
	private static final Logger logger = (Logger) LogManager.getLogger(IPopAnalsController.class);

	@Resource(name = "commService")
	private CommService commService;
	
	@Resource(name = "iPopAnalsService")
	private IPopAnalsService iPopAnalsService;	
	
	/**
	 * 시도별_유입인구
	 */
	 @RequestMapping(value = "/gis/ipop/iPopSidoList.do")
	 public String selectSiIPopList(PopAnalsVO popVo, Model model) throws Exception {
		 model.addAttribute("cnt", iPopAnalsService.selectSiIPopList(popVo));
		 model.addAttribute("geom", iPopAnalsService.selectSiGeomList(popVo));
	     return "jsonView";
	 }

	 /**
	 * 시군구별_유입인구
	 */
	 @RequestMapping(value = "/gis/ipop/iPopSigunList.do")
	 public String iPopSigunList(PopAnalsVO popVo, Model model) throws Exception {
		 model.addAttribute("cnt", iPopAnalsService.selectSigunIPopList(popVo));
		 model.addAttribute("geom", iPopAnalsService.selectSigunGeomList(popVo));
	     return "jsonView";
	 }
	 
	 /**
	 * 성연령별_유입인구
	 */
	 @RequestMapping(value = "/gis/ipop/iPopGAList.do")
	 public String iPopGAList(PopAnalsVO popVo, Model model) throws Exception {
		 model.addAttribute("cnt", iPopAnalsService.selectGAIPopList(popVo));
		 model.addAttribute("geom", iPopAnalsService.selectGAGeomList(popVo));
	     return "jsonView";
	 } 
	 
	 /**
     * 엑셀 내보내기
     */   
	@RequestMapping(value = "/gis/ipop/iPopExcel.do")
    public void exportIPopExcel(ExcelVO excelVo, PopAnalsVO popVo, HttpServletResponse resp) throws Exception {
		java.io.ByteArrayOutputStream outByteStream  = null;
		ServletOutputStream outStream = null;
		//사용자 id 가져오기
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		excelVo.setUid(loginVO.getUserId());
		try {		
			XSSFWorkbook xlsxWb = iPopAnalsService.createExcelInfo(excelVo, popVo);
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