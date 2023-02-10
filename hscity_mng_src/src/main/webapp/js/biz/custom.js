$(function(){
	
	// 모달 팝업
	$(function(){
		$(".modal-pop").css({
			"position":"fixed",
			"top":"0%",
			"left":"0%",
			"width":"100%",
			"height":"100%",
			"background":"url(/images/cmm/bg-layer-pop.png)", //경로변경시 수정.
			"overflow-y":"auto"
		});
		
		$(".layer-pop").css({
			"margin": "100px auto 0"
		});

		$(".btn-popup").on("click",
			function(e){
				e.preventDefault();
				$("body").css(	{
					"overflow":"hidden",
					"margin-right":"18px" 
				});
				$(".pop-box1").show();			
		});
		$(".btn-popup2").on("click",
			function(e){
				e.preventDefault();
				$("body").css(	{
					"overflow":"hidden",
					"margin-right":"18px" 
				});
				$(".pop-box2").show();			
		});
		$(".pop-close").on("click",
			function(e){
				e.preventDefault();
				$(".modal-pop").hide();
				$("body").css({
					"overflow":"visible",
					"margin-right":"0px"
				});
		});


		// input 엔터시 버튼 클릭이벤트 주기
		$('input[type="text"]').on('keydown', function(e){
		    if(e.keyCode === 13) {
                var id = $(this).data('for');
                $('#' + id).trigger('click');
            }
        });

        // 한글입력막기 스크립트
        $(".no-kor").keyup(function(e) {
            if(e.keyCode === 8 || e.keyCode === 9 || e.keyCode === 37 || e.keyCode === 39 || e.keyCode === 46 ) {
                return;
            }
            var v = $(this).val();
            $(this).val(v.replace(/[\ㄱ-ㅎㅏ-ㅣ가-힣]/g, ''));
        });
        
        //internal 비밀번호 변경
        $("#btnChangePw").on("click", function(e){
	        	if($("#change_pw").val() != $("#change_pw_confirm").val()){
	        		alert('새 비밀번호와 비밀번호 확인이 같지 않습니다.');
	        		return;
	        	}
	        	
	        	$.ajax({
                url: "/internal/setNewPassword.do",
                type: "POST",
                dataType: "text",
                data: JSON.stringify({userPw: $("#current_pw").val(),userPwNew: $("#change_pw").val()}),
                headers: {'Content-Type': 'application/json; charset=UTF-8'},
                success: function (response) {
                    alert(response);
                    $('span.b-close img').trigger('click');
                },
                error: function (request, status, error) {
                    console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
                }
            });
    	});
        
       function getFavList(){
    	   var rowSize = $('table#favList').find('>tbody >tr').size();
   		for (var i=0; i<rowSize; i++) {
   			$('table#favList').find('>tbody >tr').remove();
   		}
			$.ajax({
   			    url: "/internal/getFavMenuList.do",
   			    type : "POST",
   			    dataType: 'json',
   			    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
   			    success: function(response) {
	   			    	var categoryNm = "";
	   			    	for (var i=0; i<response.length; i++) {
	   			    		if (response[i].categoryNm1st != "") {
	   			    			categoryNm += response[i].categoryNm1st + ">"; 

	   			    		}
	   			    		if (response[i].categoryNm2nd != "") {
	   			    			categoryNm += response[i].categoryNm2nd + ">"; 

	   			    		}
	   			    		if (response[i].categoryNm3rd != "") {
	   			    			categoryNm += response[i].categoryNm3rd + ">"; 
	   			    		}
	   			    		console.log(response[i].menuTypeScd);
	   			    		console.log(response[i].menuUrl);
	   			    		$('table#favList > tbody:last').append('<tr><td>' + categoryNm + response[i].menuNm + '</td><td class="t-right"><a class="btn btn-orange" onclick="fnMove(\'' + response[i].menuCd + '\', \'' + response[i].categoryCd1st + '\', \'' + response[i].menuTypeScd + '\', \'' + response[i].menuUrl + '\');">바로가기</a></td></tr>');
	   			    		categoryNm = "";
	   			    	}
	   			    	
	   			    	if (response.length == 0) {
	   			    		$('table#favList > tbody:last').append('<tr><td colspan="2" style="text-align:center;">※ 조회된 결과가 없습니다.</td></tr>');
	   			    	}
   			    },
   		        error : function(request, status, error) { 
   		        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
   		    	}
   			});
       }
        
       function getSchList(){
   		var rowSize = $('#tableSearchMenu').find('>tbody >tr').size();
		for (var i=0; i<rowSize; i++) {
			$('#tableSearchMenu').find('>tbody >tr').remove();
		}
		
   		if ($("#inputSearchMenu").val() == "") {
			$('#tableSearchMenu > tbody:last').append('<tr><td colspan="2">※ 조회된 결과가 없습니다.</td></tr>');
   		} else {
   			$.ajax({
   			    url: "/internal/getMenuCategory.do",
   			    type : "POST",
   			    dataType: 'json',
   			    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
   			    data: JSON.stringify({menuNm: $("#inputSearchMenu").val()}),
   			    success: function(response) {
   			    	var categoryNm = "";
   			    	
   			    	for (var i=0; i<response.length; i++) {
   			    		if (response[i].categoryNm1st != "") {
   			    			categoryNm += response[i].categoryNm1st + ">"; 
   			    		}
   			    		if (response[i].categoryNm2nd != "") {
   			    			categoryNm += response[i].categoryNm2nd + ">"; 
   			    		}
   			    		if (response[i].categoryNm3rd != "") {
   			    			categoryNm += response[i].categoryNm3rd + ">"; 
   			    		}
   			    		console.log(response[i].menuTypeScd);
   			    		console.log(response[i].menuUrl);
   			    		$('#tableSearchMenu > tbody:last').append('<tr><td>' + categoryNm + response[i].menuNm + '</td><td class="t-right"><a class="btn btn-orange" onclick="fnMove(\'' + response[i].menuCd + '\', \'' + response[i].categoryCd1st + '\', \'' + response[i].menuTypeScd + '\', \'' + response[i].menuUrl + '\');">바로가기</a></td></tr>');
   			    		categoryNm = "";
   			    	}
   			    	
   			    	if (response.length == 0) {
   			    		$('#tableSearchMenu > tbody:last').append('<tr><td colspan="2" style="text-align:center;">※ 조회된 결과가 없습니다.</td></tr>');
   			    	}
   			    },
   		        error : function(request, status, error) { 
   		        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
   		    	}
   			});
   		}
       }
       $("a.favoSelect.poplink").on("click", function(e) {
           getFavList()
		});
       
		$('#inputSearchMenu').keydown(function(e) {
			if (e.keyCode == 13) {
				$(".btn-search").trigger('click');
			}
		});

		$(".btn-search").on("click", function(e) {
			getSchList();
		});
	});
}());

function getTicketAndUsualBIUrl(menuUrl){
	$.ajax({
		url: "/tableau/goTableauTicket.do",
	    type : "GET",
	    dataType: 'json',
	    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
	    success: function(response) {
	    	var ticket = "trusted/" + response.ticket + "/";
	    	var biServerUrl = response.TableauUrl;
	    	var biMenuUrl = menuUrl;
	    	result = biMenuUrl.replace(biServerUrl, biServerUrl + ticket+"/");
			$('#submitForm #url').val(result);
			$('#submitForm').attr('action', '/internal/index.do');
			$('#submitForm').submit();
	     }, error : function(request, status, error) {
	        console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	     }
	 });
}

function fnMove(menuCd, categoryCd, menuGbn, menuUrl) {
	$.ajax({
        url: "/login/setMenuId.do",
        type: "POST",
        dataType: 'json',
        data: JSON.stringify({menuId: categoryCd }),
        headers: {'Content-Type': 'application/json; charset=UTF-8'},
        success: function (response) {
        },
        error: function (request, status, error) {
            console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
        }
    }).done(function(){
	    	
    });
	
	 var $submitForm = $('#submitForm');
	
	if (menuGbn == 'bi'){
		$('#menuCd').val(menuCd);
		$('#url').val(menuUrl);
		$('#gbn').val(menuGbn);
		$submitForm.attr('action', '/internal/index.do');
        $submitForm.submit();
	}  else if(menuGbn =='nor'){
		$('#menuCd').val(menuCd);
		$('#url').val(menuUrl);
		$('#gbn').val(menuGbn);
		$submitForm.attr('action', '/internal/index.do');
        $submitForm.submit();	
	}  else if(menuGbn =='bi_pub'){
		$('#menuCd').val(menuCd);
		
		var strUrl = menuUrl;
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
		//$('#url').val(menuUrl);
		$('#url').val(strUrl);
		$('#gbn').val(menuGbn);
		$submitForm.attr('action', '/internal/index.do');
        $submitForm.submit();	
	} else if(menuGbn =='bbs'){
		$('#menuCd').val(menuCd);
		$('#url').val(menuUrl);
		$('#gbn').val(menuGbn);
		$submitForm.attr('action', '/internal/index.do');
        $submitForm.submit();	
	} else if(menuGbn =='gis'){
		if (menuUrl == "http://105.24.1.34/hsgis/policyMap/sso.do") {//공간정보시스템 시각화 페이지
			if (window.sessionStorage.getItem("ssoUserYn") == "n") {
				alert("새올사용자만 접근 가능한 페이지입니다.");
			} else {
				window.open(menuUrl + "?userId=" + window.sessionStorage.getItem("userId"), "newWin");				
			}
		} else {
			window.open(menuUrl, "newWin");
		}
		//window.open(menuUrl, "newWin");
	}
	$('span.b-close').trigger('click');
}

