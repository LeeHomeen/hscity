package kr.co.socsoft.gis.pop.service.impl;

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
import kr.co.socsoft.gis.pop.service.FPopAnalsDao;
import kr.co.socsoft.gis.pop.service.FPopAnalsService;
import kr.co.socsoft.gis.pop.vo.PopAnalsGisVO;
import kr.co.socsoft.gis.pop.vo.PopAnalsVO;

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
 * 유동인구분석 서비스  SERVICE
 * @author SOC SOFT
 *
 */
@Service("fPopAnalsService")
public class FPopAnalsServiceImpl implements FPopAnalsService{
	
	@Resource(name = "commService")
	private CommService commService;
	
	@Resource(name = "fPopAnalsDao")
	private FPopAnalsDao dao;	
	
	/**
	 * 기간별 분석 (밀도맵, 격자, 벌집)
	 * @param PopAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectYearFPopList(PopAnalsVO popVO){
		popVO.setSmonth(popVO.getSmonth().replaceAll("-", ""));
		popVO.setEmonth(popVO.getEmonth().replaceAll("-", ""));
		popVO.setSday(popVO.getSday().replaceAll("-", ""));
		popVO.setEday(popVO.getEday().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "skt_hs_flow_pop_age_p";
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
			return dao.selectYearFPopList(popVO);
		}
		
	}
	@Override
	public List<?> selectYearFPop_heat(PopAnalsGisVO popVO){
		popVO.setDONGCD(commService.changeCommaInfo(popVO.getDONGCD()));
		popVO.setSMONTH(popVO.getSMONTH().replaceAll("-", ""));
		popVO.setEMONTH(popVO.getEMONTH().replaceAll("-", ""));
		popVO.setSDAY(popVO.getSDAY().replaceAll("-", ""));
		popVO.setEDAY(popVO.getEDAY().replaceAll("-", ""));
		return dao.selectYearFPop_heat(popVO);
	}
	@Override
	public List<?> selectYearFPop_grid(PopAnalsGisVO popVO){
		popVO.setDONGCD(commService.changeCommaInfo(popVO.getDONGCD()));
		popVO.setSMONTH(popVO.getSMONTH().replaceAll("-", ""));
		popVO.setEMONTH(popVO.getEMONTH().replaceAll("-", ""));
		popVO.setSDAY(popVO.getSDAY().replaceAll("-", ""));
		popVO.setEDAY(popVO.getEDAY().replaceAll("-", ""));
		return dao.selectYearFPop_grid(popVO);
	}
	@Override
	public List<?> selectYearFPop_beehive(PopAnalsGisVO popVO){
		popVO.setDONGCD(commService.changeCommaInfo(popVO.getDONGCD()));
		popVO.setSMONTH(popVO.getSMONTH().replaceAll("-", ""));
		popVO.setEMONTH(popVO.getEMONTH().replaceAll("-", ""));
		popVO.setSDAY(popVO.getSDAY().replaceAll("-", ""));
		popVO.setEDAY(popVO.getEDAY().replaceAll("-", ""));
		return dao.selectYearFPop_beehive(popVO);
	}
	
	
	/**
	 * 성 연령별 분석 (밀도맵, 격자, 벌집)
	 * @param PopAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectGAFPopList(PopAnalsVO popVO){
		popVO.setSmonth(popVO.getSmonth().replaceAll("-", ""));
		popVO.setEmonth(popVO.getEmonth().replaceAll("-", ""));
		popVO.setSday(popVO.getSday().replaceAll("-", ""));
		popVO.setEday(popVO.getEday().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "skt_hs_flow_pop_age_p";
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
			return dao.selectGAFPopList(popVO);	
		}
	}
	@Override
	public List<?> selectGAFPop_heat(PopAnalsGisVO popVO){				
		popVO.setSMONTH(popVO.getSMONTH().replaceAll("-", ""));
		popVO.setEMONTH(popVO.getEMONTH().replaceAll("-", ""));
		popVO.setSDAY(popVO.getSDAY().replaceAll("-", ""));
		popVO.setEDAY(popVO.getEDAY().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "skt_hs_flow_pop_age_p";
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
			return dao.selectGAFPop_heat(popVO);			
		}
		
	}
	@Override
	public List<?> selectGAFPop_grid(PopAnalsGisVO popVO){		
		popVO.setSMONTH(popVO.getSMONTH().replaceAll("-", ""));
		popVO.setEMONTH(popVO.getEMONTH().replaceAll("-", ""));
		popVO.setSDAY(popVO.getSDAY().replaceAll("-", ""));
		popVO.setEDAY(popVO.getEDAY().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "skt_hs_flow_pop_age_p";
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
			return dao.selectGAFPop_grid(popVO);			
		}		
	}
	@Override
	public List<?> selectGAFPop_beehive(PopAnalsGisVO popVO){		
		popVO.setSMONTH(popVO.getSMONTH().replaceAll("-", ""));
		popVO.setEMONTH(popVO.getEMONTH().replaceAll("-", ""));
		popVO.setSDAY(popVO.getSDAY().replaceAll("-", ""));
		popVO.setEDAY(popVO.getEDAY().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "skt_hs_flow_pop_age_p";
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
			return dao.selectGAFPop_beehive(popVO);			
		}		
	}
	
	/**
	 * 시간대별 분석 (밀도맵, 격자, 벌집)
	 * @param PopAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectTimeFPopList(PopAnalsVO popVO){		
		popVO.setSmonth(popVO.getSmonth().replaceAll("-", ""));
		popVO.setEmonth(popVO.getEmonth().replaceAll("-", ""));
		popVO.setSday(popVO.getSday().replaceAll("-", ""));
		popVO.setEday(popVO.getEday().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "skt_hs_flow_pop_time_p";
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
			return dao.selectTimeFPopList(popVO);			
		}		
	}
	@Override
	public List<?> selectTimeFPop_heat(PopAnalsGisVO popVO){		
		popVO.setSMONTH(popVO.getSMONTH().replaceAll("-", ""));
		popVO.setEMONTH(popVO.getEMONTH().replaceAll("-", ""));
		popVO.setSDAY(popVO.getSDAY().replaceAll("-", ""));
		popVO.setEDAY(popVO.getEDAY().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "skt_hs_flow_pop_time_p";
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
			return dao.selectTimeFPop_heat(popVO);
		}
	}
	@Override
	public List<?> selectTimeFPop_grid(PopAnalsGisVO popVO){		
		popVO.setSMONTH(popVO.getSMONTH().replaceAll("-", ""));
		popVO.setEMONTH(popVO.getEMONTH().replaceAll("-", ""));
		popVO.setSDAY(popVO.getSDAY().replaceAll("-", ""));
		popVO.setEDAY(popVO.getEDAY().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "skt_hs_flow_pop_time_p";
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
			return dao.selectTimeFPop_grid(popVO);
		}
	}
	@Override
	public List<?> selectTimeFPop_beehive(PopAnalsGisVO popVO){		
		popVO.setSMONTH(popVO.getSMONTH().replaceAll("-", ""));
		popVO.setEMONTH(popVO.getEMONTH().replaceAll("-", ""));
		popVO.setSDAY(popVO.getSDAY().replaceAll("-", ""));
		popVO.setEDAY(popVO.getEDAY().replaceAll("-", ""));
		//동적테이블 생성
		String tableName = "skt_hs_flow_pop_time_p";
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
			return dao.selectTimeFPop_beehive(popVO);
		}
	}
	
	/**
	 * 범례를 위한 min, max값 가져오기
	 */
	@Override
	public List<?> selectFPopYearLgdInfo(PopAnalsVO popVO){		
		popVO.setSmonth(popVO.getSmonth().replaceAll("-", ""));
		popVO.setEmonth(popVO.getEmonth().replaceAll("-", ""));
		popVO.setSday(popVO.getSday().replaceAll("-", ""));
		popVO.setEday(popVO.getEday().replaceAll("-", ""));
		//기간별 분석 범례정보 min max
		//동적테이블 생성
		String tableName = "skt_hs_flow_pop_age_p";
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
			return dao.selectFPopYearLgdInfo(popVO);			
		}		
	}
	@Override
	public List<?> selectFPopGALgdInfo(PopAnalsVO popVO){		
		popVO.setSmonth(popVO.getSmonth().replaceAll("-", ""));
		popVO.setEmonth(popVO.getEmonth().replaceAll("-", ""));
		popVO.setSday(popVO.getSday().replaceAll("-", ""));
		popVO.setEday(popVO.getEday().replaceAll("-", ""));
		//성연령별 분석 범례정보 min max
		//동적테이블 생성
		String tableName = "skt_hs_flow_pop_age_p";
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
			return dao.selectFPopGALgdInfo(popVO);			
		}
	}
	@Override
	public List<?> selectFPopTimeLgdInfo(PopAnalsVO popVO){		
		popVO.setSmonth(popVO.getSmonth().replaceAll("-", ""));
		popVO.setEmonth(popVO.getEmonth().replaceAll("-", ""));
		popVO.setSday(popVO.getSday().replaceAll("-", ""));
		popVO.setEday(popVO.getEday().replaceAll("-", ""));
		//시간대별 분석 범례정보 min max
		//동적테이블 생성
		String tableName = "skt_hs_flow_pop_time_p";
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
			return dao.selectFPopTimeLgdInfo(popVO);			
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
		if(id.trim().equals("1-2-1"))
			title = "유동분석-기간별분석";
		else if(id.trim().equals("1-2-2"))
			title = "유동분석-성연령별분석";
		else if(id.trim().equals("1-2-3"))
			title = "유동분석-시간대별분석";
		//이력저장 완료
		LogVO logVO = new LogVO();
		logVO.setGubun(title);
		logVO.setUid(excelVo.getUid());
		logVO.setUsepps(excelVo.getPps().trim());
		//로그저장
		XSSFWorkbook xlsxWb = null;
		
		if(commService.insertExlLog(logVO)){
			List<List<?>> list = new ArrayList(); 
			List<String[]> nameList = new ArrayList(); //컬럼한글명
			List<String[]> idsList = new ArrayList();   //컴럼명
			//2개의 grid정보가 있는경우만 사용		
			
			if(id != null){
				BufferedImage mapImg = commService.getStringImg(excelVo.getMap());
				BufferedImage chartImg = commService.getStringImg(excelVo.getChart());
				if(id.trim().equals("1-2-1")){
					//인구분석_기간별 분석				
					String[] colNames = new String[]{"구분","기간","10대(명)","20대(명)","30대(명)","40대(명)","50대(명)","60대(명)","70대이상(명)","합계(명)"};
					/* db에서 직접 읽기 
					String[] colIds = new String[]{"div","item","p10","p20","p30","p40","p50","p60","p70","tot"};				
					List<?> result = selectYearFPopList(popVo);
					*/
					//jqgrid에서 직접 get
					String[] colIds = new String[]{"div","cst","p10","p20","p30","p40","p50","p60","p70","tot"};					
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
				}else if(id.trim().equals("1-2-2")){
					/* db에서 직접 읽기
					List<?> result = selectGAFPopList(popVo);
					List<String[]> colInfo = reGAResultList(result);
					*/ 
					//jqgrid에서 직접 get					
					ObjectMapper mapper = new ObjectMapper();
					List<EgovMap> result;
					try {
						mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
						result = mapper.readValue(excelVo.getExparams(), new TypeReference<List<EgovMap>>(){});
						List<String[]> colInfo = reGAResultList(result);
						//end							
						list.add(result);
						nameList.add(colInfo.get(0));
						idsList.add(colInfo.get(1));
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}else if(id.trim().equals("1-2-3")){
					/*
					List<?> result = selectTimeFPopList(popVo);
					List<String[]> colInfo = reTimeResultList(result);
					*/
					//jqgrid에서 직접 get					
					ObjectMapper mapper = new ObjectMapper();
					List<EgovMap> result;
					try {
						mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
						result = mapper.readValue(excelVo.getExparams(), new TypeReference<List<EgovMap>>(){});
						List<String[]> colInfo = reTimeResultList(result);
						//end							
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
	 * 이미지 생성
	 * @param popVO
	 * @return
	 */
	@Override
	public BufferedImage createFPopImg(PopAnalsGisVO popVO){
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
        			list = selectYearFPop_grid(popVO);
        		else if(menu.trim().equals("age")) //성연령별
        			list = selectGAFPop_grid(popVO);
        		else if(menu.trim().equals("time"))
        			list = selectTimeFPop_grid(popVO);
        	}
        	else if(vtype != null && vtype.trim().equals("beehive")){
        		if(menu.trim().equals("year"))    //기간별
        			list = selectYearFPop_beehive(popVO);
        		else if(menu.trim().equals("age")) //성연령별
        			list = selectGAFPop_beehive(popVO);
        		else if(menu.trim().equals("time")) //성연령별
        			list = selectTimeFPop_beehive(popVO);
        	}
        	if(list.size() > 0){
        		WKTReader wktReader = new WKTReader(); 
        		int max = 0;
        		int min = 0;
        		for(int i=0; i<list.size(); i++){
        			EgovMap map = (EgovMap)list.get(i);
        			if(i == 0){        				        
        				if(map.get("tot") != null){
        					max = ((BigDecimal)map.get("tot")).intValue();        					
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
            				tot = ((BigDecimal)map.get("tot")).intValue();
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
		/*
		if(std.trim().equals("day")){
			startPeriod = start.substring(0, 6);
			endPeriod = end.substring(0, 6);
		}else{
			startPeriod = start.substring(0, 6);
			endPeriod = end.substring(0, 6);	
		}
		*/
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
			colNames[0] = "구분";
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
	
	/**
	 * 컬럼정보 재정의
	 * @param list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<String[]> reTimeResultList(List<?> list){
		List<String[]> colInfo = new ArrayList();
		String[]colNames;
		String[]colIds;
		int resultCnt = 2;
		if(list.size() > 0){
			EgovMap map = (EgovMap)list.get(0);
			double t00 = Double.parseDouble((map.get("t00")).toString());
			double t01 = Double.parseDouble((map.get("t01")).toString());
			double t02 = Double.parseDouble((map.get("t02")).toString());
			double t03 = Double.parseDouble((map.get("t03")).toString());
			double t04 = Double.parseDouble((map.get("t04")).toString());
			double t05 = Double.parseDouble((map.get("t05")).toString());
			double t06 = Double.parseDouble((map.get("t06")).toString());			
			double t07 = Double.parseDouble((map.get("t07")).toString());
			double t08 = Double.parseDouble((map.get("t08")).toString());
			double t09 = Double.parseDouble((map.get("t09")).toString());
			double t10 = Double.parseDouble((map.get("t10")).toString());
			double t11 = Double.parseDouble((map.get("t11")).toString());
			double t12 = Double.parseDouble((map.get("t12")).toString());
			double t13 = Double.parseDouble((map.get("t13")).toString());			
			double t14 = Double.parseDouble((map.get("t14")).toString());
			double t15 = Double.parseDouble((map.get("t15")).toString());
			double t16 = Double.parseDouble((map.get("t16")).toString());
			double t17 = Double.parseDouble((map.get("t17")).toString());
			double t18 = Double.parseDouble((map.get("t18")).toString());
			double t19 = Double.parseDouble((map.get("t19")).toString());
			double t20 = Double.parseDouble((map.get("t20")).toString());
			double t21 = Double.parseDouble((map.get("t21")).toString());
			double t22 = Double.parseDouble((map.get("t22")).toString());
			double t23 = Double.parseDouble((map.get("t23")).toString());
			
			if(t00 > -999) resultCnt++;			if(t01 > -999) resultCnt++;			if(t02 > -999) resultCnt++;
			if(t03 > -999) resultCnt++;			if(t04 > -999) resultCnt++;			if(t05 > -999) resultCnt++;
			if(t06 > -999) resultCnt++; 		if(t07 > -999) resultCnt++;			if(t08 > -999) resultCnt++;			
			if(t09 > -999) resultCnt++;			if(t10 > -999) resultCnt++;			if(t11 > -999) resultCnt++;
			if(t12 > -999) resultCnt++;			if(t13 > -999) resultCnt++;			if(t14 > -999) resultCnt++;
			if(t15 > -999) resultCnt++;			if(t16 > -999) resultCnt++;			if(t17 > -999) resultCnt++;			
			if(t18 > -999) resultCnt++;			if(t19 > -999) resultCnt++;			if(t20 > -999) resultCnt++;
			if(t21 > -999) resultCnt++;			if(t22 > -999) resultCnt++;			if(t23 > -999) resultCnt++;
			
			//String[]colNames = new String[]{"구분","00시","01시","02시","03시","04시","05시","06시","07시","08시","09시","10시","11시","12시","13시","14시","15시","16시","17시","18시","19시","20시","21시","22시","23시","합계"};
			//String[]colIds = new String[]{"div","t00","t01","t02","t03","t04","t05","t06","t07","t08","t09","t10","t11","t12","t13","t14","t15","t16","t17","t18","t19","t20","t21","t22","t23","tot"};
			
			colNames = new String[resultCnt];			
			colIds   = new String[resultCnt];			
			colNames[0] = "구분";
			colIds[0] = "div";
			resultCnt = 1;
			if(t00 > -999){
				int num = resultCnt++;
				colNames[num] = "00시(명)";
				colIds[num] = "t00";
			}
			if(t01 > -999){
				int num = resultCnt++;
				colNames[num] = "01시(명)";
				colIds[num] = "t01";
			}
			if(t02 > -999){
				int num = resultCnt++;
				colNames[num] = "02시(명)";
				colIds[num] = "t02";
			}
			if(t03 > -999){
				int num = resultCnt++;
				colNames[num] = "03시(명)";
				colIds[num] = "t03";
			}
			if(t04 > -999){
				int num = resultCnt++;
				colNames[num] = "04시(명)";
				colIds[num] = "t04";
			}
			if(t05 > -999){
				int num = resultCnt++;
				colNames[num] = "05시(명)";
				colIds[num] = "t05";
			}
			if(t06 > -999){
				int num = resultCnt++;
				colNames[num] = "06시(명)";
				colIds[num] = "t06";
			}
			if(t07 > -999){
				int num = resultCnt++;
				colNames[num] = "07시(명)";
				colIds[num] = "t07";
			}
			if(t08 > -999){
				int num = resultCnt++;
				colNames[num] = "08시(명)";
				colIds[num] = "t08";
			}
			if(t09 > -999){
				int num = resultCnt++;
				colNames[num] = "09시(명)";
				colIds[num] = "t09";
			}
			if(t10 > -999){
				int num = resultCnt++;
				colNames[num] = "10시(명)";
				colIds[num] = "t10";
			}			
			if(t11 > -999){
				int num = resultCnt++;
				colNames[num] = "11시(명)";
				colIds[num] = "t11";
			}			
			if(t12 > -999){
				int num = resultCnt++;
				colNames[num] = "12시(명)";
				colIds[num] = "t12";
			}
			if(t13 > -999){
				int num = resultCnt++;
				colNames[num] = "13시(명)";
				colIds[num] = "t13";
			}
			if(t14 > -999){
				int num = resultCnt++;
				colNames[num] = "14시(명)";
				colIds[num] = "t14";
			}
			if(t15 > -999){
				int num = resultCnt++;
				colNames[num] = "15시(명)";
				colIds[num] = "t15";
			}
			if(t16 > -999){
				int num = resultCnt++;
				colNames[num] = "16시(명)";
				colIds[num] = "t16";
			}
			if(t17 > -999){
				int num = resultCnt++;
				colNames[num] = "17시(명)";
				colIds[num] = "t17";
			}
			if(t18 > -999){
				int num = resultCnt++;
				colNames[num] = "18시(명)";
				colIds[num] = "t18";
			}
			if(t19 > -999){
				int num = resultCnt++;
				colNames[num] = "19시(명)";
				colIds[num] = "t19";
			}
			if(t20 > -999){
				int num = resultCnt++;
				colNames[num] = "20시(명)";
				colIds[num] = "t20";
			}
			if(t21 > -999){
				int num = resultCnt++;
				colNames[num] = "21시(명)";
				colIds[num] = "t21";
			}
			if(t22 > -999){
				int num = resultCnt++;
				colNames[num] = "22시(명)";
				colIds[num] = "t22";
			}
			if(t23 > -999){
				int num = resultCnt++;
				colNames[num] = "23시(명)";
				colIds[num] = "t23";
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
