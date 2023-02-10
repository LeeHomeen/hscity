<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/head.jsp" %>
<script>
$(document).ready(function() {
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
	
	function fnGetDeptList() {
		var deptId = "";
		if ($("#deptNameTwo").val() != "") {
			deptId = $("#deptNameTwo").val();
		} else {
			deptId = $("#deptNameOne").val();
		}
		$.ajax({
			url : "/menu/getDeptList.do",
			data: JSON.stringify({'deptId' : deptId}),
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
	
	function fnGetMappingDeptList() {
		$.ajax({
			url : "/menu/getMappingDeptList.do",
			data: JSON.stringify({'menuCd' : "${categoryVo.menuCd}"}),
			type : "POST",
			dataType : 'json',
			headers : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			success : function(response) {
				$('#table2').jqGrid('GridUnload');
				setJqGrid('table2', response);
				fnGetMappingUserList();
			},
			error : function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:"
						+ request.responseText + "\n" + "error:"
						+ error);
			}
		});		
	}
	
	function setJqGrid(gridId, dataP) {
		theGrid = $("#" + gridId),
		numberTemplate = {
			formatter : 'number',
			align : 'right',
			sorttype : 'number'
		};

		theGrid.jqGrid({
			datatype : 'local',
			data : dataP,
			colNames : [ '실/국', '실과', 'deptId', 'deptOrder', 'userCnt'],
			colModel : [ {
				name : 'gbnName',
				index : 'gbnName',
				width : '65',
				align : 'center'
			}, {
				name : 'deptName',
				index : 'deptName',
				width : '65',
				align : 'center'
			}, {
				name : 'deptId',
				index : 'deptId',
				width : '70',
				align : 'center',
				hidden : true
			}, {
				name : 'deptOrder',
				index : 'deptOrder',
				width :  '70',
				align : 'center',
				hidden : true
			}, {
				name : 'userCnt',
				index : 'userCnt',
				width :  '70',
				align : 'center',
				hidden : true
			}],
			multiselect : true,
			autowidth : true,
			gridview : true,
			rownumbers : true,
			rowNum : 10,
			rowList : [ 5, 10, 15 ],
			pager : '#GridPager',
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
	
	function fnAddDeptMap(){
		var $grid = $("#table1"), selIds = $grid.jqGrid("getGridParam", "selarrrow"), i, n, cellValues = [];
		for (i = 0, n = selIds.length; i < n; i++) {
		    cellValues.push($grid.jqGrid("getCell", selIds[i], "deptId"));
		}
		$.ajax({
			url : "/menu/addDeptMap.do",
			type : "POST",
			dataType : 'json',
			data: JSON.stringify({'menuCd' : "${categoryVo.menuCd}" , 'deptId' : cellValues}),
			headers : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			success : function(response) {
			alert(response + "건의 새로운 메뉴가 추가되었습니다.");
			
			},
			error : function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:"
						+ request.responseText + "\n" + "error:" + error);
			}
		}).done(function() {
			$('#table2').jqGrid('GridUnload');
			fnGetMappingDeptList();
			fnGetUserList();
		});
	}
	
	function fnRemoveDeptMap(){
		var $grid = $("#table2"), selIds = $grid.jqGrid("getGridParam", "selarrrow"), i, n, cellValues = [];
		var userCntCheck = 0;
		for (i = 0, n = selIds.length; i < n; i++) {
		    cellValues.push($grid.jqGrid("getCell", selIds[i], "deptId"));
		    userCntCheck += Number($grid.jqGrid("getCell", selIds[i], "userCnt"));
		}
		
		if (userCntCheck>0) {
			if (confirm("사용자 매핑 테이블에 존재하는 항목이 포함되어 있습니다. \n계속 진행하시겠습니까?")) {
				$.ajax({
					url : "/menu/removeDeptMap.do",
					type : "POST",
					dataType : 'json',
					data: JSON.stringify({'menuCd' : "${categoryVo.menuCd}" , 'deptId' : cellValues}),
					headers : {
						'Content-Type' : 'application/json; charset=UTF-8'
					},
					success : function(response) {
						alert(response + "건의 메뉴를 삭제하였습니다.");
					},
					error : function(request, status, error) {
						alert("code:" + request.status + "\n" + "message:"
								+ request.responseText + "\n" + "error:" + error);
					}
				}).done(function() {
					$('#table2').jqGrid('GridUnload'); // 그리드 지우기 
					fnGetMappingDeptList();
					fnGetUserList();
					fnGetMappingUserList();
				});
			}
		} else {
			$.ajax({
				url : "/menu/removeDeptMap.do",
				type : "POST",
				dataType : 'json',
				data: JSON.stringify({'menuCd' : "${categoryVo.menuCd}" , 'deptId' : cellValues}),
				headers : {
					'Content-Type' : 'application/json; charset=UTF-8'
				},
				success : function(response) {
					alert(response + "건의 메뉴를 삭제하였습니다.");
				},
				error : function(request, status, error) {
					alert("code:" + request.status + "\n" + "message:"
							+ request.responseText + "\n" + "error:" + error);
				}
			}).done(function() {
				$('#table2').jqGrid('GridUnload'); // 그리드 지우기 
				fnGetMappingDeptList();
				fnGetUserList();
				fnGetMappingUserList();
			});
		}
	}
	
	function fnGetUserList() {
		$.ajax({
			url : "/menu/getUserList.do",
			data: JSON.stringify({'menuCd': "${categoryVo.menuCd}", 'searchType' : $("#searchType").val(), 'searchText' : $("#searchText").val()}),
			type : "POST",
			dataType : 'json',
			headers : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			success : function(response) {
				$('#table3').jqGrid('GridUnload');
				setJqGrid2('table3', response);
			},
			error : function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:"
						+ request.responseText + "\n" + "error:"
						+ error);
			}
		});		
	}
	
	function fnGetMappingUserList() {
		$.ajax({
			url : "/menu/getMappingUserList.do",
			data: JSON.stringify({'menuCd' : "${categoryVo.menuCd}"}),
			type : "POST",
			dataType : 'json',
			headers : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			success : function(response) {
				$('#table4').jqGrid('GridUnload');
				setJqGrid2('table4', response);
			},
			error : function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:"
						+ request.responseText + "\n" + "error:"
						+ error);
			}
		});		
	}
	
	function fnAddUserMap(){
		var $grid = $("#table3"), selIds = $grid.jqGrid("getGridParam", "selarrrow"), i, n, cellValues = [];
		for (i = 0, n = selIds.length; i < n; i++) {
		    cellValues.push({'deptId': $grid.jqGrid("getCell", selIds[i], "deptId"), 'userId': $grid.jqGrid("getCell", selIds[i], "userId")});
		}
		$.ajax({
			url : "/menu/addUserMap.do",
			type : "POST",
			dataType : 'json',
			data: JSON.stringify({'menuCd' : "${categoryVo.menuCd}" , 'userInfo' : cellValues}),
			headers : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			success : function(response) {
				alert(response + "건의 새로운 메뉴가 추가되었습니다.");
			},
			error : function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:"
						+ request.responseText + "\n" + "error:" + error);
			}
		}).done(function() {
			$('#table4').jqGrid('GridUnload');
			fnGetMappingDeptList();
			fnGetMappingUserList();
		});
	}
	
	function fnRemoveUserMap(){
		var $grid = $("#table4"), selIds = $grid.jqGrid("getGridParam", "selarrrow"), i, n, cellValues = [];
		for (i = 0, n = selIds.length; i < n; i++) {
		    cellValues.push({'deptId': $grid.jqGrid("getCell", selIds[i], "deptId"), 'userId': $grid.jqGrid("getCell", selIds[i], "userId")});
		}
		$.ajax({
			url : "/menu/removeUserMap.do",
			type : "POST",
			dataType : 'json',
			data: JSON.stringify({'menuCd' : "${categoryVo.menuCd}" , 'userInfo' : cellValues}),
			headers : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			success : function(response) {
				alert(response + "건의 메뉴를 삭제하였습니다.");
			},
			error : function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:"
						+ request.responseText + "\n" + "error:" + error);
			}
		}).done(function() {
			$('#table4').jqGrid('GridUnload'); // 그리드 지우기 
			fnGetMappingUserList();
		});
	}
	
	function setJqGrid2(gridId, dataP) {
		theGrid = $("#" + gridId),
		numberTemplate = {
			formatter : 'number',
			align : 'right',
			sorttype : 'number'
		};

		theGrid.jqGrid({
			datatype : 'local',
			data : dataP,
			colNames : [ '실/국', '실과', '아이디', '이름', '상태', 'deptId', 'deptOrder'],
			colModel : [ {
				name : 'gbnName',
				index : 'gbnName',
				width : '30',
				align : 'center'
			}, {
				name : 'deptName',
				index : 'deptName',
				width : '30',
				align : 'center'
			}, {
				name : 'userId',
				index : 'userId',
				width : '30',
				align : 'center'
			}, {
				name : 'userName',
				index : 'userName',
				width : '30',
				align : 'center'
			}, {
				name : 'userStatName',
				index : 'userStatName',
				width : '30',
				align : 'center'
			}, {
				name : 'deptId',
				index : 'deptId',
				width : '70',
				align : 'center',
				hidden : true
			}, {
				name : 'deptOrder',
				index : 'deptOrder',
				width :  '70',
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
	
	$("#btnDeptSearch").click(function() {
		fnGetDeptList();
	});
	
	$("#btnUserSearch").click(function() {
		fnGetUserList();
	});
	
	$("#btnAdd").click(function() {
		fnAddDeptMap();
	});
	
	$("#btnAddUser").click(function() {
		fnAddUserMap();
	});
	
	$("#btnRemoveUser").click(function() {
		fnRemoveUserMap();
	});
	
	$("#btnRemove").click(function() {
		fnRemoveDeptMap();
	});
	
	$(".btn-drop").trigger("click");
	$("#btnDeptSearch").trigger("click");
	$("#btnUserSearch").trigger("click");
	fnGetMappingDeptList();
});
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
					<span class="path">내부용 메뉴화면 관리</span>&nbsp;&nbsp;&gt;
					<span class="now">내부용 메뉴별 부서관리</span>
				</div>
				<div class="clearfix">
					<p class="tit-page">내부용 메뉴별 부서관리</p>
				</div>
			</div>
			<!-- //상단 경로 및 페이지 타이틀 -->

			<!-- 컨텐츠 영역 -->
			<div class="cont">					
				<!-- 표 상단 설정 영역 -->
				<div class="tbl-top">						
					<div class="right">
						<a href="/menu/biz/menuList.do" class="btn sr2 st2">목록보기</a>	
					</div>					
				</div>
				<!--// 표 상단 설정 영역 -->
				<table class="tbl sr1 st1">
					<caption class="hide">내부용 메뉴별 부서관리 표</caption>
					<colgroup>
						<col style="width:30%"/>
						<col style="width:20%"/>
						<col style="width:30%"/>
						<col style="width:20%"/>
					<tbody>
						<tr>
							<th scope="row" class="th2">카테고리</th>
							<th scope="row" class="th2">메뉴명</th>
							<th scope="row" class="th2">내용</th>
							<th scope="row" class="th2">화면유형</th>
						</tr>
						<tr>
							<td class="align-c border-td-r">1</td>
							<td class="align-c border-td-r">${categoryVo.menuNm}</td>
							<td class="align-c border-td-r">2</td>
							<td class="align-c">${categoryVo.menuTypeScd}</td>
						</tr>
						
					</tbody>
				</table>
			</div>
			
			<!-- 권한-메뉴 설정-->
			<div class="setting-box" style="padding-left: 30px;padding-right: 30px;">
				<p class="tit">
					<i class="fa fa-cog fa-lg"></i>메뉴권한 설정
					<button class="btn-drop">보기/닫기</button>
				</p>
				<div class="drop-slide">
					<div class="left-box">
						<div class="topwrap">
							<label for="deptNameOne" class="label">실/국</label> 
							<select id="deptNameOne" style="width:100px;">
							</select>
							<label for="deptNameTwo" class="label">실과</label> 
							<select id="deptNameTwo" style="width:100px;">
							</select>
							<a href="#" class="btn sr2 st4" id="btnDeptSearch">조회</a>
						</div>
						<div class="inwrap" style="height:200px;">
							<table id="table1"></table>
						</div>
					</div>

					<div class="ctr-box" style="height:200px;">
						<a href="#" class="btn sr2 st7" id="btnAdd">
							추가<i class="fa fa-arrow-right" aria-hidden="true"></i>
						</a>
						<a href="#" class="btn sr2 st6" id="btnRemove">
							<i class="fa fa-arrow-left" aria-hidden="true"></i>삭제
						</a>
					</div>
					
					<div class="right-box">
						<div class="topwrap" style="height:24px">
						</div>
						<div class="inwrap" style="height:200px;">
							<table id="table2"></table>
						</div>
					</div>
				</div>
			</div>
			<!--// 권한 설정-->
			<div class="setting-box" style="padding-left: 30px;padding-right: 30px;">
				<p class="tit">
					<i class="fa fa-cog fa-lg"></i>사용자 권한 설정
					<button class="btn-drop">보기/닫기</button>
				</p>
				
				
				<div class="drop-slide">
					<div class="left-box">
						<div class="topwrap">
							<label>
								<select id="searchType">
									<option value="userId">아이디</option>
									<option value="userName">사용자명</option>
								</select>
							</label>
							<label>
								<input type="text" class="ip sr1 st1 w2" id="searchText"/>
							</label> 
							<a href="#" class="btn sr2 st4" id="btnUserSearch">조회</a>
						</div>
						<div class="inwrap" style="height:200px;">
							<table id="table3"></table>
						</div>
					</div>

					<div class="ctr-box" style="height:200px;">
						<a href="#" class="btn sr2 st7" id="btnAddUser">
							추가<i class="fa fa-arrow-right" aria-hidden="true"></i>
						</a>
						<a href="#" class="btn sr2 st6" id="btnRemoveUser">
							<i class="fa fa-arrow-left" aria-hidden="true"></i>삭제
						</a>
					</div>
					
					<div class="right-box">
						<div class="topwrap" style="height:24px">
						</div>
						<div class="inwrap" style="height:200px;">
							<table id="table4"></table>
						</div>
					</div>
				</div>
				
				
				
			</div>
			<!-- //컨텐츠 영역 -->
		</div>
	</div>
	<!-- //container -->
</div>
<!-- //wrap -->
</body>
</html>