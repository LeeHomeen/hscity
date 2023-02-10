package kr.co.socsoft.gis.spatial.vo;

public class SpatialVO {
	private String lon; //경도
	private String lat; //위도
	private String radius; //반경정보 m
	
	public String getLon(){
		return this.lon;
	}
	public void setLon(String lon){
		this.lon = lon;
	}
	
	public String getLat(){
		return this.lat;
	}
	public void setLat(String lat){
		this.lat = lat;
	}
	
	public String getRadius(){
		return this.radius;
	}
	public void setRadius(String radius){
		this.radius = radius;
	}
}
