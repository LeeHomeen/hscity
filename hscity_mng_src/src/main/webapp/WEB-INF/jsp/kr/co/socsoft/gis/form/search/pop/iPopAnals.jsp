<%@ page contentType="text/html;charset=UTF-8" %>   
<div>
	<h2 class="lnb-tit">분석영역 설정</h2>
	<div class="lnb-cont">					
		<!-- 분석영역 -->
		<div id="divIPopArea" class="box-cdt"></div>
		<!-- //분석영역 -->
	
		<!-- 기간설정 -->
		<div class="box-cdt">
			<p class="tit">기간 설정<label id='lblPeriod'></label></p>
			<div class="tab3 tab-st2" style="margin-top:10px;">
				<ul class="tabs">
					<li id="liMon1-3" class="active">
						<a href="#" class="btn-tab">기준년월</a>
						<div class="sec-tab">
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
					</li>
					<li id="liDay1-3">
						<a href="#" class="btn-tab">기준일자</a>
						<div class="sec-tab">
							<div class="cont">
								<ul>
									<li>
										<span class="head">기준일자 시작</span>
										<label>
											<input id="txtDayStart" type="text" class="ip sr1 st2 w1 ico-cld" value="2017-12-01">
										</label>													
									</li>
									<li>								
										<span class="head">기준일자 종료</span>
										<label>
											<input id="txtDayEnd" type="text" class="ip sr1 st2 w1 ico-cld" value="2018-01-10">
										</label>
									</li>
								</ul>		
								<p class="ment">※ 기준일자는 데이터가 입력된 시점입니다??</p>
							</div>
						</div>
					</li>
				</ul>
			</div>	
		</div>
		<!-- //기간설정 -->
	
		<!-- 아코디언 메뉴 -->
		<ul class="sidebar-menu">  
			<li class="treeview active"> 
				<a href="#" class="title-bar" onclick="getTablePeriod('1-3-1')">시&#47;도별 유입인구</a>
				<ul class="treeview-menu">
					<li>									
						<div class="box-cdt">
							<p class="tit">관내&#47;외 설정</p>
							<!--슬라이드 업다운-->
							<button class="btn-drop pst1" title="접기&#47;열기">접기&#47;열기</button>
							<div class="drop-slide">
								<div class="cont">													
									<span class="sel-cst st2 w3">
										<label>
											<select name="selRange1-3-1" id="selRange1-3-1">
												<option value="all" selected="selected">전체</option>
												<option value="in">관내(화성시 내)</option>
												<option value="out">관외(화성시 이외지역)</option>
											</select>
										</label> 
									</span>
									
									<span id="colpck1-3-1" class="colorSelector">
										<div style="background-color: #4374D9"></div>
									</span>
									
									<div class="btn-wrap">
										<button onclick="setIPopAnals('/gis/ipop/iPopSidoList.do', '1-3-1', '${flag}')" class="btn sr2 st2">분 석</button>
									</div>
								</div>
							</div>
							<!-- //슬라이드 업다운-->
						</div>
					</li>
				</ul>
			</li>
			<li class="treeview"> 
				<a href="#" class="title-bar" onclick="getTablePeriod('1-3-2')">시&#47;군&#47;구별 유입인구</a>
				<ul class="treeview-menu">								
					<li>									
						<div class="box-cdt">
							<p class="tit">관내&#47;외 설정</p>
							<!--슬라이드 업다운-->
							<button class="btn-drop pst1" title="접기&#47;열기">접기&#47;열기</button>
							<div class="drop-slide">
								<div class="cont">													
									<span class="sel-cst st2 w3">
										<label>
											<select name="selRange1-3-2" id="selRange1-3-2">
												<option value="all" selected="selected">전체</option>
												<option value="in">관내(화성시 내)</option>
												<option value="out">관외(화성시 이외지역)</option>
											</select>
										</label> 
									</span>
									
									<span id="colpck1-3-2" class="colorSelector">
										<div style="background-color: #9FC93C"></div>
									</span>
									
									<div class="btn-wrap">
										<button onclick="setIPopAnals('/gis/ipop/iPopSigunList.do', '1-3-2', '${flag}')" class="btn sr2 st2">분 석</button>
									</div>
								</div>
							</div>
							<!-- //슬라이드 업다운-->
						</div>
					</li>
				</ul>
			</li>
			<li class="treeview"> 
				<a href="#" class="title-bar" onclick="getTablePeriod('1-3-3')">성&#47;연령별 유입인구</a>
				<ul class="treeview-menu">
					<!-- 
					<li>						
						<div class="box-cdt">
							<p class="tit">분석&#47; 시각화</p>							
							<button class="btn-drop pst1" title="접기&#47;열기">접기&#47;열기</button>
							<div class="drop-slide">
								<div class="cont">
									<ul>
										<li>													
											<span class="sel-cst st2 w3">
												<label>
													<select name="selType1-3-3" id="selType1-3-3">
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
													<select name="selColor1-3-3" id="selColor1-3-3">
														<option value="red" selected="selected">빨강(RED)</option>
														<option value="green">녹색(GREEN)</option>
														<option value="blue">파랑(BLUE)</option>
													</select>
												</label> 
											</span> 
										</li>
									</ul>
								</div>
							</div>							
						</div>						 
					</li>
					-->
					<li>									
						<div class="box-cdt">
							<p class="tit">관내&#47;외 설정</p>
							<!--슬라이드 업다운-->
							<button class="btn-drop pst1" title="접기&#47;열기">접기&#47;열기</button>
							<div class="drop-slide">
								<div class="cont">													
									<span class="sel-cst st2 w3">
										<label>
											<select name="selRange1-3-3" id="selRange1-3-3">
												<option value="all" selected="selected">전체</option>
												<option value="in">관내(화성시 내)</option>
												<option value="out">관외(화성시 이외지역)</option>
											</select>
										</label> 
									</span>
									
									<span id="colpck1-3-3" class="colorSelector">
										<div style="background-color: #BA942B"></div>
									</span>									
								</div>
							</div>
							<!-- //슬라이드 업다운-->
						</div>
					</li>	
					<li>
						<div class="box-cdt">
							<p class="tit">성&#47;연령별 분석</p>
							<!--슬라이드 업다운-->
							<button class="btn-drop pst1" title="접기&#47;열기">접기&#47;열기</button>
							<div class="drop-slide">		
								<div class="cont">
									<table class="tbl st2" >
										<caption class="hide">성,연령 선택</caption>
										<colgroup>
											<col style="width:50%"/>
											<col style="width:50%"/>
										</colgroup>
										<tbody>
											<tr>
												<td colspan="2">
													<input type="checkbox" name="chkIPopAge1-3-3" id="chkIPopAge1-3-3" class="chckbox">
													<label for="chkIPopAge1-3-3">성별&#47;연령 전체</label>	
												</td>
											</tr>	
											<tr>
												<td>
													<input type="checkbox" name="chkIPopAge1-3-3-10M" id="chkIPopAge1-3-3-10M" class="chckbox chkIPopAge" value="1m">
													<label for="chkIPopAge1-3-3-10M">10대 남성</label>	
												</td>
												<td>
													<input type="checkbox" name="chkIPopAge1-3-3-10F" id="chkIPopAge1-3-3-10F" class="chckbox chkIPopAge" value="1f">
													<label for="chkIPopAge1-3-3-10F">10대 여성</label>
												</td>
											</tr>														
											<tr>
												<td>
													<input type="checkbox" name="chkIPopAge1-3-3-20M" id="chkIPopAge1-3-3-20M" class="chckbox chkIPopAge" value="2m">
													<label for="chkIPopAge1-3-3-20M">20대 남성</label>	
												</td>
												<td>
													<input type="checkbox" name="chkIPopAge1-3-3-20F" id="chkIPopAge1-3-3-20F" class="chckbox chkIPopAge" value="2f">
													<label for="chkIPopAge1-3-3-20F">20대 여성</label>
												</td>
											</tr>												
											<tr>
												<td>
													<input type="checkbox" name="chkIPopAge1-3-3-30M" id="chkIPopAge1-3-3-30M" class="chckbox chkIPopAge" value="3m">
													<label for="chkIPopAge1-3-3-30M">30대 남성</label>	
												</td>
												<td>
													<input type="checkbox" name="chkIPopAge1-3-3-30F" id="chkIPopAge1-3-3-30F" class="chckbox chkIPopAge" value="3f">
													<label for="chkIPopAge1-3-3-30F">30대 여성</label>
												</td>
											</tr>												
											<tr>
												<td>
													<input type="checkbox" name="chkIPopAge1-3-3-40M" id="chkIPopAge1-3-3-40M" class="chckbox chkIPopAge" value="4m">
													<label for="chkIPopAge1-3-3-40M">40대 남성</label>	
												</td>
												<td>
													<input type="checkbox" name="chkIPopAge1-3-3-40F" id="chkIPopAge1-3-3-40F" class="chckbox chkIPopAge" value="4f">
													<label for="chkIPopAge1-3-3-40F">40대 여성</label>
												</td>
											</tr>												
											<tr>
												<td>
													<input type="checkbox" name="chkIPopAge1-3-3-50M" id="chkIPopAge1-3-3-50M" class="chckbox chkIPopAge" value="5m">
													<label for="chkIPopAge1-3-3-50M">50대 남성</label>	
												</td>
												<td>
													<input type="checkbox" name="chkIPopAge1-3-3-50F" id="chkIPopAge1-3-3-50F" class="chckbox chkIPopAge" value="5f">
													<label for="chkIPopAge1-3-3-50F">50대 여성</label>
												</td>
											</tr>												
											<tr>
												<td>
													<input type="checkbox" name="chkIPopAge1-3-3-60M" id="chkIPopAge1-3-3-60M" class="chckbox chkIPopAge" value="6m">
													<label for="chkIPopAge1-3-3-60M">60대 남성</label>	
												</td>
												<td>
													<input type="checkbox" name="chkIPopAge1-3-3-60F" id="chkIPopAge1-3-3-60F" class="chckbox chkIPopAge" value="6f">
													<label for="chkIPopAge1-3-3-60F">60대 여성</label>
												</td>
											</tr>												
											<tr>
												<td>
													<input type="checkbox" name="chkIPopAge1-3-3-70M" id="chkIPopAge1-3-3-70M" class="chckbox chkIPopAge" value="7m">
													<label for="chkIPopAge1-3-3-70M">70대이상 남성</label>	
												</td>
												<td>
													<input type="checkbox" name="chkIPopAge1-3-3-70F" id="chkIPopAge1-3-3-70F" class="chckbox chkIPopAge" value="7f">
													<label for="chkIPopAge1-3-3-70F">70대이상 여성</label>
												</td>
											</tr>
										</tbody>
									</table>
									<div class="btn-wrap">
										<button onclick="setIPopAgeAnals('/gis/ipop/iPopGAList.do', '1-3-3', '${flag}')" class="btn sr2 st2">분 석</button>
									</div>
								</div>
							</div>
							<!-- //슬라이드 업다운-->
						</div>
					</li>
				</ul>
			</li>
		</ul>
		<!-- //아코디언 메뉴 -->
	</div>
	
	<!-- 펼치기/접기 버튼 --> 
	<div class="lnb-fold">
		<button onclick="toggleLeftMenu('${flag}','left','1-3')" class="btn-fold">접기</button>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		//성연령대 전체 체크
		$('#chkIPopAge1-3-3').change(function(){
			var obj = $('#chkIPopAge1-3-3')[0];
			if(obj.checked){
				$('.chkIPopAge').each(function(index, item){
					item.checked = true;
				});
			}else{
				$('.chkIPopAge').each(function(index, item){
					item.checked = false;
				});
			}
		});
		//개별성연령대별 체크시 (전체 체크)
		$('.chkIPopAge').change(function(){
			var chkAll = true;
			$('.chkIPopAge').each(function(index, item){
				if(!item.checked)
					chkAll = false;
			});
			$('#chkIPopAge1-3-3')[0].checked = chkAll;	
			
		});
		
		setColorPicker("1-3-1", '#4374D9'); //common.js
		setColorPicker("1-3-2", '#9FC93C'); //common.js
		setColorPicker("1-3-3", '#BA942B'); //common.js
	});
</script>