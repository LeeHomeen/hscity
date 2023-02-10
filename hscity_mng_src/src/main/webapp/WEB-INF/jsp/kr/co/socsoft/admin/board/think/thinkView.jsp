<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="registerFlag" value="${empty result.answers[0].seq ? '답변등록' : '답변수정'}" />

<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/head.jsp" %>
<script type="text/javascript" src="<c:url value="/js/cmm/file_common.js"/>"></script>
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

        var $boardVO = $('#boardVO');
        var $answerVO = $('#answerVO');

        // 목록
        $('#btnList').on('click', function(){
            $boardVO.attr('action', '<c:url value="/admin/board/think/list.do"/>');
            $boardVO.submit();
        });

        // 수정
        $('#btnEdit').on('click', function(){
            $boardVO.attr('action', '<c:url value="/admin/board/think/updateView.do"/>');
            $boardVO.submit();
        });

        // 삭제
        $('#btnDelete').on('click', function(){
            if(confirm('<spring:message code="common.delete.msg"/>')) {
                $boardVO.attr('action', '<c:url value="/admin/board/think/delete.do"/>');
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

        // 파일 추가
        /* $('#btnFileAdd').on('click', function(){
            var template = $('#sampleTmpl').html();
            var html = _.template(template, {
                seq: $('tr.file-row').length
            });

            $('table:eq(1) tbody').append(html);
        }); */

        // 파일 삭제
        /* $('table:eq(1) tbody').on('click', '.delete-file', function(){
            var seq = $(this).data('seq');
            var $fileRow = $('.file-row').eq(seq);
            $fileRow.remove();
        }); */

        // 파일 삭제
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

        // 답변 등록
        $('#btnAnswerAdd').on('click', function(){
            oEditors.getById["contents"].exec("UPDATE_CONTENTS_FIELD", []);

           if("${registerFlag == '등록'}"){
        	   if(confirm("<spring:message code='common.regist.msg'/>")) {
                   $($answerVO).find('input[name="seq"]').val('${result.seq}');
                   $answerVO.attr('action', '<c:url value="/admin/board/think/add.do"/>');
                   $answerVO.submit();
               }
           }else if("${registerFlag == '수정'}"){
        	   if(confirm("<spring:message code='common.update.msg'/>")) {
                   $answerVO.attr('action', '<c:url value="/admin/board/think/answerUpdate.do"/>');
                   $answerVO.submit();
               }
           }
            /* <c:if test="${registerFlag == '등록'}">
                if(confirm("<spring:message code='common.regist.msg'/>")) {
                    $($answerVO).find('input[name="seq"]').val('${result.seq}');
                    $answerVO.attr('action', '<c:url value="/admin/board/think/add.do"/>');
                    $answerVO.submit();
                }
            </c:if>

            <c:if test="${registerFlag == '수정'}">
                if(confirm("<spring:message code='common.update.msg'/>")) {
                    $answerVO.attr('action', '<c:url value="/admin/board/think/answerUpdate.do"/>');
                    $answerVO.submit();
                }
            </c:if> */
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
                <div class="path-wrap">
                    <span class="home">홈</span>&nbsp;&nbsp;&gt;
                    <span class="path">게시판 관리</span>&nbsp;&nbsp;&gt;
                    <span class="path">생각의 탄생</span>&nbsp;&nbsp;&gt;
                    <span class="now">생각의 탄생 상세보기</span>
                </div>
                <div class="clearfix">
                    <p class="tit-page">생각의 탄생 상세보기</p>
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
                        <caption class="hide">생각의 탄생 글보기</caption>
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

                <div style="margin-top: 30px;">
                    <div class="btn-box"><a id='btnAnswerAdd' href="#" onclick="return false;" class="btn sr2 st4">${registerFlag}</a></div>
                    <form id="answerVO" name="answerVO" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="seq" value="${result.thinks[0].seq}"/>
                        <input type="hidden" name="upperThinkSeq" value="${result.thinks[0].upperThinkSeq}"/>
                        <input type="hidden" name="attFileMngSeq" value="${result.thinks[0].attFileMngSeq}"/>
                        <input id="fileDelList" type="hidden" name="fileDelList" />

                        <%-- 사용하지 않는값 빈값 처리--%>
                        <input type="hidden" name="title" value=""/>
                        <input type="hidden" name="bbsTypeScd" value=""/>

                        <table class="tbl sr1 st1">
                            <colgroup>
                                <col style="width:20%"/>
                                <col style="width:auto"/>
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th scope="row" class="th2" style="height:250px;"><span class="star">필수</span>답글 내용</th>
                                    <td class="align-l">
                                        <textarea id='contents' name='contents' cols="107" rows="30">${result.thinks[0].contents}</textarea>
                                    </td>
                                </tr>
                                <c:if test="${fn:length(result.answerBbsAttFiles) > 0}">
                                    <tr>
                                        <th scope="row" class="th2">첨부파일</th>
                                        <td class="align-l">
                                            <c:forEach items="${result.answerBbsAttFiles}" var="file">
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
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
            <!-- //컨텐츠 영역 -->
        </div>
    </div>
    <!--//container -->
</body>
</html>