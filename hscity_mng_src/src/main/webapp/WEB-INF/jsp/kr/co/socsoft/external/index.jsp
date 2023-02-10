<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/common/head.jsp" %>

<body>
  	<!-- 헤더영역 //-->
	<header>
		<h1><a href="main.html">화성시 대시민 데이터 포털</a></h1>
		<div>
			<input type="text" placeholder="메뉴명을 검색할 수 있습니다">
			<button type="button" class="btn-search poplink" poplink="#pop-searchresult"></button>	
			<img src="../images/cmm/menu.png" alt="Menu" class="navBtn">		
		</div>
	</header>
	<!--// 헤더영역 -->
	
	<!-- 우메뉴 //-->
	<nav class="sideNav">
		<a href="business.html"><img src="../images/cmm/icon-right-menu5.png"><span>사업소개</span></a>
		<a href="portal.html"><img src="../images/cmm/icon-right-menu6.png"><span>데이터 포털 소개</span></a>
		<a href="notice_list.html"><img src="../images/cmm/icon-right-menu1.png"><span>공지사항</span></a>
		<a href="faq.html"><img src="../images/cmm/icon-right-menu2.png"><span>FAQ</span></a>
		<a href="qna_list.html"><img src="../images/cmm/icon-right-menu3.png"><span>Q&amp;A</span></a>
	</nav>
	<!--// 우메뉴 -->

	<div class="AllWrap">
		<!-- 콘텐츠영역 //-->
			
		<ul class="summaryWrap">
			<li>
				<a href="#">
					<div class="con">
						<img src="../images/main/icon1.png" alt="">
						<h3>화성시 인구수</h3>
						<p class="big"><strong>690,000</strong>명</p>
						<p class="small">* 경기도 인구수<br>13,090,000명</p>
					</div>
				</a>
			</li>
			<li>
				<a href="#">
					<div class="con">
						<img src="../images/main/icon2.png" alt="">
						<h3>관리데이터 항목 수</h3>
						<p class="big"><strong>약 200</strong>종</p>
						<p class="small">* 포털에서 수집하는 데이터 항목 수입니다.</p>
					</div>
				</a>
			</li>
			<li>
				<a href="#">
					<div class="con">
						<img src="../images/main/icon3.png" alt="">
						<h3>일 접속자 수<span>(누계)</span></h3>
						<p class="big"><strong>10,000</strong>명</p>
						<p class="small">* 어제 일자까지의 접속자수 누계입니다.</p>
					</div>
				</a>
			</li>
			<li>
				<a href="#">
					<div class="con">
						<img src="../images/main/icon4.png" alt="">
						<h3>기상 <strong>15℃</strong></h3>
						<p class="big">미세먼지 <strong>보통</strong></p>
						<p class="small">* 현재 시내 평균 기온과<br>미세먼지를 나타냅니다.</p>
					</div>
				</a>
			</li>
		</ul>

		<div class="dataWrap">
			<div class="dataItem w50p h300">
				<div class="dataContent">

				</div>
			</div>
			<div class="dataItem w50p h300">
				<div class="dataContent">

				</div>
			</div>
		</div>

		<dl class="notice">
			<dt><img src="../images/cmm/icon-notice.png">공지사항</dt>
			<dd><a href="#">공지사항 제목이 들어가는 자리입니다. 제목을 넣어주세요.</a></dd>
		</dl>
		
		<!--// 콘텐츠영역 -->		
	</div>

	<!-- 푸터영역 //-->
	<footer>
		<p>Copyright(c) 2018 by Hwaseong City. All Rights Reserved.</p>
		<div class="familyWrap">
			<h3>Family Site</h3>
			<ul>
				<li><a href="#" target="_blank">화성시</a></li>
				<li><a href="#" target="_blank">화성시</a></li>
			</ul>
		</div>
	</footer>
	<!--// 푸터영역 -->
	
	<!-- 모달 : 비밀번호 변경 //-->
	<div class="pop" id="pop-pwfind">
		<span class="button b-close"><span><img src="../images/cmm/btn_close.png" alt="close"></span></span>
		<h3>비밀번호 변경</h3>
		<div class="content">
			<ul>
				<li><input type="password" placeholder="현재 비밀번호"></li>
				<li><input type="password" placeholder="Password"></li>
				<li><input type="password" placeholder="Password 확인"></li>
			</ul>
			<a href="#" class="btn btn-darkgray btn-pop mt20">비밀번호 변경</a>
		</div>
	</div>
	<!--// 모달 : 비밀번호 변경 -->
	
	<!-- 모달 : 메뉴명 검색결과 //-->
	<div class="pop" id="pop-searchresult">
		<span class="button b-close"><span><img src="../images/cmm/btn_close.png" alt="close"></span></span>
		<h3 class="line">메뉴명 검색결과 입니다</h3>
		<div class="content">
			<table class="tbl-pop" summary="">
				<caption></caption>
				<colgroup>
					<col>
					<col width="100px">
				</colgroup>
				<tbody>
					<tr>
						<td>01_TEST_대메뉴 > 1번에 1번째 메뉴입니다</td>
						<td class="t-right"><a class="btn btn-orange">바로가기</a></td>
					</tr>
					<tr>
						<td>01_TEST_대메뉴 > 1번에 1번째 메뉴입니다</td>
						<td class="t-right"><a class="btn btn-orange">바로가기</a></td>
					</tr>
					<tr>
						<td>01_TEST_대메뉴 > 1번에 1번째 메뉴입니다</td>
						<td class="t-right"><a class="btn btn-orange">바로가기</a></td>
					</tr>
					<tr>
						<td>01_TEST_대메뉴 > 1번에 1번째 메뉴입니다</td>
						<td class="t-right"><a class="btn btn-orange">바로가기</a></td>
					</tr>
					<tr>
						<td>01_TEST_대메뉴 > 1번에 1번째 메뉴입니다</td>
						<td class="t-right"><a class="btn btn-orange">바로가기</a></td>
					</tr>
					<tr>
						<td>01_TEST_대메뉴 > 1번에 1번째 메뉴입니다</td>
						<td class="t-right"><a class="btn btn-orange">바로가기</a></td>
					</tr>
					<tr>
						<td>01_TEST_대메뉴 > 1번에 1번째 메뉴입니다</td>
						<td class="t-right"><a class="btn btn-orange">바로가기</a></td>
					</tr>
					<tr>
						<td>01_TEST_대메뉴 > 1번에 1번째 메뉴입니다</td>
						<td class="t-right"><a class="btn btn-orange">바로가기</a></td>
					</tr>
					<tr>
						<td>01_TEST_대메뉴 > 1번에 1번째 메뉴입니다</td>
						<td class="t-right"><a class="btn btn-orange">바로가기</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<!--// 모달 : 메뉴명 검색결과 -->
	
	<script src="../js/lib/jquery/jquery-1.11.0.min.js"></script>
	<script src="../js/lib/jquery/jquery-ui.js"></script>
	<script src="../js/cmm/jquery.simpletree.js"></script>
	<script src="../js/cmm/popup.js"></script>
	<script>
		$(function(){
			// 우메뉴 열기&닫기
			$(".navBtn").click(function(){
				$(".sideNav").toggleClass("show");
				var src = $(this).attr('src');
				var newsrc = (src=='../images/cmm/menu.png') ? '../images/cmm/menu_close.png' : '../images/cmm/menu.png';
				$(this).attr('src', newsrc );
			});
			//좌메뉴 열기&닫기
			$(".cateBtn").click(function(){
				$(".cateWrap").toggleClass("fold");
				var src = $(this).attr('src');
				var newsrc = (src=='../images/cmm/btncate_close.png') ? '../images/cmm/btncate.png' : '../images/cmm/btncate_close.png';
				$(this).attr('src', newsrc );
			});
			// 패밀리사이트
			$(".familyWrap h3").click(function(){
				$(".familyWrap ul").toggle();
			});
			// 트리메뉴
			$(".cateWrap").height($('.contentWrap').outerHeight());
			$("#treemenu").accordion({active: false, collapsible: true, heightStyle: "content"});
			$("#treemenu > div > ul").simpleTree({startCollapsed: false});
			// 모달 띄우기
			$('.poplink').on('click', function(e){
				e.preventDefault();
				$($(this).attr('href')).bPopup();
				$($(this).attr('poplink')).bPopup();
			});
			//즐겨찾기 추가
			$('.favorite').click(function(){
				$(this).toggleClass('on');
			})
		})
	</script>

</body>
</html>