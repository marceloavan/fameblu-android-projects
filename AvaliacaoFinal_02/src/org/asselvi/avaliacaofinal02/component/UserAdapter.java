package org.asselvi.avaliacaofinal02.component;

import java.util.List;

import org.asselvi.avaliacaofinal02.R;
import org.asselvi.avaliacaofinal02.model.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class UserAdapter extends GenericListAdapter<User> {

	public UserAdapter(Context context) {
		super(context);
	}
	
	public UserAdapter(Context context, List<User> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE );
		View lineAdapted = inflater.inflate(R.layout.line_style, parent, false);

		TextView principal = (TextView) lineAdapted.findViewById(R.id.principal);
		TextView secondary = (TextView) lineAdapted.findViewById(R.id.secondary);

		User user = getItem(position);
		principal.setText(String.format("%s", user.getName()).trim());
		secondary.setText(String.format("%s - %s", user.getEmail(), user.getPhone()));
		return lineAdapted;
	}
}