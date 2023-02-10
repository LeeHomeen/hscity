package kr.co.socsoft.gis.sales.service.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.socsoft.gis.comm.service.CommService;
import kr.co.socsoft.gis.comm.vo.CommVO;
import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.comm.vo.LogVO;
import kr.co.socsoft.gis.pop.vo.PopAnalsGisVO;
import kr.co.socsoft.gis.sales.service.SalesAnalsDao;
import kr.co.socsoft.gis.sales.service.SalesAnalsService;
import kr.co.socsoft.gis.sales.vo.SalesAnalsVO;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.awt.ShapeWriter;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * 매출분석 서비스  SERVICE
 * @author SOC SOFT
 *
 */
@Service("salesAnalsService")
public class SalesAnalsServiceImpl implements SalesAnalsService{
	
	@Resource(name = "commService")
	private CommService commService;
	
	@Resource(name = "salesAnalsDao")
	private SalesAnalsDao dao;
	
	/**
	 * 기간별 분석 (국가기초구역)
	 * @param SalesAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectYearSalesList(SalesAnalsVO salesVO){
		salesVO = setSalesVo(salesVO);		
		//동적테이블 생성
		String tableName = "sh_card_hs_amt_demo";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);		
		String dytbl = "";
		if(salesVO.getStd().trim().equals("day"))
			dytbl = chkTblInfo(tableName, salesVO.getSday(), salesVO.getEday(), salesVO.getStd(), arrTbl);
		else
			dytbl = chkTblInfo(tableName, salesVO.getSmonth(), salesVO.getEmonth(), salesVO.getStd(), arrTbl);
		if(dytbl.trim().equals("")){
			return null;
		}else{
			String atype = salesVO.getAtype().trim();
			salesVO.setDytbl(dytbl);
			if(atype.equals("area"))
				return dao.selectYearSalesList_area(salesVO); //행정동별 검색
			else
				return dao.selectYearSalesList_user(salesVO); //사용자지정영역 검색
		}
	}
	@Override
	public List<?> selectYearSales_map(PopAnalsGisVO popVO){
		popVO.setDONGCD(commService.changeCommaInfo(popVO.getDONGCD()));		
		popVO.setSMONTH(popVO.getSMONTH().replaceAll("-", "") + "01");
		popVO.setEMONTH(popVO.getEMONTH().replaceAll("-", "") + "31");
		popVO.setSDAY(popVO.getSDAY().replaceAll("-", ""));
		popVO.setEDAY(popVO.getEDAY().replaceAll("-", ""));
		return dao.selectYearSales_map(popVO);
	}
	
	/**
	 * 성 연령별 분석 (국가기초구역)
	 * @param SalesAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectGASalesList(SalesAnalsVO salesVO){
		String tableName = "sh_card_hs_amt_demo";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);		
		String dytbl = "";
		if(salesVO.getStd().trim().equals("day"))
			dytbl = chkTblInfo(tableName, salesVO.getSday(), salesVO.getEday(), salesVO.getStd(), arrTbl);
		else
			dytbl = chkTblInfo(tableName, salesVO.getSmonth(), salesVO.getEmonth(), salesVO.getStd(), arrTbl);
		if(dytbl.trim().equals("")){
			return null;
		}else{
			salesVO.setDytbl(dytbl);
			salesVO = setSalesVo(salesVO);		
			salesVO.setQuery(setDynamicAgeQuery(salesVO.getAge()));		
			String atype = salesVO.getAtype().trim();
			if(atype.equals("area"))
				return dao.selectGASalesList_area(salesVO);
			else
				return dao.selectGASalesList_user(salesVO);	
		}
	}
	@Override
	public List<?> selectGASales_map(PopAnalsGisVO popVO){
		popVO.setSMONTH(popVO.getSMONTH().replaceAll("-", "") + "01");
		popVO.setEMONTH(popVO.getEMONTH().replaceAll("-", "") + "31");
		popVO.setSDAY(popVO.getSDAY().replaceAll("-", ""));
		popVO.setEDAY(popVO.getEDAY().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "sh_card_hs_amt_demo";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);		
		String dytbl = "";
		if(popVO.getSTD().trim().equals("day"))
			dytbl = chkTblInfo(tableName, popVO.getSDAY(), popVO.getEDAY(), popVO.getSTD(), arrTbl);
		else
			dytbl = chkTblInfo(tableName, popVO.getSMONTH(), popVO.getEMONTH(), popVO.getSTD(), arrTbl);
		if(dytbl.trim().equals("")){
			return null;
		}else{
			popVO.setDYTBL(dytbl);		
			popVO.setDONGCD(commService.changeCommaInfo(popVO.getDONGCD()));
			return dao.selectGASales_map(popVO);
		}
	}
	
	
	/**
	 * 시간대별 분석 (국가기초지역)
	 * @param SalesAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectTimeSalesList(SalesAnalsVO salesVO){
		String tableName = "sh_card_hs_amt_time";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);		
		String dytbl = "";
		if(salesVO.getStd().trim().equals("day"))
			dytbl = chkTblInfo(tableName, salesVO.getSday(), salesVO.getEday(), salesVO.getStd(), arrTbl);
		else
			dytbl = chkTblInfo(tableName, salesVO.getSmonth(), salesVO.getEmonth(), salesVO.getStd(), arrTbl);
		if(dytbl.trim().equals("")){
			return null;
		}else{
			salesVO.setDytbl(dytbl);			
			salesVO = setSalesVo(salesVO);
			salesVO.setTime(commService.changeCommaInfo(salesVO.getTime()));		
			String atype = salesVO.getAtype().trim();
			if(atype.equals("area"))
				return dao.selectTimeSalesList_area(salesVO);
			else
				return dao.selectTimeSalesList_user(salesVO);
		}
	}
	@Override
	public List<?> selectTimeSales_map(PopAnalsGisVO popVO){				
		popVO.setSMONTH(popVO.getSMONTH().replaceAll("-", "") + "01");
		popVO.setEMONTH(popVO.getEMONTH().replaceAll("-", "") + "31");
		popVO.setSDAY(popVO.getSDAY().replaceAll("-", ""));
		popVO.setEDAY(popVO.getEDAY().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "sh_card_hs_amt_time";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);		
		String dytbl = "";
		if(popVO.getSTD().trim().equals("day"))
			dytbl = chkTblInfo(tableName, popVO.getSDAY(), popVO.getEDAY(), popVO.getSTD(), arrTbl);
		else
			dytbl = chkTblInfo(tableName, popVO.getSMONTH(), popVO.getEMONTH(), popVO.getSTD(), arrTbl);
		if(dytbl.trim().equals("")){
			return null;
		}else{
			popVO.setDYTBL(dytbl);
			popVO.setDONGCD(commService.changeCommaInfo(popVO.getDONGCD()));
			return dao.selectTimeSales_map(popVO);
		}	
	}
	
	/**
	 * 업종별 분석 (국가기초지역)
	 * @param SalesAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectCtgSalesList(SalesAnalsVO salesVO){
		String tableName = "sh_card_hs_amt_demo";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);		
		String dytbl = "";
		if(salesVO.getStd().trim().equals("day"))
			dytbl = chkTblInfo(tableName, salesVO.getSday(), salesVO.getEday(), salesVO.getStd(), arrTbl);
		else
			dytbl = chkTblInfo(tableName, salesVO.getSmonth(), salesVO.getEmonth(), salesVO.getStd(), arrTbl);
		if(dytbl.trim().equals("")){
			return null;
		}else{
			salesVO.setDytbl(dytbl);		
			salesVO = setSalesVo(salesVO);
			String atype = salesVO.getAtype().trim();
			if(atype.equals("area"))
				return dao.selectCtgSalesList_area(salesVO);
			else
				return dao.selectCtgSalesList_user(salesVO);
		}
	}
	@Override
	public List<?> selectCtgSales_map(PopAnalsGisVO popVO){				
		popVO.setSMONTH(popVO.getSMONTH().replaceAll("-", "") + "01");
		popVO.setEMONTH(popVO.getEMONTH().replaceAll("-", "") + "31");
		popVO.setSDAY(popVO.getSDAY().replaceAll("-", ""));
		popVO.setEDAY(popVO.getEDAY().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "sh_card_hs_amt_demo";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);		
		String dytbl = "";
		if(popVO.getSTD().trim().equals("day"))
			dytbl = chkTblInfo(tableName, popVO.getSDAY(), popVO.getEDAY(), popVO.getSTD(), arrTbl);
		else
			dytbl = chkTblInfo(tableName, popVO.getSMONTH(), popVO.getEMONTH(), popVO.getSTD(), arrTbl);
		if(dytbl.trim().equals("")){
			return null;
		}else{
			popVO.setDYTBL(dytbl);
			popVO.setDONGCD(commService.changeCommaInfo(popVO.getDONGCD()));
			return dao.selectCtgSales_map(popVO);
		}
	}
	
	/**
	 * 외국인 분석 (국가기초지역)
	 * @param SalesAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectFgnSalesList(SalesAnalsVO salesVO){
		salesVO = setSalesVo(salesVO);
		String atype = salesVO.getAtype().trim();
		if(atype.equals("area"))
			return dao.selectFgnSalesList_area(salesVO);
		else
			return dao.selectFgnSalesList_user(salesVO);
	}
	@Override
	public List<?> selectFgnSales_map(PopAnalsGisVO popVO){
		popVO.setDONGCD(commService.changeCommaInfo(popVO.getDONGCD()));		
		popVO.setSMONTH(popVO.getSMONTH().replaceAll("-", "") + "01");
		popVO.setEMONTH(popVO.getEMONTH().replaceAll("-", "") + "31");
		popVO.setSDAY(popVO.getSDAY().replaceAll("-", ""));
		popVO.setEDAY(popVO.getEDAY().replaceAll("-", ""));
		return dao.selectFgnSales_map(popVO);
	}
	
	/**
	 * 범례정보
	 */
	@Override
	public List<?> selectSaleYearLgdInfo(SalesAnalsVO salesVO){
		//기간별
		return dao.selectSaleYearLgdInfo(setSalesVo(salesVO));
	}
	@Override
	public List<?> selectSaleGALgdInfo(SalesAnalsVO salesVO){
		//성연령별
		//동적테이블 생성
		String tableName = "sh_card_hs_amt_demo";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);		
		String dytbl = "";
		if(salesVO.getStd().trim().equals("day"))
			dytbl = chkTblInfo(tableName, salesVO.getSday(), salesVO.getEday(), salesVO.getStd(), arrTbl);
		else
			dytbl = chkTblInfo(tableName, salesVO.getSmonth(), salesVO.getEmonth(), salesVO.getStd(), arrTbl);
		if(dytbl.trim().equals("")){
			return null;
		}else{
			salesVO.setDytbl(dytbl);
			return dao.selectSaleGALgdInfo(setSalesVo(salesVO));
		}
	}
	@Override
	public List<?> selectSaleTimeLgdInfo(SalesAnalsVO salesVO){
		//시간대별
		//동적테이블 생성
		String tableName = "sh_card_hs_amt_time";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);		
		String dytbl = "";
		if(salesVO.getStd().trim().equals("day"))
			dytbl = chkTblInfo(tableName, salesVO.getSday(), salesVO.getEday(), salesVO.getStd(), arrTbl);
		else
			dytbl = chkTblInfo(tableName, salesVO.getSmonth(), salesVO.getEmonth(), salesVO.getStd(), arrTbl);
		if(dytbl.trim().equals("")){
			return null;
		}else{
			salesVO.setDytbl(dytbl);
			return dao.selectSaleTimeLgdInfo(setSalesVo(salesVO));
		}
	}
	@Override
	public List<?> selectSaleCtgLgdInfo(SalesAnalsVO salesVO){
		//업종별
		//동적테이블 생성
		String tableName = "sh_card_hs_amt_demo";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);		
		String dytbl = "";
		if(salesVO.getStd().trim().equals("day"))
			dytbl = chkTblInfo(tableName, salesVO.getSday(), salesVO.getEday(), salesVO.getStd(), arrTbl);
		else
			dytbl = chkTblInfo(tableName, salesVO.getSmonth(), salesVO.getEmonth(), salesVO.getStd(), arrTbl);
		if(dytbl.trim().equals("")){
			return null;
		}else{
			salesVO.setDytbl(dytbl);
			return dao.selectSaleCtgLgdInfo(setSalesVo(salesVO));
		}
	}
	@Override
	public List<?> selectSaleFngLgdInfo(SalesAnalsVO salesVO){
		//외국인별
		return dao.selectSaleFngLgdInfo(setSalesVo(salesVO));
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
		if(id.trim().equals("1-4-1"))
			title = "매출분석-기간별분석";
		else if(id.trim().equals("1-4-2"))
			title = "매출분석-성연령별분석";
		else if(id.trim().equals("1-4-3"))
			title = "매출분석-시간대별분석";
		else if(id.trim().equals("1-4-4"))
			title = "매출분석-업종별분석";		
		else if(id.trim().equals("1-4-5"))
			title = "매출분석-외국인분석";
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
				if(id.trim().equals("1-4-1")){				
					String[] colNames = new String[]{"구분","기간","내지인(원)","외지인(원)","인접지인(원)","합계(원)"};
					/* db에서 직접 읽기
					String[] colIds = new String[]{"div","item","apop","bpop","cpop","tot"};									
					List<?> result = selectYearSalesList(salesVo);
					*/
					//jqgrid에서 직접 get					
					ObjectMapper mapper = new ObjectMapper();
					List<EgovMap> result;
					try {
						String[] colIds = new String[]{"div","cst","apop","bpop","cpop","tot"};
						mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
						result = mapper.readValue(excelVo.getExparams(), new TypeReference<List<EgovMap>>(){});							
						list.add(result);
						nameList.add(colNames);
						idsList.add(colIds);
					} catch (Exception e) {
						e.printStackTrace();
					}				
				}else if(id.trim().equals("1-4-2")){				
					String[] colNames = new String[]{"구분","연령","남자매출(원)","여자매출(원)","합계(원)"};
					String[] colIds = new String[]{"div","gb","mcnt","wcnt","tot"};
					/* db에서 직접 읽기
					salesVo.setAge(salesVo.getAge().replaceAll("%2C", ","));										
					List<?> result = selectGASalesList(salesVo);
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
				}else if(id.trim().equals("1-4-3")){				
					String[] colNames = new String[]{"구분","시간","합계(원)"};
					String[] colIds = new String[]{"div","item","tot"};
					/* db에서 직접 읽기
					salesVo.setTime(salesVo.getTime().replaceAll("%2C", ","));					
					List<?> result = selectTimeSalesList(salesVo); 
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
				}else if(id.trim().equals("1-4-4")){				
					String[] colNames = new String[]{"구분","대분류","중분류","소분류","소비매출(명)"};
					String[] colIds = new String[]{"div","lnm","mnm", "snm", "tot"};
					/* db에서 직접 읽기
					salesVo.setCtg(salesVo.getCtg().replaceAll("%25", "%"));
					List<?> result = selectCtgSalesList(salesVo);
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
				}else if(id.trim().equals("1-4-5")){
					String[] colNames = new String[]{"구분","국가","이용건수(건)","소비매출(명)"};
					String[] colIds = new String[]{"div","item","cnt", "tot"};
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
	
	
	/////////////////////////////////////////////////////////////
	////////////////// 이미지 생성 ///////////////////////////////////
	/////////////////////////////////////////////////////////////
	
	private SalesAnalsVO setSalesVo(SalesAnalsVO salesVO){
		salesVO.setDongcd(commService.changeCommaInfo(salesVO.getDongcd()));
		salesVO.setSmonth(salesVO.getSmonth().replaceAll("-", "") + "01");
		salesVO.setEmonth(salesVO.getEmonth().replaceAll("-", "") + "31");
		salesVO.setSday(salesVO.getSday().replaceAll("-", ""));
		salesVO.setEday(salesVO.getEday().replaceAll("-", ""));
		return salesVO;
	}
	
	@Override
	public BufferedImage createSalesImg(PopAnalsGisVO popVO){
		//격자 이미지 만들기    	
    	String x = popVO.getSCREENX();
    	String y = popVO.getSCREENY();
    	BufferedImage image = null;
    	if(x != null && y != null){
    		image = new BufferedImage(Integer.parseInt(popVO.getSCREENX()), Integer.parseInt(popVO.getSCREENY()), BufferedImage.TYPE_INT_ARGB);
        	Graphics2D g = image.createGraphics();
        	
        	String menu = popVO.getMENU(); //선택 메뉴정보
        	List<?> list = null;
        	if(menu.trim().equals("year"))      //기간별
        		list = selectYearSales_map(popVO);
        	else if(menu.trim().equals("time")) //시간대별
        		list = selectTimeSales_map(popVO);
        	else if(menu.trim().equals("age"))  //성연령별
        		list = selectGASales_map(popVO);
        	else if(menu.trim().equals("ctg"))  //업종별
        		list = selectCtgSales_map(popVO);
        	else if(menu.trim().equals("fgn"))  //외국인
        		list = selectFgnSales_map(popVO);
        	
        	if(list != null && list.size() > 0){
        		WKTReader wktReader = new WKTReader(); 
        		long max = 0;
        		int min = 0;
        		for(int i=0; i<list.size(); i++){
        			EgovMap map = (EgovMap)list.get(i);
        			if(i == 0){
        				if(map.get("tot") != null){
        					//max = ((BigDecimal)map.get("tot")).intValue();
        					String strMax = map.get("tot").toString();
        					max = Long.parseLong(strMax);
        				}
        				if(map.get("min") != null){
        					min = (int)Double.parseDouble(map.get("min").toString());
        				}
        			}else{
        				Polygon poly = null;
						try {
							poly = (Polygon)wktReader.read((String)map.get("wkt"));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            			long tot = 0;        			
            			if(map.get("tot") != null){
            				//tot = ((BigDecimal)map.get("tot")).intValue();
            				String strTot = map.get("tot").toString();
        					tot = Long.parseLong(strTot);
            			}        		
            			//등릅
            			int grade = commService.getImgGrade(max, min, tot);            			
            			Color col = commService.getImgColor(popVO.getVCOLOR(), grade, 150);			
            			ShapeWriter sw = new ShapeWriter();
            			Shape polyShape = sw.toShape(poly);            			            			
            			g.setColor(col);
            			g.fill(polyShape);
            			g.setColor(commService.getSrokeColor()); //경계라인
            			g.draw(polyShape);	
        			}
        		}
        	}
    	}else
    		image = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
    	
    	return image;
	}
	
	//성연령별 체크정보로 WHERE절 쿼리 생성
	private String setDynamicAgeQuery(String age){
		String query = "";
		if(!age.trim().equals("")){			
			String[] arrAge = age.split(",");
			if(arrAge.length > 0){
				query += "and (";
				for(int i=0; i<arrAge.length; i++){
					String chk = arrAge[i];
					if(chk.trim().indexOf("m") > -1){
						query += "( sex_ccd = 'M'";
						query += " and age_gb in (select age_cd from view_card_age where chk = " + chk.replace("m", "") + ") )";
					}else if(chk.trim().indexOf("f") > -1){
						query += "( sex_ccd = 'F'";
						query += " and age_gb in (select age_cd from view_card_age where chk = " + chk.replace("f", "") + ") )";
					}
					if(i < arrAge.length -1)
						query += " or ";
				}
				query += " ) ";
			}else{
				query += " and age_gb = '' "; //없을경우
			}
		}else{
			query += " and age_gb = '' "; //없을경우
		}
		return query;
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
