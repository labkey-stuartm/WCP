<!-- Start left Content here -->
         <!-- ============================================================== -->        
        <div class="left-content">
            <div class="left-content-container" id="studyMenuId">
                <ul>
                    <li class="bsInfo">1.  Basic Information</li>
                    <li class="st&Admins">2.  Settings and Admins</li>
                    <li class="overview">3.  Overview</li>
                    <li class="elbilty">4.  Eligibility</li>
                    <li class="const">5.  Consent</li>
                    <li class="stExc">6.  Study Exercises</li>
                    <li class="stDash">7.  Study Dashboard</li>
                    <li class="misl">8.  Miscellaneous</li>
                    <li class="chklst">9.  Checklist</li>
                    <li class="acts">10. Actions</li>                   
                </ul>
            </div>
        </div>
        <!-- End left Content here -->
<script type="text/javascript">
$(document).ready(function(){
   $('#createStudyId').show();
   // Fancy Scroll Bar
   $(".left-content").niceScroll({cursorcolor:"#95a2ab",cursorborder:"1px solid #95a2ab"});
   $(".right-content-body").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"});
   $("#myNavbar li.studyClass").addClass('active');
});
</script>