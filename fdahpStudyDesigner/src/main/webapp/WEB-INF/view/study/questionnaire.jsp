<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== --> 
<div class="right-content">
   <!--  Start top tab section-->
   <div class="right-content-head">
      <div class="text-right">
         <div class="black-md-f text-uppercase dis-line pull-left line34"><span><img src="../images/icons/back-b.png" class="pr-md"/></span> Add Questionnaire</div>
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn">Cancel</button>
         </div>
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn">Save</button>
         </div>
         <div class="dis-line form-group mb-none">
            <button type="button" class="btn btn-primary blue-btn">Done</button>
         </div>
      </div>
   </div>
   <!--  End  top tab section-->
   <!--  Start body tab section -->
   <div class="right-content-body pt-none pl-none">
      <ul class="nav nav-tabs review-tabs">
         <li><a data-toggle="tab" href="#content">Content</a></li>
         <li class="active"><a data-toggle="tab" href="#schedule">Schedule</a></li>
      </ul>
      <div class="tab-content pl-xlg pr-xlg">
         <div id="content" class="tab-pane fade">
            <h3>Share Data Permissions</h3>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
         </div>
         <!---  Schedule ---> 
         <div id="schedule" class="tab-pane fade in active mt-xlg">
            <div class="gray-xs-f mb-sm">Questionnaire Frequency</div>
            <div class="pb-lg b-bor">
               <span class="radio radio-info radio-inline p-40">
               <input type="radio" id="inlineRadio1" class="schedule" value="oneTime" name="radioInline1" checked>
               <label for="inlineRadio1">One Time</label>
               </span>
               <span class="radio radio-inline p-40">
               <input type="radio" id="inlineRadio2" class="schedule" value="daily" name="radioInline1">
               <label for="inlineRadio2">Daily</label>
               </span>
               <span class="radio radio-inline p-40">
               <input type="radio" id="inlineRadio3" class="schedule" value="week" name="radioInline1">
               <label for="inlineRadio3">Weekly</label>
               </span>
               <span class="radio radio-inline p-40">
               <input type="radio" id="inlineRadio4" class="schedule" value="month" name="radioInline1">
               <label for="inlineRadio4">Monthly</label>
               </span>
               <span class="radio radio-inline p-40">
               <input type="radio" id="inlineRadio5" class="schedule" value="manually" name="radioInline1">
               <label for="inlineRadio5">Manually Schedule</label>
               </span>
            </div>
            <!-- One Time Section-->    
            <div class="oneTime all mt-xlg">
               <div class="gray-xs-f mb-sm">Date/Time of launch(pick one)</div>
               <div class="mt-sm">
                  <span class="checkbox checkbox-inline">
                  <input type="checkbox" id="inlineCheckbox2" value="option2">
                  <label for="inlineCheckbox2"> Launch with study</label>
                  </span>
                  <div class="mt-md">
                     <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                     <input id="chooseDate" type="text" class="form-control calendar" placeholder="Choose Date"/>
                     </span>
                     <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                     <input id="selectTime" type="text" class="form-control clock" data-provide="timepicker" data-minute-step="1" data-modal-backdrop="true" placeholder="Select Time" onclick="timep()" />
                     </span>
                  </div>
               </div>
               <div class="gray-xs-f mb-sm mt-xlg">Lifetime of the run and of the questionnaire</div>
               <div class="mt-sm">
                  <span class="checkbox checkbox-inline">
                  <input type="checkbox" id="inlineCheckbox1" value="option1">
                  <label for="inlineCheckbox1"> Study Lifetime</label>
                  </span>
                  <div class="mt-md">
                     <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                     <input id="chooseEndDate" type="text" class="form-control calendar" placeholder="Choose End Date"/>
                     </span>                            
                  </div>
               </div>
            </div>
            <!-- Daily Section-->    
            <div class="daily all mt-xlg dis-none">
               <div class="gray-xs-f mb-sm">Time(s) of the day for daily occurrence</div>
               <div class="mt-md">
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <input id="time" type="text" class="form-control clock" placeholder="00:00"/>
                  </span> 
                  <span class="delete vertical-align-middle"></span>
               </div>
               <div class="mt-md">
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <input id="time" type="text" class="form-control clock" placeholder="00:00"/>
                  </span> 
                  <span class="addbtn">+</span>
               </div>
               <div class="mt-xlg">                        
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <span class="gray-xs-f">Start date (pick a date)</span><br/>                            
                  <input id="startDate" type="text" class="form-control mt-sm calendar" />
                  </span>
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <span class="gray-xs-f">No. of days to repeat the questionnaire</span><br/>
                  <input id="days" type="text" class="form-control mt-sm" />
                  </span>
               </div>
               <div class="mt-xlg">
                  <div class="gray-xs-f mb-xs">End Date </div>
                  <div class="black-xs-f">2/19/2017</div>
               </div>
               <div class="mt-xlg">
                  <div class="gray-xs-f mb-xs">Lifetime of each run</div>
                  <div class="black-xs-f">Until the next run comes up</div>
               </div>
               <div class="mt-xlg">
                  <div class="gray-xs-f mb-xs">Lifetime of the questionnaire </div>
                  <div class="black-xs-f">2/14/2017  -  2/19/2017</div>
               </div>
            </div>
            <!-- Weekly Section-->    
            <div class="week all mt-xlg dis-none">
               <div>                        
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <span class="gray-xs-f">Day/Time (of the week)</span><br/>                            
                  <input id="startDateWeekly" type="text" class="form-control mt-sm calendar" />
                  </span>
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <span class="gray-xs-f">&nbsp;</span><br/>
                  <input id="starttimeWeekly" type="text" class="form-control mt-sm clock" />
                  </span>                        
               </div>
               <div class="mt-xlg">                        
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <span class="gray-xs-f">Start date (pick a date)</span><br/>                            
                  <input id="startDate" type="text" class="form-control mt-sm calendar" />
                  </span>
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <span class="gray-xs-f">No. of days to repeat the questionnaire</span><br/>
                  <input id="days" type="text" class="form-control mt-sm" />
                  </span>
               </div>
               <div class="mt-xlg">
                  <div class="gray-xs-f mb-xs">End Date </div>
                  <div class="black-xs-f">2/19/2017</div>
               </div>
               <div class="mt-xlg">
                  <div class="gray-xs-f mb-xs">Lifetime of each run</div>
                  <div class="black-xs-f">Until the next run comes up</div>
               </div>
               <div class="mt-xlg">
                  <div class="gray-xs-f mb-xs">Lifetime of the questionnaire </div>
                  <div class="black-xs-f">2/14/2017  -  2/19/2017</div>
               </div>
            </div>
            <!-- Monthly Section-->    
            <div class="month all mt-xlg dis-none">
               <div>
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <span class="gray-xs-f">Select Date/Time (of the month)</span><br/>                            
                  <input id="startDateWeekly" type="text" class="form-control mt-sm calendar" />
                  </span>
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <span class="gray-xs-f">&nbsp;</span><br/>
                  <input id="starttimeWeekly" type="text" class="form-control mt-sm clock" />
                  </span>
                  <div class="gray-xs-f mt-xs italic-txt text-weight-light">If the selected date is not available in a month, the last day of the month will be used instead</div>
               </div>
               <div class="mt-xlg">                        
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <span class="gray-xs-f">Start date (pick a date)</span><br/>                            
                  <input id="startDate" type="text" class="form-control mt-sm calendar" />
                  </span>
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <span class="gray-xs-f">No. of months to repeat the questionnaire</span><br/>
                  <input id="days" type="text" class="form-control mt-sm" />
                  </span>
               </div>
               <div class="mt-xlg">
                  <div class="gray-xs-f mb-xs">End Date </div>
                  <div class="black-xs-f">2/19/2017</div>
               </div>
               <div class="mt-xlg">
                  <div class="gray-xs-f mb-xs">Lifetime of each run</div>
                  <div class="black-xs-f">Until the next run comes up</div>
               </div>
               <div class="mt-xlg">
                  <div class="gray-xs-f mb-xs">Lifetime of the questionnaire </div>
                  <div class="black-xs-f">2/14/2017  -  2/19/2017</div>
               </div>
            </div>
            <!-- Manually Section-->    
            <div class="manually all mt-xlg dis-none">
               <div class="gray-xs-f mb-sm">Select time period</div>
               <div>
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <input id="StartDate" type="text" class="form-control calendar" placeholder="Start Date"/>
                  </span>
                  <span class="gray-xs-f mb-sm pr-md">
                  to 
                  </span>
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <input id="EndDate" type="text" class="form-control calendar" placeholder="End Date"/>
                  </span>
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <input id="EndDate" type="text" class="form-control clock" placeholder="Time"/>
                  </span>
                  <span id="delete" class="sprites_icon delete vertical-align-middle"></span>
               </div>
               <div class="mt-md">
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <input id="StartDate" type="text" class="form-control calendar" placeholder="Start Date"/>
                  </span>
                  <span class="gray-xs-f mb-sm pr-md">
                  to 
                  </span>
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <input id="EndDate" type="text" class="form-control calendar" placeholder="End Date"/>
                  </span>
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <input id="EndDate" type="text" class="form-control clock" placeholder="Time"/>
                  </span>
                  <span class="addbtn">+</span>
               </div>
               <div class="mt-xlg">
                  <div class="gray-xs-f mb-xs">Default Lifetime of each run </div>
                  <div class="black-xs-f">As defined by the start and end times selected above</div>
               </div>
            </div>
         </div>
      </div>
   </div>
   <!--  End body tab section -->
</div>
<!-- End right Content here -->
<script type="text/javascript">
$(document).ready(function() {
    
    $(".schedule").click(function() {
        $(".all").addClass("dis-none");
        var schedule_opts = $(this).val();
        $("." + schedule_opts).removeClass("dis-none");
    });

});
$(function() {
    $('#chooseDate').datetimepicker({
        format: 'DD/MM/YYYY'
    });
});
$(function() {
    $('#chooseEndDate').datetimepicker({
        format: 'DD/MM/YYYY'
    });
});
function timep() {
    $('#selectTime').timepicker().on('show.timepicker', function(e) {
        console.log('The time is ' + e.time.value);
        console.log('The hour is ' + e.time.hours);
        console.log('The minute is ' + e.time.minutes);
        console.log('The meridian is ' + e.time.meridian);
    });
}
</script>