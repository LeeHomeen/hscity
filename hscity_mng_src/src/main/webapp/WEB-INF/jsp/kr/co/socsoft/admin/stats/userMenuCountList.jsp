<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/head.jsp" %>
<script type="text/javascript">
$(document).ready(function(){
	/* 콤보박스 : 대분류 */
	$.ajax({
	    url: "/menu/getCategoryGroupList.do",
	    type : "GET",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    data: {categoryType: "biz", upperCategoryCd: ""},
	    success: function(response) {
	    	if (response.length != 0) {
	    		var html = "<option value='' selected>-대분류-</option>";
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
		//console.log("대분류바뀐겨?????");
		$.ajax({
		    url: "/menu/getCategoryGroupList.do",
		    type : "GET",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    data: {categoryType: "biz", upperCategoryCd: $('#category1depth').val()},
		    success: function(response) {
		    	//console.log("리스폰스왔어?::",response);
		    		var html = "<option value='' selected>-중분류-</option>";
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
    
	/* 콤보박스 : 세분류 */
	$('#category2depth').on('change', function(event) {
		$.ajax({
		    url: "/menu/getCategoryGroupList.do",
		    type : "GET",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    data: {categoryType: "biz", upperCategoryCd: $('#category2depth').val()},
		    success: function(response) {
	    		var html = "<option value='' selected>-세분류-</option>";
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
    
	
	
	
	var $grid = $('#grid');

    CM.createDatepickerLinked('sDate', 'eDate', 8);

    // 현재 날짜 기준으로 검색 기간을 설정한다
    searchDateTerm(1);
	
    initGrid($grid);

    $('#btnSearch').on('click', function(){
        $grid.jqGrid("setGridParam",{
            datatype: "json"
            ,page: 1
            ,postData: $("form[name='searchVO']").serializeObject()
            ,mtype: "POST"
        }).trigger("reloadGrid");
    });

    // 엑셀 다운로드
    $('#btnExcel').on('click', function(){
        if(confirm('데이터를 엑셀로 내려받으시겠습니까?\n데이터 건수가 많을 경우 오래 걸릴 수 있습니다.')) {
            // var totCount = $grid.getGridParam("records");
            CM.downloadFileWithIFrame($('body'), '<c:url value="/stats/userMenu/getUserMenuCountExcel.do"/>', $('#searchVO').serializeObject());
           
        }
    });

    // BI 시각화
    $('#btnBi').on('click', function(){       
    	if('${biUrl}' ==''){
    		alert("서비스 준비중입니다.");
    	}else{    
			$.ajax({
			    url: "/tableau/goTableauTicket.do",
			    type : "GET",
			    dataType: 'json',
			    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
			    success: function(response) {
			    	console.log(response);
			    	
			    	var ticket = "trusted/" + response.ticket + "/";
			    	var biServerUrl = response.TableauUrl;
			    	var biMenuUrl = '${biUrl}';
	
			    	var url = biMenuUrl.replace(biServerUrl, biServerUrl + ticket+"/");
	
			    	window.open(url);
			    },
		        error : function(request, status, error) { 
		        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		    	}
			});
    	}
    });
    
    function initGrid($grid) {
        var colModels = [], colNameList = [], colIndex = 0;


        // 보여지는 필드
        colModels[colIndex++]={name: "logSeq", index: "log_seq", comments: "순번", width: 50, align: "center", hidden: false};
        colModels[colIndex++]={name: "connDt", index: "conn_dt", comments: "접속일자", width: 150, align: "center", hidden: false};
        colModels[colIndex++]={name: "categoryPath", index: "category_path", comments: "카테고리", width: 300, align: "left", hidden: false};
        colModels[colIndex++]={name: "menuNm", index: "menu_nm", comments: "메뉴명", width: 250, align: "left", hidden: false};
        colModels[colIndex++]={name: "menuCd", index: "menu_cd", comments: "메뉴코드", width: 80, align: "center", hidden: false};
        colModels[colIndex++]={name: "userId", index: "user_id", comments: "사용자ID", width: 80, align: "center", hidden: false};
        colModels[colIndex++]={name: "userNm", index: "user_nm", comments: "사용자이름", width: 80, align: "center", hidden: false};


        for ( var i = 0; i < colModels.length; i++) {
            colNameList[i] = colModels[i].comments;
        }

        $grid.jqGrid({
            url: '<c:url value="/stats/userMenu/getUserMenuCountList.do"/>',
            datatype: "json",
            mtype: 'get',
            postData: $("form[name='searchVO']").serializeObject(),
            colNames: colNameList,
            colModel: colModels,
            rowNum: 20,
            rowList: [10, 20, 30],
            pager: '#gridPager',
            height: "auto",
            width: 'auto',
            autowidth: true,
            shrinkToFit: false,
            sortname: 'log_seq',
            sortorder: "desc",
            viewrecords: true,
            rownumbers: false,
            loadtext: "검색 중입니다.",
            emptyrecords: "검색된 데이터가 없습니다.",
            recordtext: "총 {2} 건 데이터 ({0}-{1})",
            jsonReader: {root: "rows", repeatitems: false}
        }).navGrid("#gridPager", {edit: false, add: false, del: false, search: false, refresh: false});
    }
});

/**
 * 로그 검색 기간 설정
 * @param term 기간 월단위
 */
function searchDateTerm(term) {
    var today = new Date();
    var oneMonthsAgo = new Date(today.getFullYear(), today.getMonth() - term, today.getDate());
    $('#sDate').datepicker('setDate', oneMonthsAgo);
    $('#eDate').datepicker('setDate', today);
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
                    <span class="path">이용현황 통계 관리</span>&nbsp;&nbsp;&gt;
                    <span class="now">내부포털 메뉴접속통계</span>
                </div>
                <div class="clearfix">
                    <p class="tit-page">내부포털 메뉴접속통계</p>
                </div>
            </div>
            <!-- //상단 경로 및 페이지 타이틀 -->

            <!-- 컨텐츠 영역 -->
            <div class="cont">
                <!-- 표 상단 설정 영역 -->
             <div style="text-align: right;margin:10px auto;"> ※BI 시각화는 브라우저의 팝업이 허용되어 있는 상태에서 보실 수 있습니다. &nbsp;&nbsp; <input id='btnBi' type="button" class="btn sr2 st5" value="BI 시각화"/></div>
                <div class="tbl-top">
                    <form id='searchVO' method="post" name='searchVO'>
                        <fieldset>
                            <legend>표,게시판 정보</legend>
                            <div class="left">
                            
                                 <input id='sDate' name='sDate' type="text" class="ip sr1 st1 cald" value="" title="시작날짜" readonly/> -
                                <input id='eDate' name='eDate' type="text" class="ip sr1 st1 cald" value="" title="끝날짜" readonly/>
                                
                                &nbsp;&nbsp;
                            <label for="category2depth" class="label">카테고리</label>	
                               <select class="ip sr1 st1 w1" id="category1depth" name="category1depth" >
									<option value="">-대분류-</option>
								</select>																	
								
								<select class="ip sr1 st1 w1" id="category2depth" name="category2depth">
									<option value="">-중분류-</option>
								</select>									
			
								<select class="ip sr1 st1 w1" id="category3depth" name="category3depth">
									<option value="">-세분류-</option>
								</select>	
                            </div>

                            <div class="right">
                           
                                    <select class="ip sr1 st1 w1" name="searchField">
                                        <option value="menu_nm">메뉴명</option>
                                        <option value="user_id">사용자명</option>
                                        <option value="user_nm">사용자 이름</option>
                                    </select>
                                <label>
                                
                                    <input id="searchString" type="text" class="ip sr1 st1 w2" name='searchString' data-for="btnSearch" style="width:100px;" />
                                               
                                </label>
                                <input id='btnSearch' type="button" class="btn sr2 st2" value="조회"/>
                                <input id='btnExcel' type="button" class="btn sr2 st6" value="엑셀"/>
                                
                            </div>
                        </fieldset>
                    </form>
                </div>
                <!-- //표 상단 설정 영역 -->
                <table id="grid"></table>
                <div id="gridPager"></div>
            </div>
            <!-- //컨텐츠 영역 -->
        </div>
        <!--//container -->
    </div>
</div>
</body>
</html>