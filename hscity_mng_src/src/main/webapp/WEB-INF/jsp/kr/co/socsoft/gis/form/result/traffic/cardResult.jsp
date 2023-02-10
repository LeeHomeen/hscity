<%@ page contentType="text/html;charset=UTF-8" %>   
<div class="tab tab-st1">
	<ul class="tabs">
		<li id="t2-3-1" id="liTraffic2-3-1" class="active">
			<a id="li2-3-1" href="#" class="btn-tab">정류장별 승하차 정보</a>
			<div class="sec-tab result-cont">				
				<!-- 분석결과 내용-->
				<div id="frmChart2-3-1" class="col-box wp60"> <!-- [d] wp 클래스로 넓이를 조절할 수 있습니다. wp30~100까지 -->
					<div id="divChart2-3-1"></div>
				</div>
				<!-- //분석결과 내용-->

				<!-- 분석결과 내용-->
				<div id="divGrid2-3-1" class="col-box wp40">
					<table class="tbl_list" id="tblGrid2-3-1"></table>
					<div id="gridPaging2-3-1"></div>			
				</div>
				<!-- 분석결과 내용-->
			</div>
		</li>
		<!-- 
		<li id="t2-3-2">
			<a id="li2-3-2" href="#" class="btn-tab">연령대별 승차정보</a>
			<div class="sec-tab result-cont">
				<div id="frmChart2-3-2" class="col-box wp70"> 
					<div id="divChart2-3-2"></div>
				</div>
				<div id="divGrid2-3-2" class="col-box wp30">
					<table class="tbl_list" id="tblGrid2-3-2"></table>
					<div id="gridPaging2-3-2"></div>			
				</div>
			</div>
		</li>
		 -->		
	</ul>
	<!-- 결과창 상단:버튼 영역-->
	<ul class="result-btn-wrap">
		<li>									
			<button onclick="openExcelPopup(true)" class="btn sr1 st1 ico-exc">Excel</button>
		</li>
		<!-- 펼치기/접기 버튼 -->
		<li>
			<button id="btnFold2-3" class="btn-fold on">접기</button>
		</li>
		<!-- //펼치기/접기 버튼 -->
	</ul>
</div>
<!-- //tab -->
<script type="text/javascript" src="/js/cmm/gis_result.js"></script>
<script type="text/javascript" src="/js/gis/traffic/cardAnals.js"></script>
<!-- chart -->
<script type="text/javascript" src="/js/lib/jqplot1.0.8/plugins/jqplot.canvasTextRenderer.min.js"></script>
<script type="text/javascript" src="/js/lib/jqplot1.0.8/plugins/jqplot.canvasAxisLabelRenderer.min.js"></script>
<script type="text/javascript" src="/js/lib/jqplot1.0.8/plugins/jqplot.canvasAxisTickRenderer.min.js"></script>
<script type="text/javascript" src="/js/lib/jqplot1.0.8/plugins/jqplot.dateAxisRenderer.min.js"></script>
<script type="text/javascript" src="/js/lib/jqplot1.0.8/plugins/jqplot.categoryAxisRenderer.min.js"></script>
<script type="text/javascript" src="/js/lib/jqplot1.0.8/plugins/jqplot.barRenderer.min.js"></script>
<script type="text/javascript" src="/js/lib/jqplot1.0.8/jqplot.toImage.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		initCardPageInfo('${flag}', '${menu}');

		$('#t2-3-1').hide();
	});
</script>