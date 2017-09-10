<?php
require_once 'connect.php';
if($_SERVER['REQUEST_METHOD'] == 'POST')
    {
        
        $umail = $_POST['email'];
       

        if($user->forgot_password($umail))
        {
          
               $success = "Password changed !";
            echo json_encode($success);
             
        }
        else
        {
            $error = "failed to reset !";
            echo json_encode($error);
        }
    }
?>