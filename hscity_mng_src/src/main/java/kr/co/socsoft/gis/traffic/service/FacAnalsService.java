package kr.co.socsoft.gis.traffic.service;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;


/**
 * 환승편의시설분석 서비스 클래스
 * @author SOC SOFT
 *
 */
public interface FacAnalsService {
	/**
	 * 환승편의시설 분석 결과 리스트
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectFacList(TrafficAnalsVO facVO);	
	
	/**
	 * create Excel info
	 */
	XSSFWorkbook createExcelInfo(ExcelVO excelVo, TrafficAnalsVO facVo);
}
