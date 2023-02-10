<%@ page contentType="text/html;charset=UTF-8" %>   
<div class="tab tab-st1">
	<ul class="tabs">
		<li id="t1-3-1" class="active">
			<a id="li1-3-1" href="#" class="btn-tab">시도별</a>
			<div class="sec-tab result-cont">
				<p class="lment">* SKT 유동인구 데이터 사용</p>
				<!-- 분석결과 내용-->
				<div id="frmChart1-3-1" class="col-box wp70"> <!-- [d] wp 클래스로 넓이를 조절할 수 있습니다. wp30~100까지 -->
					<div id="divChart1-3-1"></div>
				</div>
				<!-- //분석결과 내용-->

				<!-- 분석결과 내용-->
				<div id="divGrid1-3-1" class="col-box wp30">
					<table class="tbl_list" id="tblGrid1-3-1"></table>
					<div id="gridPaging1-3-1"></div>											
				</div>
				<!-- 분석결과 내용-->
			</div>
		</li>
		<li id="t1-3-2">
			<a id="li1-3-2" href="#" class="btn-tab">시군구별</a>
			<div class="sec-tab result-cont">
				<p class="lment">* SKT 유동인구 데이터 사용</p>
				<!-- 분석결과 내용-->
				<div id="frmChart1-3-2" class="col-box wp70"> <!-- [d] wp 클래스로 넓이를 조절할 수 있습니다. wp30~100까지 -->
					<div id="divChart1-3-2"></div>
				</div>
				<!-- //분석결과 내용-->

				<!-- 분석결과 내용-->
				<div id="divGrid1-3-2" class="col-box wp30">
					<table class="tbl_list" id="tblGrid1-3-2"></table>
					<div id="gridPaging1-3-2"></div>											
				</div>
				<!-- 분석결과 내용-->
			</div>
		</li>
		<li id="t1-3-3">
			<a id="li1-3-3" href="#" class="btn-tab">성연령별</a>
			<div class="sec-tab result-cont">
				<p class="lment">* SKT 유동인구 데이터 사용</p>
				<!-- 분석결과 내용-->
				<div id="frmChart1-3-3" class="col-box wp50"> <!-- [d] wp 클래스로 넓이를 조절할 수 있습니다. wp30~100까지 -->
					<div id="divChart1-3-3"></div>
				</div>
				<!-- //분석결과 내용-->

				<!-- 분석결과 내용-->
				<div id="divGrid1-3-3" class="col-box wp50">
					<table class="tbl_list" id="tblGrid1-3-3"></table>
					<div id="gridPaging1-3-3"></div>											
				</div>
				<!-- 분석결과 내용-->
			</div>
		</li>
	</ul>
	<!-- 결과창 상단:버튼 영역-->
	<ul class="result-btn-wrap">
		<li>									
			<button onclick="openExcelPopup(true)" class="btn sr1 st1 ico-exc">Excel</button>
		</li>
		<!-- 펼치기/접기 버튼 -->
		<li>
			<button id="btnFold1-3" class="btn-fold on">접기</button>
		</li>
		<!-- //펼치기/접기 버튼 -->
	</ul>
</div>
<!-- //tab -->
<script type="text/javascript" src="/js/cmm/gis_result.js"></script>
<script type="text/javascript" src="/js/gis/pop/iPopAnals.js"></script>
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
		initIPopPageInfo('${flag}','${menu}');
		
		$('#t1-3-1').hide();
		$('#t1-3-2').hide();
		$('#t1-3-3').hide();
	});	
</script> 