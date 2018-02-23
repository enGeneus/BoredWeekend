<?php

  $baseURL = "http://localhost:8080/boredWeekendService/insertEvent";
	
	$options = array(
	    'http' => array(
	        'header'  => "Content-type: application/json\r\n",
	        'method'  => 'POST',
	        'content' => json_encode($_POST)
	    )
	);
	$context  = stream_context_create($options);
	file_get_contents($baseURL, false, $context);
	echo json_encode(array(
    'status' => 'success',
    'message'=> 'success message'
    ));
?>
