<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/head.jsp" %>
<script type="text/javascript" src="<c:url value='/cmm/validator.do'/>"></script>
<validator:javascript formName="dataTableInfoVO" staticJavascript="false" xhtml="true" cdata="false"/>
<script type="text/javascript">
$(document).ready(function() {
    // 파일 업로드 후 파라미터 체크
    switch('${code}') {
        case '0' : alert('파일 업로드 중 문제가 발생했습니다. 업로드 파일 확인 후 다시 업로드 해주세요'); break;
        case '1' : alert('정상적으로 파일을 업로드했습니다.'); break;
        default : break;
    }

    var insertStdYmdYn = $('#tableId option:selected').data('insertStdYmdYn');
    insertStdYmdYnShowHide(insertStdYmdYn);

    if(insertStdYmdYn === 'y') {
        chnageDatepicker($('#tableId option:selected').data('mngStdYmdScd'))
    }

    $('#tableId').on('change', function(){
        $('#insertStdYmd').remove();

        var insertStdYmdYn = $('#tableId option:selected').data('insertStdYmdYn');
        insertStdYmdYnShowHide(insertStdYmdYn);

        if(insertStdYmdYn === 'y') {
            chnageDatepicker($('#tableId option:selected').data('mngStdYmdScd'))
        }
    });

    /**
     * stdymd 년월일
     * stdym 년월
     * stdy 년
     * @param mngStdYmdScd
     */
    function chnageDatepicker(mngStdYmdScd) {
        switch(mngStdYmdScd) {
            case 'stdymd' :
                var $input = createDateInput();
                $('#insertStdYmdYnRow td').append($input);
                CM.createDatepicker($input.attr('id'));
                break;
            case 'stdym':
                var $input = createDateInput();
                $('#insertStdYmdYnRow td').append($input);
                CM.createMonthpicker($input.attr('id'));
                break;
            case 'stdy':
                var $select = createDateSelect();
                $('#insertStdYmdYnRow td').append($select);
                break;
        }

        /**
         * input을 생성한다.
         *
         * @returns {Mixed|jQuery|HTMLElement}
         */
        function createDateInput() {
            var $input = $('<input/>',{
                id: 'insertStdYmd',
                name: 'insertStdYmd',
                type: 'text',
                class: 'ip sr1 st1 w1 cald'
            });
            return $input;
        }

        /**
         * select를 생성한다.
         * @returns {Mixed|jQuery|HTMLElement}
         */
        function createDateSelect() {
            var $select = $('<select/>', {
                id: 'insertStdYmd',
                name: 'insertStdYmd',
                class: 'ip sr1 st1 w1'
            });

            var date = new Date();
            var year = date.getFullYear();

            var begin = year - 10;
            var end = year + 10;

            for(var i = begin; i < end; i++) {
                var $option = $('<option/>', {
                    value: i,
                    text: i
                });

                // 해당 년도 자동 선택
                if(i === year) {
                    $option.prop('selected', true);
                }
                $select.append($option);
            }
            return $select;
        }
    }

    /**
     * 기준년월 show/hide
     * @param insertStdYmdYn
     */
    function insertStdYmdYnShowHide(insertStdYmdYn) {
        if(insertStdYmdYn === 'n') {
            $('#insertStdYmdYnRow').hide();
        } else {
            $('#insertStdYmdYnRow').show();
        }
    }

    // 엑셀 표준 양식 다운로드
    $('#standardExcelDownload').on('click', function(){
        var $tableInfo = $('#tableId option:selected');
        var datas = {
            tableId: $tableInfo.val(),
            tableNm: $tableInfo.text()
        };

        CM.downloadFileWithIFrame($('body'), "<c:url value="/data/upload/standardExcelDownload.do"/>", datas);
    });

    // 엑셀 파일 업로드
    $('#btnSave').on('click', function () {
        if ($('#insertStdYmdYn').length > 0 && !$('#insertStdYmdYn').val()) {
            alert('기준년월을 입력해주세요.');
            $('#insertStdYmdYn').focus();
            return;
        }

        var $dataTableInfoVO = $('#dataTableInfoVO');
        if (!$('#file').val()) {
            alert('파일을 선택해주세요.');
            $('#file').focus();
            return false;
        } else {
            var fileName = $('#file').val();
            var ext = fileName.slice(fileName.indexOf(".") + 1).toLowerCase();

            if(["xls", "xlsx"].indexOf(ext) < 0) {
                alert('엑셀 파일만 첨부할 수 있습니다.');
                return false;
            }
        }

        if(confirm('엑셀 파일을 업로드 하시겠습니까?')) {
            // 페이지를 리로드 하기때문에 mask를 걸어주기만 하면 된다.
            CM.showMask();
            $dataTableInfoVO.attr('action', '<c:url value="/data/upload/fileDataUploadAdd.do"/>');
            $dataTableInfoVO.submit();
        }
    });
});
</script>
<body>
<div id="wrap">
    <%--헤더--%>
    <%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/layout/header.jsp" %>
    <div id="container">
        <%-- left 메뉴 --%>
        <jsp:include page="/com/leftMenu.do?type=data&menu=fileDataUpload"/>
        <div class="sec-right">
            <!-- 상단 경로 및 페이지 타이틀 -->
            <div class="top-bar">
                <div class="path-wrap">
                    <span class="home">홈</span>&nbsp;&nbsp;&gt;
                    <span class="path">데이터 관리</span>&nbsp;&nbsp;&gt;
                    <span class="now">파일데이터 업로드 관리</span>
                </div>
                <div class="clearfix">
                    <p class="tit-page">파일데이터 업로드 관리(엑셀파일)</p>
                </div>
            </div>
            <!-- //상단 경로 및 페이지 타이틀 -->

            <!-- 컨텐츠 영역 -->
            <div class="cont">
                <!-- 표 상단 설정 영역 -->
                <div class="tbl-top">
                    <form method="post" action="#">
                        <fieldset>
                            <legend>파일데이터 업로드(엑셀파일)</legend>
                            <div class="right">
                                <a id='btnSave' href="#" onclick="return false;" class="btn sr2 st5">저장</a>
                            </div>
                        </fieldset>
                    </form>
                </div>
                <!-- //표 상단 설정 영역 -->
                <form id="dataTableInfoVO" name="dataTableInfoVO" method="post" enctype="multipart/form-data">
                    <table class="tbl sr1 st1">
                        <caption class="hide">파일데이터</caption>
                        <colgroup>
                            <col style="width:20%"/>
                            <col style="width:auto"/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="row" class="th2"><span class="star">필수</span>테이블 선택</th>
                            <td class="align-l">
                                <label>
                                    <select id='tableId' name="tableId" class="ip sr1 st1 w3">
                                        <c:forEach items="${items}" var="item">
                                            <option value="${item.tableId}" data-insert-std-ymd-yn="${item.insertStdYmdYn}" data-mng-std-ymd-scd="${item.mngStdYmdScd}">${item.tableNm}</option>
                                        </c:forEach>
                                    </select>
                                </label>
                                <span class="ment">
                                    <a id='standardExcelDownload' href="#" style="vertical-align: middle" onclick="return false;">※ 업로드 표준양식 다운로드</a>
                                </span>
                            </td>
                        </tr>
                        <tr id="insertStdYmdYnRow">
                            <th scope="row" class="th2"><span class="star">필수</span>기준년월</th>
                            <td class="align-l"></td>
                        </tr>
                        <%--<tr>
                            <th scope="row" class="th2"><span class="star">필수</span>업로드 방법</th>
                            <td class="align-l">
                                <label>
                                    <select class="ip sr1 st1 w2">
                                        <option value="">추가 적제</option>
                                        <option value="">삭제 후 적재</option>
                                    </select>
                                </label>
                            </td>
                        </tr>--%>
                        <tr>
                            <th scope="row" class="th2"><span class="star">필수</span>파일선택</th>
                            <td class="align-l">
                                <input id='file' type="file" name='file' class="ip sr1 st1 w4"/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </form>
            </div>
            <!-- //컨텐츠 영역 -->
        </div>
        <!--//container -->
    </div>
</div>
</body>
</html>