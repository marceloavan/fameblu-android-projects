package org.asselvi.marceloavancini_01.view;

import org.asselvi.marceloavancini_01.R;
import org.asselvi.marceloavancini_01.component.ContactAdapter;
import org.asselvi.marceloavancini_01.model.Contact;

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
	private ContactAdapter contactAdapter;
	
	public static int REQUEST_SEARCH = 1;
	public static int REQUEST_NEW_CONTACT = 2;
	public static int REQUEST_EDIT_CONTACT = 3;
	
	public static int RESULT_OK = 100;
	public static int RESULT_NOK = 101;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}
	
	private void init() {
		listView = (ListView) findViewById(R.id.listViewContactMain);
		infoTextView = (TextView) findViewById(R.id.infoMain);

		contactAdapter = new ContactAdapter(getApplicationContext());
		contactAdapter.addOrReplaceItem(new Contact(1, "Teste 1", "12345678918", 90834501, 12349087, "t1@t1.com", 'M'));
		contactAdapter.addOrReplaceItem(new Contact(2, "Teste 2", "12345678910", 90834500, 12349089, "t2@t2.com", 'F'));
		
		listView.setAdapter(contactAdapter);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				askToRemove(contactAdapter.getItem(position));
				return true;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), EditContactActivity.class);
				intent.putExtra("contactToEdit", contactAdapter.getItem(position));
				startActivityForResult(intent, REQUEST_EDIT_CONTACT);
			}
		});
		loadInfo();
	}

	private void loadInfo() {
		infoTextView.setText(String.format("Quantidade de contatos: %s.", contactAdapter.getCount()));
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
		case R.id.menu_bt_search:
			toast = Toast.makeText(getApplicationContext(), "Pesquisando contatos...", Toast.LENGTH_SHORT);
			Intent intent = new Intent(getApplicationContext(), SearchDeviceActivity.class);
			startActivityForResult(intent, REQUEST_SEARCH);
			break;
			
		case R.id.menu_bt_close:
			askToClose();
			toast = Toast.makeText(getApplicationContext(), "Saindo...", Toast.LENGTH_SHORT);
			break;
			
		default:
			break;
		}
		toast.show();
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
	
	private void askToRemove(final Contact contact) {
		AlertDialog.Builder builderClose = new AlertDialog.Builder(this);
		builderClose.setTitle("Remoção de usuário");
		builderClose.setMessage(String.format("Deseja realmente remover o usuário %s?", contact.getName()));
		builderClose.setNegativeButton("Não", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {				
			}
		});
		builderClose.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				contactAdapter.removeItem(contact);
				listView.invalidateViews();
				loadInfo();
			}
		});
		AlertDialog alert = builderClose.create();
		alert.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Contact contact = (Contact) data.getSerializableExtra("contact");
		if (contact != null) {
			if (requestCode == REQUEST_SEARCH || requestCode == REQUEST_NEW_CONTACT) {
				contactAdapter.addItem(contact);
			} else if (requestCode == REQUEST_EDIT_CONTACT) {
				contactAdapter.addOrReplaceItem(contact);
			}
			listView.invalidateViews();
			loadInfo();
		}
	}
	
	public void newContact(View v) {
		Intent intent = new Intent(getApplicationContext(), EditContactActivity.class);
		startActivityForResult(intent, REQUEST_NEW_CONTACT);
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
