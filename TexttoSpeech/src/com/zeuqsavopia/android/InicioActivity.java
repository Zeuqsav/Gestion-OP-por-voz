package com.zeuqsavopia.android;

import com.prgguru.android.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class InicioActivity extends Activity {

	private RelativeLayout pantalla;
	private Context context;
	private CharSequence text;
	private int duration;
	private Intent i;
	
	
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio);
		pantalla = (RelativeLayout) findViewById(R.id.Lay);
    	context = getApplicationContext();
    	text = "paso 1";
    	duration = Toast.LENGTH_SHORT;

		pantalla.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
            	Toast toast = Toast.makeText(context, text, 1000);
            	toast.show();  	
            	i = new Intent(getApplicationContext(),Login_Activity.class);
                startActivity(i);

				
				
			}
		});
		
}
	
	public void onDestroy() {
		super.onDestroy();
	}
	
	public void onInit(int status) {
		
	}
}

	
	