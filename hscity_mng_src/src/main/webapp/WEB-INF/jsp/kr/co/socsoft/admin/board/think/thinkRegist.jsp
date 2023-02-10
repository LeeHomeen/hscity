<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<c:set var="registerFlag" value="${empty result.seq ? '등록' : '수정'}" />

<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/head.jsp" %>
<script type="text/javascript" src="<c:url value='/cmm/validator.do'/>"></script>
<script type="text/javascript" src="<c:url value="/js/cmm/file_common.js"/>"></script>
<validator:javascript formName="boardVO" staticJavascript="false" xhtml="true" cdata="false"/>
<script id="sampleTmpl" type="text/template">
<tr class="file-row">
    <th scope="row" class="th2">파일선택</th>
    <td class="align-l">
        <input type="file" name='files[<@=seq@>]' class="ip sr1 st1 w4"/>
        <a href="#" onclick="return false;" class="btn sr2 st6 delete-file" data-seq="<@=seq@>">삭제</a>
    </td>
</tr>
</script>
<script type="text/javascript">
    var oEditors;

    $(document).ready(function(e){
        oEditors = CM.initSmartEditor('contents');

        var $searchVO = $('#searchVO'); // 검색폼
        var $boardVO = $('#boardVO'); // 입력폼

        // 목록
        $('#btnList').on('click', function(){
            $searchVO.attr('action', '<c:url value="/admin/board/think/list.do"/>');
            $searchVO.submit();
        });

        // 게시글 등록/수정
        $('#btnAdd').on('click', function() {
            // 스마트 에디터 내용을 html 태그로 변환한다
            oEditors.getById["contents"].exec("UPDATE_CONTENTS_FIELD", []);

            if(validateBoardVO($boardVO[0])) {
                <c:if test="${registerFlag == '등록'}">
                    if(confirm("<spring:message code='common.regist.msg'/>")) {
                        $boardVO.attr('action', '<c:url value="/admin/board/think/add.do"/>');
                        $boardVO.submit();
                    }
                </c:if>

                <c:if test="${registerFlag == '수정'}">
                    if(confirm("<spring:message code='common.update.msg'/>")) {
                        $boardVO.attr('action', '<c:url value="/admin/board/think/update.do"/>');
                        $boardVO.submit();
                    }
                </c:if>
            }
        });

        // 파일 추가
        /* $('#btnFileAdd').on('click', function(){
            var template = $('#sampleTmpl').html();
            var html = _.template(template, {
                seq: $('tr.file-row').length
            });

            $('table tbody').append(html);
        }); */

        // 파일 다운로드
        $('.download-file').on('click', function(){
            var attFileMngSeq = $(this).data('attFileMngSeq');
            var fileSeq = $(this).data('fileSeq');

            CM.postFileDownload('<c:url value="/com/downloadFile.do"/>', {
                attFileMngSeq: attFileMngSeq,
                fileSeq: fileSeq
            });
        });

        /* $('table').on('click', '.delete-file', function(){
            var seq = $(this).data('seq');
            var $fileRow = $('.file-row').eq(seq);
            $fileRow.remove();
        }); */

        $('table tbody').on('click', '.file-delete', function(){
            if(confirm('<spring:message code="cmm.message.fileDelConfirm"/>')) {
                var fileDelList = [];
                if($('#fileDelList').val()) {
                    fileDelList = $('#fileDelList').val().split(',');
                }

                var seq = $(this).data('seq');
                fileDelList.push(seq);

                $('#fileDelList').val(fileDelList.join(','));
                $(this).parent().remove();
            }
        });

        (function(){
            if(${!empty count} ) {
                if(${count > 0}) {
                    alert('<spring:message code="common.update.success.msg"/>');
                    $boardVO.attr('action', '<c:url value="/admin/board/think/view.do"/>');
                    $boardVO.submit();
                } else {
                    alert('<spring:message code="common.update.fail.msg"/>');
                    $boardVO.attr('action', '<c:url value="/admin/board/think/view.do"/>');
                    $boardVO.submit();
                }
            }
        }());
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
                <div class="path-wrap">
                    <span class="home">홈</span>&nbsp;&nbsp;&gt;
                    <span class="path">게시판 관리</span>&nbsp;&nbsp;&gt;
                    <span class="path">생각의 탄생</span>&nbsp;&nbsp;&gt;
                    <span class="now">생각의 탄생 글쓰기</span>
                </div>
                <div class="clearfix">
                    <p class="tit-page">생각의 탄생 글쓰기</p>
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
                            <div class="left">
                                <span class="star">필수</span>표시는 필수입력 항목입니다.
                            </div>
                            <div class="right">
                                <a id='btnList' href="#" onclick="return false;" class="btn sr2 st4">목록보기</a>
                                <a id='btnAdd' href="#" onclick="return false;" class="btn sr2 st5">${registerFlag}</a>
                            </div>
                        </fieldset>
                    </form>
                </div>
                <!-- //표 상단 설정 영역 -->
                <form id="searchVO" name="searchVO" method="post">
                    <input type="hidden" name="pageIndex" value="${searchVO.pageIndex}" />
                    <input type="hidden" name='searchOperate' value="${searchVO.searchOperate}" />
                    <input type="hidden" name='searchField' value="${searchVO.searchField}" />
                    <input type="hidden" name='searchString' value="${searchVO.searchString}" />
                    <input type="hidden" name='searchKeyword' value="${searchVO.searchKeyword}" />
                </form>
                <form id="boardVO" name="boardVO" method="post" enctype="multipart/form-data">
                    <input id='seq' type="hidden" name="seq" value="${result.seq}"/>
                    <input id='attFileMngSeq' type="hidden" name="attFileMngSeq" value="${result.attFileMngSeq}"/>
                    <input id="fileDelList" type="hidden" name="fileDelList" />

                    <table class="tbl sr1 st1">
                        <caption class="hide">생각의 탄생 글쓰기 표</caption>
                        <colgroup>
                            <col style="width:20%"/>
                            <col style="width:auto"/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="row" class="th2"><span class="star">필수</span>구분</th>
                            <td class="align-l">
                                <label>
                                    <select class="ip sr1 st1 w1" name="bbsTypeScd">
                                        <c:forEach var="type" items="${bbsType}">
                                            <option value="${type.cd}" ${result.bbsTypeScd == type.cd ? 'selected' : ''}>${type.nm}</option>
                                        </c:forEach>
                                    </select>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" class="th2"><span class="star">필수</span>제목</th>
                            <td class="align-l">
                                <input type="text" name='title' value='${result.title}' class="ip sr1 st1 w3"/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" class="th2" style="height:250px;"><span class="star">필수</span>본문 내용</th>
                            <td class="align-l">
                                <textarea id='contents' name='contents' cols="107" rows="30">${result.contents}</textarea>
                            </td>
                        </tr>
                        <c:if test="${registerFlag == '등록'}">
                        	<tr class="file-row">
                                <th scope="row" class="th2">파일선택</th>
                                <td class="align-l">
                                    <input id="file_input" name="files" type="file" class="ip sr1 st1 w4"/>
                                    <a id='btnFileAdd' href="#" onclick="return false;" class="btn sr2 st4">추가</a>
                                </td>
                            </tr>
                            <!-- <tr class="file-row">
                                <th scope="row" class="th2">파일선택</th>
                                <td class="align-l">
                                    <input type="file" name='files[0]' class="ip sr1 st1 w4"/>
                                    <a id='btnFileAdd' href="#" onclick="return false;" class="btn sr2 st4">추가</a>
                                </td>
                            </tr> -->
                        </c:if>
                        <c:if test="${registerFlag == '수정'}">
                            <c:if test="${fn:length(result.bbsAttFiles) > 0}">
                                <tr>
                                    <th scope="row" class="th2">첨부파일</th>
                                    <td class="align-l">
                                        <c:forEach items="${result.bbsAttFiles}" var="file">
                                            <div style="margin: 15px;">
                                                <img src="<c:url value="/images/cmm/ico-file.gif"/>" alt="${file.fileNm}"/>
                                                <a href="#" class='download-file' data-att-file-mng-seq="${file.attFileMngSeq}" data-file-seq="${file.fileSeq}">${file.fileNm}</a>
                                                <a href="#" onclick="return false;" class="btn sr2 st6 file-delete" data-seq="${file.fileSeq}">삭제</a>
                                            </div>
                                        </c:forEach>
                                    </td>
                                </tr>
                            </c:if>
                            <tr class="file-row">
                                <th scope="row" class="th2">파일선택</th>
                                <td class="align-l">
                                    <input id="file_input" name="files" type="file" class="ip sr1 st1 w4"/>
                                    <a id='btnFileAdd' href="#" onclick="return false;" class="btn sr2 st4">추가</a>
                                </td>
                            </tr>
                            <!-- <tr class="file-row">
                                <th scope="row" class="th2">파일선택</th>
                                <td class="align-l">
                                    <input type="file" name='files[0]' class="ip sr1 st1 w4"/>
                                    <a id='btnFileAdd' href="#" onclick="return false;" class="btn sr2 st4">추가</a>
                                </td>
                            </tr> -->
                        </c:if>
                        </tbody>
                    </table>
                </form>
            </div>
            <!-- //컨텐츠 영역 -->
        </div>
    </div>
</div>
<!--//container -->
</body>
</html>