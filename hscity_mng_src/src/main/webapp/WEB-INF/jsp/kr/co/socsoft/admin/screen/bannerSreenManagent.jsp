<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/head.jsp" %>
<body>
<div id="wrap">
    <%--헤더--%>
    <%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/layout/header.jsp" %>
    <c:if test="${menu == 'pubBanner'}">
		  <script>
		  let menu = 'pubBanner';
	    </script>
	</c:if>
<script type="text/javascript">
$(document).ready(function(){
	 getBannerList();
	 
	 $('#key').keydown(function (key) {
	        if(key.keyCode == 13){
		        	$('#screenTable').jqGrid('GridUnload');
		        	getBannerList();
	        }
	    });  
		$('#srch_btn').on('click', function(event) {
			$('#screenTable').jqGrid('GridUnload');
			getBannerList();
	    });
		
	$('#btnBannerSave').on('click', function(event) {
	 	fnConfSave();
	});
	
	$('#make_btn').on('click', function(event) {
		$("#popMakeBanner").show();
	});
	
	$('#btnBannerSave2').on('click', function(event) {
		beforeSub();
	});
	
	CM.createDatepickerLinked('bannerStartDate','bannerEndDate', 8);
	CM.createDatepickerLinked('bannerStartDate2','bannerEndDate2', 8);
});
var gNum = null;
var gSeq = null;

function getBannerList(){
	 	const param = {'type': menu, 'key': $('#key').val()};
   	$.ajax({
   	    url: "/screen/getBannerList.do",
   	    type : "POST",
   	    dataType: 'json',
   	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
   	    data: JSON.stringify(param),
   	    success: function(response) {
   	    	$('#screenTable').jqGrid('GridUnload');
   		    		theGrid.jqGrid({
   		    	        datatype: 'local',
   		    	        data: response ,
   		    	        colNames: ['배너 내용', '시작일', '종료일', '사용여부', '순서', '편집', '삭제', 'seq'],
   		    	        colModel: [                  
   		    		        {name: 'bannerContents', index: 'bannerContents', width: 70, align: 'center'},
   		    		        {name: 'bannerStartDate', index: 'bannerStartDate', width: 55 , align: 'center'},
   		    		        {name: 'bannerEndDate', index: 'bannerEndDate', width: 55 , align: 'center'},
   		    		        {name: 'bannerUseYn', index: 'bannerUseYn', width: 42 , align: 'center', edittype:"select", editable: true, formatter:"select",
   		    		        	editoptions: {value:"y:사용;n:사용안함" , dataEvents: [{ type: 'change', fn: function(e) { onChangeSelect(e, 'bannerUseYn'); } }]}},
   		    		        {name: 'bannerOrder', index: 'bannerOrder', width: 42, align: 'center', edittype:"select", editable: true, formatter:"select", 
   		    		        	editoptions: {value:"1:1;2:2;3:3;4:4;0:사용안함" , dataEvents: [{ type: 'change', fn: function(e) { onChangeSelect(e, 'bannerOrder'); } }]}},            //, editoptions:{value:"1:1;2:2;3:3;0:사용안함"}
   		    		        {name:'bannerSn', index:'bannerSn', width:42, align:'center', formatter:formatOpt1},
   		    		        {name:'bannerSn', index:'bannerSn', width:42, align:'center', formatter:formatOpt2},
   		    		     	{name:'bannerSn', index:'bannerSn', width:55, align:'center',hidden:true}
   		    		        ],
   		    	        autowidth: true,
   		    	        gridview: true,             
   		    	        rownumbers: true,
   		    	        rowNum: 10,
   		    	        //rowList: [5, 10, 15],
   		    	        //pager: '#GridPager',
   		    	        sortname: 'createDt',
   		    	        sortorder: 'desc',
   		    	        cellEdit: true,   
   		    	        viewrecords: true,  
   		    	        height: '300',
   		    	        width: 'auto',
   		    	        gridComplete :  function () {
		                        var maxDate; 
		                        var rowIDs = jQuery("#screenTable").jqGrid('getDataIDs');

		                        for (var i = 0; i < rowIDs.length ; i++)  {
		                            var rowID = rowIDs[i];
		                            var row = jQuery('#screenTable').jqGrid ('getRowData', rowID);

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
	            			onSelectRow: function(rowId, status, e) {	

	            				alert(rowId);
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

       theGrid = $("#screenTable"),

       numberTemplate = {formatter: 'number', align: 'right', sorttype: 'number'};
}

/* 편집 버튼 */
function formatOpt1(cellvalue, options, rowObject){
	var str = "";
	str += "<button class=\"btn sr3 st3 btn-popup\" onclick=\"fnOpenModifyPopup('" + rowObject.bannerSn + "')\">편집</button>";
	return str;
}

/* 삭제 버튼 */
function formatOpt2(cellvalue, options, rowObject){
	var str = "";
	str += "<button class=\"btn sr3 st3 btn-popup\" onclick=\"fnRemove('" + rowObject.bannerSn + "')\">삭제</button>";
	return str;
}

/* 편집창 팝업*/
function fnOpenModifyPopup(bannerSn){
	const param = {'bannerSn': bannerSn};
	$.ajax({
	    url: "/screen/getBannerDetail.do",
	    type : "POST",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: JSON.stringify(param),
	    success: function(response) {
	    	$('#bannerSn').val(response.bannerSn);
	    	$('#bannerContents').val(response.bannerContents);
	    	$('#bannerStartDate').val(response.bannerStartDate);
	    	$('#bannerEndDate').val(response.bannerEndDate);
	    	$('#bannerUseYn').val(response.bannerUseYn);
	    	$('#bannerOrder').val(response.bannerOrder);
	    	$("#popModifyBanner").show();
    	}, error : function(request, status, error) { 
        		console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	}
	}).done(function() { 
// 		console.log("done");
	}),

    theGrid = $("#columnEditGrid"),
    numberTemplate = {formatter: 'number', align: 'right', sorttype: 'number'};
}

/* 설정 저장 */
function fnConfSave() {
   	if ($("#bannerContents").val().trim().length === 0) {
		alert("배너 내용을 입력하세요.");
		return false;
	}else if ($("#bannerContents").val().trim().length < 16){
		alert("배너 내용은 16글자이상 입력해 주세요.");
		return false;
	}
	$.ajax({
	    url: "/screen/BannerConfSave.do",
	    type : "POST",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: JSON.stringify({bannerSn: $("#bannerSn").val(), bannerContents: $("#bannerContents").val(), bannerStartDate: $("#bannerStartDate").val(),
	    	bannerEndDate: $("#bannerEndDate").val(), bannerUseYn: $("#bannerUseYn").val(),bannerOrder: $("#bannerOrder").val()}),
	    success: function(response) {
	    	if (response.resultMsg == "정상처리" ) {
		    	alert("저장되었습니다.");
		    	$(".pop-box2").hide();
		    	getBannerList();
	    	}else if (response.resultMsg == "dup") {
	    		if(confirm($("#bannerOrder").val() + ' 번 위치는 이미 사용중입니다.' + $("#bannerContents").val() + '로 바꾸시겠습니까?')) {
	    			fnConfSave2();
	    		} else {
	    			return false;
	    		}
			}
        },
        error : function(request, status, error) { 
        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	}
	}).done(function() { 
		//console.log("done");
	});
}

/* 순서 중복시 설정 저장 */
function fnConfSave2(){
	$.ajax({
	    url: "/screen/BannerConfSave2.do",
	    type : "POST",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: JSON.stringify({bannerSn: $("#bannerSn").val(), bannerContents: $("#bannerContents").val(), bannerStartDate: $("#bannerStartDate").val(),
	    	bannerEndDate: $("#bannerEndDate").val(), bannerUseYn: $("#bannerUseYn").val(),bannerOrder: $("#bannerOrder").val()}),
	    success: function(response) {
	    	if (response.resultMsg == "정상처리" ) {
		    	alert("저장되었습니다.");
		    	$(".pop-box2").hide();
		    	getBannerList();
	    	}
        },
        error : function(request, status, error) { 
        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	}
	}).done(function() { 
		//console.log("done");
	});
}

/* 삭제 */
function fnRemove(bannerSn){
	if (confirm("삭제하시겠습니까?")) {
		const param = {'bannerSn': bannerSn};
		$.ajax({
		    url: "/screen/removeBanner.do",
		    type : "POST",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    data: JSON.stringify(param),
		    success: function(response) {
		    	if (response.resultMsg == "정상처리" ) {
			    	$(".pop-box2").hide();
			    	getBannerList();
		    	}
		    },
	        error : function(request, status, error) { 
	    	}
		});
	}
}

function onChangeSelect(e, gbn){
	var celValue = $('#screenTable').getRowData( $('#screenTable').getGridParam("selrow"));
	// 포커스 아웃
	$("#screenTable").jqGrid("editCell", 0, 0, false);
	
	if (gbn == "bannerOrder") {
		gNum = celValue.bannerOrder;
    	var bannerSn = celValue.bannerSn;
    	var bannerContents = celValue.bannerContents;

    	gSeq = bannerSn;
    	const param = {'type': menu, 'key': gNum, 'seq': gSeq};
		$.ajax({
	    	    url: "/screen/getCheckBannerOrder.do",
	    	    type : "POST",
	    	    dataType: 'json',
	    	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    	    data: JSON.stringify(param),
	    	    success: function(response) {
		    	    	if (response == 0 || gNum == 0) {
		    	    		setBannerOrder(gNum,gSeq);
		    	    	} else if (response > 0) {
		    	    		if(confirm(gNum + ' 번 위치는 이미 사용중입니다.' + bannerContents + '로 바꾸시겠습니까?')) {
		    	    			setBannerOrder(gNum,gSeq,'y');
		    	    		} else {
		    	    			getBannerList();
		    	    		}
		    	    	}
	    	    },error : function(request, status, error) { 
	            	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	        	}
	    });

   	} else if (gbn == "bannerUseYn") {
		const param = {'bannerSn': celValue.bannerSn, 'bannerUseYn': celValue.bannerUseYn};
		if(confirm("변경하시겠습니까?")) {
   			$.ajax({
	    	    url: "/screen/updateBannerUseYn.do",
  		    	    type : "POST",
  		    	    dataType: 'json',
  		    	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
  		    	    data: JSON.stringify(param),
  		    	    success: function(response) {
   		    	    	if (response.isOk == "ok" ) {
   		    	    		$('#screenTable').jqGrid('GridUnload');
   		    	    		getBannerList();
   		    	    		alert('변경되었습니다.');
   		    	    	}
  		    	    },error : function(request, status, error) { 
  		            	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
  		        	}
  		  	 	 });
		}else{
			$('#screenTable').jqGrid('GridUnload');
			getBannerList();
		}
	}
}
	
function setBannerOrder(num,seq){
	const param = {'key': num, 'seq' : seq};
	$.ajax({
    	    url: "/screen/setBannerOrder.do",
    	    type : "POST",
    	    dataType: 'json',
    	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
    	    data: JSON.stringify(param),
    	    success: function(response) {
    	    	getBannerList();
    	    },error : function(request, status, error) { 
            	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        	}
		});
}

function  beforeSub(){
	if ($("#bannerContents2").val().trim().length === 0) {
		alert("배너 내용을 입력하세요.");
		return false;
	}else if ($("#bannerContents2").val().trim().length < 16) {
		alert("배너 내용은 16글자이상 입력해 주세요.");
		return false;
	} else if ($("#bannerStartDate2").val().length === 0 || $("#bannerEndDate2").val().length === 0) {
		alert("일자를 입력하세요.");
		return false;
	}else {
		if ($("#bannerOrder2").val() == 0) {
			fnMakeSave();
		}else {
			$.ajax({
			    url: "/screen/getCheckBannerOrder.do",
			    type : "POST",
			    dataType: 'json',
			    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
			    data: JSON.stringify({'key': $("#bannerOrder2").val()}),
			    success: function(response) {
			    	if (response == 0 || gNum == 0) {
			    		fnMakeSave();
	    	    	} else if (response > 0) {
	    	    		if(confirm($("#bannerOrder2").val() + ' 번 위치는 이미 사용중입니다.' + $("#bannerContents2").val() + '로 바꾸시겠습니까?')) {
	    	    			fnMakeSave();
	    	    		}else {
							return false;
						}
	    	    	}
		    	}, error : function(request, status, error) { 
		        		console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		    	}
			});
		}
	}
}

/* 생성 저장 */
function fnMakeSave(){
	const param = {'bannerContents': $('#bannerContents2').val(),'bannerStartDate': $('#bannerStartDate2').val(),'bannerEndDate': $('#bannerEndDate2').val(),
			'bannerUseYn': $('#bannerUseYn2').val(), 'bannerOrder': $('#bannerOrder2').val()};
	$.ajax({
	    url: "/screen/makeNewBanner.do",
	    type : "POST",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: JSON.stringify(param),
	    success: function(response) {
	    	if (response.resultMsg == "정상처리" ) {
   	    		alert('저장되었습니다.');
	    		$('#popMakeBanner').hide();
   	    		$('#screenTable').jqGrid('GridUnload');
   	    		getBannerList();
   	    	}
    	}, error : function(request, status, error) { 
        		console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	}
	}).done(function() { 
		console.log("done");
	});
}


</script>
	<div id="container">
        <%-- left 메뉴 --%>
        <jsp:include page="/com/leftMenu.do?type=${type}&menu=${menu}"/>
			<div class="sec-right">
				<!-- 상단 경로 및 페이지 타이틀 -->
				<div class="top-bar">
					<div class="path-wrap">
						<span class="home">홈</span>&nbsp;&nbsp;&gt;
						<span class="path">메인화면 관리</span>&nbsp;&nbsp;&gt;
						<span class="path">대시민 메인화면 관리</span>&nbsp;&nbsp;&gt;
						<span class="now">대시민 메인화면 배너 관리</span>
					</div>
					<div class="clearfix">
						<p class="tit-page">대시민 메인화면 배너 관리</p>
						<ul class="btn-wrap">
						<li>
							<button class="btn sr3 st3" id="make_btn">
								<i class="fa fa-file-o" aria-hidden="true"></i>새로만들기
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
							<fieldset>
							<legend>표,게시판 검색 조건</legend>
								<div class="right">		
									<label for="srch" class="label">화면명</label>
									<input type="text" class="ip sr1 st1 w2" id="key"/>
									<input type="button" class="btn sr2 st2" id="srch_btn" value="조회"/>
								</div>
							</fieldset> 
					</div>
					<!-- //표 상단 설정 영역 -->
					<div class="tbl-wrap">
						<table id="screenTable"></table>
						<div id="GridPager"></div>
					</div>
				</div>
				<!-- //컨텐츠 영역 -->
			</div>
    </div>
    <!--//container -->
</div> 
    <!-- 모달 팝업:: 컬럼정보 편집 -->
	<div class="modal-pop pop-box2" id="popModifyBanner">
		<div class="layer-pop" style="width:900px;height:280px;">
			<p class="tit">배너정보 편집</p>
			<div class="cont">
				<table class="tbl sr1 st1">
					<caption class="hide">표</caption>
					<colgroup>
						<col style="width:20%"/>
						<col style="width:auto"/>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" class="th2">배너 내용</th>
							<td class="align-l">
								<input type="hidden" id="bannerSn"/>
								<input type="text" id="bannerContents" class="ip sr1 st1 w3"/>
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">일자</th>
							<td class="align-l">
								<input type="text" id="bannerStartDate" name="bannerStartDate" class="ip sr1 st1 cald" readonly>
								<input type="text" id="bannerEndDate" name="bannerEndDate" class="ip sr1 st1 cald" readonly>
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">사용여부</th>
							<td class="align-l">
								<select class="ip sr1 st1 w2" id="bannerUseYn">
									<option value="y">사용</option>
									<option value="n">사용안함</option>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">순서</th>
							<td class="align-l">
								<select class="ip sr1 st1 w2" id="bannerOrder">
									<option value="0">사용안함</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
								</select>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="btn-wrap mgb0">
					<a href="#" id="btnBannerSave" class="btn sr2 st2">저장</a>	
				</div>
			</div>			
			<a href="#" class="btn-close pop-close">닫기</a><!--[d] "pop-close" 클래스 값을 클릭할 시 팝업 닫힘-->
		</div>
	</div>
	<!-- 모달 팝업 -->
	
    <!-- 모달 팝업:: 컬럼 생성 -->
	<div class="modal-pop pop-box2" id="popMakeBanner">
		<div class="layer-pop" style="width:900px;height:280px;">
			<p class="tit">배너 생성</p>
			<div class="cont">
				<table class="tbl sr1 st1">
					<caption class="hide">표</caption>
					<colgroup>
						<col style="width:20%"/>
						<col style="width:auto"/>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" class="th2">배너 내용</th>
							<td class="align-l">
								<input type="hidden" id="bannerSn"/>
								<input type="text" id="bannerContents2" name="bannerContents2" class="ip sr1 st1 w3"/>
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">일자</th>
							<td class="align-l">
								<input type="text" id="bannerStartDate2" name="bannerStartDate2" class="ip sr1 st1 cald" readonly>
								<input type="text" id="bannerEndDate2" name="bannerEndDate2" class="ip sr1 st1 cald" readonly>
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">사용여부</th>
							<td class="align-l">
								<select class="ip sr1 st1 w2" id="bannerUseYn2">
									<option value="y">사용</option>
									<option value="n">사용안함</option>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">순서</th>
							<td class="align-l">
								<select class="ip sr1 st1 w2" id="bannerOrder2">
									<option value="0">사용안함</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
								</select>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="btn-wrap mgb0">
					<input type="button" id="btnBannerSave2" class="btn sr2 st2" value="저장" />	
				</div>
			</div>			
			<a href="#" class="btn-close pop-close">닫기</a><!--[d] "pop-close" 클래스 값을 클릭할 시 팝업 닫힘-->
		</div>
	</div>
	<!-- 모달 팝업 -->
</body>
</html>