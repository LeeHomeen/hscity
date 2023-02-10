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
import kr.co.socsoft.gis.traffic.service.CardAnalsDao;
import kr.co.socsoft.gis.traffic.service.CardAnalsService;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;


/**
 * 교통카드 현황분석 서비스  SERVICE
 * @author SOC SOFT
 *
 */
@Service("cardAnalsService")
public class CardAnalsServiceImpl implements CardAnalsService{

	@Resource(name = "commService")
	private CommService commService;
	
	@Resource(name = "cardAnalsDao")
	private CardAnalsDao dao;	
	
	/**
	 * 교통카드 현황분석 결과 리스트
	 * @param BlindAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectCardList(TrafficAnalsVO cardVO){
		cardVO.setBus(commService.changeCommaInfo(cardVO.getBus()));
		return dao.selectCardList(cardVO);
	}
	
	/**
	 * create Excel info
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public XSSFWorkbook createExcelInfo(ExcelVO excelVo, TrafficAnalsVO cardVo){
		String id = excelVo.getEid();
		//활용목적 저장
		String title = "";
		if(id.trim().equals("2-3-1"))
			title = "정류장별 승하차 정보";
		
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
				
				if(id.trim().equals("2-3-1")){
					//인구분석_기간별 분석				
					String[] colNames = new String[]{"정류장","승차(명)","하차(명)","환승(명)"};
					String[] colIds = new String[]{"div","spop","fpop","tpop"};
					/*
					cardVo.setBus(cardVo.getBus().replace("%2C", ","));
					List<?> result = selectCardList(cardVo);
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
