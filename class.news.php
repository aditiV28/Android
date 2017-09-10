<?php
class NEWS{
    
    private $db;
    
    function __construct($conn){
        $this->db = $conn;
        
    }
    
    public function show_feed(){
             
        try{
            
            $stmt = $this->db->prepare("SELECT * FROM news ORDER BY timestamp DESC");
            $stmt->execute();
            $news_row = $stmt->fetchAll(PDO::FETCH_ASSOC);
            if($stmt->rowCount() > 0){
                echo json_encode($news_row);
                return true;
            }
            else{
                return false;
            }
        }
        catch(PDOException $e){
            $e->getMessage();
        }
    
    
        
    }
    
    public function insert_feed($title,$description){
       
       try{
           echo $title;
           echo $description;
           date_default_timezone_set("Asia/Kolkata");
           $date = date("Y-m-d h:i:s");
           $stmt1 = $this->db->prepare("INSERT INTO news (title,description,timestamp) VALUES (:title,:description,:date)");
            $stmt1->bindParam(":title",$title);
           $stmt1->bindParam(":description",$description);
           $stmt1->bindParam(":date",$date);
           $stmt1->execute();
           /*$stmt1->execute(array(
                'title'=>$title,
                'description'=>$description));*/
            /*$stmt = "INSERT INTO news(title,description) VALUES ('".$title."','".$description."')";
          */
           
        
           
           
           return true;
        
          }
        
       catch(PDOException $e){
           echo $e->getMessage();
           return false;
       }
        
        
        
    }
    
    public function edit_feed($title,$description){
        try{
            $stmt = $this->db->prepare("UPDATE news SET description = :description WHERE title = :title");
            $stmt->execute(array(
                'title'=>$title,
                'description'=>$description));
            $updated_row = $stmt->fetch(PDO::FETCH_ASSOC);
            if($stmt->rowCount() > 0){
                echo json_encode($updated_row);
                return true;
            }
            else{
                return false;
            }
        }
        catch(PDOException $e){
            $e->getMessage();
        }
        
    }
    
    public function delete_feed($title){
        try{
            $stmt = $this->db->prepare("DELETE FROM news WHERE title = :title");
            $stmt->bindParam(':title',$title);
            $result = $stmt->execute();
            if(!$result){
                return false;
            }
            else{
                return true;
            }
        }
        catch(PDOException $e){
            $e->getMessage();
        }
    }
}
?>