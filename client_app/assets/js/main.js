
(function ($) {
  "use strict";

  var bwBaseURL = "getWeekends.php";
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
      var dayName = day["day"];
      var dayNumber = day["dayNumber"];
      var month = day["month"];
      var forecast = day["weatherForecast"];

      var dayWrapper = $("#day-wrapper").clone();
      dayWrapper.removeAttr("id");

      dayWrapper.find(".day-name").text(dayName.substring(0,3));
      dayWrapper.find(".day-number").text(dayNumber);
      dayWrapper.find(".month").text(month.substring(0,3));

      if (forecast != null) {
        dayWrapper.find(".forecast-value").text(forecast);
      } else {
        dayWrapper.find(".forecast-title").text("No weather forecast available");
        dayWrapper.find(".forecast-value").remove();
      }

      dayWrapper.find(".overview span").text(day["events"].length + " events and " + day["activities"].length + " activities found");

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
        var imgURL = activity["img"];

        var payment = activity["payment"];
        var categories = activity["categories"];

        var activityWrapper = $("#activity_content").clone();
        activityWrapper.removeAttr("id");

        activityWrapper.find(".place").html("<i class=\"fa fa-map-marker\"></i>&nbsp;" + city);
        activityWrapper.find(".stuff_date").html(month.substring(0, 3) + " <strong>" + dayNumber + "</strong>");
        activityWrapper.find(".title").text(name);
        activityWrapper.find(".description").text(info);
        activityWrapper.find(".image-background").attr("style", 'background-image: url("' + imgURL + '")');

        var furtherInfos = "";
        switch (daytime) {
          case "FullDay":
          furtherInfos = "Full day";
          break;
          default:
          furtherInfos = daytime;
        }
        if (categories.indexOf("Family") != -1) {
            furtherInfos += " | For families";
        }
        if (payment==false) {
          furtherInfos += " | Free activity"
        } else {
          furtherInfos += " | Payment required"
        }

        activityWrapper.find(".further-info p").text(furtherInfos);

        activityWrapper.removeClass("hidden");
        dayWrapper.append(activityWrapper);
      }

      $(".results_container").append(dayWrapper);
      dayWrapper.removeClass("hidden");
    }

    $("#results").removeClass("hidden");
    $("#form").fadeOut(function(){
      $("section#results").fadeIn();
    });
  }

})(jQuery);
