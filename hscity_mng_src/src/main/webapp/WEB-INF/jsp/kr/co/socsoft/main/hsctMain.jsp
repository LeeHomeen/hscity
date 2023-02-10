<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" >
    	
    <title>hsct</title>
    <script type="text/javascript">
    	//gis화면으로 이동
    	function openPopupGis(chk){
    		window.open("/gis/"+chk+".do");
    	}
    	
    	function openPopupAdmin(){
    		window.open("/permission/userList.do");
    	}
    	
    	function openLoginPage() {
    		window.open("/login/loginPage.do");
    	}
    </script>    
</head>
<body>
	<div id="content">
		<!-- 
		<div>
			메인화면 
		</div>
		
		<div>
			<a href="#" onclick="openPopupGis('pop')"> 인구 매출분석 지도 팝업화면</a>
		</div>
		<br/>
		<div>
			<a href="#" onclick="openPopupGis('traffic')"> 교통분석지도 팝업화면</a>
		</div>
		<br>
		<div>
		<a href="#" onclick="openPopupAdmin()">관리자(사용자관리페이지로 이동)</a>
		</div>
		<br>
		<div>
		<a href="#" onclick="openLoginPage()">로그인페이지</a>
		</div>
		 -->
	</div>	
</body>
</html>
