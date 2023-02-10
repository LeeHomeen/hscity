<%@ page contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" type="text/css" href="/css/ref/multiselect.css" />	
				
<!-- 분석영역 -->
<div class="tab2 tab-st2">							
	<ul class="tabs">
		<li id="liChkArea" class="active">
			<a href="#" class="btn-tab">행정동 경계</a>
			<div class="sec-tab">
				<div class="cont">
					<span class="head">기준</span>
					<span class="sel-cst st2 w1">
						<label>
							<select name="selStd" id="selStd">
							</select>
						</label>
					</span>
				</div>				
				<div class="cont">
					<span class="head">읍/면/동</span>
					<span class="sel-cst1 st2 w1">						
						<label>							
							 <div class="dropdownBox" id="multiCheckedbox" style="border: none;">
							 	<dl>
							 		<dt>
							 			<span class="multiCheckValues"></span>
							 			<span class="dropBtn">
							 				<img alt="" src="/images/cmm/sel-arrow.gif">
							 			</span>
							 		</dt>
							 		<dd>
							 			<ul id="selDong" name="selDong">
							 			</ul>
							 		</dd>
							 	</dl>							 		
							</div>	
												 
						</label>						
					</span>
										
					<p class="lment">※인구분석은 2월이전 조회시 개편전 행정동</p>
					<p class="lment">&nbsp;&nbsp;&nbsp;&nbsp;사용 (행정동 기준)</p>
				</div>
			</div>
		</li>
		<li id="liChkUser">
			<a href="#" class="btn-tab">사용자 지정영역</a>
			<div class="sec-tab">
				<div class="cont">
					<ul>
						<li>												
							<input type="radio" name="rdUsrRadius" class="" value="polygon" checked="checked"/>
							<label for="bb1">다각형 영역지정</label> 
						</li>
						<li>
							<span class="head2">
								<input type="radio" name="rdUsrRadius" value="point" class=""/>
								<label for="bb2">반경 영역지정</label>  
							</span>													
							<span class="sel-cst st2 w2">
								<label>
									<select name="selUsrRadius" id="selUsrRadius">
										<option value="300" selected="selected">300m</option>
										<option value="500">500m</option>
										<option value="1000">1km</option>
										<option value="2000">2km</option>										
									</select>
								</label> 
							</span>
						</li>
						<li>
							<input type="radio" name="rdUsrRadius" value="layer" class=""/>
							<label for="bb3">														
								<span class="sel-cst st2 w33">
									<label>
										<select name="selBookmark" id="selBookmark">
											<option value="" selected="selected">-- 지정영역 선택 --</option>
										</select>
									</label> 
								</span>
								<span>
									<a href="#" onclick="clickUsrArea('${flag}', 'del')">
									<img style="width:13px; height:13px" src='/images/cmm/ico-bin.jpg'>
									</a>
								</span>
							</label> 
						</li>
						<li id="liUsrArea" style="text-align: center;" >
							<input type="button" onclick="clickUsrArea('${flag}', 'create')" class="btn sr1 st1" value="영역지정"/>
							<input type="button" onclick="clickUsrArea('${flag}', 'clear')" class="btn sr1 st1" value="초기화"/>
							<!-- 
							&nbsp;&nbsp;
							<input type="button" onclick="clickUsrArea('${flag}', 'del')" class="btn sr1 st1" value="영역삭제"/>
							 -->
						</li>
					</ul>
				</div>
			</div>
		</li>
	</ul>
</div>
<!-- //분석영역 -->
<script type="text/javascript" src="/js/cmm/gis_search.js"> </script>
<script type="text/javascript">
	$(document).ready(function(){		
		var flag = '${flag}';		
		selectStdList("selStd", flag); //읍면동 기준정보 리스트 가져오기		
		selectBookmarkList("selBookmark");//common.js
		
		$('#selStd').change(function(){
			selectDongList("selDong", flag); //읍면동 리스트 가져오기	common.js			
		});		
	});
</script>