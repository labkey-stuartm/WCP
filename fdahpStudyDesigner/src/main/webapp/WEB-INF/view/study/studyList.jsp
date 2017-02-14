<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<body>

 <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none white-bg">
     <div class="md-container">
         
         <!-- Navigation Menu-->
         <nav class="navbar navbar-inverse">
          <div class="container-fluid p-none">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>                        
              </button>
              <a class="navbar-brand" href="#"><img src="/fdahpStudyDesigner/images/logo/logo-sm.png"/></a>
            </div>
            <div class="collapse navbar-collapse p-none" id="myNavbar">
              <ul class="nav navbar-nav">
                <li class="active"><a href="#">Studies</a></li>
                <li class="dropdown">
                  <a class="dropdown-toggle" data-toggle="dropdown" href="#">repository <span><i class="fa fa-angle-down" aria-hidden="true"></i></span></a>
                  <ul class="dropdown-menu">
                    <li><a href="#">Reference Tables</a></li>
                    <li><a href="#">QA content</a></li>
                    <li><a href="#">Resources</a></li>
                    <li><a href="#">Gateway app level content</a></li>
                    <li><a href="#">Legal Text</a></li>
                  </ul>
                </li>
                <li><a href="#">Notifications</a></li>
                <li><a href="#">Users</a></li>
              </ul>
              <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Samuel Johnson <span><i class="fa fa-angle-down" aria-hidden="true"></i></span></a></li>
              </ul>
            </div>
          </div>
        </nav>   
         
     </div>
 </div>
 
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none mt-md mb-md">
     <div class="md-container">
         <!-- widgets section-->
         <div class="col-sm-12 col-md-12 col-lg-12 p-none">
            <div class="black-lg-f">
                Manage Users
            </div>
             
             <div class="dis-inline">
              <form class="navbar-form" role="search">
                <div class="input-group add-on">
                  <input class="form-control selectpicker" placeholder="Search" name="srch-term" id="srch-term" type="text">
                  <div class="input-group-btn">
                    <button class="btn btn-default" type="submit"><i class="fa fa-search" aria-hidden="true"></i></button>
                  </div>
                </div>
              </form>
             </div>
             
             <div class="dis-line pull-right ml-md">
                 <div class="form-group mb-none mt-sm">
                     <button type="button" class="btn btn-primary blue-btn"><span class="mr-xs">+</span> Add User</button>
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

    <!-- Vendor -->
    <script src="vendor/jquery/jquery-3.1.1.min.js"></script>
    <script src="vendor/boostrap/bootstrap.min.js"></script>
    <script src="vendor/animation/wow.min.js"></script>
    <script src="vendor/datatable/js/jquery.dataTables.min.js"></script>
    <script src="vendor/select2/bootstrap-select.min.js"></script>
    <script src="vendor/dragula/react-dragula.min.js"></script>
    <script src="vendor/magnific-popup/jquery.magnific-popup.min.js"></script>    
    <script src="vendor/slimscroll/jquery.slimscroll.min.js"></script>
    
    <!-- Theme Custom JS-->
    <script src="js/theme.js"></script>
    <script src="js/common.js"></script>
    
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

        //datatable icon toggle
        $("#user_list thead tr th").click(function(){
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
    });
    </script>
</body>