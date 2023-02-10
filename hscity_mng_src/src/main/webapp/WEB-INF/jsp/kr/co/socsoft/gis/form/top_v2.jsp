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
			<input type="button" id="btnKeywordSearch" value="검색" class="btn-srch"/>
		</span>
		<span class="sel-cst st1 w1">
			<label for="aa1" class="hide">지도 종류 선택</label> 
			<select name="selMapType" id="selMapType">
				<option value="base" selected="selected">브이월드(일반)</option>
				<option value="hybrid">브이월드(위성)</option>
				<option value="kakao">카카오(일반)</option>
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
<script type="text/javascript">
$('#selMapType').change(function(e){
	gis.toggleBaseLayer(this.value);
});

$("#btnKeywordSearch").click(function() {
	var keyword = $('#keyword').val();
	if (!keyword.replace(/^\s+|\s+$/g, '')) {		
		openInfoPopup(true,'키워드를 입력해주세요!');
		closeSearchList();
	    return false;
	}
	$.ajax({
		type:"POST",
		url:"/gis/kakaoKeyword.do",
		data:{keyword: keyword},
		dataType:'json',		
		success:function(data){
			if( data.documents.length == 0 ) {
				openInfoPopup(true,'검색 결과가 존재하지 않습니다.');
		        closeSearchList();
		        return;
			}
			
			var html = "";
		    for ( var i=0; i<data.documents.length; i++ ) {
		    	//마커는 여기서 표시
		    	html += "<li><a href='#' onclick='itemLocation(\""+data.documents[i].x+"\",\""+data.documents[i].y+"\")'><img src='/images/cmm/bul1.gif'>&nbsp;<strong>"+data.documents[i].place_name + "</strong>";
		    	if(data.documents[i].road_address_name != null && data.documents[i].road_address_name != '')
		    		html += "<br/>&nbsp;&nbsp" + data.documents[i].road_address_name;
		    	if(data.documents[i].address_name != null && data.documents[i].address_name != '')
		    		html += "<br/>&nbsp;&nbsp" +data.documents[i].address_name;
		    	html += "</a>";
				html += "</li>";    	
		    }
		    if(html != ""){
		    	$('#ulSearchList').html(html);
		    	$('#divSearchList').show();
		    }
		}
	});
})
$("#keyword").keydown(function (key) {			 
	if(key.keyCode == 13){
		$('#btnKeywordSearch').trigger('click');
	}
});

//클릭 지점으로 이동
function itemLocation(x, y){
	// 위치 이동
	gis.zoomToPoint(x, y, 15, 'EPSG:4326');
	// 포인트 그리기
	var feature = gis.createPoint(x, y, 'EPSG:4326');
	var layer = gis.getLayer("addressLayer");
	layer.getSource().clear();
	layer.getSource().addFeatures([feature]);
}

//키워드 검색창 종료
function closeSearchList(){
	$('#keyword').val('');
	$('#divSearchList').hide();
	$('#ulSearchList').html('');
}

</script>
