<?php 
    error_reporting(E_ERROR | E_PARSE);
    require_once("data.php");
    $conn = new mysqli(SERVER, DATABASE, USERID, PASSWORD);
    
    if ($conn->connect_errno) {
        $arr = array("result" => "ERROR", "message" => "Failed to connect");
        echo json_encode($arr);
        die();
    }

    if (isset($_POST["old_pass"]) && isset($_POST["new_pass"]) && isset($_POST["user_id"])) {
        $old_pass = $_POST["old_pass"];
        $new_pass = $_POST["new_pass"];
        $user_id = $_POST["user_id"];

        $sql = "SELECT * FROM users WHERE iduser=?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("i", $user_id);
        $stmt->execute();
        $res = $stmt->get_result();
        
        if ($res->num_rows > 0) {
            while ($row = $res->fetch_assoc()) {
                $pass_db = $row["password"];
                if ($pass_db === $old_pass) {
                    $sql = "UPDATE users SET password=? WHERE iduser=?";
                    $stmt = $conn->prepare($sql);
                    $stmt->bind_param("si", $new_pass, $user_id);
                    $stmt->execute();
                    $array = array("result" => "OK");
                    echo json_encode($array);
                }
            }
        } else {
            echo json_encode(array("result" => "ERROR", "msg" => "No data found"));
        }
    } else {
        echo json_encode(array("result" => "ERROR", "msg" => "Cannot change password"));
    }
?>