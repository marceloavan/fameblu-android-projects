package org.asselvi.avaliacaofinal02.dao;

import java.util.ArrayList;
import java.util.List;

import org.asselvi.avaliacaofinal02.model.Role;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author marcelo
 */
public class RoleDAO extends BaseDAO<Role>{

	private static RoleDAO INSTANCE; 
	
	public static RoleDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RoleDAO();
		}
		return INSTANCE;
	}
	
	private RoleDAO() {}
	
	@Override
	public List<Role> findAll(Context context) {
		SQLiteDatabase db = getConnection(context).getReadableDatabase();
		Cursor cursor = db.query(getTableName(), null, null, null, null, null, null);
		
		List<Role> list = new ArrayList<Role>();
		while (cursor.moveToNext()) {
			Role role = new Role();
			role.setId(cursor.getLong(cursor.getColumnIndex("ID")));
			role.setDescription(cursor.getString(cursor.getColumnIndex("DESC")));

			list.add(role);
		}
		return list;
	}

	@Override
	public Role findOne(Context context, Integer id) {
		SQLiteDatabase db = getConnection(context).getReadableDatabase();
		
		String whereClause = "ID = ?";
		String[] whereArgs = new String[]{String.valueOf(id)};
		
		Cursor cursor = db.query(getTableName(), null, whereClause, whereArgs, null, null, null);
		
		cursor.moveToNext();
		Role role = new Role();
		role.setId(cursor.getLong(cursor.getColumnIndex("ID")));
		role.setDescription(cursor.getString(cursor.getColumnIndex("DESC")));

		return role;
	}

	@Override
	public void insert(Context context, Role item) {
		SQLiteDatabase db = getConnection(context).getReadableDatabase();
		ContentValues content = new ContentValues();
		content.put("DESC", item.getDescription());
		long id = db.insert(getTableName(), null, content);
		if (id != -1) {
			item.setId(id);
		}
	}

	@Override
	public void update(Context context, Role item) {
		SQLiteDatabase db= getConnection(context).getWritableDatabase();
		ContentValues content = new ContentValues();

		String whereClause = "ID = ?"; 
		String[] whereArgs = new String[]{item.getId().toString()};
		
		content.put("NAME", item.getDescription());

		long id = db.update(getTableName(), content, whereClause, whereArgs);
		
		if (id == 1) {
			// sucess
		}
	}

	@Override
	public void remove(Context context, Role item) {
		SQLiteDatabase db= getConnection(context).getWritableDatabase();
		
		String whereClause = "ID = ?"; 
		String[] whereArgs = new String[]{item.getId().toString()};
		
		long id = db.delete(getTableName(), whereClause, whereArgs);

		if (id == 1) {
			// sucess
		}
	}
	
	@Override
	public String getTableName() {
		return "ROLES";
	}
}
