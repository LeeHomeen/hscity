<%@ page contentType="text/html;charset=UTF-8" %>   
<div>
	<h2 class="lnb-tit">분석영역 설정</h2>
	<div class="lnb-cont">					
		<!-- 분석영역 -->
		<div id="divFPopArea" class="box-cdt"></div>
		<!-- //분석영역 -->

		<!-- 기간설정 -->
		<div class="box-cdt">
			<p class="tit">기간 설정<label id='lblPeriod'></label></p>
			<div class="tab3 tab-st2" style="margin-top:10px;">
				<ul class="tabs">
					<li id="liMon1-2" class="active">
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
					<li id="liDay1-2">
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
				<a href="#" class="title-bar" onclick="getTablePeriod('1-2-1')">기간별 분석</a>
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
													<select name="selType1-2-1" id="selType1-2-1">														
														<option value="grid" selected="selected">격자</option>
														<option value="beehive">벌집(Beehive)</option>
														<option value="heat">밀도맵(HeatMap)</option>
													</select>
												</label> 
											</span> 
										</li>													
										<li>													
											<span class="sel-cst st2 w3">
												<label>
													<select name="selColor1-2-1" id="selColor1-2-1">
														<option value="red" selected="selected">빨강(RED)</option>
														<option value="green">녹색(GREEN)</option>
														<option value="blue">파랑(BLUE)</option>
													</select>
												</label> 
											</span> 
										</li>
									</ul>																							
									<div class="btn-wrap">
										<button onclick="fPopAnalsPeriod('1-2-1', '${flag}')" class="btn sr2 st2">분 석</button>
									</div>
								</div>
							</div>
							<!-- //슬라이드 업다운-->
						</div>
					</li>
				</ul>
			</li>
			<li class="treeview"> 
				<a href="#" class="title-bar" onclick="getTablePeriod('1-2-2')">성&#47;연령별 분석</a>
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
													<select name="selType1-2-2" id="selType1-2-2">														
														<option value="grid" selected="selected">격자</option>
														<option value="beehive">벌집(Beehive)</option>
														<option value="heat">밀도맵(HeatMap)</option>
													</select>
												</label> 
											</span> 
										</li>													
										<li>													
											<span class="sel-cst st2 w3">
												<label>
													<select name="selColor1-2-2" id="selColor1-2-2">
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
													<input type="checkbox" name="chkFPopAge1-2-2" id="chkFPopAge1-2-2" class="chckbox">
													<label for="chkFPopAge1-2-2">성별&#47;연령 전체</label>	
												</td>
											</tr>	
											<tr>
												<td>
													<input type="checkbox" name="chkFPopAge1-2-2-10M" id="chkFPopAge1-2-2-10M" class="chckbox chkFPopAge" value="1m">
													<label for="chkFPopAge1-2-2-10M">10대 남성</label>	
												</td>
												<td>
													<input type="checkbox" name="chkFPopAge1-2-2-10F" id="chkFPopAge1-2-2-10F" class="chckbox chkFPopAge" value="1f">
													<label for="chkFPopAge1-2-2-10F">10대 여성</label>
												</td>
											</tr>														
											<tr>
												<td>
													<input type="checkbox" name="chkFPopAge1-2-2-20M" id="chkFPopAge1-2-2-20M" class="chckbox chkFPopAge" value="2m">
													<label for="chkFPopAge1-2-2-20M">20대 남성</label>	
												</td>
												<td>
													<input type="checkbox" name="chkFPopAge1-2-2-20F" id="chkFPopAge1-2-2-20F" class="chckbox chkFPopAge" value="2f">
													<label for="chkFPopAge1-2-2-20F">20대 여성</label>
												</td>
											</tr>												
											<tr>
												<td>
													<input type="checkbox" name="chkFPopAge1-2-2-30M" id="chkFPopAge1-2-2-30M" class="chckbox chkFPopAge" value="3m">
													<label for="chkFPopAge1-2-2-30M">30대 남성</label>	
												</td>
												<td>
													<input type="checkbox" name="chkFPopAge1-2-2-30F" id="chkFPopAge1-2-2-30F" class="chckbox chkFPopAge" value="3f">
													<label for="chkFPopAge1-2-2-30F">30대 여성</label>
												</td>
											</tr>												
											<tr>
												<td>
													<input type="checkbox" name="chkFPopAge1-2-2-40M" id="chkFPopAge1-2-2-40M" class="chckbox chkFPopAge" value="4m">
													<label for="chkFPopAge1-2-2-40M">40대 남성</label>	
												</td>
												<td>
													<input type="checkbox" name="chkFPopAge1-2-2-40F" id="chkFPopAge1-2-2-40F" class="chckbox chkFPopAge" value="4f">
													<label for="chkFPopAge1-2-2-40F">40대 여성</label>
												</td>
											</tr>												
											<tr>
												<td>
													<input type="checkbox" name="chkFPopAge1-2-2-50M" id="chkFPopAge1-2-2-50M" class="chckbox chkFPopAge" value="5m">
													<label for="chkFPopAge1-2-2-50M">50대 남성</label>	
												</td>
												<td>
													<input type="checkbox" name="chkFPopAge1-2-2-50F" id="chkFPopAge1-2-2-50F" class="chckbox chkFPopAge" value="5f">
													<label for="chkFPopAge1-2-2-50F">50대 여성</label>
												</td>
											</tr>												
											<tr>
												<td>
													<input type="checkbox" name="chkFPopAge1-2-2-60M" id="chkFPopAge1-2-2-60M" class="chckbox chkFPopAge" value="6m">
													<label for="chkFPopAge1-2-2-60M">60대 남성</label>	
												</td>
												<td>
													<input type="checkbox" name="chkFPopAge1-2-2-60F" id="chkFPopAge1-2-2-60F" class="chckbox chkFPopAge" value="6f">
													<label for="chkFPopAge1-2-2-60F">60대 여성</label>
												</td>
											</tr>												
											<tr>
												<td>
													<input type="checkbox" name="chkFPopAge1-2-2-70M" id="chkFPopAge1-2-2-70M" class="chckbox chkFPopAge" value="7m">
													<label for="chkFPopAge1-2-2-70M">70대이상 남성</label>	
												</td>
												<td>
													<input type="checkbox" name="chkFPopAge1-2-2-70F" id="chkFPopAge1-2-2-70F" class="chckbox chkFPopAge" value="7f">
													<label for="chkFPopAge1-2-2-70F">70대이상 여성</label>
												</td>
											</tr>
										</tbody>
									</table>
									<div class="btn-wrap">
										<button onclick="fPopAnalsAge('1-2-2', '${flag}')" class="btn sr2 st2">분 석</button>
									</div>
								</div>
							</div>
							<!-- //슬라이드 업다운-->
						</div>
					</li>
				</ul>
			</li>
			<li class="treeview"> 
				<a href="#" class="title-bar" onclick="getTablePeriod('1-2-3')">시간대별 분석</a>
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
													<select name="selType1-2-3" id="selType1-2-3">														
														<option value="grid" selected="selected">격자</option>
														<option value="beehive">벌집(Beehive)</option>
														<option value="heat">밀도맵(HeatMap)</option>
													</select>
												</label> 
											</span> 
										</li>													
										<li>													
											<span class="sel-cst st2 w3">
												<label>
													<select name="selColor1-2-3" id="selColor1-2-3">
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
							<!-- //슬라이드 업다운-->
						</div>
					</li>
					<li>
						<div class="box-cdt">
							<p class="tit">시간대</p>
							<!--슬라이드 업다운-->
							<button class="btn-drop pst1" title="접기&#47;열기">접기&#47;열기</button>
							<div class="drop-slide">
								<div class="cont">
									<table class="tbl st2" >
										<caption class="hide">시간대 선택</caption>
										<colgroup>
											<col style="width:25%"/>
											<col style="width:25%"/>
											<col style="width:25%"/>
											<col style="width:25%"/>
										</colgroup>
										<tbody>
											<tr>
												<td colspan="4">
													<input type="checkbox" name="chkTimeAll1-2-3" id="chkTimeAll1-2-3" class="chckbox">
													<label for="chkTimeAll1-2-3">전체</label>	
												</td>
											</tr>														
											<tr>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-00" id="chkFPopTime1-2-3-00" class="chckbox chkFPopTime" value="00">
													<label for="chkFPopTime1-2-3-00">00시</label>	
												</td>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-06" id="chkFPopTime1-2-3-06" class="chckbox chkFPopTime" value="06">
													<label for="chkFPopTime1-2-3-06">06시</label>
												</td>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-12" id="chkFPopTime1-2-3-12" class="chckbox chkFPopTime" value="12">
													<label for="chkFPopTime1-2-3-12">12시</label>	
												</td>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-18" id="chkFPopTime1-2-3-18" class="chckbox chkFPopTime" value="18">
													<label for="chkFPopTime1-2-3-18">18시</label>
												</td>
											</tr>														
											<tr>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-01" id="chkFPopTime1-2-3-01" class="chckbox chkFPopTime" value="01">
													<label for="chkFPopTime1-2-3-01">01시</label>	
												</td>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-07" id="chkFPopTime1-2-3-07" class="chckbox chkFPopTime" value="07">
													<label for="chkFPopTime1-2-3-07">07시</label>
												</td>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-13" id="chkFPopTime1-2-3-13" class="chckbox chkFPopTime" value="13">
													<label for="chkFPopTime1-2-3-13">13시</label>	
												</td>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-19" id="chkFPopTime1-2-3-19" class="chckbox chkFPopTime" value="19">
													<label for="chkFPopTime1-2-3-19">19시</label>
												</td>
											</tr>															
											<tr>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-02" id="chkFPopTime1-2-3-02" class="chckbox chkFPopTime" value="02">
													<label for="chkFPopTime1-2-3-02">02시</label>	
												</td>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-08" id="chkFPopTime1-2-3-08" class=" chckbox chkFPopTime" value="08">
													<label for="chkFPopTime1-2-3-08">08시</label>
												</td>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-14" id="chkFPopTime1-2-3-14" class="chckbox chkFPopTime" value="14">
													<label for="chkFPopTime1-2-3-14">14시</label>	
												</td>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-20" id="chkFPopTime1-2-3-20" class="chckbox chkFPopTime" value="20">
													<label for="chkFPopTime1-2-3-20">20시</label>
												</td>
											</tr>															
											<tr>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-03" id="chkFPopTime1-2-3-03" class="chckbox chkFPopTime" value="03">
													<label for="chkFPopTime1-2-3-03">03시</label>	
												</td>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-09" id="chkFPopTime1-2-3-09" class="chckbox chkFPopTime" value="09">
													<label for="chkFPopTime1-2-3-09">09시</label>
												</td>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-15" id="chkFPopTime1-2-3-15" class="chckbox chkFPopTime" value="15">
													<label for="chkFPopTime1-2-3-15">15시</label>	
												</td>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-21" id="chkFPopTime1-2-3-21" class="chckbox chkFPopTime" value="21">
													<label for="chkFPopTime1-2-3-21">21시</label>
												</td>
											</tr>															
											<tr>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-04" id="chkFPopTime1-2-3-04" class="chckbox chkFPopTime" value="04">
													<label for="chkFPopTime1-2-3-04">04시</label>	
												</td>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-10" id="chkFPopTime1-2-3-10" class="chckbox chkFPopTime" value="10">
													<label for="chkFPopTime1-2-3-10">10시</label>
												</td>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-16" id="chkFPopTime1-2-3-16" class="chckbox chkFPopTime" value="16">
													<label for="chkFPopTime1-2-3-16">16시</label>	
												</td>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-22" id="chkFPopTime1-2-3-22" class="chckbox chkFPopTime" value="22">
													<label for="chkFPopTime1-2-3-22">22시</label>
												</td>
											</tr>															
											<tr>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-05" id="chkFPopTime1-2-3-05" class="chckbox chkFPopTime" value="05">
													<label for="chkFPopTime1-2-3-05">05시</label>	
												</td>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-11" id="chkFPopTime1-2-3-11" class="chckbox chkFPopTime" value="11">
													<label for="chkFPopTime1-2-3-11">11시</label>
												</td>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-17" id="chkFPopTime1-2-3-17" class="chckbox chkFPopTime" value="17">
													<label for="chkFPopTime1-2-3-17">17시</label>	
												</td>
												<td>
													<input type="checkbox" name="chkFPopTime1-2-3-23" id="chkFPopTime1-2-3-23" class="chckbox chkFPopTime" value="23">
													<label for="chkFPopTime1-2-3-23">23시</label>
												</td>
											</tr>			
										</tbody>
									</table>
									<div class="btn-wrap">
										<button onclick="fPopAnalsTime('1-2-3', '${flag}')" class="btn sr2 st2">분 석</button>
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
						??
					</li>
				</ul>
			</li>
			 -->
		</ul>
		<!-- //아코디언 메뉴 -->
	</div>

	<!-- 펼치기/접기 버튼 --> 
	<div class="lnb-fold">
		<button onclick="toggleLeftMenu('${flag}','left','1-2')" class="btn-fold">접기</button>
	</div>
</div> 
<script type="text/javascript">
	$(document).ready(function(){
		//성연령대 전체 체크
		$('#chkFPopAge1-2-2').change(function(){
			var obj = $('#chkFPopAge1-2-2')[0];
			if(obj.checked){
				$('.chkFPopAge').each(function(index, item){
					item.checked = true;
				});
			}else{
				$('.chkFPopAge').each(function(index, item){
					item.checked = false;
				});
			}
		});
		//개별성연령대별 체크시 (전체 체크)
		$('.chkFPopAge').change(function(){
			var chkAll = true;
			$('.chkFPopAge').each(function(index, item){
				if(!item.checked)
					chkAll = false;
			});
			$('#chkFPopAge1-2-2')[0].checked = chkAll;	
			
		});
		
		//시간대 전체 체크
		$('#chkTimeAll1-2-3').change(function(){
			var obj = $('#chkTimeAll1-2-3')[0];
			if(obj.checked){
				$('.chkFPopTime').each(function(index, item){
					item.checked = true;
				});
			}else{
				$('.chkFPopTime').each(function(index, item){
					item.checked = false;
				});
			}
		});		
		//개별시간대 체크시 (전체 체크)
		$('.chkFPopTime').change(function(){
			var chkAll = true;
			$('.chkFPopTime').each(function(index, item){
				if(!item.checked)
					chkAll = false;
			});
			$('#chkTimeAll1-2-3')[0].checked = chkAll;	
			
		});
	});
</script>
