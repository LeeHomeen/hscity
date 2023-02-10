<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/head.jsp" %>
<script type="text/javascript">
    $(document).ready(function(e){
        var $boardVO = $('#boardVO');

        // 목록
        $('#btnList').on('click', function(){
            $boardVO.attr('action', '<c:url value="/admin/board/notice/list.do"/>');
            $boardVO.submit();
        });

        // 수정
        $('#btnEdit').on('click', function(){
            $boardVO.attr('action', '<c:url value="/admin/board/notice/updateView.do"/>');
            $boardVO.submit();
        });

        // 삭제
        $('#btnDelete').on('click', function(){
            if(confirm('<spring:message code="common.delete.msg"/>')) {
                $boardVO.attr('action', '<c:url value="/admin/board/notice/delete.do"/>');
                $boardVO.submit();
            }
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
<div id="wrap">
    <%--헤더--%>
    <%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/layout/header.jsp" %>
    <div id="container">
        <%-- left 메뉴 --%>
        <jsp:include page="/com/leftMenu.do?type=board&menu=notice"/>
        <div class="sec-right">
            <!-- 상단 경로 및 페이지 타이틀 -->
            <div class="top-bar">
                <div class="path-wrap">
                    <span class="home">홈</span>&nbsp;&nbsp;&gt;
                    <span class="path">게시판 관리</span>&nbsp;&nbsp;&gt;
                    <span class="path">공지사항</span>&nbsp;&nbsp;&gt;
                    <span class="now">공지사항 상세보기</span>
                </div>
                <div class="clearfix">
                    <p class="tit-page">공지사항 상세보기</p>
                </div>
            </div>
            <!-- //상단 경로 및 페이지 타이틀 -->

            <!-- 컨텐츠 영역 -->
            <div class="cont">
                <!-- 표 상단 설정 영역 -->
                <div class="tbl-top">
                    <form method="post" action="#">
                        <fieldset>
                            <legend>표,게시판 검색 조건</legend>
                            <div class="right">
                                <a id='btnList' href="#" onclick='return false;' class="btn sr2 st2">목록보기</a>
                                <a id='btnEdit' href="#" onclick='return false;' class="btn sr2 st4">수정</a>
                                <a id='btnDelete' href="#" onclick='return false;' class="btn sr2 st5">삭제</a>
                            </div>
                        </fieldset>
                    </form>
                </div>
                <!-- //표 상단 설정 영역 -->
                <form id="boardVO" name="boardVO" method="post">
                    <input type="hidden" name="seq" value="${result.seq}" />
                    <input type="hidden" name="pageIndex" value="${searchVO.pageIndex}" />
                    <input type="hidden" name='searchOperate' value="${searchVO.searchOperate}" />
                    <input type="hidden" name='searchField' value="${searchVO.searchField}" />
                    <input type="hidden" name='searchString' value="${searchVO.searchString}" />
                    <input type="hidden" name='searchKeyword' value="${searchVO.searchKeyword}" />

                    <table class="tbl sr1 st1">
                        <caption class="hide">공지사항 글보기</caption>
                        <colgroup>
                            <col style="width:auto"/>
                            <col style="width:15%"/>
                        </colgroup>
                        <tbody>
                            <tr>
                                <td class="txt-tit2">${result.title}
                                </td>
                                <td>${result.createDt}</td>
                            </tr>
                            <tr>
                                <td colspan="2" class="txt-tit2">
                                    <c:forEach items="${result.bbsAttFiles}" var="file">
                                        <div>
                                            <img src="<c:url value="/images/cmm/ico-file.gif"/>" alt="${file.fileNm}"/>
                                            <a href="#" class="download-file" data-att-file-mng-seq="${file.attFileMngSeq}" data-file-seq="${file.fileSeq}" onclick="return false;">${file.fileNm}</a>
                                        </div>
                                    </c:forEach>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" class="txt-cont">
                                    <div style="overflow: auto; max-height: 380px;">${result.contents}</div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </form>
            </div>
            <!-- //컨텐츠 영역 -->
        </div>
    </div>
    <!--//container -->
</body>
</html>