package org.asselvi.marceloavancini_01.view;

import org.asselvi.marceloavancini_01.R;
import org.asselvi.marceloavancini_01.component.IdGenerator;
import org.asselvi.marceloavancini_01.model.Contact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class EditContactActivity extends Activity {

	private TextView idEdit;
	private TextView nameEdit;
	private TextView documentEdit;
	private TextView emailEdit;
	private TextView phoneHomeEdit;
	private TextView phoneMobileEdit;
	private RadioGroup sexRadioGroup;
	private RadioButton sexRadioMasculino;
	private RadioButton sexRadioFeminino;
	
	private Contact contactEdited;
	private String defaultId = "- - - - - -";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_contact);
		init();
	}
	
	private void init() {
		idEdit = (TextView) findViewById(R.id.idEdt);
		idEdit.setEnabled(false);
		nameEdit = (TextView) findViewById(R.id.nameEdt);
		documentEdit = (TextView) findViewById(R.id.documentEdt);
		emailEdit = (TextView) findViewById(R.id.emailEdt);
		phoneHomeEdit = (TextView) findViewById(R.id.phoneHomeEdt);
		phoneMobileEdit = (TextView) findViewById(R.id.phoneMobileEdt);
		sexRadioGroup = (RadioGroup) findViewById(R.id.sexoRdBtn);
		sexRadioMasculino = (RadioButton) findViewById(R.id.masculinoRdBtn);
		sexRadioFeminino = (RadioButton) findViewById(R.id.femininoRdBtn);
		
		int requestCode = getIntent().getIntExtra("requestCode", MainActivity.REQUEST_NEW_CONTACT);
		if (requestCode == MainActivity.REQUEST_NEW_CONTACT) {
			contactEdited = new Contact();
			contactEdited.setId(IdGenerator.nextId());
			idEdit.setText(defaultId);
		} else if (requestCode == MainActivity.REQUEST_EDIT_CONTACT) {
			contactEdited = (Contact) getIntent().getSerializableExtra("contactToEdit");
			
			idEdit.setText(contactEdited.getIdDevice() == 0 ? defaultId : contactEdited.getIdDevice().toString());
			nameEdit.setText(contactEdited.getName());
			documentEdit.setText(contactEdited.getDocument());
			emailEdit.setText(contactEdited.getEmail());
			phoneHomeEdit.setText(contactEdited.getPhoneHome().toString());
			phoneMobileEdit.setText(contactEdited.getPhoneMobile().toString());
			
			Character sex = contactEdited.getSex();
			if (sex.equals('M')) {
				sexRadioMasculino.setChecked(true);
			} else if (sex.equals('F')) {
				sexRadioFeminino.setChecked(true);
			}
		}
	}
	
	public void save(View v) {
		Intent intent = new Intent();
		setResult(MainActivity.RESULT_OK, intent);
		
		String strName = String.valueOf(nameEdit.getText() != null ? nameEdit.getText() : "");
		contactEdited.setName(strName);
		
		String strDocument = String.valueOf(documentEdit.getText() != null ? documentEdit.getText() : "");
		contactEdited.setDocument(strDocument);
		
		String strEmail = String.valueOf(emailEdit.getText() != null ? emailEdit.getText() : "");
		contactEdited.setEmail(strEmail);
		
		String strPhoneHome = String.valueOf(phoneHomeEdit.getText() != null ? phoneHomeEdit.getText() : "0");
		strPhoneHome = strPhoneHome.length() == 0 ? "0" : strPhoneHome;
		contactEdited.setPhoneHome(Integer.valueOf(strPhoneHome));
		
		String strPhoneMobile = String.valueOf(phoneMobileEdit.getText() != null ? phoneMobileEdit.getText() : "0");
		strPhoneMobile = strPhoneMobile.length() == 0 ? "0" : strPhoneMobile;
		contactEdited.setPhoneMobile(Integer.valueOf(strPhoneMobile));
		
		if (sexRadioGroup.getCheckedRadioButtonId() == R.id.masculinoRdBtn) {
			contactEdited.setSex('M');
		} else if (sexRadioGroup.getCheckedRadioButtonId() == R.id.femininoRdBtn) {
			contactEdited.setSex('F');
		}
		
		intent.putExtra("contact", contactEdited);
		finish();
	}
	
	public void cancel(View v) {
		Intent intent = new Intent();
		setResult(MainActivity.RESULT_NOK, intent);
		finish();
	}
}
