package org.marceloavancini.parcial_02;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appanotacoes.R;

public class MainActivity extends Activity {
	private String path;
	private File folder;
	private ListView listViewFiles;
	private ArrayList<String> arrFiles;
	private File[] fileList;
	private Activity activity;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(this.getClass().getSimpleName(), "->onCreate");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		activity = this;
		
		loadSavedSharedPreferences();
		
		init();
		readList();
	}
	
	private void init() {
		Log.d(this.getClass().getSimpleName(), "->init");
		
		path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/INF28_2015_1/" ;
		folder = new File(path);
		folder.mkdirs();
		arrFiles = new ArrayList<String>();
		listViewFiles = (ListView) findViewById(R.id.tela1_listView_arquivos);
	}
	
	protected void editFile(String str){
		Log.d(this.getClass().getSimpleName(), "->editFile()");
		
		Intent it = new Intent(getApplicationContext(), EditFile.class);
		it.putExtra("pathFile", str);
		startActivityForResult(it, 2);
	}
	
	protected void readList() {
		Log.d(this.getClass().getSimpleName(), "->readList()");
		
		arrFiles.removeAll(arrFiles);
		arrFiles.add("* * * Novo Arquivo * * *");
		
		File folder = new File(path);
		FileFilter meuFiltro = new FileFilter() {
			@Override 
			public boolean accept(File arquivo) {
				return arquivo.getName().endsWith(".txt");
			}
		};
		fileList = folder.listFiles(meuFiltro);
	    if (fileList.length > 0 ){
	        for ( int i = 0;i<fileList.length;i++){
	            arrFiles.add(fileList[i].getName());
	        }
	    }
	    else{
	    	Toast.makeText(getApplicationContext(), "Nenhum arquivo encontrado! \nClique em \"Novo Arquivo\"!", Toast.LENGTH_LONG).show();
	    }

	    ArrayAdapter<String> aa;
    	aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrFiles);
	    listViewFiles.setAdapter(aa);
	    
	    listViewFiles.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
				if (position == 0) {
					final Dialog dialog = new Dialog(activity);
					dialog.setTitle("Novo arquivo");
					dialog.setContentView(R.layout.dialog_new_file);
					dialog.show();
					
					Button btnSim = (Button) dialog.findViewById(R.id.dialog_sim);
					Button btnNao = (Button) dialog.findViewById(R.id.dialog_nao);
					final EditText edit = (EditText) dialog.findViewById(R.id.dialog_text);
					
					btnSim.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Log.d(activity.getClass().getSimpleName(), "->onClick->editar");
							editFile(edit.getText().toString());
							dialog.dismiss();
						}
					});
					
					btnNao.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Log.d(activity.getClass().getSimpleName(), "->onClick->naoEditar");
							dialog.dismiss();
						}
					});
					
				} else {
					editFile(fileList[position-1].getName());
				}
			}
		});
	    
	    listViewFiles.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
				if (position == 0) {
					Toast.makeText(getApplicationContext(), "Não é possível remover a posição 1 ;)", Toast.LENGTH_SHORT).show();
				} else {
					new AlertDialog
						.Builder(activity)
						.setTitle("Remover arquivo")
						.setMessage("\n\nDeseja realmente remover o arquivo?\n\n")
						.setPositiveButton("Sim", new AlertDialog.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								Log.d(activity.getClass().getSimpleName(), "->onLongClick->remover");
								File fileExt = new File(path, fileList[position-1].getName());
								fileExt.delete();
								fileList[position-1]=null;
								listViewFiles.setOnItemClickListener(null);
								listViewFiles.invalidate();
								readList();
							}
						})
						.setNegativeButton("Não", null)
						.show();
				}
  				return true;
			}
		});
	    listViewFiles.invalidate();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(this.getClass().getSimpleName(), "->onOptionsItemSelected");
		
		switch (item.getItemId()) {
		case R.id.menu_main_sair:
			finish();
			break;
		}
		return true;
	}
	
	private void loadSavedSharedPreferences() {
		Log.d(this.getClass().getSimpleName(), "->loadSavedSharedPreferences()");
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		String fileName = sharedPreferences.getString("fileName", "");
		SharedPreferences.Editor editor = sharedPreferences.edit();
		if (fileName.isEmpty()) {
			return;
		}
		
		editor.remove("fileName");
		editor.commit();
		editFile(fileName);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(this.getClass().getSimpleName(), "->onActivityResult");
		
		if (resultCode == 10) {
			finish();
		}
		readList();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(this.getClass().getSimpleName(), "->onCreateOptionsMenu");
		
		getMenuInflater().inflate(R.menu.menu_close, menu);
		return true;
	}
}