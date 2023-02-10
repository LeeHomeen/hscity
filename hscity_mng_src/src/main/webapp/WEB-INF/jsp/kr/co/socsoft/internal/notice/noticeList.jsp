<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/common/head.jsp" %>
<script type="text/javascript">
$(document).ready(function(){
    var $searchVO = $('#searchVO');

    // 상세조회
    $('.board-title').on('click', function(){
        $('#seq').val($(this).data('seq'));
        $searchVO.attr('action', '<c:url value="/internal/notice/view.do"/>');
        $searchVO.submit();
    });

    // 조회
    $('#btnSearch').on('click', function(){
        $('#pageIndex').val(1);
        $searchVO.attr('action', '<c:url value="/internal/notice/list.do"/>');
        $searchVO.submit();
    });

    // 페이징
    $('._paging').on('click', function(){
        $('#pageIndex').val($(this).data('pageIndex'));
        $searchVO.attr('action', '<c:url value="/internal/notice/list.do"/>');
        $searchVO.submit();
    });
});
</script>
<body>
<%-- 헤더 영역 --%>
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/layout/header.jsp" %>
<%-- 우측 메뉴 --%>
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/layout/right.jsp" %>

<div class="AllWrap">
    <%-- 왼쪽 메뉴 --%>
    <jsp:include page="/internal/leftMenu.do"/>
    <!-- 콘텐츠영역 //-->
    <div class="contentWrap">
        <img src="<c:url value="/images/biz/btncate_close.png"/>" alt="카테고리 열기&닫기" class="cateBtn">
        <h3 class="stitle"><img src="<c:url value="/images/biz/icon_notice.png"/>" alt="" class="bl">공지사항</h3>
        <div class="subContentWrap">
            <div class="subContent">
                <!-- 콘텐츠 //-->
                <form id='searchVO' name='searchVO' method="post">
                    <input type="hidden" id="seq" name="seq" />
                    <input type="hidden" id="pageIndex" name="pageIndex" value="${searchVO.pageIndex}" />

                    <div class="tbl_search">
                        <select name="searchField">
                            <option value="title" ${searchVO.searchField == 'title' ? 'selected' : ''}>제목</option>
                            <option value="contents" ${searchVO.searchField == 'contents' ? 'selected' : ''}>내용</option>
                        </select>
                        <input type="text" class="input_txt" name="searchString" value="${searchVO.searchString}" data-for="btnSearch">
                        <input type="text" style="display: none" title="no enter submit!"/>
                        <a id='btnSearch' href="#" onclick="return false;" class="btn btn-darkgray btn-h30">SEARCH</a>
                    </div>
                    <table class="tbl-basic">
                        <caption>공지사항</caption>
                        <colgroup>
                            <col width="110px">
                            <col>
                            <col width="150px">
                        </colgroup>
                        <thead>
                        <tr>
                            <th scope="col">No</th>
                            <th scope="col">Title</th>
                            <th scope="col">Date</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:if test="${paginationInfo.totalRecordCount == 0 }">
                                <tr>
                                    <td colspan="3"><spring:message code="common.nodata.msg"/></td>
                                </tr>
                            </c:if>
                            <c:forEach items="${items}" var="item">
                                <tr>
                                    <td>${item.rownum}</td>
                                    <td class="t-left">
                                        <a class='board-title' href="#" onclick="return false;" data-seq="${item.seq}">${item.title}
                                            <c:if test="${item.isNew == 'y'}">
                                                <img src="<c:url value="/images/biz/ico-new.png"/>" alt="new" class="ico">
                                            </c:if>
                                        </a>
                                    </td>
                                    <td>${item.createDt}</td>
                                </tr>
                            </c:forEach>
                    </table>
                    <div class="paging">
                        <ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="linkPage" />
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
