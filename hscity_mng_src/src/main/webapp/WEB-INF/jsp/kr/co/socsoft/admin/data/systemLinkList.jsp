<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/head.jsp" %>
<script>
var gloObj = null;
$(document).ready(function() {
	/* 콤보박스 : 시스템연계(내부시스템코드) */
	$.ajax({
	    url: "/code/getSystemLinkCode.do",
	    type : "GET",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: {groupCd: "data_conn_scd", sortCol: "detail_cd", sort: "asc"},
	    success: function(response) {
	    	if (response.length != 0) {
	    		var html = "<option value='' selected>-전체-</option>";
	            for (var i=0; i<response.length; i++) {
	                html += "<option value='" + response[i].cd + "'>" + response[i].nm + "</option>";
	            }
	            $("#dataConnScd").html(html);
	    	}
	    	
	    },
        error : function(request, status, error) { 
        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	}
	});

	


	$('#btnSearch').on('click', function(event) {
		$('#Grid').jqGrid('GridUnload');
		gloObj.fnGetSystemLinkList();
    });
	
	$('#btnUpload').on('click', function(event) {
		fnExcuteManualUpload();
    });
	

	// 엑셀 다운로드
	$('#btnExcel').on('click', function(){
		
		console.log($('#searchVO').serializeObject());
	    if(confirm('데이터를 엑셀로 내려받으시겠습니까?\n데이터 건수가 많을 경우 오래 걸릴 수 있습니다.')) {
	        // var totCount = $grid.getGridParam("records");
	        CM.downloadFileWithIFrame($('body'), '<c:url value="/data/getSystemLinkExcel.do"/>', $('#searchVO').serializeObject());
	    }
	});
	
	gloObj = {
			fnGetSystemLinkList: function() {
				const param = {'dataConnScd': $('#dataConnScd').val()
					         , 'searchType': $('#searchType').val()
					         , 'searchText': $('#searchText').val()};
		    	$.ajax({
		    	    url: "/data/getSystemLinkList.do",
		    	    type : "POST",
		    	    dataType: 'json',
		    	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    	    data: JSON.stringify(param),
		    	    success: function(response) {
		    	    	console.log(response);
		    		    		theGrid.jqGrid({
		    		    	        datatype: 'local',
		    		    	        data: response ,
		    		    	        colNames: ['데이터구분','테이블ID', '데이터명', '보유기관','갱신주기','수동업로드', '최종갱신일', 'mngStdYmdScd'],
		    		    	        colModel: [                  
				    		    		        {name: 'dataGubunNm', index: 'dataGubunNm', width: 70, align: 'center'},
				    		    		        {name: 'tableId', index: 'tableId', width: 70, align: 'left'},				    		    		        
				    		    	            {name: 'tableNm', index: 'tableNm', width: 70, align: 'left'},
				    		    		        {name: 'oriDataOwner', index: 'oriDataOwner', width: 50, align: 'center'},
				    		    		        {name: 'dataUpdPerScdNm', index: 'dataUpdPerScdNm', width: 25, align: 'center'},
				    		    		        {name: '', index: '', width: 70, align: 'center', formatter:formatOpt1, fixed:true},       
				    		    		        {name: 'lastUploadDt', index:'lastUploadDt', width:50, align:'center'},
				    		    		        {name: 'dataConnScd', index:'dataConnScd', width:30, align:'center', hidden:true}
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
	gloObj.fnGetSystemLinkList();
});

/* manual 실행 팝업 호출 */
function formatOpt1(cellvalue, options, rowObject){
	var str = "";
    	str += "<button class=\"btn sr3 st3 btn-popup\" onclick=\"fnOpenExecutePopup('" + rowObject.tableId + "', '" + rowObject.dataConnScd + "')\">실행</button>";    
    return str;
}


	
/* 실행 팝업 */
function fnOpenExecutePopup(tableId, dataConnScd) {
   	$("#EXEC_TABLE_ID").val(tableId);
   	$("#EXEC_DATA_CONN_SCD").val(dataConnScd);
   	
	$(".pop-box1").show();
}



/* manual 실행 */
function fnExcuteManualUpload() {

	$(".pop-box1").hide();
	$(".pop-box2").show();
	
	$.ajax({
	    url: "/data/excuteSystemLinkManualUpload.do",
	    type : "GET",
	    dataType: 'text',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: { 'tableId': $("#EXEC_TABLE_ID").val(), 'dataConnScd': $("#EXEC_DATA_CONN_SCD").val() },
	    success: function(response) {
	    	//alert(response.resultMsg);
	        $(".pop-box2").hide();
	    	alert("처리되었습니다. 데이터 업로드 로그를 확인해주세요.");
	    	$('#Grid').jqGrid('GridUnload');
	    	gloObj.fnGetSystemLinkList();
        },
        error : function(request, status, error) { 
        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        	
	        $(".pop-box2").hide();        	
        	if (request.status != "200") {
        		alert("실행 중 오류가 발생하였습니다. 로그를 확인해 주시기 바랍니다.");
        		$('#Grid').jqGrid('GridUnload');
        		gloObj.fnGetSystemLinkList();
        	}
    	}
	}).done(function() { 
		//console.log("done");
	});
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
					<span class="now">시스템 연계 관리</span>
				</div>
				<div class="clearfix">
					<p class="tit-page">시스템 연계 관리</p>
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
	
				<!-- 표 상단 설정 영역 -->
				<div class="tbl-top">
					<form id='searchVO' method="post" name='searchVO'>
						<fieldset>
						<legend>표,게시판 정보</legend>
							<div class="left">							
								<label>
									<select id="dataConnScd" class="ip sr1 st1 w1">
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
					<p class="tit">수동 업로드 알림</p>
					<div class="cont">
					
									    <input type="hidden" id="EXEC_TABLE_ID" />
									    <input type="hidden" id="EXEC_DATA_CONN_SCD" />
									     테이블에 따라 약 1분~10분이상 소요될 수 있습니다. <br>
									     진행이 완료될 때까지 기다리시기 바랍니다.
		
						<div class="btn-wrap mgb0">
							<a href="#" id="btnUpload" class="btn sr2 st2">수동업로드 (연계시작)</a>	
						</div>				
					</div>
					<a href="#" class="btn-close pop-close">닫기</a><!--[d] "pop-close" 클래스 값을 클릭할 시 팝업 닫힘-->
				</div>
			</div>
			<!-- 모달 팝업 -->
			
			<!-- 모달 팝업2 -->
			<div class="modal-pop pop-box2">
				<div class="layer-pop" style="width:530px;">
					<p class="tit">수동업로드 진행중 </p>
					<div class="cont" style="text-align:center">
					
								<img alt="로딩" src="/images/cmm/img-loading.gif"/> <br><br>
								수동업로드가 종료될 때 까지 기다려주시기 바랍니다. <br>
								테이블에 따라 많게는 10분이상 소요될 수 있습니다.

					</div>
				
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