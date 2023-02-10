package kr.co.socsoft.gis.pop.vo;

/**
 * 인구/매출분석의 인구분석 공통 GIS vo
 * @author SOC SOFT
 *
 */
public class PopAnalsGisVO extends PopCmmVO {	
	private String MENU;   //현재 메뉴
	private String AID;    //분석 id
	private String DONGCD; //동 코드정보
	private String VTYPE;      //시간화 타입 (heat, grid, beehive)
	private String ATYPE;      //분석영역 타입 (행정동 : area, 사용자 지정영역 : user)
	private String VCOLOR;     //빨 녹 파
	
	private String SMONTH;     //시작 기준년월
	private String EMONTH;     //종료 기준년월
	
	private String SDAY;       //시작 기준년월일
	private String EDAY;       //종료 기준년월일
	
	private String GENDER;     //성별 (남 여)
	private String MGENDER;    //성별 남 체크
	private String WGENDER;    //성별 여 체크
	private String SAGE;       //시작연령
	private String EAGE;       //종료연령
	
	private String SMEMBER;    //시작 세대원수
	private String EMEMBER;    //종료 세대원수
	
	private String SYEAR;       //START 기준년
	private String EYEAR;       //END 기준년
	private String GYEAR;       //결과 GRID의 기간정보 (시계열)
	
	private String PERIOD;     //기간
	private String STD;        //연령계산 기준년도 선택 : past, cur (기준년월 기준, 현재년도 기준) 또는 기준년월, 기준년일 정보
	
	private String GUBUN;      //읍면동 조건 구분
	
	private String TIME;       //시간대별 분석의 시간
	private String AGE;        //연령별 분석의 연령
	
	private String MAX;        //범례 최대값
	private String MIN;        //범례 최소값
	
	private String DYTBL;      //동적 테이블 생성 쿼리
	
	public String getDYTBL(){
		return this.DYTBL;
	}
	public void setDYTBL(String DYTBL){
		this.DYTBL = DYTBL;
	}
	
	public String getMIN(){
		return this.MIN;
	}
	public void setMIN(String MIN){
		this.MIN = MIN;
	}
	
	public String getMAX(){
		return this.MAX;
	}
	public void setMAX(String MAX){
		this.MAX = MAX;
	}
	
	public String getMGENDER(){
		return this.MGENDER;
	}
	public void setMGENDER(String MGENDER){
		this.MGENDER = MGENDER;
	}
	
	public String getWGENDER(){
		return this.WGENDER;
	}
	public void setWGENDER(String WGENDER){
		this.WGENDER = WGENDER;
	}
	
	public String getMENU(){
		return this.MENU;
	}
	public void setMENU(String MENU){
		this.MENU = MENU;
	}
	
	public String getAGE(){
		return this.AGE;
	}
	public void setAGE(String AGE){
		this.AGE = AGE;
	}
	
	public String getTIME(){
		return this.TIME;
	}
	public void setTIME(String TIME){
		this.TIME = TIME;
	}
	
	public String getGUBUN(){
		return this.GUBUN;
	}
	public void setGUBUN(String GUBUN){
		this.GUBUN = GUBUN;
	}
		
	public String getGYEAR(){
		return this.GYEAR;
	}
	public void setGYEAR(String GYEAR){
		this.GYEAR = GYEAR;
	}
	public String getSYEAR(){
		return this.SYEAR;
	}
	public void setSYEAR(String SYEAR){
		this.SYEAR = SYEAR;
	}
	public String getEYEAR(){
		return this.EYEAR;
	}
	public void setEYEAR(String EYEAR){
		this.EYEAR = EYEAR;
	}
	
	public String getSMEMBER(){
		return this.SMEMBER;
	}
	public void setSMEMBER(String SMEMBER){
		this.SMEMBER = SMEMBER;
	}
	
	public String getEMEMBER(){
		return this.EMEMBER;
	}
	public void setEMEMBER(String EMEMBER){
		this.EMEMBER = EMEMBER;
	}
	
	public String getSTD(){
		return this.STD;
	}
	public void setSTD(String STD){
		this.STD = STD;
	}
	
	public String getAID(){
		return this.AID;
	}
	public void setAID(String AID){
		this.AID = AID;
	}
	
	public String getPERIOD(){
		return this.PERIOD;
	}
	public void setPERIOD(String PERIOD){
		this.PERIOD = PERIOD;
	}
	
	public String getGENDER(){
		return this.GENDER;
	}
	public void setGENDER(String GENDER){
		this.GENDER = GENDER;
	}
	
	public String getSAGE(){
		return this.SAGE;
	}
	public void setSAGE(String SAGE){
		this.SAGE = SAGE;
	}
	
	public String getEAGE(){
		return this.EAGE;
	}
	public void setEAGE(String EAGE){
		this.EAGE = EAGE;
	}
	
	public String getSDAY(){
		return this.SDAY;
	}
	public void setSDAY(String SDAY){
		this.SDAY = SDAY;
	}
	
	public String getEDAY(){
		return this.EDAY;
	}
	public void setEDAY(String EDAY){
		this.EDAY = EDAY;
	}	
	
	public String getDONGCD(){
		return this.DONGCD;
	}
	public void setDONGCD(String DONGCD){
		this.DONGCD = DONGCD;
	}
	
	public String getVTYPE(){
		return this.VTYPE;
	}
	public void setVTYPE(String VTYPE){
		this.VTYPE = VTYPE;
	}
	
	public String getATYPE(){
		return this.ATYPE;
	}
	public void setATYPE(String ATYPE){
		this.ATYPE = ATYPE;
	}
	
	public String getVCOLOR(){
		return this.VCOLOR;
	}
	public void setVCOLOR(String VCOLOR){
		this.VCOLOR = VCOLOR;
	}
	
	public String getSMONTH(){
		return this.SMONTH;
	}
	public void setSMONTH(String SMONTH){
		this.SMONTH = SMONTH;
	}
	
	public String getEMONTH(){
		return this.EMONTH;
	}
	public void setEMONTH(String EMONTH){
		this.EMONTH = EMONTH;
	}
}
