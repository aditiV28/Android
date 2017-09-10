<?php
session_start();
 $hostname = "127.0.0.1";
 $username = "root";
 $password = "";
 $dbname = "login";
 
    try{
        $conn = new PDO("mysql:host=$hostname;dbname=$dbname",$username,$password);
        
    }

catch(PDOException $e)
    {
    echo "Connection failed: " . $e->getMessage();
    }

require 'class.user.php';
$user = new USER($conn);

require 'class.news.php';
$news = new NEWS($conn);

?>