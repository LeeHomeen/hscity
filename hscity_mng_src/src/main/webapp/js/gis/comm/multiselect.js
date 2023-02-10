$.extend($.fn, {
	dropdownMultiCheckbox: function(domName){
		var name = "multiarea";
		if(domName != undefined && domName != null && domName != '')
			name = domName;		
		
		if(topParent == null){ //storage.js (event.js clkMnItem에서 초기화)
			topParent = this;
			//박스 클릭시			
			$('body').mousedown(function(e){
				$('.dropdownBox').each(function(){
					if($(this).find("dd").css("display") == 'block'){
						if(!$(this).has(e.target).length){
							$(this).find("dd").hide();
						}
					}
				});
			});	
			
			this.find("dt").on("click", function(){
				$(".dropdownBox dt").not(this).nextAll().filter("dd").hide();
				$(this).nextAll().filter("dd").slideToggle("fast");
			});
		}	
	
		//this.find("dd>ul").prepend("<li style='display:none;'><input type='checkbox' class='allmultichk'/>전체선택</li>");
		//this.find("dd>ul").prepend("<li style=''></li>");			
		this.setCheckedCnt(name);
		
		/*		
		this.find(".allmultichk").on("click", function(e){
			var chk = $(this).is(":checked");
			if(chk){
				$(this).parent().nextAll().find("input[name='multiarea']").attr("checked", true);
			}else{
				$(this).parent().nextAll().find("input[name='multiarea']").attr("checked", false);
			}
			topParent.setCheckedCnt();
		});
		*/
		
		this.find("input[name='"+name+"']").on('click', function(e){
			//특정 메뉴에서는 1개만 선택되도록 변경			
			topParent.setCheckedCnt(name);
			if(name.indexOf("cst") > -1)
				getSelectCstAreas('pop');
			else
				getSelectAreas('pop');
		});		
	},
	
	setCheckedCnt: function(name){
		var chk_cnt = this.find("dd input[name='"+name+"']:checked").length;
		var all_cnt = this.find("dd input[name='"+name+"']").length;
		if(chk_cnt == all_cnt){
			this.find(".multiCheckValues").html("전체 선택");
			//this.find("input[name='multiarea']").attr('checked',true);
		}else{
			this.find(".multiCheckValues").html("선택"+chk_cnt+"개 /총 "+ all_cnt+"개");
			//this.find("input[name='multiarea']").attr('checked',false);
		}
	}
});