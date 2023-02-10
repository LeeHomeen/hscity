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
					<span class="head">기준년월</span>
					<label>
						<input id="txtBasicDate" type="text" class="ip sr1 st2 w1 ico-cld" value=""/>
					</label> 
					<p class="ment">※ 기준년월은 데이터가 입력된 시점입니다.</p>
				</div>
			</div>
			<!-- //슬라이드 업다운-->
		</div>
		<!-- //기간설정 -->

		<!-- 아코디언 메뉴 -->
		<ul class="sidebar-menu">  
			<li class="treeview active"> 
				<a href="#" class="title-bar" onclick="getTablePeriod('1-1-1')">기간별 분석</a>
				<ul class="treeview-menu">
					<li>
						<div class="box-cdt">
							<p class="tit">분석&#47; 시각화</p>
							<!--슬라이드 업다운-->
							<button class="btn-drop pst1" title="접기&#47;열기">접기&#47;열기</button>
							<div class="drop-slide">
								<div class="cont">
									<ul>
										<li>													
											<span class="sel-cst st2 w3">
												<label>
													<select name="selType1-1-1" id="selType1-1-1">
														<option value="heat" selected="selected">밀도맵(HeatMap)</option>
														<option value="grid">격자</option>
														<option value="beehive">벌집(Beehive)</option>
													</select>
												</label> 
											</span> 
										</li>
										
										<li>													
											<span class="sel-cst st2 w3">
												<label>
													<select name="selColor1-1-1" id="selColor1-1-1">
														<option value="red" selected="selected">빨강(RED)</option>
														<option value="green">녹색(GREEN)</option>
														<option value="blue">파랑(BLUE)</option>
													</select>
												</label> 
											</span> 
										</li>
										<li>												
											<input type="radio" value="month" name="rdPeriod1-1-1" id="cc1" class="rdobox" checked/>
											<label for="cc1">지난6개월 분석</label> 
											<input type="radio" value="year" name="rdPeriod1-1-1" id="cc2" class="rdobox"/>
											<label for="cc2">전년도 분석</label>
										</li>
										<li>
											<span class="head">연령계산:</span>													
											<input type="radio" value="past" name="rdStd1-1-1" id="dd1" class="rdobox" checked/>
											<label for="dd1">기준년</label> 											
											<input type="radio" value="cur" name="rdStd1-1-1" id="dd2" class="rdobox"/>
											<label for="dd2">현재년</label> 
										</li>
									</ul>
									<div class="btn-wrap">
										<button onclick="popAnalsPeriod('1-1-1', '${flag}')" class="btn sr2 st2">분 석</button>
									</div>
								</div>
							</div>
							<!-- //슬라이드 업다운-->
						</div>
					</li>
				</ul>
			</li>
			<li class="treeview"> 
				<a href="#" class="title-bar" onclick="getTablePeriod('1-1-2')">성&#47;연령별 분석</a>
				<ul class="treeview-menu">
					<li>
						<div class="box-cdt">
							<p class="tit">분석&#47; 시각화</p>
							<!--슬라이드 업다운-->
							<button class="btn-drop pst1" title="접기&#47;열기">접기&#47;열기</button>
							<div class="drop-slide">
								<div class="cont">
									<ul>
										<li>													
											<span class="sel-cst st2 w3">
												<label>
													<select name="selType1-1-2" id="selType1-1-2">
														<option value="heat" selected="selected">밀도맵(HeatMap)</option>
														<option value="grid">격자</option>
														<option value="beehive">벌집(Beehive)</option>
													</select>
												</label> 
											</span> 
										</li>													
										<li>													
											<span class="sel-cst st2 w3">
												<label>
													<select name="selColor1-1-2" id="selColor1-1-2">
														<option value="red" selected="selected">빨강(RED)</option>
														<option value="green">녹색(GREEN)</option>
														<option value="blue">파랑(BLUE)</option>
													</select>
												</label> 
											</span> 
										</li>
										<!-- 
										<li>												
											<input type="radio" name="rdPeriod1-1-2" id="dd1" class="rdobox" checked/>
											<label for="dd1">지난 6개월 분석</label> 
											<input type="radio" name="rdPeriod1-1-2" id="dd2" class="rdobox"/>
											<label for="dd2">전년도 분석</label>
										</li>
										 -->
										<li>
											<span class="head">성별</span>
											<input type="checkbox" name="chkMGender1-1-2" id="chkMGender1-1-2" class="chckbox" checked/>
											<label for="chkMGender1-1-2">남자</label> 
											<input type="checkbox" name="chkWGender1-1-2" id="chkWGender1-1-2" class="chckbox"/>
											<label for="chkWGender1-1-2">여자</label>
										</li>
										<li>
											<span class="head">연령</span>													
											<label>
												<input id="sage1-1-2" type="text" class="ip sr2 st2 w4 numbersOnly"/>
											</label> ~											
											<label>
												<input id="eage1-1-2" type="text" class="ip sr2 st2 w4 numbersOnly"/>
											</label> 세
										</li>
										<li>
											<span class="head">세대원수</span>													
											<label>
												<input id="smember1-1-2" type="text" class="ip sr2 st2 w4 numbersOnly"/>
											</label> ~											
											<label>
												<input id="emember1-1-2" type="text" class="ip sr2 st2 w4 numbersOnly"/>
											</label>
										</li>
										<li>
											<span class="head">연령계산:</span>													
											<input type="radio" value="past" name="rdStd1-1-2" id="dd3" class="rdobox" checked/>
											<label for="dd3">기준년</label> 											
											<input type="radio" value="cur" name="rdStd1-1-2" id="dd4" class="rdobox"/>
											<label for="dd4">현재년</label> 
										</li>
										<li>
											<p class="ment">※ 연령의 범위는 30세 이하로 설정 가능</p>
										</li>
									</ul>												
									<div class="btn-wrap">
										<button onclick="popAnalsAge('1-1-2', '${flag}')" class="btn sr2 st2">분 석</button>
									</div>
								</div>
							</div>
							<!-- //슬라이드 업다운-->
						</div>
					</li>
				</ul>
			</li>
			<li class="treeview"> 
				<a href="#" class="title-bar" onclick="getTablePeriod('1-1-3')">전입 분석</a>
				<ul class="treeview-menu">
					<li>									
						<div class="box-cdt">
							<p class="tit">관내&#47;외 선택</p>
							<!--슬라이드 업다운-->
							<button class="btn-drop pst1" title="접기&#47;열기">접기&#47;열기</button>
							<div class="drop-slide">
								<div class="cont">													
									<span class="sel-cst st2 w3">
										<label>
											<select name="selRange1-1-3" id="selRange1-1-3">
												<option value="all" selected="selected">전체</option>
												<option value="in">관내(화성시 내)</option>
												<option value="out">관외(화성시 이외지역)</option>
											</select>																	
										</label>										 											 
									</span>					
									
									<span id="colpck1-1-3" class="colorSelector">
										<div style="background-color: #4374D9"></div>
									</span>
															
									<div class="btn-wrap">																		
										<button onclick="inOutPopAnals('/gis/pop/inPopList.do', '1-1-3', '${flag}')" class="btn sr2 st2">분 석</button>
									</div>
								</div>
							</div>
							<!-- //슬라이드 업다운-->
						</div>
					</li>
				</ul>
			</li>
			<li class="treeview"> 
				<a href="#" class="title-bar" onclick="getTablePeriod('1-1-4')">전출 분석</a>
				<ul class="treeview-menu">
					<li>									
						<div class="box-cdt">
							<p class="tit">관내&#47;외 선택</p>
							<!--슬라이드 업다운-->
							<button class="btn-drop pst1" title="접기&#47;열기">접기&#47;열기</button>
							<div class="drop-slide">
								<div class="cont">													
									<span class="sel-cst st2 w3">
										<label>
											<select name="selRange1-1-4" id="selRange1-1-4">
												<option value="all" selected="selected">전체</option>
												<option value="in">관내(화성시 내)</option>
												<option value="out">관외(화성시 이외지역)</option>
											</select>
										</label>
									</span>
									
									<span id="colpck1-1-4" class="colorSelector">
										<div style="background-color: #9FC93C"></div>
									</span>
									
									<div class="btn-wrap">
										<button onclick="inOutPopAnals('/gis/pop/outPopList.do', '1-1-4', '${flag}')" class="btn sr2 st2">분 석</button>
									</div>
								</div>
							</div>
							<!-- //슬라이드 업다운-->
						</div>
					</li>
				</ul>
			</li>
			<!-- 
			<li class="treeview"> 
				<a href="#" class="title-bar">추가 분석</a>
				<ul class="treeview-menu">
					<li>									
						<div class="box-cdt" style="border-bottom:transparent;">
							<p class="tit">추가분석(최대3가지)</p>							
							<button class="btn-drop pst1" title="접기&#47;열기">접기&#47;열기</button>
							<div class="drop-slide">
								<div class="cont">													
									<div class="sel-box">
										<ul class="sel-list">
											<li class="ment">※ 공간데이터명(기준년월)</li>
											<li>
												<input type="checkbox" name="ff" id="ff1" class="chckbox"/>
												<label for="ff1">학교현황 (2017.01)</label>	
											</li>
											<li>
												<input type="checkbox" name="ff" id="ff2" class="chckbox"/>
												<label for="ff2">어린이집현황 (2017.01)</label>	
											</li>
											<li>
												<input type="checkbox" name="ff" id="ff3" class="chckbox"/>
												<label for="ff3">CCTV (2017.01)</label>	
											</li>
											<li>
												<input type="checkbox" name="ff" id="ff4" class="chckbox"/>
												<label for="ff4">시청 (2017.01)</label>	
											</li>
											<li>
												<input type="checkbox" name="ff" id="ff5" class="chckbox"/>
												<label for="ff5">노인복지시설 (2017.01)</label>	
											</li>
											<li>
												<input type="checkbox" name="ff" id="ff6" class="chckbox"/>
												<label for="ff7">상담센터 (2017.01)</label>	
											</li>
											<li>
												<input type="checkbox" name="ff" id="ff8" class="chckbox"/>
												<label for="ff8">병원 (2017.01)</label>	
											</li>
										</ul>
									</div>
									<div class="btn-wrap">
										<button class="btn sr2 st2">분 석</button>
									</div>
								</div>
							</div>
						</div>
					</li>
				</ul>
			</li>
			 -->
		</ul>
		<!-- //아코디언 메뉴 -->
	</div>

	<!-- 펼치기/접기 버튼 --> 
	<div class="lnb-fold">
		<button onclick="toggleLeftMenu('${flag}','left','1-1')" class="btn-fold">접기</button>
	</div>
</div>

<script type="text/javascript">	
	$(document).ready(function(){
		setColorPicker("1-1-3", '#4374D9'); //common.js
		setColorPicker("1-1-4", '#9FC93C'); //common.js
	});	
</script>    