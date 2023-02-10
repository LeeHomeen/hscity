 <%@ page contentType="text/html;charset=UTF-8"%>
 <script>
 $(document).ready(function(){
	    
		$.ajax({
		    url: "/internal/getMainImg.do",
		    type : "POST",
		    dataType: 'json',
		    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
		    success: function(response) {
			    	for(var i = 0; i<response.length; i++){
			    		setMainImgAndBI(i,response[i].menuImgUseYn,response[i].menuImg,response[i].menuUrl ,response[i].menuTypeScd,response[i].menuCd, response[i].mainLocNo);
			    	}
			    	if (response[0].layout == '2') {
			    		$('.dataWrap:odd').css('display','none');
			    		$('.dataWrap').css('height','1020px');
			    		$('.dataContent').css('height','1020px');
					} else if (response[0].layout == '3') {
						$('.dataWrap:odd').css('display','none');
			    		$('.dataItem').css('display','block');
			    		$('.w50p').attr('style','width: 100% !important');
					} else if (response[0].layout == '4') {
						$('#mainImg4').parent().parent().css('display','none');
						$('#mainImg3').parent().parent().attr('style','width: 100% !important');
					} else if (response[0].layout == '5') {
						$('#mainImg4').parent().parent().css('display','none');
						$('#mainImg1').parent().parent().attr('style','width: 100% !important');
						$('#d2').css('float','left');
					} else if (response[0].layout == '6') {
						$('.dataWrap:odd').css('display','none');
						$('.w50p').attr('style','width: 100% !important');
						$('.w50p').attr('style','width: 100% !important');
						$('.dataWrap').css('height','1020px');
						$('.dataContent').css('height','1020px');
						$('#d2').css('display','none');
					}
		    },
	        error : function(request, status, error) { 
	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	   	 	}
		});

	// 상세조회
 $('.board-title').on('click', function(){
	 $('.pop-box2').show();
	 /*
     $('#seq').val($(this).data('seq'));
     $('#searchVO').attr('action', '<c:url value="/internal/notice/view.do"/>');
     $('#searchVO').submit();
     */
	/*     
     var $submitForm = $('#submitForm');
     $("#submitForm #menuCd").val("MN00000031");
     $("#submitForm #url").val("/internal/notice/newNoticeView.do");
     $("#submitForm #gbn").val("bbs");
     $('#submitForm #seq').val($(this).data('seq'));
	 $submitForm.attr('action', '<c:url value="/internal/index.do"/>');
     $submitForm.submit();
     */
 });

 $(".cateWrap").height($('.contentWrap').outerHeight());
 
 	function getTicketAndUsualBIUrl(menuUrl,index){
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
		   
				$('#mainImg' + index).parent().html('<iframe id="mainBi' + index + '" style="width:100%; height:100%;" >')
				$('#mainBi' + index).attr("src", result);
		     },
		        error : function(request, status, error) {
		         console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		     }
		 });
	}
    
    function setMainImgAndBI(num,imgUseYn, img,url,gbn,menuCd, mainLocNo){
   	 	var index = (num + 1);
        	if(imgUseYn == 'y'){
        		// img
        	 	var src = '<c:url value="data:image/png;base64,' + img  +'"/>'
				$('#mainImg' + mainLocNo).attr("src", src);
        	 	 $('#mainImg' + mainLocNo).click(function() {
        	 		$('#menuCd').val(menuCd);
        			$('#url').val(url);
        			$('#gbn').val("bi");
        			$("#submitForm").attr('action', '/internal/index.do');
        			$("#submitForm").submit();
        	 	});
        	}else if (imgUseYn == 'n'){
        		if(gbn == 'bi'){
        			getTicketAndUsualBIUrl(url,index);
        		}else if(gbn == 'bi_pub'){
        			$('#mainImg' + index).parent().html('<iframe id="mainBi' + index + '" style="width:100%; height:100%;" >')
    				$('#mainBi' + index).attr("src", url);
        		}
        	
        	}
        	
    }
    
     displayWeather();

     function displayWeather() {
        

   
    	 
    	 /*
    	 var date = new Date();
         var year = date.getFullYear();
         var month = date.getMonth() + 1;
         var day = date.getDate();
         var hour = date.getHours() - 1;
         var minute = date.getMinutes();

         var base_date = year + '' + month.zf(2) + '' + day.zf(2);
         var base_time = hour.zf(2) + '' + minute.zf(2);

         var serviceKey = '${weatherApiKey}';

         $.ajax({
             url: [
                 "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastTimeData?serviceKey=" + serviceKey
                 ,"base_date=" + base_date
                 ,"base_time=" + base_time
                 ,"nx=57"
                 ,"ny=119"
                 ,"numOfRows=50"
                 ,"pageSize=10"
                 ,"pageNo=1"
                 ,"startPage=1"
                 ,"_type=json"
             ].join('&'),
             dataType: "json",
             type: 'GET',
             success: function(res) {
                 console.log("응답::",res);
            	 var myXML = CM.stringToXMLDom(res.responseText);
                 var json = $.xml2json(myXML);
                 var body = json.body;
                 if(body && body.items) {
                     var items = body.items.item;

                     if(items.length > 0) {
                         items = _.indexBy(items, 'category');

                         for(var key in items) {
                             // T1H: 기온
                             // SKY: 하늘상태 -> 맑음(1), 구름조금(2), 구름많음(3), 흐림(4)
                             // PTY: 강수형태 -> 없음(0), 비(1), 비/눈(2), 눈(3)
                             if(key === 'T1H') {
                                 $('#T1H').text(items[key]['fcstValue'] + '℃');
                             } else if(key === 'SKY') {
                                 var _sky = ['', '맑음', '구름조금', '구름많음', '흐림'];
                                 $('#SKY').text(_sky[items[key]['fcstValue']]);
                             } else if(key === 'PTY') {
                                 var _pty = ['눈/비 없음', '비', '눈/비', '눈'];
                                 $('#PTY').text(_pty[items[key]['fcstValue']]);
                             }
                         }
                     }
                 }
             },
             error : function(request, status, error) { 
 	        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
 	   	 	}
             //error: function() {
             //    console.log('날씨 정보를 가져오는데 실패했습니다.');
            // }
         });
         
         */

         var json = JSON.parse('${strWeather}');
         console.log("받아온JSON::",json);
         var body = json.response.body;
         console.log("json바디::",body);
         if(body && body.items) {
             var items = body.items.item;

             if(items.length > 0) {
                 items = _.indexBy(items, 'category');

                 for(var key in items) {
                     // T1H: 기온
                     // SKY: 하늘상태 -> 맑음(1), 구름조금(2), 구름많음(3), 흐림(4)
                     // PTY: 강수형태 -> 없음(0), 비(1), 비/눈(2), 눈(3)
                     if(key === 'T1H') {
                         $('#T1H').text(items[key]['fcstValue'] + '℃');
                     } else if(key === 'SKY') {
                         var _sky = ['', '맑음', '구름조금', '구름많음', '흐림'];
                         $('#SKY').text(_sky[items[key]['fcstValue']]);
                     } else if(key === 'PTY') {
                         var _pty = ['눈/비 없음', '비', '눈/비', '눈', '소나기'];
                         $('#PTY').text(_pty[items[key]['fcstValue']]);
                     }
                 }
             }else{
            	 $('#T1H').text("--℃");
            	 $('#PTY').text("연결중");
             }
         }else{
        	 $('#T1H').text("--℃");
        	 $('#PTY').text("연결중");
         }
         
     }
     
     // 화성시 인구수 클릭
  $('#hscity_population').on('click', function(){
    	 window.open("http://27.101.213.4/index.jsp", "newWin");
     });
     
  	 // 관리데이터 항목 클릭
     $('#hscity_data').on('click', function(){
    	 //window.open("", "newWin");
    	 alert("서비스 준비중입니다.");
     });
  
  	 // 일 접속자 수 클릭(bi)
     $('#hscity_connect').on('click', function(){
    	 
    	 if('${biStatsUserAccess}' ==''){
     		alert("서비스 준비중입니다.");
     	}else{    
 			$.ajax({
 			    url: "/tableau/goTableauTicket.do",
 			    type : "GET",
 			    dataType: 'json',
 			    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
 			    success: function(response) {
 			    	console.log(response);
 			    	
 			    	var ticket = "trusted/" + response.ticket + "/";
 			    	var biServerUrl = response.TableauUrl;
 			    	var biMenuUrl = '${biStatsUserAccess}';
 	
 			    	var url = biMenuUrl.replace(biServerUrl, biServerUrl + ticket+"/");
 	
 			    	 window.open(url, "newWin");
 			    },
 		        error : function(request, status, error) { 
 		        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
 		    	}
 			});
     	}
    	
    	 

     });
  
  	 // 화성시 날씨 클릭
     $('#hscity_weather').on('click', function(){
    	 window.open("http://www.weather.go.kr/weather/forecast/timeseries.jsp", "newWin");
     });

 });
 </script>
        

        <!-- 콘텐츠 //-->
        <ul class="summaryWrap">
            <li>
                <a href="#" id="hscity_population">
                    <div class="con">
                        <img src="<c:url value="/images/main/icon1.png"/>" alt="">
                        <h3>화성시 인구수</h3>
                        <p class="big"><strong>${hsPop}</strong>명</p>
                        <p class="small">* 경기도 인구수<br>${allPop}명</p>
                        <p>
                        <font color="red" size="2">*외국인 등록인구 포함</font>
                    </div>
                </a>
            </li>
            <li>
                <a href="#" id="hscity_data">
                    <div class="con">
                        <img src="<c:url value="/images/main/icon2.png"/>" alt="">
                        <h3>관리데이터 항목 수</h3>
                        <p class="big"><strong>약 ${dataCount}</strong>종</p>
                        <p class="small">* 포털에서 수집하는 데이터 항목 수입니다.</p>
                    </div>
                </a>
            </li>
            <li>
                <a href="#" id="hscity_connect">
                    <div class="con">
                        <img src="<c:url value="/images/main/icon3.png"/>" alt="">
                        <h3>일 접속자 수<span>(누계)</span></h3>
                        <p class="big"><strong>${logCount}</strong>명</p>
                        <p class="small">* 어제 일자까지의 접속자수 누계입니다.</p>
                    </div>
                </a>
            </li>
            <li>
                <a href="#" id="hscity_weather">
                    <div class="con" id="weather">
                        <img src="<c:url value="/images/main/icon4.png"/>" alt="">
                        <h3>기온 <strong id="T1H"></strong></h3>
                        <p class="big"><strong id="SKY"></strong><strong>(</strong><strong id="PTY"></strong><strong>)</strong></p>
                        <p class="small">* 현재 화성시내 평균 기온과 하늘상태를 나타냅니다.</p>
                    </div>
                </a>
            </li>
        </ul>
 <dl class="notice">
            <dt><img src="<c:url value="/images/biz/icon-notice.png"/>">공지사항</dt>
            <dd><a  class='board-title' href="#" onclick="return false;" data-seq="${notice.seq}" id="${notice.seq}">${notice.title}</a></dd>
        </dl>
       <div class="dataWrap">
				<div id="d1" class="dataItem w50p h300">
					<div class="dataContent">
						<img id="mainImg1" style=" max-width:100%; max-height:100%; width: auto; height: auto;">
					</div>
				</div>
				<div id="d2" class="dataItem w50p h300">
					<div class="dataContent">
						<img id="mainImg2" style="max-width:100%; max-height:100%; width: auto; height: auto;">
					</div>
				</div>
			</div>
			 <div class="dataWrap">
				<div class="dataItem w50p h300">
					<div class="dataContent">
						<img id="mainImg3" style="max-width:100%; max-height:100%; width: auto; height: auto;">
					</div>
				</div>
				<div class="dataItem w50p h300">
					<div class="dataContent">
						<img id="mainImg4" style="max-width:100%; max-height:100%; width: auto; height: auto;">
					</div>
				</div>
			</div>

       	 <form id='submitForm' name='submitForm' method="post">
            <input type="hidden" id="url" name="url" />
             <input type="hidden" id="gbn" name="gbn" />
            <input type="hidden" id="menuCd" name="menuCd" />
            <input type="hidden" id="seq" name="seq" />
        </form>
         <form id='searchVO' name='searchVO' method="post">
                    <input type="hidden" id="seq" name="seq" />
                    </form>
        <!--// 콘텐츠 -->
<!-- 모달 팝업: 게시판 상세보기 -->
<div class="modal-pop pop-box2">
	<div class="layer-pop" style="width:1000px;height:800px;">
		<p class="tit">${notice.title}</p>
		<div class="cont">
			${notice.contents}
		</div>
		<a href="#" class="btn-close pop-close">닫기</a><!--[d] "pop-close" 클래스 값을 클릭할 시 팝업 닫힘-->
	</div>
</div>
<!-- 모달 팝업 -->

<!-- 모달 팝업 : 긴급 공지사항 -->
<div class="modal-pop pop-box3">
	<div class="layer-pop" style="width:1000px;height:800px;">
		<p class="tit">긴급 공지사항</p>
		<div class="cont">
			2020년 08월 22일 서버점검으로 인하여 임시적으로 서비스 이용이 어려우니 참고하시길 바랍니다.
		</div>
		<a href="#" class="btn-close pop-close">닫기</a><!--[d] "pop-close" 클래스 값을 클릭할 시 팝업 닫힘-->
	</div>
</div>
<!-- 모달 팝업 -->