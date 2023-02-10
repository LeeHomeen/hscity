<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
var loginName = "${sessionScope.LoginVO.userName}";
var sysYn = "${sessionScope.LoginVO.sysYn}";
if(loginName == null || loginName == ""){
    alert("세션이 없거나 만료되었습니다. 로그인 후 이용하실 수 있습니다.");
    location.href= "/login/loginPage.do";
} else {
	if (sysYn != 'y') {
		var path = jQuery(location).attr("href");
		if (path.match('/admin/') == '/admin/' || path.match('/permission/') == '/permission/' || path.match('/menu/') == '/menu/'
			|| path.match('/screen/') == '/screen/' || path.match('/data/') == '/data/' || path.match('/stats/') == '/stats/') {
			alert("사용권한이 없습니다.");
			window.location.href = '/internal/index.do';
		}
	}
}
</script>
