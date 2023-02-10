<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge" >
  <title>화성시 데이터 포털 관리 시스템</title>

 
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/base.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/admin.css" />"/>
    <script type="text/javascript" src="<c:url value="/js/lib/jquery/jquery-1.12.4.min.js"/>"></script>
    <script>
    $(document).ready(function(){
	    	$('#goHome').on('click',function(){
	    		 $('#error').attr('action', '<c:url value="/internal/index.do"/>');
             $('#error').submit();
	    	});
    });
   
    </script>

  
 </head>
 <body>
	<div id="wrap">

		<!-- 에러 페이지 -->
		<div class="error-wrap">
			<div class="cont">				
				<img src="<c:url value="../images/admin/img-error.png"/>" alt="error"/>
				<p class="txt">죄송합니다. 요청하신 작업을 처리하는 도중 에러가 발생하였습니다.<br/>잠시 후에 다시 시도하여 주시기 바랍니다.</p>
				<a href="#" id="goHome" class="btn sr2 st3">홈으로</a>
				<form id='error' name='error'  method="POST">
				</form>
			</div>			
		</div>
		<!--// 에러 페이지 -->
	</div>

 </body>
</html>
