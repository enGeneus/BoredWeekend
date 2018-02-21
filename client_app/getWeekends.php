<?php

  $baseURL = "http://localhost:8080/boredWeekendService/getWeekends?";

  foreach ($_GET as $key => $value) {
    $baseURL = $baseURL . $key . "=" . $value . "&";
  }

   echo file_get_contents($baseURL);
  //echo file_get_contents("test_response.json");

?>
