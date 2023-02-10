package kr.co.socsoft.gis.traffic.vo;

import kr.co.socsoft.gis.pop.vo.PopCmmVO;

/**
 * 교통분석 공통 GIS vo
 * @author SOC SOFT
 *
 */
public class TrafficAnalsGisVO extends PopCmmVO {
	private String AID;    //분석 id
	private String DONGCD; //동 코드정보
	private String VCOLOR; //색상체계정보
	
	private String MAX; //최대값
	private String MIN; //최소값
	
	public String getMAX(){
		return this.MAX;
	}
	public void setMAX(String MAX){
		this.MAX = MAX;
	}
	public String getMIN(){
		return this.MIN;
	}
	public void setMIN(String MIN){
		this.MIN = MIN;
	}
	
	public String getAID(){
		return this.AID;
	}
	public void setAID(String AID){
		this.AID = AID;
	}	
	
	public String getDONGCD(){
		return this.DONGCD;
	}
	public void setDONGCD(String DONGCD){
		this.DONGCD = DONGCD;
	}
	
	public String getVCOLOR(){
		return this.VCOLOR;
	}
	public void setVCOLOR(String VCOLOR){
		this.VCOLOR = VCOLOR;
	}
}
