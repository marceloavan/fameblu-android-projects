package org.asselvi.avaliacaofinal02.view;

import org.asselvi.avaliacaofinal02.R;
import org.asselvi.avaliacaofinal02.component.UserAdapter;
import org.asselvi.avaliacaofinal02.dao.UserDAO;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ListView listView;
	private TextView infoTextView;
	private UserAdapter userAdapter;
	
	public static int REQUEST_NEW_USER = 1;
	public static int REQUEST_EDIT_USER = 2;
	
	public static int RESULT_OK = 100;
	public static int RESULT_NOK = 101;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}
	
	private void init() {
		listView = (ListView) findViewById(R.id.listViewUserMain);
		infoTextView = (TextView) findViewById(R.id.infoMain);

		userAdapter = new UserAdapter(getApplicationContext(), UserDAO.getInstance().findAll(getApplicationContext()));
		
		listView.setAdapter(userAdapter);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				askToRemove(userAdapter.getItem(position));
				return true;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), EditUserActivity.class);
				intent.putExtra("userToEdit", userAdapter.getItem(position));
				startActivityForResult(intent, REQUEST_EDIT_USER);
			}
		});
		loadInfo();
	}

	private void loadInfo() {
		infoTextView.setText(String.format("Quantidade de contatos: %s.", userAdapter.getCount()));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_default, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast toast = Toast.makeText(getApplicationContext(), "Não sei o que fazer ;(", Toast.LENGTH_SHORT);

		switch (item.getItemId()) {
		case R.id.menu_bt_new_user:
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
		AlertDialog.Builder builderClose = new AlertDialog.Builder(this);
		builderClose.setTitle("Sair da aplicação");
		builderClose.setMessage("Deseja realmente sair?");
		builderClose.setNegativeButton("Não", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {				
			}
		});
		builderClose.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		AlertDialog alert = builderClose.create();
		alert.show();
	}
	
	private void askToRemove(final User user) {
		AlertDialog.Builder builderClose = new AlertDialog.Builder(this);
		builderClose.setTitle("Remoção de usuário");
		builderClose.setMessage(String.format("Deseja realmente remover o usuário %s?", user.getName()));
		builderClose.setNegativeButton("Não", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {				
			}
		});
		builderClose.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
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
		User user = (User) data.getSerializableExtra("user");
		if (user != null) {
			if (requestCode == REQUEST_NEW_USER || requestCode == REQUEST_NEW_USER) {
				userAdapter.addItem(user);
			} else if (requestCode == REQUEST_EDIT_USER) {
				userAdapter.addOrReplaceItem(user);
			}
			listView.invalidateViews();
			loadInfo();
		}
	}
	
	public void newUser(View v) {
		Intent intent = new Intent(getApplicationContext(), EditUserActivity.class);
		startActivityForResult(intent, REQUEST_NEW_USER);
	}
	
	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		intent.putExtra("requestCode", requestCode);
		super.startActivityForResult(intent, requestCode);
	}
	
	@Override
	public void onBackPressed() {
		askToClose();
	}
}
