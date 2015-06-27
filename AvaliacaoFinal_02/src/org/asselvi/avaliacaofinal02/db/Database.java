package org.asselvi.avaliacaofinal02.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author marcelo
 */
public class Database extends SQLiteOpenHelper {

	private static final int CURRENT_VERSION = 100;
	private static final String DATABASE = "users_database";
	
	private static final String CREATE_SQL = "CREATE TABLE USERS (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, EMAIL TEXT, PHONE INTEGER, LASTUPDATE INTEGER)";
	
	public static Database getInstance(Context context) {
		return new Database(context, DATABASE, null, CURRENT_VERSION);
	}
	
	public Database(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_SQL);
		db.execSQL("INSERT INTO USERS (NAME, EMAIL, PHONE, LASTUPDATE) VALUES ('Teste 1', 'teste@teste.com.br', '99009900',"+ System.currentTimeMillis() + ")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
