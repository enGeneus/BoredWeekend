
(function ($) {
  "use strict";

  var bwBaseURL = "/getWeekends.php";
  var categories = ['Outdoor', 'Sport', 'Family', 'CityLife', 'Culture', 'Nature'];
  var selectedCategories = [];

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

  $("button.submit").click(function(e){
    e.preventDefault();

    var cityValue = $("#input-city").val();
    var fromDateValue = $("#input-startdate").val();
    var endDateValue = $("#input-enddate").val();
    var nearbyValue = $("#input-nearby").val();

    var preferences;
    if (selectedCategories.length == 0) {
      preferences = categories;
    } else {
      preferences = selectedCategories;
    }

    $.ajax(bwBaseURL, {
      method: "GET",
      dataType: "json",
      data: {
        "daytime": "FullDay",
        "where": cityValue,
        "start": fromDateValue,
        "end": endDateValue,
        "traveldistance": nearbyValue,
        "preferences": preferences.toString()
      },
      success: function(response) {
        buildResponsePage(response);
      },
      error: function(xhr) {
        alert("Error " + xhr.status);
      }
    });
  });

  function buildResponsePage(response) {
    var days = response["days"];
    for (var dayIndex in days) {
      var day = days[dayIndex];
      var dayNumber = day["dayNumber"];
      var month = day["month"];
      var forecast = day["weatherForecast"];

      var dayWrapper = $("#day-wrapper").clone();
      dayWrapper.removeAttr("id");
      dayWrapper.find("h2").text(forecast);

      for (var eventIndex in day["events"]) {
        var event = day["events"][eventIndex];

        // COMPLETE THIS FOR CYCLE
      }

      for (var activityIndex in day["activities"]) {
        var activity = day["activities"][activityIndex];
        var name = activity["name"];
        var city = activity["city"];
        var daytime = activity["daytime"];
        var info = activity["info"];

        var activityWrapper = $("#element_content").clone();
        // activityWrapper.find("")
        activityWrapper.removeAttr("id");

        activityWrapper.find(".stuff_category").text(city);
        activityWrapper.find(".stuff_date").html(month.substring(0, 3) + " <strong>" + dayNumber + "</strong>");
        activityWrapper.find("h2 a").text(name);
        activityWrapper.find("p").text(info);

        activityWrapper.removeClass("hidden");
        dayWrapper.append(activityWrapper);
      }

      $(".element_container").append(dayWrapper);
      dayWrapper.removeClass("hidden");
    }
    $("#results").removeClass("hidden");
    $("#form").fadeOut(function(){
      $("section#results").fadeIn();
    });
  }

})(jQuery);
