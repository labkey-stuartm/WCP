<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="table-responsive">
            <table id="studies_list" class="table">
            <thead>
              <tr>
                <th style="display: none;"> <span class="sort"></span></th>
                <th>Study ID <span class="sort"></span></th>
                <th>Study name <span class="sort"></span></th>
                <th>Study Category <span class="sort"></span></th>
                <th>FDA project lead <span class="sort"></span></th>
                <th>Research Sponsor <span class="sort"></span></th>
                <th>Status <span class="sort"></span></th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${studyBos}" var="study">
              <tr>
                <td style="display: none;">${study.createdOn}</td>
                <td>${study.customStudyId}</td>
                <td>${study.name}</td>
                <td>${study.category}</td>
                <td>None</td>
                <td>${study.researchSponsor}</td>
                <td>${study.status}</td>
                <td>
                    <!-- <span class="sprites_icon preview-g mr-lg"></span> -->
                    <span class="sprites_icon edit-g mr-lg addEditStudyClass <c:if test="${not study.viewPermission}">cursor-none</c:if>" studyId="${study.id}"></span>
                    <!-- <span class="sprites_icon copy mr-lg"></span> -->
                    
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
         $('#studies_list').DataTable( {
                "paging":   true,
                "abColumns": [
                  { "bSortable": true },
                   { "bSortable": true },
                   { "bSortable": true },
                   { "bSortable": true },
                   { "bSortable": true },
                   { "bSortable": true }
                  ],
                  "order": [[ 0, "desc" ]],
                "info" : false, 
                "lengthChange": false, 
                "searching": false, 
                "pageLength": 10 
            } );
           
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