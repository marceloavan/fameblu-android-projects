package org.asselvi.marceloavancini_01.service.api;

import java.io.Serializable;
import java.util.List;

import org.asselvi.marceloavancini_01.model.Contact;

import android.content.ContentResolver;

/**
 * Interface padrão para integração com o aparelho
 * s
 * @author marcelo
 *
 */
public interface DeviceIntegrationAPI extends Serializable {

	public List<Contact> getAllContacts(ContentResolver contentResolver);
	
	public List<Contact> getAllContacts(ContentResolver contentResolver, List<Integer> excludeIds);
	
	public List<Contact> getContactsByIdList(ContentResolver contentResolver, List<Integer> idList);

	public Contact getContactById(ContentResolver contentResolver, Integer id);
}
