package kr.co.socsoft.gis.pop.service.impl;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.socsoft.gis.comm.service.CommService;
import kr.co.socsoft.gis.comm.vo.CommVO;
import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.comm.vo.LogVO;
import kr.co.socsoft.gis.pop.service.IPopAnalsDao;
import kr.co.socsoft.gis.pop.service.IPopAnalsService;
import kr.co.socsoft.gis.pop.vo.PopAnalsVO;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * 유입인구분석 서비스  SERVICE
 * @author SOC SOFT
 *
 */
@Service("iPopAnalsService")
public class IPopAnalsServiceImpl implements IPopAnalsService{
	
	@Resource(name = "commService")
	private CommService commService;
	
	@Resource(name = "iPopAnalsDao")
	private IPopAnalsDao dao;	
	
	/**
	 * 시도별 분석
	 * @param PopAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectSiIPopList(PopAnalsVO popVO){
		popVO.setSmonth(popVO.getSmonth().replaceAll("-", ""));
		popVO.setEmonth(popVO.getEmonth().replaceAll("-", ""));		
		popVO.setSday(popVO.getSday().replaceAll("-", ""));
		popVO.setEday(popVO.getEday().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "skt_hs_inflow_age";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);
		String dytbl = "";		
		if(popVO.getStd().trim().equals("day"))
			dytbl = chkTblInfo(tableName, popVO.getSday(), popVO.getEday(), popVO.getStd(), arrTbl);
		else
			dytbl = chkTblInfo(tableName, popVO.getSmonth(), popVO.getEmonth(), popVO.getStd(), arrTbl);		
		
		if(dytbl.trim().equals("")){
			return null;
		}else{
			popVO.setDytbl(dytbl);
			popVO.setDongcd(commService.changeCommaInfo(popVO.getDongcd()));
			return dao.selectSiIPopList(popVO); //행정동별 검색
		}
	}
	@Override
	public List<?> selectSiGeomList(PopAnalsVO popVO){
		popVO.setSmonth(popVO.getSmonth().replaceAll("-", ""));
		popVO.setEmonth(popVO.getEmonth().replaceAll("-", ""));		
		popVO.setSday(popVO.getSday().replaceAll("-", ""));
		popVO.setEday(popVO.getEday().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "skt_hs_inflow_age";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);
		String dytbl = "";
		if(popVO.getStd().trim().equals("day"))
			dytbl = chkTblInfo(tableName, popVO.getSday(), popVO.getEday(), popVO.getStd(), arrTbl);
		else
			dytbl = chkTblInfo(tableName, popVO.getSmonth(), popVO.getEmonth(), popVO.getStd(), arrTbl);	
		if(dytbl.trim().equals("")){
			return null;
		}else{
			popVO.setDytbl(dytbl);			
			return dao.selectSiGeomList(popVO); //행정동별 검색
		}
	}
	
	/**
	 * 시군구별 분석
	 * @param PopAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectSigunIPopList(PopAnalsVO popVO){
		popVO.setSmonth(popVO.getSmonth().replaceAll("-", ""));
		popVO.setEmonth(popVO.getEmonth().replaceAll("-", ""));		
		popVO.setSday(popVO.getSday().replaceAll("-", ""));
		popVO.setEday(popVO.getEday().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "skt_hs_inflow_age";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);
		String dytbl = "";
		if(popVO.getStd().trim().equals("day"))
			dytbl = chkTblInfo(tableName, popVO.getSday(), popVO.getEday(), popVO.getStd(), arrTbl);
		else
			dytbl = chkTblInfo(tableName, popVO.getSmonth(), popVO.getEmonth(), popVO.getStd(), arrTbl);	
		if(dytbl.trim().equals("")){
			return null;
		}else{
			popVO.setDytbl(dytbl);
			popVO.setDongcd(commService.changeCommaInfo(popVO.getDongcd()));
			return dao.selectSigunIPopList(popVO); //행정동별 검색
		}
	}
	@Override
	public List<?> selectSigunGeomList(PopAnalsVO popVO){
		popVO.setSmonth(popVO.getSmonth().replaceAll("-", ""));
		popVO.setEmonth(popVO.getEmonth().replaceAll("-", ""));		
		popVO.setSday(popVO.getSday().replaceAll("-", ""));
		popVO.setEday(popVO.getEday().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "skt_hs_inflow_age";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);
		String dytbl = "";
		if(popVO.getStd().trim().equals("day"))
			dytbl = chkTblInfo(tableName, popVO.getSday(), popVO.getEday(), popVO.getStd(), arrTbl);
		else
			dytbl = chkTblInfo(tableName, popVO.getSmonth(), popVO.getEmonth(), popVO.getStd(), arrTbl);	
		if(dytbl.trim().equals("")){
			return null;
		}else{
			popVO.setDytbl(dytbl);
			return dao.selectSigunGeomList(popVO); //행정동별 검색
		}
	}
	
	/**
	 * 성연령별 분석
	 * @param PopAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectGAIPopList(PopAnalsVO popVO){
		popVO.setSmonth(popVO.getSmonth().replaceAll("-", ""));
		popVO.setEmonth(popVO.getEmonth().replaceAll("-", ""));		
		popVO.setSday(popVO.getSday().replaceAll("-", ""));
		popVO.setEday(popVO.getEday().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "skt_hs_inflow_age";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);
		String dytbl = "";
		if(popVO.getStd().trim().equals("day"))
			dytbl = chkTblInfo(tableName, popVO.getSday(), popVO.getEday(), popVO.getStd(), arrTbl);
		else
			dytbl = chkTblInfo(tableName, popVO.getSmonth(), popVO.getEmonth(), popVO.getStd(), arrTbl);	
		if(dytbl.trim().equals("")){
			return null;
		}else{
			popVO.setDytbl(dytbl);
			popVO.setDongcd(commService.changeCommaInfo(popVO.getDongcd()));
			return dao.selectGAIPopList(popVO); //행정동별 검색
		}
	}
	@Override
	public List<?> selectGAGeomList(PopAnalsVO popVO){
		popVO.setSmonth(popVO.getSmonth().replaceAll("-", ""));
		popVO.setEmonth(popVO.getEmonth().replaceAll("-", ""));		
		popVO.setSday(popVO.getSday().replaceAll("-", ""));
		popVO.setEday(popVO.getEday().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "skt_hs_inflow_age";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);
		String dytbl = "";
		if(popVO.getStd().trim().equals("day"))
			dytbl = chkTblInfo(tableName, popVO.getSday(), popVO.getEday(), popVO.getStd(), arrTbl);
		else
			dytbl = chkTblInfo(tableName, popVO.getSmonth(), popVO.getEmonth(), popVO.getStd(), arrTbl);	
		if(dytbl.trim().equals("")){
			return null;
		}else{
			popVO.setDytbl(dytbl);
			return dao.selectGAGeomList(popVO); //행정동별 검색
		}
	}
	
	/**
	 * create Excel info
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public XSSFWorkbook createExcelInfo(ExcelVO excelVo, PopAnalsVO popVo){
		String id = excelVo.getEid();		
		//활용목적 저장
		String title = "";
		if(id.trim().equals("1-3-1"))
			title = "유입인구-시도별분석";
		else if(id.trim().equals("1-3-2"))
			title = "유입인구-시군구별분석";
		else if(id.trim().equals("1-3-3"))
			title = "유입인구-성연령별별분석";
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
				BufferedImage chartImg = commService.getStringImg(excelVo.getChart());
				if(id.trim().equals("1-3-1")){
					String[] colNames = new String[]{"유입시도","유입인구(명)"};
					String[] colIds = new String[]{"div","tot"};				
					//List<?> result = selectSiIPopList(popVo); //db
					//jqgrid에서 직접 get					
					ObjectMapper mapper = new ObjectMapper();
					List<EgovMap> result;
					try {
						mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
						result = mapper.readValue(excelVo.getExparams(), new TypeReference<List<EgovMap>>(){});
						//end							
						list.add(result);
						nameList.add(colNames);
						idsList.add(colIds);
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}else if(id.trim().equals("1-3-2")){
					String[] colNames = new String[]{"유입시군","유입인구(명)"};
					String[] colIds = new String[]{"div","tot"};				
					//List<?> result = selectSigunIPopList(popVo); //db 
					//jqgrid에서 직접 get					
					ObjectMapper mapper = new ObjectMapper();
					List<EgovMap> result;
					try {
						mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
						result = mapper.readValue(excelVo.getExparams(), new TypeReference<List<EgovMap>>(){});
						//end							
						list.add(result);
						nameList.add(colNames);
						idsList.add(colIds);
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}else if(id.trim().equals("1-3-3")){
					/* db에서 직접 읽기
					List<?> result = selectGAIPopList(popVo);
					List<String[]> colInfo = reGAResultList(result);
					*/
					//jqgrid에서 직접 읽기
					ObjectMapper mapper = new ObjectMapper();
					List<EgovMap> result;
					try {
						mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
						result = mapper.readValue(excelVo.getExparams(), new TypeReference<List<EgovMap>>(){});
						List<String[]> colInfo = reGAResultList(result);
						
						list.add(result);
						nameList.add(colInfo.get(0));
						idsList.add(colInfo.get(1));
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
	
	/**
	 * 단순 테이블 유무 확인 후 동적테이블 생성
	 * 테이블명
	 * 시작월 또는 일
	 * 종료월 또는 일
	 * 월, 일 구분
	 * db에 저장되어 있는 테이블명
	 */
	private String chkTblInfo(String tableName, String start, String end, String std, List<?> arrTbl){
		String result = "";
		
		String startPeriod = "";		
		String endPeriod = "";
		startPeriod = start.substring(0, 6);
		endPeriod = end.substring(0, 6);
		
		//System.out.println(startPeriod + "   " + endPeriod);		
		
		int startMon = Integer.parseInt(startPeriod);
		int endMon = Integer.parseInt(endPeriod);
		if(arrTbl.size() > 0){
			for(int i=0; i<arrTbl.size(); i++){
				EgovMap map = (EgovMap)arrTbl.get(i);
				int unit = Integer.parseInt((map.get("unit")).toString());
				
				for(int j=startMon; j<=endMon; j++){
					if(j == unit){
						result += "select * from " + tableName + "_" + (unit+",");
						break;
					}
				}
			}
		}
		if(!result.trim().equals("")){
			result = result.substring(0, result.length()-1);
			result = result.replaceAll(",", " union all ");
		}
		//System.out.println(result);
		return result;
	}
	
	/**
	 * 컬럼정보 재정의
	 * @param list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<String[]> reGAResultList(List<?> list){
		List<String[]> colInfo = new ArrayList();
		String[]colNames;
		String[]colIds;
		int resultCnt = 2;
		if(list.size() > 0){
			EgovMap map = (EgovMap)list.get(0);
			double m10 = Double.parseDouble((map.get("m10")).toString());
			double m20 = Double.parseDouble((map.get("m20")).toString());
			double m30 = Double.parseDouble((map.get("m30")).toString());
			double m40 = Double.parseDouble((map.get("m40")).toString());
			double m50 = Double.parseDouble((map.get("m50")).toString());
			double m60 = Double.parseDouble((map.get("m60")).toString());
			double m70 = Double.parseDouble((map.get("m70")).toString());
			
			double w10 = Double.parseDouble((map.get("w10")).toString());
			double w20 = Double.parseDouble((map.get("w20")).toString());
			double w30 = Double.parseDouble((map.get("w30")).toString());
			double w40 = Double.parseDouble((map.get("w40")).toString());
			double w50 = Double.parseDouble((map.get("w50")).toString());
			double w60 = Double.parseDouble((map.get("w60")).toString());
			double w70 = Double.parseDouble((map.get("w70")).toString());
			
			if(m10 > -999) resultCnt++;			if(m20 > -999) resultCnt++;			if(m30 > -999) resultCnt++;
			if(m40 > -999) resultCnt++;			if(m50 > -999) resultCnt++;			if(m60 > -999) resultCnt++;
			if(m70 > -999) resultCnt++;			
			if(w10 > -999) resultCnt++;			if(w20 > -999) resultCnt++;			if(w30 > -999) resultCnt++;
			if(w40 > -999) resultCnt++;			if(w50 > -999) resultCnt++;			if(w60 > -999) resultCnt++;
			if(w70 > -999) resultCnt++;
			//String[]colNames = new String[]{"구분","10대(남)","20대(남)","30대(남)","40대(남)","50대(남)","60대(남)","70대이상(남)","10대(여)","20대(여)","30대(여)","40대(여)","50대(여)","60대(여)","70대이상(여)","합계"};
			//String[]colIds = new String[]{"div","m10","m20","m30","m40","m50","m60","m70","w10","w20","w30","w40","w50","w60","w70","tot"};
			
			colNames = new String[resultCnt];			
			colIds   = new String[resultCnt];			
			colNames[0] = "유입시군";
			colIds[0] = "div";
			resultCnt = 1;
			if(m10 > -999){
				int num = resultCnt++;
				colNames[num] = "10대_남(명)";
				colIds[num] = "m10";
			}
			if(m20 > -999){
				int num = resultCnt++;
				colNames[num] = "20대_남(명)";
				colIds[num] = "m20";
			}
			if(m30 > -999){
				int num = resultCnt++;
				colNames[num] = "30대_남(명)";
				colIds[num] = "m30";
			}
			if(m40 > -999){
				int num = resultCnt++;
				colNames[num] = "40대_남(명)";
				colIds[num] = "m40";
			}
			if(m50 > -999){
				int num = resultCnt++;
				colNames[num] = "50대_남(명)";
				colIds[num] = "m50";
			}
			if(m60 > -999){
				int num = resultCnt++;
				colNames[num] = "60대_남(명)";
				colIds[num] = "m60";
			}
			if(m70 > -999){
				int num = resultCnt++;
				colNames[num] = "70대이상_남(명)";
				colIds[num] = "m70";
			}
			if(w10 > -999){
				int num = resultCnt++;
				colNames[num] = "10대_여(명)";
				colIds[num] = "w10";
			}
			if(w20 > -999){
				int num = resultCnt++;
				colNames[num] = "20대_여(명)";
				colIds[num] = "w20";
			}
			if(w30 > -999){
				int num = resultCnt++;
				colNames[num] = "30대_여(명)";
				colIds[num] = "w30";
			}
			if(w40 > -999){
				int num = resultCnt++;
				colNames[num] = "40대_여(명)";
				colIds[num] = "w40";
			}			
			if(w50 > -999){
				int num = resultCnt++;
				colNames[num] = "50대_여(명)";
				colIds[num] = "w50";
			}			
			if(w60 > -999){
				int num = resultCnt++;
				colNames[num] = "60대_여(명)";
				colIds[num] = "w60";
			}
			if(w70 > -999){
				int num = resultCnt++;
				colNames[num] = "70대이상_여(명)";
				colIds[num] = "w70";
			}
			int num = resultCnt++;
			colNames[num] = "합계(명)";
			colIds[num] = "tot";
			
			colInfo.add(colNames);
			colInfo.add(colIds);
			resultCnt = 0;
		}		
		return colInfo;
	}
}
