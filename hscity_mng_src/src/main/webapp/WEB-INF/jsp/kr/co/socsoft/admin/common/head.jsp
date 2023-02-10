<%@ page contentType="text/html;charset=UTF-8" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>화성시 데이터 포털 관리 시스템</title>

    <link rel="stylesheet" type="text/css" href="<c:url value="/css/jqgrid/jquery-ui.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/jqgrid/ui.jqgrid.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.modal.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/base.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/admin.css" />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/custom.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/js/lib/dynatree/src/skin/ui.dynatree.css"/>" />
	
    <script type="text/javascript" src="<c:url value="/js/lib/fontawesome/fontawesome.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jqgrid/jquery.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/lib/jquery/jquery-1.12.4.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jqgrid/jquery-ui.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jqgrid/jquery.jqgrid.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jqgrid/jquery.table2excel.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/lib/monthpicker/jquery.mtz.monthpicker.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/lib/jquery/jquery.modal.min.js"/>"></script>

    <%-- underscore JS --%>
    <script type="text/javascript" src="<c:url value="/js/lib/underscore/underscore-min.js"/>"></script>

    <script type="text/javascript" src="<c:url value="/js/cmm/sidebar-menu.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/cmm/common.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/cmm/common.util.js"/>"></script>

    <!-- smarteditor -->
    <script type="text/javascript" src="<c:url value="/js/lib/smarteditor/js/HuskyEZCreator.js"/>"></script>

    <script type="text/javascript" src="<c:url value="/js/lib/dynatree/src/jquery.dynatree.js"/>"></script>

    <script type="text/javascript">
        // jsp 에서 언더스코어 템플릿 기능을 사용하기 위한 선언
        _.templateSettings = {
            interpolate: /\<\@\=(.+?)\@\>/gim,
            evaluate: /\<\@(.+?)\@\>/gim,
            escape: /\<\@\-(.+?)\@\>/gim
        };
    </script>
</head>