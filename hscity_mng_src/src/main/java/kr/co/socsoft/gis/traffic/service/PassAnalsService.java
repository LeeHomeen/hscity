package kr.co.socsoft.gis.traffic.service;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;


/**
 * 통행패턴 분석 서비스 클래스
 * @author SOC SOFT
 *
 */
public interface PassAnalsService {
	/**
	 * 통행패턴 분석 결과 리스트
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectPassList(TrafficAnalsVO passVO);	
	
	/**
	 * 정류장 정보 조회
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectPassStaList(TrafficAnalsVO passVO);
	
	/**
	 * create Excel info
	 */
	XSSFWorkbook createExcelInfo(ExcelVO excelVo, TrafficAnalsVO passVo);
}
