<%@ page contentType="text/html;charset=UTF-8" %>   
<div>
	<h2 class="lnb-tit">분석영역 설정</h2>
	<div class="lnb-cont">					
		<div class="box-cdt">
			<p class="tit">분석기준</p>
			<!--슬라이드 업다운-->
			<button class="btn-drop pst1" title="접기&#47;열기">접기&#47;열기</button>
			<div class="drop-slide">
				<div class="cont">
					<ul>
						<li>												
							<input type="radio" name="rdPassType" class="" value="day" checked="checked"/>
							<label for="bb1">주말/주중</label>
							&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="rdPassType" value="peak" class=""/>
							<label for="bb2">주중첨두/비첨두</label> 
						</li>						
					</ul>
				</div>
				<div class="cont">												
					<!-- 여기서 조회조건 -->					
					<div>
						<span class="head">구분</span>
						<span class="sel-cst st2 w1">
							<label>
								<select name="selPassGubun" id="selPassGubun">
								</select>
							</label>
						</span>
					</div>
				</div>
			</div>
			
			<div class="drop-slide">
				<div class="cont">												
					<!-- 여기서 조회조건 -->
					<div>
						<span class="head">정류장</span>
						<span class="sel-cst st2 w1">
							<label>
								<select name="selPassSta" id="selPassSta">
								</select>
							</label>
						</span>
					</div>
				</div>
			</div>
			
			<!-- //슬라이드 업다운-->
			<div class="btn-wrap">
				<button onclick="passAnals('2-7-1', '${flag}')" class="btn sr2 st2">분석결과 조회</button>
			</div>
		</div>
	</div>

	<!-- 펼치기/접기 버튼 --> 
	<div class="lnb-fold">
		<button onclick="toggleLeftMenu('${flag}','left', '2-7')" class="btn-fold">접기</button>
	</div>	
</div>
<script type="text/javascript" src="/js/cmm/gis_search.js"> </script>
<script type="text/javascript">
	/**
	* 기본 컨트롤 구성
	*/
	$(document).ready(function(){
		//라디오버튼 체인지
		$("input:radio[name='rdPassType']").change(function(){
			$('#selPassGubun').empty();
			$('#selPassSta').empty();
			
			var type = $("input:radio[name='rdPassType']:checked").val();			
			if(type == 'day'){
				$('#selPassGubun').append("<option value='주중'>주중</option>");	
				$('#selPassGubun').append("<option value='토요일'>토요일</option>");
				$('#selPassGubun').append("<option value='일요일'>일요일</option>");
			}else if(type == 'peak'){
				$('#selPassGubun').append("<option value='아침첨두시간'>아침첨두시간</option>");	
				$('#selPassGubun').append("<option value='저녁첨두시간'>저녁첨두시간</option>");
				$('#selPassGubun').append("<option value='비첨두시간'>비첨두시간</option>");
			}
			$('#selPassGubun').trigger('change');
		});	
		
		//구분 변경시 정류장 변경
		$('#selPassGubun').change(function(){
			$('#selPassSta').empty();
			$.ajax({
				type:"POST",
				url:"/gis/traffic/passStaList.do",
				dataType:'json',
				data: {ptype:$('#selPassGubun').val()},
				async: true,
				success:function(data){
					if(data != undefined && data != null && data != ''){
						if(data.result.length > 0){
							var obj = data.result;
							for(var i=0; i<obj.length; i++){
								$('#selPassSta').append("<option value='"+obj[i].sid+"'>"+obj[i].snm+"</option>");			
							}
						}
					}
				},
				error:function(err){
					openInfoPopup(true,"정류장조회 도중 에러가 발생하였습니다.");
				}
			});	
		});
		
		$("input:radio[name='rdPassType']").trigger('change');
	});
</script>