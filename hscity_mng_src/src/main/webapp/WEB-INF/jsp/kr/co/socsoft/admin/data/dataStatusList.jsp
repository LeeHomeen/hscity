<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/head.jsp" %>
<script>
var gloObj = null;
$(document).ready(function() {
	/* 콤보박스 : 연계방법 */
	$.ajax({
	    url: "/code/getCommCode.do",
	    type : "GET",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: {groupCd: "data_conn_scd", sortCol: "detail_cd", sort: "asc"},
	    success: function(response) {
	    	if (response.length != 0) {
	    		var html = "<option value='' selected>-연계방법-</option>";
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
	
	// 엑셀 다운로드
	$('#btnExcel').on('click', function(){
		
		console.log($('#searchVO').serializeObject());
	    if(confirm('데이터를 엑셀로 내려받으시겠습니까?\n데이터 건수가 많을 경우 오래 걸릴 수 있습니다.')) {
	        // var totCount = $grid.getGridParam("records");
	        CM.downloadFileWithIFrame($('body'), '<c:url value="/data/getDataStatusExcel.do"/>', $('#searchVO').serializeObject());
	    }
	});
	
	$('#searchText').keydown(function (key) {
		if(key.keyCode == 13){
			$('#Grid').jqGrid('GridUnload');
			gloObj.fnGetDataStatusList();
		}
    });
	
	$('#btnSearch').on('click', function(event) {
		$('#Grid').jqGrid('GridUnload');
		gloObj.fnGetDataStatusList();
    });
	
	$('#btnRegist').on('click', function(event) {
		fnOpenEditPage('');
    });
	
	gloObj = {
			fnGetDataStatusList: function() {
				const param = {'dataConnScd': $('#dataConnScd').val()
						     , 'dataUpdPerScd': $('#dataUpdPerScd').val()
						     , 'searchType': $('#searchType').val()
						     , 'searchText': $('#searchText').val()};
		    	$.ajax({
		    	    url: "/data/getDataStatusList.do",
		    	    type : "POST",
		    	    dataType: 'json',
		    	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    	    data: JSON.stringify(param),
		    	    success: function(response) {
		    	    	//console.log(response);
		    		    		theGrid.jqGrid({
		    		    	        datatype: 'local',
		    		    	        data: response ,
		    		    	        colNames: ['구분', '테이블ID',  '테이블명', 'GIS', '적재방법', '갱신주기', '상태', '편집', '최종갱신일'],
		    		    	        colModel: [                  
				    		    		        {name: 'dataGubunNm', index: 'dataGubunNm', width: 70, align: 'left'},
				    		    		        //{name: 'tableId', index: 'tableId', width: 80, align: 'center'},
		    		    	        	        //{name: 'tableNm', index: 'tableNm', width: 80, align: 'center'},
		    		    	        	        {name: '', index: '', width: 80, align: 'left', formatter:formatOpt1, fixed:false},
		    		    	        	        {name: '', index: '', width: 80, align: 'left', formatter:formatOpt2, fixed:false},
				    		    		        {name: 'dataTypeScdNm', index: 'dataTypeScdNm', width: 30, align: 'center'},
				    		    		        {name: 'dataConnScdNm', index: 'dataConnScdNm', width: 40 , align: 'center'},
				    		    		        {name: 'dataUpdPerScdNm', index: 'dataUpdPerScdNm', width: 25, align: 'center'},
				    		    		        {name: 'status', index: 'status', width: 45, align: 'center'},
				    		    		        {name: '', index: '', width: 50, align: 'center', formatter:formatOpt3, fixed:true},
				    		    		        {name: 'lastUploadDt', index:'lastUploadDt', width:70, align:'left'}
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
	gloObj.fnGetDataStatusList();
});

/* 편집 버튼 */
function formatOpt1(cellvalue, options, rowObject){
	var str = "";
    	str += "<span onclick=\"fnOpenEditPage('" + rowObject.tableId + "')\"><a href='#'>" + rowObject.tableId + "</a></span>";    
    return str;
}

function formatOpt2(cellvalue, options, rowObject){
	var str = "";
    	str += "<span onclick=\"fnOpenEditPage('" + rowObject.tableId + "')\"><a href='#'>" + rowObject.tableNm + "</a></span>";    
    return str;
}

function formatOpt3(cellvalue, options, rowObject){
	var str = "";
    	str += "<button class=\"btn sr3 st3 btn-popup\" onclick=\"fnOpenEditPage('" + rowObject.tableId + "')\">편집</button>";    
    return str;
}

/* 편집창 gogo */
function fnOpenEditPage(tableId) {
	var form = document.form1;
	form.tableId.value = tableId;
	if (tableId == '') {
		//url =  '/data/status/editDataStatus.do?tableId=&crud=C';//신규
		form.crud.value = "C";
	} else {
		//url =  '/data/status/editDataStatus.do?tableId=' + tableId + '&crud=U';//update
		form.crud.value = "U";
	}
	
	form.action = '/data/status/editDataStatus.do';
	form.submit();
}
</script>
<body>
<form id='form1' name='form1' method="post">
<input type="hidden" id="tableId" name="tableId" />
<input type="hidden" id="crud" name="crud" />
</form>
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
					<span class="now">데이터 현황 조회</span>
				</div>
				<div class="clearfix">
					<p class="tit-page">데이터 현황 조회</p>
					<ul class="btn-wrap">
						<li>
							<button class="btn sr3 st3" id="btnRegist">
								<i class="fa" aria-hidden="true"></i>새로만들기
							</button>
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
						<legend>표,게시판 정보</legend>
							<div class="left">
							    <label>
									<select id="dataConnScd" name="dataConnScd" class="ip sr1 st1 w1">
									</select>
								</label>							
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
		</div>
		<!-- //sec-right -->
	</div>
	<!-- //container -->
</div>
<!-- //wrap -->
</body>
</html>