<%@ page contentType="text/html;charset=UTF-8" %>   
<div class="tab tab-st1">
	<ul class="tabs">
		<li id="t1-2-1" class="active">
			<a id="li1-2-1" href="#" class="btn-tab">기간별</a>
			<div class="sec-tab result-cont">
				<span class="lment">* SKT 유동인구 데이터 사용</span>
				<p id="plbl1-2-1" class="ment"></p>
				<!-- 분석결과 내용-->
				<div id="frmChart1-2-1" class="col-box wp50"> <!-- [d] wp 클래스로 넓이를 조절할 수 있습니다. wp30~100까지 -->
					<div id="divChart1-2-1"></div>
				</div>
				<!-- //분석결과 내용-->

				<!-- 분석결과 내용-->
				<div id="divGrid1-2-1" class="col-box wp50">
					<table class="tbl_list" id="tblGrid1-2-1"></table>
					<div id="gridPaging1-2-1"></div>							
				</div>
				<!-- 분석결과 내용-->
			</div>
		</li>
		<li id="t1-2-2">
			<a id="li1-2-2" href="#" class="btn-tab">성&#47;연령별</a>
			<div class="sec-tab result-cont">
				<span class="lment">* SKT 유동인구 데이터 사용</span>
				<p id="plbl1-2-2" class="ment"></p>
				<!-- 분석결과 내용-->
				<div id="frmChart1-2-2" class="col-box wp50"> <!-- [d] wp 클래스로 넓이를 조절할 수 있습니다. wp30~100까지 -->
					<div id="divChart1-2-2"></div>
				</div>
				<!-- //분석결과 내용-->

				<!-- 분석결과 내용-->
				<div align="center" id="divGrid1-2-2" class="col-box wp50">
					<table class="tbl_list" id="tblGrid1-2-2"></table>
					<div id="gridPaging1-2-2"></div>											
				</div>
				<!-- 분석결과 내용-->
			</div>
		</li>
		<li id="t1-2-3">
			<a id="li1-2-3" href="#" class="btn-tab">시간대별</a>
			<div class="sec-tab result-cont">
				<span class="lment">* SKT 유동인구 데이터 사용</span>
				<p id="plbl1-2-3" class="ment"></p>
				<!-- 분석결과 내용-->
				<div id="frmChart1-2-3" class="col-box wp60"> <!-- [d] wp 클래스로 넓이를 조절할 수 있습니다. wp30~100까지 -->
					<div id="divChart1-2-3"></div>
				</div>
				<!-- //분석결과 내용-->

				<!-- 분석결과 내용-->
				<div align="center" id="divGrid1-2-3" class="col-box wp40">
					<table class="tbl_list" id="tblGrid1-2-3"></table>
					<div id="gridPaging1-2-3"></div>											
				</div>
				<!-- 분석결과 내용--> 
			</div>
		</li>		
	</ul>
	<!-- 결과창 상단:버튼 영역-->
	<ul class="result-btn-wrap">
		<li>						
			<span id="spanTsLabel1-2"></span>			
			<button id="btnTsStart1-2" style="display: none;" onclick="tsAnalsResult('${flag}', '1-2')" class="btn sr1 st1">시계열분석</button>
			<button id="btnTsStop1-2" style="display: none;"  onclick="tsPopStop('${flag}', '1-2')" class="btn sr1 st1">시계열중지</button>
		</li>
		<li>									
			<button onclick="openExcelPopup(true)" class="btn sr1 st1 ico-exc">Excel</button>
		</li>
		<!-- 펼치기/접기 버튼 -->
		<li>
			<button id="btnFold1-2" class="btn-fold on">접기</button>
		</li>
		<!-- //펼치기/접기 버튼 -->
	</ul>
</div>
<!-- //tab -->
<script type="text/javascript" src="/js/cmm/gis_result.js"> </script>
<script type="text/javascript" src="/js/gis/pop/fPopAnals_v2.js"></script>
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
		initFPopPageInfo('${flag}', '${menu}');
		//시계열 버튼 VIEW
		$('#li1-2-1').click(function(){openTsBtn(true, false, '1-2-1');});
		$('#li1-2-2').click(function(){openTsBtn(false, false, '1-2-2');});
		$('#li1-2-3').click(function(){openTsBtn(false, false, '1-2-3');});
		
		$('#t1-2-1').hide();
		$('#t1-2-2').hide();
		$('#t1-2-3').hide();
	});
</script>