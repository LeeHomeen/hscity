<%@ page contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" type="text/css" href="/css/ref/multiselect.css" />	
				
<!-- 분석영역 -->
<div class="sec-tab">
		<div>
			<span class="head">버스타입</span>
			<span class="sel-cst st2 w1">
				<label>
					<select name="selBusType" id="selBusType">
					</select>
				</label>
			</span>
		</div>
	</div>			
	<div class="sec-tab">
		<div class="cont">
			<span class="head">버스노선</span>
			<span class="sel-cst st2 w1">						
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
					 			<ul id="selBus" name="selBus">
					 			</ul>
					 		</dd>
					 	</dl>							 		
					</div>	
										 
				</label>						
			</span>
		</div>
	</div>
<!-- //분석영역 -->
<script type="text/javascript" src="/js/cmm/gis_search.js"> </script>
<script type="text/javascript">
	$(document).ready(function(){		
		var flag = '${flag}';				
		selectBusTypeList("selBusType", flag); //읍면동 기준정보 리스트 가져오기
		
		$('#selBusType').change(function(){
			selectBusList("selBus", flag); //버스라인 리스트 가져오기	common.js			
		});		
	});
</script>