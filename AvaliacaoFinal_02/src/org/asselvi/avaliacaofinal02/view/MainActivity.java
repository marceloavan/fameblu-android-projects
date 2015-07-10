package org.asselvi.avaliacaofinal02.view;

import java.util.ArrayList;
import java.util.List;

import org.asselvi.avaliacaofinal02.R;
import org.asselvi.avaliacaofinal02.component.UserAdapter;
import org.asselvi.avaliacaofinal02.dao.RoleDAO;
import org.asselvi.avaliacaofinal02.dao.UserDAO;
import org.asselvi.avaliacaofinal02.logging.Level;
import org.asselvi.avaliacaofinal02.logging.LogProducer;
import org.asselvi.avaliacaofinal02.model.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ListView listView;
	private TextView infoTextView;
	private UserAdapter userAdapter;
	private Activity activity;
	
	public static int REQUEST_NEW_USER = 1;
	public static int REQUEST_EDIT_USER = 2;
	
	public static int RESULT_OK = 100;
	public static int RESULT_NOK = 101;
	public static int RESULT_REMOVED = 102;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LogProducer.log(getClass(), Level.INFO, "Executando o onCreate");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}
	
	private void init() {
		LogProducer.log(getClass(), Level.INFO, "Iniciando informações do objeto");
		
		activity = this;
		listView = (ListView) findViewById(R.id.listViewUserMain);
		infoTextView = (TextView) findViewById(R.id.infoMain);
		

		userAdapter = new UserAdapter(getApplicationContext(), UserDAO.getInstance().findAll(getApplicationContext()));
		
		listView.setAdapter(userAdapter);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				LogProducer.log(activity.getClass(), Level.INFO, "Executando o onItemLongClick da listView");
				askToRemove(userAdapter.getItem(position));
				return true;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				LogProducer.log(activity.getClass(), Level.INFO, "Executando o onItemClick da listView");
				
				Intent intent = new Intent(getApplicationContext(), EditUserActivity.class);
				intent.putExtra("userToEdit", userAdapter.getItem(position));
				startActivityForResult(intent, REQUEST_EDIT_USER);
			}
		});
		
		loadInfo();
		
	
	}

	private void loadInfo() {
		LogProducer.log(getClass(), Level.INFO, "Carregando quantidade de usuários salvos");
		infoTextView.setText(String.format("Quantidade de contatos: %s.", userAdapter.getCount()));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		LogProducer.log(getClass(), Level.INFO, "Executando o onCreateOptionsMenu");
		
		getMenuInflater().inflate(R.menu.menu_default, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		LogProducer.log(getClass(), Level.INFO, "Executando o onOptionsItemSelected");
		
		Toast toast = Toast.makeText(getApplicationContext(), "Não sei o que fazer ;(", Toast.LENGTH_SHORT);

		switch (item.getItemId()) {
		case R.id.menu_bt_new_user:
			LogProducer.log(getClass(), Level.INFO, "O botão novo usuário do menu foi pressionado");			
			
			toast = Toast.makeText(getApplicationContext(), "Novo usuário...", Toast.LENGTH_SHORT);
			Intent intent = new Intent(getApplicationContext(), EditUserActivity.class);
			toast.show();
			startActivityForResult(intent, REQUEST_NEW_USER);
			break;
			
		case R.id.menu_bt_close:
			askToClose();
			toast = Toast.makeText(getApplicationContext(), "Saindo...", Toast.LENGTH_SHORT);
			toast.show();
			break;
		}
		return true;
	}
	
	private void askToClose() {
		LogProducer.log(getClass(), Level.INFO, "Pedindo confirmação para fechar a activity");
		
		AlertDialog.Builder builderClose = new AlertDialog.Builder(this);
		builderClose.setTitle("Sair da aplicação");
		builderClose.setMessage("Deseja realmente sair?");
		builderClose.setNegativeButton("Não", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				LogProducer.log(activity.getClass(), Level.INFO, "Fechamento da activity cancelado");
			}
		});
		builderClose.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				LogProducer.log(activity.getClass(), Level.INFO, "Fechamento da activity confirmado");
				finish();
			}
		});
		AlertDialog alert = builderClose.create();
		alert.show();
	}
	
	private void askToRemove(final User user) {
		LogProducer.log(getClass(), Level.WARN, "Pedindo confirmação para excluir o usuário de id: " + user.getId());
		
		AlertDialog.Builder builderClose = new AlertDialog.Builder(this);
		builderClose.setTitle("Remoção de usuário");
		builderClose.setMessage(String.format("Deseja realmente remover o usuário %s?", user.getName()));
		builderClose.setNegativeButton("Não", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				LogProducer.log(activity.getClass(), Level.WARN, "Exclusão do usuário cancelada, id: " + user.getId());
			}
		});
		builderClose.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				LogProducer.log(getClass(), Level.WARN, "Exclusão do usuário confirmada, id: " + user.getId());
				UserDAO.getInstance().remove(getApplicationContext(), user);
				userAdapter.removeItem(user);
				listView.invalidateViews();
				loadInfo();
			}
		});
		AlertDialog alert = builderClose.create();
		alert.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		LogProducer.log(getClass(), Level.WARN, "Executando o onActivityResult");
		
		if (resultCode == RESULT_OK) {
			User user = (User) data.getSerializableExtra("user");
			if (user != null) {
				if (requestCode == REQUEST_NEW_USER || requestCode == REQUEST_NEW_USER) {
					userAdapter.addItem(user);
				} else if (requestCode == REQUEST_EDIT_USER) {
					userAdapter.addOrReplaceItem(user);
				}
			}
		}
		userAdapter = new UserAdapter(getApplicationContext(), UserDAO.getInstance().findAll(getApplicationContext()));
		listView.setAdapter(userAdapter);
		
		listView.invalidateViews();
		loadInfo();
	}
	
	public void newUser(View v) {
		LogProducer.log(getClass(), Level.WARN, "Criação de novo usuário solicitada");
		
		Intent intent = new Intent(getApplicationContext(), EditUserActivity.class);
		startActivityForResult(intent, REQUEST_NEW_USER);
	}
	
	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		LogProducer.log(getClass(), Level.WARN, "Executando o startActivityForResult");
		
		intent.putExtra("requestCode", requestCode);
		super.startActivityForResult(intent, requestCode);
	}
	
	@Override
	public void onBackPressed() {
		LogProducer.log(getClass(), Level.WARN, "Executando o onBackPressed");
		
		askToClose();
	}
	
}
