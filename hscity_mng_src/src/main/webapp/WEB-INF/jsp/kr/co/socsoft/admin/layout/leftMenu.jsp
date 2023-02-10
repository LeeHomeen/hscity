<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: G1
  Date: 2018. 2. 1.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>

var link = document.location.href;
var array1 = link.split('?');
var array = array1[0].split('/');

$(document).ready(function() { 
	
	// 공통메뉴 컨트롤 
	$('.lnb').hide();
    switch (array[3]){
        case 'permission':
            // 사용자 및 권한 관리 
            $('ul.gnb>li').removeClass("on");
            $('.nav1').addClass("on");
            $('#permission-ul').show();
            switch (array[4]){
	     		case 'editUserPermission.do':
	     		case 'userList.do':
	                // 사용자 관리 
	                $('ul.lnb>li').removeClass("on");
	                $('#permission-ul li:nth-child(1)').addClass("on");
	            break;
	     		case 'userSyncLogList.do':
	                // 사용자 동기화 로그 
	                $('ul.lnb>li').removeClass("on");
	                $('#permission-ul li:nth-child(2)').addClass("on");
	            break;
	     		case 'permissionManagement.do':
	                // 권한관리 
	                $('ul.lnb>li').removeClass("on");
	                $('#permission-ul li:nth-child(3)').addClass("on");
	            break;
	     		default:
	                // 권한 - 업무그룹 관리 
	                $('ul.lnb>li').removeClass("on");
	                $('#permission-ul li:nth-child(1)').addClass("on");
	            break;
            }
            
            break;
        case 'menu':
            // 메뉴관리 
            $('ul.gnb>li').removeClass("on");
            $('.nav2').addClass("on");
            $('#menu-ul').show();
            switch (array[4]){
	     		case 'pubCategoryList.do':
	                // 대시민 카테고리 관리 
	                $('ul.lnb>li').removeClass("on");
	                $('#menu-ul li:nth-child(1)').addClass("on");
	            break;
	     		case 'pubMenuList.do':
	     		case 'editMenu.do':
	                // 대시민 메뉴화면 관리 
	                $('ul.lnb>li').removeClass("on");
	                $('#menu-ul li:nth-child(2)').addClass("on");
	            break;
	     		case 'bizCategoryList.do':
	                // 내부용 카테고리 관리  
	                $('ul.lnb>li').removeClass("on");
	                $('#menu-ul li:nth-child(3)').addClass("on");
	            break;
	     		case 'bizMenuList.do':
	                // 대시민 메뉴화면 관리 
	                $('ul.lnb>li').removeClass("on");
	                $('#menu-ul li:nth-child(4)').addClass("on");
	            break;
	     		default:
	                // 내부용 메뉴화면 관리 
	                $('ul.lnb>li').removeClass("on");
	                $('#menu-ul li:nth-child(1)').addClass("on");
	            break;
	        }
            break;
        case 'screen':
            // 메인화면 관리 
            $('ul.gnb>li').removeClass("on");
            $('.nav3').addClass("on");
            break;
        case 'data':
            //데이터 관리 
            $('ul.gnb>li').removeClass("on");
            $('.nav4').addClass("on");
            $('#data-ul').show();
	            switch (array[4]){
	     		case 'dataStatusList.do':
	                // 데이터현황
	                $('ul.lnb>li').removeClass("on");
	                $('#data-ul li:nth-child(1)').addClass("on");
	            break;
	     		case 'gyeonggiDataLink.do':
	                //경기 데이 드림 
	                $('ul.lnb>li').removeClass("on");
	                $('#data-ul li:nth-child(2)').addClass("on");
	            break;
	     		case 'rightsMinwonDataLink.do':
	                //권익위 민원
	                $('ul.lnb>li').removeClass("on");
	                $('#data-ul li:nth-child(3)').addClass("on");
	            break;
	     		case 'bigDataUploadList.do':
	                //대용량데이터 업로드 관리  
	                $('ul.lnb>li').removeClass("on");
	                $('#data-ul li:nth-child(4)').addClass("on");
	            break;
	     		case 'fileDataUpload.do':
	     			//파일데이터 업로드 관리 
	                $('ul.lnb>li').removeClass("on");
	                $('#data-ul li:nth-child(5)').addClass("on");
	            break;
	     		case 'systemLinkList.do':
	                //시스템 연계 관리 
	                $('ul.lnb>li').removeClass("on");
	                $('#data-ul li:nth-child(6)').addClass("on");
	            break;
	     		case 'dataUploadList.do':
	     			//데이터 업로드 관리 
	                $('ul.lnb>li').removeClass("on");
	                $('#data-ul li:nth-child(7)').addClass("on");
	            break;
	     		default:
	     			 // 데이터현황
	                $('ul.lnb>li').removeClass("on");
	                $('#data-ul li:nth-child(1)').addClass("on");
	            break;
        }
            break;
        case 'stat':
            // 이용현황 통계관
            $('ul.gnb>li').removeClass("on");
            $('.nav5').addClass("on");
            break;
        case 'board':
            // 게시판 관리 
            $('ul.gnb>li').removeClass("on");
            $('.nav6').addClass("on");
            break;
        default:
            $('ul.gnb>li').removeClass("on");
            $('.nav1').addClass("on");
            break;
            
    }
});
</script>
<div class="sec-left">

				<!-- 유저 정보 -->
				<div class="sec-user">
					<p class="user">
						<i class="fa fa-address-card-o fa-2x"></i>
						정책기획과 <strong>홍길동</strong> 님
					</p>
					<button class="btn sr1 st1">로그아웃</button>
				</div>
				<!-- //유저 정보 -->
				
				<!--1ver 2depth 메뉴 -->
				<ul class="lnb" id="permission-ul">
					<li class="on">
						<a href="/permission/userList.do">
							<i class="fa fa-check" aria-hidden="true"></i>사용자 관리
						</a>
					</li>
					<li>
						<a href="/permission/userSyncLogList.do">
							<i class="fa fa-check" aria-hidden="true"></i>사용자 동기화 로그
						</a> 
					</li>
					<li>
						<a href="/permission/permissionManagement.do">
							<i class="fa fa-check" aria-hidden="true"></i> 부서별 메뉴권한 
						</a>
					</li>

				</ul>
				<!-- //2depth 메뉴 -->
				
				<!--2ver 2depth 메뉴 -->
				<ul class="lnb" id="menu-ul">
					<li class="on">
						<a href="/menu/pubCategoryList.do">
							<i class="fa fa-check" aria-hidden="true"></i>대시민 카테고리 관리
						</a>
					</li>
					<li>
						<a href="/menu/pubMenuList.do">
							<i class="fa fa-check" aria-hidden="true"></i>대시민 메뉴화면 관리
						</a> 
					</li>
					<li>
						<a href="/menu/bizCategoryList.do">
							<i class="fa fa-check" aria-hidden="true"></i>내부용 카테고리 관리
						</a>
					</li>
					<li>
						<a href="/menu/bizMenuList.do">
							<i class="fa fa-check" aria-hidden="true"></i>내부용 메뉴화면 관리
						</a>
					</li>
				</ul>
				<!-- //2depth 메뉴 -->
				
				<!--3ver 2depth 메뉴 -->
			<!-- 	<ul class="lnb">
					<li class="on">
						<a href="#">
							<i class="fa fa-check" aria-hidden="true"></i>대시민 메인화면 관리
						</a>
					</li>
					<li>
						<a href="#">
							<i class="fa fa-check" aria-hidden="true"></i>내부용 메인화면 관리
						</a> 
					</li>
				</ul>
				//2depth 메뉴
				-->
				
				<ul class="lnb" id="data-ul">
					<li class="on">
						<a href="/data/dataStatusList.do">
							<i class="fa fa-check" aria-hidden="true"></i>데이터 현황
						</a>
					</li>
					<li>
						<a href="/data/gyeonggiDataLink.do">
							<i class="fa fa-check" aria-hidden="true"></i>경기데이터드림 연계 관리
						</a> 
					</li>
					<li>
						<a href="/data/rightsMinwonDataLink.do">
							<i class="fa fa-check" aria-hidden="true"></i>권익위 민원 연계 관리
						</a>
					</li>
					<li>
						<a href="/data/bigDataUploadList.do">
							<i class="fa fa-check" aria-hidden="true"></i>대용량데이터 업로드 관리
						</a>
					</li>
					<li>
						<a href="/data/fileDataUpload.do">
							<i class="fa fa-check" aria-hidden="true"></i>파일데이터 업로드 관리
						</a>
					</li>
					<li>
						<a href="/data/systemLinkList.do">
							<i class="fa fa-check" aria-hidden="true"></i>내부시스템 연계 관리
						</a>
					</li>
					<li>
						<a href="/data/dataUploadList.do">
							<i class="fa fa-check" aria-hidden="true"></i>데이터 업로드 관리
						</a>
					</li>
				</ul> 
				<!-- //2depth 메뉴 -->
			</div>
