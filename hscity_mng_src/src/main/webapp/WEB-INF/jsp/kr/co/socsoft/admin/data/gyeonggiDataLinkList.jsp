<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/head.jsp" %>
<script>
var gloObj = null;
$(document).ready(function() {
	/* 콤보박스 : 갱신주기 */
	$.ajax({
	    url: "/code/getCommCode.do",
	    type : "GET",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: {groupCd: "data_upd_per_scd", sortCol: "detail_cd", sort: "asc"},
	    success: function(response) {
	    	if (response.length != 0) {
	    		var html = "<option value='' selected>-갱신주기-</option>";
	            for (var i=0; i<response.length; i++) {
	                html += "<option value='" + response[i].cd + "'>" + response[i].nm + "</option>";
	            }
	            $("#dataUpdPerScd").html(html);
	    	}
	    	
	    },
        error : function(request, status, error) { 
        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	}
	});
	
	/* 팝업창(설정) 콤보박스 : 갱신설정 */
	$.ajax({
	    url: "/code/getCommCode.do",
	    type : "GET",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: {groupCd: "openapi_time_scd", sortCol: "detail_cd", sort: "asc"},
	    success: function(response) {
	    	if (response.length != 0) {
	    		var html = "";
	            for (var i = 0; i < response.length; i++) {
	                html += "<option value='" + response[i].cd + "'>" + response[i].nm + "</option>";
	            }
	            $("#CONF_OPENAPI_TIME_SCD").html(html);
	    	}
	    	
	    },
        error : function(request, status, error) { 
        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	}
	});
	
	$('#btnSave').on('click', function(event) {
		fnOpenapiKeySave();
    });
	
	$('#searchText').keydown(function (key) {
		if(key.keyCode == 13){
			$('#Grid').jqGrid('GridUnload');
			gloObj.fnGetGyeonggiOpenapiList();
		}
    });
	
	$('#btnSearch').on('click', function(event) {
		$('#Grid').jqGrid('GridUnload');
		gloObj.fnGetGyeonggiOpenapiList();
    });
	
	$('#btnExecUpload').on('click', function(event) {
		fnExcuteManualUpload();
    });
	
	$('#btnConfSave').on('click', function(event) {
		fnConfSave();
    });
	
	// 엑셀 다운로드
	$('#btnExcel').on('click', function(){
		
		console.log($('#searchVO').serializeObject());
	    if(confirm('데이터를 엑셀로 내려받으시겠습니까?\n데이터 건수가 많을 경우 오래 걸릴 수 있습니다.')) {
	        // var totCount = $grid.getGridParam("records");
	        CM.downloadFileWithIFrame($('body'), '<c:url value="/data/getGyeonggiOpenapiExcel.do"/>', $('#searchVO').serializeObject());
	    }
	});
	
	gloObj = {
			fnGetGyeonggiOpenapiList: function() {
				const param = {'dataUpdPerScd': $('#dataUpdPerScd').val()
					         , 'searchType': $('#searchType').val()
					         , 'searchText': $('#searchText').val()};
		    	$.ajax({
		    	    url: "/data/getGyeonggiOpenapiList.do",
		    	    type : "POST",
		    	    dataType: 'json',
		    	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    	    data: JSON.stringify(param),
		    	    success: function(response) {
		    	    	//console.log(response);
		    		    		theGrid.jqGrid({
		    		    	        datatype: 'local',
		    		    	        data: response ,
		    		    	        colNames: ['구분', '데이터명',  'OpenAPI URL', '갱신설정', '갱신주기', '수동업로드', '설정', '최종갱신일', 'mngStdYmdScd'],
		    		    	        colModel: [                  
				    		    		        {name: 'dataGubunNm', index: 'dataGubunNm', width: 70, align: 'center'},
		    		    	                    {name: 'tableNm', index: 'tableNm', width: 70, align: 'left'},
				    		    		        {name: 'openapiUrl', index: 'openapiUrl', width: 175, align: 'left'},
				    		    		        {name: 'openapiTimeScdNm', index: 'openapiTimeScdNm', width: 25 , align: 'center'},
				    		    		        {name: 'dataUpdPerScdNm', index: 'dataUpdPerScdNm', width: 25, align: 'center'},
				    		    		        {name: '', index: '', width: 70, align: 'center', formatter:formatOpt1, fixed:true},
				    		    		        {name: '', index: '', width: 70, align: 'center', formatter:formatOpt2, fixed:true},             
				    		    		        {name: 'lastUploadDt', index:'lastUploadDt', width:70, align:'left'},
				    		    		        {name: 'mngStdYmdScd', index:'mngStdYmdScd', width:30, align:'center', hidden:true}
		    		    		        	  ],
		    		    	        autowidth: true,
		    		    	        gridview: true,             
		    		    	        rownumbers: true,
		    		    	        rowNum: 15,
		    		    	        //rowList: [5, 10, 15],
		    		    	        //pager: '#GridPager',
		    		    	        sortname: 'Date',
		    		    	        sortorder: 'asc',
		    		    	        viewrecords: true,  
		    		    	        //height: '100%',
		    		    	        height: '450',
		    		    	        width: 'auto',
		    		    	        gridComplete :  function () {
		    		    		                        var maxDate; 
		    		    		                        var rowIDs = jQuery("#Grid").jqGrid('getDataIDs');
		    	
		    		    		                        for (var i = 0; i < rowIDs.length ; i++)  {
		    		    		                            var rowID = rowIDs[i];
		    		    		                            var row = jQuery('#Grid').jqGrid ('getRowData', rowID);
		    	
		    		    		                            if(i==0){
		    		    		                                maxDate = new Date(row.orderdate);
		    		    		                            } else {
		    		    		                                if(maxDate < new Date(row.orderdate)) {   
		    		    		                                	maxDate = new Date(row.orderdate);
		    		    		                                }                                       
		    		    		                            }       
		    		    		                        }
		    		    		                        $("#maxDateField").val(maxDate);
		    		    	                        }
		    		    	    });
		        	}, error : function(request, status, error) { 
		            		console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		        	}
		    	}).done(function() { 
		    		console.log("done");
		    		//theGrid.trigger("reloadGrid");
		    	}),

		        theGrid = $("#Grid"),
		        numberTemplate = {formatter: 'number', align: 'right', sorttype: 'number'};
			}
	};
	
	// init
	gloObj.fnGetGyeonggiOpenapiList();
});

/* manual 실행 팝업 호출 */
function formatOpt1(cellvalue, options, rowObject){
	var str = "";
    	str += "<button class=\"btn sr3 st3 btn-popup\" onclick=\"fnOpenExecutePopup('" + rowObject.tableId + "', '" + rowObject.mngStdYmdScd + "')\">실행</button>";    
    return str;
}

/* 설정 변경 팝업 호출 */
function formatOpt2(cellvalue, options, rowObject){
	var str = "";
    	str += "<button class=\"btn sr3 st3 btn-popup\" onclick=\"fnOpenConfigPopup('" + rowObject.tableId + "', '" + rowObject.openapiUrl + "', '" + rowObject.openapiTimeScd + "');\">설정</button>";
    return str;
}
	
/* 실행 팝업 */
function fnOpenExecutePopup(tableId, mngStdYmdScd) {
   	$("#EXEC_TABLE_ID").val(tableId);
   	$("#EXEC_MNG_STD_YMD_SCD").val(mngStdYmdScd);
   	
   	var formatter = "";
   	if (mngStdYmdScd === "stdym") {
   		formatter = "yymm";
   	} else if (mngStdYmdScd === "stdymd") {
   		formatter = "yymmdd";
   	}
   	
   	//CM.createDatepicker('EXEC_STD_YM', 6);
   	$('#EXEC_STD_YM').datepicker({
   		dateFormat: formatter,
		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
	    dayNamesMin: ['일','월','화','수','목','금','토'],
		changeMonth: true, //월변경가능
	    changeYear: true, //년변경가능
		showMonthAfterYear: true, //년 뒤에 월 표시
	});
	$(".pop-box1").show();
}

/* 설정 팝업 */
function fnOpenConfigPopup(tableId, openapiUrl, openapiTimeScd) {
	$("#CONF_TABLE_ID").val(tableId);	    
   	$("#CONF_OPENAPI_URL").val(openapiUrl);
   	$("#CONF_OPENAPI_TIME_SCD").val(openapiTimeScd);
	$(".pop-box2").show();
}

/* manual 실행 */
function fnExcuteManualUpload() {
	if ($("#EXEC_STD_YM").val().length === 0) {
		alert("기준년월 설정을 해야 합니다.");
		return false;
	}
	$.ajax({
	    //url: "/data/excuteGyeonggiOpenapiManualUpload.do",
	    url: "/data/excuteGyeonggiOpenapiManualUploadFtp.do",
	    type : "GET",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: { 'tableId': $("#EXEC_TABLE_ID").val(), 'stdYm': $("#EXEC_STD_YM").val() },
	    success: function(response) {
	    	//alert(response.resultMsg);
	    	alert("처리되었습니다. 데이터 업로드 로그를 확인해주세요.");
	    	$('#Grid').jqGrid('GridUnload');
	    	$(".pop-box1").hide();
	    	gloObj.fnGetGyeonggiOpenapiList();
	    	$("$btnExecUpload").attr("disabled", false);
        },
        error : function(request, status, error) { 
        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        	if (request.status != "200") {
        		alert("실행 중 오류 발생");
        		$('#Grid').jqGrid('GridUnload');
        		gloObj.fnGetGyeonggiOpenapiList();
        	}
    	}
	}).done(function() { 
		//console.log("done");
	});
}

/* 설정 저장 */
function fnConfSave() {
   	if ($("#CONF_OPENAPI_URL").val().length === 0) {
		alert("OpenAPI URL을 입력하세요.");
		return false;
	}
	$.ajax({
	    url: "/data/openapiConfSave.do",
	    type : "POST",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: JSON.stringify({tableId: $("#CONF_TABLE_ID").val(), openapiUrl: $("#CONF_OPENAPI_URL").val(), openapiTimeScd: $("#CONF_OPENAPI_TIME_SCD").val()}),
	    success: function(response) {
	    	alert("저장되었습니다.");
	    	$('#Grid').jqGrid('GridUnload');
	    	$(".pop-box2").hide();
	    	gloObj.fnGetGyeonggiOpenapiList();
        },
        error : function(request, status, error) { 
        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	}
	}).done(function() { 
		//console.log("done");
	});
}

/* openapi key 저장 */
function fnOpenapiKeySave() {
   	if ($("#OPENAPI_KEY").val().length === 0) {
		alert("Open API Key를 입력하세요.");
		return false;
	}
   	
   	if (confirm("저장하시겠습니까?")) {
		$.ajax({
		    url: "/data/gyeonggiOpenapiKeySave.do",
		    type : "POST",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    data: JSON.stringify({openapiKey: $("#OPENAPI_KEY").val()}),
		    success: function(response) {
		    	alert("저장되었습니다.");
		    	$('#Grid').jqGrid('GridUnload');
		    	$(".pop-box2").hide();
		    	gloObj.fnGetGyeonggiOpenapiList();
	        },
	        error : function(request, status, error) { 
	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    	}
		}).done(function() { 
			//console.log("done");
		});
   	}
}
</script>
<body>
<div id="wrap">
	<%--헤더--%>
    <%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/layout/header.jsp" %>
	<div id="container">
		<%-- left 메뉴 --%>
        <jsp:include page="/com/leftMenu.do?type=${type}&menu=${menu}"/>
		<div class="sec-right">
			<!-- 상단 경로 및 페이지 타이틀 -->
			<div class="top-bar">
				<div class="path-wrap">
					<span class="home">홈</span>&nbsp;&nbsp;&gt;
					<span class="path">데이터 관리</span>&nbsp;&nbsp;&gt;
					<span class="now">경기데이터드림 연계 관리</span>
				</div>
				<div class="clearfix">
					<p class="tit-page">경기데이터드림 연계 관리</p>
					<!-- <ul class="btn-wrap"> -->
						<!-- <li> -->
							<!-- <button class="btn sr3 st3" title="DB에 물리테이블이 반드시 필요합니다."> -->
								<!-- <i class="fa fa-download" aria-hidden="true"></i>물리테이블 정보 불러오기 -->
							<!-- </button> -->
						<!-- </li> -->
					<!-- </ul> -->
				</div>
			</div>
			<!-- //상단 경로 및 페이지 타이틀 -->

			<!-- 컨텐츠 영역 -->
			<div class="cont">
				<table class="tbl sr1 st1" style="margin-bottom:30px">
					<caption class="hide">표</caption>
					<colgroup>
						<col style="width:20%"/>
						<col style="width:auto"/>
					<tbody>
						<tr>
							<th scope="row" class="th2">
								<label for="bb1"><span class="star">중요</span>Open API Key</label>
							</th>
							<td class="align-l">
								<input type="text" id="OPENAPI_KEY" class="ip sr1 st1 w4" />	
								<button id="btnSave" class="btn sr2 st3">저장</button>
							</td>
						</tr>
					</tbody>
				</table>	
				<!-- 표 상단 설정 영역 -->
				<div class="tbl-top">
					<form id='searchVO' method="post" name='searchVO'>
						<fieldset>
						<legend>표,게시판 정보</legend>
							<div class="left">							
								<label>
									<select id="dataUpdPerScd" name="dataUpdPerScd" class="ip sr1 st1 w1">
									</select>
								</label>									
								<label>
									<select id="searchType" name="searchType" class="ip sr1 st1 w1">
										<option value="">-전 체-</option>
										<option value="tableNm">테이블명</option>
										<option value="tableId">테이블ID</option>
									</select>
								</label>
								<label>
									<input type="text" class="ip sr1 st1 w2" id="searchText" name="searchText"/>
									<input type="text" class="ip sr1 st1 w2" style="display:none;" />
								</label>
								<input type="button" class="btn sr2 st2" id="btnSearch" value="조회"/>
							    <input id='btnExcel' type="button" class="btn sr2 st6" value="엑셀"/>
							</div>		
						</fieldset> 
					</form>
				</div>
				<!-- //표 상단 설정 영역 -->
				<table id="Grid">
				</table>
				<!-- 페이징 -->
				<div id="GridPager">
				</div>	
				<!-- //페이징 -->
			</div>
			<!-- //컨텐츠 영역 -->
			<!-- 모달 팝업1 -->
			<div class="modal-pop pop-box1">
				<div class="layer-pop" style="width:400px;">
					<p class="tit">수동 업로드</p>
					<div class="cont">
						<table class="tbl sr1 st1">
							<caption class="hide">표</caption>
							<colgroup>
								<col style="width:30%"/>
								<col style="width:auto"/>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" class="th2">기준년월 설정</th>
									<td class="align-l">
									    <input type="hidden" id="EXEC_TABLE_ID" />
									    <input type="hidden" id="EXEC_MNG_STD_YMD_SCD" />
										<input type="text"   id="EXEC_STD_YM" class="ip sr1 st1 cald stdYm">
									</td>
								</tr>
							</tbody>
						</table>
						<div class="btn-wrap mgb0">
							<button class="btn sr3 st3" id="btnExecUpload">적재</button>	
						</div>				
					</div>
					<a href="#" class="btn-close pop-close">닫기</a><!--[d] "pop-close" 클래스 값을 클릭할 시 팝업 닫힘-->
				</div>
			</div>
			<!-- 모달 팝업 -->
			
			<!-- 모달 팝업2 -->
			<div class="modal-pop pop-box2">
				<div class="layer-pop" style="width:530px;">
					<p class="tit">설정</p>
					<div class="cont">
						<table class="tbl sr1 st1">
							<caption class="hide">표</caption>
							<colgroup>
								<col style="width:20%"/>
								<col style="width:auto"/>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" class="th2">OpenAPI URL</th>
									<td class="align-l">
										<input type="hidden" id="CONF_TABLE_ID" />
										<input type="text" id="CONF_OPENAPI_URL" class="ip sr1 st1 w3"/>
									</td>
								</tr>
								<tr>
									<th scope="row" class="th2">갱신 설정</th>
									<td class="align-l">
										<select class="ip sr1 st1 w2" id="CONF_OPENAPI_TIME_SCD">
										</select>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="btn-wrap mgb0">
							<a href="#" id="btnConfSave" class="btn sr2 st2">저장</a>	
						</div>				
					</div>
					<a href="#" class="btn-close pop-close">닫기</a><!--[d] "pop-close" 클래스 값을 클릭할 시 팝업 닫힘-->
				</div>
			</div>
			<!-- 모달 팝업 -->
		</div>
		<!-- //sec-right -->
	</div>
	<!-- //container -->
</div>
<!-- //wrap -->
</body>
</html>