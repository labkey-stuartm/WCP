<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%-- <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none mt-md mb-md">
     <div class="md-container">
         <!-- widgets section-->
         <div class="col-sm-12 col-md-12 col-lg-12 p-none">
            <div class="black-lg-f">
                Manage Studies
            </div>          
             <c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_CREATE_MANAGE_STUDIES')}">
             <div class="dis-line pull-right ml-md">
                 <div class="form-group mb-none">
                     <button type="button" class="btn btn-primary blue-btn addEditStudy"><span class="mr-xs">+</span> Create Study</button>
                 </div>
             </div>
            </c:if>
         </div>         
    </div>
</div> --%>
<div class="table-responsive">
            <table id="studies_list" class="table">
            <thead>
              <tr>
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
                <td>${study.customStudyId}</td>
                <td>${study.name}</td>
                <td>${study.category}</td>
                <td>None</td>
                <td>${study.researchSponsor}</td>
                <td>${study.status}</td>
                <td>
                    <%-- <span class="sprites_icon preview-g mr-lg"></span>
                    <span class="sprites_icon edit-g mr-lg addEditStudy" studyId="${study.id}"></span>
                    <span class="sprites_icon copy mr-lg"></span> --%>
                    
                    <span class="sprites_icon preview-g mr-lg"></span>
                    <span class="sprites_icon edit-g mr-lg addEditStudyClass" studyId="${study.id}"></span>
                    <span class="sprites_icon copy mr-lg"></span>
                    
                  </td>        
              </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>  
<%-- <div class="clearfix"></div>
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none"> 
    <div class="md-container white-bg">
        <div class="table-responsive">
            <table id="studies_list" class="table">
            <thead>
              <tr>
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
                <td>${study.customStudyId}</td>
                <td>${study.name}</td>
                <td>${study.category}</td>
                <td>None</td>
                <td>${study.researchSponsor}</td>
                <td>${study.status}</td>
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
  </div>
</div> 
</body>
<form:form action="/fdahpStudyDesigner/adminStudies/viewBasicInfo.do" id="addEditStudyForm" name="addEditStudyForm" method="post">
</form:form> --%>
<form:form action="/fdahpStudyDesigner/adminStudies/viewSettingAndAdmins.do" id="addEditStudyForm" name="addEditStudyForm" method="post">
  <input type="hidden" id="studyId" name="studyId" value="">
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
                "info" : false, 
                "lengthChange": false, 
                "searching": false, 
                "pageLength": 10 
            } );
           
         $('.addEditStudyClass').on('click',function(){
        	 $('#studyId').val($(this).attr('studyId'));
			 $('#addEditStudyForm').submit();
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