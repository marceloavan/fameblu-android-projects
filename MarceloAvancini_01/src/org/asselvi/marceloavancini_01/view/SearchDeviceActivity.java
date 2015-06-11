package org.asselvi.marceloavancini_01.view;

import java.util.ArrayList;
import java.util.List;

import org.asselvi.marceloavancini_01.R;
import org.asselvi.marceloavancini_01.component.ContactAdapter;
import org.asselvi.marceloavancini_01.component.IdGenerator;
import org.asselvi.marceloavancini_01.model.Contact;
import org.asselvi.marceloavancini_01.service.DeviceIntegration;
import org.asselvi.marceloavancini_01.service.api.DeviceIntegrationAPI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SearchDeviceActivity extends Activity {
	
	private ListView listView;
	private ContactAdapter contactAdapter;
	private Contact selectedContact;
	private View lastItemListViewSelected;

	private static DeviceIntegrationAPI devIntegration;
	private static List<Integer> selectedContactIds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_device_view);
		init();
	}
	
	private void init() {
		if (devIntegration == null) {
			devIntegration = DeviceIntegration.getInstance();
		}
		if (selectedContactIds == null) {
			selectedContactIds = new ArrayList<Integer>();
		}
		contactAdapter = new ContactAdapter(getApplicationContext(), devIntegration.getAllContacts(getContentResolver(), selectedContactIds));
		
		listView = (ListView) findViewById(R.id.listViewContact);
		listView.setAdapter(contactAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (lastItemListViewSelected == null) {
					lastItemListViewSelected = view;
					lastItemListViewSelected.setBackgroundColor(Color.RED);
				} else if (lastItemListViewSelected != view) {
					lastItemListViewSelected.setBackgroundColor(Color.TRANSPARENT);
					lastItemListViewSelected = view;
					lastItemListViewSelected.setBackgroundColor(Color.RED);
				}
				selectedContact = contactAdapter.getItem(position);
			}
		});
	}
	
	public void confirm(View view) {
		Intent intent = new Intent();
		setResult(MainActivity.RESULT_OK, intent);
		
		selectedContact.setId(IdGenerator.nextId());
		intent.putExtra("contact", selectedContact);
		selectedContactIds.add(selectedContact.getIdDevice());
		finish();
	}
	
	public void cancel(View v) {
		Intent intent = new Intent();
		setResult(MainActivity.RESULT_NOK, intent);
		finish();
	}
}
