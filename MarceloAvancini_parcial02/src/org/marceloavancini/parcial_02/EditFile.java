package org.marceloavancini.parcial_02;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.appanotacoes.R;

public class EditFile extends Activity {
	private String path;
	private EditText editText;
	private String fileName;
	private Button returnBtn;
	private Button saveBtn;
	private Button removeBtn;
	private Activity activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(this.getClass().getSimpleName(), "->onCreate");
		
		activity = this;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_file);
		
		path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/OFICIAL_02_LOGGING/";
		
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
				Log.d(activity.getClass().getSimpleName(), "->returnBtn->onClick");
				finish();
			}
		});
		
		saveBtn = (Button) findViewById(R.id.edit_file_salvar);
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(activity.getClass().getSimpleName(), "->saveBtn->onClick");
				saveFile();
			}
		});
		
		removeBtn = (Button) findViewById(R.id.edit_file_excluir);
		removeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(activity.getClass().getSimpleName(), "->removeBtn->onClick");
				new AlertDialog
					.Builder(activity)
					.setTitle("Remover arquivo")
					.setMessage("\n\nDeseja realmente remover o arquivo?\n\n")
					.setPositiveButton("Sim", new AlertDialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							File fileExt = new File(path, fileName);
							fileExt.delete();
							finish();
						}
					})
					.setNegativeButton("Não", null)
					.show();
			}
		});
	}
	
	private void saveFile() {
		Log.d(this.getClass().getSimpleName(), "->saveFile");
		
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
		Log.d(this.getClass().getSimpleName(), "->readFile");
		
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
		Log.d(this.getClass().getSimpleName(), "->saveSharedPreferences");
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		Editor editor = sharedPreferences.edit();
        editor.putString("fileName", fileName);
        editor.commit();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(this.getClass().getSimpleName(), "->onOptionsItemSelected");
		
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
		Log.d(this.getClass().getSimpleName(), "->normalizeStr");
		
		str = str.replaceAll(".txt", "");
		return str + ".txt";
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(this.getClass().getSimpleName(), "->onCreateOptionsMenu");
		
		getMenuInflater().inflate(R.menu.menu_close, menu);
		return true;
	}
}