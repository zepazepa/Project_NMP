<?php 
    error_reporting(E_ERROR | E_PARSE);
    require_once("data.php");
    $conn = new mysqli(SERVER, DATABASE, USERID, PASSWORD);
    
    if ($conn->connect_errno) {
        $arr = array("result" => "ERROR", "message" => "Failed to connect");
        echo json_encode($arr);
        die();
    }

    if (isset($_POST["users_id"]) && isset($_POST["cerbung_id"])) {
        $user_id = $_POST["users_id"];
        $cerbung_id = $_POST["cerbung_id"];

        $sql = "INSERT into following (iduser, idcerbung) VALUES (?, ?)";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("ss", $user_id, $cerbung_id);
        $stmt->execute();
        $array = array("result" => "OK");
        echo json_encode($array);
    } else {
        echo json_encode(array("result" => "ERROR", "msg" => "Error making follow"));       
    }
?>