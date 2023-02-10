package kr.co.socsoft.gis.comm.service.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import kr.co.socsoft.gis.comm.service.CommDao;
import kr.co.socsoft.gis.comm.service.CommService;
import kr.co.socsoft.gis.comm.vo.CommVO;
import kr.co.socsoft.gis.comm.vo.LogVO;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * 공통 서비스 구현 SERVICE
 * @author Administrator
 *
 */
@Service("commService")
public class CommServiceImpl implements CommService{
	
	@Resource(name = "commDao")
	private CommDao dao;
	
	/**
	 * 읍/면/동 기준정보 조회
	 * @param CommVO
	 * @return
	 */
	@Override
	public List<?> selectStdDong(CommVO commVo){
		return dao.selectStdDong(commVo);
	}
	
	/**
	 * 읍/면/동 조회 
	 */
	@Override
	public List<?> selectDongList(CommVO commVo){
		return dao.selectDongList(commVo);
	}
	@Override
	public List<?> selectCstDongList(CommVO commVo){
		//법정동
		return dao.selectCstDongList(commVo);
	}
	
	/**
	 * 조회 읍면동 awk 가져오기	 
	 */
	@Override
	public List<?> selectDongWkt(CommVO commVo){
		//dong 변환
		String[] arrDong = commVo.getDongcd().trim().split(",");
		String dongcd = "";
		for(int i=0; i<arrDong.length; i++){
			dongcd += "'" + arrDong[i] + "'";
			if(i < arrDong.length -1)
				dongcd += ",";
		}
		commVo.setDongcd(dongcd);
		return dao.selectDongWkt(commVo);
	}
	
	//법정동
	@Override
	public List<?> selectCstDongWkt(CommVO commVo){
		//dong 변환
		String[] arrDong = commVo.getDongcd().trim().split(",");
		String dongcd = "";
		for(int i=0; i<arrDong.length; i++){
			dongcd += "'" + arrDong[i] + "'";
			if(i < arrDong.length -1)
				dongcd += ",";
		}
		commVo.setDongcd(dongcd);
		return dao.selectCstDongWkt(commVo);
	}
	
	/**
	 * 분석영역 저장정보 조회
	 */
	@Override
	public List<?> selectBookmarkList(CommVO commVO){
		return dao.selectBookmarkList(commVO);
	}
	
	/**
	 * 분석영역 저장
	 */
	@Override
	public int insertBookmark(CommVO commVO){
		return dao.insertBookmark(commVO);
	}
	
	/**
	 * 분석영역 수정
	 */
	@Override
	public int updateBookmark(CommVO commVO){
		return dao.updateBookmark(commVO);
	}
	
	/**
	 * 분석영역 삭제
	 */
	@Override
	public int deleteBookmark(CommVO commVO){
		return dao.deleteBookmark(commVO);
	}
	
	/**
	 * 업종분류 대분류
	 */
	@Override
	public List<?> selectLclasList(CommVO commVO){
		return dao.selectLclasList(commVO);
	}
	
	/**
	 * 업종분류 중분류
	 */
	@Override
	public List<?> selectMclasList(CommVO commVO){
		return dao.selectMclasList(commVO);
	}
	
	/**
	 * 업종분류 소분류
	 */
	@Override
	public List<?> selectSclasList(CommVO commVO){
		return dao.selectSclasList(commVO);
	}
	
	/**
	 * 버스 노선타입 정보 가져오기	  
	 */
	@Override
	public List<?> selectBusType(CommVO commVO){
		return dao.selectBusType(commVO);
	}
	
	/**
	 * 버스 노선 정보	  
	 */
	@Override
	public List<?> selectBusLine(CommVO commVO){
		return dao.selectBusLine(commVO);
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////// 공통 util /////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * 동적 테이블 생성 쿼리 생성
	 */
	@Override
	public String createDynamicTable(String tbl, int start, int end){
		String query = "";
		query += "select "; 
		query += "'(' || replace(array_to_string( array_agg(tbl), ',' ), ',', ' union all ') || ')' as tbl ";
		query += "from(";
		query += "select "; 
		for(int i=start; i<=end; i++){			
			if(i == end){
				query += "case when unit = '"+i+"' then 'select * from ' || (table_id  || '_' || unit ) else null" ;
			}else{
				query += "case when unit = '"+i+"' then 'select * from ' || (table_id  || '_' || unit ) else" ;
			}
		}
		for(int i=start; i<=end; i++){
			query += "end ";
		}
		query += " as tbl";
		query += "from sp_hscity_get_table_split_info('"+tbl+"') ";
		query += ")part1 ";
		query += "where tbl is not null ";
		
		return query;
	}
	
	/**
	 * 테이블 정보 가져오기 
	 */
	@Override
	public List<?> selectTableInfo(CommVO commVO){
		return dao.selectTableInfo(commVO);
	}
	
	/**
	 * 테이블 기갼 정보  가져오기 
	 */
	public List<?> selectTblPeriod(CommVO commVO){
		//테이블 기간조회
		String flag = commVO.getCd();
    	String tblInfo = "";
    	if(flag.trim().equals("1-1-1"))
    		tblInfo = "hsp_rsgst_popltn_p";
    	else if(flag.trim().equals("1-1-2"))
    		tblInfo = "hsp_rsgst_popltn_p";
    	else if(flag.trim().equals("1-1-3"))
    		tblInfo = "hsp_mvinnm_p";
    	else if(flag.trim().equals("1-1-4"))
    		tblInfo = "hsp_mvotnm_p";
    	else if(flag.trim().equals("1-2-1"))
    		tblInfo = "skt_hs_flow_pop_age_p";
    	else if(flag.trim().equals("1-2-2"))
    		tblInfo = "skt_hs_flow_pop_age_p";
    	else if(flag.trim().equals("1-2-3"))
    		tblInfo = "skt_hs_flow_pop_time_p";
    	else if(flag.trim().equals("1-3-1"))
    		tblInfo = "skt_hs_inflow_age";
    	else if(flag.trim().equals("1-3-2"))
    		tblInfo = "skt_hs_inflow_age";
    	else if(flag.trim().equals("1-3-3"))
    		tblInfo = "skt_hs_inflow_age";
    	else if(flag.trim().equals("1-4-1"))
    		tblInfo = "sh_card_hs_amt_demo";
    	else if(flag.trim().equals("1-4-2"))
    		tblInfo = "sh_card_hs_amt_demo";
    	else if(flag.trim().equals("1-4-3"))
    		tblInfo = "sh_card_hs_amt_time";
    	else if(flag.trim().equals("1-4-4"))
    		tblInfo = "sh_card_hs_amt_demo";
    	else if(flag.trim().equals("1-4-5"))
    		tblInfo = "sh_card_hs_foreign_f";
    	else if(flag.trim().equals("1-5-1"))
    		tblInfo = "sh_card_hs_amt_from";
    	else if(flag.trim().equals("1-5-2"))
    		tblInfo = "sh_card_hs_amt_from";
    	else if(flag.trim().equals("1-5-3"))
    		tblInfo = "sh_card_hs_amt_from";
    	
    	commVO.setTbl(tblInfo);
		return dao.selectTblPeriod(commVO);
	}
	
	/**
	 * 기준년월 최근 데이터 가져오기  
	 */
	@Override
	public List<?> selectRecentPeriod(CommVO commVO){
		String flag = commVO.getCd();
    	String tblInfo = "";
    	String year = commVO.getStd();
    	if(year.length() == 5) {
    		year = year.substring(0, 4);
    		
    		if(flag.trim().equals("1-1-1"))
    			tblInfo = "hsp_rsgst_popltn_p";
    		else if(flag.trim().equals("1-1-2"))
    			tblInfo = "hsp_rsgst_popltn_p";
    		else if(flag.trim().equals("1-1-3"))
    			tblInfo = "hsp_mvinnm_p";
    		else if(flag.trim().equals("1-1-4"))
    			tblInfo = "hsp_mvotnm_p";
    		else if(flag.trim().equals("1-4-1"))
    			tblInfo = "sh_card_hs_amt_demo";
    		else if(flag.trim().equals("1-4-2"))
    			tblInfo = "sh_card_hs_amt_demo";
    		else if(flag.trim().equals("1-4-3"))
    			tblInfo = "sh_card_hs_amt_time";
    		else if(flag.trim().equals("1-4-4"))
    			tblInfo = "sh_card_hs_amt_demo";
    		else if(flag.trim().equals("1-4-5"))
    			tblInfo = "sh_card_hs_foreign_f";
    		else if(flag.trim().equals("1-5-1"))
    			tblInfo = "sh_card_hs_amt_from";
    		else if(flag.trim().equals("1-5-2"))
    			tblInfo = "sh_card_hs_amt_from";
    		else if(flag.trim().equals("1-5-3"))
    			tblInfo = "sh_card_hs_amt_from";
    		
    		flag = flag.substring(0, 3);
    		commVO.setCd(flag);
    	
    	}else if(year.length() > 5) {
    		year = year.substring(0, 4).concat(year.substring(5, 7));
    		
    		if(flag.trim().equals("1-2-1"))
    			tblInfo = "skt_hs_flow_pop_age_p";
    		else if(flag.trim().equals("1-2-2"))
    			tblInfo = "skt_hs_flow_pop_age_p";
    		else if(flag.trim().equals("1-2-3"))
    			tblInfo = "skt_hs_flow_pop_time_p";
    		else if(flag.trim().equals("1-3-1"))
    			tblInfo = "skt_hs_inflow_age";
    		else if(flag.trim().equals("1-3-2"))
    			tblInfo = "skt_hs_inflow_age";
    		else if(flag.trim().equals("1-3-3"))
    			tblInfo = "skt_hs_inflow_age";
    		
    	}
    	tblInfo = tblInfo + "_" + year;
    	
    	commVO.setTbl(tblInfo);
		return dao.selectRecentPeriod(commVO);
	}
	/**
	 * 구간 가져오기
	 */
	@Override
	public int getImgGrade(long max, int min, long val){		
		int grade = 1;
		int color = 12;
		int section = color-2; //구간 처음과 끝 제거
		//int div = max / section; //최소값이 무조건 0일 경우
		long div = (max - min) / section;
		
		if(val > 0){
			for(int i=1; i<=section; i++){
				//if(val <= ( (div) * i)){ //기존 최소값이 0일경우					
				if(val <= ( min + ((div) * i))){ //최소값, 최대값 있는 경우 구간값 계산
					grade = (i);
					break;
				}else{
					if(val >= max){
						grade = section;
						break;
					}
				}
			}
			//grade += 1; //0이 아니면 1부터 시작
		}
		return grade;
	}
	
	/**
	 * 외곽선 정보 가져오기 
	 */
	@Override
	public Color getSrokeColor(){
		return new Color(93, 93, 93, 50);
	}
	
	/**
	 * COLOR 가져오기
	 */
	@Override
	public Color getImgColor(String chk, int grade, int alpha){
		Color color = new Color(255, 255, 255, alpha); //rgb alpha 255            
		if(chk.trim().equals("blue")){
			if(grade == 0)
				color = new Color(255, 255, 255, alpha); //rgb alpha 255
			else if(grade == 1)
				color = new Color(198, 249, 255, alpha); //rgb alpha 255
			else if(grade == 2)
				color = new Color(162, 213, 255, alpha); //rgb alpha 255
			else if(grade == 3)
				color = new Color(126, 177, 255, alpha); //rgb alpha 255
			else if(grade == 4)
				color = new Color(90, 141, 243, alpha); //rgb alpha 255
			else if(grade == 5)
				color = new Color(54, 105, 207, alpha); //rgb alpha 255
			else if(grade == 6)
				color = new Color(18, 69, 171, alpha); //rgb alpha 255
			else if(grade == 7)
				color = new Color(0, 33, 135, alpha); //rgb alpha 255
			else if(grade == 8)
				color = new Color(0, 0, 99, alpha); //rgb alpha 255
			else if(grade == 9)
				color = new Color(0, 0, 81, alpha); //rgb alpha 255
			else if(grade == 10)
				color = new Color(0, 0, 63, alpha); //rgb alpha 255
			else if(grade == 11)
				color = new Color(0, 0, 0, alpha); //rgb alpha 255
			
		}else if(chk.trim().equals("green")){
			if(grade == 0)
				color = new Color(255, 255, 255, alpha); //rgb alpha 255
			else if(grade == 1)
				color = new Color(232, 255, 226, alpha); //rgb alpha 255
			else if(grade == 2)
				color = new Color(196, 255, 190, alpha); //rgb alpha 255
			else if(grade == 3)
				color = new Color(160, 242, 154, alpha); //rgb alpha 255
			else if(grade == 4)
				color = new Color(124, 206, 118, alpha); //rgb alpha 255
			else if(grade == 5)
				color = new Color(88, 170, 82, alpha); //rgb alpha 255
			else if(grade == 6)
				color = new Color(52, 134, 46, alpha); //rgb alpha 255
			else if(grade == 7)
				color = new Color(16, 98, 10, alpha); //rgb alpha 255
			else if(grade == 8)
				color = new Color(0, 62, 0, alpha); //rgb alpha 255
			else if(grade == 9)
				color = new Color(0, 26, 0, alpha); //rgb alpha 255
			else if(grade == 10)
				color = new Color(0, 8, 0, alpha); //rgb alpha 255
			else if(grade == 11)
				color = new Color(0, 0, 0, alpha); //rgb alpha 255
		}else{
			if(grade == 0)
				color = new Color(255, 255, 255, alpha); //rgb alpha 255
			else if(grade == 1)
				color = new Color(255, 198, 198, alpha); //rgb alpha 255
			else if(grade == 2)
				color = new Color(255, 162, 162, alpha); //rgb alpha 255
			else if(grade == 3)
				color = new Color(255, 126, 126, alpha); //rgb alpha 255
			else if(grade == 4)
				color = new Color(255, 90, 90, alpha); //rgb alpha 255
			else if(grade == 5)
				color = new Color(255, 54, 54, alpha); //rgb alpha 255
			else if(grade == 6)
				color = new Color(255, 18, 18, alpha); //rgb alpha 255
			else if(grade == 7)
				color = new Color(237, 0, 0, alpha); //rgb alpha 255
			else if(grade == 8)
				color = new Color(201, 0, 0, alpha); //rgb alpha 255
			else if(grade == 9)
				color = new Color(165, 0, 0, alpha); //rgb alpha 255
			else if(grade == 10)
				color = new Color(129, 0, 0, alpha); //rgb alpha 255
			else if(grade == 11)
				color = new Color(0, 0, 0, alpha); //rgb alpha 255
		}
		return color;
	}
	
	/**
	 * 여러동 쿼리 용이한 동코드로 변환
	 * @param dongcd
	 * @return
	 */
	@Override
	public String changeCommaInfo(String items){
		String[] arrItem = items.trim().split(",");
		String tmpItem = "";
		for(int i=0; i<arrItem.length; i++){
			tmpItem += "'" + arrItem[i] + "'";
			if(i < arrItem.length -1)
				tmpItem += ",";
		}
		return tmpItem;
	}	
	
	
	/**
	 * 문자열로 된 이미지정보를 bufferedImage로 변환
	 */
	@SuppressWarnings("finally")
	@Override
	public BufferedImage getStringImg(String strImg){
		InputStream inst = null;
		BufferedImage bufferedImage = null;
		try {
			String tmpImg = strImg.substring(22);
			byte[] imgByteArray = Base64.decodeBase64(tmpImg.getBytes());
			inst = new ByteArrayInputStream(imgByteArray);
			 bufferedImage = ImageIO.read(inst);
		}catch(Exception e){
			
		}finally{
			if(inst != null){
				try {
					inst.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return bufferedImage;
		}
	}
	
	/**
	 * 엑셀 내보내기 
	 * params 컬럼한글명, 컬럼명, 결과리스트, 맵이미지, 차트이미지
	 */
	@SuppressWarnings("deprecation")
	@Override
	public XSSFWorkbook createExcel(List<String[]> colNames, List<String[]> colIds, String title, List<List<?>> list, 
			BufferedImage mapImg, BufferedImage chartImg){
		//export 시작
		XSSFWorkbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상
		//sheet생성
		XSSFSheet sheet = xlsxWb.createSheet(title);
		//이미지 맵 row
		addExcelPicture(xlsxWb, sheet, mapImg, 550, 370, 0, 0);
		
		int colNum = 0; //grid정보 시작위치
		if(chartImg != null){
			addExcelPicture(xlsxWb, sheet, chartImg, 350, 200, 20, 0);
			colNum = 8;
		}
		//이미지 맵 end
        
		int rowNum = 20;
			
		//컴럼한글명 설정
		XSSFRow xrow = sheet.createRow(rowNum);
		XSSFCellStyle colStyle = xlsxWb.createCellStyle();
		colStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(255, 255, 255)));		
		colStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);		
		colStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		colStyle.setBorderBottom(BorderStyle.MEDIUM);
		colStyle.setBorderTop(BorderStyle.MEDIUM);
		
		for(int i=0; i<colNames.size(); i++){
			String[] names = colNames.get(i);
			for(int j=0; j<names.length; j++){
				XSSFCell cell = xrow.createCell((colNum+j));
				cell.setCellStyle(colStyle);				
				cell.setCellValue(names[j]);
				//sheet.autoSizeColumn((colNum+j), true); //컬럼 자동 width맞춤
			}				
			colNum += (names.length + 1);
		}
		
		colNum = 0; //차트이미지가 있는 경우
		if(chartImg != null){
			colNum = 8; //차트가 없는 경우
		}		
		//리스트 결과설정
		XSSFCellStyle itemStyle = xlsxWb.createCellStyle();
		itemStyle.setBorderTop(BorderStyle.THIN);
		itemStyle.setBorderBottom(BorderStyle.THIN);
		 
		for(int i=0; i<list.size(); i++){
			List<?> result = list.get(i);		
			int colCnt = 0;
			
			for(int rcnt=0; rcnt<result.size(); rcnt++){
				EgovMap map = (EgovMap)result.get(rcnt);
				XSSFRow row = sheet.getRow(rcnt+(rowNum + 1));
				if( row == null)
					row = sheet.createRow(rcnt+(rowNum + 1));					
				
				for(int j=0; j<colIds.size(); j++){
					String[] ids = colIds.get(j);
					for(int cnt=0; cnt<ids.length; cnt++){
						colCnt = colIds.size();
						XSSFCell cell = row.createCell((colNum+cnt));	
						String label = "";
						/*
						if(ids[cnt].trim().equals("div") || 
							ids[cnt].trim().equals("item") ||
							ids[cnt].trim().equals("age") ||
							ids[cnt].trim().equals("gb") || 
							ids[cnt].trim().equals("lnm") ||
							ids[cnt].trim().equals("mnm") || 
							ids[cnt].trim().equals("snm") ||
							ids[cnt].trim().equals("emdcd")){
							label = (String)map.get(ids[cnt]);
							cell.setCellValue(label);
						}else{
							//천단위 숫자,
							label = (map.get(ids[cnt])).toString();
							cell.setCellValue(label);
						}
						*/
						if(map.get(ids[cnt]) != null)
							label = (map.get(ids[cnt])).toString();
						else
							label = "";
						cell.setCellValue(label);
						cell.setCellStyle(itemStyle);
						//sheet.autoSizeColumn((colNum+cnt), true); //컬럼 자동 width맞춤
					}					
				}
			}
			colNum += (colCnt + 1);
			colCnt = 0;
		}
		//리스트 결과 엑셀추가 완료		
		return xlsxWb;		
	}
	
	/**
	 * 엑셀에 이미지 추가
	 * @param workbook 엑셀 workbook
	 * @param sheet 엑셀 sheet
	 * @param bufferedImage 원본 이미지
	 * @param width  넓이
	 * @param height 높이
	 * @param row row위치
	 * @param col col위치
	 */
	@SuppressWarnings("rawtypes")
	private void addExcelPicture(XSSFWorkbook workbook, XSSFSheet sheet,
			BufferedImage bufferedImage, int width, int height, int row, int col){
		//start		
		Image resizeImage = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH); //SCALE_AREA_AVERAGING, SCALE_REPLICATE, SCALE_FAST, SCALE_SMOOTH
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = newImage.getGraphics();
		g.drawImage(resizeImage, 0, 0, null);
		g.dispose();
		
		//mapRow사이즈 조정
	    ByteArrayOutputStream baps = new ByteArrayOutputStream();
	    try {
			ImageIO.write(newImage,"png",baps);
			
			int pictureIdx =workbook.addPicture(baps.toByteArray(), Workbook.PICTURE_TYPE_PNG);
		    CreationHelper helper = workbook.getCreationHelper();	    
	        Drawing drawing = sheet.createDrawingPatriarch();
	        ClientAnchor anchor = helper.createClientAnchor();
	        anchor.setDx1(10 * XSSFShape.EMU_PER_PIXEL);
	        anchor.setDx2(10 * XSSFShape.EMU_PER_PIXEL);
	        anchor.setDy1(10 * XSSFShape.EMU_PER_PIXEL);
	        anchor.setDy2(10 * XSSFShape.EMU_PER_PIXEL);

	        anchor.setRow1(row); //row위치
	        anchor.setCol1(col); //col위치

	        Picture pict = drawing.createPicture(anchor, pictureIdx);
	        pict.resize();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * excel download log save
	 */
	@SuppressWarnings("finally")
	public boolean insertExlLog(LogVO logVo){		
		boolean result = false;
		logVo.setSec("동의");
		try{
			dao.insertExlLog(logVo);
			result = true;
		}catch(Exception e){
			result = false;
		}finally{
			return result;
		}
	}
}
