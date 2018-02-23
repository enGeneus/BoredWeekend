
(function ($) {
  "use strict";

  var bwBaseURL = "/insertActivity.php";
  var categories = ['Outdoor', 'Sport', 'Family', 'CityLife', 'Culture', 'Nature'];
  var days = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
  var selectedCategories = [];
  var selectedDays = [];
  
  $("button.category-filter").click(function(e){
    e.preventDefault();
    if ($(this).hasClass("active")) {
      var index = selectedCategories.indexOf($(this).text());
      if (index > -1) {
        selectedCategories.splice(index, 1);
      }
      $(this).removeClass("active");


    } else {
      selectedCategories.push($(this).text());
      $(this).addClass("active");

    }
  });
  
  $("button.days-filter").click(function(e){
	    e.preventDefault();
	    if ($(this).hasClass("active")) {
	      var index = selectedDays.indexOf($(this).text());
	      if (index > -1) {
	        selectedDays.splice(index, 1);
	      }
	      $(this).removeClass("active");


	    } else {
	      selectedDays.push($(this).text());
	      $(this).addClass("active");

	    }
	  });

  $("button.submit").click(function(e){
    e.preventDefault();

    var cityValue = $("#input-city").val();
    var nameValue = $("#input-name").val();
    var daytimeValue = $("#input-day_time").val();
    var infoValue = $("#input-info").val();
    var payValue = $("#input-payment").val();
    var imgValue = $("#input-img").val();
    
    var preferences;
    if (selectedCategories.length == 0) {
      preferences = categories;
    } else {
      preferences = selectedCategories;
    }
    
    var preferences_days;
    if (selectedDays.length == 0) {
      preferences_days = days;
    } else {
      preferences_days = selectedDays;
    }
  

    $.ajax(bwBaseURL, {
      method: "POST",
      dataType: "json",
      data: {
    	  	"city":cityValue,
    	  	"name":nameValue,
        "daytime": daytimeValue,
        "info": infoValue,
        "payment": payValue=='1' ? true : false,
        "categories": preferences,
        "days": preferences_days,
        "img": imgValue
      },

      success: function(response) {
    	  if(response.status === "success") {
    	  	  	alert("Activity added");
    	  	}
      },
      error: function(xhr) {
        alert("Error " + xhr.responseText);
      }
      
    });
  });

})(jQuery);
