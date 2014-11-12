package com.zeuqsavopia.android;

import java.util.ArrayList;
import java.util.Locale;

import com.zeuqsavopia.android.R;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class TexttoSpeechActivity extends Activity implements OnInitListener {
	/** Called when the activity is first created. */
	protected static final int RESULT_SPEECH = 1;
	
	private TextToSpeech tts;
	private Button btnSpeak;
	private EditText txtText;
	private Button button01;
	private TextView txtTextV;
	private DatabaseHelper dbusu;
	int nactividad;
	private int ntarea;
	private int ncomport;
	private int tareatomada;
	
	
	public  String text;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tts = new TextToSpeech(this, this);
		btnSpeak = (Button) findViewById(R.id.btnSpeak);
		txtText = (EditText) findViewById(R.id.txtText);
		button01 = (Button) findViewById(R.id.Button01);
		txtTextV = (TextView) findViewById(R.id.txtTV);
		dbusu = new DatabaseHelper(this);
		nactividad = 1;
		ntarea =1;
		ncomport=1;
		tareatomada=0;
		
		
		
		btnSpeak.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				speakOut();
			}

		});
		
		button01.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

				try {
					startActivityForResult(intent, RESULT_SPEECH);
					txtTextV.setText("");
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Ops! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}

			}
		});
		
	}

	@Override
	public void onDestroy() {
		// Don't forget to shutdown!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}

	public void onInit(int status) {
		// TODO Auto-generated method stub

		if (status == TextToSpeech.SUCCESS) {

			int result = tts.setLanguage(Locale.getDefault());
			
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Toast.makeText(this, "Language not supported", Toast.LENGTH_LONG).show();
				Log.e("TTS", "Language is not supported");
			} else {
				btnSpeak.setEnabled(true);

			}

		} else {
			Log.e("TTS", "Initilization Failed");
		}

	}

	private void speakOut() {

		text = txtText.getText().toString();
		String respuesta = Respuesta(txtTextV.getText().toString().toLowerCase());
		tts.setPitch(1);
		tts.setSpeechRate(1);
		if (text.length() == 0) {
			tts.speak(respuesta, TextToSpeech.QUEUE_FLUSH, null);
		} else {
			tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
		}

	}
	
	private String Respuesta(String text2) {
		// TODO Auto-generated method stub
				
		SQLiteDatabase db = dbusu.getWritableDatabase();
	    String qry = "select * from actividades where idactividad ='"+ nactividad +"'";
		Cursor c = db.rawQuery(qry, null);
		
		if(tareatomada == 0 && text2.equals("Texto oido") ||  text2.equals(""))
		{	
		if (c.moveToFirst()) 
			{
				//nactividad = nactividad + 1;
				String resp=c.getString(1).toString()+ " , " + c.getString(3).toString()+" , "+ c.getString(5).toString();
				db.close();
				return  (resp) ;
			}
		}
		
		if ((text2.equals("Si") || text2.equals("si")) && tareatomada == 0)
		{
		ntarea = ntarea + 1;
		tareatomada=ntarea;
		
		c = db.rawQuery("select * from actividades where idactividad = '" + nactividad + "' and idtarea = '" + ntarea+"'", null);
			if (c.moveToFirst()) 
			{	
				String resp=c.getString(1).toString() + " , " + c.getString(3).toString() +" , "+ c.getString(5).toString();
				db.close();
				return  (resp) ;	
			}	
		}
		
		if ((text2.equals("No") || text2.equals("no")) && tareatomada == 0)
			{
			nactividad = nactividad + 1;
			ntarea = ntarea + 1;
			c = db.rawQuery("select * from actividades where idactividad = '" + nactividad+"'", null);
				if (c.moveToFirst()) 
				{
					String resp=c.getString(1).toString() +" , "+ c.getString(3).toString() +" , "+ c.getString(5).toString() ;
					db.close();
					return  (resp) ;
				}	
				else
				{
					nactividad = 1;
					ntarea =  1;
					return  "no hay mas atividades se devuelve al comiennzo de la lista" ;
				}
				
			
		}
		
		if ((text2.equals("Listo") || text2.equals("listo")) && tareatomada != 0)
		{
		ntarea = ntarea + 1;
		c = db.rawQuery("select * from actividades where idactividad = '" + nactividad+"' and idtarea ='"+ ntarea +"'", null);
			if (c.moveToFirst()) 
			{
				String resp=c.getString(1).toString() +" , " +c.getString(3).toString() +" , "+ c.getString(5).toString();
				db.close();
				return  (resp) ;
			}	
		}	
	
		c = db.rawQuery("select * from actividades where idactividad = '" + nactividad + 
				"' and idtarea ='" + ntarea + "' and respuestaesperada = '" + text2 + "'", null);
		if (c.moveToFirst()) 
		{ 
			String resp=c.getString(8).toString();
			db.close();
			return  (resp) ;
		}
		else
		{
			db.close();
			return "malo";	
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SPEECH: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> textA = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				txtTextV.setText(textA.get(0));
			}
			break;
		}

		}
	}
}