// JavaScript Document
//渐隐渐现	
$(function() {
	var timer, timer2;
	var len = $(".inout .num li").length;
	var index = 0;
	$(".inout .content li").first().show();
	$(".inout").hover(function() {
		clearInterval(timer);
	}, function() {
		timer = setInterval(function() {
			if (index >= len - 1) {
				index = -1
			};
			index++;
			auto(index);
		}, 3600);
	}).trigger("mouseleave");

	$(".inout .num li").hover(function() {
		index = $(".inout .num li").index(this);
		timer2 = setTimeout(function() {
			auto(index);
		}, 120);
	}, function() {
		clearTimeout(timer2);
	});

	function auto(index) {
		$(".inout .num li").eq(index).addClass("on").siblings().removeClass("on");
		$(".inout .content li").eq(index).fadeIn("200").siblings().fadeOut("200");
		return false;
	}
});


$(function(){
	
	$(document).on('click','.product_list dd,.product_list2 dd',function(){
		$('.product_list dd,.product_list2 dd').removeClass('on');
		$(this).toggleClass('on');
		
		});
		
		$(document).on('click','.nav_list li a',function(){
		$('.nav_list li a').removeClass('ton');
		$(this).toggleClass('ton');
		
		});	
		
	$(document).on('click','.s_list li ',function(){
		$('.s_list li').removeClass('tt-red');
		$(this).toggleClass('tt-red');
		
		});	
	$(document).on('click','#product_list3 dd ',function(){
		$('#product_list3 dd').removeClass('green');
		$(this).toggleClass('green');
		
		});	
	
	})
	
	
//产品详情
$(function() {
	goods_slide();
	productScan();
	$(".jqzoom").jqueryzoom({
		xzoom: 365,
		yzoom: 365,
		offset: 10,
		position: "right",
		preload: 1,
		lens: 1
	});

})

function goods_slide() {
	var anum = $(".slidetrains ul li").length,
		awidth = $(".slidetrains ul li").outerWidth(true),
		mwidth = -(anum - 4) * awidth,
		aul = $(".slidetrains ul");
	aul.css("width", anum * awidth + "px");
	$("a#spec-backward").click(function(e) {
		if (!aul.is(":animated")) {
			var aleft = parseInt(aul.css("marginLeft"));
			if (anum > 4 && aleft > mwidth) {
				$('.spec-control').removeClass("disabled");
				aul.animate({
					marginLeft: "-=" + awidth + "px"
				}, 200);
			} else {
				$(this).addClass("disabled");
			}
		}
	});
	$("a#spec-forward").click(function(e) {
		if (!aul.is(":animated")) {
			var aleft = parseInt(aul.css("marginLeft"));
			if (aleft < 0 && anum > 4) {
				$('.spec-control').removeClass("disabled");
				aul.animate({
					marginLeft: "+=" + awidth + "px"
				}, 200);
			} else {
				$(this).addClass("disabled");
			}
		}
	})

}

function productScan() {
	$(".slidetrains li").each(function(index) {
		$(this).hover(function() {
			$('.goodsShow .thumbIMG li img').removeClass('on');
			var cSrc = $(this).children("img").attr("src")
			$(this).children("img").addClass('on');
			$(".mainpic").children("img").attr("src", cSrc);
			$(".mainpic").children("img").attr("jqimg", cSrc);
		})
	});
}

//jqzoom
(function($) {
	$.fn.jqueryzoom = function(options) {
		var settings = {
			xzoom: 400,
			yzoom: 400,
			offset: 10,
			position: "right",
			lens: 1,
			preload: 1
		};
		if (options) {
			$.extend(settings, options);
		}
		var noalt = '';
		$(this).hover(function() {
			var imageLeft = $(this).offset().left;
			var imageTop = $(this).offset().top;
			var imageWidth = $(this).children('img').get(0).offsetWidth;
			var imageHeight = $(this).children('img').get(0).offsetHeight;
			noalt = $(this).children("img").attr("alt");
			var bigimage = $(this).children("img").attr("src");
			$(this).children("img").attr("alt", '');
			if ($("div.zoomdiv").get().length == 0) {
				$(this).after("<div class='zoomdiv'><img class='bigimg' src='" + bigimage + "'/></div>");
				$(this).append("<div class='jqZoomPup'>&nbsp;</div>");
			}
			if (settings.position == "right") {
				if (imageLeft + imageWidth + settings.offset + settings.xzoom > screen.width) {
					leftpos = imageLeft - settings.offset - settings.xzoom;
				} else {
					leftpos = imageLeft + imageWidth + settings.offset;
				}
			} else {
				leftpos = imageLeft - settings.xzoom - settings.offset;
				if (leftpos < 0) {
					leftpos = imageLeft + imageWidth + settings.offset;
				}
			}
			$("div.zoomdiv").css({
				top: imageTop,
				left: leftpos
			});
			$("div.zoomdiv").width(settings.xzoom);
			$("div.zoomdiv").height(settings.yzoom);
			$("div.zoomdiv").show();
			if (!settings.lens) {
				$(this).css('cursor', 'crosshair');
			}
			$(document.body).mousemove(function(e) {
				mouse = new MouseEvent(e);
				var bigwidth = $(".bigimg").get(0).offsetWidth;
				var bigheight = $(".bigimg").get(0).offsetHeight;
				var scaley = 'x';
				var scalex = 'y';
				if (isNaN(scalex) | isNaN(scaley)) {
					var scalex = (bigwidth / imageWidth);
					var scaley = (bigheight / imageHeight);
					$("div.jqZoomPup").width((settings.xzoom) / (scalex * 1));
					$("div.jqZoomPup").height((settings.yzoom) / (scaley * 1));
					if (settings.lens) {
						$("div.jqZoomPup").css('visibility', 'visible');
					}
				}
				xpos = mouse.x - $("div.jqZoomPup").width() / 2 - imageLeft;
				ypos = mouse.y - $("div.jqZoomPup").height() / 2 - imageTop;
				if (settings.lens) {
					xpos = (mouse.x - $("div.jqZoomPup").width() / 2 < imageLeft) ? 0 : (mouse.x + $("div.jqZoomPup").width() / 2 > imageWidth + imageLeft) ? (imageWidth - $("div.jqZoomPup").width() - 2) : xpos;
					ypos = (mouse.y - $("div.jqZoomPup").height() / 2 < imageTop) ? 0 : (mouse.y + $("div.jqZoomPup").height() / 2 > imageHeight + imageTop) ? (imageHeight - $("div.jqZoomPup").height() - 2) : ypos;
				}
				if (settings.lens) {
					$("div.jqZoomPup").css({
						top: ypos,
						left: xpos
					});
				}
				scrolly = ypos;
				$("div.zoomdiv").get(0).scrollTop = scrolly * scaley;
				scrollx = xpos;
				$("div.zoomdiv").get(0).scrollLeft = (scrollx) * scalex;
			});
		}, function() {
			$(this).children("img").attr("alt", noalt);
			$(document.body).unbind("mousemove");
			if (settings.lens) {
				$("div.jqZoomPup").remove();
			}
			$("div.zoomdiv").remove();
		});
		count = 0;
		if (settings.preload) {
			$('body').append("<div style='display:none;' class='jqPreload" + count + "'></div>");
			$(this).each(function() {
				var imagetopreload = $(this).children("img").attr("src");
				var content = jQuery('div.jqPreload' + count + '').html();
				jQuery('div.jqPreload' + count + '').html(content + '<img src=\"' + imagetopreload + '\">');
			});
		}
	}
})(jQuery);

function MouseEvent(e) {
	this.x = e.pageX;
	this.y = e.pageY;
}
	
//弹出

$(function(){
	$('.input_span').click(function(){
//		alert('ok')
		if($(this).hasClass('close_ss')==false){
			$('.slowup').show();	
			$(this).addClass('close_ss');
		}else{
			$('.slowup').hide();	
			$(this).removeClass('close_ss');
		}

	});	
	
});

// 选中

$(function(){
	var father=$('.input_span');
	$('.slowup li').click(function(){
			father.html($(this).html());
			$('.input_span').removeClass('close_ss');
			$('.slowup').hide();
	});
	
	
});
