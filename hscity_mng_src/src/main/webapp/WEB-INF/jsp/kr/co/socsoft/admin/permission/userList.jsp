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

var targetUserId = '';
function openPopUp(){
	 $('#pwd1').val('');
	 $('#pwd2').val('');
  	 	$("body").css({
			"overflow":"hidden",
			"margin-right":"18px" 
		});
	$(".pop-box1").show();		
		
   }
function save(){
	if($('#pwd1').val() != $('#pwd2').val()) {
		alert('비밀번호와 비밀번호 확인의 값이 같지 않습니다.');
	} else if($('#pwd1').val().length < 6) {
		alert('비밀번호는 6자리 이상으로 설정해주세요.');
	} else {
		$.ajax({
			url : "/permission/setNewUserPassword.do",
			type : "POST",
			data: JSON.stringify({'userId' : targetUserId, 'password' : $('#pwd1').val()}),
			dataType : 'json',
			headers : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			success : function(response) {
				alert('변경되었습니다.');
				$('.pop-close').trigger('click');
			},
	        error : function(request, status, error) { 
	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    		}
		});
	}
}



$(document).ready(function() {
	
    function getUserList(){
    	const param = {'deptOne': $('#deptNameOne').val() ,'deptTwo': $('#deptNameTwo').val(), 'idOrName': $('#idOrName').val(), 'keyword': $('#keyword').val()};
	    	$.ajax({
	    		// 유저 리스트 가져와 jqGrid처리 하는 비동기  
	    	    url: "/permission/getUserPermissionList.do",
	    	    type : "POST",
	    	    dataType: 'json',
	    	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    	    data: JSON.stringify(param),
	    	  //  data: param,

	    	    success: function(response) {
	   // alert(response.length);
	    		 
	    		    		theGrid.jqGrid({
	    		    	        datatype: 'local',
	    		    	        data: response ,
	    		    	        colNames: ['실/국', '실과',  '아이디', '이름', '상태', '관리자 여부', '권한 편집','갱신일', 'sysYn'],
	    		    	        colModel: [                  
	    		    	            {name: 'upperDeptName', index: 'upperDeptName', width: 60, align: 'center'},
	    		    		        {name: 'deptName', index: 'deptName', width: 60, align: 'center'},
	    		    		        {name: 'userId', index: 'userId', width: 55 , align: 'center', formatter:formatOpt2},
	    		    		        {name: 'userName', index: 'userName', width: 42 , align: 'center'},
	    		    		        {name: 'userStatName', index: 'userStatName', width: 30, align: 'center'},
	    		    		        {name: 'sysYnNm', index: 'sysYnNm', width: 30, align: 'center'},
	    		    		        {name: 'userId', index: 'userId', width: 70, align: 'center', formatter:formatOpt1 ,fixed:true},             
	    		    		        {name:'updateDt', index:'updateDt', width:55, align:'left'},
	    		    		        {name: 'sysYn', index: 'sysYn', width: 30, align: 'center', hidden:true}
	    		    		        ],
	    	
	    		    	        autowidth: true,
	    		    	        gridview: true,             
	    		    	        rownumbers: true,
	    		    	        rowNum: 20,
	    		    	        //rowList: [5, 10, 15],
	    		    	        pager: '#GridPager',
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
	    		    	
	        },
	            error : function(request, status, error) { 
	            	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	        	}
	    	}).done(function() { 
	    		console.log("done");
	    		//theGrid.trigger("reloadGrid");
	    	}),
	
	        theGrid = $("#Grid"),
	
	        numberTemplate = {formatter: 'number', align: 'right', sorttype: 'number'};
    }
    
    
 // 엑셀 다운로드
	$('#btnExcel').on('click', function(){
		
		console.log($('#searchVO').serializeObject());
	    if(confirm('데이터를 엑셀로 내려받으시겠습니까?\n데이터 건수가 많을 경우 오래 걸릴 수 있습니다.')) {
	        // var totCount = $grid.getGridParam("records");
	        CM.downloadFileWithIFrame($('body'), '<c:url value="/permission/getUserPermissionExcel.do"/>', $('#searchVO').serializeObject());
	    }
	});
    
	$('#searchBtn').on('click', function(event) {
		$('#Grid').jqGrid('GridUnload');
		getUserList();
    });
	
	 $('#keyword').keydown(function (key) {
		if(key.keyCode == 13){
			$('#Grid').jqGrid('GridUnload');
			getUserList();
		}
	 });
	 
	$.ajax({
	    url: "/permission/getDeptNamesOne.do",
	    type : "GET",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    success: function(response) {
		    	if (response.length != 0) {
		    		var html = "<option value='' selected>-실/국-</option>";
		            for (var i = 0; i < response.length; i++) {
		                html += "<option value='" + response[i].deptId + "'>" + response[i].deptName + "</option>";
		            }
		            $("#deptNameOne").html(html);
		    	}
	    },
        error : function(request, status, error) { 
        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    		}
	});
	$("#deptNameOne").change(function() {
		$.ajax({
			// 부서2를 가져오는 비동기 
		    url: "/permission/getDeptNamesThatFoundUpper.do",
		    type : "POST",
		    dataType: 'json',
		   	data : JSON.stringify({'deptId' : $(this).children(":selected").val()}),
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    success: function(response) {
		    	var html = "<option value='' selected>-실과-</option>";
			    	if (response.length != 0) {
			            for (var i = 0; i < response.length; i++) {
			                html += "<option value='" + response[i].deptId + "'>" + response[i].deptName + "</option>";
			            }
			    	}
			    	 $("#deptNameTwo").html(html);
		    	
		    },
	        error : function(request, status, error) { 
	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    		}
		});

	});

/* 	$.ajax({
		// 부서2를 가져오는 비동기 
	    url: "/permission/getDeptNamesTwo.do",
	    type : "GET",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    success: function(response) {
		    	if (response.length != 0) {
		    		var html = "<option value='' selected>-전체-</option>";
		            for (var i = 0; i < response.length; i++) {
		                html += "<option value='" + response[i].deptId + "'>" + response[i].deptName + "</option>";
		            }
		            $("#deptNameTwo").html(html);
		    	}
	    	
	    },
        error : function(request, status, error) { 
        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    		}
	});
 */
 
	
   /*  function formatOpt1(cellvalue, options, rowObject){ 
    	  var str = "";
    	    
	     str += "<button class=\"btn sr3 st3\"><a href=\"/permission/editUserPermission.do?userId=" + rowObject.userId+ "&userName=" + rowObject.userName + "&deptName=" + rowObject.deptName + "&upperDeptName=" + rowObject.upperDeptName + "&sysYn=" + rowObject.sysYn + "\">편집</a></button>";    

	
    	     return str;
    	} */
	function formatOpt1(cellvalue, options, rowObject){ 
  	  var str = "";
  	    
	     str += "<button class=\"btn sr3 st3\" onclick=\"moveEditPage('" + rowObject.userId+ "','" + rowObject.userName + "','" + rowObject.deptName + "','" + rowObject.upperDeptName + "','" + rowObject.sysYn + "')\">편집</button>";    

	
  	     return str;
  	}
    
    /*  팝업 호출 */
    function formatOpt2(cellvalue, options, rowObject){
    	targetUserId = rowObject.userId;
    	var str = "";
        	str += "<button class=\"btn sr3 st3 btn-popup\" onclick=\"openPopUp()\">" + rowObject.userId+ "</button>";    
        return str;
    }

    
    
/*     function formatOpt2(cellvalue, options, rowObject){ 
  	  var str = "";
  	    
	     str += "<button class=\"btn sr3 st5 btn-popup\ onclick=\"openPopUp()\" "><a href=\"#\"  class=\"btn-popup\">" + rowObject.userId + "</a></button>";    

	
  	     return str;
  	} */
    
   

  
    getUserList();
  	
    function syncTrigger(){
    	$.ajax({
    		url : "/permission/triggerUserSync.do",
    		type : "POST",
    		dataType : 'json',
    		headers : {
    			'Content-Type' : 'application/json; charset=UTF-8'
    		},
    		success : function(response) {
    			alert('동기화 되었습니다.');
    			$('#Grid').jqGrid('GridUnload');
    			getUserList();
    		},
        error : function(request, status, error) { 
        		console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    		}
    	});
    }
    
    $('#syncTrigger').click(function(evt){
    		syncTrigger();
    });
});

/* function moveEditPage(userId,userName ,deptName ,upperDeptName , sysYn){
	var url =  '/permission/editUserPermission.do?userId=' + userId+ '&userName=' + userName+ '&deptName=' + deptName+ '&upperDeptName=' + upperDeptName+ '&sysYn=' + sysYn;
	 $(location).attr('href', url);
}
 */
 
function moveEditPage(userId,userName ,deptName ,upperDeptName , sysYn){
	 //var url =  '/permission/editUserPermission.do?userId=' + userId+ '&sysYn=' + sysYn;
	 // window.location =  url;
	 var form = document.form1;
	 form.userId.value = userId;
	 form.sysYn.value = sysYn;
	 form.action = '/permission/editUserPermission.do';
	 form.submit();
}
</script>
<form id='form1' name='form1' method="post">
<input type="hidden" id="userId" name="userId" />
<input type="hidden" id="sysYn" name="sysYn" />
</form>
<div class="sec-right">
	<!-- 상단 경로 및 페이지 타이틀 -->
	<div class="top-bar">
		<div class="path-wrap">
			<span class="home">홈</span>&nbsp;&nbsp;&gt; <span class="path">사용자
				및 권한 관리</span>&nbsp;&nbsp;&gt; <span class="path">사용자 관리</span>&nbsp;&nbsp;&gt;
			<span class="now">사용자 목록 조회</span>
		</div>
		<div class="clearfix">
			<p class="tit-page">사용자 목록 조회</p>
			<ul class="btn-wrap">
				<li>
					<button class="btn sr3 st3" id="syncTrigger">
						<i class="fa fa-refresh" aria-hidden="true"></i>사용자 동기화
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
					<legend>표,게시판 검색 조건</legend>
					<div class="left">
						<label> 
							<select class="ip sr1 st1 w1" id="deptNameOne" name="deptNameOne">
							</select>
						</label> 
						<label> 
							<select class="ip sr1 st1 w1" id="deptNameTwo" name="deptNameTwo">
								<option value="" selected>-실과-</option>
							</select>
						</label> 
						<label> 
							<select class="ip sr1 st1 w1" id="idOrName" name="idOrName">
								<option value="userId" selected>아이디</option>
								<option value="userName">이름</option>
							</select>
						</label> 
						<label> 
							<input type="text" class="ip sr1 st1 w2" id="keyword" name="keyword"/>
							<input type="text" style="display:none"/>
						</label> 
						<input type="button" class="btn sr2 st2" id="searchBtn" value="조회" />
					    <input id='btnExcel' type="button" class="btn sr2 st6" value="엑셀"/>
					</div>
				</fieldset>
			</form>
		</div>
		
		<table id="Grid">
		</table>
		<div id="GridPager">
		</div>

	</div>
	<!-- //컨텐츠 영역 -->

</div>
<!-- 모달 팝업 -->
	<div class="modal-pop pop-box1">
		<div class="layer-pop" style="width:530px;">
			<p class="tit">비밀번호 변경</p>
			<div class="cont">
				<table class="tbl sr1 st1">
					<caption class="hide">표</caption>
					<colgroup>
						<col style="width:20%"/>
						<col style="width:auto"/>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" class="th2">비밀번호</th>
							<td class="align-l">
								<input type="password" class="ip sr1 st1 w3" id="pwd1"/>
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">비밀번호 확인</th>
							<td class="align-l">
								<input type="password" class="ip sr1 st1 w3" id="pwd2"/>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="btn-wrap mgb0">
					<a href="#" class="btn sr2 st2" onclick="save()">저장</a>	
					<a href="#" class="btn sr2 st4 pop-close">취소</a>	
				</div>				
			</div>
			<a href="#" class="btn-close pop-close">닫기</a><!--[d] "pop-close" 클래스 값을 클릭할 시 팝업 닫힘-->
		</div>
	</div>
	<!-- 모달 팝업 -->
 	</div>
    <!--//container -->
</div>
</body>
</html>
