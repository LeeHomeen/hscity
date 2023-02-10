<%@ page contentType="text/html;charset=UTF-8" %>   
<div class="tab tab-st1">
	<ul class="tabs">
		<li id="t2-2-1" class="active">
			<a id="li2-2-1" href="#" class="btn-tab">저상버스도입 요구분석결과</a>
			<div class="sec-tab result-cont">
				<!-- 분석결과 내용-->
				<div id="divGrid2-2-1" class="col-box wp100">
					<table class="tbl_list" id="tblGrid2-2-1"></table>
					<div id="gridPaging2-2-1"></div>			
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
			<button id="btnFold2-2" class="btn-fold on">접기</button>
		</li>
		<!-- //펼치기/접기 버튼 -->
	</ul>
</div>
<!-- //tab -->
<script type="text/javascript" src="/js/cmm/gis_result.js"></script>
<script type="text/javascript" src="/js/gis/traffic/busAnals.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		initBusPageInfo('${flag}', '${menu}');
		
		$('#t2-2-1').hide();
	});
</script>