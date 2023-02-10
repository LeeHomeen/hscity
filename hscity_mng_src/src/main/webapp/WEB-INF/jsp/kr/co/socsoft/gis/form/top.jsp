<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="hd-inwrap">
	<h1 class="hd-logo">
		<a href="#"><img src="/images/gis/logo-gis.png" alt="화성시"/></a>
		<c:choose>
			<c:when test="${flag == 'pop'}">
				<span class="tit">인구&#47;매출 공간분석</span>
			</c:when>			
			<c:otherwise>
				<span class="tit">대중교통 표준모델 공간분석</span>				
			</c:otherwise>
		</c:choose>
	</h1>
	<div class="hd-right">
		<c:if test="${flag == 'pop'}">
			<span>
				<button id="btnRdSave" onclick="openRsPopup(true, '${flag}')" class="btn sr1 st1 ico-save">현재 분석영역 저장</button>
			</span>
		</c:if>
		<span class="bar-srch">
			<input type="text" id="keyword" placeholder="주소 및 건물명" class="ip-srch" />
			<input type="button" id="btnKeywordSearch" onclick="searchPlaces('${flag}')"  value="검색" class="btn-srch"/>
		</span>
		<span class="sel-cst st1 w1">
			<label for="aa1" class="hide">지도 종류 선택</label> 
			<select name="selMapType" id="selMapType">
				<option value="VWST" selected="selected">브이월드(일반)</option>
				<option value="VWSAT">브이월드(위성)</option>
				<option value="NVST">네이버(일반)</option>
				<option value="NVSAT">네이버(위성)</option>
			</select>
		</span>
		<!-- 주소 결과창 -->
		<div id="divSearchList" class="srch-list-wrap" style="display: none;">
			<ul id="ulSearchList" class="srch-list">
			</ul>
			<p class="close-bar"><button style="margin-top: 5px;" onclick="closeSearchList()" class="btn-close">닫기</button></p>
		</div>
		<!-- //주소 결과창 -->
		
	</div>
</div>
<script type="text/javascript" src="/js/gis/daum/keyword.js"></script>
<script type="text/javascript">
	$(document).ready(function(){		
		if(daumKeyword == null)
			// 키 에러
			//daumKeyword = new daum.maps.services.Places();			
			
		$('#selMapType').change(function(e){
			var mapObj = getMapObj('${flag}');
			mapObj.mapTools.changeBaseMap(this.value, false);
		});
		
		//key event
		$("#keyword").keydown(function (key) {			 
	        if(key.keyCode == 13){
	        	$('#btnKeywordSearch').trigger('click');
	        }
	    });
	});
</script>
