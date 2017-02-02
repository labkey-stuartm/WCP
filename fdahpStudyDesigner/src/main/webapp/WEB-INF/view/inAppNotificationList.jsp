<script type="text/javascript">
var isAvilableList = true;
var eofList = false;  
var csrfDetcsrfParamName = "";
var csrfToken = "";
 $(document).ready(function() {
  	csrfDetcsrfParamName = $('#csrfDet').attr('csrfParamName');
	 csrfToken = $('#csrfDet').attr('csrfToken');
   	getNotificationListPage();
   	count = 0;
// 	$(window).scroll(function() {
// 	   if($(window).scrollTop() + $(window).height() == $(document).height() && !eofList && isAvilableList) {
// 	      getLazyNotificationListPage($('#notiListPage a').length);
// 	   }
// 	});
	$('#oldNoti').click(function() {
		if(!eofList && isAvilableList) {
			getLazyNotificationListPage($('#notiListPage a').not('#notiListPage').length);
		}
	});
 });
	 /* var getNotificationCount = function() {
	    var unseenCount = 0;
			 $.ajax({	            	
	 	            url: "checkNotificationCount.do",
	 	            type: "GET",
	 	            datatype: "json",
		 	        success:function(data){
						var jsonobject = eval(data);			                       
						var message = jsonobject.message;
						var jArray = jsonobject.notificationArry;
						unseenCount = jsonobject.unseenCount;
					},
					global : false
				
				});
				return $('#notiCount').text();
	} */
	var getNotificationListPage = function() {
		 $('.loaderNotiList').show();
		// var totalHeight = $(window).outerWidth(true);
		/*  var fullHeight = screen.height-($('.topHeadBg').height() + $('.footerHight').height() + $('.notBoxHeight').height()) - $('#spinDivNotiList').height();
		 var fullHeightprv = screen.height - (111 + 101 + 57) */
		 var noNot = "10";
		
		 $.ajax({	            	
 	            url: "/acuityLink/notification/viewNotificaion.do?"+csrfDetcsrfParamName+"="+csrfToken,
 	            type: "POST",
 	            datatype: "json",
 	            data:{
 	            	lazyNotification : noNot,
 	            	"${_csrf.parameterName}":"${_csrf.token}",
 	            },
	 	        success:function(data){
					var jsonobject = eval(data);			                       
					var message = jsonobject.message;
					var jArray = jsonobject.notificationArry;
					var unseenCount = jsonobject.unseenCount;
					var notiTextStart='<a class="content" href="#"><div class="notification-item inActiveProp"><p class="item-info mar0">';
					var notiTextEnd='</p></div></a>';
					var notificationDiv = '';
					eofList = jsonobject.eof;
					if(!eofList){
						eofList = false;
					}
					if(jArray.length > 0){
// 						$.each(jArray, function(key,value) {
// 						  //alert(value.notificationSubject);
// 						  	var notiTextStart='<a class="content" href="#"><div class="notification-item';
// 						  	if(value.userViewed == 'N'){
// 						  		 notiTextStart = notiTextStart + ' inActiveProp';
// 						  	}
// 						  	notiTextStart = notiTextStart + '"><p class="item-info mar0">';
// 							var notiTextEnd='</p></div></a>';
// 							notificationDiv = notificationDiv+notiTextStart + value.notificationSubject;
// 							notificationDiv = notificationDiv + notiTextEnd;
// 						});
						notificationDiv = genarateNotification(jArray);
					} else {
						var notiTextStart='<div class="notification-item emptyNotiList';
					  	
					  	notiTextStart = notiTextStart + '"><p class="item-info mar0 txtcenter">';
						var notiTextEnd='</p></div>';
						notificationDiv = notificationDiv+notiTextStart +'No Notifications';
						notificationDiv = notificationDiv + notiTextEnd;
					}
					//alert(notificationDiv);
					//$('#notiListPage').prepend(notificationDiv);
					$('#spinDivNotiList').before(notificationDiv);
				},
				global : false,
				complete : function() {
							$('.loaderNotiList').hide(); 
							isAvilableList = true;
							if(eofList) {
								$('#oldNoti').remove();
							}
						},
			
			}); 
	} 
	
	var getLazyNotificationListPage = function(notificationCount) {
		$('.loaderNotiList').show();
		isAvilableList = false;
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
					eofList = jsonobject.eof;
					if(!eofList){
						eofList = false;
					}
					var notificationDiv = "";
					if(jArray.length > 0){
						var notiTextStart='<a class="content" href="#"><div class="notification-item inActiveProp"><p class="item-info mar0">';
						var notiTextEnd='</p></div></a>';
// 						$.each(jArray, function(key,value) {
// 						  //alert(value.notificationSubject);
// 						  	var notiTextStart='<a class="content" href="#"><div class="notification-item';
// 						  	if(value.userViewed == 'N'){
// 						  		 notiTextStart = notiTextStart + ' inActiveProp';
// 						  	}
// 						  	notiTextStart = notiTextStart + '"><p class="item-info mar0">';
// 							var notiTextEnd='</p></div></a>';
// 							notificationDiv = notificationDiv+notiTextStart + value.notificationSubject;
// 							notificationDiv = notificationDiv + notiTextEnd;
// 						});
						notificationDiv = genarateNotification(jArray);
						//alert(notificationDiv);
						
					} 
					$('#spinDivNotiList').before(notificationDiv);
				},
				global : false,
				complete : function() {
							$('.loaderNotiList').hide(); 
							isAvilableList = true;
							if(eofList) {
								$('#oldNoti').remove();
							}
						},
			
			}); 
			
	}
	
	var getNewNotificationListPage = function() {
		//$('.loaderNotiList').show();
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
						notificationDiv = genarateNotification(jArray);
						//$('#spinDivNotiList').before(notificationDiv);
						$('#notiListPage').prepend(notificationDiv);
					}
					$('.emptyNotiList').remove();
				},
				global : false,
				//complete : function() {$('.emptyNotiList').remove();},
			
			}); 
	}

</script>

<!-- <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 pad0"> -->
  <div class="aq-wrapper" style="min-height: 507px;"><!--Wrapper-->
   <div class="col-lg-10 col-md-11 col-sm-12 col-xs-12 pad0 innerWrapper notBox innerWrapper notBoxHeight" >
   		<h3 class="">Notifications</h3>
   		
   		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 notBox notificationPage" id = "notiListPage">
	   		<div class="positionRelative notiLoad loaderNotiList" id="spinDivNotiList">
	   			<div class="sm-loader marauto"></div>
	   		</div>
	   		<a class="content loadOlder" id="oldNoti">
                    Load Older Notification
                </a>
   		</div>	
	</div>
  </div>
<!-- </div> -->