	<%@ page contentType="text/html;charset=UTF-8"%>
	<!-- 모달 : 비밀번호 변경 //-->
	<div class="pop" id="pop-pwfind">
		<span class="button b-close"><span><img src="<c:url value="../../images/biz/btn_close.png"/>" alt="close"></span></span>
		<h3>비밀번호 변경</h3>
		<div class="content">
			<ul>
				<li><input type="password" placeholder="현재 비밀번호" id="current_pw"></li>
				<li><input type="password" placeholder="Password" id="change_pw"></li>
				<li><input type="password" placeholder="Password 확인" id="change_pw_confirm"></li>
			</ul>
			<a href="#" class="btn btn-darkgray btn-pop mt20" id="btnChangePw">비밀번호 변경</a>
		</div>
	</div>
	<!--// 모달 : 비밀번호 변경 -->
	
	<!-- 모달 : 메뉴명 검색결과 //-->
	<div class="pop" id="pop-searchresult">
		<span class="button b-close"><span><img src="<c:url value="../../images/biz/btn_close.png"/>" alt="close"></span></span>
		<h3 class="line">메뉴명 검색결과 입니다</h3>
		<div class="content">
			<table class="tbl-pop" summary="" id="tableSearchMenu">
				<caption></caption>
				<colgroup>
					<col>
					<col width="100px">
				</colgroup>
				<tbody>
					
				</tbody>
			</table>
		</div>
	</div>
	<!--// 모달 : 메뉴명 검색결과 -->
	
	<!-- 모달 : 즐겨찾기 목록 //-->
	<div class="pop" id="pop-favorite">
		<span class="button b-close"><span><img src="<c:url value="../../images/biz/btn_close.png"/>" alt="close"></span></span>
		<h3 class="line">즐겨찾기 목록 입니다</h3>
		<div class="content">
			<table class="tbl-pop" summary="" id="favList">
				<caption></caption>
				<colgroup>
					<col width="*">
					<col width="180px">
				</colgroup>
				<tbody>
					<tr>
						<td>01_TEST_대메뉴 > 1번에 1번째 메뉴입니다</td>
						<td class="t-right"><a class="btn btn-orange">바로가기</a> <a class="btn">삭제</a></td>
					</tr>
					<tr>
						<td>01_TEST_대메뉴 > 1번에 1번째 메뉴입니다</td>
						<td class="t-right"><a class="btn btn-orange">바로가기</a> <a class="btn">삭제</a></td>
					</tr>
					<tr>
						<td>01_TEST_대메뉴 > 1번에 1번째 메뉴입니다</td>
						<td class="t-right"><a class="btn btn-orange">바로가기</a> <a class="btn">삭제</a></td>
					</tr>
					<tr>
						<td>01_TEST_대메뉴 > 1번에 1번째 메뉴입니다</td>
						<td class="t-right"><a class="btn btn-orange">바로가기</a> <a class="btn">삭제</a></td>
					</tr>
					<tr>
						<td>01_TEST_대메뉴 > 1번에 1번째 메뉴입니다</td>
						<td class="t-right"><a class="btn btn-orange">바로가기</a> <a class="btn">삭제</a></td>
					</tr>
					<tr>
						<td>01_TEST_대메뉴 > 1번에 1번째 메뉴입니다</td>
						<td class="t-right"><a class="btn btn-orange">바로가기</a> <a class="btn">삭제</a></td>
					</tr>
					<tr>
						<td>01_TEST_대메뉴 > 1번에 1번째 메뉴입니다</td>
						<td class="t-right"><a class="btn btn-orange">바로가기</a> <a class="btn">삭제</a></td>
					</tr>
					<tr>
						<td>01_TEST_대메뉴 > 1번에 1번째 메뉴입니다</td>
						<td class="t-right"><a class="btn btn-orange">바로가기</a> <a class="btn">삭제</a></td>
					</tr>
					<tr>
						<td>01_TEST_대메뉴 > 1번에 1번째 메뉴입니다</td>
						<td class="t-right"><a class="btn btn-orange">바로가기</a> <a class="btn">삭제</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<!--// 모달 : 즐겨찾기 목록 -->
	
	<!-- 모달 : 메뉴 GIS //-->
	<div class=" modal-pop pop-box1 ">
		<div class="layer-pop" style="width:1300px;height:773px;text-align:center;">
			<p class="tit">GIS</p>
			<div class="cont">
				<iframe id="preview" name="preview" src="" style="width:1200px;height:700px;">
				</iframe>
			</div>
			<a href="#" class="btn-close pop-close">닫기</a><!--[d] "pop-close" 클래스 값을 클릭할 시 팝업 닫힘-->
		</div>
	</div>
	<!--// 모달 : 메뉴 GIS -->
