package kr.co.socsoft.gis.pop.vo;

/**
 * 인구/매출분석의 인구분석 공통 vo
 * @author SOC SOFT
 *
 */
public class PopCmmVO {
	private String MAXX; //MAXX
	private String MAXY; //MAXY
	private String MINX; //MINX
	private String MINY; //MINY
	
	private String CMAXX; //4326 MAXX
	private String CMAXY; //4326 MAXY
	private String CMINX; //4326 MINX
	private String CMINY; //4326 MINY
	
	private String RESOLUTION; //resolution	
	private String AREA;       //분석영역 (행정동코드, 다각형 polygon, 반경 polygon, 북마크영역)
	private String COORD;      //좌표체계 4326 900913 5719
	
	private String SCREENX;    //화면 width
	private String SCREENY;    //화면 height
	
	private String ZOOM;   //지도 줌레벨
	
	public String getZOOM(){
		return this.ZOOM;
	}
	public void setZOOM(String ZOOM){
		this.ZOOM = ZOOM;
	}
	
	public String getCOORD(){
		return this.COORD;
	}
	public void setCOORD(String COORD){
		this.COORD = COORD;
	}
	
	public String getSCREENX(){
		return this.SCREENX;
	}
	public void setSCREENX(String SCREENX){
		this.SCREENX = SCREENX;
	}
	
	public String getSCREENY(){
		return this.SCREENY;
	}
	public void setSCREENY(String SCREENY){
		this.SCREENY = SCREENY;
	}
	
	public String getMAXX(){
		return this.MAXX;
	}
	public void setMAXX(String MAXX){
		this.MAXX = MAXX;
	}
	
	public String getMAXY(){
		return this.MAXY;
	}
	public void setMAXY(String MAXY){
		this.MAXY = MAXY;
	}
	
	public String getMINX(){
		return this.MINX;
	}
	public void setMINX(String MINX){
		this.MINX = MINX;
	}
	
	public String getMINY(){
		return this.MINY;
	}
	public void setMINY(String MINY){
		this.MINY = MINY;
	}
	
	public String getRESOLUTION(){
		return this.RESOLUTION;
	}
	public void setRESOLUTION(String RESOLUTION){
		this.RESOLUTION = RESOLUTION;
	}
	
	public String getAREA(){
		return this.AREA;
	}
	public void setAREA(String AREA){
		this.AREA = AREA;
	}
	
	public String getCMAXX(){
		return this.CMAXX;
	}
	public void setCMAXX(String CMAXX){
		this.CMAXX = CMAXX;
	}
	
	public String getCMAXY(){
		return this.CMAXY;
	}
	public void setCMAXY(String CMAXY){
		this.CMAXY = CMAXY;
	}
	
	public String getCMINX(){
		return this.CMINX;
	}
	public void setCMINX(String CMINX){
		this.CMINX = CMINX;
	}
	
	public String getCMINY(){
		return this.CMINY;
	}
	public void setCMINY(String CMINY){
		this.CMINY = CMINY;
	}
}
