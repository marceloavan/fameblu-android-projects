package org.marceloavancini.parcial_02;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.appanotacoes.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditFile extends Activity {
	private String path;
	private EditText editText;
	private String fileName;
	private Button returnBtn;
	private Button saveBtn;
	private Button removeBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_file);
		
		path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/INF28_2015_1/";
		
		Intent intent = getIntent();
		fileName = intent.getStringExtra("pathFile");
		if (fileName == null) {
			fileName = "";
		}
		
		editText = (EditText) findViewById(R.id.edit_file_desc);

		if(fileName.equals("")){
			setTitle("Não encontrado");
		}
		else{
			setTitle(fileName);
			readFile(fileName);
		}
		
		returnBtn = (Button) findViewById(R.id.edit_file_voltar);
		returnBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		saveBtn = (Button) findViewById(R.id.edit_file_salvar);
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveFile();
			}
		});
		
		removeBtn = (Button) findViewById(R.id.edit_file_excluir);
		removeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				File fileExt = new File(path, fileName);
				fileExt.delete();
				finish();
			}
		});
	}
	
	private void saveFile() {
		fileName = normalizeStr(fileName);
	    
		File fileExt = new File(path, fileName);
    	fileExt.getParentFile().mkdirs();
    	
	    FileOutputStream fosExt = null ;
	    try {
			fosExt = new FileOutputStream(fileExt);
		} 
		catch (FileNotFoundException e) {e.printStackTrace();} 
	    try {
			 fosExt.write(new String(editText.getText().toString()).getBytes());
			 try {
				fosExt.close();
			 } 
		     catch (IOException e) {
				 e.printStackTrace();
			 }
			 finish();
		} 
	    catch (IOException e) {e.printStackTrace();}
	}
	
	private void readFile(String str)  {
		fileName = normalizeStr(fileName);
    	File file = new File(path, str); 
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			byte arrBits[] = new byte[(int)file.length()]; 
            
			try {
				fis.read(arrBits);
			} 
            catch (IOException e) {
				e.printStackTrace();
			} 
            
            String stringTemp = new String(arrBits);
            try {
				fis.close();
			}
            catch (IOException e) {
				e.printStackTrace();
			}
            editText.setText(stringTemp);
		} 
		catch (FileNotFoundException e) {e.printStackTrace();}
	}
	
	private void saveSharedPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		Editor editor = sharedPreferences.edit();
        editor.putString("fileName", fileName);
        editor.commit();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_main_sair:
			saveSharedPreferences();
			setResult(10);
			finish();
			break;
		}
		return true;
	}
	
	private String normalizeStr(String str) {
		str = str.replaceAll(".txt", "");
		return str + ".txt";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_close, menu);
		return true;
	}
}