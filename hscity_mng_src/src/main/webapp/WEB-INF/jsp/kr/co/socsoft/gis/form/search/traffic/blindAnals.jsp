<%@ page contentType="text/html;charset=UTF-8" %>   
<link rel="stylesheet" type="text/css" href="/css/ref/multiselect.css" />

<div>
	<!-- 펼치기/접기 버튼 --> 
	<div class="lnb-fold">
		<button onclick="toggleLeftMenu('${flag}','left', '2-1')" class="btn-fold">접기</button>
	</div>
</div>
<script type="text/javascript" src="/js/cmm/gis_search.js"> </script>
<script type="text/javascript">
	$(document).ready(function(){		
		$(".lnb-fold").hide();
		if ($("#gis-lnb").css("marginLeft") == "0px")
			toggleLeftMenu('${flag}', 'left', '2-1');
	});
	//updateMap
</script>