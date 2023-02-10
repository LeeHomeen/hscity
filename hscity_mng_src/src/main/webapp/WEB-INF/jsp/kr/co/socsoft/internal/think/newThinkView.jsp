<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="registerFlag" value="${empty result.thinks[0].seq ? '답변등록' : '답변수정'}" />
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/common/head.jsp" %>
<script type="text/javascript">
$(document).ready(function(){
    var $boardVO = $('#boardVO');

    // 목록
    $('#btnList').on('click', function(){
        $boardVO.attr('action', '<c:url value="/internal/think/newThinkList.do"/>');
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

    // 수정
    $('#btnUpdate').on('click', function() {
        if(confirm("<spring:message code='common.update.msg'/>")) {
            $boardVO.attr('action', '<c:url value="/internal/think/newThinkUpdateView.do"/>');
            $boardVO.submit();
        }
    });
});
</script>
<body class="board_body">
<div class="subContent">
<!-- 콘텐츠 //-->
<form id="boardVO" name="boardVO" method="post">
    <input type="hidden" name="seq" value="${result.seq}" />
    <input type="hidden" name="pageIndex" value="${searchVO.pageIndex}" />
    <input type="hidden" name='searchField' value="${searchVO.searchField}" />
    <input type="hidden" name='searchString' value="${searchVO.searchString}" />

    <table class="tbl_view" summary="">
        <caption></caption>
        <colgroup>
            <col width="140px"/>
            <col width="345px"/>
            <col width="140px"/>
            <col width="225px"/>
        </colgroup>
        <tbody>
        <tr>
            <th scope="row">제목</th>
            <td class="ft_black" colspan="3">${result.title}</td>
        </tr>
        <tr>
            <th scope="row">작성자</th>
            <td class="ft_black">${result.createNm}</td>
            <th class="l_line" scope="row">등록일</th>
            <td class="ft_black">${result.createDt}</td>
        </tr>
        <tr>
            <th scope="row">내용</th>
            <td class="ft_black" colspan="3">
                <div>${result.contents}</div>
            </td>
        </tr>
        </tbody>
    </table>
</form>

<c:if test="${result.thinks[0] != null}">
    <h4 class="mt30 mb10">답변내역</h4>
    <table class="tbl_view" summary="답변내역">
        <caption></caption>
        <colgroup>
            <col width="140px"/>
            <col width="345px"/>
            <col width="140px"/>
            <col width="225px"/>
        </colgroup>
        <tbody>
        <tr>
            <th scope="row">작성자</th>
            <td class="ft_black">${result.thinks[0].createNm}</td>
            <th class="l_line" scope="row">답변 등록일</th>
            <td class="ft_black">${result.thinks[0].createDt}</td>
        </tr>
        <tr>
            <th scope="row">답변내용</th>
            <td class="ft_black" colspan="3">${result.thinks[0].contents}</td>
        </tr>
        <c:forEach items="${result.answerBbsAttFiles}" var="file">
            <tr class="tbl_view_file">
                <th scope="row">첨부파일</th>
                <td colspan="3">
                    <div>
                        <a href="#" class="download-file" data-att-file-mng-seq="${file.attFileMngSeq}" data-file-seq="${file.fileSeq}" onclick="return false;">${file.fileNm}</a>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<div class="t-right mt20">
    <a id='btnList' href="#" onclick="return false;" class="btn btn-blue btn-h30">목록보기</a>
    <a id='btnUpdate' href="#" onclick="return false;" class="btn btn-darkgray btn-h30">수정</a>
</div>
<!--// 콘텐츠 -->
</div>
</body>
</html>
