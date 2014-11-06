package com.prgguru.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class Login_Activity extends Activity {

	private RelativeLayout pantalla;
	private Context context;
	private CharSequence text;
	private int duration;
	private Button aceptar;
	private EditText usu;
	private EditText pass;
	private DatabaseHelper dbusu;
	private Intent i;
	
	
		
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_);
		aceptar = (Button) findViewById(R.id.button1);
		usu = (EditText) findViewById(R.id.editText1) ;
		pass = (EditText) findViewById(R.id.editText2) ;
    	context = getApplicationContext();
    	text = "paso 1";
    	duration = Toast.LENGTH_SHORT;
    	dbusu = new DatabaseHelper(this);
    	
    	
		aceptar.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				
		    	SQLiteDatabase db = dbusu.getWritableDatabase();		
            	String[] args = new String[] {usu.getText().toString()};
				Cursor c = db.rawQuery("select * from usuarios where usuario = ?", args);
				
				if (c.moveToFirst()) 
				{
				    // do {
				          String usuario= c.getString(1);
				          String password = c.getString(2);
				      
				           if (usuario.equals(usu.getText().toString())   && password.equals(pass.getText().toString())){
				        	   Toast toast = Toast.makeText(context, "OK " + usuario + Environment.getDataDirectory().toString()  , 500);
				        	   toast.show();
				        	   
				        	   i = new Intent(getApplicationContext(),TexttoSpeechActivity.class);
				               startActivity(i);
				           //    break;
				               
				           }
				           else 
				           {
				        	   Toast toast = Toast.makeText(context, "NO OK", 1000);
				        	   toast.show();
				           }
				        	   
				           db.close();		          
				    // } while(c.moveToNext());
				}
				
			}
		});
		
}
	
	public void onDestroy() {
		super.onDestroy();
	}
	
	public void onInit(int status) {
		
	}
}
