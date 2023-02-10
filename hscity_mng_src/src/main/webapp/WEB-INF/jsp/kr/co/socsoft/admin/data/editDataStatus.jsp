<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/head.jsp" %>
<script>
$(document).ready(function() {
	/* 콤보박스 : 테이블명 */
	$.ajax({
	    url: "/data/getGyeonggiDataLinkTableList.do",
	    type : "GET",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    success: function(response) {
	    	if (response.length != 0) {
	    		var html = "<option value='' selected>-선택-</option>";
	    		for (var i = 0; i < response.length; i++) {
	                html += "<option value='" + response[i].tableId + "'>" + response[i].tableNm + "</option>";
	            }
	            $("#tableId").html(html);
	    	}
	    	
	    },
        error : function(request, status, error) { 
        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	}
	});
	
	/* 콤보박스 : 연계방법 */
	$.ajax({
	    url: "/code/getCommCode.do",
	    type : "GET",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: {groupCd: "data_conn_scd", sortCol: "detail_cd", sort: "asc"},
	    success: function(response) {
	    	if (response.length != 0) {
	    		var html = "<option value='' selected>-선택-</option>";
	            for (var i=0; i<response.length; i++) {
	                html += "<option value='" + response[i].cd + "'>" + response[i].nm + "</option>";
	            }
	            $("#dataConnScd").html(html);
	    	}
	    	$("#dataConnScd").val("${dataVo.dataConnScd}");
	    	if ($("#dataConnScd").val() == 'excel') {
	    		$('#uploadTr').css("display", "");	    		
	    	}
	    },
        error : function(request, status, error) { 
        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	}
	});
	
	/* 콤보박스 : Data Type */
	$.ajax({
	    url: "/code/getCommCode.do",
	    type : "GET",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: {groupCd: "data_type_scd", sortCol: "detail_cd", sort: "asc"},
	    success: function(response) {
	    	if (response.length != 0) {
	    		var html = "<option value='' selected>-선택-</option>";
	            for (var i=0; i<response.length; i++) {
	                html += "<option value='" + response[i].cd + "'>" + response[i].nm + "</option>";
	            }
	            $("#dataTypeScd").html(html);
	    	}
	    	$("#dataTypeScd").val("${dataVo.dataTypeScd}");
	    },
        error : function(request, status, error) { 
        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	}
	});
	
	/* 콤보박스 : 관리 기준년월일 */
	$.ajax({
	    url: "/code/getCommCode.do",
	    type : "GET",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: {groupCd: "mng_std_ymd_scd", sortCol: "detail_cd", sort: "asc"},
	    success: function(response) {
	    	if (response.length != 0) {
	    		var html = "<option value='' selected>-선택-</option>";
	            for (var i=0; i<response.length; i++) {
	                html += "<option value='" + response[i].cd + "'>" + response[i].nm + "로 관리</option>";
	            }
	            $("#mngStdYmdScd").html(html);
	    	}
	    	$("#mngStdYmdScd").val("${dataVo.mngStdYmdScd}");
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
	    		var html = "<option value='' selected>-선택-</option>";
	            for (var i=0; i<response.length; i++) {
	                html += "<option value='" + response[i].cd + "'>" + response[i].nm + "</option>";
	            }
	            $("#dataUpdPerScd").html(html);
	    	}
	    	$("#dataUpdPerScd").val("${dataVo.dataUpdPerScd}");
	    },
        error : function(request, status, error) { 
        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	}
	});
	
	/* 콤보박스 : db테이블 분리여부 */
	$.ajax({
	    url: "/code/getCommCode.do",
	    type : "GET",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: {groupCd: "split_table_scd", sortCol: "detail_cd", sort: "asc"},
	    success: function(response) {
	    	if (response.length != 0) {
	    		var html = "<option value='' selected>-선택-</option>";
	            for (var i=0; i<response.length; i++) {
	                html += "<option value='" + response[i].cd + "'>" + response[i].nm + "</option>";
	            }
	            $("#splitTableScd").html(html);
	    	}
	    	$("#splitTableScd").val("${dataVo.splitTableScd}");
	    },
        error : function(request, status, error) { 
        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	}
	});
	
	$('#gisType').on('change', function(event) {
		$('#dataTypeScd').val("");
    });
	
	$('#dataConnScd').on('change', function(event) {
		if (this.value == 'excel') {
			$('#uploadTr').css("display", "");			
		} else {
			$('#uploadTr').css("display", "none");
		}
    });
	
	$('#btnSearchUser').on('click', function(event) {
		$('#searchUserGrid').jqGrid('GridUnload');
		fnGetUserList();		
	});
});

/* 사용자찾기 팝업 */
function fnSearchUserPop() {
	fnGetUserList();
	$("#popSearchUser").show();	
}

/* 컬럼정보 편집 팝업 */
function fnColumnEditPop() {
	fnGetDataTableColumnList();
	$("#popColumnEdit").show();	
}

/* 사용자 조회 */
function fnGetUserList() {
	const param = {'idOrName': $('#idOrName').val(), 'keyword': $('#keyword').val()};
	$.ajax({
	    url: "/permission/getUserPermissionList.do",
	    type : "POST",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: JSON.stringify(param),
	    success: function(response) {
		    	theGrid.jqGrid({
	   	        datatype: 'local',
	   	        data: response ,
	   	        colNames: ['부서1',  '부서2', '아이디', '이름', '연락처', '선택'],
	   	        colModel: [                  
  		    		        {name: 'upperDeptName', index: 'upperDeptName', width: 70, align: 'center'},
  		    		        {name: 'deptName', index: 'deptName', width: 70 , align: 'center'},
  		    		        {name: 'userId', index: 'userId', width: 50, align: 'center'},
  		    		        {name: 'userName', index: 'userName', width: 50, align: 'center'},
  		    		        {name: 'mobile', index: 'mobile', width: 70, align: 'center'},
  		    		        {name: '', index: '', width: 40, align: 'center', formatter:formatOpt1, fixed:false}
	   		        	  ],
	   	        autowidth: true,
	   	        gridview: true,    
	   	        rownumbers: true,
	   	        rowNum: 15,
	   	        //rowList: [5, 10, 15],
	   	        //pager: '#searchUserGridPager',
	   	        sortname: 'Date',
	   	        sortorder: 'asc',
	   	        viewrecords: true,  
	   	        height: '300',
	   	        width: 'auto',
	   	        gridComplete :  function () {
	   		                        var maxDate; 
	   		                        var rowIDs = jQuery("#searchUserGrid").jqGrid('getDataIDs');
	
	   		                        for (var i = 0; i < rowIDs.length ; i++)  {
	   		                            var rowID = rowIDs[i];
	   		                            var row = jQuery('#searchUserGrid').jqGrid ('getRowData', rowID);
	
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

    theGrid = $("#searchUserGrid"),
    numberTemplate = {formatter: 'number', align: 'right', sorttype: 'number'};
}

/* 컬럼정보 조회 */
function fnGetDataTableColumnList() {
	const param = {'tableId': $('#tableId').val()};
	$.ajax({
	    url: "/data/getDataTableColumnList.do",
	    type : "POST",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: JSON.stringify(param),
	    success: function(response) {
		    	theGrid.jqGrid({
	   	        datatype: 'local',
	   	        data: response ,
	   	        colNames: ['컬럼순서', '컬럼ID', '컬럼이름', '설명', '원본 컬럼여부', '사용여부', 'GIS 설정', 'tableId', 'crud'],
	   	        colModel: [                  
	   	        	        {name: 'columnSeq', index: 'columnSeq', width: 40, editable: true, align: 'center', hidden:true},
	   	        	        {name: 'columnId', 
	   	        	         index: 'columnId', 
	   	        	         width: 120, 
	   	        	         editable: true, 
	   	        	         align: 'center'
	   	        	        },
  		    		        {name: 'columnNm', index: 'columnNm', width: 120 , editable: true, align: 'center'},
  		    		        {name: 'description', index: 'description', width: 120, editable: true, align: 'center'},
  		    		        {name: 'stdFormUseYn', index: 'stdFormUseYn', width: 60, align: 'center', editable: true, edittype:"select", formatter:"select", editoptions:{value:"y:있음;n:없음"}},
  		    		        {name: 'useYn', index: 'useYn', width: 60, align: 'center', editable: true, edittype:"select", formatter:"select", editoptions:{value:"y:사용;n:미사용"}},
  		    		        {name: 'gisType', index: 'gisType', width: 60, align: 'center', editable: true, edittype:"select", formatter:"select", editoptions:{value:"x:X;logt:경도;lat:위도"}},
  		    		        {name: 'tableId', index: 'tableId', width: 60, align: 'center', hidden:true},
  		    		        {name: 'crud', index: 'crud', width: 60, align: 'center', hidden:true}
	   		        	  ],
	   	        autowidth: true,
	   	        gridview: true,             
	   	        rownumbers: true,
	   	        rowNum: 100,
	   	        multiselect : true,
	   	        sortname: 'Date',
	   	        sortorder: 'asc',
	   	        caption: "테이블 컬럼 정보",
	   	        loadtext : '로딩중...',
	   	        cellEdit: true,   
	   	        viewrecords: true,  
	   	        height: '530',
	   	        width: 'auto',
	   	        gridComplete :  function () {
	   		                        var maxDate; 
	   		                        var rowIDs = jQuery("#columnEditGrid").jqGrid('getDataIDs');
	
	   		                        for (var i = 0; i < rowIDs.length ; i++)  {
	   		                            var rowID = rowIDs[i];
	   		                            var row = jQuery('#columnEditGrid').jqGrid ('getRowData', rowID);
	
	   		                            if(i==0){
	   		                                maxDate = new Date(row.orderdate);
	   		                            } else {
	   		                                if(maxDate < new Date(row.orderdate)) {   
	   		                                	maxDate = new Date(row.orderdate);
	   		                                }                                       
	   		                            }       
	   		                        }
	   		                        $("#maxDateField").val(maxDate);
	   	        },
	   	     	onCellSelect: function(id){
	   	     		var rowId = $("#columnEditGrid").jqGrid("getGridParam", "selrow");
			   	}
	   	    });
    	}, error : function(request, status, error) { 
        		console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	}
	}).done(function() { 
		console.log("done");
		//theGrid.trigger("reloadGrid");
	}),

    theGrid = $("#columnEditGrid"),
    numberTemplate = {formatter: 'number', align: 'right', sorttype: 'number'};
}

/* 테이블정보 편집 저장 */
function fnSaveDataTableInfo() {
	if ($("#dataGubunNm").val() == "") { alert("구분은 필수입력 항목입니다."); $("#dataGubunNm").focus(); return;}
	if ($("#tableNm").val() == "") { alert("데이터 이름은 필수입력 항목입니다."); $("#tableNm").focus(); return;}
	if ($("#tableId").val() == "") { alert("DB 테이블 이름은 필수입력 항목입니다."); $("#tableId").focus(); return;}
	if ($("#gisType").val() == "") { alert("GIS여부는 필수입력 항목입니다."); $("#gisType").focus(); return;}
	if ($("#dataConnScd").val() == "") { alert("데이터 연계방법은 필수입력 항목입니다."); $("#dataConnScd").focus(); return;}
	if ($("#mngStdYmdScd").val() == "") { alert("관리 기준년월일은 필수입력 항목입니다."); $("#mngStdYmdScd").focus(); return;}
	if ($("#splitTableScd").val() == "") { alert("DB테이블 분리여부는 필수입력 항목입니다."); $("#splitTableScd").focus(); return;}
	if ($("#dataUpdPerScd").val() == "") { alert("데이터 갱신단위는 필수입력 항목입니다."); $("#dataUpdPerScd").focus(); return;}
	if ($("#confirmTableYn").val() != "y") {
		alert("존재하는 테이블이 아니므로 사용할 수 없습니다.");
		return;
	}
	
	if ($("#confirmTableId").val() != $("#tableId").val()) {
		alert("검증된 테이블명과 일치하지 않습니다.");
		return;
	}
	
	if (confirm('저장하시겠습니까?')) {
		var saveData = {
				dataGubunNm: $("#dataGubunNm").val(), 
				tableNm: $("#tableNm").val(),
				tableId: $("#tableId").val(),
				description: $("#description").val(),
				dataTypeScd: $("#dataTypeScd").val(),
				dataConnScd: $("#dataConnScd").val(),
				openapiUrl: $("#openapiUrl").val(),
				mngStdYmdScd: $("#mngStdYmdScd").val(),
				insertStdYmdYn: $("#insertStdYmdYn").val(),
				splitTableScd: $("#splitTableScd").val(),
				dataUpdPerScd: $("#dataUpdPerScd").val(),
				oriDataOwner: $("#oriDataOwner").val(),
				oriDataMngNm: $("#oriDataMngNm").val(),
				oriDataMngTel: $("#oriDataMngTel").val(),
				oriDataMngEmail: $("#oriDataMngEmail").val(),
				rmk: $("#rmk").val(),
				useYn: $("#useYn").val()
				};
		
		var dataUploadUser = [];
		for (var i=0; i<tmpDataUploadUser.length; i++) {
			if (typeof tmpDataUploadUser[i] == 'object') {
				dataUploadUser.push({tableId: $("#tableId").val(), uploadUserId: tmpDataUploadUser[i].uploadUserId});
			}
		}
		$.ajax({
		    url: "/data/saveDataTableInfo.do",
		    type : "POST",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    data: JSON.stringify({'saveData': saveData, 'dataUploadUser': dataUploadUser}),
		    success: function(response) {
		    	console.log(response.isOk);
		    	if (response.isOk == "ok") {
		    		alert("정상적으로 저장되었습니다.");
		    		window.location =  '/data/status/dataStatusList.do';
		    	}
	        },
	        error : function(request, status, error) { 
	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    	}
		}).done(function() { 
			console.log("done");
		});
	}
}

/* 테이블정보 검증 */
function fnConfirmTableExistYn() {
	var crud = "${dataVo.crud}";
	if ($('#tableId').val() == "") {
		alert("테이블 이름을 입력하세요.");
		return;
	}
	if (crud == "U") {
		alert("이미 검증된 테이블입니다.");
		return;
	}
	
	
	$.ajax({
	    url: "/data/getConfirmTableExistYn.do",
	    type : "POST",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: JSON.stringify({'tableId': $('#tableId').val()}),
	    success: function(response) {
	    	if (crud != "U") {
	    		if (response.confirmTableYn == 'not_table') {
	    			alert("존재하는 테이블이 아니므로 사용할 수 없습니다.");
	    			$("#confirmTableYn").val("n");
	    		} else if (response.confirmTableYn == 'used_table') {
	    			alert("사용중인 테이블이므로 사용할 수 없습니다.");
	    			$("#confirmTableYn").val("n");
	    		} else {
	    			alert("사용가능한 테이블입니다.");
	    			$("#confirmTableYn").val("y");
	    			$("#confirmTableId").val($('#tableId').val());
	    		}
	    	}
        },
        error : function(request, status, error) { 
        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	}
	}).done(function() { 
		console.log("done");
	});
}

function fnAddColumn() {
	//var rowId = $('#columnEditGrid').jqGrid('getRowData');
	var data = [{columnSeq: '', columnId: '', columnNm: '', description: '', stdFormUseYn: 'n', useYn: 'y', gisType: 'x', tableId:$("#tableId").val(), crud: 'c'}];
	$("#columnEditGrid").jqGrid('addRowData', 'last', data);
}

var deleteRowList = [];
function fnRemoveColumn() {
	var $grid = $("#columnEditGrid"), selIds = $grid.getGridParam("selarrrow"), i, n, cellValues = [];
	for (var i = selIds.length; i >= 0; i--) {
		if ($grid.jqGrid("getCell", selIds[i], "crud") == "r") {
			deleteRowList.push({columnSeq: $grid.jqGrid("getCell", selIds[i], "columnSeq")
				              , columnId: $grid.jqGrid("getCell", selIds[i], "columnId")
				              , tableId:$("#tableId").val()
				              , crud: 'd'});
		}
		$('#columnEditGrid').delRowData(selIds[i]);
	}
	//console.log(deleteRowList);
}

/* 컬럼정보 편집 저장 */
function fnSaveDataTableColumn() {
	// 포커스 아웃
	$("#columnEditGrid").jqGrid("editCell", 0, 0, false);
	
	var $grid = $("#columnEditGrid");
	var saveDataList = $grid.jqGrid("getRowData");
	//console.log(saveDataList);
	var colIdChk = 0;
	var colNmChk = 0;
	for (var i=0; i<saveDataList.length; i++) {
		if (saveDataList[i].columnId == "") colIdChk++;
		if (saveDataList[i].colNmChk == "") colNmChk++;
	}
	
	if (colIdChk > 0) {
		alert("컬럼 ID는 반드시 입력해야 합니다.");
		return;
	}
	
	if (colNmChk > 0) {
		alert("컬럼이름은 반드시 입력해야 합니다.");
		return;
	}
	//console.log(deleteRowList);
	if (confirm('저장하시겠습니까?')) {
		$.ajax({
		    url: "/data/saveDataTableColumn.do",
		    type : "POST",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    data: JSON.stringify({'saveDataList': saveDataList, 'deleteRowList':deleteRowList}),
		    success: function(response) {
		    	console.log(response.isOk);
		    	if (response.isOk == "ok") {
		    		deleteRowList = [];
		    		alert("정상적으로 저장되었습니다.");
		    		$('#columnEditGrid').jqGrid('GridUnload');
		    		fnGetDataTableColumnList();	
		    	}
	        },
	        error : function(request, status, error) { 
	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    	}
		}).done(function() { 
			console.log("done");
		});
	}
}

/* 사용자 선택 버튼 */
function formatOpt1(cellvalue, options, rowObject){
	var str = "";
    	str += "<button class=\"btn sr3 st3 btn-popup\" onclick=\"fnSetDataUploadUser('" + rowObject.userId + "', '" + rowObject.userName + "', '" + rowObject.deptName + "')\">선택</button>";
    return str;
}

var tmpDataUploadUser = [];
/* 데이터 업로드 담당자 셋팅 */
function fnSetDataUploadUser(userid, username, deptname) {
	var chkCnt = 0;
	$("input[name=dataUploadUserId]").each(function(idx){   
        var value = $(this).val();
        var eqValue = $("input[name=dataUploadUserId]:eq(" + idx + ")").val() ;
        if (userid == eqValue) {
        	chkCnt++;
        }
        
    });
	
	if (chkCnt == 0) {
    	var delBtn = '<a href="#" onclick="fnDeleteDataUloadUser(\'c\', \'' + userid + '\', \'' + $('#tableId').val() + '\')"><img src="<c:url value="/images/cmm/btn-delete.gif"/>" alt="삭제" title="삭제"/></a>';
    	$("#popSearchUser").hide();	
    	$("#dbUpldUserId").val(userid);
    	var html = $("#uploadUserInfo").html();
    	$("#uploadUserInfo").html(html + "<div id=\'" + userid + "\' style='float:left'>(" + deptname + ") " + username + delBtn + "</div>");
    	tmpDataUploadUser.push({tableId: $("#tableId").val(), uploadUserId: userid});
    } else {
    	alert("존재하는 사용자입니다.");
    }
}

function fnDeleteDataUloadUser(crud, userId, tableId) {
	if (crud == "r") {
		if (confirm("담당자를 삭제하시겠습니까?")) {
			$.ajax({
			    url: "/data/deleteDataUploadUser.do",
			    type : "POST",
			    dataType: 'json',
			    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
			    data: JSON.stringify({'tableId': tableId, 'uploadUserId':userId}),
			    success: function(response) {
			    	console.log(response.isOk);
			    	location.reload();
		        },
		        error : function(request, status, error) { 
		        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		    	}
			}).done(function() { 
				console.log("done");
			});
		}
	} else {
		for (var i=0; i <tmpDataUploadUser.length; i++) {
	        if (typeof tmpDataUploadUser[i] == 'object' && tmpDataUploadUser[i].uploadUserId == userId) {
	        	delete tmpDataUploadUser[i];
	        }
    	}
		$("#" + userId).remove();
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
					<span class="path">데이터 현황</span>&nbsp;&nbsp;&gt;
					<span class="now">데이터 현황 편집</span>
				</div>
				<div class="clearfix">
					<p class="tit-page">데이터 현황 편집(등록)</p>
				</div>
			</div>
			<!-- //상단 경로 및 페이지 타이틀 -->

			<!-- 컨텐츠 영역 -->
			<div class="cont">					
				<!-- 표 상단 설정 영역 -->
				<div class="tbl-top">
					<div class="left">
						<span class="star">필수</span>표시는 필수입력 항목입니다.
					</div>						
					<div class="right">
						<a href="/data/status/dataStatusList.do" class="btn sr2 st2">목록보기</a>	
						<c:if test="${dataVo.crud eq 'U'}"><a href="#" class="btn sr2 st4" onclick="fnColumnEditPop();">컬럼정보 편집</a></c:if>	
						<a href="#" class="btn sr2 st5" onclick="fnSaveDataTableInfo();">저장</a>	
					</div>					
				</div>
				<!--// 표 상단 설정 영역 -->
				<form id="form1" name="form1" method="POST">
				<table class="tbl sr1 st1">
					<caption class="hide">대시민 메뉴화면 편집 표</caption>
					<colgroup>
						<col style="width:20%"/>
						<col style="width:auto"/>
					<tbody>
						<tr>
							<th scope="row" class="th2"><span class="star">필수</span>구분</th>
							<td class="align-l">
								<label>
									<select class="ip sr1 st1 w2" id="crud" disabled>
										<option value="C" <c:if test="${dataVo.crud eq 'C'}">selected</c:if>>신규입력</option>
										<option value="U" <c:if test="${dataVo.crud eq 'U'}">selected</c:if>>편집수정</option>
									</select>
								</label>
								<label>
									<input type="text" class="ip sr1 st1 w2" id="dataGubunNm" value="${dataVo.dataGubunNm}" maxlength="30" />
								</label>									
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">
								<label for="tableNm"><span class="star">필수</span>데이터 이름</label>	
							</th>
							<td class="align-l">
								<input type="text" class="ip sr1 st1 w3" id="tableNm" value="${dataVo.tableNm}" maxlength="50" />	
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">
								<label for="tableId"><span class="star">필수</span>DB 테이블 이름</label>
							</th>
							<td class="align-l">
								<span><a href="#" class="btn sr2 st3" onclick="fnConfirmTableExistYn();">테이블 검증</a></span>
								<input type="hidden" id="confirmTableYn" value="${dataVo.confirmTableYn}">
								<input type="hidden" id="confirmTableId" value="${dataVo.confirmTableId}">
								<input type="text" id="tableId" class="ip sr1 st1 w3" value="${dataVo.tableId}" style="width:670px" <c:if test="${dataVo.crud eq 'U'}">readonly</c:if> maxlength="50" />
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">
								<label for="description">상세내용</label>
							</th>
							<td class="align-l">
								<input type="text" class="ip sr1 st1 w3" id="description" value="${dataVo.description}" maxlength="300" />	
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">
								<span class="star">필수</span>GIS 여부
							</th>
							<td class="align-l">
								<label>
									<select class="ip sr1 st1 w2" id="gisType">
										<option value="gis" <c:if test="${dataVo.gisType eq 'gis'}">selected</c:if>>GIS</option>
										<option value="nor" <c:if test="${dataVo.gisType eq 'nor'}">selected</c:if>>일반</option>
									</select>
								</label>
								<label>
									<select class="ip sr1 st1 w2" id="dataTypeScd">
									</select>
								</label>
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">
								<span class="star">필수</span>데이터 연계방법
							</th>
							<td class="align-l">
								<label>
									<select class="ip sr1 st1 w2" id="dataConnScd">
									</select>
								</label>
								<label for="openapiUrl" class="label">연계 URL:</label> 
								<input type="text" class="ip sr1 st1 w4" placeholder="경기데이터 드림일 경우 연계 URL을 입력하세요." id="openapiUrl" value="${dataVo.openapiUrl}" maxlength="300" />
							</td>
						</tr>
						<!--  
						<tr>
							<th scope="row" class="th2">
								<label for="dataFilePath">대용량파일업로드경로</label>
							</th>
							<td class="align-l">
								<input type="text" class="ip sr1 st1 w4" placeholder="대용량파일업로드경로를 입력하세요." id="dataFilePath" value="${dataVo.dataFilePath}" maxlength="300" />
							</td>
						</tr>
						-->
						<tr>
							<th scope="row" class="th2">
								<label for="mngStdYmdScd"><span class="star">필수</span>관리 기준년월일</label>
							</th>
							<td class="align-l">
								<select class="ip sr1 st1 w2" id="mngStdYmdScd">
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">
								<label for="insertStdYmdYn"><span class="star">필수</span>기준년월(일) 입력여부</label>
							</th>
							<td class="align-l">
								<select class="ip sr1 st1 w2" id="insertStdYmdYn">
									<option value="y" <c:if test="${dataVo.insertStdYmdYn eq 'y'}">selected</c:if>>기준년월 입력</option>
									<option value="n" <c:if test="${dataVo.insertStdYmdYn eq 'n'}">selected</c:if>>기준년월 미입력</option>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">
								<label for="splitTableScd"><span class="star">필수</span>DB테이블 분리</label>
							</th>
							<td class="align-l">
								<select class="ip sr1 st1 w2" id="splitTableScd">
								</select>
								※ 대용량 데이터의 경우 기준에 따라 DB테이블을 분리하여 관리
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">
								<label for="dataUpdPerScd"><span class="star">필수</span>데이터 갱신단위</label>
							</th>
							<td class="align-l">
								<select class="ip sr1 st1 w1" id="dataUpdPerScd">
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">
								<label for="oriDataOwner">원본데이터 보유부서 및 기관</label>
							</th>
							<td class="align-l">
								<input type="text" class="ip sr1 st1 w3" id="oriDataOwner" value="${dataVo.oriDataOwner}" maxlength="30" />
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">
								<label for="oriDataMngNm">원본데이터 담당자</label>
							</th>
							<td class="align-l">
								<input type="text" class="ip sr1 st1 w3" id="oriDataMngNm" value="${dataVo.oriDataMngNm}" maxlength="10" />
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">
								<label for="oriDataMngTel">원본데이터 담당자 연락처</label>
							</th>
							<td class="align-l">
								<input type="text" class="ip sr1 st1 w3" id="oriDataMngTel" value="${dataVo.oriDataMngTel}" maxlength="30" />
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">
								<label for="oriDataMngEmail">담당자 이메일</label>
							</th>
							<td class="align-l">
								<label>
									<input type="text" class="ip sr1 st1 w2" id="oriDataMngEmail" value="${dataVo.oriDataMngEmail}" maxlength="30" />
								</label> 
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">
								<label for="rmk">비고</label>
							</th>
							<td class="align-l">
								<input type="text" id="rmk" class="ip sr1 st1 w3" value="${dataVo.rmk}" maxlength="150" />
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">
								<label for="useYn"><span class="star">필수</span>사용여부</label>
							</th>
							<td class="align-l">
								<select id="useYn" class="ip sr1 st1 w1">
									<option value="y" <c:if test="${dataVo.useYn eq 'y'}">selected</c:if>>사용함</option>
									<option value="n" <c:if test="${dataVo.useYn eq 'n'}">selected</c:if>>사용안함</option>
								</select>
							</td>
						</tr>
						<tr id="uploadTr" style="display:<c:if test="${dataVo.dataConnScd ne 'excel'}">none</c:if>;">
							<th scope="row" class="th2">
								<span class="star">필수</span>데이터업로드 담당자
							</th>
							<td class="align-l">
								<div style="vertical-align: middle;float:left">
									<span><a href="#" class="btn sr2 st3" onclick="fnSearchUserPop();">사용자 찾기</a></span>
								</div>
								<div>
									<ul class="file-list">
										<li>
											<c:if test="${fn:length(dataUploadUserList) > 0}">
												<c:forEach var="list" items="${dataUploadUserList}" varStatus="status">
													(${list.uploadUserDeptName})${list.uploadUserName}
		                                            <a href="#" onclick="fnDeleteDataUloadUser('r', '${list.uploadUserId}', '${list.tableId}')"><img src="<c:url value="/images/cmm/btn-delete.gif"/>" alt="삭제" title="삭제"/></a>
		                                            <input type="hidden" id="dataUploadUserId" name="dataUploadUserId" value="${list.uploadUserId}">
												</c:forEach>
											</c:if>
											<span id="uploadUserInfo"></span>
										</li>
									</ul>
									<!-- <input type="hidden" id="dbUpldUserId" class="ip sr1 st1 w3" /> -->
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				</form>
			</div>
			<!-- //컨텐츠 영역 -->
			<!-- 모달 팝업:: 사용자 찾기 -->
			<div class="modal-pop pop-box2" id="popSearchUser">
				<div class="layer-pop" style="width:600px;height:500px;">
					<p class="tit">사용자 조회</p>
					<div class="cont">
						<!-- 표 상단 설정 영역 -->
						<div class="tbl-top">
							<form method="post" action="#">
								<fieldset>
								<legend>표,게시판 정보</legend>
									<div class="left">
									    <label>
											<select id="idOrName" class="ip sr1 st1 w1">
												<option value="userId" selected>아이디</option>
												<option value="userName">이름</option>
											</select>
										</label>							
										<label>
											<input type="text" class="ip sr1 st1 w2" id="keyword"/>
										</label>
										<input type="button" class="btn sr2 st2" id="btnSearchUser" value="조회"/>
									</div>		
								</fieldset> 
							</form>
						</div>
						<!-- //표 상단 설정 영역 -->
						<table id="searchUserGrid">
						</table>
					</div>
					<a href="#" class="btn-close pop-close">닫기</a><!--[d] "pop-close" 클래스 값을 클릭할 시 팝업 닫힘-->
				</div>
			</div>
			<!-- 모달 팝업 -->
			
			<!-- 모달 팝업:: 컬럼정보 편집 -->
			<div class="modal-pop pop-box2" id="popColumnEdit">
				<div class="layer-pop" style="width:900px;height:680px;">
					<p class="tit">컬럼정보 편집</p>
					<div class="cont">
						<!-- //표 상단 설정 영역 -->
						<table id="columnEditGrid">
						</table>
					</div>
					<div style="text-align:right;padding-right:20px;">
						<a href="#" class="btn sr2 st5" onclick="fnAddColumn();">컬럼추가</a>
						<a href="#" class="btn sr2 st5" onclick="fnRemoveColumn();">컬럼삭제</a>
						<a href="#" class="btn sr2 st5" onclick="fnSaveDataTableColumn();">저장</a>
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