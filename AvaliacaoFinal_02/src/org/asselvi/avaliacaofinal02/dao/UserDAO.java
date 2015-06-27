package org.asselvi.avaliacaofinal02.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.asselvi.avaliacaofinal02.model.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDAO extends BaseDAO<User> {
	
	private static UserDAO INSTANCE; 
	
	public static UserDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UserDAO();
		}
		return INSTANCE;
	}

	@Override
	public List<User> findAll(Context context) {
		SQLiteDatabase db = getConnection(context).getReadableDatabase();
		Cursor cursor = db.query(getTableName(), null, null, null, null, null, null);
		
		List<User> list = new ArrayList<User>();
		while (cursor.moveToNext()) {
			User user = new User();
			user.setId(cursor.getLong(cursor.getColumnIndex("ID")));
			user.setName(cursor.getString(cursor.getColumnIndex("NAME")));
			user.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
			user.setPhone(cursor.getInt(cursor.getColumnIndex("PHONE")));
			
			Date lastUpdate = new Date(cursor.getLong(cursor.getColumnIndex("LASTUPDATE")));
			user.setLastUpdate(lastUpdate);
			
			list.add(user);
		}
		return list;
	}

	@Override
	public User findOne(Context context, Integer id) {
		return null;
	}

	@Override
	public void insert(Context context, User item) {
		item.setLastUpdate(new Date());
		
		SQLiteDatabase db = getConnection(context).getReadableDatabase();
		ContentValues content = new ContentValues();
		content.put("NAME", item.getName());
		content.put("EMAIL", item.getEmail());
		content.put("PHONE", item.getPhone());
		content.put("LASTUPDATE", item.getLastUpdate().getTime());
		long id = db.insert(getTableName(), null, content);
		if (id != -1) {
			item.setId(id);
		} else {
		}
	}

	@Override
	public void update(Context context, User item) {
		item.setLastUpdate(new Date());
		
		SQLiteDatabase db= getConnection(context).getWritableDatabase();
		ContentValues content = new ContentValues();

		String whereClause = "ID = ?"; 
		String[] whereArgs = new String[]{item.getId().toString()};
		
		content.put("NAME", item.getName());
		content.put("EMAIL", item.getEmail());
		content.put("PHONE", item.getPhone());
		content.put("LASTUPDATE", item.getLastUpdate().getTime());

		long id = db.update(getTableName(), content, whereClause, whereArgs);
		
		if (id == 1) {
			// sucess
		}
	}

	@Override
	public void remove(Context context, User item) {
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
		return "USERS";
	}
}