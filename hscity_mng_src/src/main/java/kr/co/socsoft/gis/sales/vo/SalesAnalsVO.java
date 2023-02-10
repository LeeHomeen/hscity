package kr.co.socsoft.gis.sales.vo;

/**
 * 매출분석 공통 vo
 * @author SOC SOFT
 *
 */
public class SalesAnalsVO  {	
	private String query;  //동적쿼리 생성
	private String dongcd; //동코드	
	private String syear;  //start 기준년
	private String eyear;  //end 기준년
	private String gubun;  //읍면동 기준정보
	
	private String atype; //분석영역 타입 (행정동 : area, 사용자 지정영역 : user)
	private String area;  //분석영역 (행정동코드, 다각형 polygon, 반경 polygon, 북마크영역)
	private String month; //동적 조회월
	
	private String smonth; //기준년월_start
	private String emonth; //기준년월_end
	private String period; //month, year
	private String std;    //past, cur
	
	private String sday; //기준년일_start	
	private String eday; //기준년일_end
	
	//전입전출 범위
	private String range; //관내, 관외등
	private String coord;  //분석지도의 좌표체계 (벡터분석용)
	
	//개별 조회 파라미터 
	private String time; //시간대별 00,06,12~ 
	private String age;  //성연령별
	private String ctg;  //업종구분 코드
	
	//범례
	private String gyear;
	private String gender;
	
	//동적 테이블 생성쿼리
	private String dytbl;
	
	
	public String getDytbl(){
		return this.dytbl;
	}
	public void setDytbl(String dytbl){
		this.dytbl = dytbl;
	}
	
	public String getGender(){
		return this.gender;
	}
	public void setGender(String gender){
		this.gender = gender;
	}
	
	public String getGyear(){
		return this.gyear;
	}
	public void setGyear(String gyear){
		this.gyear = gyear;
	}
	
	public String getCtg(){
		return this.ctg;
	}
	public void setCtg(String ctg){
		this.ctg = ctg;
	}
	
	public String getTime(){
		return this.time;
	}
	public void setTime(String time){
		this.time = time;
	}

	public String getAge(){
		return this.age;
	}
	public void setAge(String age){
		this.age = age;
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
	
	public String getEmonth(){
		return this.emonth;
	}
	public void setEmonth(String emonth){
		this.emonth = emonth;
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
