/**
 * ajax Global 설정
 */
$.ajaxSetup({
    beforeSend: function(jqXHR, settings) {
        jqXHR.setRequestHeader("AJAX", true);
    },
    complete: function(jqXHR, textStatus) {
    	$('#divMapLoading').hide();
    },
    error: function(jqXHR, textStatus) {
    	$('#divMapLoading').hide();
        if(jqXHR.status === 401) {
        	//openInfoPopup(true,"로그인 정보 에러가 발생하였습니다.");            
        	//openInfoPopup(true,"사용자정보가 없습니다. 다시 로그인하시기 바랍니다.");
        	location.href = "/login/logout.do";
        } else {
        	openInfoPopup(true,"서버 에러가 발생하였습니다.");
        }
    }
});

/**
 * 세션없음 체크후 로그아웃
 */
function chkSession(status){
	var chk = true;
	if(status === 401 ){
		alert("로그인정보가 없습니다. 다시 로그인하시기 바랍니다.");
		location.href = "/login/logout.do";
		chk = false;
	}
	return chk;
}

/**
 * 페이지화면 load
 * return jsp page
 */
function searchJspPage(url, params, async, id){
	$('#'+id).html("");
	$.ajax({
		type:"POST",
		url:url,
		dataType:'html',
		data: params,
		async: async,
		success:function(data){
			$('#'+id).html(data);
		}
	});
}

//왼쪽 메뉴 RESIZE시 MAP UPDATE
function updateMap(flag, chkType, pid){
	resizeGrid(chkType, pid);
	resizeChart(chkType, pid);
	//추가 기능
	var mapObj = getMapObj(flag);
	if(mapObj != null){				
		mapObj.mapRefresh();
	}	
}

/**
 * 시작수, 종료수, 제한수 체크
 * 
 */
function chkNumSeq(start, end, limit){
	var chkSeq = false;
	if(start != undefined && start != null && start != ''){
		if(end != undefined && end != null && end != ''){
			start = start.replace(/-/g, ''); 
			end = end.replace(/-/g, '');
			var start = Number(start);
			var end = Number(end);
			if(end >= start){				
				chkSeq = true;
			}else{
				openInfoPopup(true,"시작월(일)이 종료월(일)보다 초과되었습니다.");
			}
		}else{
			openInfoPopup(true,"종료월(일)을 선택하여주시기 바랍니다.");
		}
	}else{
		openInfoPopup(true,"시작월(일)을 선택하여주시기 바랍니다.");	
	}
	return chkSeq;
}


/**
 * 기준일 , 월 구분가져오기
 * @returns {String}
 */
function getStdInfo(id){
	var std = ''; //기준년월 또는 기준일자 정보 가져오기
	 if($('#liMon'+id).hasClass("active"))
		 std = "month";
	 else if($('#liDay'+id).hasClass("active"))
		 std = "day";
	 return std;
}

/**
 * 기준년월(일) validation
 * @returns
 */
function chkNumValid(id){
	var std = getStdInfo(id);
	var start = "";
	var end = "";
	if(std == 'day'){		 
		 start = $('#txtDayStart').val();
		 end = $('#txtDayEnd').val();
	}else{
		start = $('#txtMonthStart').val();			 
		end = $('#txtMonthEnd').val();
	}
	return chkNumSeq(start, end, 0);
}



/**
 * 선택한 체크박스 정보 가져오기
 * @param id
 */
function getChkItems(id){
	var val = '';
	$('.'+id).each(function(index, item){
		if(item.checked)
			val += item.value + ",";
	});
	if(val != '')
		val = val.substring(0, val.length-1);	
	return val;
}

//기준년월일 세팅 
function setBasicDay(id){
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var day = date.getDay();
	if(Number(month) < 10)
		month = 0 + "" + month;
	if(Number(day) < 10)
		day = 0 + "" + day;
	$('#'+id).val(year + "-" + month + "-" + day);	
}

//기준녕월 세팅
function setBasicMonth(id){
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	if(Number(month) < 10)
		month = 0 + "" + month;
	$('#'+id).val(year + "-" + month);
}

//달력 달까지만 선택
function setCalendarMonth(id){
	$('#'+id).datepicker({
		format: "yyyy-mm",
		language: "kr",
		startView: 2,
		minViewMode: 1,
		daysOfWeekDisabled: "0",
		autoclose: true
	});
}

//달력 일까지만 선택
function setCalendarDay(id){
	$('#'+id).datepicker({
		format: "yyyy-mm-dd",
		language: "kr",
		startView: 0,
		autoclose: true
	});
}

/**
 * colorpicker 세팅
 */
function setColorPicker(id, color){
	$('#colpck'+id).ColorPicker({
		color: color,
		onShow: function (colpkr) {
			$(colpkr).fadeIn(500);				
			return false;
		},
		onHide: function (colpkr) {
			$(colpkr).fadeOut(500);
			return false;
		},
		onChange: function (hsb, hex, rgb) {
			$('#colpck'+id+' div').css('backgroundColor', '#' + hex);
		}
	});
}

/**
 *  읍/면동 기준 리스트 가져오기
*/
function selectStdList(selectId, mapType){
	$('#'+selectId).empty();
	$.ajax({
		type:"POST",
		url:"/gis/comm/stdList.do",
		data:null,
		dataType:'json',		
		success:function(data){
			if(data != undefined && data != null && data != ''){
				if(data.result.length > 0){
					var obj = data.result;
					for(var i=0; i<obj.length; i++){
						$('#'+selectId).append("<option value='"+obj[i].chk+"'>"+obj[i].gnm+"</option>");
						if(i == 0){
							$('#'+selectId).trigger("change");
						}
					}
					//selectDongList("selDong", mapType); //읍면동 리스트 가져오기
				}else{
					openInfoPopup(true,"읍면동 기준정보 조회결과가 없습니다.");
				}
			}else{
				openInfoPopup(true,"읍면동 기준정보 조회결과가 없습니다.");
			}
		},
		error:function(err){
			openInfoPopup(true,"읍면동 기준정보 조회 중 에러가 발생하였습니다.");
		}
	});
}

/**
 *  읍/면동 리스트 가져오기
*/
function selectDongList(selectId, mapType){
	$('#'+selectId).empty();
	$.ajax({
		type:"POST",
		url:"/gis/comm/dongList.do",
		data:{std: $("#selStd").val()},//$("#selStd option:selected").text()
		dataType:'json',		
		success:function(data){
			if(data != undefined && data != null && data != ''){
				if(data.result.length > 0){
					var obj = data.result;
					if(obj.length > 0){
						$('#'+selectId).append("<li style='display:none;'><input style='display:none;' type='checkbox' name='tmp' value=''/>&nbsp;</li>");
						for(var i=0; i<obj.length; i++){
							/*
							$('#'+selectId).append("<option value='"+obj[i].admcd+"'>"+obj[i].admnm+"</option>");
							if(i == 0){
								$('#'+selectId).trigger("change");
							}
							*/
							$('#'+selectId).append("<li><input type='checkbox' name='multiarea' value='"+obj[i].admcd+"'/>&nbsp;&nbsp;"+obj[i].admnm+"</li>");
						}	
						console.log( $('#multiCheckedbox') );
						$('#multiCheckedbox').dropdownMultiCheckbox();
						// getSelectAreas(mapType);
					}										
				}else{
					openInfoPopup(true,"읍면동 조회결과가 없습니다.");
				}
			}else{
				openInfoPopup(true,"읍면동 조회결과가 없습니다.");
			}
		},
		error:function(err){
			openInfoPopup(true,"읍면동 조회 중 에러가 발생하였습니다.");
		}
	});
}

/**
 * 법정동 정보 가져오기 
 */
function selectCstDongList(selectId, mapType){	
	$('#'+selectId).empty();
	$.ajax({
		type:"POST",
		url:"/gis/comm/cstDongList.do",		
		dataType:'json',		
		success:function(data){
			if(data != undefined && data != null && data != ''){
				if(data.result.length > 0){
					var obj = data.result;
					if(obj.length > 0){						
						for(var i=0; i<obj.length; i++){
							$('#'+selectId).append("<option value='"+obj[i].admcd+"'>"+obj[i].admnm+"</option>");
						}						
					}										
				}else{
					openInfoPopup(true,"읍면동 조회결과가 없습니다.");
				}
			}else{
				openInfoPopup(true,"읍면동 조회결과가 없습니다.");
			}
		},
		error:function(err){
			openInfoPopup(true,"읍면동 조회 중 에러가 발생하였습니다.");
		}
	});
}

//선택되어 있는 읍면동 코드정보 가져오기
function multiAreaValue(domName){
	var name = "multiarea";
	if(domName != undefined && domName != null && domName != '')
		name = domName;		
	var multiDong = '';
	$("input[name='"+name+"']:checkbox").each(function(){
		if($(this)[0].checked){
			multiDong += $(this).val() + ",";			
		}						
	});
	if(multiDong != '')
		multiDong = multiDong.substring(0, multiDong.length-1);
	
	return multiDong;
}

//선택되어 있는 읍면동 수 체크
function chkMultiAreaCnt(limit, domName){
	if($('#liChkArea').hasClass("active")){
		var name = "multiarea";
		if(domName != undefined && domName != null && domName != '')
			name = domName;	
		var cnt = $("input[name='"+name+"']:checked").length;
		if(cnt == 0){
			openInfoPopup(true,"선택한 읍/면/동 정보가 없습니다.");
			return false;
		}else if(cnt > 0 && limit >= cnt){		
			return true;
		}else{
			openInfoPopup(true,"분석가능한  읍/면/동 수는 최대 "+limit+"개 입니다.");
			return false;
		}	
	}else if($('#liChkUser').hasClass("active")){
		var layer = getMapObj('pop').getLayer("analysisLayer");
		var feature = layer.getSource().getFeatures();
		if(feature.length > 0){
			return true;
		}else{
			openInfoPopup(true,"설정된 영역정보가 없습니다.");
			return ;
		}
	}
}

//선택되어 있는 버스노선수 체크
function chkMultiBusCnt(limit, domName){
	var name = "multibus";
	if(domName != undefined && domName != null && domName != '')
		name = domName;	
	var cnt = $("input[name='"+name+"']:checked").length;
	if(cnt == 0){
		openInfoPopup(true,"선택한 버스노선정보가 없습니다.");
		return false;
	}else if(cnt > 0 && limit >= cnt){		
		return true;
	}else{
		openInfoPopup(true,"분석가능한  버스노선 수는 최대 "+limit+"개 입니다.");
		return false;
	}
}


/**
 * 선택된 지역정보 가져오기
 */

function getSelectAreas(mapType, callback){
	
	var mapObj = getMapObj(mapType);
	
	// 행정동 경계 선택시
	if($('#liChkArea').hasClass("active")){
		var mapObj = getMapObj(mapType)
		var multiDong = multiAreaValue();
		if(multiDong != ''){
			//poly정보 가져오기			
			mapObj.popAreaSearch = true;			
			$.ajax({
				type:"POST",
				url:"/gis/comm/dongWkt.do",
				data:{dongcd:multiAreaValue(), 
					 coord:mapObj.getProjection().replace("EPSG:", ''),
					 std:$('#selStd').val()},
				dataType:'json',
				async: false,
				success:function(data){
					mapObj.popAreaSearch = false;
					if(data.result != undefined && data.result != null && data.result.length > 0){
						var layer = mapObj.getLayer("analysisLayer");
						layer.getSource().clear();
						var feature = mapObj.getWktToGeometry(data.result[0].wkt);
						feature.set("type", "area");
						layer.getSource().addFeatures([feature]);
						mapObj.zoomToLayer("analysisLayer")
					}
				},
				error:function(err){			
					mapObj.popAreaSearch = false;
					mapObj.popAreaPoly = null;
				},
				complete: function() {
					if( callback ) callback.call(this);
				}
			});	
		}
		else {
			callback.call(this);
		}
	}
	// 사용자영역 조회
	else if($('#liChkUser').hasClass("active")){
		callback.call(this);
	}
}

/**
 * 법정동 관련 선택한 지역정보 가져오기 (다중)
 */
function getSelectCstAreas(mapType){	
	var mapObj = getMapObj(mapType);
	var multiDong = multiAreaValue("cstMultiarea");
	if(multiDong != ''){
		//poly정보 가져오기			
		mapObj.popAreaSearch = true;			
		$.ajax({
			type:"POST",
			url:"/gis/comm/cstDongWkt.do",
			data:{dongcd:multiAreaValue("cstMultiarea"), 
				 coord:mapObj.map.getProjection().replace("EPSG:", ''),
				 std:$('#selStd').val()},
			dataType:'json',
			async: false,
			success:function(data){					
				mapObj.popAreaSearch = false;
				if(data.result != undefined && data.result != null && data.result.length > 0){						
					mapObj.popAreaPoly = new OpenLayers.Format.WKT().read(data.result[0].wkt);
				}else
					mapObj.popAreaPoly = null;
			},
			error:function(err){			
				mapObj.popAreaSearch = false;
				mapObj.popAreaPoly = null;
			}
		});	
	}
}

//단일 법정동 지역정보 가져오기
function getSelectCstAreas(mapType, emdcd){
	var result = null;
	var mapObj = getMapObj(mapType);
	$.ajax({
		type:"POST",
		url:"/gis/comm/cstDongWkt.do",
		data:{dongcd:emdcd, 
			 coord:mapObj.map.getProjection().replace("EPSG:", '')
		},
		dataType:'json',
		async: false,
		success:function(data){
			if(data.result != undefined && data.result != null && data.result.length > 0){						
				var wkt = new OpenLayers.Format.WKT().read(data.result[0].wkt);
				result = wkt;
			}
		}
	});
	return result;
}


/**
 * 사용자 지정영역 북마크정보 가져오기
 * @param selectId
 */
function selectBookmarkList(selectId){
	$('#'+selectId).empty();
	$.ajax({
		type:"POST",
		url:"/gis/comm/bmkList.do",		
		dataType:'json',		
		success:function(data){
			$('#'+selectId).append("<option value=''>-- 지정영역 선택 --</option>");
			if(data != undefined && data != null && data != ''){
				if(data.result.length > 0){
					var obj = data.result;
					for(var i=0; i<obj.length; i++){
						$('#'+selectId).append("<option value='"+obj[i].seq+"'>"+obj[i].name+"</option>");						
						$('#'+selectId).append("<input value='"+obj[i].geom+"' type='hidden' id='hid"+obj[i].seq+"'/>");
					}					
				}
			}
		},
		error:function(err){
			openInfoPopup(true,"사용자 지정영역 조회 중 에러가 발생하였습니다.");
		}
	});
}

/**
 *  업종별 대분류
 *  select id, 중분류 유무 여부
*/
function selectLclasList(selectId, isMclas){	
	$('#l'+selectId).empty(); //대분류	
	if(isMclas){
		$('#s'+selectId).empty(); //소분류
		$('#m'+selectId).empty(); //중분류
	}
	$.ajax({
		type:"POST",
		url:"/gis/comm/lClasList.do",
		data:null,
		dataType:'json',		
		success:function(data){
			if(data != undefined && data != null && data != ''){
				if(data.result.length > 0){
					var obj = data.result;
					for(var i=0; i<obj.length; i++){
						$('#l'+selectId).append("<option value='"+obj[i].cd+"'>"+obj[i].nm+"</option>");
						if(isMclas && i == 0){
							$('#l'+selectId).trigger("change");
						}
					}					
				}else{
					openInfoPopup(true,"업종정보(대분류) 조회결과가 없습니다.");
				}
			}else{
				openInfoPopup(true,"업종정보(대분류) 조회결과가 없습니다.");
			}
		},
		error:function(err){
			openInfoPopup(true,"업종정보(대분류) 조회 중 에러가 발생하였습니다.");
		}
	});
}

/**
 *  업종별 중분류
*/
function selectMclasList(selectId){	
	$('#m'+selectId).empty(); //중분류
	$('#s'+selectId).empty(); //소분류
	$('#m'+selectId).append("<option value=''>전체</option>");
	$.ajax({
		type:"POST",
		url:"/gis/comm/mClasList.do",
		data:{cd: $('#l'+selectId).val()},
		dataType:'json',		
		success:function(data){
			if(data != undefined && data != null && data != ''){
				if(data.result.length > 0){
					var obj = data.result;
					for(var i=0; i<obj.length; i++){
						$('#m'+selectId).append("<option value='"+obj[i].cd+"'>"+obj[i].nm+"</option>");
						if(i == 0){
							$('#m'+selectId).trigger("change");
						}
					}					
				}
			}
		},
		error:function(err){
			openInfoPopup(true,"업종정보(중분류) 조회 중 에러가 발생하였습니다.");
		}
	});
}

/**
 *  업종별 소분류
*/
function selectSclasList(selectId){
	$('#s'+selectId).empty(); //소분류
	$('#s'+selectId).append("<option value=''>전체</option>");
	$.ajax({
		type:"POST",
		url:"/gis/comm/sClasList.do",
		data:{cd: $('#m'+selectId).val()},
		dataType:'json',		
		success:function(data){
			if(data != undefined && data != null && data != ''){
				if(data.result.length > 0){
					var obj = data.result;
					for(var i=0; i<obj.length; i++){
						$('#s'+selectId).append("<option value='"+obj[i].cd+"'>"+obj[i].nm+"</option>");						
					}					
				}
			}
		},
		error:function(err){
			openInfoPopup(true,"업종정보(소분류) 조회 중 에러가 발생하였습니다.");
		}
	});
}

/**
 * 선택 업종정보 가져오기
 */
function getCtgInfo(selectId){
	var lclas = $('#l'+selectId).val();
	var mclas = $('#m'+selectId).val();
	var sclas = $('#s'+selectId).val();
	var cd = lclas+"%";
	if(mclas != undefined && mclas)
		cd = mclas+"%";
	if(sclas != undefined && sclas)
		cd = sclas;
	return cd;
}


/**
 * 분석영역 설정
 */
//분석영역 설정 타입 (인구/매출 분석)
function cmmAreaType(){
	var type = 'area'; //행정동 경계
	if($('#liChkUser').hasClass("active")){ 
		type = "user"; //사용자 지정영역
	} 
	return type;
}


//메뉴 한글명 가져오기
function getMenuName(id){
	var analsName = "";
	if(id == '1-1-1')
		 analsName = "인구분석_기간별";
	 else if(id == '1-1-2')
		 analsName = "인구분석_성/연령별";
	 else if(id == '1-1-3')
		 analsName = "인구분석_전입";
	 else if(id == '1-1-4')
		 analsName = "인구분석_전출";
	 else if(id == '1-2-1')
		 analsName = "유동인구_기간별";
	 else if(id == '1-2-2')
		 analsName = "유동인구_성/연령별";
	 else if(id == '1-2-3')
		 analsName = "유동인구_시간대별";
	 else if(id == '1-3-1')
		 analsName = "유입인구_시도별";
	 else if(id == '1-3-2')
		 analsName = "유입인구_시군구별";
	 else if(id == '1-3-3')
		 analsName = "유입인구_성/연령별";
	 else if(id == '1-4-1')
		 analsName = "매출분석_기간별";
	 else if(id == '1-4-2')
		 analsName = "매출분석_성/연령별";
	 else if(id == '1-4-3')
		 analsName = "매출분석_시간대별";
	 else if(id == '1-4-4')
		 analsName = "매출분석_업종별";
	 else if(id == '1-4-5')
		 analsName = "매출분석_외국인";
	 else if(id == '1-5-1')
		 analsName = "유입매출_시도별";
	 else if(id == '1-5-2')
		 analsName = "유입매출_시군구별";
	 else if(id == '1-5-3')
		 analsName = "유입매출_화성(인점)";
	 else if(id == "2-1-1")
		 analsName = "사각지대 분석";
	 else if(id == "2-2-1")
		 analsName = "저상버스 도입요구 분석";
	 else if(id == "2-3-1")
		 analsName = "교통카드 현황분석";
	 else if(id == "2-4-1")
		 analsName = "환승편의시설 분석";
	 else if(id == "2-5-1")
		 analsName = "요일별 승차인원";
	 else if(id == "2-5-2")
		 analsName = "시간대별 승차인원";
	 else if(id == "2-6-1")
		 analsName = "버스노선 중복도";
	 else if(id == "2-7-1")
		 analsName = "통행패턴 분석";
	
	return analsName;
}

//메뉴별 id가져오기
function getMenuId(){
	var id = "";
	if($('#t1-1-1').hasClass("active"))
		id = "1-1-1";
	else if($('#t1-1-2').hasClass("active"))
		id = "1-1-2";
	else if($('#t1-1-3').hasClass("active"))
		id = "1-1-3";
	else if($('#t1-1-4').hasClass("active"))
		id = "1-1-4";
	
	else if($('#t1-2-1').hasClass("active"))
		id = "1-2-1";
	else if($('#t1-2-2').hasClass("active"))
		id = "1-2-2";
	else if($('#t1-2-3').hasClass("active"))
		id = "1-2-3";
	
	else if($('#t1-3-1').hasClass("active"))
		id = "1-3-1";
	else if($('#t1-3-2').hasClass("active"))
		id = "1-3-2";
	else if($('#t1-3-3').hasClass("active"))
		id = "1-3-3";
	
	else if($('#t1-4-1').hasClass("active"))
		id = "1-4-1";
	else if($('#t1-4-2').hasClass("active"))
		id = "1-4-2";
	else if($('#t1-4-3').hasClass("active"))
		id = "1-4-3";
	else if($('#t1-4-4').hasClass("active"))
		id = "1-4-4";
	else if($('#t1-4-5').hasClass("active"))
		id = "1-4-5";
	
	else if($('#t1-5-1').hasClass("active"))
		id = "1-5-1";
	else if($('#t1-5-2').hasClass("active"))
		id = "1-5-2";
	else if($('#t1-5-3').hasClass("active"))
		id = "1-5-3";
	
	else if($('#t2-1-1').hasClass("active"))
		id = "2-1-1";
	else if($('#t2-2-1').hasClass("active"))
		id = "2-2-1";
	else if($('#t2-3-1').hasClass("active"))
		id = "2-3-1";
	else if($('#t2-4-1').hasClass("active"))
		id = "2-4-1";
	else if($('#t2-5-1').hasClass("active"))
		id = "2-5-1";
	else if($('#t2-5-2').hasClass("active"))
		id = "2-5-2";
	else if($('#t2-6-1').hasClass("active"))
		id = "2-6-1";
	else if($('#t2-7-1').hasClass("active"))
		id = "2-7-1";
	
	return id;
}


/**
 * 범례정보 가져오기
 * @param url
 * @param 파라미터들
 */
function getLegendInfo(url, params){
	var result = null;
	$.ajax({
		type:"POST",
		url:url,
		data:params,
		dataType:'json',
		async: false,
		success:function(data){			
			result = data.result;
		},
		error:function(err){			
			result = null;
		}		
	});
	return result;
}

//시계열 중지
function tsPopStop(mapType, pid){
	console.log("tsPopStop");
	var mapObj = getMapObj(mapType);
	//mapObj.mapCustom.tsPopStop();
	clearInterval(mapObj.chkTsUse);
	mapObj.chkTsUse = null;
	$('#spanTsLabel'+pid).html("");
	$('#btnTsStop'+pid).hide();
	$('#btnTsStart'+pid).show();
}

//시계열 분석 체크
function chkTsAnals(mapType){
	var mapObj = getMapObj(mapType);
	if(mapObj.chkTsUse != null){
		openInfoPopup(true,"시계열분석 중 입니다.");
		return false;
	}else{
		return true;
	}
	
}

//시계열 분석
function tsAnalsResult(mapType, pid){
	console.log("tsAnalsResult", mapType, pid)
	var mapObj = getMapObj(mapType); //map.js
	console.log("mapObj", mapObj)
	var infos = gridInfo(mapType); //grid_chart.js
	console.log("infos", infos)
	
	if(infos.ids != undefined && infos.ids != null){
		if(infos.ids.length > 0){
			$('#btnTsStart'+infos.pid).hide();
			$('#btnTsStop'+infos.pid).show();
			var chk = 0;
			showMapResult(false); //gis_result.js
			var layer = mapObj.getLayer("geoserverLayer");
			mapObj.chkTsUse = setInterval(function(){ 
				if(infos.ids.length == chk){
					tsPopStop(mapType, infos.pid); //시계열 중지
					showMapResult(true);
				}	
				else{
					if(!mapObj.chkLyrLoading){
						var label = "";
						var rowid = infos.ids[chk++]; //무조건 key로 지정된값으로 가져오니 key설정하지 말것
						var data = $("#"+infos.gridId).jqGrid('getRowData', rowid);
						var chkItem = data.item; 
						if(infos.id == '1-1-2')
							chkItem = data.age;
						if(chkItem != undefined && chkItem != '' && chkItem != '합계'){
							$('#'+infos.gridId).setSelection(rowid, false);
							if(infos.id == '1-1-1' || infos.id == '1-2-1' || infos.id == '1-4-1'){
								layer.attributes.params.gyear = data.item;
								var year =  chkItem.substring(0,4);
								var month = chkItem.substring(4,6);
								//std 날짜 / 월 여부 파악 후 데이터 표시 변경								
								if(chkItem.length == 8){
									var day = chkItem.substring(6,8);
									label = "<b>* 데이터 : "+year+"년 "+month+"월 "+day+"일</b>&nbsp;&nbsp;";
								}else
									label = "<b>* 데이터 : "+year+"년 "+month+"월</b>&nbsp;&nbsp;";								
							}else if(infos.id == '1-1-2'){
								layer.attributes.params.gyear = data.ym;
								layer.attributes.params.age = data.item;//선택 연령
								label = "<b>* 연령 : "+chkItem+"</b>&nbsp;&nbsp;";								
							}else if(infos.id == '1-4-2'){
								var mcnt = data.mcnt;
								var wcnt = data.wcnt;
								var gender = "all";
								if(mcnt > 0 && wcnt == 0)
									gender = "M";
								else if(wcnt > 0 && mcnt == 0)
									gender = "F";
								layer.attributes.params.gender = gender;            //성별
								layer.attributes.params.gyear = data.item; //연령대
								label = "<b>* 연령 : "+data.gb+"</b>&nbsp;&nbsp;";
							}else if(infos.id == '1-4-3'){
								layer.attributes.params.gyear = data.item;
								label = "<b>* 시간 : "+data.item+"시</b>&nbsp;&nbsp;";
							}else if(infos.id == '1-4-4'){
								layer.attributes.params.gyear = data.item;
								label = "<b>* 업종 : "+data.snm+"</b>&nbsp;&nbsp;";
							}else if(infos.id == '1-4-5'){
								
							}	
							
							var url = legendUrl.get(infos.id);
							if(url != undefined && url != null && url != ''){
								//legend 변경
								var legendInfo = getLegendInfo(url, layer.attributes.params); //범례표시 정보 (max, min)
								var lndParams = {};
								if(legendInfo != undefined && legendInfo != null){
									layer.attributes.params.max = legendInfo[0].max;
									layer.attributes.params.min = legendInfo[0].min;									
									lndParams.max = layer.attributes.params.max;
									lndParams.min = layer.attributes.params.min;
									if(infos.id.indexOf("1-4-") > -1)
										lndParams.vtype = "area";
									else
										lndParams.vtype = layer.attributes.params.vtype;
									lndParams.vcolor =layer.attributes.params.vcolor;
									showLegendInfo(lndParams); //common.js
								}
								setGridParams("tblGrid" + infos.id, layer.attributes.params, lndParams);
							}
							
							$('#plbl'+infos.id).html(label);
							$('#spanTsLabel'+infos.pid).html(label);

							console.log("layer.attributes.syear", layer.attributes.params.syear);
							console.log("layer.attributes.gyear", layer.attributes.params.gyear);
							console.log("infos.id", infos.id);
							
							if( infos.id == "1.1.1" || infos.id == "1-1-2" ) {
								addAnalsPopWmsLayer(layer.attributes);
							}
							else if( infos.id == "1-2-1" ) {
								addAnalsfPopWmsLayer(layer.attributes);
							}
							//var params = mapObj.mergeParams(mapType, layer.attributes.params);
							//if( layer.type != "geoserver"){
							//	layer.mergeNewParams(params);
							//}
						}
					}
				}
			}, 3000);
		}else
			openInfoPopup(true,"조회된 결과정보가 없습니다.");
	}else
		openInfoPopup(true,"조회된 결과정보가 없습니다.");
}

/**
 * 범례표시
 * @param max 최대값
 * @param vtype 타입
 * @param color 색상
 */
function showLegendInfo(lndParams){
	var max = lndParams.max;
	var min = lndParams.min;
	var vtype = lndParams.vtype;
	var color = lndParams.vcolor;	
	var cont = ""; //내용
	var legendColor = null;
	var height = 0;
	var width = 0;
	
	if(vtype == 'round'){
		//var div = Math.floor(max / 10);  //기존 최소값이 0부터 시작일 경우
		var div = Math.floor( (max-min) / 10); 
		/* 원 사이지
		cont += setLegendRadius((60*2), div, 10);
		cont += setLegendRadius((55*2), div, 9);
		cont += setLegendRadius((50*2), div, 8);
		cont += setLegendRadius((45*2), div, 7);
		cont += setLegendRadius((40*2), div, 6);
		cont += setLegendRadius((35*2), div, 5);
		cont += setLegendRadius((30*2), div, 4);
		cont += setLegendRadius((25*2), div, 3);
		cont += setLegendRadius((20*2), div, 2);
		cont += setLegendRadius((15*2), div, 1);
		*/
		//up 사이즈 (라벨 추가를 위해 사이즈를 좀 더 키움)
		cont += setLegendRadius((70*2), div, 10, min, max, color);
		cont += setLegendRadius((64*2), div, 9, min, max, color);
		cont += setLegendRadius((58*2), div, 8, min, max, color);
		cont += setLegendRadius((52*2), div, 7, min, max, color);
		cont += setLegendRadius((46*2), div, 6, min, max, color);
		cont += setLegendRadius((40*2), div, 5, min, max, color);
		cont += setLegendRadius((34*2), div, 4, min, max, color);
		cont += setLegendRadius((28*2), div, 3, min, max, color);
		cont += setLegendRadius((22*2), div, 2, min, max, color);
		cont += setLegendRadius((16*2), div, 1, min, max, color);
		width = 180;
		height = 175;
	}else{		
		if(color === 'red')
			legendColor = legendRed;
		else if(color == 'blue')
			legendColor = legendBlue;
		else if(color == 'green')
			legendColor = legendGreen;		
				
		if(vtype == 'heat'){
			//for(var i=0; i<legendColor.size()-1; i++){
			for(var i=(legendColor.size()-2); i>=0; i--){
				//cont += "<div style='overflow:hidden;border-top:1px solid #A6A6A6; border-right:1px solid #A6A6A6; width:30px; height:12px;'>";
				cont += "<div style='overflow:hidden;'>";
				cont += "<span style='border:1px solid #A6A6A6;width:30px; height:30px;background-color:"+legendColor.get(i)+"'>";
				cont += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>";
				if(i==legendColor.size()-2)
					cont += "<span><font size='2'>&nbsp;고</font></span></div>";
				else if(i == 0)
					cont += "<span><font size='2'>&nbsp;저</font></span></div>";
				else 
					cont += "<span>&nbsp;</span>";
				cont += "</div>";
			}
			//임시로 맨하단 라인 추가
			cont += "<div style='overflow:hidden; border-top:1px solid #A6A6A6; width:20px;'>";
			cont += "<span style='border:0px solid #A6A6A6;width:30px; height:10px;background-color:white'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>";			
			width = 56;
			height = 185;
		}else if(vtype == 'grid' || vtype == 'beehive' || vtype == 'area'){
			var size = legendColor.size()-2;
			var maxSize = 0;
			for(var i=1; i<=size; i++){				
				cont += "<div style='overflow:hidden;'><span style='border:1px solid #A6A6A6; width:30px; height:30px; background-color:"+legendColor.get(i)+"'>&nbsp;&nbsp;&nbsp;&nbsp;</span>";
				//var div = (max / size); //기존 최소값이 0부터일경우			
				var div = ( (max - min) / size);
				var range =  "";
				if(i == 1)
					range = Number(min.toFixed(0)).toLocaleString('en') + " ~ " + (Number(min.toFixed(0)) + Number( (div*(i)).toFixed(0) )).toLocaleString('en');
				else if(i == size)
					range = (Number(min.toFixed(0)) + Number( (div*(i-1)).toFixed(0) ) ).toLocaleString('en') + " ~ " + Number(max.toFixed(0)).toLocaleString('en');
				else
					range = (Number(min.toFixed(0)) + Number( (div*(i-1)).toFixed(0) ) ).toLocaleString('en') + " ~ " + (Number(min.toFixed(0)) + Number( (div*(i)).toFixed(0) )).toLocaleString('en');
				if(maxSize < range.length)
					maxSize = range.length;
				
				cont += "<span><font size='2'>&nbsp;" +range + "</font></span></div>";			
			}
			//width = ((max.toString().length * 2) * 11) + 40;
			width = (30 + (maxSize * 7)); //글자당 7
			height = 175;
		}else if(vtype == 'pass'){
			var size = 10;
			var maxSize = 0;
			for(var i=1; i<=size; i++){				
				cont += "<div style='overflow:hidden; height:22px;'><span style='position:absolute; width:20px; height:"+(i*1)+"px; background-color:blue;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>";
				//var div = (max / size); //기존 최소값이 0부터일경우			
				var div = ( (max - min) / size);
				var range =  "";
				if(i == 1)
					range = Number(min.toFixed(0)).toLocaleString('en') + " ~ " + (Number(min.toFixed(0)) + Number( (div*(i)).toFixed(0) )).toLocaleString('en');
				else if(i == size)
					range = (Number(min.toFixed(0)) + Number( (div*(i-1)).toFixed(0) ) ).toLocaleString('en') + " ~ " + Number(max.toFixed(0)).toLocaleString('en');
				else
					range = (Number(min.toFixed(0)) + Number( (div*(i-1)).toFixed(0) ) ).toLocaleString('en') + " ~ " + (Number(min.toFixed(0)) + Number( (div*(i)).toFixed(0) )).toLocaleString('en');
				if(maxSize < range.length)
					maxSize = range.length;
				cont += "<span style='position:absolute; left:32px;><font size='2'>&nbsp;"; 
				cont += range + "</font></span></div>";			
			}
			width = (45 + (maxSize * 7)); //글자당 7
			height = 253;
		}		
	}
	
	$('#divLegendCont').width(width);
	$('#divLegendCont').height(height);
	$('#divMapLegend').width(width);
	$('#divMapLegend').height(height);
	$('#divLegendCont').html(cont);
	$('#divMapLegend').show();
}

//범례 초기화
function clearLegendInfo(){
	$('#divMapLegend').hide();
	$('#divLegendCont').html("");
}

//radius 범례
function setLegendRadius(size, div, level, min, max, color){	
	var radius = 50;
	if(size > 100)
		radius  = Math.floor(size/2);

	var cont = "";
	cont += "<div style='background-color: #fff; border:1px solid "+color+"; width:"+size+"px; height:"+size+"px; ";
	cont += " position:absolute; left: 50%; transform:translate(-50%, 0); bottom:10px; ";
	cont += " -webkit-border-radius:"+radius+"px; -moz-border-radius:"+radius+"px; border-radius:"+radius+"px;'>";	
	var label = "";
	if(level == 1){
		label = Number(min).toLocaleString('en') + "~"+ (Number(min) + Number(div * level)).toLocaleString('en');
	}else if(level == 10){ //사용안함
		label = (Number(min) + Number(div * (level-1))).toLocaleString('en')+"~"+ Number(max).toLocaleString('en');
	}else{
		label = (Number(min) + Number(div * (level-1))).toLocaleString('en')+"~"+ (Number(min) + Number(div * level)).toLocaleString('en');
	}
	var loc = Math.floor((size/2)- (label.length/2)) - (2 * label.length); //width-length (4*level)	
	cont += "<span style='position:absolute;top: 1px; left:"+ loc +"px;'>";	
	cont += "<font size=1>" + label + "</font></span></div>";
	return cont;
}

/**
 * 엑셀내보내기
 */
function exportExcel(flag){
	//우선 동의체크 확인
	if($('#rdExcelAgree')[0].checked){
		//1. 활용목적 가져오기
		var purpose = $(":input:radio[name=rdExcelChk]:checked").val();
		if(purpose == 'etc'){
			purpose = $('#txtExcelEtc').val();
			if(purpose != ''){
				purpose = "기타 미입력";
			}
		}
		var chkRead = 'jqgrid'; //grid 출력방식 (직접, db)
		var fileName = $('#txtExcelName').val();
		if(fileName != null && fileName != ''){	
			openExcelPopup(false);
			$('#divMapLoading').show();
			//지도이미지를 먼저 생성후 지도이미지 chart모두 export
			//ie 10이상
			var id = getMenuId();
			var url = '';
			if(id == '1-1-1' || id == '1-1-2' || id == "1-1-3" || id == "1-1-4")
				url = '/gis/pop/popExcel.do';
			else if(id == '1-4-1' || id == '1-4-2' || id == '1-4-3'	|| id == '1-4-4' || id == '1-4-5')
				url = '/gis/sale/saleExcel.do';
			else if(id == '1-5-1' || id == '1-5-2' || id == '1-5-3')
				url = '/gis/sale/iSaleExcel.do';
			else if(id == '1-2-1' || id == '1-2-2' || id == '1-2-3')
				url = '/gis/fpop/fPopExcel.do';			
			else if(id == '1-3-1' || id == '1-3-2' || id == '1-3-3')
				url = '/gis/ipop/iPopExcel.do';
			else if(id == "2-1-1"){
				chkRead = 'db';
				url = '/gis/traffic/blindExcel.do'; //사각지대
			}else if(id == '2-2-1'){
				url = '/gis/traffic/busExcel.do'; //저상버스 도입요구
			}else if(id == '2-3-1'){
				url = '/gis/traffic/cardExcel.do'; //교통카드현황
			}else if(id == '2-4-1'){
				url = '/gis/traffic/facExcel.do'; //환승편의시설
			}else if(id == '2-5-1' || id == '2-5-2'){
				url = '/gis/traffic/arrExcel.do'; //탄력배차
			}else if(id == '2-7-1'){
				url = '/gis/traffic/passExcel.do'; //통행패턴
			}	
			
			//layer여부 체크
			html2canvas($('#gis-area')[0], {		
		        useCORS: true,
		        proxy : '/gis/comm/proxy.do',
		        async: true
		    }).then(function (canvas) {
		    	var mapImg = canvas.toDataURL();
				
				var $iframe = $('<iframe>', {
			        id: 'excelDownIfrm',
			        name: 'excelDownIfrm'
			    }).css({
			        'display': 'none'
			    });

			    var $form = $('<form>', {
			        method: 'post',
			        action: url,
			        target: 'excelDownIfrm'
			    }).css({
			        'display': 'none'
			    });
			    
			    var chartImg = "";
			    if(id == "2-1-1" || id == "2-2-1"  || 
			    	id == '2-4-1' || id == '2-5-1' || id == '2-5-2')
			    	chartImg = '';
			    else
			    	chartImg = $('#divChart'+id).jqplotToImageStr({});
			    
			    
			    var $chart =    $('<input>',  {	value: chartImg, name: 'chart'});
			    var $map   =    $('<input>',  {	value: mapImg,   name: 'map'});
			    var $id    =    $('<input>',  {	value: id,       name: 'eid'});
			    var $purpose =  $('<input>',  {	value: decodeURIComponent(purpose),  name: 'pps'});			    
			    var $fname =    $('<input>',  { value: decodeURIComponent(fileName), name: 'fnm'});
			    
			    var tmpId = id; //임시 id저장
			    if(id == '1-1-3' || id == '1-1-4'){
			    	id += "-1";
			    }			    			    	
			    var gisParams = $('#tblGrid' + id)[0].gisparams;
			    //기존 db에서jqgrid 직접읽어서 가져오기
			    if(gisParams != undefined && gisParams != null && gisParams != ''){			    	
					
					if(chkRead == 'db'){ //db에서 읽어오기
						var params = $.param(gisParams);	    
					    var arrParams = params.split("&");
					    for(var i=0; i<arrParams.length; i++){
					    	if(arrParams[i] != undefined && arrParams[i].indexOf("=") > -1){	    		
					    		var tmpParam = arrParams[i].split("=");
						    	$form.append($('<input>', {	value: tmpParam[1],   name: tmpParam[0]}));	
					    	}
					    }
					}else{ //jqgrid에서 직접처리
						var params = getExcelParams(id);			    	
				    	$form.append($('<input>', {	value: params,   name: 'exparams'}));
				    	//예외사항 : 결과리스트가 2개인 메뉴
				    	if(id == '1-1-3-1' || id == '1-1-4-1'){
				    		params = getExcelParams(tmpId+"-2");
				    		$form.append($('<input>', {	value: params,   name: 'exparams1'}));
				    	}	
					}
					//교통_통행패턴은 sheet명이 고정값이 아닌 능동형
					if(id == '2-7-1'){
						$form.append($('<input>', {	value: decodeURIComponent($('#spn2-7-1').html()),   name: 'title'}));
					}			    	
				    $form.append($chart);
				    $form.append($id);				    
				    $form.append($map);
				    $form.append($purpose);
				    $form.append($fname);
				    
				    $('body').append($iframe);
				    $('body').append($form);

				    $iframe.load(function(){
				        $form.remove();
				        $iframe.remove();
				    });
				    $form.submit();	 	
				    //1.5초 후 제거
				    var chkDelay = null;
				    chkDelay = setInterval(function(){
				    	$('#divMapLoading').hide();				    	
				    	clearInterval(chkDelay);
				    }, 1500);
			    }else{
			    	$('#divMapLoading').hide();
			    	openInfoPopup(true,"검색결과가 없습니다.");
			    	openExcelPopup(false);
			    } 
		    });	
		}else{			
			openInfoPopup(true,"파일명을 등록하여 주시기 바랍니다.");
		}
	}else{		
		openInfoPopup(true,"활용 및 보안동의 체크는 필수사항입니다.");
	}	
}

/**
 * rgb to hex
 * @param rgb
 * @returns
 */
function rgb2hex(rgb){
	 rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
	 return "#" +
	  ("0" + parseInt(rgb[1],10).toString(16)).slice(-2) +
	  ("0" + parseInt(rgb[2],10).toString(16)).slice(-2) +
	  ("0" + parseInt(rgb[3],10).toString(16)).slice(-2);
}

/**
 * 하단 tab result down 
 * @param id
 */
function clearResultTab(id, pid){	
	if(!$('.btn-fold').hasClass("on"))
		$('#btnFold'+pid).trigger('click');
	$('#t'+id).hide();
}


/**
 * 간략명칭으로 변경
 * @param name
 */
function replaceSigName(name){
	//제거하려는 시명이 최소 3자리이기때문에 시군구명은 최소 3자리이상
	if(name.length > 3){
		for(var i=0; i<removeSiInfo.length; i++){		
			name = name.replace(removeSiInfo[i],"");
		}	
	}
	return name;
}

/**
 * 버스타입 정보
 */
function selectBusTypeList(selectId, mapType){
	$('#'+selectId).empty();
	$.ajax({
		type:"POST",
		url:"/gis/comm/busType.do",
		data:null,
		dataType:'json',		
		success:function(data){
			if(data != undefined && data != null && data != ''){
				if(data.result.length > 0){
					var obj = data.result;
					for(var i=0; i<obj.length; i++){
						$('#'+selectId).append("<option value='"+obj[i].btype+"'>"+obj[i].btype+"</option>");
						if(i == 0){
							$('#'+selectId).trigger("change");
						}
					}
					//selectDongList("selDong", mapType); //읍면동 리스트 가져오기
				}else{
					openInfoPopup(true,"버스타입애 대한 조회결과가 없습니다.");
				}
			}else{
				openInfoPopup(true,"버스타입에 대한 조회결과가 없습니다.");
			}
		},
		error:function(err){
			openInfoPopup(true,"버스타입 조회 중 에러가 발생하였습니다.");
		}
	});
}

/**
 * 버스타입별 라인정보
 */
function selectBusList(selectId, mapType){
	$('#'+selectId).empty();
	$.ajax({
		type:"POST",
		url:"/gis/comm/busLine.do",
		data:{btype: decodeURIComponent($("#selBusType").val())},
		dataType:'json',		
		success:function(data){
			if(data != undefined && data != null && data != ''){
				if(data.result.length > 0){
					var obj = data.result;
					if(obj.length > 0){
						$('#'+selectId).append("<li style='display:none;'><input style='display:none;' type='checkbox' name='tmp' value=''/>&nbsp;</li>");
						for(var i=0; i<obj.length; i++){
							$('#'+selectId).append("<li><input type='checkbox' name='multibus' value='"+obj[i].lid+"'/>&nbsp;&nbsp;"+obj[i].bnm+"</li>");
						}	
						$('#multiCheckedbox').dropdownMultiCheckbox("multibus");
						// getSelectAreas(mapType);
					}										
				}else{
					openInfoPopup(true,"버스노선 조회결과가 없습니다.");
				}
			}else{
				openInfoPopup(true,"버스노선 조회결과가 없습니다.");
			}
		},
		error:function(err){
			openInfoPopup(true,"버스노선 조회 중 에러가 발생하였습니다.");
		}
	});
}

//시계열분석 버튼 view
function openTsBtn(start, stop, id){
	var pid = id.split('-');
	if(pid.length == 3)	pid = pid[0] + "-" + pid[1];
	if(start)
		$('#btnTsStart'+pid).show();
	else
		$('#btnTsStart'+pid).hide();
	
	if(stop)
		$('#btnTsStop'+pid).show();
	else
		$('#btnTsStop'+pid).hide();
}

/**
 * 기간설정정보 가져오기
 * @param menu id
 */
function getTablePeriod(id){
	var html = "";
	$('#lblPeriod').html("");
	$.ajax({
		type:"POST",
		url:"/gis/comm/tblPeriod.do",
		data:{cd:id},
		dataType:'json',
		async: true,
		success:function(data){			
			if(data.result.length > 0){
				html += "<font size='1'>&nbsp;&nbsp;&nbsp;(";
				for(var i=0; i<data.result.length; i++){
					var obj = data.result[i];
					html += obj.unit;
					if((i < data.result.length-1) && (data.result.length > 1))
						html += " ~ ";
				}
				html += ")</font>";
				$('#lblPeriod').html(html);
				var len = '';
				
				if(data.result.length == 1){
					len = 0;
				}else{
					len = 1;
					// 인구 기간 분석시만 기준년월 조회
					// 각 기간 분석시 기준년월 값 삽입 추가
					if(data.commVO.cd == "1-1-1"){
						getRecentPeriod(data.result[len].unit, data.commVO.cd)
					}
				}
			}
		}
	});	
}

/**
 * 최근 기준년월 가져오기
 * @param table unit
 */
function getRecentPeriod(unit, id){
	$.ajax({
		type:"POST",
		url:"/gis/comm/recentPeriod.do",
		data:{std:unit, cd:id},//{tbl:'hsp_rsgst_popltn_p_'+id},
		dataType:'json',
		async: true,
		success:function(data){			
			if(data.result.length > 0){
				// 인구분석 기간설정
				$('#txtBasicDate').val(data.result[0].stdYm.substr(0, 4) + "-" + data.result[0].stdYm.substr(4, 6));
				setCalendarMonth('txtBasicDate'); //common.js	
			}
		}
	});	
}

//search height 설정 (scroll생성)
function setContHeight(){
	$(".lnb-cont").height($(window).height() - (35+63));
}
