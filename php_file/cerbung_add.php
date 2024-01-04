<?php 
    error_reporting(E_ERROR | E_PARSE);
    require_once("data.php");
    $conn = new mysqli(SERVER, DATABASE, USERID, PASSWORD);
    
    if ($conn->connect_errno) {
        $arr = array("result" => "ERROR", "message" => "Failed to connect");
        echo json_encode($arr);
        die();
    }

    if (isset($_POST["title"]) && isset($_POST["description"]) && isset($_POST["num_like"]) 
    && isset($_POST["akses"]) && isset($_POST["genre"]) && isset($_POST["img_url"]) && isset($_POST['paragraf'])
     && isset($_POST["users_id"])) {
        $title = $_POST["title"];
        $desc = $_POST["description"];
        $num_likes = intval($_POST["num_like"]);
        $img_url = $_POST["img_url"];
        $genre = $_POST["genre"];
        $akses = $_POST["akses"];
        $users_id = intval($_POST["users_id"]);
        $paragraf = $_POST['paragraf'];

        $sql = "SELECT * FROM genres where nama = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("s", $genre);
        $stmt->execute();
        $result = $stmt->get_result();
        if ($result->num_rows > 0) {
            $row = $result->fetch_assoc();
            $genres_id = intval($row['id']);
        }

        $sql = "INSERT into cerbung (title,description,num_like, img_url, users_id,genres_id,akses) VALUES (?,?,?,?,?, ?, ?)";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("ssisiis", $title, $desc, $num_likes,$img_url,$users_id,$genres_id,$akses);
        $stmt->execute();
        $id_cerbung = $stmt->insert_id;

        $sql2 = "INSERT into paragraf(cerbung_id,users_id,isi) VALUES (?,?,?)";
        $stmt2 = $conn->prepare($sql2);
        $stmt2 ->bind_param("iis",$id_cerbung,$users_id,$paragraf);
        $stmt2->execute();

        $array = array("result" => "OK","akses"=>$akses,"users_id"=>$users_id,"id_cerbung"=>$id_cerbung);
        echo json_encode($array);
    } else {
        echo json_encode(array("result" => "ERROR", "msg" => "Error making cerbung"));       
    }
?>