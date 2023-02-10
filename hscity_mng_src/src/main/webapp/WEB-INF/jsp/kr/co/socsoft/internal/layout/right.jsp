
<%@ page contentType="text/html;charset=UTF-8" %>
<nav class="sideNav">
    <a href="<c:url value="/internal/notice/list.do"/>"><img src="<c:url value="/images/biz/icon-right-menu1.png"/>"><span>공지사항</span></a>
    <a href="<c:url value="/internal/faq/list.do"/>"><img src="<c:url value="/images/biz/icon-right-menu2.png"/>"><span>FAQ</span></a>
    <a href="<c:url value="/internal/qa/list.do"/>"><img src="<c:url value="/images/biz/icon-right-menu3.png"/>"><span>Q&amp;A</span></a>
    <a href="<c:url value="/internal/think/list.do"/>"><img src="<c:url value="/images/biz/icon-right-menu5.png"/>"><span>생각의 탄생</span></a>
    <c:if test="${sysYn == 'y'}">
    <a href="<c:url value="/permission/userList.do"/>"><img src="<c:url value="/images/biz/icon-right-menu4.png"/>"><span>관리자</span></a>
    </c:if>
    
</nav>
