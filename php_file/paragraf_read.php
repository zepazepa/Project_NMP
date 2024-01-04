<?php 
    error_reporting(E_ERROR | E_PARSE);
    require_once("data.php");
    $conn = new mysqli(SERVER, DATABASE, USERID, PASSWORD);
    
    if($conn->connect_errno) {
        $error = array('result' => 'ERROR', 'message' => 'Failed to connect');
        echo json_encode($error);
        die();
    }
    if (isset($_POST["cerbung_id"])){
        $cerbung_id = $_POST["cerbung_id"];

        $conn->set_charset("UTF8");
        $sql = "SELECT p.idpar,p.cerbung_id,p.users_id, u.name, p.isi, p.waktu_dibuat
        FROM paragraf p INNER JOIN users u on u.iduser = p.users_id
        WHERE p.cerbung_id = $cerbung_id
        ORDER BY p.idpar";
    
        $result = $conn->query($sql);
        $array = array();
        if ($result->num_rows > 0) {
            while ($obj = $result->fetch_object()) {
                $array[] = $obj;
            }
            echo json_encode(array("result" => "OK", "data" => $array));
        } else {
            echo json_encode(array("result" => "ERROR", "message" => "No data found"));
            die();
        }
    }
    else{
        echo json_encode(array("result" => "ERROR", "message" => "No Data Sent"));
            die();
    }

    
?>