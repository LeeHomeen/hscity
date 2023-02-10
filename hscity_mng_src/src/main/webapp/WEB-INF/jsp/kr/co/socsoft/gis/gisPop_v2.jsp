<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ko">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge" >
   <title>화성시  인구/매출 공간분석 시스템</title>
   
   <link rel="stylesheet" type="text/css" href="/css/gis_base.css" />
   <link rel="stylesheet" type="text/css" href="/css/gis.css" />
   <link rel="stylesheet" type="text/css" href="/css/gis_custom.css" />
   <link rel="stylesheet" type="text/css" href="/css/gis.ui.jqgrid.css" />
   <link rel="stylesheet" type="text/css" href="/css/ol.css" />
   <link rel="stylesheet" type="text/css" href="/css/style.css" />
   <link rel="stylesheet" type="text/css" href="/css/ref/jquery-ui.css" />
   <link rel="stylesheet" type="text/css" href="/css/ref/ui.jqgrid.css" />
   <link rel="stylesheet" type="text/css" href="/css/ref/jquery.jqplot.css" />
   <link rel="stylesheet" type="text/css" href="/css/ref/datepicker3.css" />
   <link rel="stylesheet" type="text/css" href="/css/ref/colorpicker.css"/>
   
   <script type="text/javascript" src="/js/lib/jquery/jquery-1.11.0.min.js"></script> 
   <script type="text/javascript" src="/js/lib/jquery/jquery-ui.js"></script>
   <script type="text/javascript" src="/js/cmm/sidebar-menu.js"> </script>
   <!-- multiselect -->
   <script type="text/javascript" src="/js/gis/comm/multiselect_v2.js"> </script>
   <!-- jqgrid -->
   <script type="text/javascript" src="/js/lib/jqgrid4.7/jquery.jqGrid.min.js"></script>
   <script type="text/javascript" src="/js/lib/jqgrid4.7/grid.locale-kr.js"></script>
   <!-- jqplot -->  
   <script type="text/javascript" src="/js/lib/jqplot1.0.8/jquery.jqplot.min.js"></script>   
   <!-- datepicker -->
   <script type="text/javascript" src="/js/lib/datepicker/datepicker.js"></script>
   <script type="text/javascript" src="/js/lib/datepicker/datepicker.kr.js"></script>
    <!-- ref js -->
    <script type="text/javascript" src="/js/lib/html2canvas/html2canvas.min.js"></script>
    <script type="text/javascript" src="/js/lib/html2canvas/promise-6.1.0.js"></script>        
    <!-- ref JS -->    
    <script type="text/javascript" src="/js/gis/comm/map.js"></script>
    <script type="text/javascript" src="/js/gis/comm/traffic.js"></script>
    <script type="text/javascript" src="/js/gis/comm/storage.js"></script>
    <script type="text/javascript" src="/js/gis/comm/common_v2.js"></script>
    <script type="text/javascript" src="/js/gis/comm/grid_chart_v2.js"></script>
    <script type="text/javascript" src="/js/gis/comm/excel.js"></script>
    <script type="text/javascript" src="/js/gis/map/event_v2.js"></script>    
    <!-- color picker -->
    <script type="text/javascript" src="/js/lib/colorpicker/colorpicker.js"></script>
    <script type="text/javascript" src="/js/lib/colorpicker/eye.js"></script>
    <script type="text/javascript" src="/js/lib/colorpicker/utils.js"></script>    
    
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=37a6c6ecaaf7cc99b08ac16c38977321"></script>
    <script src="/js/map/openlayers/ol.js"></script>
    <script src="/js/map/config/config.js"></script>
    <script src="/js/map/utils/proj4.js"></script>
    <script src="/js/map/map.js"></script>
    <script src="/js/map/layer/vworld-layer.js"></script>
    <script src="/js/map/layer/kakao-layer.js"></script>
    <script src="/js/map/interaction/roadview-interaction.js"></script>
    <script src="/js/map/interaction/length-interaction.js"></script>
    <script src="/js/map/interaction/area-interaction.js"></script>
    <script src="/js/map/utils/FileSaver.min.js"></script>
 </head>
 <body>
	<div id="wrap">		
		<!-- header -->
		<div id="header">			
		</div>
		<!--// header -->

		<div id="gis-body">
			<!--대메뉴 -->			
			<div id="gis-gnb">				
			</div>
			<!--//대메뉴 -->

			<!-- 왼쪽:설정 영역 -->
			<div id="gis-lnb">
				
			</div>
			<!-- //왼쪽:설정 영역 -->

			<!-- 오른쪽:지도 영역 -->			
			<div id="gis-container">
			</div>
			<!-- //오른쪽:지도 영역 -->		

		</div>
	</div>
	
	<!-- poupu -->
	<div id="gis-popup"></div>	
	
	<script type="text/javascript">
		$(document).ready(function(){			
			searchJspPage("/gis/map_v2.do", {flag:'pop'}, false, "gis-container"); //content
			searchJspPage("/gis/menu_v2.do", {flag:'pop'}, false, "gis-gnb"); //gisPopup side menu 호출			
			searchJspPage("/gis/search_v2.do", {flag:"pop", menu:"pop"}, false, "gis-lnb"); //gisPopup side search 호출
			searchJspPage("/gis/result_v2.do", {flag:"pop", menu:"pop"}, false, "gis-result");
			searchJspPage("/gis/top_v2.do", {flag:'pop'}, false, "header"); //gisPopup top 호출
			searchJspPage("/gis/popup_v2.do", {flag:'pop'}, false, "gis-popup"); //gisPopup top 호출
			
			setContHeight();
		});
		
		//그리드 리사이즈
		$(window).resize(function() {			
			updateMap('pop','win','0');
			setContHeight();
		});
		
		//현재 화면상의 map객체 가져오기
		function getMapObj(mapType){
			if(mapType == 'pop')
				return popMap;
			else if(mapType == 'traffic')
				return trafficMap;
			else 
				return null;
		}
	</script>  	
 </body>
</html>
