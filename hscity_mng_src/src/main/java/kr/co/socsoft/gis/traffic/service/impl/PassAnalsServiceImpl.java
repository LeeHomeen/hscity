package kr.co.socsoft.gis.traffic.service.impl;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import kr.co.socsoft.gis.comm.service.CommService;
import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.comm.vo.LogVO;
import kr.co.socsoft.gis.traffic.service.PassAnalsDao;
import kr.co.socsoft.gis.traffic.service.PassAnalsService;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;


/**
 * 통행패턴 분석 서비스  SERVICE
 * @author SOC SOFT
 *
 */
@Service("passAnalsService")
public class PassAnalsServiceImpl implements PassAnalsService{

	@Resource(name = "commService")
	private CommService commService;
	
	@Resource(name = "passAnalsDao")
	private PassAnalsDao dao;	
	
	/**
	 * 환승편의시설 분석 결과 리스트
	 * @param TrafficAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectPassList(TrafficAnalsVO passVO){		
		return dao.selectPassList(passVO);
	}
	
	/**
	 * 정류장 정보 조회
	 * @param TrafficAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectPassStaList(TrafficAnalsVO passVO){
		return dao.selectPassStaList(passVO);
	}
	
	/**
	 * create Excel info
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public XSSFWorkbook createExcelInfo(ExcelVO excelVo, TrafficAnalsVO passVo){
		String id = excelVo.getEid();
		//활용목적 저장
		String title = "";
		if(id.trim().equals("2-7-1"))
			title = passVo.getTitle();
		
		//이력저장 완료
		LogVO logVO = new LogVO();
		logVO.setGubun(title);
		logVO.setUid(excelVo.getUid());
		logVO.setUsepps(excelVo.getPps().trim());
		
		XSSFWorkbook xlsxWb = null;
		//로그저장
		if(commService.insertExlLog(logVO)){
			List<List<?>> list = new ArrayList(); 
			List<String[]> nameList = new ArrayList(); //컬럼한글명
			List<String[]> idsList = new ArrayList();   //컴럼명
			//2개의 grid정보가 있는경우만 사용
			
			if(id != null){
				BufferedImage mapImg = commService.getStringImg(excelVo.getMap());
				String strChart = excelVo.getChart();
				BufferedImage chartImg = null;
				if(strChart != null && !strChart.trim().equals(""))
					chartImg = commService.getStringImg(excelVo.getChart());
				
				if(id.trim().equals("2-7-1")){
					//인구분석_기간별 분석				
					String[] colNames = new String[]{"하차정류장","승객수(명)"};
					String[] colIds = new String[]{"fnm","pop"};
					/* db에서 get					
					List<?> result = selectPassList(passVo);
					*/
					//jqgrid에서 직접 get					
					ObjectMapper mapper = new ObjectMapper();
					List<EgovMap> result;
					try {
						mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
						result = mapper.readValue(excelVo.getExparams(), new TypeReference<List<EgovMap>>(){});
						list.add(result);
						nameList.add(colNames);
						idsList.add(colIds);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}				
				if(list != null){				
					xlsxWb = commService.createExcel(nameList, idsList, title, list, mapImg, chartImg);
				}
			}
		}
		return xlsxWb;
	}
}
