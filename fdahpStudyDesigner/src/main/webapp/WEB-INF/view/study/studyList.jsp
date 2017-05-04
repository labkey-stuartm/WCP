<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<div>
            <table id="studies_list" class="table wid100 tbl">
            <thead>
              <tr>
                <th style="display: none;"> <span class="sort"></span></th>
                <th>Live Study ID <span class="sort"></span></th>
                <th>Study ID <span class="sort"></span></th>
                <th>Study name <span class="sort"></span></th>
                <th>Study Category <span class="sort"></span></th>
                <th>Project Lead <span class="sort"></span></th>
                <th>Research Sponsor <span class="sort"></span></th>
                <th>Status <span class="sort"></span></th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${studyBos}" var="study">
              <tr>
                <td style="display: none;">${study.createdOn}</td>
                <td>${study.liveStudyId}</td>
                <td>${study.customStudyId}</td>
                <td><div class="studylist-txtoverflow">${study.name}</div></td>
                <td>${study.category}</td>
                <td>None</td>
                <td>${study.researchSponsor}</td>
                <td>${study.status}</td>
                <td>
                    <!-- <span class="sprites_icon preview-g mr-lg"></span> -->
                    <span class="sprites_icon preview-g mr-lg viewStudyClass" isLive="" studyId="${study.id}" permission="view"></span>
                    <span class="sprites_icon edit-g mr-lg addEditStudyClass 
                    <c:choose>
						<c:when test="${not study.viewPermission}">
								cursor-none
						</c:when>
						<c:when test="${not empty study.status && (study.status eq 'Deactivated')}">
							  cursor-none
						</c:when>
					</c:choose>" studyId="${study.id}"></span>
                    <c:if test = "${not empty study.liveStudyId}">
                    <span class="eye-inc mr-lg viewStudyClass" isLive="Yes" studyId="${study.liveStudyId}" permission="view"></span>
					</c:if>
                  </td>        
              </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>  
<form:form action="/fdahpStudyDesigner/adminStudies/viewBasicInfo.do" id="addEditStudyForm" name="addEditStudyForm" method="post">
  <input type="hidden" id="studyId" name="studyId">
</form:form>
<script>
       $(document).ready(function() {
    	 $('.studyClass').addClass('active');
         
           
         $('.addEditStudyClass').on('click',function(){
			    var form= document.createElement('form');
		    	form.method= 'post';
		    	var input= document.createElement('input');
		    	input.type= 'hidden';
				input.name= 'studyId';
				input.value= $(this).attr('studyId');
				form.appendChild(input);
				
				input= document.createElement('input');
		    	input.type= 'hidden';
				input.name= '${_csrf.parameterName}';
				input.value= '${_csrf.token}';
				form.appendChild(input);
				
		    	form.action= '/fdahpStudyDesigner/adminStudies/viewBasicInfo.do';
		    	document.body.appendChild(form);
		    	form.submit();
		 });
         
         $('.viewStudyClass').on('click',function(){
        	 var form= document.createElement('form');
		    	form.method= 'post';
		    	var input= document.createElement('input');
		    	input.type= 'hidden';
				input.name= 'studyId';
				input.value= $(this).attr('studyId');
				form.appendChild(input);
				
				var input1= document.createElement('input');
		    	input1.type= 'hidden';
				input1.name= 'permission';
				input1.value= $(this).attr('permission');
				form.appendChild(input1);
				
				var input2= document.createElement('input');
		    	input2.type= 'hidden';
				input2.name= 'isLive';
				input2.value= $(this).attr('isLive');
				form.appendChild(input2);
				
				input= document.createElement('input');
		    	input.type= 'hidden';
				input.name= '${_csrf.parameterName}';
				input.value= '${_csrf.token}';
				form.appendChild(input);
				
		    	form.action= '/fdahpStudyDesigner/adminStudies/viewBasicInfo.do';
		    	document.body.appendChild(form);
		    	form.submit();
 	     });
         
         $('#studies_list').DataTable( {
             "paging":   true,
             "abColumns": [
               { "bSortable": true },
                { "bSortable": true },
                { "bSortable": true },
                { "bSortable": true },
                { "bSortable": true },
                { "bSortable": false }
               ],
               "columnDefs": [ { orderable: false, targets: [8] } ],
               "order": [[ 0, "desc" ]],
             "info" : false, 
             "lengthChange": false, 
             "searching": false, 
             "pageLength": 10 
         } );
         
        });
	    
        //datatable icon toggle
        $(".table thead tr th").click(function(){
          $(this).children().removeAttr('class')
          $(this).siblings().children().removeAttr('class').addClass('sort');    
          if($(this).attr('class') == 'sorting_asc'){
            $(this).children().addClass('asc'); 
            //alert('asc');
          }else if($(this).attr('class') == 'sorting_desc'){
           $(this).children().addClass('desc');
            //alert('desc');
          }else{
            $(this).children().addClass('sort');
          }
        });
 </script>