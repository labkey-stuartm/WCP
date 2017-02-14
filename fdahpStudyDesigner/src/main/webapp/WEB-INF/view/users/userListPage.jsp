<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<body>
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none mt-md mb-md">
     <div class="md-container">
         <!-- widgets section-->
         <div class="col-sm-12 col-md-12 col-lg-12 p-none">
            <div class="black-lg-f">
                Manage Users
            </div>
             
            <%--  <div class="dis-inline">
              <form class="navbar-form" role="search">
                <div class="input-group add-on">
                  <input class="form-control selectpicker" placeholder="Search" name="srch-term" id="srch-term" type="text">
                  <div class="input-group-btn">
                    <button class="btn btn-default" type="submit"><i class="fa fa-search" aria-hidden="true"></i></button>
                  </div>
                </div>
              </form>
             </div> --%>
             
             <div class="dis-line pull-right ml-md">
                 <div class="form-group mb-none mt-sm">
                     <button type="button" class="btn btn-primary blue-btn"><span class="mr-xs">+</span> Add User</button>
                 </div>
             </div>
            
            <!--  <div class="dis-line pull-right">
              <div class="form-group mb-none mt-sm">
                  <select class="form-control selectpicker btn-md" id="sel1">
                    <option disabled selected>Filter by Role</option>
                    <option>Project Lead</option>
                    <option>Coordinator</option>
                    <option>Recruiter</option>
                    <option>Project Lead</option>
                  </select>
                </div>
             </div> -->
                      
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
</body>
