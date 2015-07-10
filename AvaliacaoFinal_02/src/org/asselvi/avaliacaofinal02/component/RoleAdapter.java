package org.asselvi.avaliacaofinal02.component;

import java.util.List;

import org.asselvi.avaliacaofinal02.R;
import org.asselvi.avaliacaofinal02.model.Role;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class RoleAdapter extends GenericListAdapter<Role> {

	public RoleAdapter(Context context) {
		super(context);
	}
	
	public RoleAdapter(Context context, List<Role> list) {
		super(context, list);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE );
		View lineAdapted = inflater.inflate(R.layout.line_style_role, parent, false);

		TextView principal = (TextView) lineAdapted.findViewById(R.id.principalRole);

		Role role = getItem(position);
		principal.setText(String.format("%s", role.getDescription()));
		return lineAdapted;
	}

}
