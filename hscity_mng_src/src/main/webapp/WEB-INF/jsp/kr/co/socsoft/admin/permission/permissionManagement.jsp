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
$(document).ready(function() {
	$(".btn-drop").trigger("click");
	var haveChildDeptYn = false;
	var deptId = '';
	
	$('#srchBtn').on('click', function(event) {
		if($('#deptNameOne').children(":selected").val() == ''){
			alert('권한을 조회할 상세 부서를 선택하세요');
		    deptId = '';
			return;
		} 		
		
		if(!haveChildDeptYn && $('#deptNameTwo').children(":selected").val() == ''){
			alert('권한을 조회할 상세 부서를 선택하세요');
			deptId = '';
			return;
		} 			
		deptId = haveChildDeptYn ? $('#deptNameOne').children(":selected").val() : $('#deptNameTwo').children(":selected").val() ; 
		
		getDeptMenuPermit();
		
		var deptNm = haveChildDeptYn ? $('#deptNameOne').children(":selected").text() : $('#deptNameTwo').children(":selected").text() ; 
		
		$('#deptNm').text(deptNm);
		if(!$('button.btn-drop').hasClass('on')){
			$('button.btn-drop').addClass('on');
			$('.drop-slide').show();
		}
		alert(deptNm + '의 권한을 편집합니다.');
	});
	getHsBizMenuMst();
	
	function getDeptMenuPermit(cl,cm,cs){
		$.ajax({
			url : "/permission/getDeptMenuPermit.do",
			data: JSON.stringify({'categoryL' : cl,'categoryM' : cm,'categoryS' : cs,'deptId' : deptId}),
			type : "POST",
			dataType : 'json',
			headers : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			success : function(response) {
				$('#table2').jqGrid('GridUnload');
				setJqGrid('table2', response);
			},
			error : function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:"
						+ request.responseText + "\n" + "error:"
						+ error);
			}
		});
	}

	function getHsBizMenuMst(cl,cm,cs){
		$.ajax({
			url : "/permission/getHsBizMenuMst.do",
			data: JSON.stringify({'categoryL' : cl,'categoryM' : cm,'categoryS' : cs, 'type' : 'dept'}),
			type : "POST",
			dataType : 'json',
			headers : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			success : function(response) {
				$('#table1').jqGrid('GridUnload');
				setJqGrid('table1', response);
			},
			error : function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:"
						+ request.responseText + "\n" + "error:"
						+ error);
			}
		});
	}
	


	$.ajax({
			url: "/permission/getDeptNamesOne.do",
		    type : "GET",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    success: function(response) {
			    	if (response.length != 0) {
			    		let html = "<option value='' selected>-전체-</option>";
						for (var i = 0; i < response.length; i++ ){
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
		    	var html = "<option value='' selected>-전체-</option>";
			    	if (response.length != 0) {
			            for (var i = 0; i < response.length; i++) {
			                html += "<option value='" + response[i].deptId + "'>" + response[i].deptName + "</option>";
			            }
			        haveChildDeptYn = false;
			    	} else {
			    		haveChildDeptYn = true;
			    	}
			    	 $("#deptNameTwo").html(html);
		    	
		    },
	        error : function(request, status, error) { 
	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    		}
		});

	});
	/* 콤보박스 : 카테고리1 */
	$.ajax({
	    url: "/menu/getCategoryGroupList.do",
	    type : "GET",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: {categoryType: 'biz', upperCategoryCd: ""},
	    success: function(response) {
	    	if (response.length != 0) {
	    		var html = '<option id="" val="">전체</option>';
	            for (var i=0; i<response.length; i++) {
	                html += "<option value='" + response[i].categoryCd + "'>" + response[i].categoryNm + "</option>";
	            }
	            $('#categoryL1').html(html);
		  		$('#categoryL2').html(html);
	    	}
	    },
        error : function(request, status, error) { 
        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	}
	});
	
	/*
	$.ajax({
		url : "/permission/getUpperCategory.do",
		type : "POST",
		dataType : 'json',
		headers : {
			'Content-Type' : 'application/json; charset=UTF-8'
		},
		success : function(response) {
			var html = '<option id="" val="">전체</option>';
			for (var i = 0; i < response.length; i++) {
	  			html += '<option id="' + response[i].upperBizCategoryNm +'">' + response[i].upperBizCategoryNm + '</option>'

	  		}
	  		$('#categoryM1').html(html);
	  		$('#categoryM2').html(html);
		},
		error : function(request, status, error) {
			alert("code:" + request.status
					+ "\n" + "message:"
					+ request.responseText
					+ "\n" + "error:"
					+ error);
		}
	});
	*/
	/* 콤보박스 : 카테고리2 */
	$('#categoryL1').on('change', function(event) {
		$.ajax({
		    url: "/menu/getCategoryGroupList.do",
		    type : "GET",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    data: {categoryType: 'biz', upperCategoryCd: $('#categoryL1').val()},
		    success: function(response) {
	    		var html = "<option value='' selected>-전체-</option>";
		    	if (response.length != 0) {

		            for (var i=0; i<response.length; i++) {
		                html += "<option value='" + response[i].categoryCd + "'>" + response[i].categoryNm + "</option>";
		            }
		    	}
	            $("#categoryM1").html(html);
		    },
	        error : function(request, status, error) { 
	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    	}
		});
    });
	$('#categoryL2').on('change', function(event) {
		$.ajax({
		    url: "/menu/getCategoryGroupList.do",
		    type : "GET",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    data: {categoryType: 'biz', upperCategoryCd: $('#categoryL2').val()},
		    success: function(response) {
	    		var html = "<option value='' selected>-전체-</option>";
		    	if (response.length != 0) {

		            for (var i=0; i<response.length; i++) {
		                html += "<option value='" + response[i].categoryCd + "'>" + response[i].categoryNm + "</option>";
		            }
		    	}
	            $("#categoryM2").html(html);
		    },
	        error : function(request, status, error) { 
	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    	}
		});
    });
	
	/*
	$.ajax({
		url : "/permission/getCategory.do",
		type : "POST",
		dataType : 'json',
		headers : {
			'Content-Type' : 'application/json; charset=UTF-8'
		},
		success : function(response) {
			var html = '<option id="" val="">전체</option>';
			for (var i = 0; i < response.length; i++) {
	  			html += '<option id="' + response[i].bizCategoryNm +'">' + response[i].bizCategoryNm + '</option>'

	  		}
	  		$('#categoryS1').html(html);
	  		$('#categoryS2').html(html);
		},
		error : function(request, status, error) {
			alert("code:" + request.status
					+ "\n" + "message:"
					+ request.responseText
					+ "\n" + "error:"
					+ error);
		}
	});
	*/
	
	/* 콤보박스 : 카테고리3 */
	$('#categoryM1').on('change', function(event) {
		$.ajax({
		    url: "/menu/getCategoryGroupList.do",
		    type : "GET",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    data: {categoryType: 'biz', upperCategoryCd: $('#categoryM1').val()},
		    success: function(response) {
	    		var html = "<option value='' selected>-전체-</option>";
		    	if (response.length != 0) {

		            for (var i=0; i<response.length; i++) {
		                html += "<option value='" + response[i].categoryCd + "'>" + response[i].categoryNm + "</option>";
		            }

		    	}
	            $("#categoryS1").html(html);
		    },
	        error : function(request, status, error) { 
	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    	}
		});
    });
	$('#categoryM2').on('change', function(event) {
		$.ajax({
		    url: "/menu/getCategoryGroupList.do",
		    type : "GET",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    data: {categoryType: 'biz', upperCategoryCd: $('#categoryM2').val()},
		    success: function(response) {
	    		var html = "<option value='' selected>-전체-</option>";
		    	if (response.length != 0) {

		            for (var i=0; i<response.length; i++) {
		                html += "<option value='" + response[i].categoryCd + "'>" + response[i].categoryNm + "</option>";
		            }

		    	}
	            $("#categoryS2").html(html);
		    },
	        error : function(request, status, error) { 
	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    	}
		});
    });
/* 
	$.ajax({
		url : "/permission/getRootCategory.do",
		type : "POST",
		dataType : 'json',
		headers : {
			'Content-Type' : 'application/json; charset=UTF-8'
		},
		success : function(response) {
			var html = '<option id="" val="">전체</option>';
	  		for (var i = 0; i < response.length; i++){
	  			html += '<option id="' + response[i].upperBizRootCategoryNm +'">' + response[i].upperBizRootCategoryNm + '</option>'
	  		}
	  		$('#categoryL1').html(html);
	  		$('#categoryL2').html(html);
		},
		error : function(request, status, error) {
			alert("code:" + request.status
					+ "\n" + "message:"
					+ request.responseText
					+ "\n" + "error:"
					+ error);
		}
	});
	
	$.ajax({
		url : "/permission/getUpperCategory.do",
		type : "POST",
		dataType : 'json',
		headers : {
			'Content-Type' : 'application/json; charset=UTF-8'
		},
		success : function(response) {
			var html = '<option id="" val="">전체</option>';
	  		for (var i = 0; i < response.length; i++ ){
	  			html += '<option id="' + response[i].upperBizCategoryNm +'">' + response[i].upperBizCategoryNm + '</option>'

	  		}
	  		$('#categoryM1').html(html);
	  		$('#categoryM2').html(html);
		},
		error : function(request, status, error) {
			alert("code:" + request.status
					+ "\n" + "message:"
					+ request.responseText
					+ "\n" + "error:"
					+ error);
		}
	});
	
	$.ajax({
		url : "/permission/getCategory.do",
		type : "POST",
		dataType : 'json',
		headers : {
			'Content-Type' : 'application/json; charset=UTF-8'
		},
		success : function(response) {
			var html = '<option id="" val="">전체</option>';
			for (var i = 0; i < response.length; i++ ){
	  			html += '<option id="' + response[i].bizCategoryNm +'">' + response[i].bizCategoryNm + '</option>'
	  		}
	  		$('#categoryS1').html(html);
	  		$('#categoryS2').html(html);
		},
		error : function(request, status, error) {
			alert("code:" + request.status
					+ "\n" + "message:"
					+ request.responseText
					+ "\n" + "error:"
					+ error);
		}
	});
	 */
	function formatOpt1(cellvalue, options, rowObject) {
		var str = "";
		str += "<button class=\"btn sr3 st3 btn-popup\" onclick=\"fnOpenPreviewPopup('" + rowObject.bizMenuTypeScd + "','" + rowObject.bizMenuUrl + "')\">보기</button>";

		return str;
	}
	function setJqGrid(gridId, dataP) {
		var width = [25, 70, 70, 70, 70, 55, 45,45, 45];
		
		
		theGrid = $("#" + gridId),
		numberTemplate = {
			formatter : 'number',
			align : 'right',
			sorttype : 'number'
		};

		theGrid.jqGrid({
			datatype : 'local',
			data : dataP,
			colNames : [ '구분', '카테고리(대)', '카테고리(중)', '카테고리(소)', '메뉴명', '설명', '미리보기', 'bizMenuCd' ],
			colModel : [ {
				name : 'bizMenuTypeScd',
				index : 'bizMenuTypeScd',
				width : width[0],
				align : 'center'
			}, {
				name : 'upperBizRootCategoryNm',
				index : 'upperBizRootCategoryNm',
				width : width[1],
				align : 'center'
			}, {
				name : 'upperBizCategoryNm',
				index : 'upperBizCategoryNm',
				width : width[2],
				align : 'center'
			}, {
				name : 'bizCategoryNm',
				index : 'bizCategoryNm',
				width :  width[3],
				align : 'center'
			}, {
				name : 'bizMenuNm',
				index : 'bizMenuNm',
				width :  width[4],
				align : 'center'
			}, {
				name : 'description',
				index : 'description',
				width : width[5],
				align : 'center'
			}, {
				name : 'bizMenuUrl',
				index : 'bizMenuUrl',
				width : width[6],
				align : 'center',
				formatter : formatOpt1
			}, {
				name : 'bizMenuCd',
				index : 'bizMenuCd',
				width : width[7],
				align : 'center',
				hidden : true
			} ],
			multiselect : true,
			autowidth : true,
			gridview : true,
			rownumbers : true,
			rowNum : 10,
			rowList : [ 5, 10, 15 ],
			pager : '#GridPager',
			sortname : 'Date',
			sortorder : 'asc',
			viewrecords : true,
			height : '100%',
			width : 'auto',
			gridComplete : function() {
				var maxDate;
				var rowIDs = jQuery("#" + gridId).jqGrid('getDataIDs');

				for (var i = 0; i < rowIDs.length; i++) {
					var rowID = rowIDs[i];
					var row = jQuery("#" + gridId).jqGrid('getRowData', rowID);

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
	}
	$("#categoryL1").change(function() {
		var key = $("#categoryL1 option:selected").text() == '전체' ? '': $("#categoryL1 option:selected").text();
		getHsBizMenuMst(key,'','');
		
	});
	$("#categoryM1").change(function() {
		var key = $("#categoryM1 option:selected").text() == '전체' ? '': $("#categoryM1 option:selected").text();
		getHsBizMenuMst('',key,'');
		});
	$("#categoryS1").change(function() {
		var key = $("#categoryS1 option:selected").text() == '전체' ? '': $("#categoryS1 option:selected").text();
		getHsBizMenuMst('','',key);
		});
	
	$("#categoryL2").change(function() {
		var key = $("#categoryL2 option:selected").text() == '전체' ? '': $("#categoryL2 option:selected").text();
		getDeptMenuPermit(key, '','');
	});
	$("#categoryM2").change(function() {
		var key = $("#categoryM2 option:selected").text() == '전체' ? '': $("#categoryM2 option:selected").text();
		getDeptMenuPermit('',key,'');
		});
	$("#categoryS2").change(function() {
		var key = $("#categoryS2 option:selected").text() == '전체' ? '': $("#categoryS2 option:selected").text();
		getDeptMenuPermit('','',key);
		});
	
	$("#saveBtn").click(function() {
		if(deptId == ''){
			alert('부서를 선택해 주세요.');
		return;
		}
		save();
		});
	
	$("#removeBtn").click(function() {
		if(deptId == ''){
			alert('부서를 선택해 주세요.');
		return;
		}
		remove();
		});
	
	function save(){
		
		var $grid = $("#table1"), selIds = $grid.jqGrid("getGridParam", "selarrrow"), i, n, cellValues = [];
		for (i = 0, n = selIds.length; i < n; i++) {
		    cellValues.push($grid.jqGrid("getCell", selIds[i], "bizMenuCd"));
		}
		$.ajax({
			url : "/permission/setMenuDeptMap.do",
			type : "POST",
			dataType : 'json',
			data: JSON.stringify({'deptId' : deptId , 'bizMenuCd' : cellValues}),
			headers : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			success : function(response) {
			alert(response + "건의 새로운 메뉴권한이 추가되었습니다.");
			
			},
			error : function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:"
						+ request.responseText + "\n" + "error:" + error);
			}
		}).done(function() {
			$('#table2').jqGrid('GridUnload');
			getDeptMenuPermit();

		});
	}
	
	function remove(){
		var $grid = $("#table2"), selIds = $grid.jqGrid("getGridParam", "selarrrow"), i, n, cellValues = [];
		for (i = 0, n = selIds.length; i < n; i++) {
		    cellValues.push($grid.jqGrid("getCell", selIds[i], "bizMenuCd"));
		}
		$.ajax({
			url : "/permission/deleteMenuDeptMap.do",
			type : "POST",
			dataType : 'json',
			data: JSON.stringify({'deptId' : deptId, 'bizMenuCd' : cellValues}),
			headers : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			success : function(response) {
			alert(response + "건의 메뉴권한을 삭제하였습니다.");
			
			},
			error : function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:"
						+ request.responseText + "\n" + "error:" + error);
			}
		}).done(function() {
			$('#table2').jqGrid('GridUnload'); // 그리드 지우기 
			getDeptMenuPermit();

		});
	}
});

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
</script>

			<div class="sec-right">
				<!-- 상단 경로 및 페이지 타이틀 -->
				<div class="top-bar">
					<div class="path-wrap">
						<span class="home">홈</span>&nbsp;&nbsp;&gt;
						<span class="path">사용자 및 권한 관리</span>&nbsp;&nbsp;&gt;
						<span class="path">권한 관리</span>&nbsp;&nbsp;&gt;
						<span class="now">권한 편집(등록)</span>
					</div>
					<div class="clearfix">
						<p class="tit-page">권한목록 조회</p>
					</div>
				</div>
				<!-- //상단 경로 및 페이지 타이틀 -->

				<!-- 컨텐츠 영역 -->
				<div class="cont">
					<!-- 표 상단 설정 영역 -->
			
				<div>
					<div class="setting-box">
					<p class="tit">
						<i class="fa fa-cog fa-lg"></i>부서선택
					</p>
				</div>
				<label for="deptNameOne" class="label">실/국</label> 
				<select id="deptNameOne" style="width: 100px;">
				</select>
				<label for="deptNameTwo" class="label">실과</label> 
				<select id="deptNameTwo" style="width: 100px;">
				</select>
				<a href="#" class="btn sr2 st4" id="srchBtn">선택</a>
				</div>

					<!-- 권한-메뉴 설정-->
					<div class="setting-box">
						<p class="tit">
							<i class="fa fa-cog fa-lg"></i>메뉴권한 설정
							<button class="btn-drop">보기/닫기</button>
						</p>
						<div class="drop-slide">
							<div class="left-box">
								<p class="tit">메뉴리스트</p>
								<div class="topwrap">
									<label for="categoryL1" class="label">카테고리(대)</label> 
									<select id="categoryL1" class="ip sr1 st1 w5">
									</select>
									<label for="categoryM1" class="label">카테고리(중)</label> 
									<select id="categoryM1" class="ip sr1 st1 w5">
									</select>
									<label for="categoryS1" class="label">카테고리(소)</label> 
									<select id="categoryS1" class="ip sr1 st1 w5">
									</select>
								</div>
								<div class="inwrap">
								<table id="table1"></table>
								</div>
							</div>

							<div class="ctr-box">
								<a href="#" class="btn sr2 st7" id="saveBtn">
									추가<i class="fa fa-arrow-right" aria-hidden="true"></i>
								</a>
								<a href="#" class="btn sr2 st6" id="removeBtn">
									<i class="fa fa-arrow-left" aria-hidden="true"></i>삭제
								</a>
							</div>
							
							<div class="right-box">
								<p class="tit"><span id="deptNm" style="margin-top: -2px; display: inline-block;">권한</span>에 부여된 메뉴</p>
								<div class="topwrap">
									<label for="categoryL2" class="label">카테고리(대)</label> 
									<select id="categoryL2" class="ip sr1 st1 w5">
									</select>
									<label for="categoryM2" class="label">카테고리(중)</label> 
									<select id="categoryM2" class="ip sr1 st1 w5">
									</select>
									<label for="categoryS2" class="label">카테고리(소)</label> 
									<select id="categoryS2" class="ip sr1 st1 w5">
									</select>
								</div>
								<div class="inwrap">
									<table id="table2"></table>
								</div>
							</div>
						</div>
					</div>
					<!--// 권한 설정-->
					
				</div>
				<!-- //컨텐츠 영역 -->
				
			</div>
    </div>
    <!--//container -->
</div>
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
</body>
</html>