<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/head.jsp" %>
<style>
    #popup {
        max-width: 800px;
        z-index: 1000;
        display: none;
    }

    .blocker {
        z-index: 9999;
    }
</style>
<script type="text/javascript">
$(document).ready(function(){
    var $popupForm = $('#popupForm');
    initGrid($('#grid'));

    $('#btnSearch').on('click', function(){
        $('#grid').jqGrid("setGridParam",{
            datatype: "json"
            ,page: 1
            ,postData: $("form[name='searchVO']").serializeObject()
            ,mtype: "POST"
        }).trigger("reloadGrid");
    });

    // 엑셀 다운로드
    $('#btnExcel').on('click', function(){
        if(confirm('데이터를 엑셀로 내려받으시겠습니까?')) {
            CM.downloadFileWithIFrame($('body'), '<c:url value="/data/upload/bigDataUploadExcelList.do"/>');
        }
    });

    function initGrid($grid) {
        var colModels = [], colNameList = [], colIndex = 0;

        // 숨겨진 필드
        colModels[colIndex++]={name: "useYn", index: "use_yn", comments: "사용여부", width: 40, align: "center", hidden: true};
        colModels[colIndex++]={name: "createId", index: "create_id", comments: "생성자아이디", width: 40, align: "center", hidden: true};
        colModels[colIndex++]={name: "createDt", index: "create_dt", comments: "생성일자", width: 40, align: "center", hidden: true};
        colModels[colIndex++]={name: "updateId", index: "update_id", comments: "수정자아이디", width: 40, align: "center", hidden: true};
        colModels[colIndex++]={name: "rmk", index: "rmk", comments: "비고", width: 40, align: "center", hidden: true};
        colModels[colIndex++]={name: "upldFileEncoding", index: "upld_file_encoding", comments: "업로드파일인코딩", width: 60, align: "center", hidden: true};
        colModels[colIndex++]={name: "splitTableYm", index: "split_table_ym", comments: "DB테이블분리기준년월", width: 120, align: "left", hidden: true};
        colModels[colIndex++]={name: "scheduleHour", index: "schedule_hour", comments: "예약시간_시", width: 40, align: "center", hidden: true};
        colModels[colIndex++]={name: "scheduleMinute", index: "schedule_minute", comments: "예약시간_분", width: 40, align: "center", hidden: true};

        // 보여지는 필드
        colModels[colIndex++]={name: "regist", index: "regist", comments: "설정", width: 52, align: "center", hidden: false, sortable: false, formatter: function(cellvalue, options, rowObject){
            return '<button class="btn sr3 st3">설정</button>';
        }};
        colModels[colIndex++]={name: "tableId", index: "table_id", comments: "테이블ID", width: 150, align: "left", hidden: false};
        colModels[colIndex++]={name: "tableNm", index: "table_nm", comments: "데이터명", width: 250, align: "left", hidden: false};
        colModels[colIndex++]={name: "shceduleDay", index: "shcedule_day", comments: "예약일", width: 100, align: "center", hidden: false};
        colModels[colIndex++]={name: "insertStdym", index: "insert_stdym", comments: "입력기준년월(일)", width: 100, align: "center", hidden: false};
        colModels[colIndex++]={name: "upldFileNm", index: "upld_file_nm", comments: "업로드 파일명", width: 100, align: "left", hidden: false};
        colModels[colIndex++]={name: "scheduleState", index: "schedule_state", comments: "상태", width: 70, align: "center", hidden: false, sortable: false};
        colModels[colIndex++]={name: "regist", index: "regist", comments: "예약상태", width: 70, align: "center", hidden: false, sortable: false, formatter: function(cellvalue, options, rowObject){
            var state = '미등록';
            if(rowObject.useYn === 'n') {
                state = '<span style="color: #ff3e5e; font-weight: 700;">사용안함<span>';
            } else {
                if(cellvalue === 'y') {
                    state = '<span style="color: #5750ff; font-weight: 700;">예약중<span>';
                }
            }
            return state;
        }};
        colModels[colIndex++]={name: "description", index: "description", comments: "예약설명", width: 400, align: "left", hidden: false};

        for ( var i = 0; i < colModels.length; i++) {
            colNameList[i] = colModels[i].comments;
        }

        $grid.jqGrid({
            url: '<c:url value="/data/upload/bigDataUploadListJson.do"/>',
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
            sortname: 'table_id',
            sortorder: "desc",
            viewrecords: true,
            rownumbers: true,
            loadtext: "검색 중입니다.",
            emptyrecords: "검색된 데이터가 없습니다.",
            recordtext: "총 {2} 건 데이터 ({0}-{1})",
            jsonReader: {root: "rows", repeatitems: false},
            onCellSelect: function(rowid, index, contents, event) {
                // 설정 셀 번호 10
                if(index === 10) {
                    var row = $(this).jqGrid('getRowData', rowid);
                    var tableId = row['tableId'];

                    if(tableId) {
                        $('#popup').modal();
                        $('#tableId').val(tableId);
                        $popupForm.attr('target', 'bigdataMngPopup');
                        $popupForm.attr('action', '<c:url value="/data/upload/bigDataUploadPopup.do"/>');
                        $popupForm.submit();

                    }
                }
            }
        }).navGrid("#gridPager", {edit: false, add: false, del: false, search: false, refresh: false});
    }
})
</script>
<body>
<div id="wrap">
    <%--헤더--%>
    <%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/layout/header.jsp" %>
    <div id="container">
        <%-- left 메뉴 --%>
        <jsp:include page="/com/leftMenu.do?type=data&menu=bigDataUpload"/>
        <div class="sec-right">
            <!-- 상단 경로 및 페이지 타이틀 -->
            <div class="top-bar">
                <div class="path-wrap">
                    <span class="home">홈</span>&nbsp;&nbsp;&gt;
                    <span class="path">데이터 관리</span>&nbsp;&nbsp;&gt;
                    <span class="now">대용량데이터 업로드 관리</span>
                </div>
                <div class="clearfix">
                    <p class="tit-page">대용량데이터 업로드 관리</p>
                </div>
            </div>
            <!-- //상단 경로 및 페이지 타이틀 -->

            <!-- 컨텐츠 영역 -->
            <div class="cont">
                <!-- 표 상단 설정 영역 -->
                <div class="tbl-top">
                    <form id='searchVO' method="post" name='searchVO'>
                        <fieldset>
                            <div class="right">
                                <label>
                                    <select class="ip sr1 st1 w1" name="searchField">
                                        <option value="table_id">테이블명</option>
                                    </select>
                                </label>
                                <label>
                                    <input type="text" class="ip sr1 st1 w2" data-for="btnSearch" name='searchString'/>
                                </label>
                                <input id='btnSearch' type="button" class="btn sr2 st2" value="조회"/>
                                <input id='btnExcel' type="button" class="btn sr2 st6" value="엑셀"/>
                                <input type="text" style="display: none"/>
                            </div>
                        </fieldset>
                    </form>
                </div>
                <!-- //표 상단 설정 영역 -->
                <table id="grid"></table>
                <div id="gridPager"></div>
                <!-- //페이징 -->
            </div>
            <!-- //컨텐츠 영역 -->
        </div>
        <!--//container -->
    </div>
</div>
<div style="display: none;">
    <form id="popupForm">
        <input type="hidden" id="tableId" name="tableId"/>
    </form>
    <div id='popup'>
        <iframe name='bigdataMngPopup' width="750" height="390"></iframe>
    </div>
</div>
</body>
</html>