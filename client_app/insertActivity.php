<?php

  $baseURL = "http://localhost:8080/boredWeekendService/insertActivity";
	
	$options = array(
	    'http' => array(
	        'header'  => "Content-type: application/x-www-form-urlencoded\r\n",
	        'method'  => 'POST',
	        'content' => http_build_query($_POST)
	    )
	);
	$context  = stream_context_create($options);
	file_get_contents($baseURL, false, $context);
	
?>
