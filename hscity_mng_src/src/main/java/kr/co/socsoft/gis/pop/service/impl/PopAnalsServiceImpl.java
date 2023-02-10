package kr.co.socsoft.gis.pop.service.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import kr.co.socsoft.gis.comm.service.CommService;
import kr.co.socsoft.gis.comm.vo.CommVO;
import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.comm.vo.LogVO;
import kr.co.socsoft.gis.pop.service.PopAnalsDao;
import kr.co.socsoft.gis.pop.service.PopAnalsService;
import kr.co.socsoft.gis.pop.vo.PopAnalsGisVO;
import kr.co.socsoft.gis.pop.vo.PopAnalsVO;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.SimpleDateFormat;
import com.vividsolutions.jts.awt.ShapeWriter;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * 인구분석 서비스  SERVICE
 * @author SOC SOFT
 *
 */
@Service("popAnalsService")
public class PopAnalsServiceImpl implements PopAnalsService{
	
	@Resource(name = "commService")
	private CommService commService;
	
	@Resource(name = "popAnalsDao")
	private PopAnalsDao dao;	
	
	/**
	 * 기간별 분석 (밀도맵, 격자, 벌집)
	 * @param PopAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectYearPopList(PopAnalsVO popVO){
		//동적테이블 생성
		String tableName = "hsp_rsgst_popltn_p";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);
		String dytbl = chkTblInfo(tableName, popVO.getSmonth(), popVO.getPeriod(), arrTbl);
		if(dytbl.trim().equals("")){
			return null;
		}else{
			popVO.setDytbl(dytbl);
			popVO.setDongcd(commService.changeCommaInfo(popVO.getDongcd()));		
			return dao.selectYearPopList(popVO);
		}
	}
	@Override
	public List<?> selectYearPop_heat(PopAnalsGisVO popVO){
		popVO.setDONGCD(commService.changeCommaInfo(popVO.getDONGCD()));
		return dao.selectYearPop_heat(popVO);
	}
	@Override
	public List<?> selectYearPop_grid(PopAnalsGisVO popVO){
		//dong 변환		
		popVO.setDONGCD(commService.changeCommaInfo(popVO.getDONGCD()));
		return dao.selectYearPop_grid(popVO);
	}
	@Override
	public List<?> selectYearPop_beehive(PopAnalsGisVO popVO){
		popVO.setDONGCD(commService.changeCommaInfo(popVO.getDONGCD()));
		return dao.selectYearPop_beehive(popVO);
	}	
	
	/**
	 * 성 연령별 분석 (밀도맵, 격자, 벌집)
	 * @param PopAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectGAPopList(PopAnalsVO popVO){
		//테이블 유무확인
		String tableName = "hsp_rsgst_popltn_p";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);
		String dytbl = chkTblInfo(tableName, popVO.getSmonth(), arrTbl);
		if(dytbl.trim().equals("")){
			return null;
		}else{
			popVO.setDongcd(commService.changeCommaInfo(popVO.getDongcd()));
			return dao.selectGAPopList(popVO);
		}
	}
	@Override
	public List<?> selectGAPop_heat(PopAnalsGisVO popVO){
		popVO.setDONGCD(commService.changeCommaInfo(popVO.getDONGCD()));
		return dao.selectGAPop_heat(popVO);
	}
	@Override
	public List<?> selectGAPop_grid(PopAnalsGisVO popVO){
		popVO.setDONGCD(commService.changeCommaInfo(popVO.getDONGCD()));
		return dao.selectGAPop_grid(popVO);
	}
	@Override
	public List<?> selectGAPop_beehive(PopAnalsGisVO popVO){
		popVO.setDONGCD(commService.changeCommaInfo(popVO.getDONGCD()));
		return dao.selectGAPop_beehive(popVO);
	}
	
	/**
	 * 전입 분석  지역/요인/geom정보
	 * @param PopAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectInPopCntList(PopAnalsVO popVO){
		//테이블 유무 확인
		String tableName = "hsp_mvinnm_p";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);
		String dytbl = chkTblInfo(tableName, popVO.getSmonth(), arrTbl);
		if(dytbl.trim().equals("")){
			return null;
		}else{
			popVO.setDongcd(commService.changeCommaInfo(popVO.getDongcd()));
			return dao.selectInPopCntList(popVO);
		}
		
	}
	@Override
	public List<?> selectInPopResnList(PopAnalsVO popVO){
		//popVO.setDongcd(changeDongInfo(popVO.getDongcd()));
		return dao.selectInPopResnList(popVO);
	}
	@Override
	public List<?> selectInPopGeomList(PopAnalsVO popVO){
		//popVO.setDongcd(changeDongInfo(popVO.getDongcd()));
		return dao.selectInPopGeomList(popVO);
	}
	
	/**
	 * 전출 분석 지역/요인/geom
	 * @param PopAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectOutPopCntList(PopAnalsVO popVO){
		//테이블 유무 확인
		String tableName = "hsp_mvotnm_p";
		CommVO commVO = new CommVO(); commVO.setTbl(tableName);		
		List<?> arrTbl = commService.selectTableInfo(commVO);
		String dytbl = chkTblInfo(tableName, popVO.getSmonth(), arrTbl);
		if(dytbl.trim().equals("")){
			return null;
		}else{
			popVO.setDongcd(commService.changeCommaInfo(popVO.getDongcd()));
			return dao.selectOutPopCntList(popVO);
		}
	}
	@Override
	public List<?> selectOutPopResnList(PopAnalsVO popVO){
		return dao.selectOutPopResnList(popVO);
	}
	@Override
	public List<?> selectOutPopGeomList(PopAnalsVO popVO){
		return dao.selectOutPopGeomList(popVO);
	}
	
	/**
	 * 범례를 위한 min, max값 가져오기
	 */
	@Override
	public List<?> selectPopYearLgdInfo(PopAnalsVO popVO){
		//기간별 분석 범례정보 min max
		popVO.setDongcd(commService.changeCommaInfo(popVO.getDongcd()));
		return dao.selectPopYearLgdInfo(popVO);
	}
	@Override
	public List<?> selectPopGALgdInfo(PopAnalsVO popVO){
		//성연령별 분석 범례정보 min max
		popVO.setDongcd(commService.changeCommaInfo(popVO.getDongcd()));
		return dao.selectPopGALgdInfo(popVO);
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
		if(id.trim().equals("1-1-1"))
			title = "인구분석-기간별분석";
		else if(id.trim().equals("1-1-2"))
			title = "인구분석-성연령별분석";
		else if(id.trim().equals("1-1-3"))
			title = "인구분석-전입분석";
		else if(id.trim().equals("1-1-4"))
			title = "인구분석-전출분석";		
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
				if(id.trim().equals("1-1-1")){
					//인구분석_기간별 분석				
					String[] colNames = new String[]{"구분","기간","10세미만(명)","10대(명)","20대(명)","30대(명)","40대(명)","50대(명)","60대(명)","70대(명)","80대(명)","90대(명)","100세이상(명)","합계(명)"};
					/* db에서 직접 get
					String[] colIds = new String[]{"div","item","a","b","c","d","e","f","g","h","i","j","k","tot"};				
					List<?> result = selectYearPopList(popVo); 
					*/
					//jqgrid에서 직접 get
					String[] colIds = new String[]{"div","cst","a00","a10","a20","a30","a40","a50","a60","a70","a80","a90","a100","tot"};					
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
				}else if(id.trim().equals("1-1-2")){
					String[]colNames = new String[]{"구분","연령","남자인구수(명)","여자인구수(명)","합계(명)"};
					String[]colIds = new String[]{"div","age","mcnt","wcnt","tot"};
					/* db에서 직접 읽기									
					List<?> result = selectGAPopList(popVo);
					*/
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
				}else if(id.trim().equals("1-1-3")){
					//결과가 두개				
					String[]colNames = new String[]{"전입지역","전입자수(명)"};
					String[]colIds = new String[]{"item","cnt"};
					nameList.add(colNames);
					idsList.add(colIds);
					colNames = new String[]{"전입요인","건수(건)"};
					colIds = new String[]{"item","cnt"};
					nameList.add(colNames);
					idsList.add(colIds);
					/* db용
					List<?> result = selectInPopCntList(popVo); //db에서 직접읽기
					list.add(result);
					result = selectInPopResnList(popVo); //db에서 직접읽기
					list.add(result);
					*/
					//jqgrid에서 직접 get					
					ObjectMapper mapper = new ObjectMapper();
					List<EgovMap> result;
					try {
						mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
						result = mapper.readValue(excelVo.getExparams(), new TypeReference<List<EgovMap>>(){});													
						list.add(result);
						result = mapper.readValue(excelVo.getExparams1(), new TypeReference<List<EgovMap>>(){});
						list.add(result);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(id.trim().equals("1-1-4")){
					//결과가 두개
					String[]colNames = new String[]{"전출지역","전출자수(명)"};
					String[]colIds = new String[]{"item","cnt"};
					nameList.add(colNames);
					idsList.add(colIds);
					colNames = new String[]{"전출요인","건수(건)"};
					colIds = new String[]{"item","cnt"};
					nameList.add(colNames);
					idsList.add(colIds);
					/* db에서 직접 읽기
					List<?> result = selectOutPopCntList(popVo);
					list.add(result);
					result = selectOutPopResnList(popVo);
					list.add(result);
					*/		
					//jqgrid에서 직접 get					
					ObjectMapper mapper = new ObjectMapper();
					List<EgovMap> result;
					try {
						mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
						result = mapper.readValue(excelVo.getExparams(), new TypeReference<List<EgovMap>>(){});													
						list.add(result);
						result = mapper.readValue(excelVo.getExparams1(), new TypeReference<List<EgovMap>>(){});
						list.add(result);
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
	
	////////////////// 이미지 생성 /////////////////////////
	@Override
	public BufferedImage createPopImg(PopAnalsGisVO popVO){
		//격자 이미지 만들기    	
    	String x = popVO.getSCREENX();
    	String y = popVO.getSCREENY();
    	BufferedImage image = null;
    	if(x != null && y != null){
    		image = new BufferedImage(Integer.parseInt(popVO.getSCREENX()), Integer.parseInt(popVO.getSCREENY()), BufferedImage.TYPE_INT_ARGB);
        	Graphics2D g = image.createGraphics();
        	 
        	String vtype = popVO.getVTYPE().trim();
        	List<?> list = null;
        	String menu = popVO.getMENU();
        	if(vtype != null && vtype.trim().equals("grid")){
        		if(menu.trim().equals("year"))  //기간별
        			list = selectYearPop_grid(popVO);
        		else if(menu.trim().equals("age")) //성연령별
        			list = selectGAPop_grid(popVO);
        	}
        	else if(vtype != null && vtype.trim().equals("beehive")){
        		if(menu.trim().equals("year"))    //기간별
        			list = selectYearPop_beehive(popVO);
        		else if(menu.trim().equals("age")) //성연령별
        			list = selectGAPop_beehive(popVO);
        	}
        	if(list.size() > 0){
        		WKTReader wktReader = new WKTReader(); 
        		int max = 0;
        		int min = 0;
        		for(int i=0; i<list.size(); i++){
        			EgovMap map = (EgovMap)list.get(i);
        			if(i == 0){
        				if(map.get("tot") != null){
	        				//max = ((Long)map.get("tot")).intValue();
                            max = Integer.parseInt(String.valueOf(map.get("tot")));
        				}        				
        				if(map.get("min") != null){
        					min = (int)Double.parseDouble(map.get("min").toString());
        				}
        			}else{
        				Polygon poly = null;
						try {
							poly = (Polygon)wktReader.read((String)map.get("wkt"));
						} catch (ParseException e) {
							e.printStackTrace();
						}
            			int tot = 0;        			
            			if(map.get("tot") != null){
            				//tot = ((Long)map.get("tot")).intValue();
                            tot = Integer.parseInt(String.valueOf(map.get("tot")));
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
	
	/**
	 * 단순 테이블 유무 확인 후 동적테이블 생성
	 */
	private String chkTblInfo(String tableName, String smonth, String period, List<?> arrTbl){
		String result = "";
		
		String startMon = smonth.replace("-", "");
		String endMon = "";
		SimpleDateFormat startFormat = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat endFormat = new SimpleDateFormat("yyyyMM");
		Date date = null;
		try{
			date = startFormat.parse(startMon);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			Calendar cal = new GregorianCalendar(Locale.KOREA);
			cal.setTime(date);
			if(period.trim().equals("month"))
				cal.add(Calendar.MONTH, -5);
			else
				cal.add(Calendar.YEAR, -1);			
			endMon = endFormat.format(cal.getTime());
		}			
		//System.out.println(startMon + "   " + endMon);
		
		int startYear = Integer.parseInt(startMon.substring(0, 4));
		int endYear = Integer.parseInt(endMon.substring(0, 4));
		if(arrTbl.size() > 0){
			for(int i=0; i<arrTbl.size(); i++){
				EgovMap map = (EgovMap)arrTbl.get(i);
				int unit = Integer.parseInt((map.get("unit")).toString());
				
				for(int j=endYear; j<=startYear; j++){
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
	
	/*
	 * 단순 테이블 유무만 확인
	 */
	private String chkTblInfo(String tableName, String smonth, List<?> arrTbl){
		String result = "";		
		String startMon = smonth.replace("-", "");		
		int startYear = Integer.parseInt(startMon.substring(0, 4));
		if(arrTbl.size() > 0){
			for(int i=0; i<arrTbl.size(); i++){
				EgovMap map = (EgovMap)arrTbl.get(i);
				int unit = Integer.parseInt((map.get("unit")).toString());
				//System.out.println(startYear + " " + unit);				
				if(startYear == unit){
					result += "Y";
					break;
				}
			}
		}
		return result;
	}
}
