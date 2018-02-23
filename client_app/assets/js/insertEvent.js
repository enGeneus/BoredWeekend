
(function ($) {
  "use strict";

  var bwBaseURL = "insertEvent.php";


  $("button.submit").click(function(e){
    e.preventDefault();

    var nameValue = $("#input-name").val();
    var cityValue = $("#input-city").val();
    var categoryValue = $("#input-category").val();
    var locationNameValue = $("#input-locationName").val();
    
    var startDateValue = $("#input-startdate").val();
    var startTimeValue = $("#input-starttime").val();
    
    var endDateValue = $("#input-enddate").val();
    var endTimeValue = $("#input-endtime").val();
    
    var startValue = startDateValue.concat("T", startTimeValue, ":00");
    var endValue = endDateValue.concat("T", endTimeValue, ":00");
    
    var addressValue = $("#input-address").val();
    var infoValue = $("#input-info").val();
    var payValue = $("#input-payment").val();
    var descriptionValue = $("#input-description").val();
    var imgValue = $("#input-img").val();
    
    
    
    console.log(nameValue);
    console.log(cityValue);
    console.log(categoryValue);
    console.log(locationNameValue);
    console.log(startValue);
    console.log(endValue);
    console.log(addressValue);
    console.log(infoValue);
    console.log(payValue);
    console.log(descriptionValue);
    console.log(imgValue);
    
    $.ajax(bwBaseURL, {
      method: "POST",
      dataType: "json",
      data: {
    	  	"city":cityValue,
    	  	"name":nameValue,
    	  	"address":addressValue,
    	  	"category":categoryValue,
    	  	"locationName":locationNameValue,
    	  	"start":startValue,
    	  	"end":endValue,
    	  	"description":descriptionValue,
        "info": infoValue,
        "payment": payValue=='1' ? true : false,
        "img":imgValue
      },

      success: function(response) {
    	  if(response.status === "success") {
  	  	alert("event added");
    	  }
      },
      error: function(xhr) {
        alert("Error " + xhr.responseText);
      }
      
    });
  });

})(jQuery);
