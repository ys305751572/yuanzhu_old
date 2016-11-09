jQuery.extend({
	createUploadForm : function(id, setting) {
		var fileElementId = setting.fileElementId;
		var action = setting.url;
		var target = setting.frameId;
		var formId = 'jUploadForm' + id;
		var fileId = 'jUploadFile' + id;
		var form = $('<form></form>').attr({
			action : action,
			method : "POST",
			name : formId,
			id : formId,
			target : target,
			encoding : "multipart/form-data",
			enctype : "multipart/form-data"
		}).appendTo('body').hide();
		
		if(typeof(fileElementId) == 'string'){  
		    fileElementId = [fileElementId];  
		}  
		for(var i in fileElementId){  
		    var oldElement = jQuery('#' + fileElementId[i]);  
		    var newElement = jQuery(oldElement).clone();  
		    jQuery(oldElement).attr('id', fileId);  
		    jQuery(oldElement).before(newElement);  
		    jQuery(oldElement).appendTo(form);  
		}  
		
		var params = setting.param;
		if(params != null){
			for(var name in params){
				$("<input />").attr({type:'hidden',name:name,value:params[name]}).appendTo(form);
			}
		}
		return form;
	},
	ajaxFileUpload : function(s) {
		s = jQuery.extend({}, jQuery.ajaxSettings, s);
		var id = new Date().getTime();
		var form = jQuery.createUploadForm(id,s);
		var io = document.getElementById(s.frameId);
		if (s.global && !jQuery.active++) {
			jQuery.event.trigger("ajaxStart");
		}
		var xml = {};
		if (s.global)
			jQuery.event.trigger("ajaxSend", [ xml, s ]);
		var uploadCallback = function() {
			try {
				if (io.contentWindow) {
					xml.responseText = io.contentWindow.document.body ? io.contentWindow.document.body.innerHTML : null;
					xml.responseXML = io.contentWindow.document.XMLDocument ? io.contentWindow.document.XMLDocument : io.contentWindow.document;
				} else if (io.contentDocument) {
					xml.responseText = io.contentDocument.document.body ? io.contentDocument.document.body.innerHTML : null;
					xml.responseXML = io.contentDocument.document.XMLDocument ? io.contentDocument.document.XMLDocument : io.contentDocument.document;
				}
			} catch (e) {
				
			}
			console.log("====:" + xml.responseText);
			if (xml) {
				var data = eval('(' + xml.responseText + ')');
				if (s.success)
					s.success(data);
				if (s.global)
					jQuery.event.trigger("ajaxSuccess", [ xml, s ]);
				if (s.global)
					jQuery.event.trigger("ajaxComplete", [ xml, s ]);
				if (s.global && !--jQuery.active)
					jQuery.event.trigger("ajaxStop");
				jQuery(io).unbind();
				setTimeout(function() {form.remove();}, 100);
				xml = null;
			}
		};
		if (window.attachEvent) {
			io.attachEvent('onload', uploadCallback);
		} else {
			io.addEventListener('load', uploadCallback, false);
		}
		form.submit();
	}
});
