<%@ page contentType="text/html;charset=UTF-8" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>화성시 데이터 포털 관리 시스템</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/biz/jquery-ui.css"/>">
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/biz/base.css"/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/biz/layout.css"/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/biz/simpletree.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/biz/custom.css"/>" />

	<script type="text/javascript" src="<c:url value="/js/lib/jquery/jquery-1.11.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jqgrid/jquery-ui.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/js/biz/jquery.simpletree.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/js/biz/popup.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/js/biz/common.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/js/cmm/common.util.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/js/biz/custom.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/biz/jquery-ui.js"/>"></script>
	<%-- underscore JS --%>
	<script type="text/javascript" src="<c:url value="/js/lib/underscore/underscore-min.js"/>"></script>

	<%-- 공통 JS --%>
	<script type="text/javascript" src="<c:url value="/js/cmm/common.js"/>"></script>

	<%--smarteditor--%>
	<script type="text/javascript" src="<c:url value="/js/lib/smarteditor/js/HuskyEZCreator.js"/>"></script>

	<script type="text/javascript">
        // jsp 에서 언더스코어 템플릿 기능을 사용하기 위한 선언
        _.templateSettings = {
            interpolate: /\<\@\=(.+?)\@\>/gim,
            evaluate: /\<\@(.+?)\@\>/gim,
            escape: /\<\@\-(.+?)\@\>/gim
        };
	</script>
</head>