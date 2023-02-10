<%@ page contentType="text/html;charset=UTF-8" %>   
<div class="tab tab-st1">
	<ul class="tabs">
		<li id="t2-4-1" class="active">
			<a id="li2-4-1" href="#" class="btn-tab">정류장별 승하차 정보</a>
			<div class="sec-tab result-cont">
				<!-- 분석결과 내용-->
				<div id="divGrid2-4-1" class="col-box wp100">
					<table class="tbl_list" id="tblGrid2-4-1"></table>
					<div id="gridPaging2-4-1"></div>			
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
			<button id="btnFold2-4" class="btn-fold on">접기</button>
		</li>
		<!-- //펼치기/접기 버튼 -->
	</ul>
</div>
<!-- //tab -->
<script type="text/javascript" src="/js/cmm/gis_result.js"></script>
<script type="text/javascript" src="/js/gis/traffic/facAnals.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		initFacPageInfo('${flag}', '${menu}');
		
		$('#t2-4-1').hide();
	});
</script>