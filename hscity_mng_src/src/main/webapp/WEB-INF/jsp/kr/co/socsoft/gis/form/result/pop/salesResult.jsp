<%@ page contentType="text/html;charset=UTF-8" %>   

<div class="tab tab-st1">
	<ul class="tabs">
		<li id="t1-4-1" class="active">
			<a id="li1-4-1" href="#" class="btn-tab">기간별</a>
			<div class="sec-tab result-cont">
				<span class="lment">* 신한카드 데이터 사용 (개인정보 보호에 의해 소비매출 정보는 블럭단위로 제공되며 이로 인해 지정된 영역보다 넓은 영역으로 분석될 수 있습니다.)					
				</span>					
				<p id="plbl1-4-1" class="ment"></p>
				<!-- 분석결과 내용-->
				<div id="frmChart1-4-1" class="col-box wp50"> <!-- [d] wp 클래스로 넓이를 조절할 수 있습니다. wp30~100까지 -->
					<div id="divChart1-4-1"></div>
				</div>
				<!-- //분석결과 내용-->

				<!-- 분석결과 내용-->
				<div id="divGrid1-4-1" class="col-box wp50">
					<table class="tbl_list" id="tblGrid1-4-1"></table>
					<div id="gridPaging1-4-1"></div>								
				</div>
				<!-- 분석결과 내용-->
			</div>
		</li>
		<li id="t1-4-2">
			<a id="li1-4-2" href="#" class="btn-tab">성&#47;연령별</a>
			<div class="sec-tab result-cont">
				<span class="lment">* 신한카드 데이터 사용 (개인정보 보호에 의해 소비매출 정보는 블럭단위로 제공되며 이로 인해 지정된 영역보다 넓은 영역으로 분석될 수 있습니다.)</span>
				<p id="plbl1-4-2" class="ment"></p>
				<!-- 분석결과 내용-->
				<div id="frmChart1-4-2" class="col-box wp60">
					<div id="divChart1-4-2"></div>
				</div>
				<!-- //분석결과 내용-->

				<!-- 분석결과 내용-->
				<div id="divGrid1-4-2" class="col-box wp40">
					<table class="tbl_list" id="tblGrid1-4-2"></table>
					<div id="gridPaging1-4-2"></div>								
				</div>
				<!-- 분석결과 내용-->
			</div>
		</li>
		<li id="t1-4-3">
			<a id="li1-4-3" href="#" class="btn-tab">시간대별</a>			
			<div class="sec-tab result-cont">
				<span class="lment">* 신한카드 데이터 사용 (개인정보 보호에 의해 소비매출 정보는 블럭단위로 제공되며 이로 인해 지정된 영역보다 넓은 영역으로 분석될 수 있습니다.)</span>
				<p id="plbl1-4-3" class="ment"></p>
				<!-- 분석결과 내용-->
				<div id="frmChart1-4-3" class="col-box wp60">
					<div id="divChart1-4-3"></div>
				</div>
				<!-- //분석결과 내용-->

				<!-- 분석결과 내용-->
				<div id="divGrid1-4-3" class="col-box wp40">
					<table class="tbl_list" id="tblGrid1-4-3"></table>
					<div id="gridPaging1-4-3"></div>								
				</div>
				<!-- 분석결과 내용-->
			</div>
		</li>
		<li id="t1-4-4">
			<a id="li1-4-4" href="#" class="btn-tab">업종별</a>			
			<div class="sec-tab result-cont">
				<span class="lment">* 신한카드 데이터 사용 (개인정보 보호에 의해 소비매출 정보는 블럭단위로 제공되며 이로 인해 지정된 영역보다 넓은 영역으로 분석될 수 있습니다.)</span>
				<p id="plbl1-4-4" class="ment"></p>
				<!-- 분석결과 내용-->
				<div id="frmChart1-4-4" class="col-box wp60">
					<div id="divChart1-4-4"></div>
				</div>
				<!-- //분석결과 내용-->

				<!-- 분석결과 내용-->
				<div id="divGrid1-4-4" class="col-box wp40">
					<table class="tbl_list" id="tblGrid1-4-4"></table>
					<div id="gridPaging1-4-4"></div>								
				</div>
				<!-- 분석결과 내용-->
			</div>
		</li>
		<li id="t1-4-5">
			<a id="li1-4-5" href="#" class="btn-tab">외국인</a>			
			<div class="sec-tab result-cont">
				<span class="lment">* 신한카드 데이터 사용 (개인정보 보호에 의해 소비매출 정보는 블럭단위로 제공되며 이로 인해 지정된 영역보다 넓은 영역으로 분석될 수 있습니다.)
					<br/>&nbsp;&nbsp;&nbsp;&nbsp;* 외국인 데이터는 읍/면/동 단위로만 제공합니다.
				</span>
				<p id="plbl1-4-5" class="ment"></p>
				<!-- 분석결과 내용-->
				<div id="frmChart1-4-5" class="col-box wp60">
					<div id="divChart1-4-5"></div>
				</div>
				<!-- //분석결과 내용-->

				<!-- 분석결과 내용-->
				<div id="divGrid1-4-5" class="col-box wp40">
					<table class="tbl_list" id="tblGrid1-4-5"></table>
					<div id="gridPaging1-4-5"></div>								
				</div>
				<!-- 분석결과 내용-->
			</div>
		</li>
	</ul>
	<!-- 결과창 상단:버튼 영역-->
	<ul class="result-btn-wrap">
		<li>						
			<span id="spanTsLabel1-4"></span>			
			<button id="btnTsStart1-4" style="display: none;" onclick="tsAnalsResult('${flag}', '1-4')" class="btn sr1 st1">시계열분석</button>
			<button id="btnTsStop1-4" style="display: none;"  onclick="tsPopStop('${flag}', '1-4')" class="btn sr1 st1">시계열중지</button>
		</li>
		<li>									
			<button onclick="openExcelPopup(true)" class="btn sr1 st1 ico-exc">Excel</button>
		</li>
		<!-- 펼치기/접기 버튼 -->
		<li>
			<button id="btnFold1-4" class="btn-fold on">접기</button>
		</li>
		<!-- //펼치기/접기 버튼 -->
	</ul>
</div>
<!-- //tab -->
<script type="text/javascript" src="/js/cmm/gis_result.js"></script>
<script type="text/javascript" src="/js/gis/pop/salesAnals.js"></script>
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
		initSalePageInfo('${flag}','${menu}');
		//시계열 버튼 VIEW
		$('#li1-4-1').click(function(){openTsBtn(true, false, '1-4-1');});
		$('#li1-4-2').click(function(){openTsBtn(true, false, '1-4-2');});
		$('#li1-4-3').click(function(){openTsBtn(true, false, '1-4-3');});
		$('#li1-4-4').click(function(){openTsBtn(true, false, '1-4-4');});
		$('#li1-4-5').click(function(){openTsBtn(false, false, '1-4-5');});
		
		$('#t1-4-1').hide();
		$('#t1-4-2').hide();
		$('#t1-4-3').hide();
		$('#t1-4-4').hide();
		$('#t1-4-5').hide();
	});
</script>