<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/head.jsp" %>
<script>
$(document).ready(function() {
	var treeObj = {
			originData: new Array(),
			fnInit: function(treeData, originData) {
				$.each(originData, function(idx, item){
					treeObj.originData.push(item);
				});
				//console.log("originData::: " + treeObj.originData.length);
				$("#dynatree").dynatree({
					children: treeData,
					autoCollapse: true,
					minExpandLevel: 4,
					autoFocus: true,
					onPostInit: function(isReloading, isError) {
						console.log("isReloading::" + isReloading);
						console.log("isError::" + isError);
						this.reactivate();
				    },
					onActivate: function(node) {
						$("#pubCategoryNm").val(node.data.title);
						$("#description").val(node.data.description);
						node.select(true);
						//console.log("isSelected??" + node.isSelected());
						//console.log($("#dynatree").dynatree("getSelectedNodes"));
						//console.log(node.hasChildren());
					},
					onDeactivate: function(node) {
				        node.select(false);
				    }
				});
			},
			/* 위로 */
			fnMoveUpNode: function() {
				if ($("#dynatree").dynatree("getSelectedNodes").length == 0) {
					alert("이동할 카테고리를 선택하세요.");
					return;
				}
				
				var selectedNode = $("#dynatree").dynatree("getSelectedNodes")[0];
				var prevNode = selectedNode.getPrevSibling();
				
				var selectedNodeSortNo = selectedNode.data.sortNo;
				var prevNodeSortNo = prevNode.data.sortNo;
				
				if (prevNode != null) {
					selectedNode.move(prevNode, 'before');
					selectedNode.data.sortNo = prevNodeSortNo;
					prevNode.data.sortNo = selectedNodeSortNo;
				}
			},
			/* 아래로 */
			fnMoveDownNode: function() {
				if ($("#dynatree").dynatree("getSelectedNodes").length == 0) {
					alert("이동할 카테고리를 선택하세요.");
					return;
				}
				
				var selectedNode = $("#dynatree").dynatree("getSelectedNodes")[0];
				var nextNode = selectedNode.getNextSibling();
				
				var selectedNodeSortNo = selectedNode.data.sortNo;
				var nextNodeSortNo = nextNode.data.sortNo;
				
				if (nextNode != null) {
					selectedNode.move(nextNode, 'after');
					selectedNode.data.sortNo = selectedNodeSortNo;
					nextNode.data.sortNo = selectedNodeSortNo;
				}
			},
			/* 추가 */
			fnAddNode: function() {
				if ($("#dynatree").dynatree("getSelectedNodes").length == 0) {
					alert("추가할 카테고리를 선택하세요.");
					return;
				}
				
				var selectedNode = $("#dynatree").dynatree("getSelectedNodes")[0];
				
				if (selectedNode.data.pubCategoryCd == "NEW") {
					alert("신규 추가된 카테고리는 저장 후 사용 가능합니다.");
					return;
				}
				/* if (selectedNode.data.level == 3) {
					alert("3Depth까지만 추가 가능합니다.");
					return;
				} */
				
				var newNode = {
						title : "메뉴명을 입력하세요.",
						pubCategoryCd: "NEW",
						upperPubCategoryCd: selectedNode.data.pubCategoryCd,
						level : Number(selectedNode.data.level) + 1,
						sortNo : 99,
						rmk : "",
						isFolder: true,
						crud: 'c',
				}
				selectedNode.addChild(newNode);
				$("#dynatree").dynatree("getTree").getNodeByKey(newNode.key).select();
			},
			/* 삭제 */
			fnDeleteNode: function() {
				if ($("#dynatree").dynatree("getSelectedNodes").length == 0) {
					alert("삭제할 카테고리를 선택하세요.");
					return;
				}
				
				var selectedNode = $("#dynatree").dynatree("getSelectedNodes")[0];
				
				if (selectedNode.data.includeMenuCnt > 0) {
					alert("메뉴가 포함된 카테고리는 삭제할 수 없습니다. \n메뉴 삭제 후 사용해주세요.");
					return;
				}
				
				if (selectedNode.hasChildren()) {
					if (confirm("하위 카테고리가 존재합니다. 삭제하시겠습니까?")) {
						alert("삭제된 카테고리는 우측의 저장 버튼 클릭 후 적용됩니다.");
						selectedNode.remove();						
					}
				} else {
					alert("삭제된 카테고리는 우측의 저장 버튼 클릭 후 적용됩니다.");
					selectedNode.remove();
				}
			}
	};

	/* 카테고리 데이터 가져오기 및 트리 구성 */
	fnGetTreeData();
	
	
	$('#nodeUp').on('click', function(event) {
		treeObj.fnMoveUpNode();
    });
	
	$('#nodeDown').on('click', function(event) {
		treeObj.fnMoveDownNode();
    });
	
	$('#nodeAdd').on('click', function(event) {
		treeObj.fnAddNode();
    });
	
	$('#nodeDel').on('click', function(event) {
		treeObj.fnDeleteNode();
    });
	
	$('#save').on('click', function(event) {
		fnSaveData();
    });
	
	$('#cancel').on('click', function(event) {
		fnGetTreeData();
		$("#dynatree").dynatree("getTree").reload();
    });
	
	$('#pubCategoryNm').on('click', function(event) {
		if ($('#pubCategoryNm').val() == "메뉴명을 입력하세요.") {
			$('#pubCategoryNm').val("");
		}
    });
	
	$('#pubCategoryNm').on('keyup', function(event) {
		if ($("#dynatree").dynatree("getSelectedNodes").length == 0) {
			alert("카테고리를 선택하세요.");
			return false;
		}
		
		var selectedNode = $("#dynatree").dynatree("getSelectedNodes")[0];
		var preTitle = selectedNode.data.title;
		/*
		if ($('#pubCategoryNm').val().length == 0) {
			alert("카테고리명은 빈 값으로 설정할 수 없습니다.");
			selectedNode.setTitle(preTitle);
			$('#pubCategoryNm').val(preTitle);
		} else {
			selectedNode.setTitle($('#pubCategoryNm').val());			
		}
		*/
		selectedNode.setTitle($('#pubCategoryNm').val());
    });
	
	$('#description').on('keyup', function(event) {
		if ($("#dynatree").dynatree("getSelectedNodes").length == 0) {
			alert("카테고리를 선택하세요.");
			return false;
		}
		var selectedNode = $("#dynatree").dynatree("getSelectedNodes")[0];
		selectedNode.data.description = $('#description').val(); 
    });	
	
	
	/* 카테고리 데이터 가져오기 */
	function fnGetTreeData () {
		$.ajax({
		    url: "/menu/getCategoryData.do",
		    type : "GET",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    //data: JSON.stringify({ 'categoryType': 'External' })
		    data: { 'categoryType': 'Pub' },
		    success: function(response) {
		    	var dynaRootLst = new Array();
		    	var dyna1DepthLst = new Array();
				var dyna2DepthLst = new Array();
				var dyna3DepthLst = new Array();
				$.each(response.result, function(idx, item){
					if (item.level == 0) {
						dynaRootLst.push({
							title : item.pubCategoryNm,
							pubCategoryCd: item.pubCategoryCd,
							upperPubCategoryCd: item.upperPubCategoryCd,
							level : item.level,
							description : item.description,
							rmk : item.rmk,
							sortNo: item.sortNo,
							isFolder: false,
							crud: 'r',
							children: []
						});
					}
					if (item.level == 1) {
						dyna1DepthLst.push({
							title : item.pubCategoryNm,
							pubCategoryCd: item.pubCategoryCd,
							upperPubCategoryCd: item.upperPubCategoryCd,
							level : item.level,
							description : item.description,
							rmk : item.rmk,
							sortNo: item.sortNo,
							isFolder: true,
							crud: 'r',
							includeMenuCnt : item.includeMenuCnt,
							children: []
						});
					}
					if (item.level == 2) {
						dyna2DepthLst.push({
							title : item.pubCategoryNm,
							pubCategoryCd: item.pubCategoryCd,
							upperPubCategoryCd: item.upperPubCategoryCd,
							level : item.level,
							description : item.description,
							rmk : item.rmk,
							sortNo: item.sortNo,
							isFolder: true,
							crud: 'r',
							includeMenuCnt : item.includeMenuCnt,
							children: []
						});
					}
					if (item.level == 3) {
						dyna3DepthLst.push({
							title : item.pubCategoryNm,
							pubCategoryCd: item.pubCategoryCd,
							upperPubCategoryCd: item.upperPubCategoryCd,
							level : item.level,
							description : item.description,
							rmk : item.rmk,
							sortNo: item.sortNo,
							isFolder: true,
							crud: 'r',
							includeMenuCnt : item.includeMenuCnt,
							children: []
						});
					}
		        });
				
				for (var i=0; i<dyna3DepthLst.length; i++) {
					for (var j=0; j<dyna2DepthLst.length; j++) {
						if (dyna3DepthLst[i].upperPubCategoryCd == dyna2DepthLst[j].pubCategoryCd) {
							dyna2DepthLst[j].children.push({
								title : dyna3DepthLst[i].title,
								pubCategoryCd: dyna3DepthLst[i].pubCategoryCd,
								upperPubCategoryCd: dyna3DepthLst[i].upperPubCategoryCd,
								level : dyna3DepthLst[i].level,
								description : dyna3DepthLst[i].description,
								rmk : dyna3DepthLst[i].rmk,
								sortNo: dyna3DepthLst[i].sortNo,
								isFolder: true,
								crud: 'r',
								includeMenuCnt : dyna3DepthLst[i].includeMenuCnt
							});
						}
					}
				}
				
				for (var i=0; i<dyna2DepthLst.length; i++) {
					for (var j=0; j<dyna1DepthLst.length; j++) {
						if (dyna2DepthLst[i].upperPubCategoryCd == dyna1DepthLst[j].pubCategoryCd) {
							dyna1DepthLst[j].children.push({
								title : dyna2DepthLst[i].title,
								pubCategoryCd: dyna2DepthLst[i].pubCategoryCd,
								upperPubCategoryCd: dyna2DepthLst[i].upperPubCategoryCd,
								level : dyna2DepthLst[i].level,
								description : dyna2DepthLst[i].description,
								rmk : dyna2DepthLst[i].rmk,
								sortNo: dyna2DepthLst[i].sortNo,
								isFolder: true,
								crud: 'r',
								includeMenuCnt : dyna2DepthLst[i].includeMenuCnt,
								children: dyna2DepthLst[i].children
							});
						}
					}
				}
				
				for (var i=0; i<dyna1DepthLst.length; i++) {
					dynaRootLst[0].children.push({
						title : dyna1DepthLst[i].title,
						pubCategoryCd: dyna1DepthLst[i].pubCategoryCd,
						upperPubCategoryCd: dyna1DepthLst[i].upperPubCategoryCd,
						level : dyna1DepthLst[i].level,
						description : dyna1DepthLst[i].description,
						rmk : dyna1DepthLst[i].rmk,
						sortNo: dyna1DepthLst[i].sortNo,
						isFolder: true,
						crud: 'r',
						includeMenuCnt : dyna1DepthLst[i].includeMenuCnt,
						children: dyna1DepthLst[i].children
					});
				}
				
				treeObj.fnInit(dynaRootLst, response.result);
	        },
	        error : function(request, status, error) { 
	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        	}
		}).done(function() { 
			//console.log("done");
		});
	}
	
	/* 카테고리 저장하기 */
	function fnSaveData() {
		/* 저장 전 tree 담기 */
		var saveData = [];
		var checkSaveDataCd = new Array();
		var checkEmpty = 0;
		//추가 및 업데이트된 데이터
		$("#dynatree").dynatree("getRoot").visit(function(node){
		    //console.log(node.data);
		    if (node.data.title == "") checkEmpty++;
		    saveData.push({
		    	pubCategoryCd: node.data.pubCategoryCd, 
		    	pubCategoryNm: node.data.title, 
		    	upperPubCategoryCd: node.data.upperPubCategoryCd, 
		    	level: node.data.level,
		    	description: node.data.description, 
		    	rmk: "",
		    	sortNo: node.data.sortNo,
		    	useYn: "y",
		    	crud: node.data.crud
		    });
		    checkSaveDataCd.push(node.data.pubCategoryCd);
		});
		
		if (checkEmpty>0) {
			alert("카테고리명에 빈 값이 존재합니다.");
			return;
		}
		
		//삭제된 데이터
		$.each(treeObj.originData, function(idx, item){
			//if (!checkSaveDataCd.includes(item.pubCategoryCd)) {
			if (checkSaveDataCd.indexOf(item.pubCategoryCd) == -1) {
				if (item.crud != "c") {
					saveData.push({
				    	pubCategoryCd: item.pubCategoryCd, 
				    	pubCategoryNm: item.pubCategoryNm, 
				    	upperPubCategoryCd: item.upperPubCategoryCd, 
				    	level: item.level,
				    	description: item.description, 
				    	rmk: "",
				    	sortNo: item.sortNo,
				    	useYn: "n",
				    	crud: "d"
				    });					
				}
			}
		});
		
		/* sorting */
		for(var i=0; i<saveData.length; i++) {
			saveData[i].sortNo = i;
		}
		
		$.ajax({
		    url: "/menu/saveCategoryData.do",
		    type : "POST",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    //data: { 'categoryType': 'Pub', 'saveDataList': saveData },
		    data: JSON.stringify({'categoryType': 'Pub', 'saveDataList': saveData}),
		    success: function(response) {
		    	console.log(response.isOk);
		    	if (response.isOk == "ok") {
		    		alert("정상적으로 저장되었습니다.");
		    		location.reload();
		    	}
	        },
	        error : function(request, status, error) { 
	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        	}
		}).done(function() { 
			console.log("done");
		});
	}
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
					<span class="now">대시민 카테고리 관리</span>
				</div>
				<div class="clearfix">
					<p class="tit-page">대시민 카테고리 관리</p>
					<ul class="btn-wrap">
	
					</ul>
				</div>
			</div>
			<!-- //상단 경로 및 페이지 타이틀 -->

			<!-- 컨텐츠 영역 -->
			<div class="cont">
				<form id="abcd" name="aaa" method="post">							
					<fieldset> 						
						<!-- 트리 영역 -->
						<div class="tree-wrap">
							<div class="btn-wrap2">
								<a href="#" id="nodeUp" class="btn sr3 st2">위로</a>
								<a href="#" id="nodeDown" class="btn sr3 st5">아래로</a>
								<a href="#" id="nodeAdd" class="btn sr3 st7">추가</a>
								<a href="#" id="nodeDel" class="btn sr3 st6">삭제</a>
							</div>
							<p class="ment">※카테고리는 최대3depth 까지만 가능합니다.</p>
							
							<div class="tree-box">
								<div class="tree-area">
									<!--  <p class="tree-title"><span class="tree_ico_monitor"></span>&nbsp;대시민 카테고리</p>-->
									<div id="dynatree">
									</div>
								</div>
							</div>
						</div>
						<!--//트리 영역 -->

						<!--카테고리 입력 영역 -->
						<div class="tbl-box">
							<table class="tbl sr1 st1" style="margin-top:0px;">
								<colgroup>
									<col style="width:20%">
									<col style="width:auto">
								</colgroup>
								<tbody>
									<tr>
										<th scope="row" class="th2">상위카테고리</th>
										<td class="align-l">
											<span id="upperPubCategory">대시민 카테고리</span>
										</td>										
									</tr>
									<tr>
										<th scope="row" class="th2">카테고리명</th>
										<td>
											<input type="text" id="pubCategoryNm" class="ip sr1 st1 w3" maxlength="30" />
										</td>										
									</tr>
									<tr>
										<th scope="row" class="th2">설명</th>
										<td>
											<input type="text" id="description" class="ip sr1 st1 w3" maxlength="100" />
										</td>										
									</tr>
								</tbody>
							</table>

							<div class="btn-wrap">
								<a href="#" id="save" class="btn sr2 st2">저장</a>
								<a href="#" id="cancel" class="btn sr2 st5">취소</a>
							</div>
							<!--//btn-wrap -->									
						</div>
						<!--//카테고리 입력 영역 -->
					</fieldset> 
				</form>				
			</div>
			<!-- //컨텐츠 영역 -->
		</div>
		<!--  sec-right -->
	</div>
	<!-- container -->
</div>
<!-- wrap -->
</body>
</html>