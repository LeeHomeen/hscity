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

	// ์์ธ์กฐํ
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
                 console.log("์๋ต::",res);
            	 var myXML = CM.stringToXMLDom(res.responseText);
                 var json = $.xml2json(myXML);
                 var body = json.body;
                 if(body && body.items) {
                     var items = body.items.item;

                     if(items.length > 0) {
                         items = _.indexBy(items, 'category');

                         for(var key in items) {
                             // T1H: ๊ธฐ์จ
                             // SKY: ํ๋์ํ -> ๋ง์(1), ๊ตฌ๋ฆ์กฐ๊ธ(2), ๊ตฌ๋ฆ๋ง์(3), ํ๋ฆผ(4)
                             // PTY: ๊ฐ์ํํ -> ์์(0), ๋น(1), ๋น/๋(2), ๋(3)
                             if(key === 'T1H') {
                                 $('#T1H').text(items[key]['fcstValue'] + 'โ');
                             } else if(key === 'SKY') {
                                 var _sky = ['', '๋ง์', '๊ตฌ๋ฆ์กฐ๊ธ', '๊ตฌ๋ฆ๋ง์', 'ํ๋ฆผ'];
                                 $('#SKY').text(_sky[items[key]['fcstValue']]);
                             } else if(key === 'PTY') {
                                 var _pty = ['๋/๋น ์์', '๋น', '๋/๋น', '๋'];
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
             //    console.log('๋?์จ ์?๋ณด๋ฅผ ๊ฐ์?ธ์ค๋๋ฐ ์คํจํ์ต๋๋ค.');
            // }
         });
         
         */

         var json = JSON.parse('${strWeather}');
         console.log("๋ฐ์์จJSON::",json);
         var body = json.response.body;
         console.log("json๋ฐ๋::",body);
         if(body && body.items) {
             var items = body.items.item;

             if(items.length > 0) {
                 items = _.indexBy(items, 'category');

                 for(var key in items) {
                     // T1H: ๊ธฐ์จ
                     // SKY: ํ๋์ํ -> ๋ง์(1), ๊ตฌ๋ฆ์กฐ๊ธ(2), ๊ตฌ๋ฆ๋ง์(3), ํ๋ฆผ(4)
                     // PTY: ๊ฐ์ํํ -> ์์(0), ๋น(1), ๋น/๋(2), ๋(3)
                     if(key === 'T1H') {
                         $('#T1H').text(items[key]['fcstValue'] + 'โ');
                     } else if(key === 'SKY') {
                         var _sky = ['', '๋ง์', '๊ตฌ๋ฆ์กฐ๊ธ', '๊ตฌ๋ฆ๋ง์', 'ํ๋ฆผ'];
                         $('#SKY').text(_sky[items[key]['fcstValue']]);
                     } else if(key === 'PTY') {
                         var _pty = ['๋/๋น ์์', '๋น', '๋/๋น', '๋', '์๋๊ธฐ'];
                         $('#PTY').text(_pty[items[key]['fcstValue']]);
                     }
                 }
             }else{
            	 $('#T1H').text("--โ");
            	 $('#PTY').text("์ฐ๊ฒฐ์ค");
             }
         }else{
        	 $('#T1H').text("--โ");
        	 $('#PTY').text("์ฐ๊ฒฐ์ค");
         }
         
     }
     
     // ํ์ฑ์ ์ธ๊ตฌ์ ํด๋ฆญ
  $('#hscity_population').on('click', function(){
    	 window.open("http://27.101.213.4/index.jsp", "newWin");
     });
     
  	 // ๊ด๋ฆฌ๋ฐ์ดํฐ ํญ๋ชฉ ํด๋ฆญ
     $('#hscity_data').on('click', function(){
    	 //window.open("", "newWin");
    	 alert("์๋น์ค ์ค๋น์ค์๋๋ค.");
     });
  
  	 // ์ผ ์?์์ ์ ํด๋ฆญ(bi)
     $('#hscity_connect').on('click', function(){
    	 
    	 if('${biStatsUserAccess}' ==''){
     		alert("์๋น์ค ์ค๋น์ค์๋๋ค.");
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
  
  	 // ํ์ฑ์ ๋?์จ ํด๋ฆญ
     $('#hscity_weather').on('click', function(){
    	 window.open("http://www.weather.go.kr/weather/forecast/timeseries.jsp", "newWin");
     });

 });
 </script>
        

        <!-- ์ฝํ์ธ? //-->
        <ul class="summaryWrap">
            <li>
                <a href="#" id="hscity_population">
                    <div class="con">
                        <img src="<c:url value="/images/main/icon1.png"/>" alt="">
                        <h3>ํ์ฑ์ ์ธ๊ตฌ์</h3>
                        <p class="big"><strong>${hsPop}</strong>๋ช</p>
                        <p class="small">* ๊ฒฝ๊ธฐ๋ ์ธ๊ตฌ์<br>${allPop}๋ช</p>
                        <p>
                        <font color="red" size="2">*์ธ๊ตญ์ธ ๋ฑ๋ก์ธ๊ตฌ ํฌํจ</font>
                    </div>
                </a>
            </li>
            <li>
                <a href="#" id="hscity_data">
                    <div class="con">
                        <img src="<c:url value="/images/main/icon2.png"/>" alt="">
                        <h3>๊ด๋ฆฌ๋ฐ์ดํฐ ํญ๋ชฉ ์</h3>
                        <p class="big"><strong>์ฝ ${dataCount}</strong>์ข</p>
                        <p class="small">* ํฌํธ์์ ์์งํ๋ ๋ฐ์ดํฐ ํญ๋ชฉ ์์๋๋ค.</p>
                    </div>
                </a>
            </li>
            <li>
                <a href="#" id="hscity_connect">
                    <div class="con">
                        <img src="<c:url value="/images/main/icon3.png"/>" alt="">
                        <h3>์ผ ์?์์ ์<span>(๋๊ณ)</span></h3>
                        <p class="big"><strong>${logCount}</strong>๋ช</p>
                        <p class="small">* ์ด์? ์ผ์๊น์ง์ ์?์์์ ๋๊ณ์๋๋ค.</p>
                    </div>
                </a>
            </li>
            <li>
                <a href="#" id="hscity_weather">
                    <div class="con" id="weather">
                        <img src="<c:url value="/images/main/icon4.png"/>" alt="">
                        <h3>๊ธฐ์จ <strong id="T1H"></strong></h3>
                        <p class="big"><strong id="SKY"></strong><strong>(</strong><strong id="PTY"></strong><strong>)</strong></p>
                        <p class="small">* ํ์ฌ ํ์ฑ์๋ด ํ๊ท? ๊ธฐ์จ๊ณผ ํ๋์ํ๋ฅผ ๋ํ๋๋๋ค.</p>
                    </div>
                </a>
            </li>
        </ul>
 <dl class="notice">
            <dt><img src="<c:url value="/images/biz/icon-notice.png"/>">๊ณต์ง์ฌํญ</dt>
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
        <!--// ์ฝํ์ธ? -->
<!-- ๋ชจ๋ฌ ํ์: ๊ฒ์ํ ์์ธ๋ณด๊ธฐ -->
<div class="modal-pop pop-box2">
	<div class="layer-pop" style="width:1000px;height:800px;">
		<p class="tit">${notice.title}</p>
		<div class="cont">
			${notice.contents}
		</div>
		<a href="#" class="btn-close pop-close">๋ซ๊ธฐ</a><!--[d] "pop-close" ํด๋์ค ๊ฐ์ ํด๋ฆญํ? ์ ํ์ ๋ซํ-->
	</div>
</div>
<!-- ๋ชจ๋ฌ ํ์ -->

<!-- ๋ชจ๋ฌ ํ์ : ๊ธด๊ธ ๊ณต์ง์ฌํญ -->
<div class="modal-pop pop-box3">
	<div class="layer-pop" style="width:1000px;height:800px;">
		<p class="tit">๊ธด๊ธ ๊ณต์ง์ฌํญ</p>
		<div class="cont">
			2020๋ 08์ 22์ผ ์๋ฒ์?๊ฒ์ผ๋ก ์ธํ์ฌ ์์์?์ผ๋ก ์๋น์ค ์ด์ฉ์ด ์ด๋?ค์ฐ๋ ์ฐธ๊ณ?ํ์๊ธธ ๋ฐ๋๋๋ค.
		</div>
		<a href="#" class="btn-close pop-close">๋ซ๊ธฐ</a><!--[d] "pop-close" ํด๋์ค ๊ฐ์ ํด๋ฆญํ? ์ ํ์ ๋ซํ-->
	</div>
</div>
<!-- ๋ชจ๋ฌ ํ์ -->