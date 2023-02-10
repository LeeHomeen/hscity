<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/common/head.jsp" %>
<script type="text/javascript">
$(document).ready(function(){
    var $boardVO = $('#boardVO');

    // 목록
    $('#btnList').on('click', function(){
        $boardVO.attr('action', '<c:url value="/internal/notice/list.do"/>');
        $boardVO.submit();
    });

    // 파일 다운로드
    $('.download-file').on('click', function(){
        var attFileMngSeq = $(this).data('attFileMngSeq');
        var fileSeq = $(this).data('fileSeq');

        CM.postFileDownload('<c:url value="/com/downloadFile.do"/>', {
            attFileMngSeq: attFileMngSeq,
            fileSeq: fileSeq
        });
    });
});
</script>
<body>
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/layout/header.jsp" %>
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/layout/right.jsp" %>

<div class="AllWrap">
    <jsp:include page="/internal/leftMenu.do"/>
    <!-- 콘텐츠영역 //-->
    <div class="contentWrap">
        <img src="<c:url value="/images/biz/btncate_close.png"/>" alt="카테고리 열기&닫기" class="cateBtn">

        <h3 class="stitle"><img src="<c:url value="/images/biz/icon_notice.png"/>" alt="" class="bl">공지사항</h3>

        <div class="subContentWrap">
            <div class="subContent">
                <!-- 콘텐츠 //-->
                <form id="boardVO" name="boardVO" method="post">
                    <input type="hidden" name="seq" value="${result.seq}" />
                    <input type="hidden" name="pageIndex" value="${searchVO.pageIndex}" />
                    <input type="hidden" name='searchField' value="${searchVO.searchField}" />
                    <input type="hidden" name='searchString' value="${searchVO.searchString}" />

                    <table class="tbl_view mt10">
                        <caption>공지사항 상세내용</caption>
                        <colgroup>
                            <col width="80px">
                            <col>
                            <col width="180px">
                        </colgroup>
                        <tbody>
                        <tr>
                            <td colspan="2" scope="row"><strong>${result.title}</strong></td>
                            <td class="tbl_view_date">${result.createDt}</td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <div class="tbl_view_txt">
                                    ${result.contents}
                                </div>
                            </td>
                        </tr>
                        <c:forEach items="${result.bbsAttFiles}" var="file">
                            <tr class="tbl_view_file">
                                <th scope="row">첨부파일</th>
                                <td colspan="2">
                                    <div>
                                        <a href="#" class="download-file" data-att-file-mng-seq="${file.attFileMngSeq}" data-file-seq="${file.fileSeq}" onclick="return false;">${file.fileNm}</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="mt20 t-right">
                        <a id='btnList' href="#" onclick="return false;" class="btn btn-blue btn-h30">목록보기</a>
                    </div>
                </form>
                <!--// 콘텐츠 -->
            </div>
        </div>
    </div>
    <!--// 콘텐츠영역 -->
</div>
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/layout/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/layout/modals.jsp" %>
</body>
</html>
