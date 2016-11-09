function divfloat(content, width, height, node) {
	if (node == null) {
		$('body').append("<div class='divfloat'></div>");
	} else {
		$(node).append("<div class='divfloat'></div>");
	}
	$(".divfloat").css({
		'width' : width + 'px',
		'height' : height + 'px',
		'position' : 'fixed',
		'left' : '50%',
		'top' : '50%',
		'z-index' : 9999,
		'margin' : '-' + height / 2 + 'px -100px 0 ' + '-' + width / 2 + 'px',
		'background-color' : 'grey',
		'color' : '#fff',
		'font-size' : '18px',
		'text-align' : 'center',
		'line-height' : height + 'px'
	});
	$(".divfloat").html(content);
	$(".divfloat").fadeIn(150).delay(2000).fadeOut(300);
}
