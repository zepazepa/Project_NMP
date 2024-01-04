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
        $sql = "SELECT c.idcerbung, c.title, u.name, g.nama, c.num_like, c.img_url, c.description,  COUNT(p.idpar) as num_paragraf, c.akses, c.waktu_dibuat
        FROM cerbung c INNER join users u on c.users_id = u.iduser
        INNER JOIN genres g on c.genres_id = g.id
        INNER JOIN paragraf p on p.cerbung_id = c.idcerbung
        GROUP BY c.idcerbung
        HAVING c.idcerbung = $cerbung_id";
    
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