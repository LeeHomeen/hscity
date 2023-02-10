package kr.co.socsoft.gis.pop.vo;

/**
 * 인구/매출분석의 인구분석 공통 vo
 * @author SOC SOFT
 *
 */
public class PopAnalsVO  {	
	private String query;  //동적쿼리 생성
	private String dongcd; //동코드	
	private String syear;  //start 기준년
	private String eyear;  //end 기준년
	private String gubun;  //읍면동 기준정보
	
	private String atype; //분석영역 타입 (행정동 : area, 사용자 지정영역 : user)
	private String vtype; //heat, grid, beehive
	private String area;  //분석영역 (행정동코드, 다각형 polygon, 반경 polygon, 북마크영역)
	private String month; //동적 조회월
	
	private String smonth; //기준년월
	private String period; //month, year
	private String std;    //past, cur
	
	//전입전출 범위
	private String range; //관내, 관외등
	private String coord;  //분석지도의 좌표체계 (벡터분석용)
	
	//성연령별
	private String gender;  //남여 성별 체크 (쿼리 복잡해결)
	private String mgender; //성별 구분 (남성)
	private String wgender; //성별 구분 (여성)
	private String sage;   //시작 연령
	private String eage;   //종료 연령
	private String smember; //시작 세대원수
	private String emember; //종료 세대원수
	
	//유동인구 추가	
	private String emonth; //기준년월_end
	private String sday; //기준년일_start	
	private String eday; //기준년일_end
	private String time; //시간대별
	
	//범례
	private String gyear;
	private String age;
	
	//동적 테이블 생성쿼리
	private String dytbl;
	
	
	public String getDytbl(){
		return this.dytbl;
	}
	public void setDytbl(String dytbl){
		this.dytbl = dytbl;
	}
	
	public String getTime(){
		return this.time;
	}
	public void setTime(String time){
		this.time = time;
	}
	
	public String getEmonth(){
		return this.emonth;
	}
	public void setEmonth(String emonth){
		this.emonth = emonth;
	}
	
	public String getSday(){
		return this.sday;
	}
	public void setSday(String sday){
		this.sday = sday;
	}
	
	public String getEday(){
		return this.eday;
	}
	public void setEday(String eday){
		this.eday = eday;
	}
	
	public String getAge(){
		return this.age;
	}
	public void setAge(String age){
		this.age = age;
	}
	
	public String getVtype(){
		return this.vtype;
	}
	public void setVtype(String vtype){
		this.vtype = vtype;
	}
	
	public String getGyear(){
		return this.gyear;
	}
	public void setGyear(String gyear){
		this.gyear = gyear;
	}
	
	public String getGender(){
		return this.gender;
	}
	public void setGender(String gender){
		this.gender = gender;
	}
	
	public String getMgender(){
		return this.mgender;
	}
	public void setMgender(String mgender){
		this.mgender = mgender;
	}
	
	public String getWgender(){
		return this.wgender;
	}
	public void setWgender(String wgender){
		this.wgender = wgender;
	}
	
	public String getSage(){
		return this.sage;
	}
	public void setSage(String sage){
		this.sage = sage;
	}
	
	public String getEage(){
		return this.eage;
	}
	public void setEage(String eage){
		this.eage = eage;
	}
	
	public String getSmember(){
		return this.smember;
	}
	public void setSmember(String smember){
		this.smember = smember;
	}
	
	public String getEmember(){
		return this.emember;
	}
	public void setEmember(String emember){
		this.emember = emember;
	}
	
	public String getGubun(){
		return this.gubun;
	}
	public void setGubun(String gubun){
		this.gubun = gubun;
	}
	
	public String getPeriod(){
		return this.period;
	}
	public void setPeriod(String period){
		this.period = period;
	}
	
	public String getStd(){
		return this.std;
	}
	public void setStd(String std){
		this.std = std;
	}
	
	public String getQuery(){
		return this.query;
	}
	public void setQuery(String query){
		this.query = query;
	}
	
	public String getCoord(){
		return this.coord;			
	}
	public void setCoord(String coord){
		this.coord = coord;
	}
	
	public String getRange(){
		return this.range;
	}
	public void setRange(String range){
		this.range = range;
	}
	
	public String getSmonth(){
		return this.smonth;
	}
	public void setSmonth(String smonth){
		this.smonth = smonth;
	}
	
	public String getAtype(){
		return this.atype;
	}
	public void setAtype(String atype){
		this.atype = atype;
	}
	
	public String getMonth(){
		return this.month;
	}
	public void setMonth(String month){
		this.month = month;
	}
	
	public String getArea(){
		return this.area;
	}
	public void setArea(String area){
		this.area = area;
	}
	
	public String getSyear(){
		return this.syear;
	}
	public void setSyear(String syear){
		this.syear = syear;
	}
	
	public String getEyear(){
		return this.eyear;
	}
	public void setEyear(String eyear){
		this.eyear = eyear;
	}
	
	public String getDongcd(){
		return this.dongcd;
	}
	public void setDongcd(String dongcd){
		this.dongcd = dongcd;
	}
}
