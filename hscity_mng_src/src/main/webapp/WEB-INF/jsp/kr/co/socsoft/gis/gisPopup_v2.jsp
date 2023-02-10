<%@ page contentType="text/html;charset=UTF-8" %>
	
<!-- 팝업1 -->
<div id="divSavePop" class="pop sr1 st1 gisPopup"> <!--[d] top과 left값으로 위치를 조정-->
	<p class="tit">분석영역 저장</p>
	<div class="cont">			
		<span class="head">영역명</span>
		<label>
			<input type="text" name="txtAnalsNm" id="txtAnalsNm" class="ip sr1 st2 w1" value=""/>
		</label> 
	</div>
	<div class="sec-btn">
		<button onclick="clickRadiusSave('${flag}')" class="btn sr3 st3">저장</button>
		<button onclick="openRsPopup(false, '${flag}')" class="btn sr3 st1">취소</button>
	</div>
	<button href="#" onclick="openRsPopup(false, '${flag}')" class="btn-close">닫기</button>
</div>
<!--// 팝업1 -->

<div id="divInfoPop" class="pop sr1 st1 gisPopup"> <!--[d] top과 left값으로 위치를 조정-->
	<p class="tit"><img class="infopop" src="/images/gis/ico-info.png">&nbsp;알림</p>
	<div class="cont">
		<label id="lblInfo">				
		</label> 
	</div>
	<div class="sec-btn">
		<button onclick="openInfoPopup(false, '')" class="btn sr3 st3">확인</button>
	</div>
	<button href="#" onclick="openInfoPopup(false, '')" class="btn-close">닫기</button>
</div>


<!-- 팝업2 -->
<div id="divAnalsResult" class="pop sr1 st2 gisAnlTree">			
	<div class="cont">
		<ul id="ulPopupLayer" class="pop-list">						
		</ul>
	</div>
	<button href="#" onclick="openTreePopup(false,'${flag}')" class="btn-close">닫기</button>
</div>
<!--// 팝업2 -->

<div id="divMapLoading">
	<img alt="로딩" src="/images/cmm/img-loading.gif"/>
</div>

<div id="divLoadingBar">
	<img alt="로딩" src="/images/cmm/img-loading-bar.gif"/>
</div>

<!-- 범례팝업 -->
<div id="divMapLegend" class="pop sr1 st3 gisLegend">
	<p class="tit">범례</p>
	<div id="divLegendCont" class="cont" style="overflow:hidden;">		
	</div>
	<button href="#" onclick="openLegendPopup(false)" class="btn-close">닫기</button>
</div>

<!-- 팝업3 -->
<div id="divPopExcel" class="pop sr1 st1">
	<p class="tit">데이터 활용 목적</p>
	<div class="cont">
		<ul class="pop-list">
			<li>
				<input type="radio" name="rdExcelChk" id="rdExcelOne" value="보고서 작성" checked="checked"/>
				<label for="rdExcelOne" class="pop-lbl">보고서 작성</label>
			</li>
			<li>
				<input type="radio" name="rdExcelChk" id="bb" value="자체 분석(연구, 논문 등)"/>
				<label for="bb" class="pop-lbl">자체 분석(연구, 논문 등)</label>
			</li>
			<li>
				<input type="radio" name="rdExcelChk" id="cc" value="프로그램 개발(시스템, 웹, 모바일 등)"/>
				<label for="cc" class="pop-lbl">프로그램 개발(시스템, 웹, 모바일 등)</label>
			</li>
			<li>
				<input type="radio" name="rdExcelChk" id="dd" value="etc"/>
				<label for="dd" class="pop-lbl">기타</label>
				<label>
					<input type="text" name="txtExcelEtc" id="txtExcelEtc" class="ip sr1 st2 w1"/>
				</label> 
			</li>
		</ul>
		<label>
		</label> 
	</div>
	<p class="tit">엑셀 다운로드시 파일명 등록</p>
	<div class="cont">	
		<ul class="pop-list">
			<li>
				<label for="dd" class="pop-lbl">파일명 : </label>
				<label>
					<input value="엑셀내보내기" type="text" name="txtExcelName" id="txtExcelName" class="ip sr1 st2 w1"/>
				</label> 
			</li>
		</ul>
	</div>
	<p class="tit">데이터 활용 및 보안 동의</p>	
	<div class="cont">
		<ul class="pop-list">
			<li>
				<span class="head3">1.</span>
				<p class="tail">데이터를 활용 목적으로만 활용하고<br/>
				제 3자의 권리를 침해하거나 범죄 등의<br/>불법행위를 할 목적으로 사용하지<br/> 아니할 것</p>
			</li>
			<li>
				<span class="head3">2.</span>
				<p class="tail">데이터를 제 3자에게 임의로 제공하거나 <br/>유출하지 아니할 것</p>
			</li>
			<li>
				<span class="head3">3.</span>
				<p class="tail">데이터를 위조하거나 변조하지 아니할 것</p>
			</li>
		</ul>
		<p class="ggg">
			<input type="checkbox" name="rdExcelAgree" id="rdExcelAgree"/>
			<label for="rdExcelAgree">데이터를 제공받는데 동의하고, 위 사항을 <br/>지킬 것을 약속합니다.</label> 
		</p>
	</div>	
	<div class="sec-btn">
		<button onclick="exportExcel('${flag}')" class="btn sr3 st3">다운로드</button>
		<button onclick="openExcelPopup(false)" class="btn sr3 st1">취소</button>
	</div>
	<button onclick="openExcelPopup(false)" class="btn-close">닫기</button>
</div>
<!--// 팝업3 -->


<script type="text/javascript">
	$(document).ready(function(){		
		$('#divSavePop').draggable();
		$('#divInfoPop').draggable();
		$('#divMapLegend').draggable();
		$('#divPopExcel').draggable();
		//$('#divAnalsResult').draggable();
	});
	
	//esc 적용
	 $(document).keyup(function(e) {
	     if (e.keyCode == 27) { 
	    	 openRsPopup(false,'${flag}');
	    	 openInfoPopup(false, '');
	    	 openTreePopup(false);
	    	 openExcelPopup(false);
	     }
	});
</script>