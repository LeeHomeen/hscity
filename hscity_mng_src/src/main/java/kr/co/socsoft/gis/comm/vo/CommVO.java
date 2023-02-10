package kr.co.socsoft.gis.comm.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 공통 서비스 vo
 * @author soc soft
 *
 */
public class CommVO {
	private String std;   //기준정보
	private String dongcd; //동코드
	private String coord;  //좌표체계
	
	private String userid; //사용자 id
	private String geom; //폴리곤 정보
	private String name; //명칭	
	private String seq;  //일련번호
	
	private String cd; //코드값
	
	private String tbl; //테이블명
	
	private String btype; //노선 타입
	
	public String getBtype(){
		try {
			if(this.btype != null)
				URLEncoder.encode(this.btype , "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return this.btype;		
	}
	public void setBtype(String btype){
		this.btype = btype;
	}
	
	public String getTbl(){
		return this.tbl;
	}
	public void setTbl(String tbl){
		this.tbl = tbl;
	}
	
	public String getCd(){
		return this.cd;
	}
	public void setCd(String cd){
		this.cd = cd;
	}
	
	public String getStd(){
		return this.std;
	}
	public void setStd(String std){
		this.std = std;
	}
	
	public String getSeq(){
		return this.seq;
	}
	public void setSeq(String seq){
		this.seq = seq;
	}
	
	public String getUserid(){
		return this.userid;
	}
	public void setUserid(String userid){
		this.userid = userid;
	}
	
	public String getGeom(){
		return this.geom;
	}
	public void setGeom(String geom){
		this.geom = geom;
	}
	
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public String getCoord(){
		return this.coord;
	}
	public void setCoord(String coord){
		this.coord = coord;
	}
	
	public String getDongcd(){
		return this.dongcd;
	}
	public void setDongcd(String dongcd){
		this.dongcd = dongcd;
	}	
}
