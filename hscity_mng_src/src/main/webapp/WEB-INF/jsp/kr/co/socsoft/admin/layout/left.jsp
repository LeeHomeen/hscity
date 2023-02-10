<%@ page contentType="text/html;charset=UTF-8" %>
<div class="sec-left">
    <!-- 유저 정보 -->
    <div class="sec-user">
        <p class="user">
            <i class="fa fa-address-card-o fa-2x"></i>
            	${sessionScope.LoginVO.deptName} <strong>${sessionScope.LoginVO.userName}</strong> 님
        </p>
        <a href="<c:url value="/login/logout.do"/>" class="btn sr1 st1" id="btnLogout">로그아웃</a>
    </div>

    <!-- //유저 정보 -->

    <!--1ver 2depth 메뉴 -->
    <c:if test="${type == 'user'}">
        <ul class="lnb" id="permission-ul">
            <li class="${menu == 'list' ? 'on' : '' }"><a href="<c:url value="/permission/userList.do"/>"><i class="fa fa-check" aria-hidden="true"></i>사용자 관리</a></li>
            <li class="${menu == 'sync' ? 'on' : '' }"><a href="<c:url value="/permission/userSyncLogList.do"/>"><i class="fa fa-check" aria-hidden="true"></i>사용자 동기화 로그</a></li>
            <li class="${menu == 'permissionManagement' ? 'on' : '' }"><a href="<c:url value="/permission/permissionManagement.do"/>"><i class="fa fa-check" aria-hidden="true"></i> 부서별 메뉴권한</a>
            </li>
        </ul>
    </c:if>
    <!-- //2depth 메뉴 -->

    <!--2ver 2depth 메뉴 -->
    <c:if test="${type == 'category'}">
        <ul class="lnb" id="menu-ul">
            <li class="${menu == 'pubctg' ? 'on' : '' }"><a href="<c:url value="/menu/pubCategoryList.do"/>"><i class="fa fa-check" aria-hidden="true"></i>대시민 카테고리 관리</a></li>
            <li class="${menu == 'pub' ? 'on' : '' }"><a href="<c:url value="/menu/pub/menuList.do"/>"><i class="fa fa-check" aria-hidden="true"></i>대시민 메뉴화면 관리</a></li>
            <li class="${menu == 'bizctg' ? 'on' : '' }"><a href="<c:url value="/menu/bizCategoryList.do"/>"><i class="fa fa-check" aria-hidden="true"></i>내부용 카테고리 관리</a></li>
            <li class="${menu == 'biz' ? 'on' : '' }"><a href="<c:url value="/menu/biz/menuList.do"/>"><i class="fa fa-check" aria-hidden="true"></i>내부용 메뉴화면 관리</a></li>
        </ul>
    </c:if>
    <!-- //2depth 메뉴 -->

    <!--3ver 2depth 메뉴 -->
    <c:if test="${type == 'screen'}">
   	 	<ul class="lnb">
            <li class="${menu == 'pub' ? 'on' : '' }"><a href="<c:url value="/screen/externalMainSreenManagent.do"/>"><i class="fa fa-check" aria-hidden="true"></i>대시민 메인화면 관리</a></li>
            <li class="${menu == 'biz' ? 'on' : '' }"><a href="<c:url value="/screen/internalMainSreenManagent.do"/>"><i class="fa fa-check" aria-hidden="true"></i>내부용 메인화면 관리</a></li>
            <li class="${menu == 'pubBanner' ? 'on' : '' }"><a href="<c:url value="/screen/externalMainBannerManagent.do"/>"><i class="fa fa-check" aria-hidden="true"></i>대시민 메인화면 배너 관리</a></li>
        </ul>
       </c:if>
    <c:if test="${type == 'data'}">
        <ul class="lnb" id="data-ul">
            <li class="${menu == 'status' ? 'on' : '' }"><a href="<c:url value="/data/status/dataStatusList.do"/>"><i class="fa fa-check" aria-hidden="true"></i>데이터 현황</a></li>
            <li class="${menu == 'ggapi' ? 'on' : '' }"><a href="<c:url value="/data/ggapi/gyeonggiDataLink.do"/>"><i class="fa fa-check" aria-hidden="true"></i>경기데이터드림 연계 관리</a></li>
            <li class="${menu == 'minwon' ? 'on' : '' }"><a href="<c:url value="/data/minwon/rightsMinwonDataLink.do"/>"><i class="fa fa-check" aria-hidden="true"></i>권익위 민원 연계 관리</a></li>
            <li class="${menu == 'bigDataUpload' ? 'on' : '' }"><a href="<c:url value="/data/upload/bigDataUploadList.do"/>"><i class="fa fa-check" aria-hidden="true"></i>대용량데이터 업로드 관리</a></li>
            <li class="${menu == 'fileDataUpload' ? 'on' : '' }"><a href="<c:url value="/data/upload/fileDataUpload.do"/>"><i class="fa fa-check" aria-hidden="true"></i>파일데이터 업로드 관리</a></li>
            <li class="${menu == 'systemLink' ? 'on' : '' }"><a href="<c:url value="/data/systemLink/systemLinkList.do"/>"><i class="fa fa-check" aria-hidden="true"></i>내부시스템 연계 관리</a></li>
            <li class="${menu == 'dataUpload' ? 'on' : '' }"><a href="<c:url value="/data/upload/dataUploadList.do"/>"><i class="fa fa-check" aria-hidden="true"></i>데이터 업로드 로그 관리</a></li>
        </ul>
    </c:if>
    
        <c:if test="${type == 'stats'}">
        <ul class="lnb" id="data-ul">
            <li class="${menu == 'publicAccess' ? 'on' : '' }"><a href="<c:url value="/stats/publicAccess/publicAccessCountList.do"/>"><i class="fa fa-check" aria-hidden="true"></i>대시민 접속통계</a></li>
            <li class="${menu == 'userAccess' ? 'on' : '' }"><a href="<c:url value="/stats/userAccess/userAccessCountList.do"/>"><i class="fa fa-check" aria-hidden="true"></i>내부사용자 접속통계</a></li>
            <li class="${menu == 'publicMenu' ? 'on' : '' }"><a href="<c:url value="/stats/publicMenu/publicMenuCountList.do"/>"><i class="fa fa-check" aria-hidden="true"></i>대시민포털 메뉴접속통계</a></li>
            <li class="${menu == 'userMenu' ? 'on' : '' }"><a href="<c:url value="/stats/userMenu/userMenuCountList.do"/>"><i class="fa fa-check" aria-hidden="true"></i>내부포털 메뉴접속통계</a></li>
       
        </ul>
    </c:if>

    <c:if test="${type == 'board'}">
        <ul class="lnb">
            <li class="${menu == 'notice' ? 'on' : '' }"><a href="<c:url value="/admin/board/notice/list.do"/>"><i class="fa fa-check" aria-hidden="true"></i>공지사항</a></li>
            <li class="${menu == 'faq' ? 'on' : '' }"><a href="<c:url value="/admin/board/faq/list.do"/>"><i class="fa fa-check" aria-hidden="true"></i>FAQ</a></li>
            <li class="${menu == 'qa' ? 'on' : '' }"><a href="<c:url value="/admin/board/qa/list.do"/>"><i class="fa fa-check" aria-hidden="true"></i>Q&A</a></li>
            <li class="${menu == 'think' ? 'on' : '' }"><a href="<c:url value="/admin/board/think/list.do"/>"><i class="fa fa-check" aria-hidden="true"></i>생각의 탄생</a></li>
            <li class="${menu == 'dataManegement' ? 'on' : '' }"><a href="<c:url value="/admin/board/dataManegement/list.do"/>"><i class="fa fa-check" aria-hidden="true"></i>데이터도움말(자료관리)</a></li>
            <li class="${menu == 'homeen' ? 'on' : '' }"><a href="<c:url value="/admin/board/leehomeen/list.do"/>"><i class="fa fa-check" aria-hidden="true"></i>이호민 </a></li>
        </ul>
    </c:if>
    <!-- //2depth 메뉴 -->
</div>
