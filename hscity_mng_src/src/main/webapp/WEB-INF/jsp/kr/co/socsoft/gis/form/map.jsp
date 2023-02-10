<%@ page contentType="text/html;charset=UTF-8" %>

<div id="gis-main" class="active">
	<!-- 지도 표시 영역 -->
	<div id="gis-area">
		<div id="gis-map" class="custom-map"></div>	
	</div>
	<!-- //지도 표시 영역 -->
	
	<!--지도 툴 -->
	<div class="map-controller">
		<div class="map-type">
			<input type="button" id="btnRaod" onclick="clickMapTool('road','${flag}')" title="로드맵" class="btn-roadmap"/>
		</div>

		<div class="zoom-wrap">
			<!-- 이미지 잘라놨으니, 적용하실때 쓰시면 됩니다. zoom-으로 시작되는 이미지-->
			<div class="gis_item"> 
				<img onclick="clickMapTool('zoomout', '${flag}')" style="cursor:pointer;" src="/images/gis/zoom-plus.png" alt="확대" >
				<div>
					<img id='imgZoombar' onclick="clickMapTool('zoom', '${flag}')" src="/images/gis/zoombar.png" alt="현재위치">
					<div id="toolSliser" style="position:absolute; top:24px; left:8px;"><img src="/images/gis/zoom-slider.png" alt="현재위치"></div>					
				</div>				
				<img onclick="clickMapTool('zoomin', '${flag}')" style="cursor:pointer;" src="/images/gis/zoom-minus.png" alt="확대" >				
			</div>
		</div>

		<div class="tools-wrap">
			<ul class="tools">
				<li class="ctr-1st">
					<input type="button" id="btnMapClear" onclick="clickMapTool('clear', '${flag}')" value="새로고침" class="reset"/>
					<label for="">새로고침</label> 
				</li>
				<li id="liMeasure" class="ctr-1st" >
					<input type="button" onclick="clickMapTool('measure', '${flag}')" value="측정하기" class="measure"/>
					<label for="" class="position1">측정하기</label> 
					<ul class="tool-measure tool-sub">
						<li id="liArea" class="ctr-2nd">
							<input type="button" id="btnArea" onclick="clickMapTool('area', '${flag}')" value="면적" class="area"/>
							<label for="">면적</label> 
						</li>
						<li id="liDist" class="ctr-2nd">
							<input type="button" id="btnLine" onclick="clickMapTool('line', '${flag}')" value="거리" class="distance"/>
							<label for="">거리</label> 
						</li>
					</ul>
				</li>
				<li id="liLayer" class="ctr-1st">
					<input type="button" id="btnLyResult" onclick="clickMapTool('layer', '${flag}')" value="분석결과 레이어" class="layer"/>
					<label for="">분석결과 레이어</label> 
				</li>
				<li class="ctr-1st">
					<input type="button" id="btnSave" crossorigin="Anonymous" onclick="clickMapTool('save', '${flag}')" value="화면저장" class="save"/>
					<label for="">화면저장</label> 
				</li>
			</ul>
		</div>						
	</div>
	<!--//지도 툴 -->	

	<!-- 하단:분석 결과 영역 -->
	<div id="gis-result">			
	</div>
	<!-- //하단:분석 결과 영역 -->			
</div>

<!--로드뷰 -->
<div id="roadmap">
</div>

<!-- Adobe Flash 다운로드 안내 -->
  <div id="setupFlash">
      <div class="contents">
          <img src="/images/cmm/pop-gis-flash.png" alt="로드뷰 서비스를 이용하시려면 Adobe Flash Player 최신버전 설치를 권합니다. (설치완료 후, 새로고침 해야 적용됩니다.)"/>
          <a href="http://get.adobe.com/flashplayer/" target="_blank" class="btn-download">최신버전 다운로드</a>
      </div>
      <button class="btn-close" title="닫기">닫기</button>
  </div>

<!--//로드뷰 -->
<script type="text/javascript">
	$(document).ready(function(){
		var flag = '${flag}';
		if(flag == 'pop'){
			popMap = new HsctMap('${flag}');
			popMap.initHsctMap('gis-map', null);
			popMap.mapTools.changeBaseMap('VWST', true);
			popMap.map.isValidZoomLevel =  function(zoomLevel) {
				var valid = zoomLevel;
				if(popMap.chkNMap){ //nvmap
					if(zoomLevel < 1) valid = 0;
				}else //vwmap
					if(zoomLevel < 6) valid = 0;				
				return valid;
			};
		}else if(flag == 'traffic'){
			trafficMap = new HsctMap('${flag}');
			trafficMap.initHsctMap('gis-map', null);
			trafficMap.mapTools.changeBaseMap('VWST', true);
			trafficMap.map.isValidZoomLevel =  function(zoomLevel) {
				var valid = zoomLevel;
				if(trafficMap.chkNMap){ //nvmap
					if(zoomLevel < 1) valid = 0;
				}else //vwmap
					if(zoomLevel < 6) valid = 0;				
				return valid;
			};
		}
		
		$('#imgZoombar').click(function(evt){
			clickZoombar(evt, '${flag}');
		});
		
		$( "#toolSliser" ).draggable({ 
			axis: "y",
			cursor : "move",
			drag : function(event, ui){
				var top = ui.position.top;
				if(top <= 24)
					ui.position.top = 24;   //limit min
				else if(top >= 140)
					ui.position.top = 140;  //limit max				
			},
			stop : function(event, ui){				
				var zoom = (Number(ui.position.top)/10).toFixed(0);				
				var mapObj = getMapObj('${flag}');
				var maxZoom = (mapObj.map.getNumZoomLevels()+1); //v:19, n:14
				if(mapObj.chkNMap){ //naver
					zoom = Number(zoom) - 1;
					maxZoom -= 1;
				}else{ //vworld
					zoom = Number(zoom) + 4;
					maxZoom += 4;
				}				
				mapObj.map.zoomTo(maxZoom - zoom);	
			}
		});
	});
	
	 //윈도우 리사이즈시 지도 업데이트	
	 $(window).resize(function() {
		 var mapObj = getMapObj('${flag}');
		 mapObj.mapRefresh();		
	 });
</script>
