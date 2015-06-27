package org.asselvi.avaliacaofinal02.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.asselvi.avaliacaofinal02.R;
import org.asselvi.avaliacaofinal02.dao.UserDAO;
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_user);
		init();
	}
	
	private void init() {
		dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		
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
		String info;
		if (userEdited.getId() != null && userEdited.getLastUpdate() != null) {
			info = String.format("Última atualização: %s.", dateFormatter.format(userEdited.getLastUpdate()));
		} else {
			info = "Você está cadastrando um novo usuário";
		}
		infoTextView.setText(info);
	}
	
	public void save(View v) {
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
			UserDAO.getInstance().insert(getApplicationContext(), userEdited);
		} else {
			UserDAO.getInstance().update(getApplicationContext(), userEdited);
		}
		
		intent.putExtra("user", userEdited);
		finish();
	}
	
	public void cancel(View v) {
		Intent intent = new Intent();
		setResult(MainActivity.RESULT_NOK, intent);
		finish();
	}
}
