package au.com.deangoddard_17822594.tut7_2;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener
{
    EditText editFName, editLName, editPhone;
    Button btnAdd,btnDelete,btnModify,btnView,btnViewAll;
    SQLiteDatabase db;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Friends text input
        editFName =(EditText)findViewById(R.id.editFName);
        editLName =(EditText)findViewById(R.id.editName);
        editPhone =(EditText)findViewById(R.id.editPhoneNo);


        //Buttons

        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnDelete=(Button)findViewById(R.id.btnDelete);
        btnModify=(Button)findViewById(R.id.btnModify);
        btnView=(Button)findViewById(R.id.btnView);
        btnViewAll=(Button)findViewById(R.id.btnViewAll);

        //Listeners
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnViewAll.setOnClickListener(this);


        //Database
        db=openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(FirstName VARCHAR,LastName VARCHAR,YearOfBirth VARCHAR,Gender VARCHAR);");
        db=openOrCreateDatabase("Login", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Login (UserName VARCHAR,Password VARCHAR,StudentID VARCHAR);");
    }
    public void onClick(View view)
    {
        if(view==btnAdd)
        {
            if(editFName.getText().toString().length()==0||
                    editLName.getText().toString().length()==0||
                    editPhone.getText().toString().length()==0)
            {

                Toast.makeText(getApplicationContext(), "Missing Details!",
                        Toast.LENGTH_LONG).show();

                return;
            }
            db.execSQL("INSERT INTO student VALUES('" + editFName.getText() + "','" + editLName.getText() +
                    "','" + editPhone.getText() + "');");

            Toast.makeText(getApplicationContext(), "Friend has been added!",
                    Toast.LENGTH_LONG).show();


            clearText();
        }
        if(view==btnDelete)
        {
            if(editFName.getText().toString().trim().length()==0)
            {
                Toast.makeText(getApplicationContext(), "ERROR Please enter First Name",
                        Toast.LENGTH_LONG).show();

                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM student WHERE FName='"+ editFName.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("DELETE FROM student WHERE FName='" + editFName.getText() + "'");

                Toast.makeText(getApplicationContext(), "Friend has been DELETED!",
                        Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Invalid First Name!",
                        Toast.LENGTH_LONG).show();

            }
            clearText();
        }
        if(view==btnModify)
        {
            if(editFName.getText().toString().trim().length()==0)
            {
                Toast.makeText(getApplicationContext(), "Please Enter First Name!",
                        Toast.LENGTH_LONG).show();

                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM student WHERE FName='"+ editFName.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("UPDATE student SET name='" + editLName.getText() + "',marks='" + editPhone.getText() +
                        "' WHERE FName='" + editFName.getText() + "'");

                Toast.makeText(getApplicationContext(), "Friend has been Modified!",
                        Toast.LENGTH_LONG).show();


            }
            else
            {
                Toast.makeText(getApplicationContext(), "Invalid Name!",
                        Toast.LENGTH_LONG).show();

            }
            clearText();
        }
        if(view==btnView)
        {
            if(editFName.getText().toString().trim().length()==0)
            {

                Toast.makeText(getApplicationContext(), "Please enter First Name!",
                        Toast.LENGTH_LONG).show();

                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM student WHERE FName='"+ editFName.getText()+"'", null);
            if(c.moveToFirst())
            {
                editLName.setText(c.getString(1));
                editPhone.setText(c.getString(2));
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Invalid Name!",
                        Toast.LENGTH_LONG).show();
                clearText();
            }
        }
        if(view==btnViewAll)
        {
            Cursor c=db.rawQuery("SELECT * FROM student", null);
            if(c.getCount()==0)
            {
                Toast.makeText(getApplicationContext(), "INo Records Found!",
                        Toast.LENGTH_LONG).show();

                return;
            }
            StringBuffer buffer=new StringBuffer();
            while(c.moveToNext())
            {
                buffer.append("FName: "+c.getString(0)+"\n");
                buffer.append("Name: " + c.getString(1) + "\n");
                buffer.append("Marks: "+c.getString(2)+"\n\n");
            }
            Message("Student Details", buffer.toString());
        }

    }
    public void Message(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        editFName.setText("");
        editLName.setText("");
        editPhone.setText("");
        editFName.requestFocus();
    }
}