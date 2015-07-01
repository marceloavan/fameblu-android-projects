package org.asselvi.avaliacaofinal02.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.asselvi.avaliacaofinal02.R;
import org.asselvi.avaliacaofinal02.dao.UserDAO;
import org.asselvi.avaliacaofinal02.logging.Level;
import org.asselvi.avaliacaofinal02.logging.LogProducer;
import org.asselvi.avaliacaofinal02.model.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EditUserActivity extends Activity {

	private TextView nameEdit;
	private TextView emailEdit;
	private TextView phoneEdit;
	private TextView infoTextView;
	private DateFormat dateFormatter;
	
	private User userEdited;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LogProducer.log(getClass(), Level.INFO, "Executando o onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_user);
		init();
	}
	
	private void init() {
		LogProducer.log(getClass(), Level.INFO, "Iniciando informações do objeto");
		
		dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		nameEdit = (TextView) findViewById(R.id.nameEdt);
		emailEdit = (TextView) findViewById(R.id.emailEdt);
		phoneEdit = (TextView) findViewById(R.id.phoneHomeEdt);
		infoTextView = (TextView) findViewById(R.id.infoEdit);
		
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
	
	public void cancel(View v) {
		LogProducer.log(getClass(), Level.INFO, "O botão cancelar foi pressionado");
		
		Intent intent = new Intent();
		setResult(MainActivity.RESULT_NOK, intent);
		finish();
	}
}
