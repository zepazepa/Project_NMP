<?php 
    error_reporting(E_ERROR | E_PARSE);
    $conn = new mysqli("localhost", "native_160421033", "ubaya", "native_160421033");
    
    if ($conn->connect_errno) {
        $arr = array("result" => "ERROR", "message" => "Failed to connect");
        echo json_encode($arr);
        die();
    }

    if (isset($_POST["username"]) && isset($_POST["password"])) {
        $username = $_POST["username"];
        $password = $_POST["password"];

        $conn->set_charset("UTF8");
        $sql = "SELECT * FROM users where name=? AND password=?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("ss", $username, $password);
        $stmt->execute();
        $array = array();
        $result = $stmt->get_result();
        if ($result->num_rows > 0) {
            while ($obj = $result->fetch_object()) {
                $array[] = $obj;
                echo json_encode(array("result" => "OK", "data" => $array));
            }
        } else {
            echo json_encode(array("result" => "ERROR", "data" => "No data found"));
            die();
        }
    }
?>