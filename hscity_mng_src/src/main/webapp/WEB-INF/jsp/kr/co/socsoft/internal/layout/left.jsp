<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
<!--controller에서 만든 동적 메뉴 클릭 이벤트//-->
$(document).ready(function(){
	
	 var $submitForm = $('#submitForm');

	$('#treemenu ul li a').click(function(evt){
		var menuGbn = evt.target.parentElement.id.split('/')[0];
		var menuCd = evt.target.parentElement.id.split('/')[1];

		$('#menuCd').val(menuCd);
	//	$('#gbn').val(menuGbn);
		
		$.ajax({
	        url: "/internal/setBizMenuLog.do",
	        type: "POST",
	        dataType: 'json',
	        data: JSON.stringify({menuCd: menuCd}),
	        headers: {'Content-Type': 'application/json; charset=UTF-8'},
	        success: function (response) {
	        },
	        error: function (request, status, error) {
	            console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
	        }
	    });
		if (menuGbn == 'bi' ){
			getTicketAndUsualBIUrl(evt.target.id);
			
		} else if(menuGbn =='bi_pub'){
			var strUrl = evt.target.id;
			if (strUrl.match('embed')) {
				if (strUrl.match('embed=y')) {
					strUrl = strUrl.replace('embed=y', 'embed=true');
					strUrl += '&:showVizHome=no';
				} else {
					strUrl += '&:showVizHome=no';
				}	
			} else {
				strUrl += '&:embed=true&:showVizHome=no';
			}
			
			//$('#url').val(evt.target.id);
			$('#url').val(strUrl);
			$submitForm.attr('action', '<c:url value="/internal/subPage.do"/>');
	        $submitForm.submit();
	        
		}  else if(menuGbn =='nor'){
			$submitForm.attr('action', '<c:url value="/internal/subPage.do"/>');
	        $submitForm.submit();
	        
		}  else if(menuGbn =='gis'){
			if (evt.target.id == "http://105.24.1.34/hsgis/policyMap/sso.do") {//공간정보시스템 시각화 페이지
				if ("${sessionScope.LoginVO.ssoUserYn}" == "n") {
					alert("새올사용자만 접근 가능한 페이지입니다.");
				} else {
					window.open(evt.target.id + "?userId=" + "${sessionScope.LoginVO.userId}", "newWin");				
				}
			} else {
				window.open(evt.target.id, "newWin");
			}
			//window.open(evt.target.id, "newWin");
			
		}  else if (menuGbn =='bbs'){
			$('#url').val(evt.target.id);
			$submitForm.attr('action', '<c:url value="/internal/subPage.do"/>');
	        $submitForm.submit();
		}
		
/* 		if (menuGbn == 'bi'){
			getTicketAndUsualBIUrl(evt.target.id);
		}  else if(menuGbn =='normal'){
			$submitForm.attr('action', '<c:url value="/internal/subPage.do"/>');
	        $submitForm.submit();
			
		}  else if(menuGbn =='gis'){
			window.open(evt.target.id);
		}
	 */
	});
	
	function getTicketAndUsualBIUrl(menuUrl){
		$.ajax({
		     url: "/tableau/goTableauTicket.do",
		     type : "GET",
		     dataType: 'json',
		     headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		     success: function(response) {
		      console.log(response);
		      
		      var ticket = "trusted/" + response.ticket + "/";
		      var biServerUrl = response.TableauUrl;
		      var biMenuUrl = menuUrl;
		      /*
		       http://192.168.1.144/agdsidsf?sdfsdsdf=sdfsd&sdfsdfsd   => http://192.168.1.144/trusted/티켓값/agdsidsf?sdfsdsdf=sdfsd&sdfsdfsd
		       */
		       result = biMenuUrl.replace(biServerUrl, biServerUrl + ticket+"/");
		      //console.log(url);
		    //  $('#preview').attr('src', url);
		   //   $(".pop-box1").show();
		   
				$('#url').val(result);

				$submitForm.attr('action', '<c:url value="/internal/subPage.do"/>');
		        $submitForm.submit();
		     },
		        error : function(request, status, error) {
		         console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		     }
		 });
		
		
	}
	
});
</script>
		<form id='submitForm' name='submitForm' method="post">
            <input type="hidden" id="url" name="url" />
             <input type="hidden" id="gbn" name="gbn" />
            <input type="hidden" id="menuCd" name="menuCd" />
            <input type="text" style="display: none" title="no enter submit!"/>
        </form>
		<!-- 좌메뉴 //-->
		<div class="cateWrap">
			<a class="favoSelect poplink" href="#pop-favorite">즐겨찾기 항목 선택</a>
			<div id="treemenu">
			${leftMenu}
			</div>
		</div>
		<!--// 좌메뉴 -->
		