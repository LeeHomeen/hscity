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
import kr.co.socsoft.gis.traffic.service.ArrAnalsDao;
import kr.co.socsoft.gis.traffic.service.ArrAnalsService;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;


/**
 * 탄력배치 분석 서비스  SERVICE
 * @author SOC SOFT
 *
 */
@Service("arrAnalsService")
public class ArrAnalsServiceImpl implements ArrAnalsService{

	@Resource(name = "commService")
	private CommService commService;
	
	@Resource(name = "arrAnalsDao")
	private ArrAnalsDao dao;	
	
	/**
	 * 요일별 승차인원
	 * @param TrafficAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectArrWeekList(TrafficAnalsVO facVO){
		facVO.setBus(commService.changeCommaInfo(facVO.getBus()));
		return dao.selectArrWeekList(facVO);	
	}
	
	/**
	 * 청두 미청두
	 * @param TrafficAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectArrTimeList(TrafficAnalsVO facVO){
		facVO.setBus(commService.changeCommaInfo(facVO.getBus()));
		return dao.selectArrTimeList(facVO);
	}
	
	/**
	 * create Excel info
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public XSSFWorkbook createExcelInfo(ExcelVO excelVo, TrafficAnalsVO arrVo){
		String id = excelVo.getEid();
		//활용목적 저장
		String title = "";
		if(id.trim().equals("2-5-1"))
			title = "요일별 승차인원";
		else if(id.trim().equals("2-5-2"))
			title = "시간별 승차인원";
		
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
				
				if(id.trim().equals("2-5-1")){
					//요일별 승차인원			
					String[] colNames = new String[]{"버스노선","월(명)","화(명)","수(명)","목(명)","금(명)","토(명)","일(명)","승차인원(A)(명)","주말승차(B)(명)","비율(B/A)(%)"};
					String[] colIds = new String[]{"div","d1","d2","d3","d4","d5","d6","d7","d8","d9","d10"};
					/* db에서 get
					arrVo.setBus(arrVo.getBus().replace("%2C", ","));
					List<?> result = selectArrWeekList(arrVo);
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
				}else if(id.trim().equals("2-5-2")){
					//청두/비청두		
					String[] colNames = new String[]{"버스노석","00시(명)","01시(명)","02시(명)","03시(명)","04시(명)","05시(명)","06시(명)","07시(명)","08시(명)","09시(명)","10시(명)","11시(명)","12시(명)","13시(명)","14시(명)","15시(명)","16시(명)","17시(명)","18시(명)","19시(명)","20시(명)","21시(명)","22시(명)","23시(명)","승차인원(A)(명)","첨두시간 승차인원(B)(명)","비율(B/A)(%)"};
					String[] colIds = new String[]{"div","p00","p01","p02","p03","p04","p05","p06","p07","p08","p09","p10","p11","p12","p13","p14","p15","p16","p17","p18","p19","p20","p21","p22","p23","tot","peak","rat"};
					/*
					arrVo.setBus(arrVo.getBus().replace("%2C", ","));
					List<?> result = selectArrTimeList(arrVo);
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
