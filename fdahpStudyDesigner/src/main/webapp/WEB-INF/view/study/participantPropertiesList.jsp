<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
.table>thead:last-child>tr:last-child>th, .table>body:last-child>tr:last-child>td
	{
	text-align: center;
}

.no-border {
	border: none !important;
	margin: 0px !important;
}
</style>

<div class="col-sm-10 col-rc white-bg p-none">

	<!--  Start top tab section-->
	<div class="right-content-head">
		<div class="text-right">
			<div class="black-md-f text-uppercase dis-line pull-left line34">Participant
				Properties</div>
			<div class="dis-line form-group mb-none">
				<button type="button" class="btn btn-default gray-btn cancelBut">Cancel</button>
			</div>

			<c:if test="${empty permission}">
				<div class="dis-line form-group mb-none ml-sm">
					<span class="tool-tip" id="markAsTooltipId" data-toggle="tooltip"
						data-placement="bottom"
						<c:if test="${!markAsComplete}"> title="${activityMsg}" </c:if>>
						<button type="button" class="btn btn-primary blue-btn"
							id="markAsCompleteBtnId" onclick="markAsCompleted();"
							<c:if test="${!markAsComplete}"> disabled </c:if>>Mark
							as Completed</button>
					</span>
				</div>
			</c:if>
		</div>
	</div>
	<!--  End  top tab section-->

	<!--  Start body tab section -->
	<div class="right-content-body">
		<div class="table-responsive">
			<table id="participantProperties_list"
				class="display bor-none dragtbl dataTable no-footer">
				<thead>
					<tr>
						<th>SHORT TITLE</th>
						<th>DATA TYPE</th>
						<th><c:if test="${empty permission}">
								<div class="dis-line form-group mb-none">
									<button type="button" class="btn btn-primary blue-btn"
										onclick="addParticipantProperties();">+ Add Property</button>
								</div>
							</c:if></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${participantPropertiesList}"
						var="participantProperty">
						<tr>
							<td>${participantProperty.propertyName}</td>
							<td>${participantProperty.dataType}</td>
							<td>
								<%-- <span class="sprites_icon edit-g mr-lg"
								data-toggle="tooltip" data-placement="top" title="Edit"
								onclick="editQuestionnaires(${participantProperty.id});"></span> --%>
								<span class="sprites_icon preview-g mr-lg" data-toggle="tooltip"
								data-placement="top" title="View"
								onclick="viewQuestionnaires(${participantProperty.id});"></span>
								<span
								class="${participantProperty.completed?'edit-inc':'edit-inc-draft mr-md'} mr-lg 
								<c:if test="${not empty permission}"> cursor-none </c:if> 
								<c:if test="${not participantProperty.status}"> cursor-none </c:if>"
								data-toggle="tooltip" data-placement="top" title="Edit"
								onclick="editQuestionnaires(${participantProperty.id});"></span>
								<%-- <span class="sprites_icon copy delete" data-toggle="tooltip"
								data-placement="top" title="Delete"
								onclick="deleteQuestionnaire(${participantProperty.id});"></span> --%>
								<%-- <span class="ml-lg"> <label class="switch"
									data-toggle="tooltip" id="label${participantProperty.id}"
									data-placement="top"
									<c:if test="${participantProperty.status}">title="Active"</c:if>
									<c:if test="${not participantProperty.status}">title="Deactivated"</c:if>>
										<input type="checkbox" class="switch-input"
										value="${participantProperty.status ? 1 : 0}"
										id="${participantProperty.id}"
										<c:if test="${participantProperty.status}">checked</c:if>
										onchange="activateOrDeactivateParticipantProperty(${participantProperty.id})">
										<span class="switch-label" data-on="On" data-off="Off"></span>
										<span class="switch-handle"></span>
								</label>
							</span> --%>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<!--  End body tab section -->




</div>
<!-- End right Content here -->

<form:form
	action="/fdahpStudyDesigner/adminStudies/addParticipantProperties.do?_S=${param._S}"
	name="participantPropertiesForm" id="participantPropertiesForm"
	method="post">
	<input type="hidden" name="participantPropertiesId"
		id="participantPropertiesId" value="">
	<input type="hidden" name="actionType" value="add">
</form:form>

<form:form
	action="/fdahpStudyDesigner/adminStudies/editParticipantProperties.do?_S=${param._S}"
	name="editParticipantProperties" id="editParticipantProperties"
	method="post">
	<input type="hidden" name="actionType" id="actionType" value="">
	<input type="hidden" name="${csrf.parameterName}" value="${csrf.token}">
	<input type="hidden" name="participantPropertyId"
		id="participantPropertyId" value="">
	<input type="hidden" name="studyId" id="studyId" value="${studyId}" />
</form:form>
<script>
	$(document).ready(function() {
		$('[data-toggle="tooltip"]').tooltip();

		$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
			var a = $(".col-lc").height();
			var b = $(".col-rc").height();
			if (a > b) {
				$(".col-rc").css("height", a);
			} else {
				$(".col-rc").css("height", "auto");
			}
		});
		
		$(".menuNav li.active").removeClass('active');
	   	$(".menuNav li.sixth").addClass('active');
		
		$('#participantProperties_list').DataTable( {
            "paging":   true,
            "abColumns": [
              { "bSortable": true },
               { "bSortable": true },
               { "bSortable": true }
              ],
              "order": [],
            "info" : false, 
            "lengthChange": false, 
            "searching": false, 
            "pageLength": 10,
            "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
   	    	// $('td:eq(0)', nRow).addClass("dis-none");
   	      }
        } );
		if(document.getElementById("markAsCompleteBtnId") != null && document.getElementById("markAsCompleteBtnId").disabled){
     		$('[data-toggle="tooltip"]').tooltip();
     	}
	});
</script>


<script>
	$(document).ready(function() {

		//datatable icon toggle
		$("#user_list thead tr th").click(function() {
			$(this).children().removeAttr('class')
			$(this).siblings().children().removeAttr('class').addClass('sort');
			if ($(this).attr('class') == 'sorting_asc') {
				$(this).children().addClass('asc');
			} else if ($(this).attr('class') == 'sorting_desc') {
				$(this).children().addClass('desc');
			} else {
				$(this).children().addClass('sort');
			}
		});
	});

	function addParticipantProperties() {
		$("#participantPropertiesId").val('');
		$("#participantPropertiesForm").submit();
	}
	
	function viewQuestionnaires(participantPropertyId){
		if(participantPropertyId != null && participantPropertyId != '' && typeof participantPropertyId !='undefined'){
			$("#actionType").val('view');
			$("#participantPropertyId").val(participantPropertyId);
			$("#editParticipantProperties").submit();
	    }
	  }

	function editQuestionnaires(participantPropertyId) {
		if(participantPropertyId != null && participantPropertyId != '' && typeof participantPropertyId !='undefined'){
			$("#actionType").val('edit');
			$("#participantPropertyId").val(participantPropertyId);
			$("#editParticipantProperties").submit();
		}
	}
	
	function activateOrDeactivateParticipantProperty(participantPropertyId){
		var status = $('#'+participantPropertyId).val();
		var msgPart = "";
		if("0" == status){
			msgPart = "activate";
		} else if("1" == status){
			msgPart = "deactivate";
		}
	  	 bootbox.confirm("Are you sure you want to " + msgPart + " this Participant Property?", function(result){
			 if(result){
				$.ajax({
					url : "/fdahpStudyDesigner/adminUsersEdit/activateOrDeactivateParticipantProperty.do",
					type : "POST",
					datatype : "json",
					data : {
						participantPropertyId : participantPropertyId,
						participantPropertyStatus : status,
						"${_csrf.parameterName}":"${_csrf.token}"
					},
					success : function(data) {
						var jsonObj = eval(data);
						var message = jsonObj.message;
						if(message == 'SUCCESS'){
							if(status == 1){
								showSucMsg('Participant Property successfully deactivated.');
								$('#'+participantPropertyId).val("0");
								$('#label'+participantPropertyId).attr('data-original-title','Deactivated');
								$('#editIcon'+participantPropertyId).addClass('cursor-none');
							}else{
								showSucMsg('Participant Property successfully activated.');
								$('#'+participantPropertyId).val("1");
								$('#label'+participantPropertyId).attr('data-original-title','Active');
								$('#editIcon'+participantPropertyId).removeClass('cursor-none');
							}
						}else {
							showErrMsg('Failed to update. Please try again.');
							if("0" == status){
								$('#'+participantPropertyId).prop('checked', false);
							} else if("1" == checked){
								$('#'+participantPropertyId).prop('checked', true);
							}
						}
					}
				});
		 	} else {
				if("0" == status){
					$('#'+participantPropertyId).prop('checked', false);
				} else if("1" == status){
					$('#'+participantPropertyId).prop('checked', true);
				}
				return;
			}
	 	});
	}
	
	function deleteQuestionnaire(participantPropertyId){
		  bootbox.confirm({
			    message: "Are you sure you want to delete this Participant Property item? This item will no longer appear on the mobile app or admin portal. Response data already gathered against this item, if any, will still be available on the response database.",
			    buttons: {
			        confirm: {
			            label: 'Yes',
			        },
			        cancel: {
			            label: 'No',
			        }
			    },
			    callback: function (result) {
			    	if(result){
						if(participantPropertyId != null && participantPropertyId != '' && typeof participantPropertyId !='undefined'){
							$.ajax({
				    			url: "/fdahpStudyDesigner/adminStudies/deleteParticipantProperty.do?_S=${param._S}",
				    			type: "POST",
				    			datatype: "json",
				    			data:{
				    				participantPropertyId: participantPropertyId,
				    				"${_csrf.parameterName}":"${_csrf.token}",
				    			},
				    			success: function deleteConsentInfo(data){
				    				var jsonobject = eval(data);
				    				var status = jsonobject.message;
				    				//var markAsComplete = data.markAsComplete;
				    				//var activityMsg = data.activityMsg;
				    				if(status == "SUCCESS"){
				    					$("#alertMsg").removeClass('e-box').addClass('s-box').html("Participant Property deleted successfully");
				    					$('#alertMsg').show();
				    					var participantPropertiesList = jsonobject.participantPropertiesList;
				    					reloadDataTabel(participantPropertiesList);
				    					/* if($('.sixthQuestionnaires').find('span').hasClass('sprites-icons-2 tick pull-right mt-xs')){
				    						$('.sixthQuestionnaires').find('span').removeClass('sprites-icons-2 tick pull-right mt-xs');
				    					} */
				    					/* if(!markAsComplete){
				    						$('#markAsCompleteBtnId').prop('disabled',true);
				    						$('#markAsTooltipId').attr("data-original-title", activityMsg);
				    					}else{
				    						$('#markAsCompleteBtnId').prop('disabled',false);
				    						$('#markAsTooltipId').removeAttr('data-original-title');
				    					} */
				    				}else{
				    					/* if(status == 'FAILUREanchorused'){
				    						$("#alertMsg").removeClass('s-box').addClass('e-box').html("Participant Property already live anchorbased.unable to delete");
				    					}else{
				    						$("#alertMsg").removeClass('s-box').addClass('e-box').html("Unable to delete Participant Property");
					    					
				    					} */
				    					$("#alertMsg").removeClass('s-box').addClass('e-box').html("Unable to delete Participant Property");
				    					$('#alertMsg').show();
				    	            }
				    				setTimeout(hideDisplayMessage, 4000);
				    			},
				    			error: function(xhr, status, error) {
				    			  $("#alertMsg").removeClass('s-box').addClass('e-box').html(error);
				    			  setTimeout(hideDisplayMessage, 4000);
				    			}
				    		});
						}
					}
			    }
		  });
	  }
	
	function reloadDataTabel(participantPropertiesList){
		  $('#participantProperties_list').DataTable().clear();
		  if (typeof participantPropertiesList != 'undefined' && participantPropertiesList != null && participantPropertiesList.length >0){
			  $.each(participantPropertiesList, function(i, obj) {
				 var status=0;
				 var flagStatus = false;
				 if(obj.status){
					 status=1;
				 }
				 if(obj.status){
					 flagStatus = true;
					 checkedStatus = "checked";
				 }
	             var datarow = [];
	 			 if(typeof obj.propertyName === "undefined" && typeof obj.propertyName === "undefined" ){
	 					datarow.push(' ');
	 			 }else{
	 					datarow.push(obj.propertyName);
	 			 }
	 			 if(typeof obj.dataType === "undefined" && typeof obj.dataType === "undefined" ){
						datarow.push(' ');
				 }else{
						datarow.push(obj.dataType);
				 }	
	 			 var actionDiv = "<span class='sprites_icon edit-g mr-lg' data-toggle='tooltip' data-placement='top' title='Edit' onclick='editQuestionnaires("+obj.id+");'></span>";
	 			 
	 			 actionDiv += "<span class='sprites_icon copy delete' data-toggle='tooltip' data-placement='top' title='Delete' onclick='deleteQuestionnaire("+obj.id+");'></span>";
	             
	 			 actionDiv += "<span class='ml-lg'> <label class='switch' data-toggle='tooltip' id='label"+obj.id+"' data-placement='top' ";
	 			 if(flagStatus){
	 				actionDiv += " title='Active' ";
	 			 }else{
	 				actionDiv += " title='Deactivated' ";
	 			 }
	 			 actionDiv += " > <input type='checkbox' class='switch-input' value='"+status+"' ";
	 			if(flagStatus){
	 				actionDiv += " checked ";
	 			 }
	 			 actionDiv +=" id='"+obj.id+"' <c:if test='"+obj.status+"'>checked</c:if> onchange='activateOrDeactivateParticipantProperty("+obj.id+")'> <span class='switch-label' data-on='On' data-off='Off'></span> <span class='switch-handle'></span> </label> </span>";
	 			   
	 			 datarow.push(actionDiv);
	             $('#participantProperties_list').DataTable().row.add(datarow);
			  });
			  $('#participantProperties_list').DataTable().draw();
		  }
		 else{
			  $('#participantProperties_list').DataTable().draw();
			  //$("#markAsCompleteBtnId").prop("disabled",false);
			  //$("#markAsTooltipId").removeAttr('data-original-title');
		  }
		  $('[data-toggle="tooltip"]').tooltip();
	  }
	function markAsCompleted(){
		document.editParticipantProperties.action="/fdahpStudyDesigner/adminStudies/participantPropertiesMarkAsCompleted.do?_S=${param._S}";	 
		document.editParticipantProperties.submit();
	}
</script>