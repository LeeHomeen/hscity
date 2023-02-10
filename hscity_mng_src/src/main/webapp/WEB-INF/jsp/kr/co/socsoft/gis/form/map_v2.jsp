<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" >
	<title>화성시  인구/매출 공간분석 시스템</title>
    <script type="text/javascript" src="/js/map/config/app.js"></script>
</head>
<body>
<div id="gis-main" class="active">
	<!-- 지도 표시 영역 -->
	<div id="gis-area">
		<div id="gis-map" class="custom-map"></div>	
	</div>
	<!-- //지도 표시 영역 -->
	
	<!--지도 툴 -->
	<div class="map-controller">
		<div class="map-type">
			<input type="button" id="btnRaod" title="로드맵" class="btn-roadmap"/>
		</div>

		<div class="zoom-wrap">
			<!-- 이미지 잘라놨으니, 적용하실때 쓰시면 됩니다. zoom-으로 시작되는 이미지-->
			<div class="gis_item"> 
				<img id="btnZoomIn" style="cursor:pointer;" src="/images/gis/zoom-plus.png" alt="확대" >
				<div style="visibility:hidden">
					<img id='imgZoombar' onclick="clickMapTool('zoom', '${flag}')" src="/images/gis/zoombar.png" alt="현재위치">
					<div id="toolSliser" style="position:absolute; top:24px; left:8px;"><img src="/images/gis/zoom-slider.png" alt="현재위치"></div>					
				</div>				
				<img id="btnZoomOut" style="cursor:pointer;" src="/images/gis/zoom-minus.png" alt="확대" >				
			</div>
		</div>

		<div class="tools-wrap">
			<ul class="tools">
				<li class="ctr-1st">
					<input type="button" id="btnMapClear" value="새로고침" class="reset"/>
					<label for="">새로고침</label> 
				</li>
				<li id="liMeasure" class="ctr-1st" >
					<input type="button" id="btnMeasure" value="측정하기" class="measure"/>
					<label for="" class="position1">측정하기</label> 
					<ul class="tool-measure tool-sub">
						<li id="liArea" class="ctr-2nd">
							<input type="button" id="btnArea" value="면적" class="area"/>
							<label for="">면적</label> 
						</li>
						<li id="liDist" class="ctr-2nd">
							<input type="button" id="btnLine" value="거리" class="distance"/>
							<label for="">거리</label> 
						</li>
					</ul>
				</li>
				<li id="liLayer" class="ctr-1st">
					<input type="button" id="btnLyResult" onclick="clickMapTool('layer', '${flag}')" value="분석결과 레이어" class="layer"/>
					<label for="">분석결과 레이어</label> 
				</li>
				<li class="ctr-1st">
					<input type="button" id="btnSave" crossorigin="Anonymous" value="화면저장" class="save"/>
					<label for="">화면저장</label> 
				</li>
			</ul>
		</div>	
	<!--//지도 툴 -->					
	</div>
	<!-- 하단:분석 결과 영역 -->
	<div id="gis-result">			
	</div>
	<!-- //하단:분석 결과 영역 -->	
</div>
	
<!--로드뷰 -->
<div id="roadmap">
</div>
<script>
var flag = '${flag}';
</script>
 </body>
</html>
