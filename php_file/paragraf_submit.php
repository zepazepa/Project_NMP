<?php 
    error_reporting(E_ERROR | E_PARSE);
    require_once("data.php");
    $conn = new mysqli(SERVER, DATABASE, USERID, PASSWORD);
    
    if ($conn->connect_errno) {
        $arr = array("result" => "ERROR", "message" => "Failed to connect");
        echo json_encode($arr);
        die();
    }

    if (isset($_POST["cerbung_id"]) && isset($_POST["users_id"]) && isset($_POST["isi"])) {
        $cerbung_id = $_POST["cerbung_id"];
        $users_id = $_POST["users_id"];
        $isi = $_POST["isi"];

        $sql = "INSERT INTO paragraf(cerbung_id, users_id, isi) VALUES (?,?,?)";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("sss", $cerbung_id, $users_id, $isi);
        $stmt->execute();
        $array = array("result" => "OK");
        echo json_encode($array);
    } else {
        echo json_encode(array("result" => "ERROR", "msg" => "Error making user"));       
    }
?>