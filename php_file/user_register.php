<?php 
    error_reporting(E_ERROR | E_PARSE);
    require_once("data.php");
    $conn = new mysqli(SERVER, DATABASE, USERID, PASSWORD);
    
    if ($conn->connect_errno) {
        $arr = array("result" => "ERROR", "message" => "Failed to connect");
        echo json_encode($arr);
        die();
    }

    if (isset($_POST["username"]) && isset($_POST["password"]) && isset($_POST["url_profile"])) {
        $username = $_POST["username"];
        $pass = $_POST["password"];
        $url_profile = $_POST["url_profile"];

        $sql = "INSERT into users (name, password, url_photo) VALUES (?, ?, ?)";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("sss", $username, $pass, $url_profile);
        $stmt->execute();
        $array = array("result" => "OK");
        echo json_encode($array);
    } else {
        echo json_encode(array("result" => "ERROR", "msg" => "Error making user"));       
    }
?>