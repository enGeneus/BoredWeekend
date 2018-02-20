
(function ($) {
  "use strict";

  var bwBaseURL = "/insertEvent.php";


  $("button.submit").click(function(e){
    e.preventDefault();
    
    var cityValue = $("#input-city").val();
    var nameValue = $("#input-name").val();
    var categoryValue = $("#input-category").val();
    var locationNameValue = $("#input-locationName").val();
    
    var startDateValue = $("#input-startdate").val();
    var startTimeValue = $("#input-starttime").val();
    
    var endDateValue = $("#input-enddate").val();
    var endTimeValue = $("#input-endtime").val();
    
    var addressValue = $("#input-address").val();
    var infoValue = $("#input-info").val();
    var payValue = $("#input-payment").val();
    var descriptionValue = $("#input-description").val();
    var imgValue = $("#input-img").val;


    $.ajax(bwBaseURL, {
      method: "POST",
      dataType: "json",
      data: {
    	  	"city":cityValue,
    	  	"name":nameValue,
    	  	"address":addressValue,
    	  	"category":categoryValue,
    	  	"locationName":locationNameValue,
    	  	"start":startDateValue+" "+startTimeValue+":00",
    	  	"end":endtDateValue+" "+endTimeValue+":00",
    	  	"description":descriptionValue,
        "info": infoValue,
        "payment": payValue,
        "img":imgValue
        
      },

      success: function(response) {
    	  	alert("aggiunto");
      },
      error: function(xhr) {
        alert("Error " + xhr.responseText);
      }
      
    });
  });

})(jQuery);
