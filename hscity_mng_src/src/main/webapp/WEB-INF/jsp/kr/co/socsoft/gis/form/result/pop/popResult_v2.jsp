<%@ page contentType="text/html;charset=UTF-8" %>

<div class="tab tab-st1">
	<ul class="tabs">
		<li id="t1-1-1" class="active">
			<a id="li1-1-1" href="#" class="btn-tab">기간별</a>
			<div class="sec-tab result-cont">
				<p id="plbl1-1-1" class="ment"></p>
				<!-- 분석결과 내용-->
				<div id="frmChart1-1-1" class="col-box wp50"> <!-- [d] wp 클래스로 넓이를 조절할 수 있습니다. wp30~100까지 -->
					<div id="divChart1-1-1"></div>
				</div>
				<!-- //분석결과 내용-->

				<!-- 분석결과 내용-->
				<div id="divGrid1-1-1" class="col-box wp50">
					<table class="tbl_list" id="tblGrid1-1-1"></table>
					<div id="gridPaging1-1-1"></div>			
				</div>
				<!-- 분석결과 내용-->
			</div>
		</li>
		<li id="t1-1-2">
			<a id="li1-1-2" href="#" class="btn-tab">성&#47;연령별</a>
			<div class="sec-tab result-cont">
				<p id="plbl1-1-2" class="ment"></p>
				<!-- 분석결과 내용-->
				<div id="frmChart1-1-2" class="col-box wp60"> <!-- [d] wp 클래스로 넓이를 조절할 수 있습니다. wp30~100까지 -->
					<div id="divChart1-1-2"></div>
				</div>
				<!-- //분석결과 내용-->

				<!-- 분석결과 내용-->
				<div align="center" id="divGrid1-1-2" class="col-box wp40">
					<table class="tbl_list" id="tblGrid1-1-2"></table>
					<div id="gridPaging1-1-2"></div>											
				</div>
				<!-- 분석결과 내용-->
			</div>
		</li>
		<li id="t1-1-3">
			<a id="li1-1-3" href="#" class="btn-tab">전입 분석</a>
			<div class="sec-tab result-cont">
				<!-- 분석결과 내용-->
				<div id="frmChart1-1-3" class="col-box wp55"> <!-- [d] wp 클래스로 넓이를 조절할 수 있습니다. wp30~100까지 -->
					<div id="divChart1-1-3"></div>
				</div>
				<!-- //분석결과 내용-->

				<!-- 분석결과 내용-->
				<div id="divGrid1-1-3-1" class="col-box wp25">
					<table class="tbl_list" id="tblGrid1-1-3-1"></table>
					<div id="gridPaging1-1-3-1"></div>											
				</div>
				<div id="divGrid1-1-3-2" class="col-box wp20">
					<table class="tbl_list" id="tblGrid1-1-3-2"></table>
					<div id="gridPaging1-1-3-2"></div>											
				</div>
				<!-- 분석결과 내용-->
			</div>
		</li>
		<li id="t1-1-4">
			<a id="li1-1-4" href="#" class="btn-tab">전출 분석</a>
			<div class="sec-tab result-cont">
				<!-- 분석결과 내용-->
				<div id="frmChart1-1-4" class="col-box wp55"> <!-- [d] wp 클래스로 넓이를 조절할 수 있습니다. wp30~100까지 -->
					<div id="divChart1-1-4"></div>
				</div>
				<!-- //분석결과 내용-->

				<!-- 분석결과 내용-->
				<div id="divGrid1-1-4-1" class="col-box wp25">
					<table class="tbl_list" id="tblGrid1-1-4-1"></table>
					<div id="gridPaging1-1-4-1"></div>											
				</div>
				<div id="divGrid1-1-4-2" class="col-box wp20">
					<table class="tbl_list" id="tblGrid1-1-4-2"></table>
					<div id="gridPaging1-1-4-2"></div>											
				</div>
				<!-- 분석결과 내용-->
			</div>
		</li>
	</ul>
	<!-- 결과창 상단:버튼 영역-->
	<ul class="result-btn-wrap">
		<li>						
			<span id="spanTsLabel1-1"></span>			
			<button id="btnTsStart1-1" style="display: none;" onclick="tsAnalsResult('${flag}', '1-1')" class="btn sr1 st1">시계열분석</button>
			<button id="btnTsStop1-1" style="display: none;"  onclick="tsPopStop('${flag}', '1-1')" class="btn sr1 st1">시계열중지</button>
		</li>
		<li>									
			<button onclick="openExcelPopup(true)" class="btn sr1 st1 ico-exc">Excel</button>
		</li>
		<!-- 펼치기/접기 버튼 -->
		<li>
			<button id="btnFold1-1" class="btn-fold on">접기</button>
		</li>
		<!-- //펼치기/접기 버튼 -->
	</ul>
</div>
<!-- //tab -->
<script type="text/javascript" src="/js/cmm/gis_result.js"></script>
<script type="text/javascript" src="/js/gis/pop/popAnals_v2.js"></script>
<!-- chart -->
<script type="text/javascript" src="/js/lib/jqplot1.0.8/plugins/jqplot.canvasTextRenderer.min.js"></script>
<script type="text/javascript" src="/js/lib/jqplot1.0.8/plugins/jqplot.canvasAxisLabelRenderer.min.js"></script>
<script type="text/javascript" src="/js/lib/jqplot1.0.8/plugins/jqplot.canvasAxisTickRenderer.min.js"></script>
<script type="text/javascript" src="/js/lib/jqplot1.0.8/plugins/jqplot.dateAxisRenderer.min.js"></script>
<script type="text/javascript" src="/js/lib/jqplot1.0.8/plugins/jqplot.categoryAxisRenderer.min.js"></script>
<script type="text/javascript" src="/js/lib/jqplot1.0.8/plugins/jqplot.barRenderer.min.js"></script>
<script type="text/javascript" src="/js/lib/jqplot1.0.8/jqplot.toImage.js"></script>
<script type="text/javascript">
	//왼쪽 메뉴 RESIZE시 MAP UPDATE
	$(document).ready(function(){
		initPopPageInfo('${flag}','${menu}');
		//시계열 버튼 VIEW
		$('#li1-1-1').click(function(){openTsBtn(true, false, '1-1-1');});
		$('#li1-1-2').click(function(){openTsBtn(true, false, '1-1-2');});
		$('#li1-1-3').click(function(){openTsBtn(false, false, '1-1-3');});
		$('#li1-1-4').click(function(){openTsBtn(false, false, '1-1-4');});
		
		$('#t1-1-1').hide();
		$('#t1-1-2').hide();
		$('#t1-1-3').hide();
		$('#t1-1-4').hide();
	});	
</script>