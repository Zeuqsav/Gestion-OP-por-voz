package com.zeuqsavopia.android;

import com.zeuqsavopia.android.R;

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


public class MainActivitycreatarea extends Activity {

	private RelativeLayout pantalla;
	private Context context;
	private CharSequence text;
	private int duration;
	private Button btact;
	private Button bttarea;
	private Button btcom;
	private EditText tact;
	private EditText ttarea;
	private EditText tcom;
	private DatabaseHelper dbusu;
	private Intent i;
	
	
		
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activitycreatarea);
		btact = (Button) findViewById(R.id.Button01);
		bttarea = (Button) findViewById(R.id.Button02);
		btcom = (Button) findViewById(R.id.Button03);
		tact = (EditText) findViewById(R.id.txtAct) ;
		ttarea = (EditText) findViewById(R.id.txtTarea) ;
		tcom = (EditText) findViewById(R.id.txtCom) ;
    	context = getApplicationContext();
    	text = "paso 1";
    	duration = Toast.LENGTH_SHORT;
    	dbusu = new DatabaseHelper(this);
    	
    	
		btact.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				
		    	SQLiteDatabase db = dbusu.getWritableDatabase();		
		    	String[] args = new String[] {tact.getText().toString().toLowerCase()};
				Cursor c = db.rawQuery("select * from usuarios where usuario = ?", args);
				if (c.moveToFirst()) 
				{
				    // do {
				          String usuario= c.getString(1);
				          String password = c.getString(2);
				      
				           if (usuario.equals(tact.getText().toString())   && password.equals(tact.getText().toString())){
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
