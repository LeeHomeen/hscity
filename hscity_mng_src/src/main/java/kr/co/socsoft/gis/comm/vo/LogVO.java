package kr.co.socsoft.gis.comm.vo;

/**
 * 엑셀 저장 log 서비스 vo
 * @author soc soft
 *
 */
public class LogVO {
	private String gubun;  //구분
	private String uid;    //사용자 아이디
	private String downdt; //다운로드일자
	private String usepps; //활용목적
	private String sec;    //보안동의
	
	public String getGubun(){
		return this.gubun;
	}
	public void setGubun(String gubun){
		this.gubun = gubun;
	}
	
	public String getUid(){
		return this.uid;
	}
	public void setUid(String uid){
		this.uid = uid;
	}
	
	public String getDowndt(){
		return this.downdt;
	}
	public void setDowndt(String downdt){
		this.downdt = downdt;
	}
	
	public String getUsepps(){
		return this.usepps;
	}
	public void setUsepps(String usepps){
		this.usepps = usepps;
	}
	
	public String getSec(){
		return this.sec;
	}
	public void setSec(String sec){
		this.sec = sec;
	}
}
