<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/admin/common/sessionCheck.jsp"%>
<script type="text/javascript" src="<c:url value="/js/lib/etc/jquery.xdomainajax.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/lib/etc/xml2json.js"/>"></script>
<style>
/* index.jsp 에서만 적용되어야함 */
	.stitle {
		width: 94%;
	}
</style>
<body>
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/layout/header.jsp" %>
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/layout/right.jsp" %>
<div class="AllWrap">
  <script type="text/javascript">
  var qMenuCode = '${menuCd}';
  var qUrl = '${url}';
  var qGbn = '${gbn}';
 
  $(document).ready(function(){
	  //긴급알림참 팝업용(mainSubPage.jsp)
	  //$('.pop-box3').show();
	  if(qGbn != ''){
		  pageMove(qMenuCode,qGbn,qUrl);
	  }
	  $('.contentWrap .stitle i.favorite').on('click',function(){
			$.ajax({
			    url: "/internal/setFavPage.do",
			    type : "POST",
			    dataType: 'json',
			    data :JSON.stringify({menuCd : qMenuCode}), 
			    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
			    success: function(response) {
				   alert(response);
			    },
		        error : function(request, status, error) { 
		          	if(request.status == 200){
						   alert(request.responseText);
		       	 	}
		        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		   	 	}
			});
		})
	  
/*         $('#treemenu h3').on('click', function () {
            $.ajax({
                url: "/login/setMenuId.do",
                type: "POST",
                dataType: 'json',
                data: JSON.stringify({menuId: $(this)[0].id}),
                headers: {'Content-Type': 'application/json; charset=UTF-8'},
                success: function (response) {
                },
                error: function (request, status, error) {
                    console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
                }
            });
        }); */
        

        // 미세먼지
        /*var serviceKey = 'jjiqw14CjDkknmhdIGXKJwGQUCYcaUQtOtmWrcPh5Xx5CV5IK5%2FDPkfzUbnZXEZzeB%2B6PClEKr5qaXVk4zUxog%3D%3D';
        $.ajax({
            url: [
                "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?serviceKey=" + serviceKey
                ,"numOfRows=10"
                ,"pageSize=10"
                ,"pageNo=4"
                ,"startPage=1"
                ,"sidoName=" + encodeURIComponent("경기")
                ,"searchCondition=DAILY"
            ].join('&'),
            dataType: "xml",
            type: 'GET',
            success: function(res) {
                var myXML = CM.stringToXMLDom(res.responseText);
                var JSONConvertedXML = $.xml2json(myXML);
                console.log(JSONConvertedXML);
            }
        });*/


	// 파일 다운로드
	$(document).on("click",".download-file",function(){  
	        var menuCd = $(this).data('menuCd');
	        var seq = $(this).data('seq');

	        CM.postFileDownload('<c:url value="/menu/downloadFile.do"/>', {
	        	categoryType: 'biz',// 'biz', 'pub'
	            menuCd: menuCd,
	            seq: seq
	        });
	    });
        
	$('#treemenu ul li a').click(function(evt){
	  	 //초기화 
   	 initManuInfo();
		var menuGbn = evt.target.parentElement.id.split('/')[0];
		var menuCd = evt.target.parentElement.id.split('/')[1];

		pageMove(menuCd,menuGbn,evt.target.id);
	});
	
	function pageMove(menu,gbn,url){
		/* 메뉴이동시 전역변수 셋팅 */
		qMenuCode = menu;
	    	qUrl = url;
	    	qGbn = gbn;
	    	$('#menuCd').val(menu);
	    	
	  	     if(qGbn == 'bbs'){
	  	    	 $('.favorite').hide();
	  	     }else{
	  	    	 $('.favorite').show();
	  	     }
	  	     
	    	
		$.ajax({
	        url: "/internal/setBizMenuLog.do",
	        type: "POST",
	        dataType: 'json',
	        data: JSON.stringify({menuCd: menu}),
	        headers: {'Content-Type': 'application/json; charset=UTF-8'},
	        success: function (response) {
	        	//console.log(response);
	        },
	        error: function (request, status, error) {
	        }
	    });
		
		if (gbn == 'bi' ){
			getMenuInfomation(menu);
			getTicketAndUsualBIUrl(url);
		} else if(gbn =='bi_pub'){
			var strUrl = url;
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
			
			getMenuInfomation(menu);
			$('#urlDiv iframe').attr('src', strUrl);
			$('#urlDiv').show();
		}  else if (gbn =='bbs'){
			 //$('#urlDiv iframe').attr('src', url);
			 //$('#urlDiv').show();
			getMenuInfomation(menu);
			$('#urlDiv iframe').attr('src', url);
			$('#urlDiv').show();
		}else if(gbn =='nor'){
			getMenuInfomation(menu);
		}  else if(gbn =='gis'){
			if (url == "http://105.24.1.34/hsgis/policyMap/sso.do") {//공간정보시스템 시각화 페이지
				if ("${sessionScope.LoginVO.ssoUserYn}" == "n") {
					alert("새올사용자만 접근 가능한 페이지입니다.");
				} else {
					window.open(url + "?userId=" + "${sessionScope.LoginVO.userId}", "newWin");				
				}
			} else {
				window.open(url, "newWin");
			}
		}
	}
	
	function initManuInfo(){
		 $('tr#contentsTd').hide();
		 $('#contents').html();
		 $('#attachFile').hide();
		 $('#fileTd').html();
	     $('#urlDiv').hide();
	     
	  
	    /*  if($('.favorite').hasClass('on')){
	   		 $('.favorite').removeClass('on');
	     } */
	}
		
	function getMenuInfomation(menuCd){
		$.ajax({
		     url: "/internal/getMenuInfomation.do",
		     type : "POST",
		     dataType: 'json',
		     data: JSON.stringify({menuCd: menuCd}),
		     headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		     success: function(response) {
		    	 
		    	 if(response.menuVo.contents != null){
			    	 if(response.menuVo.contents != '<p>&nbsp;</p>'){
			    		 $('tr#contentsTd').show();
			    		 $('#contents').html(response.menuVo.contents);
			    	 }
		    	 }
		    	 if(response.attachFile.length > 0){
		    		 $('#attachFile').show();
		    		 var html = '';
		    	 	 for(var i = 0; i < response.attachFile.length; i++){
		    	 		html += '<a href="#" class="download-file" data-menu-cd="' + response.attachFile[i].menuCd + '" data-seq="' + response.attachFile[i].seq + '" onclick="return false;">' + response.attachFile[i].fileNm + '</a>';

		    	 	 }
		    	 	 $('#fileTd').html(html);
		    	 }
		    	 console.log(response);
		    	$('#menuNm').html(response.menuVo.menuNm);
			   if(response.menuVo.favYn == 0){
				  $('.favorite').removeClass('on');
			   }else if (response.menuVo.favYn == 1){
				   $('.favorite').addClass('on');
			   }
		    	
		    	 $('#indexPage').hide();
			 $('#subPage').show();
		     }
		     ,error : function(request, status, error) {
			         console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		     }
		 });
	}
	
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
		      
		       result = biMenuUrl.replace(biServerUrl, biServerUrl + ticket+"/");
				
				 $('#urlDiv iframe').attr('src', result);
				 $('#urlDiv').show();
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
            <input type="hidden" id="menuCd" name="menuCd" />
             <input type="hidden" id="gbn" name="gbn" />
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
		<div class="contentWrap">
		<img src="<c:url value="/images/biz/btncate_close.png"/>" alt="카테고리 열기&닫기" class="cateBtn">
		
        <div id="indexPage">
			<jsp:include page="/internal/mainSubPage.do"/> 
		</div>
		
		<div id="subPage" class="hidden">
			<!-- 콘텐츠영역 //-->
			
					<h3 class="stitle">
						<img src="<c:url value="/images/biz/icon_graph.png"/>" alt="" class="bl"><span id="menuNm"></span> 
						
							<i class="favorite"></i>
						
					</h3>
					
					<div class="subContentWrap">
						<div class="subContent">
							<!-- 콘텐츠 //-->
							
								<table class="tbl_view mt10">
									<caption></caption>
									<colgroup>
										<col width="80px">
										<col>
										<col width="180px">
									</colgroup>
									<tbody>
										<tr class="tbl_view_file" id="attachFile"  style="display:none">
											<th scope="row">첨부파일</th>
											<td colspan="2" id="fileTd">
											</td>
										</tr>
										<tr id="contentsTd"  style="display:none">
											<td colspan="3">
												<div class="tbl_view_txt" id="contents">
													
												</div>
											</td>
										</tr>
									</tbody>
								</table>
								<div class="bi_div" id="urlDiv" style="display:none">
									<iframe id="" name="" src="" style="width: 100%; height: 943px;"> 
								   </iframe>
							    </div>
						</div>
					</div>
				</div>
		<!--// 콘텐츠영역 -->		
	</div>
</div>
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/layout/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/layout/modals.jsp" %>

</body>
</html>
