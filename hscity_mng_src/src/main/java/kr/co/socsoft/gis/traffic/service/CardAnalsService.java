package kr.co.socsoft.gis.traffic.service;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;


/**
 * 교통카드 현황분석 서비스 클래스
 * @author SOC SOFT
 *
 */
public interface CardAnalsService {
	/**
	 * 교통카드 현황분석 결과 리스트
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectCardList(TrafficAnalsVO cardVO);	
	
	/**
	 * create Excel info
	 */
	XSSFWorkbook createExcelInfo(ExcelVO excelVo, TrafficAnalsVO busVo);
}
