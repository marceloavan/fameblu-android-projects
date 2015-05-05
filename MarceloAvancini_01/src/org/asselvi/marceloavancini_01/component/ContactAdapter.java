package org.asselvi.marceloavancini_01.component;

import java.util.List;

import org.asselvi.marceloavancini_01.R;
import org.asselvi.marceloavancini_01.model.Contact;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class ContactAdapter extends GenericListAdapter<Contact> {

	public ContactAdapter(Context context) {
		super(context);
	}
	
	public ContactAdapter(Context context, List<Contact> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE );
		View lineAdapted = inflater.inflate(R.layout.line_style, parent, false);

		TextView principal = (TextView) lineAdapted.findViewById(R.id.principal);
		TextView secondary = (TextView) lineAdapted.findViewById(R.id.secondary);
		ImageView star = (ImageView) lineAdapted.findViewById(R.id.star);

		Contact contact = getItem(position);
		String idContact = String.valueOf(contact.getIdDevice() == 0 ? "" : "["+contact.getIdDevice()+"]");
		principal.setText(String.format("%s %s", idContact, contact.getName()).trim());
		secondary.setText(String.format("%s - %s - %s", contact.getEmail(), contact.getPhoneHome(), contact.getPhoneMobile()));
		if (contact.getSex() == 'M') {
			star.setImageResource(android.R.drawable.btn_star_big_on);
		} else {
			star.setImageResource(android.R.drawable.btn_star_big_off);
		}
		return lineAdapted;
	}
}