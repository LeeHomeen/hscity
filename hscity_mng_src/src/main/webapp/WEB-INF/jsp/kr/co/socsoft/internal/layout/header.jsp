<%@ page contentType="text/html;charset=UTF-8"%>
<header>
	<h1><a href="<c:url value="/internal/index.do"/>"><img src="<c:url value="/images/cmm/hscity_logo_small.png"/>"></a></h1>
	<div>
		<input type="text" placeholder="메뉴명을 검색할 수 있습니다" id="inputSearchMenu">
		<button id="btnSearchMenu" type="button" class="btn-search poplink" poplink="#pop-searchresult"></button>
		<c:choose>
			<c:when test="${sessionScope.LoginVO.userName ne null}">
				<p>${sessionScope.LoginVO.deptName} <strong>${sessionScope.LoginVO.userName}</strong>
					<a href="#pop-pwfind" class="btn btn-white ml5 poplink">비밀번호 변경</a>
					<a href="<c:url value="/login/logout.do"/>" class="btn" id="btnLogout">로그아웃</a>
				</p>
			</c:when>
			<c:otherwise>
				<p><a href="<c:url value="/login/loginPage.do"/>" class="btn">로그인</a></p>
			</c:otherwise>
		</c:choose>
		
		<img src="<c:url value="/images/biz/menu.png"/>" alt="Menu" class="navBtn">
	</div>
	
</header>
<script>
window.sessionStorage.clear();
if (window.sessionStorage) {
	sessionStorage.setItem("userId", "${sessionScope.LoginVO.userId}");
	sessionStorage.setItem("ssoUserYn", "${sessionScope.LoginVO.ssoUserYn}");
}
//console.log(window.sessionStorage);
</script>