package org.asselvi.marceloavancini_01.service;

import java.util.ArrayList;
import java.util.List;

import org.asselvi.marceloavancini_01.model.Contact;
import org.asselvi.marceloavancini_01.service.api.DeviceIntegrationAPI;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;

public class DeviceIntegration implements DeviceIntegrationAPI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static DeviceIntegration INSTANCE;
	
	private DeviceIntegration() {}

	public static DeviceIntegrationAPI getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DeviceIntegration();
		}
		return INSTANCE;
	}

	@Override
	public List<Contact> getAllContacts(ContentResolver contentResolver) {
		return getAllContacts(contentResolver, null);
	}
	
	@Override
	public List<Contact> getAllContacts(ContentResolver contentResolver, List<Integer> excludeIds) {
		List<Contact> list = new ArrayList<Contact>();
		Cursor cursorContact;
		if (excludeIds != null) {
			StringBuilder idParttern = new StringBuilder();
			idParttern.append("(");
			for (int i = 0; i < excludeIds.size(); i++) {
				if (i ==  excludeIds.size() - 1) {
					idParttern.append(excludeIds.get(i));
				} else {
					idParttern.append(excludeIds.get(i) + ",");
				}
			}
			idParttern.append(")");
			String where = Contacts._ID + " NOT IN " + idParttern.toString();
			cursorContact = contentResolver.query(android.provider.ContactsContract.Contacts.CONTENT_URI, null, where, null, null);
		} else {
			cursorContact = contentResolver.query(android.provider.ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		}
		
		while (cursorContact.moveToNext()) {
			Integer idDevice = Integer.valueOf(cursorContact.getString(cursorContact.getColumnIndex(Contacts._ID)));
			String name = cursorContact.getString(cursorContact.getColumnIndex(Contacts.DISPLAY_NAME));

			Contact contact = new Contact();
			contact.setIdDevice(idDevice);
			contact.setName(name);
			
			Cursor cursorEmail = contentResolver.query( Email.CONTENT_URI, null, Email.CONTACT_ID+ " = "+ idDevice, null, null);
			while (cursorEmail.moveToNext()) {
				contact.setEmail(cursorEmail.getString(cursorEmail.getColumnIndex(Email.ADDRESS)));
				break;
			}
			cursorEmail.close();
			
			Cursor cursorPhone = contentResolver.query( Phone.CONTENT_URI, null, Phone.CONTACT_ID+ " = "+ idDevice, null, null);
			boolean hasPhoneHome = false;
			boolean hasPhoneMobile = false;
			while (cursorPhone.moveToNext()) {
				int typePhone = cursorPhone.getInt(cursorPhone.getColumnIndex(Phone.TYPE));
				switch (typePhone) {

				case Phone.TYPE_HOME:
				case Phone.TYPE_MAIN:
				case Phone.TYPE_FAX_HOME:
					if (hasPhoneHome) {
						contact.setPhoneHome(cursorPhone.getInt(cursorPhone.getColumnIndex(Phone.NUMBER)));
					}
					hasPhoneHome = true;
					break;
				case Phone.TYPE_MOBILE:
					if (hasPhoneMobile) {
						contact.setPhoneMobile(cursorPhone.getInt(cursorPhone.getColumnIndex(Phone.NUMBER)));
					}
					hasPhoneMobile = true;
					break;
				}
				if (hasPhoneHome && hasPhoneMobile) {
					break;
				}
			}
			cursorPhone.close();
			list.add(contact);
		}
		cursorContact.close();
		return list;
	}

	@Override
	public List<Contact> getContactsByIdList(ContentResolver contentResolver, List<Integer> idList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contact getContactById(ContentResolver contentResolver, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
}
