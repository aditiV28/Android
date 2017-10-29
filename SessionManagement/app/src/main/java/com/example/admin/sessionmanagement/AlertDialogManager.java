package com.example.admin.sessionmanagement;

/**
 * Created by ADMIN on 29-10-2017.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogManager {
    public void showAlertDialog(Context context,String title,String message,Boolean status){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        alertDialog.setButton("OK",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){

            }
        });
        alertDialog.show();
    }
}