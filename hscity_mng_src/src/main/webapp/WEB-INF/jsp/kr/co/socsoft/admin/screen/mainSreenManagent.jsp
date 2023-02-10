<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/head.jsp" %>

<body>
<div id="wrap">
    <%--헤더--%>
    <%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/layout/header.jsp" %>
    <c:if test="${menu == 'pub'}">
	    <script>
	    let menu = 'pub';
	    </script>
	</c:if>
	 <c:if test="${menu == 'biz'}">
		  <script>
		  let menu = 'biz';
	    </script>
	</c:if>
<script>

$(document).ready(function(){
	 getScreenList();
	 
	 $('#key').keydown(function (key) {
	        if(key.keyCode == 13){
		        	$('#screenTable').jqGrid('GridUnload');
		    		getScreenList();
	        }
	    });  
		$('#srch_btn').on('click', function(event) {
			$('#screenTable').jqGrid('GridUnload');
			getScreenList();
	    });
	
	$('.layout').on('click',function (event){
		if (confirm("레이아웃을 변경하시겠습니까?")) {
			const param = {'type': menu, 'key': $(this).val()};
			$.ajax({
		    	    url: "/screen/updateLayout.do",
		    	    type : "POST",
		    	    dataType: 'json',
		    	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    	    data: JSON.stringify(param),
		    	    success: function(response) {
		    	    	if (response.resultMsg == "정상처리" ) {
							alert('레이아웃이 변경되었습니다.');
						}
		    	    },error : function(request, status, error) { 
		            	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		        	}
	    		});
			
		} else {
			return false;
		}
	});
});
var gNum = null;
var gSeq = null;

function getScreenList(){
	 	const param = {'type': menu, 'key': $('#key').val()};
    	$.ajax({
    	    url: "/screen/getScreenList.do",
    	    type : "POST",
    	    dataType: 'json',
    	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
    	    data: JSON.stringify(param),
    	    success: function(response) {
    	    	$('#screenTable').jqGrid('GridUnload');
    		    		theGrid.jqGrid({
    		    	        datatype: 'local',
    		    	        data: response ,
    		    	        colNames: ['화면명', '화면설명',  '대표이미지 사용', '미리보기', '화면위치', '삭제','seq'],
    		    	        colModel: [                  
    		    	            {name: 'menuNm', index: 'menuNm', width: 60, align: 'center'},
    		    		        {name: 'description', index: 'description', width: 60, align: 'center'},
    		    		        {name: 'menuImgUseYn', index: 'menuImgUseYn', width: 55 , align: 'center', edittype:"select", editable: true, formatter:"select",
    		    		        	editoptions: {value:"y:사용;n:사용안함" , dataEvents: [{ type: 'change', fn: function(e) { onChangeSelect(e, 'menuImgUseYn'); } }]}},
    		    		        {name: 'menuUrl', index: 'menuUrl', width: 42 , align: 'center', formatter:formatOpt2},
    		    		        {name: 'mainLocNo', index: 'mainLocNo', width: 70, align: 'center', edittype:"select", editable: true, formatter:"select", 
    		    		        	editoptions: {value:"1:1;2:2;3:3;4:4;0:사용안함" , dataEvents: [{ type: 'change', fn: function(e) { onChangeSelect(e, 'mainLocNo'); } }]}},            //, editoptions:{value:"1:1;2:2;3:3;0:사용안함"}
    		    		        {name:'menuCd', index:'menuCd', width:55, align:'center', formatter:formatOpt1},
    		    		        {name:'mainCd', index:'mainCd', width:55, align:'center',hidden:true}
    		    		        ],
    		    	        autowidth: true,
    		    	        gridview: true,             
    		    	        rownumbers: true,
    		    	        rowNum: 10,
    		    	        //rowList: [5, 10, 15],
    		    	        //pager: '#GridPager',
    		    	        sortname: 'menuCd',
    		    	        sortorder: 'asc',
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
    		    $('input[name=layout]:input[value='+response[0].layout+']').attr("checked",true);
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
function openPopUp(){
 	 	$("body").css({
			"overflow":"hidden",
			"margin-right":"18px" 
		});
	$(".pop-box1").show();		
		
  }


	
	function setLocNumbers(num,seq,dupYn){
		const param = {'type': menu, 'key': num, 'seq' : seq, 'dupYn' : dupYn};
		$.ajax({
	    	    url: "/screen/setLocNumbers.do",
	    	    type : "POST",
	    	    dataType: 'json',
	    	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    	    data: JSON.stringify(param),
	    	    success: function(response) {
	    	 
	    	    },error : function(request, status, error) { 
	            	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	        	}
    		});
	}
	
	
/* 	function clickSaveBtn(){
		const param = {'type': menu, 'key': gNum};
		
		$.ajax({
	    	    url: "/screen/getCheckLocNumbers.do",
	    	    type : "POST",
	    	    dataType: 'json',
	    	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    	    data: JSON.stringify(param),
	    	    success: function(response) {
	    	    	setLocNumbers(gNum,gSeq,'y');
					$('.pop-close').trigger('click');
					getScreenList();
	    	    },error : function(request, status, error) { 
	            	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	        	}
	    	});
	} */
	$('#saveBtn').on('click', function(event) {
    });
	
    function onChangeSelect(e, gbn){
    	var celValue = $('#screenTable').getRowData( $('#screenTable').getGridParam("selrow" ));
    	// 포커스 아웃
    	$("#screenTable").jqGrid("editCell", 0, 0, false);
    	
    	if (gbn == "mainLocNo") {
    		gNum = celValue.mainLocNo;
        	var cMainCd = celValue.mainCd;
        	var cMainNm = celValue.menuNm;
        	
        	gSeq = cMainCd;
        	const param = {'type': menu, 'key': gNum};
    		
        	if (confirm("화면위치를 변경하시겠습니까?")){
    			$.ajax({
    		    	    url: "/screen/getCheckLocNumbers.do",
    		    	    type : "POST",
    		    	    dataType: 'json',
    		    	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
    		    	    data: JSON.stringify(param),
    		    	    success: function(response) {
    			    	    	if (response == 0 || gNum == 0) {
    			    	    		setLocNumbers(gNum,cMainCd);
    			    	    	} else if (response > 0) {
    			    	    		// response가 0 이상이라는것은 변경할 화면번호를 이미 사용하고 있다는 의미이므로
    			    	    		// 사용자 동의 인터페이스를 진행한후에 request한다 (merge)
    			    	    		if(confirm(gNum + ' 번 위치는 이미 사용중입니다.' + cMainNm + '로 바꾸시겠습니까?')) {
    			    	    		 	setLocNumbers(gNum,gSeq,'y');
    			    	    		} else {
    			    	    			getScreenList();
    			    	    		}
    			    	    	}
    		    	    },error : function(request, status, error) { 
    		            	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    		        	}
    		    });
        	} else {
        		getScreenList();

        	}
    	} else if (gbn == "menuImgUseYn") {
    		const param = {'type': menu, 'mainCd': celValue.mainCd, 'menuImgUseYn': celValue.menuImgUseYn};
    		if(confirm("변경하시겠습니까?")) {
    			if(celValue.menuImgUseYn == 'y'){
    				// BI - > IMG 로 변경시 이미지갖고있는지 여부를 체크해야함 
        			$.ajax({
    		    	    url: "/screen/getMenuImgYn.do",
    		    	    type : "POST",
    		    	   // dataType: 'json',
    		    	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
    		    	    data: JSON.stringify(param),
    		    	    success: function(response) {
        		    	    	if (response > 1) {
        		    	    		$.ajax({
        		    		    	    url: "/screen/updateMenuImgUseYn.do",
        		    		    	    type : "POST",
        		    		    	    dataType: 'json',
        		    		    	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
        		    		    	    data: JSON.stringify(param),
        		    		    	    success: function(response) {
        			    		    	    	if (response.isOk == "ok" ) {
        			    		    	    		$('#screenTable').jqGrid('GridUnload');
        			    		    	    		getScreenList();
        			    		    	    		alert('변경되었습니다.');
        			    		    	    	}
        		    		    	    },error : function(request, status, error) { 
        		    		            	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        		    		        	}
        		    		    });
        		    	    	} else if(response == 0) {
        		    	    		alert('해당메뉴는 이미지가 등록되어있지 않습니다.');
        		    	    		getScreenList();
        		    	    	}
    		    	    },error : function(request, status, error) { 
    		    	   	 	alert(request.responseText);
    		            	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    		        	}
    		    });
    			}else{
    				// IMG - > BI 
    				$.ajax({
    		    	    url: "/screen/updateMenuImgUseYn.do",
    		    	    type : "POST",
    		    	    dataType: 'json',
    		    	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
    		    	    data: JSON.stringify(param),
    		    	    success: function(response) {
	    		    	    	if (response.isOk == "ok" ) {
	    		    	    		$('#screenTable').jqGrid('GridUnload');
	    		    	    		getScreenList();
	    		    	    	}
    		    	    },error : function(request, status, error) { 
    		            	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    		        	}
    		    });
    			}
    			
    		}else{
    			$('#screenTable').jqGrid('GridUnload');
	    		getScreenList();
    		}
    	}
    	
    }

   
/* 사용자 선택 버튼 */
function formatOpt1(cellvalue, options, rowObject){
	var str = "";
    	str += "<button class=\"btn sr3 st3 btn-popup\" onclick=\"removeMainMng('" + rowObject.mainCd  + "')\">삭제</button>";
    return str;
}

/* 사용자 선택 버튼 */
function formatOpt2(cellvalue, options, rowObject){
	var str = "";
	str += "<button class=\"btn sr3 st3 btn-popup\" onclick=\"fnOpenPreviewPopup('" + rowObject.menuTypeScd + "','" + rowObject.menuUrl + "','" + rowObject.menuImgUseYn + "','" + rowObject.menuImg + "')\">보기</button>";
	return str;
}

/* 미리보기 팝업 */
function fnOpenPreviewPopup(menuTypeScd, menuUrl, menuImgUseYn, menuImg) {
	console.log(menuImg);
	if (menuImgUseYn == "y") {
		$(".pop-box2").show();
		$("#preview").hide();
		$("#previewImg").show();
		var src = '<c:url value="data:image/png;base64,' + menuImg +'"/>';
		$("#previewImg").attr("src", 'data:image/png;base64, ' + menuImg);
	} else {
		if (menuUrl == "") {
			alert("미리보기 url이 존재하지 않습니다.");
			return;
		}
		$("#previewImg").hide();
		$("#preview").show();
		if (menuTypeScd == "bi") {
			$.ajax({
			    url: "/tableau/goTableauTicket.do",
			    type : "GET",
			    dataType: 'json',
			    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
			    success: function(response) {
			    	console.log(response);
			    	
			    	var ticket = "trusted/" + response.ticket + "/";
			    	var biServerUrl = response.TableauUrl;
			    	var biMenuUrl = menuUrl;
			    	/* 
			    	 http://192.168.1.144/agdsidsf?sdfsdsdf=sdfsd&sdfsdfsd   => http://192.168.1.144/trusted/티켓값/agdsidsf?sdfsdsdf=sdfsd&sdfsdfsd
		   			 */
			    	var url = biMenuUrl.replace(biServerUrl, biServerUrl + ticket+"/");
			    	$('#preview').attr('src', url);
			    	$(".pop-box2").show();
			    },
		        error : function(request, status, error) { 
		        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		 	   	}
			});
		} else if (menuTypeScd == "gis") {
			$('#preview').attr('src', menuUrl);
	    	$(".pop-box2").show();
		}
	}
}

function removeMainMng(seq){
	if (confirm("삭제하시겠습니까?")) {
		const param = {'type': menu, 'seq': seq };
		$.ajax({
		    url: "/screen/removeMainMng.do",
		    type : "POST",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    data: JSON.stringify(param),
		    success: function(response) {
		     $('#screenTable').jqGrid('GridUnload');
	    		getScreenList();
		    },
	        error : function(request, status, error) { 
	    	}
		});
	}
	    
}
</script>
    <div id="container">
        <%-- left 메뉴 --%>
        <jsp:include page="/com/leftMenu.do?type=${type}&menu=${menu}"/>
			<div class="sec-right">
				<!-- 상단 경로 및 페이지 타이틀 -->
				<div class="top-bar">
				<!-- 대시민  -->
				<c:if test="${menu == 'pub'}">
					<div class="path-wrap">
						<span class="home">홈</span>&nbsp;&nbsp;&gt;
						<span class="path">메인화면 관리</span>&nbsp;&nbsp;&gt;
						<span class="path">대시민 메인화면 관리</span>&nbsp;&nbsp;&gt;
						<span class="now">대시민 대시보드 목록</span>
					</div>
					<div class="clearfix">
						<p class="tit-page">대시민 대시보드 목록</p>
					</div>
				</c:if>
				<!-- 내부용  -->
				<c:if test="${menu == 'biz'}">
					<div class="path-wrap">
						<span class="home">홈</span>&nbsp;&nbsp;&gt;
						<span class="path">메인화면 관리</span>&nbsp;&nbsp;&gt;
						<span class="path">내부용 메인화면 관리</span>&nbsp;&nbsp;&gt;
						<span class="now">내부용 대시보드 목록</span>
					</div>
					<div class="clearfix">
						<p class="tit-page">내부용 대시보드 목록</p>
					</div>
				</c:if>
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
					<!-- 이미지 박스 -->
					<div class="img-wrap">
						<p class="tit">※ 참고화면</p>
						<p class="sub_tit">메인 화면의 대시보드 레이아웃 선택</p>
						<div class="box_img">
							<ul>
								<li>
									<input type="radio" name="layout" class="layout" value="1" id="layout1">
									<label for="layout1">
										<img src="../images/admin/img-layout01.png" alt="메인화면의 대시보드 레이아웃1">
									</label>
								</li>
								<li>
									<input type="radio" name="layout" class="layout" value="2" id="layout2">
									<label for="layout2">
										<img src="../images/admin/img-layout02.png" alt="메인화면의 대시보드 레이아웃2">	
									</label>
								</li>
								<li>
									<input type="radio" name="layout" class="layout" value="3" id="layout3">
									<label for="layout3">
										<img src="../images/admin/img-layout03.png" alt="메인화면의 대시보드 레이아웃3">	
									</label>
								</li>
								<li>
									<input type="radio" name="layout" class="layout" value="4" id="layout4">
									<label for="layout4">
										<img src="../images/admin/img-layout04.png" alt="메인화면의 대시보드 레이아웃4">	
									</label>
								</li>
								<li>
									<input type="radio" name="layout" class="layout" value="5" id="layout5">
									<label for="layout5">
										<img src="../images/admin/img-layout05.png" alt="메인화면의 대시보드 레이아웃5">	
									</label>
								</li>
								<li>
									<input type="radio" name="layout" class="layout" value="6" id="layout6">
									<label for="layout6">
										<img src="../images/admin/img-layout06.png" alt="메인화면의 대시보드 레이아웃6">	
									</label>
								</li>
							</ul>	
						</div>
					</div>
					<!--// 이미지 박스 -->
				</div>
				<!-- //컨텐츠 영역 -->
			</div>
    </div>
    <!--//container -->
</div>
<!-- 모달 팝업 -->
<div class="modal-pop pop-box2">
	<div class="layer-pop" style="width:1300px;height:773px;text-align:center;">
		<p class="tit">미리보기</p>
		<div class="cont">
			<img id="previewImg" style="display: block; max-width:1200px; max-height:700px; width: auto; height: auto;">
			<iframe id="preview" name="preview"  style="width:1200px;height:700px;">
			</iframe>
		</div>
		<a href="#" class="btn-close pop-close">닫기</a><!--[d] "pop-close" 클래스 값을 클릭할 시 팝업 닫힘-->
	</div>
</div>
<!-- //모달 팝업 -->

<!-- 모달 팝업 -->
	<div class="modal-pop pop-box1">
		<div class="layer-pop" style="width:530px;">
			<p class="tit" style="border: 0px;">현재 <span id="pop-num"></span>번 위치는 사용중입니다.</p>
			<p class="tit"><span id="pop-cur-name"></span>으로 바꾸시겠습니까?</p>

			<div class="cont">
								<div class=" mgb0">
					<a href="#" class="btn sr2 st2" id="saveBtn">저장</a>	
					<a href="#" class="btn sr2 st4 pop-close">취소</a>	
				</div>				
			</div>
			<a href="#" class="btn-close pop-close">닫기</a><!--[d] "pop-close" 클래스 값을 클릭할 시 팝업 닫힘-->
		</div>
	</div>
	<!-- 모달 팝업 -->

</body>
</html>