<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/head.jsp" %>
<script>
var oEditors;
$(document).ready(function() {
	oEditors = CM.initSmartEditor('contents');
	
	/* 콤보박스 : 카테고리1 */
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
	
	/* 콤보박스 : 카테고리2 */
	$('#category1depth').on('change', function(event) {
		$.ajax({
		    url: "/menu/getCategoryGroupList.do",
		    type : "GET",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    data: {categoryType: '${menu}', upperCategoryCd: $('#category1depth').val()},
		    success: function(response) {
	    		var html = "<option value='' selected>-전체-</option>";
		    	if (response.length != 0) {

		            for (var i=0; i<response.length; i++) {
		                html += "<option value='" + response[i].categoryCd + "'>" + response[i].categoryNm + "</option>";
		            }
		    	}
	            $("#category2depth").html(html);
		    },
	        error : function(request, status, error) { 
	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    	}
		});
    });
    
	/* 콤보박스 : 카테고리3 */
	$('#category2depth').on('change', function(event) {
		$.ajax({
		    url: "/menu/getCategoryGroupList.do",
		    type : "GET",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    data: {categoryType: '${menu}', upperCategoryCd: $('#category2depth').val()},
		    success: function(response) {
	    		var html = "<option value='' selected>-전체-</option>";
		    	if (response.length != 0) {

		            for (var i=0; i<response.length; i++) {
		                html += "<option value='" + response[i].categoryCd + "'>" + response[i].categoryNm + "</option>";
		            }

		    	}
	            $("#category3depth").html(html);
		    },
	        error : function(request, status, error) { 
	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    	}
		});
    });
	/* 콤보박스 : 화면여부 */
	$.ajax({
	    url: "/code/getCommCode.do",
	    type : "GET",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: {groupCd: "biz_menu_type_scd", sortCol: "detail_cd", sort: "asc"},
	    success: function(response) {
	    	if (response.length != 0) {
	    		var html = "<option value='' selected>-선택-</option>";
	    		if('${menu}' == 'pub') {
	    			for (var i=0; i<response.length; i++) {
	    				if(response[i].cd != "bi") {
	    					html += "<option value='" + response[i].cd + "'>" + response[i].nm + "</option>";
	    				}
		            }
	    		}else {
	    			for (var i=0; i<response.length; i++) {
		                html += "<option value='" + response[i].cd + "'>" + response[i].nm + "</option>";
		            }
	    		}
	    		$("#menuTypeScd").html(html);
	    	}
	    	$("#menuTypeScd").val("${categoryVo.menuTypeScd}");
	    },
        error : function(request, status, error) { 
        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	}
	});
    
	$('#menuTypeScd').on('change', function(event) {
		if ($('#menuTypeScd').val() == "bi" || $('#menuTypeScd').val() == "bi_pub") {
			$('#rowMainUseYn').show();
		} else {
			$('#rowMainUseYn').hide();
		}
		
		if ($('#menuTypeScd').val() == "nor") {
			$('#rowMenuUrl').hide();
			$('#menuUrl').val("");
		} else {
			$('#rowMenuUrl').show();
		}
    });
	
	// 파일 다운로드
    $('.download-file').on('click', function(){
        var menuCd = $(this).data('menuCd');
        var seq = $(this).data('seq');

        CM.postFileDownload('<c:url value="/menu/downloadFile.do"/>', {
        	categoryType: '${menu}',// 'biz', 'pub'
            menuCd: menuCd,
            seq: seq
        });
    });
	
	
    var strCategory = "${categoryVo.categoryPath}";
	//console.log(strCategory);
	strCategory = strCategory.replace("{", "").replace("}", "");
	var category = strCategory.split(",");
	/* 콤보박스 : 카테고리1 */
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
	}).done(function(){
		if (category.length >= 2) $("#category1depth").val(category[1]);
       	/* 콤보박스 : 카테고리2 */
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
  			}).done(function(){
  				if (category.length >= 3) $("#category2depth").val(category[2]);
  				/* 콤보박스 : 카테고리3 */
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
					}).done(function(){
						if (category.length == 4) $("#category3depth").val(category[3]);
		  			});
  			});
	});
});

/* 미리보기 팝업 */
function fnOpenPreviewPopup() {
	/*
	if (menuUrl == "") {
		alert("미리보기 url이 존재하지 않습니다.");
		return;
	}
	*/
	var menuUrl = $("#menuUrl").val();
	var menuTypeScd = $("#menuTypeScd").val();
	//console.log(menuUrl);
	//console.log(menuTypeScd);
	if (menuTypeScd == "bi") {
		$.ajax({
		    url: "/tableau/goTableauTicket.do",
		    type : "GET",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    success: function(response) {
		    	var ticket = "trusted/" + response.ticket + "/";
		    	var biServerUrl = response.TableauUrl;
		    	var biMenuUrl = menuUrl;
		    	var url = biMenuUrl.replace(biServerUrl, biServerUrl + ticket+"/");
		    	$('#preview').attr('src', url);
		    	$(".pop-box1").show();
		    },
	        error : function(request, status, error) { 
	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    	}
		});
	} else if(menuTypeScd == 'gg_api') {
		window.open(menuUrl,"WinNewPops");
	} else {
		
		var strUrl = menuUrl;
		
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
		
		
		$('#preview').attr('src', strUrl);
    	$(".pop-box1").show();
	}
}

/* 메뉴정보 저장 */
function fnSaveMenuInfo() {
	
	var categoryCd = $("#category3depth").val();
	if (categoryCd == "") {
		categoryCd = $("#category2depth").val();
		if (categoryCd == "") {
			categoryCd = $("#category1depth").val();
		}
	} 
	if (confirm('저장하시겠습니까?')) {
		oEditors.getById["contents"].exec("UPDATE_CONTENTS_FIELD", []);
		var formData = new FormData();     
		var saveData = {
				categoryType: '${menu}', 
				menuCd: ($("#menuCd").val() == "" ? "NEW" : $("#menuCd").val()),
				layout: $("#layout").val(),
				menuNm: $("#menuNm").val(),
				categoryCd: categoryCd,
				description: $("#description").val(),
				contents: $("#contents").val(),
				menuUrl: $("#menuUrl").val(),
				menuTypeScd: $("#menuTypeScd").val(),
				mainCd: $("#mainCd").val(),
				mainUseYn: $("#mainUseYn").val(),
				menuRoleScd: ('${menu}' == 'biz' ? $("#menuRoleScd").val() : ""),
				useYn: $("#useYn").val()
				};
		formData.append("saveData", JSON.stringify(saveData));
		var menuImgCnt = 0;
		if ($("input[name=menuImg]")[0].files.length != 0) {
			formData.append("menuImg", $("input[name=menuImg]")[0].files[0]);    	
			menuImgCnt++;
		}
    	formData.append("menuImgCnt", menuImgCnt);
    	var fileCnt = 0;
    	if ($("input[name=fileData]").length>0) {
    		for (var i=0; i<$("input[name=fileData]").length; i++) {
    			if ($("input[name=fileData]")[i].files.length != 0) {
    				formData.append("fileData" + fileCnt, $("input[name=fileData]")[i].files[0]);    	
    				fileCnt++;
    			}
    		}
    	}
    	formData.append("fileDataCnt", fileCnt);    	
    	
		$.ajax({
		    url: "/menu/saveMenuInfo.do",
		    type : "POST",
		    enctype: 'multipart/form-data',
		    data : formData,
		    async:true,
			cache:false,
			contentType:false,
			processData:false,
		    success: function(response) {
		    	console.log(response.isOk);
		    	if (response.isOk == "ok") {
		    		alert("정상적으로 저장되었습니다.");
		    		//분기처리~~
		    		window.location = '/menu/${menu}/menuList.do';
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

function fnAddFile() {
	$("#fileDiv").append('<input type="file" id="fileData" name="fileData" class="ip sr1 st1 w4"/>');
}


function fnDeleteMenuInfo() {
	if (confirm('삭제하시겠습니까?')) {
		var data = {
				categoryType: '${menu}', 
				menuCd: $("#menuCd").val()
		};
		
		$.ajax({
		    url: "/menu/deleteMenuInfo.do",
		    type : "POST",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    data: JSON.stringify({data: data}),
		    success: function(response) {
		    	console.log(response.isOk);
		    	if (response.isOk == "ok") {
		    		var url =  "/menu/${menu}/menuList.do";
		    		window.location =  url;
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

function fnDeleteMenuFileData(menuCd, seq) {
	if (confirm('삭제하시겠습니까?')) {
		var data = {
				categoryType: '${menu}', 
				menuCd: menuCd,
				seq: seq
		};
		
		$.ajax({
		    url: "/menu/deleteMenuFileData.do",
		    type : "POST",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    data: JSON.stringify({data: data}),
		    success: function(response) {
		    	console.log(response.isOk);
		    	if (response.isOk == "ok") {
		    		var url =  "/menu/${menu}/editMenu.do?menuCd=" + menuCd + "&crud=U";
		    		window.location =  url;
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
					<span class="path">메뉴 관리</span>&nbsp;&nbsp;&gt;
					<span class="path"><c:if test="${menu == 'pub'}">대시민</c:if><c:if test="${menu == 'biz'}">내부용</c:if> 메뉴화면 관리</span>&nbsp;&nbsp;&gt;
					<span class="now"><c:if test="${menu == 'pub'}">대시민</c:if><c:if test="${menu == 'biz'}">내부용</c:if> 메뉴화면 편집</span>
				</div>
				<div class="clearfix">
					<p class="tit-page"><c:if test="${menu == 'pub'}">대시민</c:if><c:if test="${menu == 'biz'}">내부용</c:if> 메뉴화면 편집(등록)</p>
				</div>
			</div>
			<!-- //상단 경로 및 페이지 타이틀 -->

			<!-- 컨텐츠 영역 -->
			<div class="cont">					
				<!-- 표 상단 설정 영역 -->
				<div class="tbl-top">						
					<div class="right">
						<a href="/menu/${menu}/menuList.do" class="btn sr2 st2">목록보기</a>	
						<a href="#" class="btn sr2 st4" onclick="fnSaveMenuInfo();">저장</a>	
						<c:if test="${param.crud eq 'U'}"><a href="#" onclick="fnDeleteMenuInfo();" class="btn sr2 st5">삭제</a></c:if>
					</div>					
				</div>
				<!--// 표 상단 설정 영역 -->
				<table class="tbl sr1 st1">
					<caption class="hide"><c:if test="${menu == 'pub'}">대시민</c:if><c:if test="${menu == 'biz'}">내부용</c:if> 메뉴화면 편집 표</caption>
					<colgroup>
						<col style="width:20%"/>
						<col style="width:auto"/>
					<tbody>
						<tr>
							<th scope="row" class="th2">카테고리</th>
							<td class="align-l">
								<label>
									<select class="ip sr1 st1 w2" id="category1depth">
										<option value="" selected>-전체-</option>
									</select>
								</label>
								<label>
									<select class="ip sr1 st1 w2" id="category2depth">
										<option value="" selected>-전체-</option>
									</select>
								</label>
								<label>
									<select class="ip sr1 st1 w2" id="category3depth">
										<option value="" selected>-전체-</option>
									</select>
								</label>								
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">
								<label for="menuNm">등록메뉴명(화면)</label>	
							</th>
							<td class="align-l">
							    <input type="hidden" id="menuCd" value="${categoryVo.menuCd}" />
							    <input type="hidden" id="layout" value="${categoryVo.layout}" />
								<input type="text" id="menuNm" class="ip sr1 st1 w3" value="${categoryVo.menuNm}" />	
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">
								<label for="description">한줄 설명</label>
							</th>
							<td class="align-l">
								<input type="text" id="description" class="ip sr1 st1 w3" value="${categoryVo.description}" />	
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2" style="height:250px;">내용</th>
							<td class="align-l">
								<textarea id="contents" name="contents" style="width:100%;height:200px;">${categoryVo.contents}</textarea>
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">
								<label for="aa3">첨부파일</label>
							</th>
							<td class="align-l">
								<div>
									<div id="fileDiv" style="float:left;width:500px">
									</div>
									<div>
										<a href="#" onclick="fnAddFile();" class="btn sr2 st3">추가</a>
									</div>
								</div>
								<ul class="file-list">
								<c:if test="${fn:length(attachFile) > 0}">
									<c:forEach var="file" items="${attachFile}" varStatus="status">
										<div>
                                            <img src="<c:url value="/images/cmm/ico-file.gif"/>" alt="${file.fileNm}"/>
                                            <a href="#" class="download-file" data-menu-cd="${file.menuCd}" data-seq="${file.seq}" onclick="return false;">${file.fileNm}</a>
                                            <a href="#" onclick="fnDeleteMenuFileData('${file.menuCd}',${file.seq})"><img src="<c:url value="/images/cmm/btn-delete.gif"/>" alt="삭제" title="삭제"/></a>
                                        </div>
									</c:forEach>
								</c:if>
								</ul>
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">화면 여부</th>
							<td class="align-l">
								<label>
									<select class="ip sr1 st1 w1" id="menuTypeScd">
									</select>
								</label>		
							</td>
						</tr>
						<tr id="rowMenuUrl" style="display:<c:if test="${categoryVo.menuTypeScd eq 'nor'}">none;</c:if>">
							<th scope="row" class="th2">
								<label for="menuUrl">URL</label>
							</th>
							<td class="align-l">
								<input type="text" id="menuUrl" class="ip sr1 st1 w4" value="${categoryVo.menuUrl}" />
								<a href="javascript:fnOpenPreviewPopup();" class="btn sr2 st3">미리보기</a>
							</td>
						</tr>
						<tr id="rowMainUseYn" style="display:<c:if test="${categoryVo.menuTypeScd eq 'gis'}">none;</c:if>">
							<th scope="row" class="th2">메인화면 사용여부</th>
							<td class="align-l">
								<label>
								    <input type="hidden" id="mainCd" value="${categoryVo.mainCd}" />
									<select class="ip sr1 st1 w1" id="mainUseYn">
										<option value="n" <c:if test="${categoryVo.mainUseYn eq 'n'}">selected</c:if>>사용안함</option>
										<option value="y" <c:if test="${categoryVo.mainUseYn eq 'y'}">selected</c:if>>사용</option>
									</select>
								</label>
								<label for="menuImg" class="label">대표이미지:</label> 
								<input type="file" id="menuImg" name="menuImg" class="ip sr1 st1 w4"/>
								<c:if test="${menuImg ne null}">
									<a href="#" onclick="$('.pop-box2').show();">대표이미지 보기</a>
								</c:if>
							</td>
						</tr>
						<tr>
							<th scope="row" class="th2">메뉴 사용여부</th>
							<td class="align-l">
								<label>
									<select class="ip sr1 st1 w1" id="useYn">
										<option value="y" <c:if test="${categoryVo.useYn eq 'y'}">selected</c:if>>사용</option>
										<option value="n" <c:if test="${categoryVo.useYn eq 'n'}">selected</c:if>>사용안함</option>
									</select>
								</label>
							</td>
						</tr>
						<c:if test="${menu == 'biz'}">
							<tr>
								<th scope="row" class="th2">메뉴 권한</th>
								<td class="align-l">
									<label>
										<select class="ip sr1 st1 w1" id="menuRoleScd">
											<option value="std" <c:if test="${categoryVo.menuRoleScd eq 'std'}">selected</c:if>>전체</option>
											<option value="dept" <c:if test="${categoryVo.menuRoleScd eq 'dept'}">selected</c:if>>부서</option>
											<option value="deptuser" <c:if test="${categoryVo.menuRoleScd eq 'deptuser'}">selected</c:if>>부서+사용자</option>
											<option value="none" <c:if test="${categoryVo.menuRoleScd eq 'none'}">selected</c:if>>미정</option>
										</select>
									</label>
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
			<!-- //컨텐츠 영역 -->
		</div>
		<!-- 모달 팝업:미리보기 -->
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
		<!-- 모달 팝업 -->
		<!-- 모달 팝업:미리보기 -->
		<div class="modal-pop pop-box1">
			<div class="layer-pop" style="width:1300px;height:773px;text-align:center;">
				<p class="tit">미리보기</p>
				<div class="cont">
					<table>
						<tr>
							<td id='preview2'>
							</td>
						</tr>
						
					</table>
				</div>
				<a href="#" class="btn-close pop-close">닫기</a><!--[d] "pop-close" 클래스 값을 클릭할 시 팝업 닫힘-->
			</div>
		</div>
		<!-- 모달 팝업 -->
		<!-- 모달 팝업:대표이미지 보기 -->
		<div class="modal-pop pop-box2">
			<div class="layer-pop" style="width:800px;height:600px;">
				<p class="tit">대표이미지</p>
				<div class="cont" style="text-align:center;">
					<c:if test="${menuImg ne null}">
						<img src="data:image/png;base64,${menuImg}" style="display: block; max-width:750px; max-height:550px; width: auto; height: auto;">
					</c:if>
				</div>
				<a href="#" class="btn-close pop-close">닫기</a><!--[d] "pop-close" 클래스 값을 클릭할 시 팝업 닫힘-->
			</div>
		</div>
		<!-- 모달 팝업 -->
	</div>
	<!-- //container -->
</div>
<!-- //wrap -->
</body>
</html>