<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now}" pattern="yyyy" var="year" />

<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/head.jsp" %>
<script type="text/javascript" src="<c:url value='/cmm/validator.do'/>"></script>
<validator:javascript formName="dataUploadScheduleVO" staticJavascript="false" xhtml="true" cdata="false"/>
<script type="text/javascript">
$(document).ready(function () {
    var $dataUploadScheduleVO = $('#dataUploadScheduleVO');

    // 예약일
    CM.createDatepicker('shceduleDay', 8);

    var insertStdYmdYn = '${result.insertStdYmdYn}';

    if(insertStdYmdYn === 'y') {
        var mngStdYmdScd = '${result.mngStdYmdScd}';

        if(mngStdYmdScd === 'stdymd') {
            CM.createDatepicker('insertStdym', 8);
        } else if(mngStdYmdScd === 'stdym'){
            CM.createMonthpicker('insertStdym', 8);
        }
    }

    var splitTableScd = '${result.splitTableScd}';
    if(splitTableScd === 'yyyymm') {
        CM.createMonthpicker('splitTableYm', 8);
    }

    $('#btnSetting').on('click', function () {
        if(validateDataUploadScheduleVO($dataUploadScheduleVO[0])) {
            var upldFileNm = $('#upldFileNm').val();
            var fileNm = upldFileNm.split('.');
            if(fileNm.length < 2) {
                alert('잘못된 파일명입니다. 파일명을 확인해주세요.');
                return false;
            } else {
                if(['csv', 'txt'].indexOf(fileNm[1]) < 0) {
                    alert('파일은 csv 또는 txt만 설정할 수 있습니다.');
                    return false;
                }
            }
            if (confirm('<spring:message code="schedule.message.add"/>')) {
                $.ajax({
                    url: "<c:url value="/data/upload/addBigDataUpload.do"/>",
                    data: $("#dataUploadScheduleVO").serialize(),
                    type: 'POST',
                    dataType: 'json',
                    success: function (data) {
                        alert('<spring:message code="schedule.message.success"/>');
                    },
                    fail: function() {
                        alert('<spring:message code="schedule.message.fail"/>');
                    },
                    complete: function() {
                        parent.location.reload();
                    }
                });
            }
        }
    });
});
</script>
<body>
<div id="wrap" style="background-color: white; width: 750px">
    <div id="container" style="width: 750px">
        <div class="cont" style="width: 750px">
            <!-- 표 상단 설정 영역 -->
            <div class="tbl-top">
                <form method="post" action="#">
                    <fieldset>
                        <legend>파일데이터 업로드(엑셀파일)</legend>
                        <div class="left">
                            ${result.tableNm} 상세내용
                        </div>
                        <div class="right">
                            <a id='btnSetting' href="#" onclick="return false;" class="btn sr2 st5">설정</a>
                        </div>
                    </fieldset>
                </form>
            </div>
            <!-- //표 상단 설정 영역 -->
            <form id="dataUploadScheduleVO" name="dataUploadScheduleVO" method="post">
                <input type="hidden" name="tableId" value="${result.tableId}"/>

                <table class="tbl sr1 st1">
                    <caption class="hide">파일데이터</caption>
                    <colgroup>
                        <col style="width:20%"/>
                        <col style="width:auto"/>
                    </colgroup>
                    <tbody>
                    <tr>
                       <th><span class="em">※주의사항 </span></th>
                    <td align="left">
                       <font color="red">-예약일시가 현시간 보다 작을 경우 "설정"을 하는 순간 서버에서 데이터업로드를 바로 실행합니다. </font> 
                    </td>
                    </tr>
                    <tr>
                        <th><span class="em">데이터 </span></th>
                        <td class="align-l">${result.tableNm}</td>
                    </tr>
                    <tr>
                        <th>예약설명</th>
                        <td>
                            <input type="text" class="ip sr1 st1 w3" name='description' value="${result.description}"/>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="em">예약일</span></th>
                        <td class="align-l">
                            <input type="text" id="shceduleDay" name="shceduleDay" class="ip sr1 st1 cald" value="${result.shceduleDay}" readonly>
                            <label>
                                <select class="ip sr1 st1 w1" name="scheduleHour">
                                    <c:forEach begin="0" end="23" varStatus="status">        
                                    <c:choose>
                                       <c:when test="${status.index == result.scheduleHour}">
                                        <option value="<fmt:formatNumber value="${status.index}" minIntegerDigits="2" type="number"/>" selected="selected"><fmt:formatNumber value="${status.index}" minIntegerDigits="2" type="number"/></option>
									   </c:when>
									   <c:otherwise>
                                        <option value="<fmt:formatNumber value="${status.index}" minIntegerDigits="2" type="number"/>"><fmt:formatNumber value="${status.index}" minIntegerDigits="2" type="number"/></option>									   
									   </c:otherwise>
                                    </c:choose>
                                    </c:forEach>
                                </select>
                            </label>시

                            <label>
                                <select class="ip sr1 st1 w1" name="scheduleMinute">
                                    <c:forEach begin="0" end="59" varStatus="status">
                                       <c:choose>
                                       <c:when test="${status.index == result.scheduleMinute}">
                                        <option value="<fmt:formatNumber value="${status.index}" minIntegerDigits="2" type="number"/>"  selected="selected"><fmt:formatNumber value="${status.index}" minIntegerDigits="2" type="number"/></option>
									   </c:when>
									   <c:otherwise>
                                        <option value="<fmt:formatNumber value="${status.index}" minIntegerDigits="2" type="number"/>"><fmt:formatNumber value="${status.index}" minIntegerDigits="2" type="number"/></option>									   
									   </c:otherwise>
                                    </c:choose>
                                    </c:forEach>
                                </select>
                            </label>분
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <th>대상파일명</th>
                        <td class="align-l">
                            <input id='upldFileNm' type="text" class="ip sr1 st1 w3 no-kor" name='upldFileNm' value="${result.upldFileNm}" />
                            <span style="color: red;">* 파일은 csv 또는 txt 파일만 가능합니다.</span>
                        </td>
                    </tr>
                    <c:if test="${result.insertStdYmdYn == 'y'}">
                        <tr>
                            <th>기준년월(일)</th>
                            <td class="align-l">
                                <c:if test="${result.mngStdYmdScd == 'stdy'}">
                                    <select class="ip sr1 st1 w1" name="insertStdym">
                                        <c:forEach begin="${year - 10}" end="${year + 10}" varStatus="status">                                         
		                                    <c:choose>
		                                       <c:when test="${status.index == result.insertStdym}">
		                                        <option value="${status.index}" selected="selected">${status.index}</option>
											   </c:when>
											   <c:otherwise>
		                                         <option value="${status.index}" ${status.index == year ? 'selected' : ''}>${status.index}</option>
											   </c:otherwise>
		                                    </c:choose>                                            
                                        </c:forEach>
                                    </select>
                                </c:if>
                                <c:if test="${result.mngStdYmdScd == 'stdym'}">
                                    <input type="text" id="insertStdym" name="insertStdym" class="ip sr1 st1 cald" value="${result.insertStdym}" readonly>
                                </c:if>
                                <c:if test="${result.mngStdYmdScd == 'stdymd'}">
                                    <input type="text" id="insertStdym" name="insertStdym" class="ip sr1 st1 cald" value="${result.insertStdym}" readonly>
                                </c:if>
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${result.splitTableScd != 'none'}">
                        <tr>
                            <th>DB테이블 분리기준년(월)</th>
                            <td class="align-l">
                                <c:if test="${result.splitTableScd == 'yyyymm'}">
                                    <input id='splitTableYm' type="text" name="splitTableYm" class="ip sr1 st1 cald" value="${result.splitTableYm}">
                                </c:if>
                                <c:if test="${result.splitTableScd == 'yyyy'}">
                                    <select class="ip sr1 st1 w1" name="splitTableYm">
                                        <c:forEach begin="${year - 10}" end="${year + 10}" varStatus="status">
                                           <c:choose>
		                                       <c:when test="${status.index == result.splitTableYm}">
		                                        <option value="${status.index}" selected="selected">${status.index}</option>
											   </c:when>
											   <c:otherwise>
		                                         <option value="${status.index}" ${status.index == year ? 'selected' : ''}>${status.index}</option>
											   </c:otherwise>
		                                    </c:choose>   
                                        </c:forEach>
                                    </select>
                                </c:if>
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <th>사용여부</th>
                        <td class="align-l">
                            <label>
                                <select class="ip sr1 st1 w1" name="useYn">                                    
                                     <option value="y" ${result.useYn == 'y' ? 'selected' : ''}>사용함 </option>
		                             <option value="n" ${result.useYn == 'n' ? 'selected' : ''}>사용안함</option>                    
                                </select>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <th>파일형식</th>
                        <td class="align-l">
                            <select id='upldFileEncoding' class="ip sr1 st1 w1" name="upldFileEncoding">
                                <option value="UTF-8" ${result.upldFileEncoding == 'UTF-8' ? 'selected' : ''}>UTF-8</option>
                                <option value="EUC-KR" ${result.upldFileEncoding == 'EUC-KR' ? 'selected' : ''}>EUC-KR</option>
                            </select>
                        </td>
                    </tr>
                    </tbody>
                </table>
                  
            </form>
        </div>
    </div>
</div>
</body>