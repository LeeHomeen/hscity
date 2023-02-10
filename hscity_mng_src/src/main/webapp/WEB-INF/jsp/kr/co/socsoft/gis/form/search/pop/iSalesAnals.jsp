<%@ page contentType="text/html;charset=UTF-8" %>   
<div>
	<h2 class="lnb-tit">분석영역 설정</h2>
	<div class="lnb-cont">					
		<!-- 분석영역 -->
		<div id="divPopArea" class="box-cdt"></div>
		<!-- //분석영역 -->

		<!-- 기간설정 -->
		<div class="box-cdt">
			<p class="tit">기간 설정<label id='lblPeriod'></label></p>
			<!--슬라이드 업다운-->
			<button class="btn-drop pst1" title="접기&#47;열기">접기&#47;열기</button>
			<div class="drop-slide">
				<div class="cont">
					<ul>
						<li>
							<span class="head">기준년월 시작</span>
							<label>
								<input id="txtMonthStart" type="text" class="ip sr1 st2 w1 ico-cld" value="2017-12">
							</label>													
						</li>
						<li>								
							<span class="head">기준년월 종료</span>
							<label>
								<input id="txtMonthEnd" type="text" class="ip sr1 st2 w1 ico-cld" value="2018-01">
							</label>
						</li>
					</ul>		
					<p class="ment">※ 기준년월은 데이터가 입력된 시점입니다.</p>
				</div>
			</div>
			<!-- //슬라이드 업다운-->
		</div>
		<!-- //기간설정 -->

		<!-- 아코디언 메뉴 -->
		<ul class="sidebar-menu">			
			<li class="treeview active"> 
				<a href="#" class="title-bar" onclick="getTablePeriod('1-5-1')">시도별 유입매출</a>
				<ul class="treeview-menu">
					<li>
						<span id="colpck1-5-1" class="colorSelector" style="left:30px; top:8px;">
								<div style="background-color: #4374D9"></div>
						</span>	
						<div class="btn-wrap">													
							<button onclick="salesInfluxAnals('/gis/sale/siSaleList.do', '1-5-1', '${flag}')" class="btn sr2 st2">분 석</button>
						</div>
					</li>
				</ul>
			</li>	
			
			<li class="treeview"> 
				<a href="#" class="title-bar" onclick="getTablePeriod('1-5-2')">시군구별(서울,경기) 유입매출</a>
				<ul class="treeview-menu">
					<li>
						<span id="colpck1-5-2" class="colorSelector" style="left:30px; top:8px;">
								<div style="background-color: #9FC93C"></div>
						</span>
						<div class="btn-wrap">
							<button onclick="salesInfluxAnals('/gis/sale/sigunSaleList.do', '1-5-2', '${flag}')" class="btn sr2 st2">분 석</button>
						</div>
					</li>
				</ul>
			</li>
			
			<li class="treeview"> 
				<a href="#" class="title-bar" onclick="getTablePeriod('1-5-3')">화성시내(인접) 매출</a>
				<ul class="treeview-menu">
					<li>
						<span id="colpck1-5-3" class="colorSelector" style="left:30px; top:8px;">
								<div style="background-color: #BA942B"></div>
						</span>
						<div class="btn-wrap">							
							<button onclick="salesInfluxAnals('/gis/sale/adjSaleList.do', '1-5-3', '${flag}')" class="btn sr2 st2">분 석</button>
						</div>
					</li>
				</ul>
			</li>
			
		</ul>
		<!-- //아코디언 메뉴 -->
	</div>

	<!-- 펼치기/접기 버튼 --> 
	<div class="lnb-fold">
		<button onclick="toggleLeftMenu('${flag}','left','1-5')" class="btn-fold">접기</button>
	</div>
</div>

<script type="text/javascript">	
	$(document).ready(function(){
		setColorPicker("1-5-1", '#4374D9'); //common.js
		setColorPicker("1-5-2", '#9FC93C'); //common.js
		setColorPicker("1-5-3", '#BA942B'); //common.js
	});	
</script>    