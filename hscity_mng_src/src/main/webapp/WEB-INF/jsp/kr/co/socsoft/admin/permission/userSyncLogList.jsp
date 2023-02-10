<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/head.jsp" %>
<body>
<div id="wrap">
    <%--헤더--%>
    <%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/layout/header.jsp" %>

    <div id="container">
        <%-- left 메뉴 --%>
        <jsp:include page="/com/leftMenu.do?type=${type}&menu=${menu}"/>
<script>
$(document).ready(function(){
	CM.createDatepickerLinked('stdYm1', 'stdYm2', 8);
	searchDateTerm(1);
    
	$('#srchBtn').on('click', function(event) {
		$('#syncTable').jqGrid('GridUnload');
		getSyncList($('#stdYm1').val(),$('#stdYm2').val(),$('#logType').children(":selected").attr("id"),$('#srch').val());
	});
	$.ajax({
		url : "/code/getCommCode.do",
		type : "GET",
		dataType : 'json',
		headers : {
			'Content-Type' : 'application/json; charset=UTF-8'
		},
		data: {groupCd: "log_type_scd", sortCol: "detail_cd", sort: "asc"},
		success : function(response) {
			var html = '<option>전체</option>'
				 for (var i = 0; i < response.length; i++) {
				html += '<option id="' + response[i].cd + '" value="' + response[i].cd + '">' + response[i].nm + '</option>'
			}
			$('#logType').html(html);
		}
	});
	getSyncList($('#stdYm1').val(),$('#stdYm2').val(),$('#logType').children(":selected").attr("id"),$('#srch').val());
	/*
	$('#stdYm1,#stdYm2').datepicker({
		dateFormat: 'yy-mm-dd',
		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
	     dayNamesMin: ['일','월','화','수','목','금','토'],
		 changeMonth: true, //월변경가능
	     changeYear: true, //년변경가능
		 showMonthAfterYear: true, //년 뒤에 월 표시
	});
	*/
	
	// 엑셀 다운로드
	$('#btnExcel').on('click', function(){
		
		console.log($('#searchVO').serializeObject());
	    if(confirm('데이터를 엑셀로 내려받으시겠습니까?\n데이터 건수가 많을 경우 오래 걸릴 수 있습니다.')) {
	        // var totCount = $grid.getGridParam("records");
	        CM.downloadFileWithIFrame($('body'), '<c:url value="/permission/getLogSyncExcel.do"/>', $('#searchVO').serializeObject());
	    }
	});
	
});

function getSyncList(stdYm1,stdYm2,logType,key){
	$.ajax({
		url : "/permission/getLogSyncList.do",
		type : "POST",
		data: JSON.stringify({'stdYm1' : stdYm1, 'stdYm2' : stdYm2, 'logType' : logType, 'key' : key}),
		dataType : 'json',
		headers : {
			'Content-Type' : 'application/json; charset=UTF-8'
		},
		success : function(response) {
		
		$('#syncTable').jqGrid({
			datatype : 'local',
			data : response,
			colNames : [ '로그일자','로그타입', '로그메세지' ],
			colModel : [ {
				name : 'logDate',
				index : 'logDate',
				width : 70,
				align : 'center'
			}, {
				name : 'logTypeScdNm',
				index : 'logTypeScdM,',
				width : 30,
				align : 'center'
			}, {
				name : 'logMsg',
				index : 'logMsg',
				width : 250,
				align : 'left'
			}],
			multiselect :false,
			autowidth : true,
			gridview : true,
			rownumbers : true,
			rowNum : 10,
			//rowList : [ 5, 10, 15 ],
			//pager : '#GridPager',
			sortname : 'Date',
			sortorder : 'asc',
			viewrecords : true,
			height : '450',
			width : 'auto',
			gridComplete : function() {
				var maxDate;
				var rowIDs = jQuery("#syncTable").jqGrid('getDataIDs');

				for (var i = 0; i < rowIDs.length; i++) {
					var rowID = rowIDs[i];
					var row = jQuery("#syncTable").jqGrid('getRowData', rowID);

					if (i == 0) {
						maxDate = new Date(row.orderdate);
					} else {
						if (maxDate < new Date(row.orderdate)) {
							maxDate = new Date(row.orderdate);
						}
					}
				}
				$("#maxDateField").val(maxDate);
			}
		});
		
		},
		error : function(request, status, error) {
			alert("code:" + request.status + "\n" + "message:"
					+ request.responseText + "\n" + "error:" + error);
		}
	}).done(function() {
		//$('#table3').jqGrid('GridUnload');
		//getAllUserMemuPermit();

	});

}

function searchDateTerm(term) {
    var today = new Date();
    var oneMonthsAgo = new Date(today.getFullYear(), today.getMonth() - term, today.getDate());
    $('#stdYm1').datepicker('setDate', oneMonthsAgo);
    $('#stdYm2').datepicker('setDate', today);
}
</script>

			<div class="sec-right">
				<!-- 상단 경로 및 페이지 타이틀 -->
				<div class="top-bar">
					<div class="path-wrap">
						<span class="home">홈</span>&nbsp;&nbsp;&gt;
						<span class="path">사용자 및 권한 관리</span>&nbsp;&nbsp;&gt;
						<span class="path">사용자 동기화 로그</span>&nbsp;&nbsp;&gt;
						<span class="now">사용자 동기화 로그 조회</span>
					</div>
					<div class="clearfix">
						<p class="tit-page">사용자 동기화 로그 조회</p>
						<ul class="btn-wrap">
							<li>
							</li>
						</ul>
					</div>
				</div>
				<!-- //상단 경로 및 페이지 타이틀 -->

				<!-- 컨텐츠 영역 -->
				<div class="cont">
					<!-- 표 상단 설정 영역 -->
					<div class="tbl-top">
						<form id='searchVO' method="post" name='searchVO'>
							<fieldset>
							<legend>표,게시판 검색 조건</legend>
								<div class="left">
								
								
								   <fieldset>
									<input type="text" class="ip sr1 st1 cald stdYm" title="시작날짜" id="stdYm1" name="stdYm1"/> -
									<input type="text" class="ip sr1 st1 cald stdYm" title="끝날짜" id="stdYm2" name="stdYm2"/>
									</fieldset>
							
								</div>
								<div class="right">
									<label>
										<select class="ip sr1 st1 w1" id="logType" name="logType">
										</select>
									</label>
									<label for="srch" class="label">메세지:</label>
									<input type="text" class="ip sr1 st1 w2" id="srch" name="srch"/>
									<input type="button" class="btn sr2 st2" value="조회" id="srchBtn"/>
									       <input id='btnExcel' type="button" class="btn sr2 st6" value="엑셀"/>
								</div>
							</fieldset> 
						</form>							
					</div>
					
					<table id="syncTable"></table>
					<div id="GridPager"></div>

				</div>
				<!-- //컨텐츠 영역 -->
				
			</div>
		
    </div>
    <!--//container -->
</div>
</body>
</html>