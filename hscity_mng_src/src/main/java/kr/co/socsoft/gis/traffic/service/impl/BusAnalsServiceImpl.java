package kr.co.socsoft.gis.traffic.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

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
import kr.co.socsoft.gis.comm.service.CommService;
import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.comm.vo.LogVO;
import kr.co.socsoft.gis.traffic.service.BusAnalsDao;
import kr.co.socsoft.gis.traffic.service.BusAnalsService;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsGisVO;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;


/**
 * 저상버스 도입요구분석 서비스  SERVICE
 * @author SOC SOFT
 *
 */
@Service("busAnalsService")
public class BusAnalsServiceImpl implements BusAnalsService{

	@Resource(name = "commService")
	private CommService commService;
	
	@Resource(name = "busAnalsDao")
	private BusAnalsDao dao;	
	
	/**
	 * 저장버스도입 요구분석 결과 리스트
	 * @param TrafficAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectBusList(TrafficAnalsVO busVO){
		busVO.setBus(commService.changeCommaInfo(busVO.getBus()));
		return dao.selectBusList(busVO);
	}
	
	/**
	 * 노인격자 화면 처리
	 * @param TrafficAnalsGisVO
	 * @return List
	 */
	@Override
	public List<?> selectBusAnals(TrafficAnalsGisVO busVO){
		return dao.selectBusAnals(busVO);
	}
	
	/**
	 * 범례를 위한 min, max값 가져오기
	 * @param TrafficAnalsVO
	 * @return List
	 */
	@Override
	public List<?> selectBusLgdInfo(TrafficAnalsVO busVO){
		return dao.selectBusLgdInfo(busVO);
	}
	
	/**
	 * 노인격자 이미지 생성
	 */
	@Override
	public BufferedImage createBusImg(TrafficAnalsGisVO busVO){
		String x = busVO.getSCREENX();
    	String y = busVO.getSCREENY();
    	int coord = Integer.parseInt(busVO.getCOORD());
    	int zoom = Integer.parseInt(busVO.getZOOM());
    	if(coord == 5179)zoom += 6;
    	
    	BufferedImage image = null;
    	if(x != null && y != null){
    		image = new BufferedImage(Integer.parseInt(busVO.getSCREENX()), Integer.parseInt(busVO.getSCREENY()), BufferedImage.TYPE_INT_ARGB);
    		Graphics2D g = image.createGraphics();
    		if(zoom > 12){
    			List<?> list = selectBusAnals(busVO);
        		
        		if(list.size() > 0){
            		WKTReader wktReader = new WKTReader();
            		int max = 0;
            		int min = 0;
            		for(int i=0; i<list.size(); i++){
            			EgovMap map = (EgovMap)list.get(i);
            			if(i == 0){
            				//최소값 , 최대값 세팅
            				if(map.get("cnt") != null){
            					max = (int)Double.parseDouble(map.get("cnt").toString());	
            				}        				
            				if(map.get("min") != null){
            					min = (int)Double.parseDouble(map.get("min").toString());
            				}
            			}else{
            				//전체 표시
                			Polygon poly = null;
                			try {
                				if(map.get("wkt") != null)
                					poly = (Polygon)wktReader.read((String)map.get("wkt"));
        					} catch (ParseException e) {
        						e.printStackTrace();
        					}
                			if(poly != null){
                				int cnt = 0;        			
                    			if(map.get("cnt") != null){
                    				cnt = (int)Double.parseDouble(map.get("cnt").toString());
                    			}
                    			int grade = commService.getImgGrade(max, min, cnt); 
                    			Color col = commService.getImgColor(busVO.getVCOLOR(), grade, 150);
                    			ShapeWriter sw = new ShapeWriter();
                    			Shape polyShape = sw.toShape(poly);            			            			
                    			g.setColor(col);
                    			g.fill(polyShape);
                    			g.setColor(commService.getSrokeColor()); //경계라인
                    			g.draw(polyShape);
                    			
                    			//label
                    			Rectangle rec = polyShape.getBounds();
                    			if(grade > 6)
                    				col= new Color(255, 255, 255, 200);
                    			else
                    				col= new Color(76, 76, 76, 200);
                    			g.setColor(col);
                    			
                    			if(zoom > 14){
                    				g.setFont(new Font("TimesRoman", Font.PLAIN, zoom-4)); 
                    				g.drawString(cnt+"", (int)rec.x+6, (int)rec.getCenterY());
                    			}
                			}	
            			}
            		}
        		}	
    		}    		
    	}    	
    	return image;
	}
	
	/**
	 * create Excel info
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public XSSFWorkbook createExcelInfo(ExcelVO excelVo, TrafficAnalsVO busVo){
		String id = excelVo.getEid();
		//활용목적 저장
		String title = "";
		if(id.trim().equals("2-2-1"))
			title = "저상버스 도입요구 분석";
		
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
				
				if(id.trim().equals("2-2-1")){
					//인구분석_기간별 분석				
					String[] colNames = new String[]{"버스노선","노인인구(명)","유동인구(명)","시청/구청(개)","병원(개)","보건소(개)","복지시설(개)","노인인구 백분위(%)","시설 백분위(%)","백분위 합계(%)"};
					String[] colIds = new String[]{"div","opop","fpop","cityhall","hospital","medical","woffice","old100","fac100","tot"};
					/* db에서 get
					busVo.setBus(busVo.getBus().replace("%2C", ","));
					List<?> result = selectBusList(busVo);
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
