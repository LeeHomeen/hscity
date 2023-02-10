package kr.co.socsoft.gis.traffic.service;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;


/**
 * 탄력배치분석 서비스 클래스
 * @author SOC SOFT
 *
 */
public interface ArrAnalsService {
	/**
	 * 요일별 승차인원
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectArrWeekList(TrafficAnalsVO facVO);	
	
	/**
	 * 청두 미청두
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectArrTimeList(TrafficAnalsVO facVO);
	
	/**
	 * create Excel info
	 */
	XSSFWorkbook createExcelInfo(ExcelVO excelVo, TrafficAnalsVO busVo);
}
