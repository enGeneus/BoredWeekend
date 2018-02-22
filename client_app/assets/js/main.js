
(function ($) {
  "use strict";

  var bwBaseURL = "getWeekends.php";
  var categories = ['Outdoor', 'Sport', 'Family', 'CityLife', 'Culture', 'Nature'];
  var selectedCategories = [];
  var monthNames = [
    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
  ];
  var dayNames = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

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

    $("#form").hide();
    $(window).scrollTop(0);
    $("#loading").fadeIn();

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
        $("#loading").hide();
        $(window).scrollTop(0);
        $("#form").fadeIn();
      }
    });
  });

  $(document).on("click", "a.more", function(e){
    e.preventDefault();
    $(this).parent().parent().find("span.text-cutter").hide();
    $(this).parent().hide();
    $(this).parent().parent().find(".minimized").slideDown();
    $(this).parent().parent().find(".less-button").slideDown();
  });

  $(document).on("click", "a.less", function(e){
    e.preventDefault();
    $(this).parent().parent().find("span.text-cutter").show();
    $(this).parent().hide();
    $(this).parent().parent().find(".minimized").slideUp();
    $(this).parent().parent().find(".more-button").slideDown();
  });

  $(document).on("click", "a.back-button", function(e){
    e.preventDefault();
    var results = $("#results .day-contents");
    $("#results").fadeOut(function() {
      results.remove();
      $("#form").fadeIn();
    })
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

        var name = event["name"];
        var place = event["city"];
        var locationName = event["locationName"];
        var description = event["description"];
        var info = event["info"];
        var address = event["address"];
        var imgURL = event["img"];
        var startDate = new Date(event["start"]);
        var endDate = new Date(event["end"]);
        var payment = event["payment"];
        var category = event["categories"];

        var eventWrapper = $("#event_content").clone();
        eventWrapper.removeAttr("id");


        if (locationName != null && locationName != "") {
          place += " | " + locationName;
        }
        eventWrapper.find(".place").html("<i class=\"fa fa-map-marker\"></i>&nbsp;" + place);
        eventWrapper.find(".stuff_date").html(month.substring(0, 3) + " <strong>" + dayNumber + "</strong>");
        eventWrapper.find(".title").text(name);
        eventWrapper.find(".image-background").attr("style", 'background-image: url("' + imgURL + '")');

        if (description.length > 200) {
          eventWrapper.find(".description").prepend(description.slice(0,200)+'<span class="text-cutter">... </span>');
          eventWrapper.find(".description .description-overflow").text(description.slice(200,description.length));
        } else {
          eventWrapper.find(".description").text(description);
        }

        eventWrapper.find(".event-info").append(info);
        eventWrapper.find(".location-info").append(locationName + ", " + address);

        var categoryInfos = category;

        if (payment==false) {
          categoryInfos += " | Free event"
        } else {
          categoryInfos += " | Payment required"
        }

        eventWrapper.find(".further-info p.category-info").text(categoryInfos);

        if (startDate.getDate()==endDate.getDate() && startDate.getMonth()==endDate.getMonth()) {
          eventWrapper.find(".further-info p.calendar-info").append(
            dayNames[startDate.getDay()] + " " + startDate.getDate() + " " + monthNames[startDate.getMonth()]
          );
        } else {
          eventWrapper.find(".further-info p.calendar-info").append(
            dayNames[startDate.getDay()] + " " + startDate.getDate() + " " + monthNames[startDate.getMonth()] +
            " - " + dayNames[endDate.getDay()] + " " + endDate.getDate() + " " + monthNames[endDate.getMonth()]
          );
        }

        eventWrapper.removeClass("hidden");
        dayWrapper.append(eventWrapper);
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
        activityWrapper.find(".image-background").attr("style", 'background-image: url("' + imgURL + '")');

        if (info.length > 200) {
          activityWrapper.find(".description").prepend(info.slice(0,200)+'<span class="text-cutter">... </span>');
          activityWrapper.find(".description .description-overflow").text(info.slice(200,info.length));
        } else {
          activityWrapper.find(".description").text(info);
          activityWrapper.find(".more-button").hide();
        }

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

    $("#loading").hide();
    $("section#results").fadeIn();
  }

})(jQuery);
