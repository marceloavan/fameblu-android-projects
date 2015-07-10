package org.asselvi.avaliacaofinal02.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.asselvi.avaliacaofinal02.R;
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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditUserActivity extends Activity {

	private TextView nameEdit;
	private TextView emailEdit;
	private TextView phoneEdit;
	private TextView infoTextView;
	private DateFormat dateFormatter;
	private Spinner roleSpinner;
	private Activity activity;
	
	private User userEdited;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LogProducer.log(getClass(), Level.INFO, "Executando o onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_user);
		setTitle("Novo ou alterar usuário");
		init();
	}
	
	private void init() {
		LogProducer.log(getClass(), Level.INFO, "Iniciando informações do objeto");
		
		activity = this;
		dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		nameEdit = (TextView) findViewById(R.id.nameEdt);
		emailEdit = (TextView) findViewById(R.id.emailEdt);
		phoneEdit = (TextView) findViewById(R.id.phoneHomeEdt);
		infoTextView = (TextView) findViewById(R.id.infoEdit);
		roleSpinner = (Spinner) findViewById(R.id.roleSpn);
		
		int requestCode = getIntent().getIntExtra("requestCode", MainActivity.REQUEST_NEW_USER);
		if (requestCode == MainActivity.REQUEST_NEW_USER) {
			userEdited = new User();
		} else if (requestCode == MainActivity.REQUEST_EDIT_USER) {
			userEdited = (User) getIntent().getSerializableExtra("userToEdit");
			
			nameEdit.setText(userEdited.getName());
			emailEdit.setText(userEdited.getEmail());
			phoneEdit.setText(userEdited.getPhone().toString());
		}
		loadInfo();
		spinnerAdd();
	}
	
	private void loadInfo() {
		LogProducer.log(getClass(), Level.INFO, "Carregando última atualização do usuário");
		
		String info;
		if (userEdited.getId() != null && userEdited.getLastUpdate() != null) {
			info = String.format("Última atualização: %s.", dateFormatter.format(userEdited.getLastUpdate()));
		} else {
			info = "Você está cadastrando um novo usuário";
		}
		infoTextView.setText(info);
	}
	
	public void save(View v) {
		LogProducer.log(getClass(), Level.INFO, "O botão salvar usuário foi pressionado");
		
		Intent intent = new Intent();
		setResult(MainActivity.RESULT_OK, intent);
		
		String strName = String.valueOf(nameEdit.getText() != null ? nameEdit.getText() : "");
		userEdited.setName(strName);
		
		String strEmail = String.valueOf(emailEdit.getText() != null ? emailEdit.getText() : "");
		userEdited.setEmail(strEmail);
		
		String strPhoneHome = String.valueOf(phoneEdit.getText() != null ? phoneEdit.getText() : "0");
		strPhoneHome = strPhoneHome.length() == 0 ? "0" : strPhoneHome;
		userEdited.setPhone(Integer.valueOf(strPhoneHome));
		
		if (userEdited.getId() == null) {
			LogProducer.log(getClass(), Level.INFO, "Criando novo usuário com id: "+ userEdited.getId());
			UserDAO.getInstance().insert(getApplicationContext(), userEdited);
		} else {
			LogProducer.log(getClass(), Level.INFO, "Editando o usuário com id: "+ userEdited.getId());
			UserDAO.getInstance().update(getApplicationContext(), userEdited);
		}
		
		intent.putExtra("user", userEdited);
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		LogProducer.log(getClass(), Level.INFO, "Executando o onCreateOptionsMenu");
		
		getMenuInflater().inflate(R.menu.menu_remove, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		LogProducer.log(getClass(), Level.INFO, "Executando o onOptionsItemSelected");
		
		switch (item.getItemId()) {
		case R.id.menu_bt_remove_user:
			LogProducer.log(getClass(), Level.INFO, "O botão excluir usuário do menu foi pressionado");			
			
			askToRemove();
			
			break;
		}
		return true;
	}
	
	private void askToRemove() {
		LogProducer.log(getClass(), Level.WARN, "Pedindo confirmação para excluir o usuário de id: " + userEdited.getId());
		
		if (userEdited.getId() == null) {
			Toast.makeText(getApplicationContext(), "Usuário não salvo", Toast.LENGTH_SHORT).show();
			return;
		}
		
		AlertDialog.Builder builderClose = new AlertDialog.Builder(this);
		builderClose.setTitle("Remoção de usuário");
		builderClose.setMessage(String.format("Deseja realmente remover o usuário %s?", userEdited.getName()));
		builderClose.setNegativeButton("Não", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				LogProducer.log(activity.getClass(), Level.WARN, "Exclusão do usuário cancelada, id: " + userEdited.getId());
			}
		});
		builderClose.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (userEdited.getId() != null) {
					LogProducer.log(getClass(), Level.WARN, "Exclusão do usuário confirmada, id: " + userEdited.getId());
					UserDAO.getInstance().remove(getApplicationContext(), userEdited);
					setResult(MainActivity.RESULT_REMOVED, new Intent());

					finish();
					loadInfo();
				} else {
					Toast.makeText(getApplicationContext(), "Usuário não salvo", Toast.LENGTH_SHORT).show();
				}
			}
		});
		AlertDialog alert = builderClose.create();
		alert.show();
	}
	
	public void cancel(View v) {
		LogProducer.log(getClass(), Level.INFO, "O botão cancelar foi pressionado");
		
		setResult(MainActivity.RESULT_NOK, new Intent());
		finish();
	}
	
	public void spinnerAdd() {
		List<RoleDAO> roleList = new ArrayList<RoleDAO>();
		ArrayAdapter<RoleDAO> adapter = new ArrayAdapter<RoleDAO>(this, android.R.layout.simple_spinner_dropdown_item, roleList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		roleSpinner.setAdapter(adapter);
		
	}
}
