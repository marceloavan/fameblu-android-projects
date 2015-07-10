package org.asselvi.avaliacaofinal02.component;

import java.util.List;

import org.asselvi.avaliacaofinal02.R;
import org.asselvi.avaliacaofinal02.model.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class UserAdapter extends GenericListAdapter<User> {

	private int lastUserSelectedId;
	
	public UserAdapter(Context context) {
		super(context);
	}
	
	public UserAdapter(Context context, List<User> list, int lastUserSelectedId) {
		super(context, list);
		this.lastUserSelectedId = lastUserSelectedId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE );
		View lineAdapted = inflater.inflate(R.layout.line_style_user, parent, false);

		TextView principal = (TextView) lineAdapted.findViewById(R.id.principalUser);
		TextView secondary = (TextView) lineAdapted.findViewById(R.id.secondaryUser);

		User user = getItem(position);
		principal.setText(String.format("%s", user.getName()).trim());
		secondary.setText(String.format("%s - %s", user.getEmail(), user.getPhone()));
		
		if (user.getId().intValue() == lastUserSelectedId) {
			lineAdapted.setBackgroundColor(Color.GREEN);
		}
		return lineAdapted;
	}
}