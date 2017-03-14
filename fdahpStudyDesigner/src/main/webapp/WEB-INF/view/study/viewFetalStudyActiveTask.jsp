<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="changeContent">
                    <div class="pt-lg">
                        <div class="gray-xs-f mb-sm">Title 1</div>
                         <div>
                             <div class="form-group">
                                 <input type="text" class="form-control"/>  
                                 <div class="help-block with-errors red-txt"></div>
                            </div>
                        </div>                            
                    </div>
                    
                    <div>
                        <div class="gray-xs-f mb-sm">Title 2</div>
                         <div class="add_notify_option">
                             <div class="form-group">
                                 <input type="text" class="form-control"/>  
                                 <div class="help-block with-errors red-txt"></div>
                            </div>
                        </div>                            
                    </div>
                    
                    
                    
                    <div class="mt-xlg blue-md-f text-uppercase">Configurable parameters</div>
                    <div class="gray-xs-f mt-md mb-sm">Instructions</div>
                    <div class="form-group">                     
                      <textarea class="form-control" rows="5" id="comment"></textarea>
                      <div class="help-block with-errors red-txt"></div>
                    </div>
                    
                    <div class="gray-xs-f mt-md mb-sm">Duration over which to record the kick count</div>                    
                    <div class="form-group col-md-2 p-none hrs">
                         <input type="text" class="form-control pr-xlg"/>  
                         <span>hr</span>
                         <div class="help-block with-errors red-txt"></div>
                    </div>
                    
                    <div class="clearfix"></div>
                    
                    <div class="blue-md-f text-uppercase">Results captured from the task</div>
                    <div class="pt-xs">
                        <div class="bullets bor-b-2-gray black-md-f pt-md pb-md">Duration over which kick count is recorded</div>
                        <div class="bullets black-md-f pt-md">Number of kicks recorded</div>
                        
                        <div class="pl-xlg ml-xs bor-l-1-gray mt-lg">
                        
                          <div class="mb-lg">
                            <span class="checkbox checkbox-inline">
                                <input type="checkbox" id="inlineCheckbox1" value="option1">
                                <label for="inlineCheckbox1">Add to line chart</label>
                            </span>  
                          </div>   
                            
                          <div class="pb-lg">
                            <div class="gray-xs-f mt-md mb-sm">Time range for the chart</div>
                             <div class="add_notify_option">
                                <select class="selectpicker">
                                  <option>Days of the current week</option>
                                  <option>A Study for Pregnant Women</option>
                                  <option>Medication Survey 2</option>
                                </select>
                            </div> 
                          </div>
                            
                          <div class="pb-lg">
                              <div class="gray-xs-f mb-sm">Allow rollback of chart?</div>
                              <div>
                                <span class="radio radio-info radio-inline p-45">
                                    <input type="radio" id="inlineRadio1" value="option1" name="radioInline1">
                                    <label for="inlineRadio1">Yes</label>
                                </span>
                                <span class="radio radio-inline">
                                    <input type="radio" id="inlineRadio2" value="option1" name="radioInline1">
                                    <label for="inlineRadio2">No</label>
                                </span>
                              </div>
                          </div>
                            
                        <div class="bor-b-dash">
                            <div class="gray-xs-f mb-sm">Title for the chart</div>
                             <div class="add_notify_option">
                                 <div class="form-group">
                                     <input type="text" class="form-control"/>  
                                     <div class="help-block with-errors red-txt"></div>
                                </div>
                            </div>                            
                        </div>
                            
                         <div class="pt-lg mt-xs pb-lg">
                            <span class="checkbox checkbox-inline">
                                <input type="checkbox" id="inlineCheckbox1" value="option1">
                                <label for="inlineCheckbox1">Use for Statistic</label>
                            </span>  
                          </div>
                            
                          <div>
                            <div class="gray-xs-f mb-sm">Short identifier name</div>
                             <div class="add_notify_option">
                                 <div class="form-group">
                                     <input type="text" class="form-control"/>  
                                     <div class="help-block with-errors red-txt"></div>
                                </div>
                            </div>                            
                         </div>
                            
                         <div>
                            <div class="gray-xs-f mb-sm">Display name for the Stat (e.g. Total Hours of Activity Over 6 Months)</div>
                             <div class="form-group">
                                 <input type="text" class="form-control"/>  
                                 <div class="help-block with-errors red-txt"></div>
                            </div>
                         </div>
                            
                         <div>
                            <div class="gray-xs-f mb-sm">Display Units (e.g. hours)</div>
                             <div class="add_notify_option">
                                 <div class="form-group">
                                     <input type="text" class="form-control"/>  
                                     <div class="help-block with-errors red-txt"></div>
                                </div>
                             </div>
                         </div>
                            
                         <div>
                            <div class="gray-xs-f mb-sm">Display Units (e.g. hours)</div>
                             <div class="add_notify_option">
                                  <select class="selectpicker">
                                      <option>Select</option>
                                      <option>A Study for Pregnant Women</option>
                                      <option>Medication Survey 2</option>
                                  </select>
                                 <div class="help-block with-errors red-txt"></div>
                             </div>
                         </div>
                            
                         <div>
                            <div class="gray-xs-f mb-sm">Formula for to be applied</div>
                             <div class="form-group">
                                 <input type="text" class="form-control"/>  
                                 <div class="help-block with-errors red-txt"></div>
                            </div>
                         </div>
                            
                         <div>
                            <div class="gray-xs-f mb-sm">Time ranges options available to the mobile app user</div>
                             <div class="add_notify_option">
                                  <select class="selectpicker">
                                      <option>Current Week</option>
                                      <option>A Study for Pregnant Women</option>
                                      <option>Medication Survey 2</option>
                                  </select>
                                 <div class="help-block with-errors red-txt"></div>
                             </div>
                         </div>
                         
                            
                        </div>
                    </div> 
                    
                    </div>