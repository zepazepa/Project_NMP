<?php 
    error_reporting(E_ERROR | E_PARSE);
    require_once("data.php");
    $conn = new mysqli(SERVER, DATABASE, USERID, PASSWORD);
    
    if($conn->connect_errno) {
        $error = array('result' => 'ERROR', 'message' => 'Failed to connect');
        echo json_encode($error);
        die();
    }

    if (isset($_POST["username"])) {
        $username = $_POST["username"];
 
        $conn->set_charset("UTF8");
        $sql = "SELECT * FROM users where username=?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("s", $username);
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