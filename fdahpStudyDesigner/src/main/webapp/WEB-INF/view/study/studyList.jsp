<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style>
/* The Modal (background) */
.modal {
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    padding-top: 100px; /* Location of the box */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
    background-color: #fefefe;
    margin: auto;
    padding: 20px;
    border: 1px solid #888;
    width: 80%;
}

/* The Close Button */
.close {
    color: #aaaaaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close:hover,
.close:focus {
    color: #000;
    text-decoration: none;
    cursor: pointer;
}
</style>
<body>
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none mt-md mb-md">
     <div class="md-container">
         <!-- widgets section-->
         <div class="col-sm-12 col-md-12 col-lg-12 p-none">
            <div class="black-lg-f">
                Manage Studies
            </div>
             
             <div class="dis-inline">
              <form class="navbar-form" role="search">
                <!-- <div class="input-group add-on">
                  <input class="form-control selectpicker" placeholder="Search" name="srch-term" id="srch-term" type="text">
                  <div class="input-group-btn">
                    <button class="btn btn-default" type="submit"><i class="fa fa-search" aria-hidden="true"></i></button>
                  </div>
                </div> -->
              </form>
             </div>
             
             <div class="dis-line pull-right ml-md">
                 <div class="form-group mb-none mt-sm">
                     <button id="myBtn" type="button" class="btn btn-primary blue-btn"><span class="mr-xs">+</span> Create Study</button>
                 </div>
             </div>
            
             <div class="dis-line pull-right">
              <div class="form-group mb-none mt-sm">
                  <select class="form-control selectpicker btn-md" id="sel1">
                    <option disabled selected>Filter by Role</option>
                    <option>Project Lead</option>
                    <option>Coordinator</option>
                    <option>Recruiter</option>
                    <option>Project Lead</option>
                  </select>
                </div>
             </div>
                      
         </div>         
    </div>
</div>
    
<div class="clearfix"></div>
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none"> 
    <div class="md-container white-bg">
        <div class="table-responsive">
            <table id="user_list" class="table">
            <thead>
              <tr>
                <th>Name <span class="sort"></span></th>
                <th>e-mail address</th>
                <th>ROLE <span class="sort"></span></th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>Marylyn Deshields</td>
                <td>jason@gmail.com</td>
                <td>Project Lead</td>
                <td>
                    <span class="sprites_icon edit-g"></span>
                    <span class="ml-lg">
                        <label class="switch">
                          <input type="checkbox" class="switch-input" checked>
                          <span class="switch-label" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </span>
                  </td>        
              </tr>
               <tr>
                <td>Percy Fimbres</td>
                <td>laura007@gmail.com</td>
                <td>Coordinator</td>
                <td>
                    <span class="sprites_icon edit-g"></span>
                    <span class="ml-lg">
                        <label class="switch">
                          <input type="checkbox" class="switch-input">
                          <span class="switch-label" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </span>                   
                </td>          
              </tr>
               <tr>
                <td>Percy Fimbres</td>
                <td>laura007@gmail.com</td>
                <td>Coordinator</td>
                <td>
                   <span class="sprites_icon edit-g"></span>
                    <span class="ml-lg">
                        <label class="switch">
                          <input type="checkbox" class="switch-input">
                          <span class="switch-label" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </span>
                </td>          
              </tr>
               <tr>
                <td>Paulette Culbertson</td>
                <td>mariuspop@gmail.com</td>
                <td>Project Lead</td>
                <td>
                   <span class="sprites_icon edit-g"></span>
                    <span class="ml-lg">
                        <label class="switch">
                          <input type="checkbox" class="switch-input" checked>
                          <span class="switch-label" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </span>
                </td>      
              </tr>
             <tr>
                <td>Percy Fimbres</td>
                <td>laura007@gmail.com</td>
                <td>Coordinator</td>
               <td>
                  <span class="sprites_icon edit-g"></span>
                    <span class="ml-lg">
                        <label class="switch">
                          <input type="checkbox" class="switch-input">
                          <span class="switch-label" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </span>
               </td>     
              </tr>
              <tr>
                <td>Percy Fimbres</td>
                <td>laura007@gmail.com</td>
                <td>Coordinator</td>
                <td>
                   <span class="sprites_icon edit-g"></span>
                    <span class="ml-lg">
                        <label class="switch">
                          <input type="checkbox" class="switch-input" checked>
                          <span class="switch-label" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </span>
                </td>             
              </tr>
               <tr>
                <td>Percy Fimbres</td>
                <td>laura007@gmail.com</td>
                <td>Coordinator</td>
                 <td>
                   <span class="sprites_icon edit-g"></span>
                    <span class="ml-lg">
                        <label class="switch">
                          <input type="checkbox" class="switch-input">
                          <span class="switch-label" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </span>
                </td>             
              </tr>
              <tr>
                <td>Percy Fimbres</td>
                <td>laura007@gmail.com</td>
                <td>Coordinator</td>
                 <td>
                   <span class="sprites_icon edit-g"></span>
                    <span class="ml-lg">
                        <label class="switch">
                          <input type="checkbox" class="switch-input" checked>
                          <span class="switch-label" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </span>
                </td>           
              </tr>
                <tr>
                <td>Percy Fimbres</td>
                <td>laura007@gmail.com</td>
                <td>Coordinator</td>
                 <td>
                   <span class="sprites_icon edit-g"></span>
                    <span class="ml-lg">
                        <label class="switch">
                          <input type="checkbox" class="switch-input">
                          <span class="switch-label" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </span>
                </td>            
              </tr>
                <tr>
                <td>Percy Fimbres</td>
                <td>laura007@gmail.com</td>
                <td>Coordinator</td>
                <td>
                   <span class="sprites_icon edit-g"></span>
                    <span class="ml-lg">
                        <label class="switch">
                          <input type="checkbox" class="switch-input">
                          <span class="switch-label" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </span>
                </td>          
              </tr>
                <tr>
                <td>Percy Fimbres</td>
                <td>laura007@gmail.com</td>
                <td>Coordinator</td>
                 <td>
                   <span class="sprites_icon edit-g"></span>
                    <span class="ml-lg">
                        <label class="switch">
                          <input type="checkbox" class="switch-input" checked>
                          <span class="switch-label" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </span>
                </td>        
              </tr>
                <tr>
                <td>Percy Fimbres</td>
                <td>laura007@gmail.com</td>
                <td>Coordinator</td>
                <td>
                   <span class="sprites_icon edit-g"></span>
                    <span class="ml-lg">
                        <label class="switch">
                          <input type="checkbox" class="switch-input">
                          <span class="switch-label" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </span>
                </td>       
              </tr>
                <tr>
                <td>Percy Fimbres</td>
                <td>laura007@gmail.com</td>
                <td>Coordinator</td>
                <td>
                   <span class="sprites_icon edit-g"></span>
                    <span class="ml-lg">
                        <label class="switch">
                          <input type="checkbox" class="switch-input" checked>
                          <span class="switch-label" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </span>
                </td>       
              </tr>
                <tr>
                <td>Percy Fimbres</td>
                <td>laura007@gmail.com</td>
                <td>Coordinator</td>
                 <td>
                   <span class="sprites_icon edit-g"></span>
                    <span class="ml-lg">
                        <label class="switch">
                          <input type="checkbox" class="switch-input">
                          <span class="switch-label" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </span>
                </td>    
              </tr>
                <tr>
                <td>Percy Fimbres</td>
                <td>laura007@gmail.com</td>
                <td>Coordinator</td>
                <td>
                   <span class="sprites_icon edit-g"></span>
                    <span class="ml-lg">
                        <label class="switch">
                          <input type="checkbox" class="switch-input">
                          <span class="switch-label" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </span>
                </td>    
              </tr>
                <tr>
                <td>Percy Fimbres</td>
                <td>laura007@gmail.com</td>
                <td>Coordinator</td>
                <td>
                   <span class="sprites_icon edit-g"></span>
                    <span class="ml-lg">
                        <label class="switch">
                          <input type="checkbox" class="switch-input" checked>
                          <span class="switch-label" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </span>
                </td>    
              </tr>
                <tr>
                <td>Percy Fimbres</td>
                <td>laura007@gmail.com</td>
                <td>Coordinator</td>
                 <td>
                   <span class="sprites_icon edit-g"></span>
                    <span class="ml-lg">
                        <label class="switch">
                          <input type="checkbox" class="switch-input">
                          <span class="switch-label" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </span>
                </td>           
              </tr>
                <tr>
                <td>Percy Fimbres</td>
                <td>laura007@gmail.com</td>
                <td>Coordinator</td>
                <td>
                   <span class="sprites_icon edit-g"></span>
                    <span class="ml-lg">
                        <label class="switch">
                          <input type="checkbox" class="switch-input" checked>
                          <span class="switch-label" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </span>
                </td>        
              </tr>
                <tr>
                <td>Percy Fimbres</td>
                <td>laura007@gmail.com</td>
                <td>Coordinator</td>
                <td>
                   <span class="sprites_icon edit-g"></span>
                    <span class="ml-lg">
                        <label class="switch">
                          <input type="checkbox" class="switch-input">
                          <span class="switch-label" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </span>
                </td>    
              </tr>

            </tbody>
          </table>
        </div>
  </div>
</div>
<!-- The Modal -->
<div id="myModal" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <span class="close">&times;</span>
    <p>Some text in the Modal..</p>
    <button type="button" id="keySubmitId" class="">Submit</button>
       <table id="adminTableList">
            <thead>
              <tr>
                <th> </th>
                <th>Name <span class=""></span></th>
                <th>e-mail address</th>
                <th>ROLE <span class=""></span></th>
              </tr>
            </thead>
            <tbody>
                <tr>
                  <td><input type="checkbox" name="adminIdClass" class="adminIdClass" value='1'/></td>
                  <td class="adminName">Ronalin</td>
                  <td>ronalins@boston-technology.com</td>
                  <td>Software Engineer<span class=""></span></td>
                </tr>
                <tr>
                  <td><input type="checkbox" name="adminIdClass" class="adminIdClass" value='2'/></td>
                  <td class="adminName">Kanchana</td>
                  <td>kanchanar@boston-technology.com</td>
                  <td>Software Engineer<span class=""></span></td>
                </tr>
                <tr>
                  <td><input type="checkbox" name="adminIdClass" class="adminIdClass" value='3'/></td>
                  <td class="adminName">Pradyumn</td>
                  <td>pradyumnkumarb@boston-technology.com</td>
                  <td>Software Engineer<span class=""></span></td>
                </tr>
            </tbody>
    </table>
  </div>

</div>
    <script>
    $(document).ready(function(){
        //User_List page Datatable
        $('#user_list').DataTable( {
            "paging":   true,
            "aoColumns": [
              { "bSortable": true },
               { "bSortable": false },
               { "bSortable": true },
               { "bSortable": false }
              ],  
            "info" : false, 
            "lengthChange": false, 
            "searching": false, 
            "pageLength": 10 
        });
        
        $('#keySubmitId').click(function(){
        	var adminIds = "";
        	var adminName = "";
    		$('#adminTableList').find('input[name="adminIdClass"]:checked').each(function(){
    			adminIds = adminIds+","+$(this).val();
    			adminName = adminName+","+$(this).find('td:eq(2)').find(".adminName").text();   
			});
    		
        	alert("adminIds::"+adminIds);
        	alert("adminName::"+adminName);
        });

    });
    </script>
<script>
// Get the modal
var modal = document.getElementById('myModal');

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal 
btn.onclick = function() {
    modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
</script>
</body>