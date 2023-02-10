package kr.co.socsoft.gis.sales.service.impl;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.socsoft.gis.comm.service.CommService;
import kr.co.socsoft.gis.comm.vo.CommVO;
import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.comm.vo.LogVO;
import kr.co.socsoft.gis.sales.service.ISalesAnalsDao;
import kr.co.socsoft.gis.sales.service.ISalesAnalsService;
import kr.co.socsoft.gis.sales.vo.SalesAnalsVO;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * 유입매출분석 서비스  SERVICE
 * @author SOC SOFT
 *
 */
@Service("iSalesAnalsService")
public class ISalesAnalsServiceImpl implements ISalesAnalsService{
	
	@Resource(name = "commService")
	private CommService commService;
	
	@Resource(name = "iSalesAnalsDao")
	private ISalesAnalsDao dao;
	
	/**
	 * 시도별 분석 (리스트, geometry)
	 * @param SalesAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectSiSalesList(SalesAnalsVO salesVO){		
		salesVO.setSmonth(salesVO.getSmonth().replaceAll("-", ""));
		salesVO.setEmonth(salesVO.getEmonth().replaceAll("-", ""));		
		//동적테이블 생성
		String tableName = "sh_card_hs_amt_from";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);
		String dytbl = chkTblInfo(tableName, salesVO.getSmonth(), salesVO.getEmonth(), arrTbl);	
		if(dytbl.trim().equals("")){
			return null;
		}else{
			salesVO.setDytbl(dytbl);
			salesVO.setDongcd(commService.changeCommaInfo(salesVO.getDongcd()));
			return dao.selectSiSalesList(salesVO); //행정동별 검색
		}
	}
	
	@Override
	public List<?> selectSiGeomList(SalesAnalsVO salesVO){		
		salesVO.setSmonth(salesVO.getSmonth().replaceAll("-", ""));
		salesVO.setEmonth(salesVO.getEmonth().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "sh_card_hs_amt_from";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);
		String dytbl = chkTblInfo(tableName, salesVO.getSmonth(), salesVO.getEmonth(), arrTbl);	
		if(dytbl.trim().equals("")){
			return null;
		}else{
			salesVO.setDytbl(dytbl);					
			String atype = salesVO.getAtype().trim();
			if(atype.equals("area"))
				return dao.selectSiSalesArea_geom(salesVO); //행정동별 검색
			else
				return dao.selectSiSalesUser_geom(salesVO); //사용자지정영역 검색
		}
	}
	
	/**
	 * 시군구별 분석 (리스트, geometry)
	 * @param SalesAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectSigunSalesList(SalesAnalsVO salesVO){		
		salesVO.setSmonth(salesVO.getSmonth().replaceAll("-", ""));
		salesVO.setEmonth(salesVO.getEmonth().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "sh_card_hs_amt_from";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);
		String dytbl = chkTblInfo(tableName, salesVO.getSmonth(), salesVO.getEmonth(), arrTbl);	
		if(dytbl.trim().equals("")){
			return null;
		}else{					
			salesVO.setDytbl(dytbl);
			salesVO.setDongcd(commService.changeCommaInfo(salesVO.getDongcd()));
			return dao.selectSigunSalesList(salesVO); //행정동별 검색
		}
	}
	@Override
	public List<?> selectSigunGeomList(SalesAnalsVO salesVO){		
		salesVO.setSmonth(salesVO.getSmonth().replaceAll("-", ""));
		salesVO.setEmonth(salesVO.getEmonth().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "sh_card_hs_amt_from";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);
		String dytbl = chkTblInfo(tableName, salesVO.getSmonth(), salesVO.getEmonth(), arrTbl);	
		if(dytbl.trim().equals("")){
			return null;
		}else{					
			salesVO.setDytbl(dytbl);			
			String atype = salesVO.getAtype().trim();
			if(atype.equals("area"))
				return dao.selectSigunSalesArea_geom(salesVO); //행정동별 검색
			else
				return dao.selectSigunSalesUser_geom(salesVO); //사용자지정영역 검색
		}
	}
	
	
	/**
	 * 시군구별 분석 (리스트, geometry)
	 * @param SalesAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectAdjSalesList(SalesAnalsVO salesVO){		
		salesVO.setSmonth(salesVO.getSmonth().replaceAll("-", ""));
		salesVO.setEmonth(salesVO.getEmonth().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "sh_card_hs_amt_from";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);
		String dytbl = chkTblInfo(tableName, salesVO.getSmonth(), salesVO.getEmonth(), arrTbl);	
		if(dytbl.trim().equals("")){
			return null;
		}else{					
			salesVO.setDytbl(dytbl);
			salesVO.setDongcd(commService.changeCommaInfo(salesVO.getDongcd()));
			return dao.selectAdjSalesList(salesVO); //행정동별 검색
		}
	}
	@Override
	public List<?> selectAdjGeomList(SalesAnalsVO salesVO){		
		salesVO.setSmonth(salesVO.getSmonth().replaceAll("-", ""));
		salesVO.setEmonth(salesVO.getEmonth().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "sh_card_hs_amt_from";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);
		String dytbl = chkTblInfo(tableName, salesVO.getSmonth(), salesVO.getEmonth(), arrTbl);	
		if(dytbl.trim().equals("")){
			return null;
		}else{					
			salesVO.setDytbl(dytbl);					
			String atype = salesVO.getAtype().trim();
			if(atype.equals("area"))
				return dao.selectAdjSalesArea_geom(salesVO); //행정동별 검색
			else
				return dao.selectAdjSalesUser_geom(salesVO); //사용자지정영역 검색
		}
	}
	
	
	/**
	 * create Excel info
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public XSSFWorkbook createExcelInfo(ExcelVO excelVo, SalesAnalsVO salesVo){
		String id = excelVo.getEid();
		//활용목적 저장
		String title = "";
		if(id.trim().equals("1-5-1"))
			title = "유입매출-시도별분석";
		else if(id.trim().equals("1-5-2"))
			title = "유입매출-시군구별(서울 경기)분석";
		else if(id.trim().equals("1-5-3"))
			title = "유입매출-화성시내인접분석";		
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
				if(id.trim().equals("1-5-1")){				
					String[] colNames = new String[]{"구분","유입매출(원)"};
					String[] colIds = new String[]{"item","cnt"};				
					//List<?> result = selectSiSalesList(salesVo); //db연결
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
				}else if(id.trim().equals("1-5-2")){				
					String[] colNames = new String[]{"구분","유입매출(원)"};
					String[] colIds = new String[]{"item","cnt"};
					//List<?> result = selectSigunSalesList(salesVo); //db연결
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
				}else if(id.trim().equals("1-5-3")){				
					String[] colNames = new String[]{"구분","유입매출(원)"};
					String[] colIds = new String[]{"item","cnt"};
					//List<?> result = selectAdjSalesList(salesVo); //db
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
	
	
	/**
	 * 단순 테이블 유무 확인 후 동적테이블 생성
	 * 테이블명
	 * 시작월 또는 일
	 * 종료월 또는 일
	 * db에 저장되어 있는 테이블명
	 */
	private String chkTblInfo(String tableName, String start, String end, List<?> arrTbl){
		String result = "";
		
		String startPeriod = "";		
		String endPeriod = "";
		startPeriod = start.substring(0, 4);
		endPeriod = end.substring(0, 4);
		
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
}