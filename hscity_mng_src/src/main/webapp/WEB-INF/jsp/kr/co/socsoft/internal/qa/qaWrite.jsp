<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="registerFlag" value="${empty result.seq ? '등록' : '수정'}" />

<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/common/head.jsp" %>
<script type="text/javascript">
var oEditors;

$(document).ready(function(){
    oEditors = CM.initSmartEditor('contents');

    var $boardVO = $('#boardVO');
    var $searchVO = $('#searchVO');

    // 목록
    $('#btnList').on('click', function(){
        $searchVO.attr('action', '<c:url value="/internal/qa/list.do"/>');
        $searchVO.submit();
    });

    // 저장&수정
    $('#btnAdd').on('click', function() {
        // 스마트 에디터 내용을 html 태그로 변환한다
        oEditors.getById["contents"].exec("UPDATE_CONTENTS_FIELD", []);

        <c:if test="${registerFlag == '등록'}">
        if(confirm("<spring:message code='common.regist.msg'/>")) {
            $boardVO.attr('action', '<c:url value="/internal/qa/add.do"/>');
            $boardVO.submit();
        }
        </c:if>

        <c:if test="${registerFlag == '수정'}">
        if(confirm("<spring:message code='common.update.msg'/>")) {
            $boardVO.attr('action', '<c:url value="/internal/qa/update.do"/>');
            $boardVO.submit();
        }
        </c:if>
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

        <h3 class="stitle"><img src="<c:url value="/images/biz/icon_qna.png"/>" alt="" class="bl">질의응답 등록</h3>

        <div class="subContentWrap">
            <div class="subContent">
                <!-- 콘텐츠 //-->

                <form id="searchVO" name="searchVO" method="post">
                    <input type="hidden" name="pageIndex" value="${searchVO.pageIndex}" />
                    <input type="hidden" name='searchField' value="${searchVO.searchField}" />
                    <input type="hidden" name='searchString' value="${searchVO.searchString}" />
                </form>

                <form id="boardVO" name="boardVO" method="post" enctype="multipart/form-data">
                    <input id='seq' type="hidden" name="seq" value="${result.seq}"/>
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
                            <td class="ft_black t-left" colspan="3"><input id='title' name="title" type="text" placeholder="제목을 입력하세요" value="${result.title}"></td>
                        </tr>
                        <tr>
                            <th scope="row">내용</th>
                            <td class="ft_black" colspan="3">
                                <textarea id='contents' name="contents" style="width:100%; height:412px; min-width:610px;" placeholder="질의응답 내용을 입력하세요. 내용은 최대 xxx자 까지 입력 가능합니다.">${result.contents}</textarea> 
                            </td> 
                        </tr>
                        </tbody>
                    </table>

                    <div class="t-right mt20">
                        <a id='btnList' href="#" onclick="return false;" class="btn btn-blue btn-h30 mr5">목록보기</a>
                        <a id='btnAdd' href="#" onclick="return false;" class="btn btn-darkgray btn-h30">${registerFlag}</a>
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
