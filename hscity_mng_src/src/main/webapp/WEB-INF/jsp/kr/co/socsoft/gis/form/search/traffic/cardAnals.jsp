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
			</div>
			<!-- //슬라이드 업다운-->
			<div class="btn-wrap">
				<button onclick="cardAnals('2-3-1', '${flag}')" class="btn sr2 st2">분석결과 조회</button>
			</div>
		</div>		
		
	</div>

	<!-- 펼치기/접기 버튼 --> 
	<div class="lnb-fold">
		<button onclick="toggleLeftMenu('${flag}','left', '2-3')" class="btn-fold">접기</button>
	</div>	
</div>

<script type="text/javascript">
	$(document).ready(function(){		
		$(window).trigger("resize");
	});
	//updateMap
</script>