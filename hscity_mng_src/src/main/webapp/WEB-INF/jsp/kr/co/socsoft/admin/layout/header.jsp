<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/sessionCheck.jsp"%>
<%@ page session="true" %>
<div id="header">
    <div class="hd-inwrap">
        <h1 class="logo">
            <a href="/internal/index.do"><img src="<c:url value="/images/cmm/logo1.png"/>" alt="화성시 데이터포털 관리시스템"/></a>
        </h1>
        <div class="gnb-wrap">
            <ul class="gnb">
                <li class="nav1 ${type == 'user' ? 'on' : ''}">
                    <a href="<c:url value="/permission/userList.do"/>"><i class="fa fa-user-circle-o" aria-hidden="true"></i>사용자 및 권한 관리</a>
                </li>
                <li class="nav2 ${type == 'category' ? 'on' : ''}">
                    <a href="<c:url value="/menu/pubCategoryList.do"/>"><i class="fa fa-list" aria-hidden="true"></i>메뉴 관리</a>
                </li>
                <li class="nav3 ${type == 'screen' ? 'on' : ''}">
                    <a href="<c:url value="/screen/externalMainSreenManagent.do"/>"><i class="fa fa-desktop" aria-hidden="true"></i>메인화면 관리</a>
                </li>
                <li class="nav4 ${type == 'data' ? 'on' : ''}">
                    <a href="<c:url value="/data/status/dataStatusList.do"/>"><i class="fa fa-server" aria-hidden="true"></i>데이터 관리</a>
                </li>
                <li class="nav5  ${type == 'stats' ? 'on' : ''}">
                    <a href="<c:url value="/stats/publicAccess/publicAccessCountList.do"/>"><i class="fa fa-bar-chart" aria-hidden="true"></i>이용현황 통계 관리</a>
                </li>
                <li class="nav6 ${type == 'board' ? 'on' : ''}">
                    <a href="<c:url value="/admin/board/notice/list.do"/>"><i class="fa fa-pencil-square-o" aria-hidden="true"></i>게시판 관리</a>
                </li>
            </ul>
        </div>
    </div>
</div>