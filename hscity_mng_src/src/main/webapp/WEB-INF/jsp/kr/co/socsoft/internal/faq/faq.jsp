<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/common/head.jsp" %>
<script type="text/javascript">
$(document).ready(function(){
    var $searchVO = $('#searchVO');

    // 조회
    $('#btnSearch').on('click', function(){
        $('#pageIndex').val(1);
        $searchVO.attr('action', '<c:url value="/internal/faq/list.do"/>');
        $searchVO.submit();
    });

    // FAQ
    $('.faq_q').on('click', function(){
        $(this).parent().parent().siblings('tr').find('.faq_a').slideUp(100);
        $(this).parent().parent().siblings('tr').find('.faq_q').removeClass('open');
        $(this).toggleClass('open');
        $(this).next('.faq_a').slideToggle(100, function(){
            $(".cateWrap").height($('.contentWrap').outerHeight()-20);
        });
        var img = $(this).children('.btn_faq');
        $('img').not(img).removeClass('rotate');
        img.toggleClass('rotate');
    });

    // 페이징
    $('._paging').on('click', function(){
        $('#pageIndex').val($(this).data('pageIndex'));
        $searchVO.attr('action', '<c:url value="/internal/faq/list.do"/>');
        $searchVO.submit();
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
        <h3 class="stitle"><img src="<c:url value="/images/biz/icon_faq.png" />" alt="" class="bl">자주 묻는 질문</h3>
        <div class="subContentWrap">
            <div class="subContent">
                <!-- 콘텐츠 //-->
                <form id='searchVO' name='searchVO' method="post">
                    <input type="hidden" id="seq" name="seq" />
                    <input type="hidden" id="pageIndex" name="pageIndex" value="${searchVO.pageIndex}" />

                    <div class="tbl_search">
                        <select name="searchField">
                            <option value="title" ${searchVO.searchField == 'title' ? 'selected' : ''}>질문</option>
                            <option value="contents" ${searchVO.searchField == 'contents' ? 'selected' : ''}>답변</option>
                        </select>
                        <input type="text" class="input_txt" name="searchString" value="${searchVO.searchString}" data-for="btnSearch">
                        <input type="text" style="display: none" title="no enter submit!"/>
                        <a id='btnSearch' href="#" onclick="return false;" class="btn btn-darkgray btn-h30">SEARCH</a>
                    </div>

                    <table class="tbl-basic faq_tbl">
                        <caption>데이터관련</caption>
                        <colgroup>
                            <col width="150px"/>
                            <col/>
                        </colgroup>
                        <tbody>
                            <c:if test="${paginationInfo.totalRecordCount == 0 }">
                                <tr>
                                    <td colspan="2" style="vertical-align: middle; padding: 16px 0px;"><spring:message code="common.nodata.msg"/></td>
                                </tr>
                            </c:if>
                            <c:forEach items="${items}" var="item">
                                <tr>
                                    <th>질문</th>
                                    <td class="t-left">
                                        <a href="#" onclick="return false;" class="faq_q">${item.title}
                                            <span class="btn_faq">답변확인</span></a>
                                        <div class="faq_a">${item.contents}</div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
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
