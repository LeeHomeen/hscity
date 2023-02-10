<%@ page contentType="text/html;charset=UTF-8" %>   
<div class="tab tab-st1">
	<ul class="tabs">
		<li id="t2-1-1" class="active">
			<a id="li2-1-1" href="#" class="btn-tab">사각지대 결과</a>
			<div class="sec-tab result-cont">
				<div class="col-box wp20"> <!-- [d] wp 클래스로 넓이를 조절할 수 있습니다. wp30~100까지 -->
					<br/>
					<div><b>* 대중교통 사각지대 분석방법</b></div>
					<br/>
					<div>&nbsp;&nbsp;1) 버스정류장 버퍼영역 설정</div>
					<p>
					<div>&nbsp;&nbsp;2) 버스정류장 버퍼를 제외한 국토부 <p>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;100m X 100m 격자조회
					</div>
					<p>
					<div>&nbsp;&nbsp;3) 조회된 국토부 100m X 100m 와<p>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;주민등록인구분석 매핑
					</div>
					<p>
					<div>&nbsp;&nbsp;4) 입력된 기준인구수이상인 격자분석</div>
					<br>
					<div>&nbsp;&nbsp;&nbsp;&nbsp;<font color='#180054'>- 검색결과는 상위100개만 표시</font></div>
				</div>
				<!-- 분석결과 내용-->
				<div id="divGrid2-1-1" class="col-box wp80">
					<table class="tbl_list" id="tblGrid2-1-1"></table>
					<div id="gridPaging2-1-1"></div>			
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
			<button id="btnFold2-1" class="btn-fold on">접기</button>
		</li>
		<!-- //펼치기/접기 버튼 -->
	</ul>
</div>
<!-- //tab -->
<script type="text/javascript" src="/js/cmm/gis_result.js"> </script>
<script type="text/javascript" src="/js/gis/traffic/blindAnals.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		initBlindPageInfo('${flag}', '${menu}');
		
		$('#t2-1-1').hide();
	});
</script>