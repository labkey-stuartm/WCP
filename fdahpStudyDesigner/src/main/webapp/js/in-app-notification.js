var csrfDetcsrfParamName = "";
var csrfToken = "";
(function($){
	    $.fn.isActive = function(){
	        return $(this.get(0)).hasClass('open')
	    }
})(jQuery)
var isAvilable = true;
var eof = false;
 $(document).ready(function() {
	 csrfDetcsrfParamName = $('#csrfDet').attr('csrfParamName');
	 csrfToken = $('#csrfDet').attr('csrfToken');
	 var isUsed = false;
	$('#notiCount').hide();
 	var isOpend = 0;
 	$('#dropdownDivId').on('show.bs.dropdown',function(){
	    	if(isOpend == 0){
	    		getPopupList();
	    		isOpend ++ ;
	    	} else if(count > 0 && isOpend > 0){
	    		getNewNotification();
	    	}
	    	count = 0;
		});
	$('#displayedNotiId').on('scroll', function() {
        if($(this).scrollTop() + $(this).innerHeight() >= $(this)[0].scrollHeight && !eof && isAvilable) {
            getLazyPopupList($('#displayedNotiId a').length);
        }
	});
 });
	var getNotificationCount = function() {
	    var unseenCount = 0;
			 $.ajax({	            	
	 	            url: "/acuityLink/notification/checkNotificationCount.do?"+csrfDetcsrfParamName+"="+csrfToken,
	 	            type: "GET",
	 	            datatype: "json",
		 	        success:function(data){
						var jsonobject = eval(data);			                       
						var message = jsonobject.message;
						var jArray = jsonobject.notificationArry;
						unseenCount = jsonobject.unseenCount;
						if(unseenCount && unseenCount > 0){
							$('#notiCount').show();
							$('#notiCount').text(unseenCount);
						} else{
							$('#notiCount').text(0);
							$('#notiCount').hide();
						}
						
					},
					global : false
				
				});
				return $('#notiCount').text();
	}
	 var getPopupList = function() {
		 $('.loader123').show();
		 $.ajax({	            	
 	            url: "/acuityLink/notification/viewNotificaion.do?"+csrfDetcsrfParamName+"="+csrfToken,
 	            type: "GET",
 	            datatype: "json",
	 	        success:function(data){
					var jsonobject = eval(data);			                       
					var message = jsonobject.message;
					var jArray = jsonobject.notificationArry;
					var unseenCount = jsonobject.unseenCount;
					var notiTextStart='<a class="content" href="#"><div class="notification-item inActiveProp"><p class="item-info mar0">';
					var notiTextEnd='</p></div></a>';
					var notificationDiv = '';
					if(jArray.length > 0){
//						$.each(jArray, function(key,value) {
//						  //alert(value.notificationSubject);
//						  	var notiTextStart='<a class="content" href="#"><div class="notification-item';
//						  	if(value.userViewed == 'N'){
//						  		 notiTextStart = notiTextStart + ' inActiveProp';
//						  	}
//						  	notiTextStart = notiTextStart + '"><p class="item-info mar0">';
//							var notiTextEnd='</p></div></a>';
//							notificationDiv = notificationDiv+notiTextStart + value.notificationSubject;
//							notificationDiv = notificationDiv + notiTextEnd;
//						});
						notificationDiv = genarateNotification(jArray);
					} else {
						var notiTextStart='<div class="notification-item emptyNoti';
					  	
					  	notiTextStart = notiTextStart + '"><p class="item-info mar0 txtcenter">';
						var notiTextEnd='</p></div>';
						notificationDiv = notificationDiv+notiTextStart +'No Notifications';
						notificationDiv = notificationDiv + notiTextEnd;
					}
					//alert(notificationDiv);
					//$('#displayedNotiId').prepend(notificationDiv);
					$('#spinDiv').before(notificationDiv);
				},
				global : false,
				complete : function() {$('.loader123').hide();}
			
			}); 
	}
	
	var getLazyPopupList = function(notificationCount) {
		$('.loader123').show();
		isAvilable = false;
		 $.ajax({	            	
 	            url: "/acuityLink/notification/viewNotificaion.do?"+csrfDetcsrfParamName+"="+csrfToken,
 	            type: "POST",
 	            datatype: "json",
 	            data:{
 	            	notificationCount : notificationCount,
 	            	lazyNotification : '5',
 	            	"${_csrf.parameterName}":"${_csrf.token}",
 	            },
	 	        success:function(data){
					var jsonobject = eval(data);			                       
					var message = jsonobject.message;
					var jArray = jsonobject.notificationArry;
					var unseenCount = jsonobject.unseenCount;
					eof = jsonobject.eof;
					if(!eof){
						eof = false;
					}
					if(jArray){
						var notiTextStart='<a class="content" href="#"><div class="notification-item inActiveProp"><p class="item-info mar0">';
						var notiTextEnd='</p></div></a>';
						var notificationDiv = "";
//						$.each(jArray, function(key,value) {
//						  //alert(value.notificationSubject);
//						  	var notiTextStart='<a class="content" href="#"><div class="notification-item';
//						  	if(value.userViewed == 'N'){
//						  		 notiTextStart = notiTextStart + ' inActiveProp';
//						  	}
//						  	notiTextStart = notiTextStart + '"><label>'+value.notificationType+'</label><span class="timeNoti">2 hours ago</span><p class="item-info mar0">';
//							var notiTextEnd='</p></div></a>';
//							notificationDiv = notificationDiv+notiTextStart + value.notificationSubject;
//							notificationDiv = notificationDiv + notiTextEnd;
//						});
						notificationDiv = genarateNotification(jArray);
						//alert(notificationDiv);
						$('#spinDiv').before(notificationDiv);
					}
				},
				global : false,
				complete : function() {isUsed = true;$('.loader123').hide();isAvilable = true;},
			
			}); 
	}
	
	var getNewNotification = function() {
		//$('.loader123').show();
		 $.ajax({	            	
 	            url: "/acuityLink/notification/getNewNotificaion.do?"+csrfDetcsrfParamName+"="+csrfToken,
 	            type: "GET",
 	            datatype: "json",
	 	        success:function(data){
					var jsonobject = eval(data);			                       
					var message = jsonobject.message;
					var jArray = jsonobject.notificationArry;
					var unseenCount = jsonobject.unseenCount;
					if(jArray){
						var notiTextStart='<a class="content" href="#"><div class="notification-item inActiveProp"><p class="item-info mar0">';
						var notiTextEnd='</p></div></a>';
						var notificationDiv = "";
						if($('#displayedNotiId .emptyNoti').length > 0 ){
							$(this).remove();
						}
						/*$.each(jArray, function(key,value) {
						  //alert(value.notificationSubject);
						  	var notiTextStart='<a class="content" href="#"><div class="notification-item';
						  	if(value.userViewed == 'N'){
						  		 notiTextStart = notiTextStart + ' inActiveProp';
						  	}
						  	notiTextStart = notiTextStart + '"><p class="item-info mar0">';
							var notiTextEnd='</p></div></a>';
							notificationDiv = notificationDiv+notiTextStart + value.notificationSubject;
							notificationDiv = notificationDiv + notiTextEnd;
						});*/
						notificationDiv = genarateNotification(jArray);
						//alert(notificationDiv);
						//$('#spinDiv').before(notificationDiv);
						$('#displayedNotiId').prepend(notificationDiv);
						$('.emptyNoti').remove();
					}
				},
				global : false,
				//complete : function() {},
			
			}); 
	}
	var genarateNotification = function(jArray) {
		var notiTextStart='<a class="content" href="#"><div class="notification-item inActiveProp"><p class="item-info mar0">';
		var notiTextEnd='</p></div></a>';
		var notificationDiv = '';
		$.each(jArray, function(key,value) {
			var anchorTag = '<a class="content '+(value.userViewed == 'N'? 'inActiveProp' : '') + ((value.noficationUrl && value.noficationUrl != null) ?' "href="'+value.noficationUrl+'">':' poniter-none-with-cursor"href="">');
		  	var notiTextStart= anchorTag+'<div class="notification-item';
		  	/*if(value.userViewed == 'N'){
		  		 notiTextStart = notiTextStart + ' inActiveProp';
		  	}*/
		  	notiTextStart = notiTextStart + '"><label>'+value.notificationType+'</label><p class="item-info mar0">';
			var notiTextEnd='</p><span class="timeNoti n-txt">'+value.noficationDate+'</span></div></a>';
			notificationDiv = notificationDiv+notiTextStart + value.notificationSubject;
			notificationDiv = notificationDiv + notiTextEnd;
		});
		return notificationDiv;
	}