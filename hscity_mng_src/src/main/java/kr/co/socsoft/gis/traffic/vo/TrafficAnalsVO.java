package kr.co.socsoft.gis.traffic.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 교통  분석 공통 vo
 * @author SOC SOFT
 *
 */
public class TrafficAnalsVO  {
	private String coord; //좌표체계정보
	private String bus; //버스노선 정보
	private String lid; //버스노선 id
	private String sid; //정류장 id
	private String title; //타이틀
	private String ptype; //패턴통행 검색조건
	
	public String getSid(){
		return this.sid;
	}
	public void setSid(String sid){
		this.sid = sid;
	}
	
	public String getPtype(){
		try {
			if(this.ptype != null)
				URLEncoder.encode(this.ptype , "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return this.ptype;	
	}
	public void setPtype(String ptype){
		this.ptype = ptype;
	}
	
	public String getTitle(){
		try {
			if(this.title != null)
				URLEncoder.encode(this.title , "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return this.title;	
	}
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getLid(){
		return this.lid;
	}
	public void setLid(String lid){
		this.lid = lid;
	}
	
	public String getBus(){
		return this.bus;
	}
	public void setBus(String bus){
		this.bus = bus;
	}
	
	public String getCoord(){
		return this.coord;
	}
	public void setCoord(String coord){
		this.coord = coord;
	}
}
