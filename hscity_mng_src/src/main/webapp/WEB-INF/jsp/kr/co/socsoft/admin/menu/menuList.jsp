<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/head.jsp" %>
<script>
var gloObj = null;
$(document).ready(function() {
	/* 콤보박스 : 대분류 */
	$.ajax({
	    url: "/menu/getCategoryGroupList.do",
	    type : "GET",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: {categoryType: '${menu}', upperCategoryCd: ""},
	    success: function(response) {
	    	if (response.length != 0) {
	    		var html = "<option value='' selected>-전체-</option>";
	            for (var i=0; i<response.length; i++) {
	                html += "<option value='" + response[i].categoryCd + "'>" + response[i].categoryNm + "</option>";
	            }
	            $("#category1depth").html(html);
	    	}
	    },
        error : function(request, status, error) { 
        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	}
	});

	/* 콤보박스 : 중분류 */
	$('#category1depth').on('change', function(event) {
		$.ajax({
		    url: "/menu/getCategoryGroupList.do",
		    type : "GET",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    data: {categoryType: '${menu}', upperCategoryCd: $('#category1depth').val()},
		    success: function(response) {
		    	if (response.length != 0) {
		    		var html = "<option value='' selected>-전체-</option>";
		            for (var i=0; i<response.length; i++) {
		                html += "<option value='" + response[i].categoryCd + "'>" + response[i].categoryNm + "</option>";
		            }
		            $("#category2depth").html(html);
		    	}
		    },
	        error : function(request, status, error) { 
	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    	}
		});
    });
    
	/* 콤보박스 : 세분류 */
	$('#category2depth').on('change', function(event) {
		$.ajax({
		    url: "/menu/getCategoryGroupList.do",
		    type : "GET",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    data: {categoryType: '${menu}', upperCategoryCd: $('#category2depth').val()},
		    success: function(response) {
		    	if (response.length != 0) {
		    		var html = "<option value='' selected>-전체-</option>";
		            for (var i=0; i<response.length; i++) {
		                html += "<option value='" + response[i].categoryCd + "'>" + response[i].categoryNm + "</option>";
		            }
		            $("#category3depth").html(html);
		    	}
		    },
	        error : function(request, status, error) { 
	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    	}
		});
    });
    
    
	$('#menuNm').keydown(function (key) {
		if(key.keyCode == 13){
			$('#Grid').jqGrid('GridUnload');
			gloObj.fnGetMenuList();
		}
    });
	
	$('#btnSearch').on('click', function(event) {
		$('#Grid').jqGrid('GridUnload');
		gloObj.fnGetMenuList();
    });
	
	$('#btnRegist').on('click', function(event) {
		fnOpenEditPage('');
    });
	
	gloObj = {
			fnGetMenuList: function() {
				var category1depth = $('#category1depth').val();
				var category2depth = $('#category2depth').val();
				var category3depth = $('#category3depth').val();
				var categoryCd = "";
				
				if (category3depth == "" && category2depth == "" && category1depth == "") {
					categoryCd = "";
				}
				
				if (category3depth == "" && category2depth == "" && category1depth != "") {
					categoryCd = category1depth;
				}
				
				if (category3depth == "" && category2depth != "") {
					categoryCd = category2depth;
				}
				
				if (category3depth != "") {
					categoryCd = category3depth;
				}
				const param = {
						        'categoryCd': categoryCd
						      , 'menuNm': $('#menuNm').val()
						      , 'categoryType': '${menu}'
						      };
				
		    	$.ajax({
		    	    url: "/menu/getMenuList.do",
		    	    type : "POST",
		    	    dataType: 'json',
		    	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    	    data: JSON.stringify(param),
		    	    success: function(response) {
		    	    	//console.log(response);
		    		    		theGrid.jqGrid({
		    		    	        datatype: 'local',
		    		    	        data: response ,
		    		    	        colNames: [
		    		    	        			'카테고리'
		    		    	        	      , '메뉴명'
		    		    	        	      , '한줄 설명'
		    		    	        	      , '메뉴권한'
		    		    	        	      , '미리보기'
		    		    	        	      , '속성편집'
		    		    	        	      , '권한편집'
		    		    	        	      , '메인화면'
		    		    	        	      , '메뉴사용'
		    		    	        	      , 'menuImgSrc'
		    		    	        	      , 'menuCd'
		    		    	        	      , 'mainCd'
		    		    	        	      , 'menuUrl'
		    		    	        	      ],
		    		    	        colModel: [                  
				    		    	            {name: 'categoryNm', index: 'categoryNm', width: 100, align: 'center'},
				    		    		        {name: 'menuNm', index: 'menuNm', width: 100, align: 'center'},
				    		    		        {name: 'description', index: 'description', width: 100, align: 'center'},
				    		    		        {name: 'menuRoleScd', index: 'menuRoleScd', width: 50, align: 'center', hidden:('${menu}' == 'biz' ? false : true), editable: true, edittype:"select", formatter:"select", editoptions:{value:"std:전체;dept:부서;deptuser:부서+사용자;"}, fixed:false},
				    		    		        {name: '', index: '', width: 40, align: 'center', formatter:formatOpt1, fixed:false},
				    		    		        {name: '', index: '', width: 40, align: 'center', formatter:formatOpt2, fixed:false},
				    		    		        {name: '', index: '', width: 40, align: 'center', formatter:formatOpt3, fixed:false, hidden:('${menu}' == 'biz' ? false : true)},
				    		    		        {	name: 'mainUseYn', 
				    		    		        	index: 'mainUseYn', 
				    		    		        	width: 40, 
				    		    		        	align: 'center', 
				    		    		        	editable: true, 
				    		    		        	edittype:"select", 
				    		    		        	formatter:"select", 
				    		    		        	editoptions:{	value:"y:사용;n:미사용",
				    		    		        		            dataEvents: [{ type: 'change', fn: function(e) { onChangeSelect(e, 'mainUseYn'); } }]
				    		    		        	}
				    		    		        },
				    		    		        {	name: 'useYn', 
				    		    		        	index: 'useYn', 
				    		    		        	width: 40, 
				    		    		        	align: 'center', 
				    		    		        	editable: true, 
				    		    		        	edittype:"select", 
				    		    		        	formatter:"select", 
				    		    		        	editoptions:{	value:"y:사용;n:미사용",
				    		    		        		            dataEvents: [{ type: 'change', fn: function(e) { onChangeSelect(e, 'useYn'); } }]
				    		    		        	}
				    		    		        },
				    		    		        {name: 'menuImgSrc', index: 'menuImgSrc', width: 40, align: 'center', hidden:true},
				    		    		        {name: 'menuCd', index:'menuCd', width:30, align:'center', hidden:true},
				    		    		        {name: 'mainCd', index:'mainCd', width:30, align:'center', hidden:true},
				    		    		        {name: 'menuUrl', index:'menuUrl', width:30, align:'center', hidden:true}
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
		    		    	        cellEdit: true,
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
		    		//console.log("done");
		    		//theGrid.trigger("reloadGrid");
		    	}),

		        theGrid = $("#Grid"),
		        numberTemplate = {formatter: 'number', align: 'right', sorttype: 'number'};
			}
	};
	
	// init
	gloObj.fnGetMenuList();
});

/* 미리보기 팝업 호출 */
function formatOpt1(cellvalue, options, rowObject){
	
	var strUrl = rowObject.menuUrl;
	
	if(strUrl.match('public.tableau.com')) {
		if (strUrl.match('embed')) {
			if (strUrl.match('embed=y')) {
				strUrl = strUrl.replace('embed=y', 'embed=true');
				strUrl += '&:showVizHome=no';
			} else {
				strUrl += '&:showVizHome=no';
			}	
		} else {
			strUrl += '&:embed=true&:showVizHome=no';
		}
	}
	
	//st6,7
	var str = "";
    	str += "<button class=\"btn sr3 st3 btn-popup\" onclick=\"fnOpenPreviewPopup('" + rowObject.menuTypeScd + "','" + strUrl + "')\">미리보기</button>";    
    return str;
}

/* 편집 팝업 호출 */
function formatOpt2(cellvalue, options, rowObject){
	var str = "";
    	str += "<button class=\"btn sr3 st3 btn-popup\" onclick=\"fnOpenEditPage('" + rowObject.menuCd + "');\">속성편집</button>";
    return str;
}

/* 편집 팝업 호출 */
function formatOpt3(cellvalue, options, rowObject){
	var str = "";
    	str += "<button class=\"btn sr3 st3 btn-popup\" onclick=\"fnOpenEditAuthPage('" + rowObject.menuCd + "','" + rowObject.menuRoleScd + "');\">권한편집</button>";
    return str;
}

/* 편집창 gogo */
function fnOpenEditPage(menuCd) {
	var form = document.form1;
	if (menuCd == '') {
		//url =  "/menu/${menu}/editMenu.do?menuCd=" + menuCd + "&crud=C";//신규
		form.crud.value = "C";
	} else {
		//url =  "/menu/${menu}/editMenu.do?menuCd=" + menuCd + "&crud=U";//update
		form.crud.value = "U";
	}
	form.menuCd.value = menuCd;
	form.action = "/menu/${menu}/editMenu.do";
	form.submit();
}

/* 권한 편집창 gogo */
function fnOpenEditAuthPage(menuCd, menuRoleScd) {
	var form = document.form1;
	form.menuCd.value = menuCd;
	if (menuRoleScd == 'std') {
		alert("[부서] 또는 [부서+사용자] 권한일 경우 사용 가능합니다.");
	} else if (menuRoleScd == 'dept') {//부서:: 부서 권한 편집창 이동
		//url =  "/menu/biz/editAuthDept.do?menuCd=" + menuCd;
		//window.location =  url;
		form.action = "/menu/biz/editAuthDept.do";
		form.submit();
	} else if (menuRoleScd == 'deptuser') {//부서+사용자:: 부서+사용자 권한 편집창 이동
		//url =  "/menu/biz/editAuthDeptUser.do?menuCd=" + menuCd;
		//window.location =  url;
		form.action = "/menu/biz/editAuthDeptUser.do";
		form.submit();
	}
}

/* 미리보기 팝업 */
function fnOpenPreviewPopup(menuTypeScd, menuUrl) {
	if (menuUrl == "") {
		alert("미리보기 url이 존재하지 않습니다.");
		return;
	}
	
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
		    	$(".pop-box1").show();
		    },
	        error : function(request, status, error) { 
	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    	}
		});
	} else {
		$('#preview').attr('src', menuUrl);
    	$(".pop-box1").show();
	}
}

function onChangeSelect(evt, index) {
	var celValue = $('#Grid').getRowData( $('#Grid').getGridParam("selrow" ));
	// 포커스 아웃
	$("#Grid").jqGrid("editCell", 0, 0, false);
	const param = {'categoryType': '${menu}', 'menuCd': celValue.menuCd, 'mainCd': celValue.mainCd, 'mainUseYn': celValue.mainUseYn, 'useYn': celValue.useYn};
	if(confirm("변경하시겠습니까?")) {
		$.ajax({
	    	    url: "/menu/updateMenuMst.do",
	    	    type : "POST",
	    	    dataType: 'json',
	    	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    	    data: JSON.stringify(param),
	    	    success: function(response) {
	    	    	if (response.isOk == "ok" ) {
	    	    		$('#Grid').jqGrid('GridUnload');
	    	    		gloObj.fnGetMenuList();
	    	    	}
	    	    },error : function(request, status, error) { 
	            	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	        	}
	    });
	}
}
</script>
<body>
<form id='form1' name='form1' method="post">
<input type="hidden" id="menuCd" name="menuCd" />
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
					<span class="path">메뉴 관리</span>&nbsp;&nbsp;&gt;
					<span class="now"><c:if test="${menu == 'pub'}">대시민</c:if><c:if test="${menu == 'biz'}">내부용</c:if> 메뉴화면 관리</span>
				</div>
				<div class="clearfix">
					<p class="tit-page"><c:if test="${menu == 'pub'}">대시민</c:if><c:if test="${menu == 'biz'}">내부용</c:if> 메뉴화면 관리</p>
					<ul class="btn-wrap">
						<li>
							<button class="btn sr3 st3" id="btnRegist">
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
					<form method="post" action="#">
						<fieldset>
						<legend>표,게시판 검색 조건</legend>
							<div class="left">									
								<label for="category1depth" class="label">대분류</label>	
								<select class="ip sr1 st1 w1" id="category1depth">
									<option value="">-전 체-</option>
								</select>																	
								<label for="category2depth" class="label">중분류</label>	
								<select class="ip sr1 st1 w1" id="category2depth">
									<option value="">-전 체-</option>
								</select>									
								<label for="category3depth" class="label">세분류</label>	
								<select class="ip sr1 st1 w1" id="category3depth">
									<option value="">-전 체-</option>
								</select>	
							</div>
							<div class="right">									
								<label for="menuNm" class="label">메뉴명</label>
								<label>
									<input type="text" class="ip sr1 st1 w2" id="menuNm" />
									<input type="text" class="ip sr1 st1 w2" id="menuNm2" style="display:none;" />
								</label> 
								<input type="button" id="btnSearch" class="btn sr2 st2" value="조회"/>
							</div>
						</fieldset> 
					</form>							
				</div>
				<!-- //표 상단 설정 영역 -->
				<!-- 그리드 -->
				<table id="Grid">
				</table>
				<!-- //그리드 -->
				<!-- 페이징 -->
				<div id="GridPager">
				</div>	
				<!-- //페이징 -->
			</div>
			<!-- //컨텐츠 영역 -->
		</div>
		<!-- //sec-right -->
		<!-- 모달 팝업 -->
		<div class="modal-pop pop-box1">
			<div class="layer-pop" style="width:1300px;height:773px;text-align:center;">
				<p class="tit">미리보기</p>
				<div class="cont">
					<iframe id="preview" name="preview" src="" style="width:1200px;height:700px;">
					</iframe>
				</div>
				<a href="#" class="btn-close pop-close">닫기</a><!--[d] "pop-close" 클래스 값을 클릭할 시 팝업 닫힘-->
			</div>
		</div>
		<!-- //모달 팝업 -->
	</div>
	<!-- //container -->
</div>
<!-- //wrap -->
</body>
</html>