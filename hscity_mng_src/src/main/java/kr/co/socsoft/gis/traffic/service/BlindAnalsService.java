package kr.co.socsoft.gis.traffic.service;

import java.awt.image.BufferedImage;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.socsoft.gis.comm.vo.ExcelVO;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsGisVO;
import kr.co.socsoft.gis.traffic.vo.TrafficAnalsVO;


/**
 * 사각지대 분석 서비스 클래스
 * @author SOC SOFT
 *
 */
public interface BlindAnalsService {
	/**
	 * 사각지대 분석 결과 리스트
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectBlindList(TrafficAnalsVO blindVO);
	
	/**
	 * 사각지대 화면 처리
	 * @param TrafficAnalsGisVO
	 * @return List
	 */
	List<?> selectBlindAnals(TrafficAnalsGisVO blindVO);
	
	/**
	 * 사각지대 이미지 생성
	 * @param TrafficAnalsGisVO
	 * @return List
	 */
	BufferedImage createBlindImg(TrafficAnalsGisVO blindVO);
	
	/**
	 * 범례를 위한 min, max값 가져오기
	 * @param TrafficAnalsVO
	 * @return List
	 */
	List<?> selectBlindLgdInfo(TrafficAnalsVO blindVO);
	
	/**
	 * create Excel info
	 */
	XSSFWorkbook createExcelInfo(ExcelVO excelVo, TrafficAnalsVO busVo);
}
