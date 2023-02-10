jQuery(function($){
	// Tree Navigation
	var tree = $('.tree');
	var treePlus = '\<button type=\"button\" class=\"treeToggle plus\"\>+\<\/button\>';
	var treeMinus = '\<button type=\"button\" class=\"treeToggle minus\"\>-\<\/button\>';
	tree.find('li>ul').css('display','none');
	tree.find('ul>li:last-child').addClass('tree_last');
	tree.find('li>ul:hidden').parent('li').prepend(treePlus);
	tree.find('li>ul:visible').parent('li').prepend(treeMinus);
	tree.find('li.tree_clicked').addClass('tree_open').parents('li').addClass('tree_open');
	tree.find('li.tree_open').parents('li').addClass('tree_open');
	tree.find('li.tree_open>.treeToggle').text('-').removeClass('plus').addClass('minus');
	tree.find('li.tree_open>ul').slideDown(100);
	$('.tree .treeToggle').click(function(){
		t = $(this);
		t.parent('li').toggleClass('tree_open');
		if(t.parent('li').hasClass('tree_open')){
			t.text('-').removeClass('plus').addClass('minus');
			t.parent('li').find('>ul').slideDown(100);
		} else {
			t.text('+').removeClass('minus').addClass('plus');
			t.parent('li').find('>ul').slideUp(100);
		}
		return false;
	});
	$('.tree .click_open').click(function(){
		t = $(this);
		t.parent('li').toggleClass('tree_open');
		if(t.parent('li').hasClass('tree_open')){
			t.prev('button.treeToggle').text('-').removeClass('plus').addClass('minus');
			t.parent('li').find('>ul').slideDown(100);
		} else {
			t.prev('button.treeToggle').text('+').removeClass('minus').addClass('plus');
			t.parent('li').find('>ul').slideUp(100);
		}
		return false;
	});
});