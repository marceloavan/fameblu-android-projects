package org.asselvi.avaliacaofinal02.view;

import org.asselvi.avaliacaofinal02.R;
import org.asselvi.avaliacaofinal02.component.RoleAdapter;
import org.asselvi.avaliacaofinal02.dao.RoleDAO;
import org.asselvi.avaliacaofinal02.logging.Level;
import org.asselvi.avaliacaofinal02.logging.LogProducer;
import org.asselvi.avaliacaofinal02.model.Role;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class EditRoleActivity extends Activity {

	private ListView listView;
	private RoleAdapter roleAdapter;
	private Activity activity;
	private TextView roleDescEdit;
	private Role roleEditing;
	private View viewSelectedBefore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_role);
		setTitle("Edição de cargos");
		
		
		
		init();
	}
	
	private void init() {
		LogProducer.log(getClass(), Level.INFO, "Iniciando informações do objeto");
		
		activity = this;
		viewSelectedBefore = null;
		
		listView = (ListView) findViewById(R.id.listViewRole);
		roleDescEdit = (TextView) findViewById(R.id.roleDescEdt);

		roleAdapter = new RoleAdapter(getApplicationContext(), RoleDAO.getInstance().findAll(getApplicationContext()));
		
		listView.setAdapter(roleAdapter);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				LogProducer.log(activity.getClass(), Level.INFO, "Executando o onItemLongClick da listView");
				askToRemove(roleAdapter.getItem(position));
				return true;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				LogProducer.log(activity.getClass(), Level.INFO, "Executando o onItemClick da listView");
				
				if (viewSelectedBefore != null) {
					viewSelectedBefore.setBackgroundColor(Color.TRANSPARENT);
				}
				viewSelectedBefore = view;
				viewSelectedBefore.setBackgroundColor(Color.GREEN);
				
				roleEditing = roleAdapter.getItem(position);
				roleDescEdit.setText(roleEditing.getDescription());
			}
		});
	}
	
	private void askToRemove(final Role role) {
		LogProducer.log(getClass(), Level.WARN, "Pedindo confirmação para excluir o cargo: " + role.getDescription());
		
		AlertDialog.Builder builderClose = new AlertDialog.Builder(this);
		builderClose.setTitle("Remoção de cargo");
		builderClose.setMessage(String.format("Deseja realmente remover o cargo %s?", role.getDescription()));
		builderClose.setNegativeButton("Não", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				LogProducer.log(activity.getClass(), Level.WARN, "Exclusão do cargo cancelada:  " + role.getDescription());
			}
		});
		builderClose.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				LogProducer.log(getClass(), Level.WARN, "Exclusão do cargo confirmada: " + role.getDescription());
				RoleDAO.getInstance().remove(getApplicationContext(), role);
				roleAdapter.removeItem(role);
				listView.invalidateViews();
				roleDescEdit.setText("");
			}
		});
		AlertDialog alert = builderClose.create();
		alert.show();
	}
	
	public void save(View view) {
		LogProducer.log(getClass(), Level.INFO, "O botão salvar cargo foi pressionado");
		
		if (roleEditing == null) {
			roleEditing = new Role(roleDescEdit.getText().toString());
			RoleDAO.getInstance().insert(getApplicationContext(), roleEditing);
			LogProducer.log(getClass(), Level.INFO, "Criando novo cargo com id: "+ roleEditing.getId());
		} else {
			LogProducer.log(getClass(), Level.INFO, "Editando o cargo com id: "+ roleEditing.getId());
			roleEditing.setDescription(roleDescEdit.getText().toString());
			RoleDAO.getInstance().update(getApplicationContext(), roleEditing);
		}
		roleDescEdit.setText("");
		roleEditing = null;
		if (viewSelectedBefore != null) {
			viewSelectedBefore.setBackgroundColor(Color.TRANSPARENT);
		}
		
		roleAdapter = new RoleAdapter(getApplicationContext(), RoleDAO.getInstance().findAll(getApplicationContext()));
		listView.setAdapter(roleAdapter);
		listView.invalidateViews();
	}
	
	public void cancel(View view) {
		LogProducer.log(getClass(), Level.INFO, "O botão cancelar cargo foi pressionado");
		
		roleDescEdit.setText("");
		roleEditing = null;
		if (viewSelectedBefore != null) {
			viewSelectedBefore.setBackgroundColor(Color.TRANSPARENT);
		}
		
		roleAdapter = new RoleAdapter(getApplicationContext(), RoleDAO.getInstance().findAll(getApplicationContext()));
		listView.setAdapter(roleAdapter);
		listView.invalidateViews();
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
}
