<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
.cursonMove{
 cursor: move !important;
}
.sepimgClass{
 position: relative;
}
.sepimgClass:after{
     width: 7px;
    content: ' ';
    position: absolute;
    background-image: url(../images/sepImg.png);
    height: 20px;
    background-repeat: no-repeat;
    right: 15px;
    top: 23px;
}
</style>
<div class="table-responsive">
<input type="hidden" name="studyId" id="studyId" value="${studyId}" />
   <table id="consentInfoList" class="table">
      <thead>
         <tr>
            <th># <span class="sort"></span></th>
            <th>CONSENT TITLE <span class="sort"></span></th>
            <th>VISUAL STEP <span class="sort"></span></th>
            <th> <span class="sort"></span></th>
         </tr>
      </thead>
      <tbody>
         <c:forEach items="${consentInfoList}" var="consentInfo">
            <tr>
               <td class="cursonMove sepimgClass">${consentInfo.sequenceNo}</td>
               <td>${consentInfo.title}</td>
               <td>${consentInfo.visualStep}</td>
               <td>
                  <span class="sprites_icon preview-g mr-lg"></span>
                  <span class="sprites_icon edit-g mr-lg"></span>
                  <span class="sprites_icon copy mr-lg"></span>
               </td>
            </tr>
         </c:forEach>
      </tbody>
   </table>
</div>
<script type="text/javascript">
var table1 = $('#consentInfoList').DataTable( {
    "paging":false,
    "info":     false,
    "filter": false,
     rowReorder: true
	
});
table1.on( 'row-reorder', function ( e, diff, edit ) {
	var oldOrderNumber = '', newOrderNumber = '';
    var result = 'Reorder started on row: '+edit.triggerRow.data()[1]+'<br>';
	var studyId = $("#studyId").val();
    for ( var i=0, ien=diff.length ; i<ien ; i++ ) {
        var rowData = table1.row( diff[i].node ).data();
        if(i==0){
        	oldOrderNumber = diff[i].oldData;
            newOrderNumber = diff[i].newData;
        }
        result += rowData[1]+' updated to be in position '+
            diff[i].newData+' (was '+diff[i].oldData+')<br>';
    }

    console.log('oldOrderNumber:'+oldOrderNumber);
    console.log('newOrderNumber:'+newOrderNumber);
    console.log('studyId:'+studyId);
    
    if(oldOrderNumber !== undefined && oldOrderNumber != null && oldOrderNumber != "" 
		&& newOrderNumber !== undefined && newOrderNumber != null && newOrderNumber != ""){
    	$.ajax({
			url: "/fdahpStudyDesigner/adminStudies/reOrderConsentInfo.do",
			type: "POST",
			datatype: "json",
			data:{
				studyId : studyId,
				oldOrderNumber: oldOrderNumber,
				newOrderNumber : newOrderNumber,
				"${_csrf.parameterName}":"${_csrf.token}",
			},
			success: function consentInfo(data){
				var status = data.message;
				if(status == "SUCCESS"){
					alert("hoory sucess.....")
				}else{
                    //  bootbox.alert("<div style='color:red'>Fail to add asp</div>");
	            }
			},
			error: function(xhr, status, error) {
			  alert(xhr.responseText);
			  alert("Error : "+error);
			}
		});
    }
} );
</script>