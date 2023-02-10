package kr.co.socsoft.gis.traffic.service;

import java.awt.image.BufferedImage;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsGisVO;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;


/**
 * 저장버스도입 요구분석 서비스 클래스
 * @author SOC SOFT
 *
 */
public interface BusAnalsService {
	/**
	 * 저장버스도입 요구분석 결과 리스트
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectBusList(TrafficAnalsVO busVO);
	
	/**
	 * 노인격자 화면 처리
	 * @param TrafficAnalsGisVO
	 * @return List
	 */
	List<?> selectBusAnals(TrafficAnalsGisVO blindVO);	
	
	/**
	 * 범례를 위한 min, max값 가져오기
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectBusLgdInfo(TrafficAnalsVO blindVO);
	
	/**
	 * 노인격자 이미지 생성
	 * @param TrafficAnalsGisVO
	 * @return List
	 */
	BufferedImage createBusImg(TrafficAnalsGisVO blindVO);
	
	/**
	 * create Excel info
	 */
	XSSFWorkbook createExcelInfo(ExcelVO excelVo, TrafficAnalsVO busVo);	
}
