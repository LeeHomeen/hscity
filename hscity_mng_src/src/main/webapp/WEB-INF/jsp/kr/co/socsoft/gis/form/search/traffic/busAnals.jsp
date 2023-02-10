<%@ page contentType="text/html;charset=UTF-8" %>   
<div>
	<h2 class="lnb-tit">분석영역 설정</h2>
	<div class="lnb-cont">					
		<div class="box-cdt">
			<p class="tit">분석기준</p>
			<!--슬라이드 업다운-->
			<button class="btn-drop pst1" title="접기&#47;열기">접기&#47;열기</button>
			<div class="drop-slide">
				<div class="cont">												
					<div id="divBusInfo"></div>
					<ul class="exp-list">								
					</ul>
				</div>
				<div class="btn-wrap">
					<button onclick="busAnals('2-2-1', '${flag}')" class="btn sr2 st2">분석결과 조회</button>
				</div>
			</div>
			<!-- //슬라이드 업다운-->
		</div>
		<div class="box-notc">
			<p class="tit">저상버스 도입 요구분석</p>
			<ul class="list">
				<li>1) 화성시 버스노선에 포함된 정류장분석</li>
				<li>2) 정류장 반경 200m 버퍼 설정</li>
				<li>3) 버퍼내의 주민등록인구, 유동인구, 관공서(시청, 구청), 병원 보건소, 복지시설 분석</li>
				<li>4) 노인인구 백분위, 시설백분위 순으로 정렬</li>
			</ul>
		</div>
	</div>

	<!-- 펼치기/접기 버튼 --> 
	<div class="lnb-fold">
		<button onclick="toggleLeftMenu('${flag}','left', '2-2')" class="btn-fold">접기</button>
	</div>
</div>