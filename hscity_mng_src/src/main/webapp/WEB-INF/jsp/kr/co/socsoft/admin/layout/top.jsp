<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>화성시 데이터 포털 관리 시스템</title>

    <link rel="stylesheet" type="text/css" href="<c:url value="/css/jqgrid/jquery-ui.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/jqgrid/ui.jqgrid.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/base.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/admin.css" />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/custom.css"/>"/>

    <script type="text/javascript" src="<c:url value="/js/lib/fontawesome/fontawesome.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jqgrid/jquery.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/lib/jquery/jquery-1.12.4.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jqgrid/jquery-ui.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jqgrid/jquery.jqgrid.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jqgrid/jquery.table2excel.min.js"/>"></script>

    <script type="text/javascript" src="<c:url value="/js/cmm/sidebar-menu.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/cmm/common.js"/>"></script>

    <!-- ck editor -->
    <script type="text/javascript" src="<c:url value="/js/lib/ckeditor/ckeditor.js"/>"></script>
</head>
<body>
<div id="wrap">
    <!-- 헤더 -->
    <div id="header">
        <div class="hd-inwrap">
            <h1 class="logo">
                <a href="#"><img src="<c:url value="/images/cmm/logo1.png"/>" alt="화성시 데이터포털 관리시스템"/></a>
            </h1>
            <div class="gnb-wrap">
                <ul class="gnb">
                    <li class="nav1 on">
                        <a href="<c:url value="/permission/userList.do"/>"><i class="fa fa-user-circle-o" aria-hidden="true"></i>사용자 및 권한 관리</a>
                    </li>
                    <li class="nav2 on">
                        <a href="<c:url value="/menu/pubCategoryList.do"/>"><i class="fa fa-list" aria-hidden="true"></i>메뉴 관리</a>
                    </li>
                    <li class="nav3 on">
                        <a href="#"><i class="fa fa-desktop" aria-hidden="true"></i>메인화면 관리</a>
                    </li>
                    <li class="nav4 on">
                        <a href="<c:url value="/data/dataStatusList.do"/>"><i class="fa fa-server" aria-hidden="true"></i>데이터 관리</a>
                    </li>
                    <li class="nav5 on">
                        <a href="#"><i class="fa fa-bar-chart" aria-hidden="true"></i>이용현황 통계 관리</a>
                    </li>
                    <li class="nav6 on">
                        <a href="#"><i class="fa fa-pencil-square-o" aria-hidden="true"></i>게시판 관리</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <!-- //헤더 -->

    <div id="container">
			