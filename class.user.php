<?php
class USER
{
    private $db;

    function __construct($conn)
    {
      $this->db = $conn;
    }

    public function insert_user($uname,$upass,$umail){
      try{
        $stmt = $this->db->prepare("INSERT INTO users(user_name,password,email) VALUES (:uname,:upass,:umail)");
        $stmt->bindParam(':uname',$uname);
        $stmt->bindParam(':upass',$upass);
        $stmt->bindParam(':umail',$umail);
        $result = $stmt->execute();
        if(!$result){
          return false;
        }
        else{
          return true;
        }
      }
      catch(Exception $e){
        echo $e->getMessage();
      }
    }
    public function login($uname,$upass)
    {
       try
       {
          $stmt = $this->db->prepare("SELECT * FROM users WHERE user_name=:uname  LIMIT 1");
         // $stmt->execute(array(':uname'=>$uname, ':umail'=>$umail));
          $stmt->bindParam(':uname',$uname);
          $stmt->execute();
          $userRow=$stmt->fetch(PDO::FETCH_ASSOC);
          if($stmt->rowCount() > 0)
          {
              
             //if(password_verify($upass,password_hash($userRow['password'],PASSWORD_DEFAULT )))
            
              if(md5($upass)==$userRow['password'])
             {
                $_SESSION['user_session'] = $userRow['user_name'];
                /*$stmt = $this->db->prepare("UPDATE users SET status=1 WHERE user_name=:uname");
                $stmt->bindparam(":uname", $uname);
                $stmt->execute();*/
                
                //echo json_encode(password_hash($userRow['password'],PASSWORD_DEFAULT ));
                
                
               
                return true;
             }
             else
             {
                return false;
             }
          }
          else {
            return false;
          }
       }
       catch(PDOException $e)
       {
           echo $e->getMessage();
       }
   }

   public function is_loggedin()
   {
      if(!empty($_SESSION['user_session']))
      {
        return true;
      }
      else{
       return false;
      }
   }

   public function redirect($url)
   {
       header("Location: $url");
   }

   public function logout()
   {
     try {
         /* $stmt1 = "UPDATE users SET status=0 WHERE user_name ='$username'";
          $this->db->exec($stmt1);*/
          session_destroy();
          unset($_SESSION['user_session']);
          return true;

     } catch (PDOException $e) {
       echo $e->getMessage();
       return false;
     }

   }
    
    public function sendMail($umail){
            $stmt = $this->db->prepare("SELECT * FROM users WHERE  email=:umail LIMIT 1");
           $stmt->bindparam(':umail',$umail);
           $stmt->execute();
        $userRow=$stmt->fetch(PDO::FETCH_ASSOC);
          if($stmt->rowCount() > 0)
          {
                require_once "vendor/autoload.php";
                require_once "vendor/phpmailer/phpmailer/class.phpmailer.php";

                //PHPMailer Object
                $mail = new PHPMailer;

                //From email address and name
                $mail->From = "belleza.developers.team@gmail.com"; 
                $mail->FromName = "Aditi Vasekar";

                //To address and name
                //$mail->addAddress("belleza.developers.team@gmail.com", "Aditi Vasekar");
                $mail->addAddress($umail); //Recipient name is optional

                //Address to which recipient will reply
               // $mail->addReplyTo($umail, "Reply");

                //CC and BCC
                

                //Send HTML or Plain Text email
                $mail->isHTML(true);

                $mail->Subject = "Password Reset";
                $mail->Body = "<i>Please click on below link to reset your password:</i> <br> http://127.0.0.1/SMS/resetPass.html";
               // $mail->AltBody = "This is the plain text version of the email content";

                if(!$mail->send()) 
                {
                    echo "Mailer Error: " . $mail->ErrorInfo;
                    return false;
                } 
                else 
                {
                    echo "Message has been sent successfully";
                    return true;
                }
        }
        else
       {
           echo json_encode("Falied to send mail!!");
            return false;
       }
       
    }
    
   public function forgot_password($uname,$newpass)
   {
           $stmt = $this->db->prepare("SELECT * FROM users WHERE user_name=:uname LIMIT 1");
           $stmt->bindParam(':uname',$uname);
           $stmt->execute();
          $userRow=$stmt->fetch(PDO::FETCH_ASSOC);
          if($stmt->rowCount() > 0)
          {
                if($uname == $userRow['user_name']){
                        $hashed_new_pass = md5($newpass);
                        $stmt2 = $this->db->prepare("UPDATE users SET password=:new_password WHERE user_name=:uname");
                        $stmt2->bindparam(":uname", $uname);
                        $stmt2->bindparam(":new_password",$hashed_new_pass);
                        $stmt2->execute();
                         return true;
                        }
                    else{
                        return false;
                    }    
                    
                    
              
          }
       else
       {
           echo json_encode("Falied to change password!!");
       }
       
       
   }
  public function change_password($uname,$upass,$newpass){
     try
       {
          $stmt = $this->db->prepare("SELECT * FROM users WHERE user_name=:uname LIMIT 1");
          $stmt->bindParam(':uname',$uname);
          $stmt->execute();
         // $stmt->execute(array(':uname'=>$uname, ':umail'=>$umail));
          $userRow=$stmt->fetch(PDO::FETCH_ASSOC);
          if($stmt->rowCount() > 0)
          {
              //echo json_encode("user present");
             //if(password_verify($upass,password_hash($userRow['password'],PASSWORD_DEFAULT )))
            
              if(md5($upass)==$userRow['password'])
             {
                $hashed_new_pass=md5($newpass);
                //$_SESSION['user_session'] = $userRow['user_id'];
                $stmt = $this->db->prepare("UPDATE users SET password=:new_password WHERE user_name=:uname");
                $stmt->bindparam(":uname", $uname);
                $stmt->bindparam(":new_password",$hashed_new_pass );
                $stmt->execute();
                //echo json_encode(password_hash($userRow['password'],PASSWORD_DEFAULT ));
                return true;
             }
             else
             {
                return false;
             }
          }
          else {
            return false;
          }
       }
       catch(PDOException $e)
       {
           echo $e->getMessage();
       }
  }


 

  

}
?>
