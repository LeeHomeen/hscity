<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/head.jsp" %>
<script type="text/javascript">
    $(document).ready(function(e){
        var $searchForm = $('#searchForm');

        // 상세보기
        $('tbody td.align-l a').on('click', function(){
            $('#seq').val($(this).data('seq'));
            $searchForm.attr('action', '<c:url value="/admin/board/think/view.do"/>');
            $searchForm.submit();
        });

        // 조회
        $('#btnSearch').on('click', function(){
            $('#pageIndex').val(1);
            $searchForm.attr('action', '<c:url value="/admin/board/think/list.do"/>');
            $searchForm.submit();
        });

        // 페이징
        $('._paging').on('click', function(){
            $('#pageIndex').val($(this).data('pageIndex'));
            $searchForm.attr('action', '<c:url value="/admin/board/think/list.do"/>');
            $searchForm.submit();
        });
    });
</script>
<body>
<div id="wrap">
    <%--헤더--%>
    <%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/layout/header.jsp" %>

    <div id="container">
        <%-- left 메뉴 --%>
        <jsp:include page="/com/leftMenu.do?type=board&menu=think"/>
        <div class="sec-right">
            <!-- 상단 경로 및 페이지 타이틀 -->
            <div class="top-bar">
                <%-- Q&A --%>
                <div class="path-wrap">
                    <span class="home">홈</span>&nbsp;&nbsp;&gt;
                    <span class="path">게시판 관리</span>&nbsp;&nbsp;&gt;
                    <span class="now">생각의 탄생</span>
                </div>
                <div class="clearfix">
                    <p class="tit-page">생각의 탄생</p>
                </div>
            </div>
            <!-- //상단 경로 및 페이지 타이틀 -->

            <!-- 컨텐츠 영역 -->
            <div class="cont">
                <!-- 표 상단 설정 영역 -->
                <div class="tbl-top">
                    <form id='searchForm' name='searchForm' method="post">
                        <input type="hidden" id="seq" name="seq" />
                        <input type="hidden" id="pageIndex" name="pageIndex" value="${searchVO.pageIndex}" />
                        <input type="hidden" name='searchOperate' value="cn" />
                        <input type="hidden" name='searchField' value="bbs_type_scd" />

                        <fieldset>
                            <legend>표,게시판 검색 조건</legend>
                            <div class="left">
                                <label>
                                    <select class="ip sr1 st1 w1" name="searchKeyword">
                                        <option value="" selected>전체</option>
                                        <c:forEach items="${bbsType}" var="type">
                                            <option value="${type.cd}" ${searchVO.searchKeyword == type.cd ? 'selected' : ''}>${type.nm}</option>
                                        </c:forEach>
                                    </select>
                                </label>
                                <label>
                                    <input type="text" class="ip sr1 st1 w2" id="searchString" name="searchString" data-for="btnSearch" value="${searchVO.searchString}"/>
                                </label>
                                <input id='btnSearch' type="button" class="btn sr2 st2" value="조회"/>
                                <input type="text" style="display: none" title="no enter submit!"/>
                            </div>
                            <%--<div class="right">
                                <a id='btnAddView' href="#" onclick='return false;' class="btn sr2 st2">글쓰기</a>
                            </div>--%>
                        </fieldset>
                    </form>
                </div>
                <!-- //표 상단 설정 영역 -->
                <table class="tbl sr1 st1">
                    <caption class="hide">생각의 탄생 게시판 표</caption>
                    <colgroup>
                        <col style="width:8%"/>
                        <col style="width:15%"/>
                        <col style="width:15%"/>
                        <col style="width:auto"/>
                        <col style="width:10%"/>
                        <col style="width:15%"/>
                    </colgroup>
                    <thead>
                    <tr>
                        <th scope="col">No</th>
                        <th scope="col">업무구분</th>
                        <th scope="col">처리상태</th>
                        <th scope="col">제 목</th>
                        <th scope="col">작성자</th>
                        <th scope="col">날 짜</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:if test="${paginationInfo.totalRecordCount == 0 }">
                            <tr>
                                <td colspan="6"><spring:message code="common.nodata.msg"/></td>
                            </tr>
                        </c:if>

                        <c:if test="${paginationInfo.totalRecordCount > 0}">
                            <c:forEach items="${items}" var="item">
                                <tr>
                                    <th scope="row">${item.rownum}</th>
                                    <td>${item.bbsTypeNm}</td>
                                    <td scope="row">${fn:length(item.thinks) > 0 ? '답변완료' : '처리중'}</td>
                                    <td class="align-l">
                                        <a href="#" onclick="return false;" data-seq="${item.seq}">${item.title}</a>
                                        <c:if test="${item.isNew == 'y'}">
                                            <img src="<c:url value="/images/cmm/ico-new.gif"/>" alt="새글"/>
                                        </c:if>
                                        <c:if test="${fn:length(item.bbsAttFiles) > 0}">
                                            <img src="<c:url value="/images/cmm/ico-file.gif"/>" alt="첨부파일있음"/>
                                        </c:if>
                                    </td>
                                    <td>${item.createNm}</td>
                                    <td>${item.createDt}</td>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </tbody>
                </table>

                <!-- 페이징 -->
                <div class="paging">
                    <ol>
                        <ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="linkPage" />
                    </ol>
                </div>
                <!-- //페이징 -->
            </div>
            <!-- //컨텐츠 영역 -->
        </div>
    </div>
    <!--//container -->
</div>
</body>
</html>