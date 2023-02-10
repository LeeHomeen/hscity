		$(function(){
			// 우메뉴 열기&닫기
			$(".navBtn").click(function(){
				$(".sideNav").toggleClass("show");
				var src = $(this).attr('src');
				var newsrc = (src=='/images/biz/menu.png') ? '/images/biz/menu_close.png' : '/images/biz/menu.png';
				$(this).attr('src', newsrc );
			});
			//좌메뉴 열기&닫기
			$(".cateBtn").click(function(){
				$(".cateWrap").toggleClass("fold");
				var src = $(this).attr('src');
				var newsrc = (src=='/images/biz/btncate_close.png') ? '/images/biz/btncate.png' : '/images/biz/btncate_close.png';
				$(this).attr('src', newsrc );
			});
			// 패밀리사이트
			$(".familyWrap h3").click(function(){
				$(".familyWrap ul").toggle();
			});
			// 트리메뉴
			$(".cateWrap").height($('.contentWrap').outerHeight()-20);
			$("#treemenu").accordion({active: false, collapsible: true, heightStyle: "content"});
			$("#treemenu > div > ul").simpleTree({startCollapsed: false});
			// 모달 띄우기
			$('.poplink').on('click', function(e){
				e.preventDefault();
				$($(this).attr('href')).bPopup();
				$($(this).attr('poplink')).bPopup();
			});
			//즐겨찾기 추가
			$('.favorite').click(function(){
				$(this).toggleClass('on');
			})
		})