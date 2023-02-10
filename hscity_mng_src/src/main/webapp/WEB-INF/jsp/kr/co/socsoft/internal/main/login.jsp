<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/common/head.jsp" %>
<script type="text/javascript" src="<c:url value="/js/biz/core.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/biz/sha256.min.js"/>"></script>
<script>
if ("${status}" == "false") {
	alert("${msg}");	
}

$(document).ready(function() {
	$("#userId").keypress(function(e) {
	    if(e.keyCode === 13 ) {
	    	login();
	    }
	});
	
	$("#userPwd").keypress(function(e) {
	    if(e.keyCode === 13 ) {
	    	login();
	    }
	});
});
function login() {
	if ($('#userId').val() == "") {
		alert("아이디를 입력하세요.");
		return;
	}
	
	if ($('#userPwd').val() == "") {
		alert("PW를 입력하세요.");
		return;
	}
	
	var form = document.loginform;
	form.action = "/login/userLogin.do";
	//form.userPwd.value = CryptoJS.SHA256($('#userPw').val()).toString();
	form.submit();
}
</script>
<body>

	<div class="loginWrap">		
		<h1><img src="../images/main/logo.png" alt="logo"><br>화성시 데이터 포털</h1>
		<form method="POST" name="loginform" id="loginform" action = "/login/userLogin.do">
			<input type="text" placeholder="ID" class="id" id="userId" name="userId" maxlength="30">
			<!--
			<p>아이디를 확인하시고 입력해 주세요.</p>
			-->
			<input type="password" placeholder="Password" class="password" id="userPwd" name="userPwd" maxlength="30">
			<!--
			<p>비밀번호를 확인하고 입력해 주세요.</p>
			-->
			<a href="javascript:login();" class="btnLogin">로그인</a>
		</form>
	</div>

</body>
</html>
