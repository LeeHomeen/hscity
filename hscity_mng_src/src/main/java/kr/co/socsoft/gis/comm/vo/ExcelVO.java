package kr.co.socsoft.gis.comm.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 엑셀 공통 서비스 vo
 * @author soc soft
 *
 */
public class ExcelVO {
	private String chart; //차트 이미지
	private String map;   //지도 이미지
	private String eid;   //식별 아이디
	private String pps;   //엑셍 사용목적 purpose
	private String fnm;   //엑셀export명칭	
	
	private String uid;   //저장 id	
	private String exparams; //jqgrid data	
	private String exparams1; //결과 2개인 화면용 임시
		
	public String getExparams(){
		String ex = this.exparams;
		if(ex != null){
			ex = ex.replaceAll("&quot;", "\"");
			ex = ex.replaceAll("&apos;", "\"");
			ex = ex.replaceAll("\"\\{", "\\{");
			ex = ex.replaceAll("\\}\"", "\\}");
			ex = ex.replaceAll("'", "\"");
		}
		return ex;
	}
	public void setExparams(String exparams){
		this.exparams = exparams;
	}
	
	public String getExparams1(){
		String ex = this.exparams1;
		if(ex != null){
			ex = ex.replaceAll("&quot;", "\"");
			ex = ex.replaceAll("&apos;", "\"");
			ex = ex.replaceAll("\"\\{", "\\{");
			ex = ex.replaceAll("\\}\"", "\\}");
			ex = ex.replaceAll("'", "\"");
		}
		return ex;
	}
	public void setExparams1(String exparams1){
		this.exparams1 = exparams1;
	}
	
	public String getUid(){
		return this.uid;
	}
	public void setUid(String uid){
		this.uid = uid;
	}
	
	public String getFnm(){
		try {
			if(this.fnm != null)
				URLEncoder.encode(this.fnm , "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return this.fnm;
	}
	public void setFnm(String fnm){
		this.fnm = fnm;
	}
	
	public String getPps(){
		try {
			if(this.pps != null)
				URLEncoder.encode(this.pps , "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return this.pps;
	}
	public void setPps(String pps){
		this.pps = pps;
	}
	
	public String getEid(){
		return this.eid;
	}
	public void setEid(String eid){
		this.eid = eid;
	}
	
	public String getMap(){
		return this.map;
	}
	public void setMap(String map){
		this.map = map;
	}
	
	public String getChart(){
		return this.chart;
	}
	public void setChart(String chart){
		this.chart = chart;
	}
}
