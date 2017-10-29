package com.example.admin.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);

        /*Log.d("Insert:", "Inserting............");
        db.addContact(new Contacts("Aditi","98765432"));
        db.addContact(new Contacts("Amu","75673829475"));
        db.addContact(new Contacts("Unda","8474627495"));

        Log.d("Reading","Reading all contact");
        List<Contacts> contactsList = db.getAllContacts();

        for(Contacts cn: contactsList){
            String log = "Id: " + cn.getId() + "Name:" + cn.getName() + "Phone:" + cn.getPhone();
            Log.d("Name",log);
        }*/

        Log.d("Reading by id...", "Reading by ID");
        Contacts contacts = db.getContact(1);
        String log = "Id:" + contacts.getId() + "Name:" + contacts.getName() + "Phone:" + contacts.getPhone();
        Log.d("Record", log);

        Log.d("Count:", "Count is:");
        int count = db.getCountContacts();
        String log1 = "COUNT IS:" + count;
        Log.d("Count", log1);

        Log.d("Update", "Updating by ID......");
        int update = db.updateContact(new Contacts(1, "Aditee", "98765432"));
        String log2 = "Updated record:" + update;
        Log.d("UPDATE:", log2);
        Contacts contacts1 = db.getContact(1);
        String log3 = "Id:" + contacts.getId() + "Name:" + contacts.getName() + "Phone:" + contacts.getPhone();
        Log.d("Record", log3);

       /* Log.d("Delete", "Deleting record....");
        int stat = db.deleteContact(4);
        String log4 = "Delete:" + stat;
        Log.d("DELETE",log4); */

        Log.d("Reading", "Reading all contact");
        List<Contacts> contactsList = db.getAllContacts();

        for (Contacts cn : contactsList) {
            String log5 = "Id: " + cn.getId() + "Name:" + cn.getName() + "Phone:" + cn.getPhone();
            Log.d("Name", log5);
        }
    }
}
