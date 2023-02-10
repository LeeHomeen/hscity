package kr.co.socsoft.gis.traffic.web;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import kr.co.socsoft.common.vo.LoginVO;
import kr.co.socsoft.gis.comm.service.CommService;
import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.traffic.service.PassAnalsService;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.EgovUserDetailsHelper;


/**
 * 통행패턴 분석 controller 
 * @author SocSoft
 *
 */
@Controller
public class PassAnalsController{
	
	@SuppressWarnings("unused")
	private static final Logger logger = (Logger) LogManager.getLogger(PassAnalsController.class);

	@Resource(name = "commService")
	private CommService commService;
	
	@Resource(name = "passAnalsService")
	private PassAnalsService passAnalsService;	
	
	
	/**
	 * 통행패턴 분석_ 결과 LIST
	 */
	 @RequestMapping(value = "/gis/traffic/passList.do")
	 public String selectPassList(TrafficAnalsVO passVO, Model model) throws Exception {
		 model.addAttribute("result", passAnalsService.selectPassList(passVO));
	     return "jsonView";
	 }
	 
	 /**
	 * 정류장_ 결과 LIST
	 */
	 @RequestMapping(value = "/gis/traffic/passStaList.do")
	 public String selectPassStaList(TrafficAnalsVO passVO, Model model) throws Exception {
		 model.addAttribute("result", passAnalsService.selectPassStaList(passVO));
	     return "jsonView";
	 }
	 
	 /**
     * 엑셀 내보내기
     */
	@RequestMapping(value = "/gis/traffic/passExcel.do")
    public void exportPassExcel(ExcelVO excelVo, TrafficAnalsVO passVo, HttpServletResponse resp) throws Exception {	
		java.io.ByteArrayOutputStream outByteStream  = null;
		ServletOutputStream outStream = null;
		//사용자 id 가져오기
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		excelVo.setUid(loginVO.getUserId());
		try {		
			XSSFWorkbook xlsxWb = passAnalsService.createExcelInfo(excelVo, passVo);
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