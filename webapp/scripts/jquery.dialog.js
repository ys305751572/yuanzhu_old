/*!
Jquery.dialog.js (v1.0)
http://www.google.com
mailto:anymajoy@gmail.com

Copyright (c) 2013 Any Ma
Dual licensed under the MIT and GPL licenses.
*/

(function($){
	$.fn.dialog = function(options) {
        try {
            var defaults = $.fn.dialog.defaults;
            var o = $.extend({}, defaults, options);
            
            for (var prop in o) {
                if (defaults[prop] === undefined) throw ('Invalid option specified: ' + prop + '\nPlease check your spelling and try again.');
            };
            
            var self = $('<div></div>').addClass('dialog-conts');
            $("<div></div>").addClass("dialog-bg").appendTo(self);
            var content = $("<div></div>").addClass("dialog-cont").appendTo(self);
            $("<div></div>").addClass("title").html(o.title).appendTo(content);
            $("<div></div>").addClass("dialog-content").html(o.content).appendTo(content);
            $("<div></div>").addClass("close-btn").html(o.closeText).click(function(){
            	if(o.handler != null){
            		o.handler();
            	}
            	self.hide();
            }).appendTo(content);
            self.hide().appendTo('body');
            
            self.alert = function(){
            	this.show();
            };
           
            self.alert = function(content){
            	this.changeContent(content);
            	this.show();
            };
            
            self.alert = function(content, handler){
            	o.handler = handler;
            	this.changeContent(content);
            	this.show();
            };
            
            self.changeTitle = function(title){
            	this.find('.title').html(title);
            };
            
            self.changeContent = function(content){
            	this.find('.dialog-content').html(content);
            };
            
            self.changeBtn = function(text){
            	this.find('.close-btn').html(text);
            };
            
            return self;
        } catch (ex) {
            if (typeof ex === 'object') alert(ex.message); else alert(ex);
        };
    };
    
	$.fn.dialog.defaults = {
		handler : null,
		title : '提示',
		content : '',
		closeText : '关闭'
	};
})(jQuery);