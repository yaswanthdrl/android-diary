package com.example.prdiary;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener
{
    EditText Rollno,Name;
    Button Insert,Delete,View,explore;
    SQLiteDatabase db;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Rollno=(EditText)findViewById(R.id.ddate);
        Name=(EditText)findViewById(R.id.ddata);
        explore=(Button)findViewById(R.id.button1);
        Insert= (Button) findViewById(R.id.Insert);
        Delete= (Button) findViewById(R.id.Delete);
        View=(Button)findViewById(R.id.View);
        Insert.setOnClickListener(this);
        Delete.setOnClickListener(this);
        View.setOnClickListener(this);
        // Creating database and table
        db=openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno VARCHAR,name VARCHAR);");
    }
    public void onClick(View view)
    {
        // Inserting a record to the Student table
        if(view==Insert)
        {
            // Checking for empty fields
            if(Rollno.getText().toString().trim().length()==0||
                    Name.getText().toString().trim().length()==0
                    )
            {
                showMessage("Error", "please enter all fields");
                return;
            }
            db.execSQL("INSERT INTO student VALUES('"+Rollno.getText()+"','"+Name.getText()+
                    "');");
            showMessage("Success", "Data saved📕");
            clearText();
        }
        // Deleting a record from the Student table
        if(view==Delete)
        {
            // Checking for empty roll number
            if(Rollno.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter Date📅");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+Rollno.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("DELETE FROM student WHERE rollno='"+Rollno.getText()+"'");
                showMessage("Success", "Data deleted");
            }
            else
            {
                showMessage("Error", "Enter existing Date📅");
            }
            clearText();
        }
        // Updating a record in the Student table

        // Display a record from the Student table
        if(view==View)
        {
            // Checking for empty roll number
            if(Rollno.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter Date📅");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+Rollno.getText()+"'", null);
            if(c.moveToFirst())
            {
                Name.setText(c.getString(1));

            }
            else
            {
                showMessage("Error", "No Data found");
                clearText();
            }
        }


    }
    public void showMessage(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        Rollno.setText("");
        Name.setText("");
        Rollno.requestFocus();
    }

    public void jp(android.view.View view) {
        Insert.setVisibility(view.VISIBLE);
        Delete.setVisibility(view.VISIBLE);
        View.setVisibility(view.VISIBLE);
    }
}